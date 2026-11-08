#!/usr/bin/env python3
"""Validate a release-notes file against the repository template and house style.

The template at .github/RELEASE_NOTES_TEMPLATE.md is the single source of truth for the
top-level sections and their subsections. On top of it, the checker enforces the pattern
used by every published release in this project:

  * the exact title "# EnduranceTrio Community - Release vX.Y.Z";
  * a summary paragraph before the first section;
  * every template section, in template order, with no extra sections;
  * only the template subsections, only when they contain content;
  * the literal "None." marker for an empty section (an initial release may add a short
    explanation after an em dash);
  * the fixed deployment footer pointing at ghcr.io/endurancetrio/endurancetrio-community;
  * no HTML comments or leftover placeholder text from the template.

Usage:
    check-release-notes.py --file PATH [--version X.Y.Z] [--template PATH]

The version is read from the title when --version is omitted. Exit code 0 means the
release notes comply; 1 means at least one error must be fixed. Warnings do not fail.
"""

from __future__ import annotations

import argparse
import re
import sys
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parents[4]
DEFAULT_TEMPLATE = REPO_ROOT / ".github/RELEASE_NOTES_TEMPLATE.md"

# Text from the template comments that must never survive into release notes.
PLACEHOLDER_SNIPPETS = (
    "A summary of changes, enhancements, and fixes",
    "List any changes that break backward compatibility",
    "List new endpoints, major new functionality",
    "List updates to core business logic",
    "List any critical issues, especially",
    "List minor bugs or issues resolved",
    "List any optimizations, especially",
    "List any major code restructuring",
    "List any significant changes to documentation",
    "List any major dependency version updates",
    "Add any additional notes or acknowledgments",
)

TITLE_RE = re.compile(r"^# EnduranceTrio Community - Release v(\d+\.\d+\.\d+)$")
H1_RE = re.compile(r"^# (.+?)\s*$")
H2_RE = re.compile(r"^## (.+?)\s*$")
H3_RE = re.compile(r"^### (.+?)\s*$")
HR_RE = re.compile(r"^---\s*$")
BOLD_BULLET_RE = re.compile(r"^- \*\*[^*]+\*\* \u2014 \S")
DEPLOY_RE = re.compile(
    r"^\*\*Deployment\*\*: the Docker image for this release is published as "
    r"`ghcr\.io/endurancetrio/endurancetrio-community:(\d+\.\d+\.\d+)`\. "
    r"Set `PRD_VERSION=(\d+\.\d+\.\d+)` in the deployment `\.env`\.$"
)
ACKNOWLEDGMENTS_RE = re.compile(r"^\*\*Acknowledgments\*\*: \S")
VERSION_RE = re.compile(r"^\d+\.\d+\.\d+$")
NONE_RE = re.compile(r"^None(\.|\s+\u2014\s+\S.*)$")


def parse_template(text: str) -> tuple[list[str], dict[str, list[str]]]:
    """Return the template H2 sections and the H3 subsections of each section."""
    sections: list[str] = []
    subsections: dict[str, list[str]] = {}
    current: str | None = None

    for line in text.splitlines():
        section = H2_RE.match(line)
        if section:
            current = section.group(1).strip()
            sections.append(current)
            subsections[current] = []
            continue

        subsection = H3_RE.match(line)
        if current is not None and subsection:
            subsections[current].append(subsection.group(1).strip())

    return sections, subsections


def split_footer(text: str) -> tuple[str, list[str], list[str]]:
    """Split the body at the horizontal rule that introduces the deployment footer."""
    lines = text.splitlines()
    rule_indexes: list[int] = []
    in_fence = False

    for index, line in enumerate(lines):
        stripped = line.strip()
        if stripped.startswith("```") or stripped.startswith("~~~"):
            in_fence = not in_fence
            continue
        if not in_fence and HR_RE.match(line):
            rule_indexes.append(index)

    if len(rule_indexes) != 1:
        return (
            text,
            [],
            [
                'expected exactly one "---" separator before the deployment footer, '
                f"found {len(rule_indexes)}"
            ],
        )

    index = rule_indexes[0]
    return "\n".join(lines[:index]), lines[index + 1 :], []


def visible_lines(text: str) -> list[str]:
    """Return the lines outside fenced code blocks."""
    result: list[str] = []
    in_fence = False

    for line in text.splitlines():
        stripped = line.strip()
        if stripped.startswith("```") or stripped.startswith("~~~"):
            in_fence = not in_fence
            continue
        if not in_fence:
            result.append(line)

    return result


def split_h2(text: str) -> tuple[list[str], dict[str, str]]:
    """Split text into H2 sections, ignoring headings inside fenced code blocks."""
    return split_sections(text, H2_RE)


def split_h3(text: str) -> tuple[list[str], dict[str, str]]:
    """Split text into H3 subsections, ignoring headings inside fenced code blocks."""
    return split_sections(text, H3_RE)


def split_sections(
    text: str, heading_re: re.Pattern[str]
) -> tuple[list[str], dict[str, str]]:
    order: list[str] = []
    sections: dict[str, str] = {}
    current: str | None = None
    buffer: list[str] = []
    in_fence = False

    def flush() -> None:
        if current is not None:
            sections[current] = "\n".join(buffer).strip()

    for raw_line in text.splitlines():
        line = raw_line.rstrip("\r")
        stripped = line.strip()

        if stripped.startswith("```") or stripped.startswith("~~~"):
            in_fence = not in_fence
            if current is not None:
                buffer.append(line)
            continue

        match = None if in_fence else heading_re.match(line)
        if match:
            flush()
            current = match.group(1).strip()
            order.append(current)
            buffer = []
        elif current is not None:
            buffer.append(line)

    flush()
    return order, sections


def bullets(text: str) -> list[str]:
    """Return top-level bullet lines outside fenced code blocks."""
    return [line for line in visible_lines(text) if line.startswith("- ")]


def is_none_marker(text: str) -> bool:
    """True when a section says it has no entries (for example "None.")."""
    if bullets(text):
        return False
    first = next((line.strip() for line in text.splitlines() if line.strip()), "")
    return NONE_RE.match(first) is not None


def first_content(text: str) -> str:
    return next((line.strip() for line in text.splitlines() if line.strip()), "")


def first_heading_index(text: str, heading_re: re.Pattern[str]) -> int | None:
    """Return the index of the first heading outside fenced code blocks."""
    in_fence = False

    for index, line in enumerate(text.splitlines()):
        stripped = line.strip()
        if stripped.startswith("```") or stripped.startswith("~~~"):
            in_fence = not in_fence
            continue
        if not in_fence and heading_re.match(line):
            return index

    return None


def paragraphs(lines: list[str]) -> list[list[str]]:
    """Group lines into paragraphs separated by blank lines."""
    result: list[list[str]] = []
    current: list[str] = []

    for line in lines:
        stripped = line.strip()
        if stripped:
            current.append(stripped)
        elif current:
            result.append(current)
            current = []

    if current:
        result.append(current)

    return result


def validate_footer(
    footer_lines: list[str], version: str, errors: list[str]
) -> None:
    footer_paragraphs = paragraphs(footer_lines)

    if not footer_paragraphs:
        errors.append("deployment footer is missing after the --- separator")
        return

    deployment = footer_paragraphs[0]
    if len(deployment) != 1:
        errors.append("the deployment footer must be a single line")

    match = DEPLOY_RE.match(deployment[0])
    if not match:
        errors.append(
            "deployment footer does not match the required sentence: "
            '"**Deployment**: the Docker image for this release is published as '
            '`ghcr.io/endurancetrio/endurancetrio-community:X.Y.Z`. '
            'Set `PRD_VERSION=X.Y.Z` in the deployment `.env`."'
        )
    else:
        image_version, env_version = match.group(1), match.group(2)
        if image_version != version:
            errors.append(
                f"deployment image version is {image_version}, expected {version}"
            )
        if env_version != version:
            errors.append(
                f"deployment PRD_VERSION is {env_version}, expected {version}"
            )

    if len(footer_paragraphs) > 2:
        errors.append(
            "only one optional acknowledgments paragraph may follow the deployment footer"
        )
        return

    if len(footer_paragraphs) == 2:
        acknowledgment = footer_paragraphs[1]
        if not ACKNOWLEDGMENTS_RE.match(acknowledgment[0]):
            errors.append(
                'the paragraph after the deployment footer must start with '
                f'"**Acknowledgments**: ...", found: "{acknowledgment[0][:80]}"'
            )


def check_section(
    section: str,
    text: str,
    template_subsections: list[str],
    errors: list[str],
    warnings: list[str],
) -> None:
    order, subsections = split_h3(text)

    duplicates = sorted({name for name in order if order.count(name) > 1})
    if duplicates:
        errors.append(
            f'section "{section}" has duplicate subsection(s): ' + ", ".join(duplicates)
        )

    unknown = [name for name in order if name not in template_subsections]
    if unknown:
        errors.append(
            f'section "{section}" has subsection(s) not in the template: '
            + ", ".join(f'"{name}"' for name in unknown)
        )

    ordered_body = [name for name in order if name in template_subsections]
    ordered_template = [name for name in template_subsections if name in subsections]
    if ordered_body != ordered_template:
        errors.append(
            f'subsections of "{section}" are out of template order; expected: '
            + (" -> ".join(ordered_template) if ordered_template else "(none)")
        )

    # split_sections only records content after the first heading. Recover the text
    # before the first H3 separately.
    first_heading = first_heading_index(text, H3_RE)
    prefix = (
        text
        if first_heading is None
        else "\n".join(text.splitlines()[:first_heading])
    )
    prefix = prefix.strip()

    if order:
        if prefix and is_none_marker(prefix):
            errors.append(
                f'section "{section}" mixes None with subsections; use one or the other'
            )
        elif prefix:
            errors.append(
                f'section "{section}" has content before its first subsection; '
                "put content inside a subsection"
            )
        if not any(subsections.get(name, "").strip() for name in order):
            errors.append(
                f'section "{section}" must contain a populated subsection or None.'
            )

        for name in ordered_body:
            subsection_text = subsections.get(name, "")
            if not subsection_text.strip():
                errors.append(
                    f'subsection "{name}" of "{section}" is empty; omit it instead'
                )
                continue
            if is_none_marker(subsection_text):
                errors.append(
                    f'subsection "{name}" of "{section}" uses None.; '
                    "omit the subsection instead"
                )
                continue
            if not bullets(subsection_text):
                errors.append(
                    f'subsection "{name}" of "{section}" must contain a bullet list'
                )
            check_bullet_style(name, subsection_text, warnings)
        return

    if not prefix:
        errors.append(
            f'section "{section}" is empty; write None. or add a bullet list'
        )
        return

    section_bullets = bullets(prefix)
    if section_bullets:
        if is_none_marker(prefix):
            errors.append(f'section "{section}" mixes None with a bullet list')
        check_bullet_style(section, prefix, warnings)
    elif not is_none_marker(prefix):
        errors.append(
            f'section "{section}" must contain a bullet list or the literal None.'
        )


def check_bullet_style(section: str, text: str, warnings: list[str]) -> None:
    section_bullets = bullets(text)
    non_conforming = [line for line in section_bullets if not BOLD_BULLET_RE.match(line)]
    if non_conforming:
        warnings.append(
            f'{section}: {len(non_conforming)} bullet(s) do not use '
            f'"- **Area** \u2014 description" (first: {non_conforming[0][:80]})'
        )


def validate(text: str, template_text: str, requested_version: str | None) -> int:
    errors: list[str] = []
    warnings: list[str] = []

    text = text.replace("\r\n", "\n").replace("\r", "\n")

    if "<!--" in text or "-->" in text:
        errors.append("release notes still contain an HTML comment; remove all template comments")

    for snippet in PLACEHOLDER_SNIPPETS:
        if snippet in text:
            errors.append(
                f'release notes still contain template placeholder text: "{snippet}"'
            )

    body_text, footer_lines, footer_problems = split_footer(text)
    errors.extend(footer_problems)

    lines = body_text.splitlines()
    title_index = next(
        (index for index, line in enumerate(lines) if line.strip()), None
    )
    if title_index is None:
        errors.append("release notes are empty")
        return report(errors, warnings)

    title_line = lines[title_index].strip()
    title_match = TITLE_RE.match(title_line)
    if not title_match:
        errors.append(
            'title must be exactly "# EnduranceTrio Community - Release vX.Y.Z"'
        )
        version = requested_version or ""
    else:
        version = title_match.group(1)
        if requested_version and version != requested_version:
            errors.append(
                f"title version is {version}, expected {requested_version}"
            )

    if requested_version and not VERSION_RE.match(requested_version):
        errors.append(f"requested version is not X.Y.Z: {requested_version}")

    body_after_title = "\n".join(lines[title_index + 1 :])

    extra_titles = [
        line for line in visible_lines(body_after_title) if H1_RE.match(line)
    ]
    if extra_titles:
        errors.append("release notes must contain exactly one H1 title")

    order, sections = split_h2(body_after_title)

    template_sections, template_subsections = parse_template(template_text)

    first_h2 = first_heading_index(body_after_title, H2_RE)
    intro = (
        body_after_title
        if first_h2 is None
        else "\n".join(body_after_title.splitlines()[:first_h2])
    ).strip()

    if not intro:
        errors.append("a summary paragraph is required between the title and the first section")
    elif len(first_content(intro)) < 20:
        errors.append("the summary paragraph is too short to describe the release")

    duplicates = sorted({name for name in order if order.count(name) > 1})
    if duplicates:
        errors.append("duplicate section(s): " + ", ".join(duplicates))

    unknown = [name for name in order if name not in template_sections]
    if unknown:
        errors.append(
            "section(s) not in the template: "
            + ", ".join(f'"{name}"' for name in unknown)
        )

    missing = [name for name in template_sections if name not in sections]
    if missing:
        errors.append(
            "missing required section(s): "
            + ", ".join(f'"{name}"' for name in missing)
        )

    if order != template_sections:
        errors.append(
            "sections are not in template order; expected: "
            + " -> ".join(template_sections)
        )

    for section in template_sections:
        if section in sections:
            check_section(
                section,
                sections[section],
                template_subsections.get(section, []),
                errors,
                warnings,
            )

    if version:
        validate_footer(footer_lines, version, errors)
    else:
        validate_footer(footer_lines, "X.Y.Z", errors)

    return report(errors, warnings)


def report(errors: list[str], warnings: list[str]) -> int:
    for warning in warnings:
        print(f"WARNING: {warning}")

    if errors:
        for error in errors:
            print(f"ERROR: {error}")
        print(f"Release notes check: FAILED ({len(errors)} error(s), {len(warnings)} warning(s))")
        return 1

    print(
        f"Release notes check: OK ({len(warnings)} warning(s)) — matches the release-note pattern"
    )
    return 0


def main() -> int:
    parser = argparse.ArgumentParser(
        description="Validate release notes against the repository template."
    )
    parser.add_argument("--file", required=True, help="Release-notes Markdown file")
    parser.add_argument(
        "--version",
        help="Expected release version (X.Y.Z); read from the title when omitted",
    )
    parser.add_argument(
        "--template",
        default=str(DEFAULT_TEMPLATE),
        help="Path to the release-notes template (default: .github/RELEASE_NOTES_TEMPLATE.md)",
    )
    args = parser.parse_args()

    template_path = Path(args.template)
    if not template_path.is_file():
        print(
            f"Release notes check: FAILED\ntemplate not found: {template_path}",
            file=sys.stderr,
        )
        return 1

    file_path = Path(args.file)
    if not file_path.is_file():
        print(
            f"Release notes check: FAILED\nfile not found: {file_path}",
            file=sys.stderr,
        )
        return 1

    version = args.version
    if version is not None:
        version = version.removeprefix("v")

    return validate(
        file_path.read_text(encoding="utf-8"),
        template_path.read_text(encoding="utf-8"),
        version,
    )


if __name__ == "__main__":
    sys.exit(main())
