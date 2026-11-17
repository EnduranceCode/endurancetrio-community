#!/usr/bin/env python3
"""Publish an approved backlog-item draft to its planning issue and move the issue to Backlog.

The draft's filename must be ``draft-<N>-<slug>.md``; ``<N>`` is the planning issue number. The
draft is validated with the draft-backlog-item checker, which is imported so that the frontmatter
parser is shared.

Without ``--apply`` the script is a dry run: every read is performed, the plan and the body diff
are printed, and nothing is written. ``--apply`` re-reads the issue and board item, aborts if they
changed since the dry run, backs up the original issue, performs the writes in this order (issue
title/body, Issue type, Effort, Status last), then verifies the result. The draft is deleted only
when ``--delete-draft`` is given and every verification passes.

Usage:
    publish-backlog-draft.py --draft tasks/draft-11-events-directory-search.md
    publish-backlog-draft.py --draft tasks/draft-11-events-directory-search.md \\
        --issue-type Feature --effort Medium
    publish-backlog-draft.py --draft tasks/draft-11-events-directory-search.md \\
        --issue-type Feature --effort Medium --apply --delete-draft
    publish-backlog-draft.py --self-test

Exit codes: 0 success (or dry run complete), 1 blocked or invalid input, 2 writes applied but
verification failed (see the restore command printed).
"""

from __future__ import annotations

import argparse
import difflib
import importlib.util
import json
import re
import subprocess
import sys
from pathlib import Path
from types import ModuleType

REPO_ROOT = Path(__file__).resolve().parents[4]
CHECKER_PATH = (
    REPO_ROOT / ".agents" / "skills" / "draft-backlog-item" / "scripts" / "check-backlog-draft.py"
)
TEMPLATE_PATH = REPO_ROOT / ".github" / "ISSUE_TEMPLATE" / "backlog-item.md"
GRAPHQL_QUERY_PATH = Path(__file__).resolve().parent / "project-item.graphql"
SCRATCH_ROOT = Path("/tmp/opencode")

OWNER = "endurancetrio"
PLANNING_REPO = "endurancetrio/endurancetrio-planning"
PROJECT_NUMBER = "1"

DRAFT_RE = re.compile(r"^draft-(\d+)-[a-z0-9][a-z0-9-]*\.md$")

ISSUE_QUERY = """
query($owner: String!, $name: String!, $number: Int!) {
  organization(login: $owner) {
    issueFields(first: 50) {
      nodes { ... on IssueFieldSingleSelect { id name options { id name } } }
    }
  }
  repository(owner: $owner, name: $name) {
    issueTypes(first: 20) { nodes { id name } }
    issue(number: $number) {
      id title body state issueType { name }
      issueFieldValues(first: 20) {
        nodes { ... on IssueFieldSingleSelectValue { name field { ... on IssueFieldSingleSelect { name } } } }
      }
    }
  }
}
"""

UPDATE_ISSUE_TYPE_MUTATION = """
mutation($issueId: ID!, $issueTypeId: ID!) {
  updateIssueIssueType(input: {issueId: $issueId, issueTypeId: $issueTypeId}) {
    issue { number }
  }
}
"""

SET_ISSUE_FIELD_VALUE_MUTATION = """
mutation($issueId: ID!, $fieldId: ID!, $optionId: ID!) {
  setIssueFieldValue(input: {issueId: $issueId, issueFields: [{fieldId: $fieldId, singleSelectOptionId: $optionId}]}) {
    issue { number }
  }
}
"""


class PublishError(Exception):
    """A condition that stops the publication before or during the writes."""


def load_checker() -> ModuleType:
    spec = importlib.util.spec_from_file_location("check_backlog_draft", CHECKER_PATH)
    if spec is None or spec.loader is None:
        raise PublishError(f"cannot load the draft checker at {CHECKER_PATH}")
    module = importlib.util.module_from_spec(spec)
    sys.modules[spec.name] = module
    spec.loader.exec_module(module)
    return module


def run(args: list[str], stdin: str | None = None) -> str:
    result = subprocess.run(args, input=stdin, capture_output=True, text=True, check=False)
    if result.returncode != 0:
        detail = result.stderr.strip() or result.stdout.strip()
        raise PublishError(f"'{' '.join(args[:3])} ...' failed: {detail}")
    return result.stdout


def graphql(query: str, variables: dict[str, object]) -> dict[str, object]:
    payload = json.dumps({"query": query, "variables": variables})
    response = json.loads(run(["gh", "api", "graphql", "--input", "-"], stdin=payload))
    if response.get("errors"):
        messages = "; ".join(error.get("message", "") for error in response["errors"])
        raise PublishError(f"GraphQL error: {messages}")
    return response["data"]


def normalize_body(text: str) -> str:
    lines = [line.rstrip() for line in text.replace("\r\n", "\n").split("\n")]
    return "\n".join(lines).strip("\n") + "\n"


def check_auth() -> None:
    result = subprocess.run(["gh", "auth", "status"], capture_output=True, text=True, check=False)
    output = result.stdout + result.stderr
    if result.returncode != 0:
        raise PublishError("gh is not authenticated; run: gh auth login")
    match = re.search(r"Token scopes:\s*(.+)", output)
    if match is None:
        raise PublishError(
            "cannot read the token scopes; the 'project' scope is required: "
            "gh auth refresh -s project"
        )
    scopes = re.findall(r"'([^']+)'", match.group(1))
    if "project" not in scopes:
        raise PublishError("the gh token lacks the 'project' scope; run: gh auth refresh -s project")


def read_draft(path: Path, checker: ModuleType) -> tuple[int, str, str, list[str]]:
    if not path.is_file():
        raise PublishError(f"draft does not exist: {path}")
    if not checker.is_under_tasks(path):
        raise PublishError(f"draft must live under tasks/ (got {path})")
    match = DRAFT_RE.fullmatch(path.name)
    if match is None:
        raise PublishError(f"draft filename must match draft-<N>-<slug>.md (got {path.name!r})")

    text = path.read_text(encoding="utf-8")
    errors, warnings = checker.validate_draft(
        text, TEMPLATE_PATH.read_text(encoding="utf-8"), path
    )
    if errors:
        raise PublishError("draft fails validation: " + "; ".join(errors))

    frontmatter, body, _ = checker.parse_frontmatter(text)
    return int(match.group(1)), frontmatter["title"], normalize_body(body), warnings


def fetch_board_item(number: int) -> dict[str, object]:
    query = GRAPHQL_QUERY_PATH.read_text(encoding="utf-8")
    cursor: str | None = None
    matches: list[dict[str, object]] = []
    while True:
        items = graphql(query, {"after": cursor})["organization"]["projectV2"]["items"]
        for node in items["nodes"]:
            if (node.get("content") or {}).get("number") == number:
                matches.append(node)
        if not items["pageInfo"]["hasNextPage"]:
            break
        cursor = items["pageInfo"]["endCursor"]
    if len(matches) != 1:
        raise PublishError(f"expected one board item for #{number}, found {len(matches)}")
    return matches[0]


def fetch_state(
    number: int,
) -> tuple[dict[str, object], list[dict[str, str]], dict[str, object], dict[str, dict[str, object]]]:
    data = graphql(ISSUE_QUERY, {"owner": OWNER, "name": PLANNING_REPO.split("/")[1], "number": number})
    repository = data["repository"]
    issue = repository["issue"]
    if issue is None:
        raise PublishError(f"issue #{number} does not exist in {PLANNING_REPO}")
    issue_fields = {
        field["name"]: field
        for field in data["organization"]["issueFields"]["nodes"]  # type: ignore[index]
        if field.get("name")
    }
    return issue, repository["issueTypes"]["nodes"], fetch_board_item(number), issue_fields


def item_field(item: dict[str, object], name: str) -> str:
    for value in item["fieldValues"]["nodes"]:
        field = value.get("field") or {}
        if field.get("name") == name:
            return value.get("name") or ""
    return ""


def issue_field_value(issue: dict[str, object], name: str) -> str:
    for value in issue["issueFieldValues"]["nodes"]:  # type: ignore[index]
        field = value.get("field") or {}
        if field.get("name") == name:
            return value.get("name") or ""
    return ""


def fetch_project_fields() -> tuple[str, dict[str, dict[str, object]]]:
    project = json.loads(
        run(["gh", "project", "view", PROJECT_NUMBER, "--owner", OWNER, "--format", "json"])
    )
    fields = json.loads(
        run(["gh", "project", "field-list", PROJECT_NUMBER, "--owner", OWNER, "--format", "json"])
    )["fields"]
    return project["id"], {field["name"]: field for field in fields}


def build_plan(
    args: argparse.Namespace,
    number: int,
    title: str,
    body: str,
    issue: dict[str, object],
    issue_types: list[dict[str, str]],
    item: dict[str, object],
    project_id: str,
    fields: dict[str, dict[str, object]],
    issue_fields: dict[str, dict[str, object]],
) -> dict[str, object]:
    blockers: list[str] = []
    warnings: list[str] = []

    if issue["state"] != "OPEN":
        blockers.append(f"issue #{number} is {issue['state']}, not OPEN")

    current_body = normalize_body(issue["body"] or "")
    content_changed = issue["title"] != title or current_body != body
    status = item_field(item, "Status")
    already_published = status == "Backlog" and not content_changed
    if not already_published and status != "Inbox":
        blockers.append(f"board status is {status!r}; only Inbox items can be published")

    issue_type = (issue.get("issueType") or {}).get("name") or ""
    issue_type_id: str | None = None
    type_ids = {entry["name"]: entry["id"] for entry in issue_types}
    if not issue_type:
        if not args.issue_type:
            blockers.append(
                "Issue type is missing; choose one with --issue-type "
                f"({', '.join(type_ids)})"
            )
        elif args.issue_type not in type_ids:
            blockers.append(f"unknown Issue type {args.issue_type!r}")
        else:
            issue_type = args.issue_type
            issue_type_id = type_ids[args.issue_type]
    elif args.issue_type and args.issue_type != issue_type:
        warnings.append(f"Issue type is already {issue_type!r}; --issue-type ignored")

    effort = issue_field_value(issue, "Effort")
    effort_field = issue_fields.get("Effort")
    effort_options = {
        option["name"]: option["id"]
        for option in (effort_field or {}).get("options", [])  # type: ignore[union-attr]
    }
    effort_option_id: str | None = None
    if not effort:
        if not effort_options:
            blockers.append(
                "the organization's Effort issue field has no options; add High, Medium and Low first"
            )
        elif not args.effort:
            blockers.append(
                "Effort is missing; choose one with --effort (" + ", ".join(effort_options) + ")"
            )
        elif args.effort not in effort_options:
            blockers.append(f"unknown Effort {args.effort!r}")
        else:
            effort = args.effort
            effort_option_id = effort_options[args.effort]
    elif args.effort and args.effort != effort:
        warnings.append(f"Effort is already {effort!r}; --effort ignored")

    status_field = fields.get("Status")
    backlog_option_id = next(
        (
            option["id"]
            for option in (status_field or {}).get("options", [])  # type: ignore[union-attr]
            if option["name"] == "Backlog"
        ),
        None,
    )
    if status_field is None or backlog_option_id is None:
        blockers.append("the Status field has no Backlog option on the board")

    if blockers:
        raise PublishError("publication is blocked:\n  - " + "\n  - ".join(blockers))

    return {
        "number": number,
        "title": title,
        "body": body,
        "content_changed": content_changed,
        "already_published": already_published,
        "issue_id": issue["id"],
        "issue_type": issue_type,
        "issue_type_id": issue_type_id,
        "item_id": item["id"],
        "project_id": project_id,
        "status": status,
        "status_field_id": status_field["id"],  # type: ignore[index]
        "backlog_option_id": backlog_option_id,
        "effort": effort,
        "effort_field_id": (effort_field or {}).get("id"),
        "effort_option_id": effort_option_id,
        "warnings": warnings,
        "snapshot": {
            "title": issue["title"],
            "body": current_body,
            "raw_body": issue["body"] or "",
            "status": status,
            "effort": issue_field_value(issue, "Effort"),
        },
    }


def print_preview(plan: dict[str, object], draft: Path, scratch: Path) -> None:
    snapshot = plan["snapshot"]  # type: ignore[index]
    print(f"Draft:        {draft}")
    print(f"Issue:        #{plan['number']} https://github.com/{PLANNING_REPO}/issues/{plan['number']}")
    print(f"Title:        {'changed' if snapshot['title'] != plan['title'] else 'unchanged'}")  # type: ignore[index]
    if plan["content_changed"]:
        diff = list(
            difflib.unified_diff(
                str(snapshot["body"]).splitlines(),  # type: ignore[index]
                str(plan["body"]).splitlines(),
                fromfile="issue (current)",
                tofile="draft (new)",
                lineterm="",
            )
        )
        (scratch / "body.diff").write_text("\n".join(diff) + "\n", encoding="utf-8")
        added = sum(1 for line in diff if line.startswith("+") and not line.startswith("+++"))
        removed = sum(1 for line in diff if line.startswith("-") and not line.startswith("---"))
        print(f"Body:         changed (+{added} / -{removed} lines); full diff in {scratch / 'body.diff'}")
    else:
        print("Body:         unchanged")
    print(f"Issue type:   {plan['issue_type'] or 'none'}"
          + (" (to be set)" if plan["issue_type_id"] else ""))
    print(f"Effort:       {plan['effort']}" + (" (to be set)" if plan["effort_option_id"] else ""))
    print(f"Status:       {plan['status']} -> Backlog"
          if plan["status"] != "Backlog" else "Status:       already Backlog")
    for warning in plan["warnings"]:  # type: ignore[union-attr]
        print(f"WARNING:      {warning}")


def apply_plan(plan: dict[str, object], scratch: Path) -> None:
    number = int(plan["number"])  # type: ignore[arg-type]
    issue, _, item, _ = fetch_state(number)
    snapshot = plan["snapshot"]  # type: ignore[index]
    if (
        issue["title"] != snapshot["title"]
        or normalize_body(issue["body"] or "") != snapshot["body"]
        or item_field(item, "Status") != snapshot["status"]
        or issue_field_value(issue, "Effort") != snapshot["effort"]
    ):
        raise PublishError("the issue or board item changed since the dry run; rerun the dry run")

    (scratch / "original.json").write_text(
        json.dumps({"title": snapshot["title"]}, ensure_ascii=False, indent=2) + "\n",
        encoding="utf-8",
    )
    (scratch / "original-body.md").write_text(snapshot["raw_body"], encoding="utf-8")
    body_file = scratch / "body.md"
    body_file.write_text(str(plan["body"]), encoding="utf-8")

    if plan["content_changed"]:
        run([
            "gh", "issue", "edit", str(number), "-R", PLANNING_REPO,
            "--title", str(plan["title"]), "--body-file", str(body_file),
        ])
    if plan["issue_type_id"]:
        graphql(
            UPDATE_ISSUE_TYPE_MUTATION,
            {"issueId": plan["issue_id"], "issueTypeId": plan["issue_type_id"]},
        )
    if plan["effort_option_id"]:
        graphql(
            SET_ISSUE_FIELD_VALUE_MUTATION,
            {
                "issueId": plan["issue_id"],
                "fieldId": plan["effort_field_id"],
                "optionId": plan["effort_option_id"],
            },
        )
    if plan["status"] != "Backlog":
        run([
            "gh", "project", "item-edit", "--id", str(plan["item_id"]),
            "--project-id", str(plan["project_id"]),
            "--field-id", str(plan["status_field_id"]),
            "--single-select-option-id", str(plan["backlog_option_id"]),
        ])


def verify(plan: dict[str, object]) -> list[tuple[str, bool]]:
    number = int(plan["number"])  # type: ignore[arg-type]
    issue, _, item, _ = fetch_state(number)
    return [
        ("title matches the draft", issue["title"] == plan["title"]),
        ("body matches the draft", normalize_body(issue["body"] or "") == plan["body"]),
        ("Issue type is set", (issue.get("issueType") or {}).get("name") == plan["issue_type"]),
        ("Effort is set", issue_field_value(issue, "Effort") == plan["effort"]),
        ("Status is Backlog", item_field(item, "Status") == "Backlog"),
    ]


def print_restore(plan: dict[str, object], scratch: Path) -> None:
    print(
        "\nRestore the original issue text if needed:\n"
        f"  gh issue edit {plan['number']} -R {PLANNING_REPO} "
        f"--title {json.dumps(str(plan['snapshot']['title']))} "  # type: ignore[index]
        f"--body-file {scratch / 'original-body.md'}",
        file=sys.stderr,
    )


def run_self_test(checker: ModuleType) -> int:
    failures = 0

    def check(label: str, ok: bool) -> None:
        nonlocal failures
        if ok:
            print(f"PASS  {label}")
        else:
            failures += 1
            print(f"FAIL  {label}")

    check("body normalisation strips CRLF and trailing spaces",
          normalize_body("a  \r\nb\r\n\r\n") == "a\nb\n")
    frontmatter, _, _ = checker.parse_frontmatter("---\ntitle: 'Don''t stop'\n---\nbody\n")
    check("title with an escaped quote is unquoted", frontmatter["title"] == "Don't stop")
    frontmatter, _, _ = checker.parse_frontmatter('---\ntitle: "Quoted: colon"\n---\nbody\n')
    check("double-quoted title keeps its colon", frontmatter["title"] == "Quoted: colon")
    check("filename pattern accepts draft-<N>-<slug>.md",
          DRAFT_RE.fullmatch("draft-11-events-directory-search.md") is not None)
    check("filename pattern rejects other names", DRAFT_RE.fullmatch("notes.md") is None)

    print(f"\nSelf-test {'failed' if failures else 'passed'} ({failures} failure(s)).")
    return 1 if failures else 0


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__, formatter_class=argparse.RawDescriptionHelpFormatter)
    parser.add_argument("--draft", help="path to the draft under tasks/")
    parser.add_argument("--issue-type", help="Issue type to set when it is missing")
    parser.add_argument("--effort", help="Effort to set when it is missing")
    parser.add_argument("--apply", action="store_true", help="perform the writes (after approval)")
    parser.add_argument("--delete-draft", action="store_true", help="delete the draft after verification")
    parser.add_argument("--self-test", action="store_true", help="run the bundled self-test")
    args = parser.parse_args()

    checker = load_checker()
    if args.self_test:
        return run_self_test(checker)
    if not args.draft:
        parser.error("--draft is required unless --self-test is given")
    if args.delete_draft and not args.apply:
        parser.error("--delete-draft requires --apply")

    try:
        draft = Path(args.draft).resolve()
        number, title, body, warnings = read_draft(draft, checker)
        for warning in warnings:
            print("WARNING: draft " + warning)

        check_auth()
        scratch = SCRATCH_ROOT / f"publish-{number}"
        scratch.mkdir(parents=True, exist_ok=True)

        issue, issue_types, item, issue_fields = fetch_state(number)
        project_id, fields = fetch_project_fields()
        plan = build_plan(
            args, number, title, body, issue, issue_types, item, project_id, fields, issue_fields
        )
        print_preview(plan, draft, scratch)
        for warning in plan["warnings"]:  # type: ignore[union-attr]
            print("WARNING: " + str(warning))

        if not args.apply:
            print("\nDry run: nothing was written. Re-run with --apply after the green light.")
            return 0

        if plan["already_published"]:
            print("\nNothing to write: the issue already matches the draft and is in Backlog.")
        else:
            try:
                apply_plan(plan, scratch)
            except PublishError as error:
                print("ERROR: " + str(error), file=sys.stderr)
                print_restore(plan, scratch)
                return 2

        checks = verify(plan)
        for label, ok in checks:
            print(f"{'PASS' if ok else 'FAIL'}  {label}")
        if not all(ok for _, ok in checks):
            print("ERROR: verification failed; the draft was kept.", file=sys.stderr)
            print_restore(plan, scratch)
            return 2

        if args.delete_draft:
            draft.unlink()
            print(f"Deleted draft {draft}")
        print(f"\nPublished #{number} to Backlog: https://github.com/{PLANNING_REPO}/issues/{number}")
        return 0
    except PublishError as error:
        print("ERROR: " + str(error), file=sys.stderr)
        return 1


if __name__ == "__main__":
    sys.exit(main())
