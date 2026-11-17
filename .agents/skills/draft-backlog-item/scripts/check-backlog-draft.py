#!/usr/bin/env python3
"""Validate a backlog-item draft in tasks/ against the repository template.

The template at .github/ISSUE_TEMPLATE/backlog-item.md is the single source of truth. Its
frontmatter, H2 sections, H3 subsections, HTML prompts and example acceptance criteria are read at
runtime, so a template edit is picked up without changing this script. On top of it, the checker
enforces the house rules for a draft that can be handed to a junior developer or another agent with
no further input:

  * the draft lives under tasks/ (file mode only);
  * the frontmatter matches the template and has a non-empty title;
  * exactly the template H2 sections, in order, with no extras;
  * the template H3 subsections, in order. "Out of scope" and "Dependencies" may be omitted; every
    other template subsection is required and must not be empty;
  * no HTML comments, no leftover template prompt text, no template example criteria;
  * no undecided markers (TBD, TBA, TODO, FIXME, ???, "to be decided", ...);
  * at least two acceptance-criteria checkboxes.

Warnings (non-fatal) cover an empty optional subsection, a Context section without a backticked
code path, and vague wording that suggests an unresolved decision.

Usage:
    check-backlog-draft.py --file PATH [--template PATH]
    check-backlog-draft.py --self-test

--self-test validates the bundled fixture and checks that known defects are rejected.

Exit code 0 means the draft complies (or every self-test passed); 1 means at least one error.
"""

from __future__ import annotations

import argparse
import re
import sys
from dataclasses import dataclass, field
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parents[4]
DEFAULT_TEMPLATE = REPO_ROOT / ".github" / "ISSUE_TEMPLATE" / "backlog-item.md"
FIXTURES_DIR = Path(__file__).resolve().parent / "fixtures"
VALID_FIXTURE = "valid-complete.md"

OPTIONAL_SUBSECTIONS = frozenset({"Dependencies", "Out of scope"})
MIN_PROMPT_LENGTH = 20

COMMENT_RE = re.compile(r"<!--(.*?)-->", re.DOTALL)
CHECKBOX_RE = re.compile(r"^- \[[ xX]\] .+")
CODE_PATH_RE = re.compile(r"`[^`\s]*[/.][^`\s]*`")
UNDECIDED_RE = re.compile(
    r"\b(?:tbd|tba|todo|fixme)\b|\?\?\?|\bto be (?:decided|confirmed|determined)\b",
    re.IGNORECASE,
)
VAGUE_RE = re.compile(
    r"\b(?:maybe|probably|i think|we could|might|perhaps|possibly)\b", re.IGNORECASE
)


@dataclass
class Section:
    """The content of one H2 section: its H3 subsections and the lines directly under the H2."""

    h3_order: list[str] = field(default_factory=list)
    h3_body: dict[str, list[str]] = field(default_factory=dict)
    body: list[str] = field(default_factory=list)


def has_content(lines: list[str]) -> bool:
    return any(line.strip() for line in lines)


def is_under_tasks(path: Path) -> bool:
    try:
        relative = path.resolve().relative_to(REPO_ROOT)
    except ValueError:
        return False
    return relative.parts[:1] == ("tasks",)


def normalize(text: str) -> str:
    return re.sub(r"\s+", " ", text).strip().lower()


def parse_frontmatter(text: str) -> tuple[dict[str, str], str, list[str]]:
    if not text.startswith("---\n"):
        return {}, text, ["missing YAML frontmatter (file must start with '---')"]
    end = text.find("\n---\n", 4)
    if end == -1:
        return {}, text, ["unterminated YAML frontmatter (missing closing '---')"]

    errors: list[str] = []
    data: dict[str, str] = {}
    for line in text[4:end].splitlines():
        if not line.strip():
            continue
        if ":" not in line:
            errors.append(f"invalid frontmatter line: {line!r}")
            continue
        key, _, value = line.partition(":")
        data[key.strip()] = value.strip().strip("'\"")
    return data, text[end + 5:], errors


def parse_structure(body: str) -> tuple[list[str], dict[str, Section]]:
    order: list[str] = []
    sections: dict[str, Section] = {}
    current_h2: str | None = None
    current_h3: str | None = None
    in_fence = False

    for line in body.splitlines():
        stripped = line.strip()
        if stripped.startswith(("```", "~~~")):
            in_fence = not in_fence
        elif not in_fence and line.startswith("## "):
            current_h2 = line[3:].strip()
            current_h3 = None
            order.append(current_h2)
            sections[current_h2] = Section()
            continue
        elif not in_fence and line.startswith("### ") and current_h2 is not None:
            current_h3 = line[4:].strip()
            sections[current_h2].h3_order.append(current_h3)
            sections[current_h2].h3_body[current_h3] = []
            continue

        if current_h2 is not None:
            target = sections[current_h2]
            if current_h3 is None:
                target.body.append(line)
            else:
                target.h3_body[current_h3].append(line)

    return order, sections


def visible_lines(text: str) -> list[str]:
    result: list[str] = []
    in_fence = False
    for line in text.splitlines():
        stripped = line.strip()
        if stripped.startswith(("```", "~~~")):
            in_fence = not in_fence
            continue
        if not in_fence:
            result.append(line)
    return result


def check_section(
        name: str, section: Section, template_section: Section,
        errors: list[str], warnings: list[str],
) -> None:
    last_index = -1
    for h3 in section.h3_order:
        if h3 not in template_section.h3_order:
            errors.append(f"unexpected subsection '### {h3}' under '## {name}'")
            continue
        index = template_section.h3_order.index(h3)
        if index <= last_index:
            errors.append(f"subsection '### {h3}' is out of order under '## {name}'")
        last_index = max(last_index, index)

    if not template_section.h3_order:
        if not has_content(section.body):
            errors.append(f"section '## {name}' is empty")
        return

    for h3 in template_section.h3_order:
        present = h3 in section.h3_order
        if h3 in OPTIONAL_SUBSECTIONS:
            if present and not has_content(section.h3_body[h3]):
                warnings.append(f"'### {h3}' is present but empty; delete it")
        elif not present:
            errors.append(f"missing required subsection '### {h3}' under '## {name}'")
        elif not has_content(section.h3_body[h3]):
            errors.append(f"subsection '### {h3}' under '## {name}' is empty")


def validate_draft(
        text: str, template_text: str, path: Path | None = None,
) -> tuple[list[str], list[str]]:
    errors: list[str] = []
    warnings: list[str] = []

    if path is not None and not is_under_tasks(path):
        errors.append(f"draft must live under tasks/ (got {path})")

    template_fm, template_body, _ = parse_frontmatter(template_text)
    template_order, template_sections = parse_structure(template_body)

    fm, body, fm_errors = parse_frontmatter(text)
    errors.extend(fm_errors)
    for key, expected in template_fm.items():
        if key != "title" and fm.get(key) != expected:
            errors.append(f"frontmatter '{key}' must be {expected!r}, got {fm.get(key)!r}")
    if not fm.get("title"):
        errors.append("frontmatter 'title' must be non-empty")
    unexpected_keys = sorted(set(fm) - set(template_fm))
    if unexpected_keys:
        errors.append("unexpected frontmatter keys: " + ", ".join(unexpected_keys))

    if "<!--" in text:
        errors.append("HTML comments are not allowed in the draft")

    normalized_draft = normalize(text)
    for comment in COMMENT_RE.findall(template_text):
        for sentence in re.split(r"(?<=[.!?])\s+", normalize(comment)):
            if len(sentence) >= MIN_PROMPT_LENGTH and sentence in normalized_draft:
                errors.append(f"template prompt text left in draft: '{sentence[:60]}'")

    template_criteria_section = template_sections.get("Acceptance Criteria", Section())
    example_items = {
        line.strip() for line in template_criteria_section.body if CHECKBOX_RE.match(line.strip())
    }
    for line in visible_lines(body):
        if line.strip() in example_items:
            errors.append(f"template example criterion left in draft: '{line.strip()}'")

    for marker in sorted({match.group(0).lower() for match in UNDECIDED_RE.finditer(text)}):
        errors.append(f"undecided marker found: '{marker}'")

    order, sections = parse_structure(body)
    if order != template_order:
        errors.append(f"H2 sections must be exactly {template_order}, got {order}")
    for name in order:
        if name not in template_sections:
            errors.append(f"unexpected section '## {name}'")
            continue
        check_section(name, sections[name], template_sections[name], errors, warnings)

    context_lines = sections["Description"].h3_body.get("Context", []) if "Description" in sections else []
    if not CODE_PATH_RE.search("\n".join(context_lines)):
        warnings.append("'### Context' contains no backticked code path")

    criteria_section = sections.get("Acceptance Criteria", Section())
    criteria = [line for line in criteria_section.body if CHECKBOX_RE.match(line.strip())]
    if len(criteria) < 2:
        errors.append("need at least two acceptance-criteria checkboxes ('- [ ] ...')")

    vague_lines = [line for line in visible_lines(body) if VAGUE_RE.search(line)]
    if vague_lines:
        warnings.append(
            f"vague wording may hide an unresolved decision in {len(vague_lines)} line(s)"
        )

    return errors, warnings


SELF_TEST_MUTATIONS = (
    (
        "frontmatter changed",
        lambda t: t.replace("Report a problem", "Describe a problem", 1),
        "frontmatter 'about'",
    ),
    (
        "extra section",
        lambda t: t + "\n## Notes\n\nExtra.\n",
        "H2 sections must be exactly",
    ),
    (
        "subsections swapped",
        lambda t: t.replace("### Context", "@@SWAP@@").replace("### Scope", "### Context")
        .replace("@@SWAP@@", "### Scope"),
        "out of order",
    ),
    (
        "template prompt left",
        lambda t: t.replace(
            "### Scope\n\n",
            "### Scope\n\nState what you checked, not what you assume.\n\n",
            1,
        ),
        "template prompt text",
    ),
    (
        "undecided marker",
        lambda t: t.replace("### Testing", "### Testing\n\nTODO: decide the test level.", 1),
        "undecided marker",
    ),
    (
        "empty required subsection",
        lambda t: re.sub(r"### Context\n.*?(?=### Scope)", "### Context\n\n", t, flags=re.DOTALL),
        "is empty",
    ),
    (
        "missing checkboxes",
        lambda t: re.sub(r"^- \[ \] .*$", "", t, flags=re.MULTILINE),
        "at least two acceptance-criteria",
    ),
    (
        "template example criterion",
        lambda t: t + "- [ ] Item 1\n",
        "template example criterion",
    ),
    (
        "HTML comment",
        lambda t: t + "\n<!-- note -->\n",
        "HTML comments",
    ),
)


def run_self_test(template_text: str) -> int:
    valid_text = (FIXTURES_DIR / VALID_FIXTURE).read_text(encoding="utf-8")
    failures = 0

    errors, _ = validate_draft(valid_text, template_text)
    if errors:
        failures += 1
        print(f"FAIL  {VALID_FIXTURE} should pass, got:")
        for error in errors:
            print("      " + error)
    else:
        print(f"PASS  {VALID_FIXTURE} is accepted")

    for label, mutate, expected in SELF_TEST_MUTATIONS:
        errors, _ = validate_draft(mutate(valid_text), template_text)
        if any(expected in error for error in errors):
            print(f"PASS  rejected: {label}")
        else:
            failures += 1
            print(f"FAIL  {label}: expected an error containing {expected!r}")

    print(f"\nSelf-test {'failed' if failures else 'passed'} ({failures} failure(s)).")
    return 1 if failures else 0


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("--file", help="path to the draft Markdown file")
    parser.add_argument("--template", default=str(DEFAULT_TEMPLATE), help="path to the template")
    parser.add_argument("--self-test", action="store_true", help="run the bundled self-test")
    args = parser.parse_args()

    template_path = Path(args.template).resolve()
    if not template_path.is_file():
        print(f"ERROR: template does not exist: {template_path}")
        return 1
    template_text = template_path.read_text(encoding="utf-8")

    if args.self_test:
        return run_self_test(template_text)

    if not args.file:
        parser.error("--file is required unless --self-test is given")

    draft_path = Path(args.file).resolve()
    if not draft_path.is_file():
        print(f"ERROR: file does not exist: {draft_path}")
        return 1

    errors, warnings = validate_draft(
        draft_path.read_text(encoding="utf-8"), template_text, draft_path
    )

    for error in errors:
        print("ERROR: " + error)
    for warning in warnings:
        print("WARNING: " + warning)

    if errors:
        print(f"\nValidation failed with {len(errors)} error(s).")
        return 1
    print("\nValidation passed.")
    if warnings:
        print(f"({len(warnings)} warning(s) to review.)")
    return 0


if __name__ == "__main__":
    sys.exit(main())
