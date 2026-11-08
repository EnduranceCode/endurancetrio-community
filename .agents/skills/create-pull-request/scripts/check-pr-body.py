#!/usr/bin/env python3
"""Validate a pull request title and body against the repository template.

The template at .github/PULL_REQUEST_TEMPLATE.md is the single source of truth: the
expected section headings, the Type of Change options and the Checklist items are read
from it at runtime. This means the checker automatically follows future template edits.

On top of the template, the checker enforces the house style used by every pull request
in this repository:

  * the four template sections, in template order, with no extra top-level sections;
  * an optional Screenshots section, only when real screenshots are embedded;
  * no HTML comments or leftover placeholder text from the template;
  * a Description with a summary paragraph and a "### Key changes" bullet list;
  * all Type of Change options present, in order, with at least one checked;
  * all Checklist options present and checked, except the documentation item;
  * a title in imperative mood, without a Conventional Commits prefix or trailing period.

Usage:
    check-pr-body.py --body-file PATH [--title TITLE] [--template PATH]

PATH may be "-" to read the body from standard input. Exit code 0 means the pull
request complies; 1 means at least one error must be fixed. Warnings do not fail.
"""

from __future__ import annotations

import argparse
import re
import sys
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parents[4]
DEFAULT_TEMPLATE = REPO_ROOT / ".github/PULL_REQUEST_TEMPLATE.md"

# Sections that may be omitted. The template itself tells authors to delete Screenshots
# when it is not needed, so the checker accepts it as optional instead of required.
OPTIONAL_SECTIONS = {"Screenshots"}

# Sections that cannot be empty.
NON_EMPTY_SECTIONS = {"Description", "Type of Change", "How Has This Been Tested?", "Checklist"}

# Text from the template comments that must never survive into a pull request.
PLACEHOLDER_SNIPPETS = (
    "Provide a clear and concise summary",
    "Check the box that applies",
    "Describe the steps you took to verify",
    "If applicable, add screenshots",
    "Ensure all items are complete",
)

H2_RE = re.compile(r"^## (.+?)\s*$")
H3_RE = re.compile(r"^### (.+?)\s*$")
OPTION_RE = re.compile(r"^- \[([ xX])\] (.+?)\s*$")
CONVENTIONAL_PREFIX_RE = re.compile(r"^[a-z]+(\([^)]*\))?!?:")
KEY_CHANGE_RE = re.compile(r"^- \*\*[^*]+\*\* \u2014 \S")


def parse_template(text: str) -> tuple[list[str], dict[str, list[str]]]:
    """Return the H2 headings and the checkbox options of each heading."""
    headings: list[str] = []
    options: dict[str, list[str]] = {}
    current: str | None = None

    for line in text.splitlines():
        match = H2_RE.match(line)
        if match:
            current = match.group(1).strip()
            headings.append(current)
            options[current] = []
            continue

        option = OPTION_RE.match(line)
        if current is not None and option:
            options[current].append(option.group(2).strip())

    return headings, options


def split_sections(text: str) -> tuple[list[str], dict[str, str]]:
    """Split a body into H2 sections, ignoring headings inside fenced code blocks."""
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

        match = None if in_fence else H2_RE.match(line)
        if match:
            flush()
            current = match.group(1).strip()
            order.append(current)
            buffer = []
        elif current is not None:
            buffer.append(line)

    flush()
    return order, sections


def subheadings(text: str) -> list[str]:
    """Return the H3 headings of a section, ignoring fenced code blocks."""
    found: list[str] = []
    in_fence = False

    for line in text.splitlines():
        stripped = line.strip()
        if stripped.startswith("```") or stripped.startswith("~~~"):
            in_fence = not in_fence
            continue
        if in_fence:
            continue
        match = H3_RE.match(line)
        if match:
            found.append(match.group(1).strip())

    return found


def parse_options(text: str) -> list[tuple[bool, str]]:
    """Return (checked, label) pairs from a section, in order."""
    options: list[tuple[bool, str]] = []
    for line in text.splitlines():
        match = OPTION_RE.match(line)
        if match:
            options.append((match.group(1).lower() == "x", match.group(2).strip()))
    return options


def describe_option_differences(
    expected: list[str], actual: list[str]
) -> list[str]:
    """Explain how a checkbox list differs from the template."""
    problems: list[str] = []

    missing = [label for label in expected if label not in actual]
    extra = [label for label in actual if label not in expected]

    if missing:
        problems.append(
            "missing template option(s): " + "; ".join(f'"{label}"' for label in missing)
        )
    if extra:
        problems.append(
            "option(s) not in the template: " + "; ".join(f'"{label}"' for label in extra)
        )
    if not missing and not extra and actual != expected:
        problems.append("options are present but not in template order")

    return problems


def check_title(title: str, errors: list[str], warnings: list[str]) -> None:
    if not title:
        errors.append("title is empty")
        return

    if title != title.strip():
        errors.append("title has leading or trailing whitespace")
    if title.endswith("."):
        errors.append("title must not end with a period")
    if not re.match(r"^[A-Z0-9]", title):
        errors.append("title must start with a capital letter")
    if CONVENTIONAL_PREFIX_RE.match(title):
        errors.append(
            "title must not use a Conventional Commits prefix (for example 'feat:' or 'fix:')"
        )
    if len(title) > 100:
        errors.append(f"title is {len(title)} characters; keep it at 100 or fewer")
    elif len(title) > 72:
        warnings.append(f"title is {len(title)} characters; 72 or fewer reads better")


def check_body(
    body: str,
    template_text: str,
    errors: list[str],
    warnings: list[str],
) -> None:
    template_order, template_options = parse_template(template_text)
    required = [heading for heading in template_order if heading not in OPTIONAL_SECTIONS]

    if "<!--" in body or "-->" in body:
        errors.append("body still contains an HTML comment; remove all template comments")

    for snippet in PLACEHOLDER_SNIPPETS:
        if snippet in body:
            errors.append(f'body still contains template placeholder text: "{snippet}"')

    if not body.strip():
        errors.append("body is empty")
        return

    order, sections = split_sections(body)

    duplicates = sorted({heading for heading in order if order.count(heading) > 1})
    if duplicates:
        errors.append("duplicate section(s): " + ", ".join(duplicates))

    unknown = [heading for heading in order if heading not in template_order]
    if unknown:
        errors.append(
            "section(s) not in the template: " + ", ".join(f'"{heading}"' for heading in unknown)
        )

    missing = [heading for heading in required if heading not in sections]
    if missing:
        errors.append(
            "missing required section(s): " + ", ".join(f'"{heading}"' for heading in missing)
        )

    ordered_body = [heading for heading in order if heading in template_order]
    ordered_template = [heading for heading in template_order if heading in sections]
    if ordered_body != ordered_template:
        errors.append(
            "sections are out of template order; expected: " + " -> ".join(ordered_template)
        )

    for heading in NON_EMPTY_SECTIONS & sections.keys():
        if not sections[heading]:
            errors.append(f'section "{heading}" is empty')

    description = sections.get("Description", "")
    if description:
        check_description(description, errors, warnings)

    if "Screenshots" in sections:
        screenshots = sections["Screenshots"]
        if not screenshots:
            errors.append(
                'section "Screenshots" is empty; delete the section when there is nothing to show'
            )
        elif "![" not in screenshots:
            errors.append(
                'section "Screenshots" has no embedded image; use Markdown image syntax or '
                "delete the section"
            )

    if "Type of Change" in template_order:
        expected = template_options.get("Type of Change", [])
        check_type_of_change(
            sections.get("Type of Change", ""), expected, errors
        )

    if "Checklist" in template_order:
        expected = template_options.get("Checklist", [])
        check_checklist(sections.get("Checklist", ""), expected, errors)


def check_description(
    description: str, errors: list[str], warnings: list[str]
) -> None:
    headings = subheadings(description)

    if headings != ["Key changes"]:
        if not headings:
            errors.append(
                'section "Description" must contain a "### Key changes" subsection'
            )
        else:
            errors.append(
                'section "Description" must contain only the "### Key changes" subsection; '
                "found: " + ", ".join(f'"### {heading}"' for heading in headings)
            )

    lines = description.splitlines()
    first_content = next((line.strip() for line in lines if line.strip()), "")
    if first_content.startswith("#"):
        errors.append(
            'section "Description" must start with a summary paragraph before "### Key changes"'
        )
    elif len(first_content) < 20:
        errors.append(
            'section "Description" must start with a meaningful summary paragraph'
        )

    key_index = next(
        (index for index, line in enumerate(lines) if H3_RE.match(line)),
        None,
    )
    if key_index is not None:
        bullets = [
            line.strip()
            for line in lines[key_index + 1 :]
            if line.strip().startswith("- ")
        ]
        if not bullets:
            errors.append('"### Key changes" must contain at least one bullet')
        for bullet in bullets:
            if not KEY_CHANGE_RE.match(bullet):
                warnings.append(
                    'key-change bullet does not follow "- **Area** \u2014 description": '
                    + bullet[:80]
                )


def check_type_of_change(
    text: str, expected: list[str], errors: list[str]
) -> None:
    if not text:
        return

    options = parse_options(text)
    actual = [label for _, label in options]

    for problem in describe_option_differences(expected, actual):
        errors.append(f'"Type of Change" {problem}')

    if not any(checked for checked, _ in options):
        errors.append('"Type of Change" must have at least one option checked')


def check_checklist(text: str, expected: list[str], errors: list[str]) -> None:
    if not text:
        return

    options = parse_options(text)
    actual = [label for _, label in options]

    for problem in describe_option_differences(expected, actual):
        errors.append(f'"Checklist" {problem}')

    # The documentation item is genuinely optional: a pull request that changes no
    # documentation is expected to leave it unchecked. Every other item is mandatory.
    for checked, label in options:
        if not checked and "documentation" not in label.lower():
            errors.append(f'"Checklist" item must be checked: "{label}"')


def main() -> int:
    parser = argparse.ArgumentParser(
        description="Validate a pull request title and body against the repository template."
    )
    parser.add_argument(
        "--body-file",
        required=True,
        help='Path to the pull request body, or "-" to read it from standard input',
    )
    parser.add_argument("--title", help="Pull request title to validate")
    parser.add_argument(
        "--template",
        default=str(DEFAULT_TEMPLATE),
        help="Path to the pull request template (default: .github/PULL_REQUEST_TEMPLATE.md)",
    )
    args = parser.parse_args()

    template_path = Path(args.template)
    if not template_path.is_file():
        print(f"PR body check: FAILED\ntemplate not found: {template_path}", file=sys.stderr)
        return 1

    if args.body_file == "-":
        body = sys.stdin.read()
    else:
        body_path = Path(args.body_file)
        if not body_path.is_file():
            print(f"PR body check: FAILED\nbody file not found: {body_path}", file=sys.stderr)
            return 1
        body = body_path.read_text(encoding="utf-8")

    errors: list[str] = []
    warnings: list[str] = []

    if args.title is not None:
        check_title(args.title, errors, warnings)

    check_body(
        body,
        template_path.read_text(encoding="utf-8"),
        errors,
        warnings,
    )

    for warning in warnings:
        print(f"WARNING: {warning}")

    if errors:
        for error in errors:
            print(f"ERROR: {error}")
        print(f"PR check: FAILED ({len(errors)} error(s), {len(warnings)} warning(s))")
        return 1

    print(f"PR check: OK ({len(warnings)} warning(s)) — matches the repository template")
    return 0


if __name__ == "__main__":
    sys.exit(main())
