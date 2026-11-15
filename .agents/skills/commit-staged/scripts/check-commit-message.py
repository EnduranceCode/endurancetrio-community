#!/usr/bin/env python3
"""Validate a commit message against AGENTS.md and the repository's house style.

Rules (from AGENTS.md and the recent commit history):

  * subject 50 characters or fewer, capitalized, imperative mood, no Conventional
    Commits prefix, no trailing period, no surrounding whitespace;
  * a blank line after the subject when a body is present;
  * body paragraphs wrapped at 72 characters, prose only (no bullets, headings or
    trailers such as Signed-off-by / Co-authored-by / Change-Id);
  * no double blank lines, no trailing blank lines;
  * the body is optional (trivial commits carry only a subject).

Usage:
    check-commit-message.py --file PATH [--history N]

PATH may be "-" to read from standard input. Exit code 0 means the message complies;
1 means at least one error must be fixed. Warnings do not fail. The last few subjects
from the repository history are printed for reference.
"""

from __future__ import annotations

import argparse
import re
import subprocess
import sys
from pathlib import Path

REPO_ROOT = Path(__file__).resolve().parents[4]

# Common past-tense or gerund openers that are not imperative mood.
NON_IMPERATIVE_OPENERS = {
    "added", "adds", "adding", "updated", "updates", "updating", "fixed", "fixes",
    "fixing", "removed", "removes", "removing", "changed", "changes", "changing",
    "implemented", "implements", "implementing", "improved", "improves", "improving",
    "created", "creates", "creating", "refactored", "refactors", "refactoring",
    "moved", "moves", "moving", "renamed", "renames", "renaming", "documented",
    "documents", "documenting", "replaced", "replaces", "replacing", "corrected",
    "corrects", "correcting", "allowed", "allows", "allowing", "used", "uses",
    "using", "merged", "merges", "merging", "deleted", "deletes", "deleting",
    "enabled", "enables", "enabling", "disabled", "disables", "disabling",
    "extracted", "extracts", "extracting", "introduced", "introduces", "introducing",
    "added-up", "was", "were", "this", "the",
}

CONVENTIONAL_PREFIX_RE = re.compile(r"^[a-z]+(\([^)]*\))?!?:\s")
TRAILER_RE = re.compile(r"^[A-Za-z-]+:\s.+$")
BULLET_RE = re.compile(r"^\s*[-*+]\s")
HEADING_RE = re.compile(r"^#{1,6}\s")


def recent_subjects(count: int) -> list[str]:
    try:
        result = subprocess.run(
            ["git", "-C", str(REPO_ROOT), "log", f"-n{count}", "--format=%s"],
            check=True, capture_output=True, text=True,
        )
    except (subprocess.CalledProcessError, FileNotFoundError):
        return []
    return [line for line in result.stdout.splitlines() if line.strip()]


def check_subject(subject: str, errors: list[str], warnings: list[str]) -> None:
    if not subject:
        errors.append("subject is empty")
        return

    if subject != subject.strip():
        errors.append("subject has leading or trailing whitespace")
    if subject.endswith("."):
        errors.append("subject must not end with a period")
    if len(subject) > 50:
        errors.append(f"subject is {len(subject)} characters; keep it at 50 or fewer")
    elif len(subject) >= 45:
        warnings.append(f"subject is {len(subject)} characters; 50 is the limit")
    if not re.match(r"^[A-Z0-9]", subject):
        errors.append("subject must start with a capital letter")
    if CONVENTIONAL_PREFIX_RE.match(subject):
        errors.append(
            "subject must not use a Conventional Commits prefix (for example 'feat:' or 'fix:')"
        )

    first_word = subject.split()[0].lower().rstrip(":,") if subject.split() else ""
    if first_word in NON_IMPERATIVE_OPENERS:
        errors.append(
            f"subject must use imperative mood (opener '{first_word}' is past tense or gerund)"
        )


def check_body(
    body: str,
    errors: list[str],
    warnings: list[str],
    has_body: bool,
) -> None:
    if not has_body:
        return

    lines = body.splitlines()

    # No double blank lines.
    for index in range(len(lines) - 1):
        if lines[index].strip() == "" and lines[index + 1].strip() == "":
            errors.append(f"line {index + 1}: double blank line in body")
            break

    # No trailing blank lines.
    while lines and lines[-1].strip() == "":
        errors.append("body has a trailing blank line")
        lines.pop()

    for index, raw in enumerate(lines, start=1):
        if len(raw) > 72:
            if " " not in raw.strip():
                warnings.append(
                    f"line {index}: line is {len(raw)} characters (unbreakable token)"
                )
            else:
                errors.append(
                    f"line {index}: line is {len(raw)} characters; wrap at 72"
                )

        stripped = raw.strip()
        if BULLET_RE.match(raw):
            errors.append(f"line {index}: body uses a bullet; use prose paragraphs")
        if HEADING_RE.match(raw):
            errors.append(f"line {index}: body uses a heading; use prose paragraphs")
        if TRAILER_RE.match(raw) and stripped.split(":", 1)[0].lower() in {
            "signed-off-by", "co-authored-by", "change-id", "reviewed-by",
        }:
            errors.append(f"line {index}: body contains a trailer; remove it")

    # Paragraph openers: every non-blank line that begins a paragraph should be capitalized.
    in_paragraph = False
    for index, raw in enumerate(lines, start=1):
        stripped = raw.strip()
        if stripped == "":
            in_paragraph = False
            continue
        if not in_paragraph and not re.match(r"^[A-Z0-9\"'(]", stripped):
            warnings.append(
                f"line {index}: paragraph starts with a lowercase letter"
            )
        in_paragraph = True


def main() -> int:
    parser = argparse.ArgumentParser(
        description="Validate a commit message against AGENTS.md and the house style."
    )
    parser.add_argument(
        "--file",
        required=True,
        help='Path to the commit message, or "-" to read it from standard input',
    )
    parser.add_argument(
        "--history",
        type=int,
        default=10,
        help="How many recent subjects to print for reference (default: 10)",
    )
    args = parser.parse_args()

    if args.file == "-":
        raw = sys.stdin.read()
    else:
        path = Path(args.file)
        if not path.is_file():
            print(f"commit message check: FAILED\nfile not found: {path}", file=sys.stderr)
            return 1
        raw = path.read_text(encoding="utf-8")

    # Preserve the subject/body split. A message with a single line has no body.
    split = raw.split("\n", 1)
    subject = split[0].rstrip("\r")
    rest = split[1] if len(split) > 1 else ""

    # Strip a single leading blank line (some editors leave one).
    if rest.startswith("\n"):
        rest = rest[1:]

    # The body is everything after the first blank line.
    if "\n\n" in rest:
        # subject already separated; rest starts with the blank line
        body_candidate = rest.lstrip("\n")
    else:
        # No blank line: treat the whole remainder as a malformed body.
        body_candidate = rest

    # Detect the separator explicitly.
    lines = raw.splitlines()
    if len(lines) >= 2 and lines[1].strip() == "":
        body = "\n".join(lines[2:])
        has_body = any(line.strip() for line in lines[2:])
    else:
        body = ""
        has_body = False
        if len(lines) >= 2 and any(line.strip() for line in lines[1:]):
            # There is content after the subject but no blank separator.
            has_body = True
            body = "\n".join(lines[1:])

    errors: list[str] = []
    warnings: list[str] = []

    if len(lines) >= 2 and lines[1].strip() != "" and has_body:
        errors.append(
            "missing blank line between subject and body (line 2 must be empty)"
        )

    check_subject(subject, errors, warnings)

    if has_body:
        check_body(body, errors, warnings, has_body)

    # File-level checks.
    if raw.endswith("\n\n"):
        errors.append("message has a trailing blank line")
    if not raw.endswith("\n") and raw.strip():
        warnings.append("message does not end with a newline")

    subjects = recent_subjects(args.history)
    if subjects:
        print("Recent subjects (house style reference):")
        for item in subjects:
            print(f"  - {item}")
        print()

    for warning in warnings:
        print(f"WARNING: {warning}")
    if errors:
        for error in errors:
            print(f"ERROR: {error}")
        print(f"Commit message check: FAILED ({len(errors)} error(s), {len(warnings)} warning(s))")
        return 1

    print(f"Commit message check: OK ({len(warnings)} warning(s))")
    return 0


if __name__ == "__main__":
    sys.exit(main())
