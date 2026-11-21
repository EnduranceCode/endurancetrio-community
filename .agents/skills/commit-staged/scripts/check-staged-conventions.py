#!/usr/bin/env python3
"""Check the staged changes against the EnduranceTrio code conventions.

The checker inspects exactly what is in the Git index (git show :<path>), so it
reflects what would be committed. With --source worktree it reads the working
tree instead, which is useful to verify convention fixes before staging them.

Usage:
    check-staged-conventions.py [--source {index,worktree}] [--repo PATH]

Exit code 0 means the staged content complies; 1 means at least one error must be
fixed. Warnings do not fail.
"""

from __future__ import annotations

import argparse
import re
import subprocess
import sys
from pathlib import Path

MIGRATION_DIR_RE = re.compile(
    r"^endurancetrio-(?:app|business|data)/src/main/resources/db/migration/"
    r"(ddl|dml)/(h2|postgres)/"
)
MIGRATION_NAME_RE = re.compile(
    r"^V(\d{8})\.(\d{3})__[a-z0-9][a-z0-9-]*-(postgres|h2)\.sql$"
)
LEGACY_MIGRATION_NAME_RE = re.compile(
    r"^V\d{3}(\.\d{3}){2,}__[a-z0-9][a-z0-9-]*-(postgres|h2)\.sql$"
)
JAVA_PUBLIC_TYPE_RE = re.compile(
    r"\bpublic\s+(?:final\s+|abstract\s+|sealed\s+|static\s+)*"
    r"(class|interface|enum|record|@interface)\s+([A-Za-z_]\w*)"
)
SLICE_ANNOTATIONS = ["@WebMvcTest", "@DataJpaTest", "@WebFluxTest", "@JsonTest", "@RestClientTest"]

# Files that are produced by tooling and must never be hand-edited or checked.
GENERATED_FILES = {
    "endurancetrio-app/src/main/resources/webpack/src/scss/utilities/endurance-icons.scss",
}

errors: list[str] = []
warnings: list[str] = []


def git(repo: str, *args: str) -> str:
    result = subprocess.run(
        ["git", "-C", repo, *args], capture_output=True, text=True
    )
    return result.stdout


def git_ok(repo: str, *args: str) -> bool:
    return subprocess.run(
        ["git", "-C", repo, *args], capture_output=True, text=True
    ).returncode == 0


def read_index(repo: str, path: str) -> str | None:
    result = subprocess.run(
        ["git", "-C", repo, "show", f":{path}"], capture_output=True, text=True
    )
    return result.stdout if result.returncode == 0 else None


def read_worktree(repo: str, path: str) -> str | None:
    full = Path(repo) / path
    try:
        return full.read_text(encoding="utf-8")
    except (OSError, UnicodeDecodeError):
        return None


def read_repo_file(repo: str, path: str) -> str | None:
    """Read from the committed tree: index first, then HEAD, then worktree."""
    value = read_index(repo, path)
    if value is not None:
        return value
    result = subprocess.run(
        ["git", "-C", repo, "show", f"HEAD:{path}"], capture_output=True, text=True
    )
    if result.returncode == 0:
        return result.stdout
    return read_worktree(repo, path)


def all_repo_files(repo: str) -> set[str]:
    tracked = set(git(repo, "ls-files").splitlines())
    head = set(git(repo, "ls-tree", "-r", "--name-only", "HEAD").splitlines())
    return tracked | head


def migration_counterpart(path: str, db: str) -> str:
    """Return the other database's twin for a -h2.sql or -postgres.sql migration."""
    other_db = "postgres" if db == "h2" else "h2"
    file_path = Path(path)
    suffix = f"-{db}.sql"
    stem = file_path.name[: -len(suffix)] if file_path.name.endswith(suffix) else file_path.name
    return str(file_path.parent.parent / other_db / f"{stem}-{other_db}.sql")


def staged_name_status(repo: str) -> list[tuple[str, str]]:
    output = git(repo, "diff", "--cached", "--name-status")
    result: list[tuple[str, str]] = []
    for line in output.splitlines():
        parts = line.split("\t")
        if len(parts) == 2:
            result.append((parts[0], parts[1]))
        elif len(parts) == 3:
            result.append((parts[0], parts[2]))
    return result


def err(path: str, line: int | None, message: str) -> None:
    if line is None:
        errors.append(f"ERROR: {path}: {message}")
    else:
        errors.append(f"ERROR: {path}:{line}: {message}")


def warn(path: str, line: int | None, message: str) -> None:
    if line is None:
        warnings.append(f"WARNING: {path}: {message}")
    else:
        warnings.append(f"WARNING: {path}:{line}: {message}")


def check_java(repo: str, path: str, content: str, source: str) -> None:
    stem = Path(path).name[:-5]  # strip .java

    # Package vs path.
    package_match = re.search(r"^\s*package\s+([A-Za-z0-9_.]+)\s*;", content, re.M)
    parts = path.split("/")
    package_path: list[str] = []
    for i, part in enumerate(parts):
        if part in ("main", "test") and i + 2 < len(parts) and parts[i + 1] == "java":
            package_path = parts[i + 2:-1]
            break
    if package_match:
        expected = ".".join(package_path)
        if package_match.group(1) != expected:
            line_no = content[:package_match.start()].count("\n") + 1
            err(path, line_no, f"package '{package_match.group(1)}' does not match path (expected '{expected}')")

    # Public top-level type name vs file name.
    for match in JAVA_PUBLIC_TYPE_RE.finditer(content):
        type_name = match.group(2)
        if type_name != stem:
            line_no = content[:match.start()].count("\n") + 1
            err(path, line_no, f"public type '{type_name}' does not match file name '{stem}'")

    # Impl suffix is forbidden.
    if stem.endswith("Impl"):
        err(path, None, "implementation suffix 'Impl' is forbidden; use 'Main' or a descriptive suffix")

    # EnumType.ORDINAL / STRING forbidden.
    for token in ("EnumType.ORDINAL", "EnumType.STRING"):
        if token in content:
            idx = content.find(token)
            err(path, content[:idx].count("\n") + 1, f"forbidden {token}; use an AttributeConverter")

    # Javadoc self-closing tags.
    for block in re.finditer(r"/\*\*.*?\*/", content, re.S):
        for tag in re.finditer(r"<[^>]+/>", block.group(0)):
            line_no = content[:block.start() + tag.start()].count("\n") + 1
            err(path, line_no, f"Javadoc contains self-closing tag '{tag.group(0)}'")

    # Entity @Serial.
    if "@Entity" in content and "@Serial" not in content:
        warn(path, None, "entity is missing '@Serial private static final long serialVersionUID = 1L'")

    # Controller / service naming pairings.
    if stem.endswith("RestController"):
        if "@EnduranceTrioRestController" not in content:
            err(path, None, "missing @EnduranceTrioRestController")
        if not re.search(r"implements\s+\w+API\b", content):
            warn(path, None, "RestController does not implement an *API interface")
    if stem.endswith("WebController"):
        if "@EnduranceTrioWebController" not in content:
            err(path, None, "missing @EnduranceTrioWebController")
    if stem.endswith("Service") and not stem.endswith("ServiceMain"):
        if re.search(r"\bclass\s+" + re.escape(stem) + r"\b", content):
            err(path, None, "service interface declared as class; use an interface plus *ServiceMain")
    if stem.endswith("ServiceMain"):
        if "@Service" not in content:
            warn(path, None, "ServiceMain is missing @Service")

    # Test conventions.
    if "/src/test/java/" in path:
        if "@Test" in content:
            if not stem.endswith("Test"):
                err(path, None, "test class name must end with 'Test'")
            if re.search(r"\bpublic\s+(?:final\s+|abstract\s+)?class\s+" + re.escape(stem), content):
                err(path, None, "test class must be package-private (remove 'public')")
            if ("@Mock" in content or "@InjectMocks" in content) and \
                    "@ExtendWith(MockitoExtension.class)" not in content:
                err(path, None, "Mockito test missing @ExtendWith(MockitoExtension.class)")
            if "@InjectMocks" in content:
                match = re.search(
                    r"@InjectMocks\s*\n\s*(?:private|protected|public)?\s*\w+\s+(\w+)", content
                )
                if match and match.group(1) != "underTest":
                    err(path, None, "@InjectMocks field must be named 'underTest'")
            if "@MockBean" in content:
                err(path, None, "@MockBean is not allowed; declare collaborators as @Bean mocks")
            if "@SpringBootTest" in content:
                warn(path, None, "@SpringBootTest loads a full context; prefer a slice test")
            if any(a in content for a in SLICE_ANNOTATIONS):
                if '@ActiveProfiles("test")' not in content and "@ActiveProfiles('test')" not in content:
                    warn(path, None, "slice test missing @ActiveProfiles(\"test\")")
                for annotation in SLICE_ANNOTATIONS:
                    if annotation in content:
                        slice_name = annotation.lstrip("@").replace("Test", "")
                        if slice_name not in stem:
                            warn(path, None, f"slice test name should contain '{slice_name}'")


def check_migration(repo: str, path: str, status: str, staged_paths: set[str]) -> None:
    match = MIGRATION_DIR_RE.match(path)
    if not match:
        return
    kind, db = match.group(1), match.group(2)
    directory = str(Path(path).parent)
    name = Path(path).name

    if not (MIGRATION_NAME_RE.match(name) or LEGACY_MIGRATION_NAME_RE.match(name)):
        err(path, None, "invalid migration file name (expected V<yyyymmdd>.<seq>__description-{h2,postgres}.sql)")

    counterpart = migration_counterpart(path, db)

    # Version uniqueness across all migration directories, excluding the database twin.
    version_match = re.match(r"^(V[^_]+)__", name)
    if version_match:
        version = version_match.group(1)
        duplicates: list[str] = []
        for other in all_repo_files(repo):
            if MIGRATION_DIR_RE.match(other):
                other_name = Path(other).name
                other_version_match = re.match(r"^(V[^_]+)__", other_name)
                if (
                    other_version_match
                    and other_version_match.group(1) == version
                    and other != path
                    and other != counterpart
                ):
                    duplicates.append(other)
        # Also count staged duplicates not yet in all_repo_files.
        if duplicates:
            err(path, None, f"duplicate migration version {version}: {', '.join(sorted(duplicates))}")

    # DDL requires a twin.
    if kind == "ddl":
        if counterpart not in all_repo_files(repo) and counterpart not in staged_paths:
            err(path, None, f"missing DDL twin: {counterpart}")

    # DML postgres without H2 twin is a warning (porting is event-date dependent).
    if kind == "dml" and db == "postgres":
        if counterpart not in all_repo_files(repo) and counterpart not in staged_paths:
            warn(path, None, "no H2 twin; if the event is dated 1989 or earlier, port it with the port-postgres-dml-to-h2 skill")

    # New migration should be registered in DATABASE.md.
    if status.startswith("A"):
        if "endurancetrio-data/src/main/resources/db/DATABASE.md" not in staged_paths:
            warn(path, None, "new migration staged without a DATABASE.md update")


def validate_yaml_key_order(content: str) -> list[tuple[int, str]]:
    result: list[tuple[int, str]] = []
    # levels: dict indent -> list of keys seen at that indent
    levels: dict[int, list[str]] = {}
    for i, raw in enumerate(content.splitlines(), start=1):
        if not raw.strip() or raw.lstrip().startswith("#"):
            continue
        stripped = raw.strip()
        if stripped.startswith("- "):
            continue
        if ":" not in stripped:
            continue
        indent = len(raw) - len(raw.lstrip())
        key = stripped.split(":", 1)[0].strip()
        if (key.startswith('"') and key.endswith('"')) or (key.startswith("'") and key.endswith("'")):
            key = key[1:-1]
        if not key:
            continue

        # Drop deeper levels only; keep siblings at the same indent.
        for level in [lvl for lvl in levels if lvl > indent]:
            del levels[level]

        if indent not in levels:
            levels[indent] = []
        keys = levels[indent]

        if keys:
            prev = keys[-1]
            # username/password exception: username always before password.
            if prev == "password" and key == "username":
                result.append((i, "keys out of order: 'username' must precede 'password'"))
            elif not (prev == "username" and key == "password"):
                if prev > key:
                    result.append((i, f"keys out of order: '{key}' should come before '{prev}'"))
        keys.append(key)
    return result


def check_config(repo: str, path: str, content: str) -> None:
    basename = Path(path).name
    if basename.startswith("application") and basename.endswith(".yaml"):
        for line_no, message in validate_yaml_key_order(content):
            err(path, line_no, message)
    elif basename == "template-secrets.yaml":
        for line_no, message in validate_yaml_key_order(content):
            err(path, line_no, message)
    elif path.endswith(".properties") and not Path(path).name.startswith("messages"):
        keys: list[str] = []
        key_lines: list[int] = []
        for i, raw in enumerate(content.splitlines(), start=1):
            stripped = raw.strip()
            if not stripped or stripped.startswith("#") or "=" not in stripped:
                continue
            key = stripped.split("=", 1)[0].strip()
            keys.append(key)
            key_lines.append(i)
        for idx in range(1, len(keys)):
            prev, curr = keys[idx - 1], keys[idx]
            if prev == "password" and curr == "username":
                err(path, key_lines[idx], "keys out of order: 'username' must precede 'password'")
            elif not (prev == "username" and curr == "password"):
                if prev > curr:
                    err(path, key_lines[idx], f"keys out of order: '{curr}' should come before '{prev}'")


def check_frontend(repo: str, path: str, staged_paths: set[str]) -> None:
    webpack_prefix = "endurancetrio-app/src/main/resources/webpack/"
    if path.startswith(webpack_prefix) and path.endswith(".scss") and path != webpack_prefix + "src/scss/style.scss":
        rel = path[len(webpack_prefix + "src/scss/"):]
        rel_no_ext = rel[:-5]  # strip .scss
        style_content = read_repo_file(repo, webpack_prefix + "src/scss/style.scss")
        if style_content is not None:
            active = f"@forward '{rel_no_ext}'" in style_content or f'@forward "{rel_no_ext}"' in style_content
            commented = (
                f"// @forward '{rel_no_ext}'" in style_content
                or f'// @forward "{rel_no_ext}"' in style_content
            )
            if not active:
                if commented:
                    warn(path, None, "partial is only commented out in src/scss/style.scss; enable its @forward to compile")
                else:
                    err(path, None, "new SCSS partial is not @forwarded in src/scss/style.scss")

    # endurance- prefixed modifiers are forbidden.
    if path.endswith((".scss", ".html", ".js")):
        content = read_index(repo, path) if False else None
        # Read from the source selected by the caller is handled by the caller; here read index.
        text = read_index(repo, path)
        if text is None:
            text = read_worktree(repo, path)
        if text:
            for match in re.finditer(r"endurance-[a-z0-9-]*-(?:is|has)-[a-z0-9-]*", text):
                line_no = text[:match.start()].count("\n") + 1
                err(path, line_no, f"modifier must not take the 'endurance-' prefix: '{match.group(0)}'")

    # Style guide update warning for webpack changes.
    # Handled centrally in main().


def main() -> int:
    global errors, warnings
    parser = argparse.ArgumentParser(
        description="Check staged changes against the EnduranceTrio code conventions."
    )
    parser.add_argument(
        "--source", choices=["index", "worktree"], default="index",
        help="Where to read file contents from (default: index)",
    )
    parser.add_argument("--repo", default=".", help="Repository root (default: current directory)")
    args = parser.parse_args()

    repo = str(Path(args.repo).resolve())
    read = read_worktree if args.source == "worktree" else read_index

    # Preconditions.
    if not git_ok(repo, "rev-parse", "--git-dir"):
        print("conventions check: FAILED\nnot a git repository", file=sys.stderr)
        return 1

    unmerged = git(repo, "ls-files", "-u").strip()
    if unmerged:
        print("conventions check: FAILED\nunmerged entries present; resolve conflicts first",
              file=sys.stderr)
        return 1

    name_status = staged_name_status(repo)
    if not name_status:
        print("conventions check: FAILED\nnothing is staged", file=sys.stderr)
        return 1

    # Whitespace check on the index.
    check_result = subprocess.run(
        ["git", "-C", repo, "diff", "--cached", "--check"],
        capture_output=True, text=True,
    )
    if check_result.stdout.strip() or check_result.stderr.strip():
        for line in (check_result.stdout + check_result.stderr).splitlines():
            if line.strip():
                errors.append(f"ERROR: whitespace: {line}")

    staged_paths = {item[1] for item in name_status}
    all_files = all_repo_files(repo)

    # Style guide warning (central).
    frontend_root = "endurancetrio-app/src/main/resources/"
    frontend_changed = any(
        p.startswith(frontend_root + "webpack/") or p.startswith(frontend_root + "templates/")
        for p in staged_paths
    )
    style_guide_changed = any(p.startswith("docs/style-guide/") for p in staged_paths)
    if frontend_changed and not style_guide_changed:
        warnings.append(
            "WARNING: (repo): frontend changes staged without a docs/style-guide update "
            "(required when adding components, behaviours, icons or non-standard HTML)"
        )

    for status, path in name_status:
        # Scope checks.
        if Path(path).name == "application-secrets.yaml":
            err(path, None, "application-secrets.yaml must never be committed")
        if path.startswith("tasks/") and path != "tasks/info.md":
            err(path, None, "files under tasks/ must not be committed (gitignored)")

        if status.startswith("D"):
            # Deleted files have no staged blob to inspect; nothing to check.
            continue

        if path in GENERATED_FILES:
            # Generated file: skip all convention checks (regenerate via tooling).
            continue

        content = read(repo, path)
        if content is None:
            # Binary or unreadable; skip content checks but warn.
            warn(path, None, "file is binary or unreadable; content checks skipped")
            continue

        # Binary/large file warnings.
        if "\x00" in content:
            warn(path, None, "binary file staged")
        added_lines = content.count("\n")
        if added_lines > 2000:
            warn(path, None, f"large file staged ({added_lines} lines)")

        # Secrets scan.
        if re.search(r"-----BEGIN (?:RSA |OPENSSH |EC |PGP )?PRIVATE KEY-----", content):
            err(path, None, "staged content contains a private key")
        if re.search(r"\bAKIA[0-9A-Z]{16}\b", content):
            err(path, None, "staged content contains an AWS access key id")

        # Java.
        if path.endswith(".java"):
            check_java(repo, path, content, args.source)

        # Migrations.
        check_migration(repo, path, status, staged_paths)

        # Config.
        check_config(repo, path, content)

        # Frontend.
        check_frontend(repo, path, staged_paths)

    for warning in warnings:
        print(warning)
    if errors:
        for error in errors:
            print(error)
        print(f"Staged conventions check: FAILED ({len(errors)} error(s), {len(warnings)} warning(s))")
        return 1

    print(f"Staged conventions check: OK ({len(warnings)} warning(s))")
    return 0


if __name__ == "__main__":
    sys.exit(main())
