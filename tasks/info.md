# tasks/

Staging area for drafts: ideas, options being weighed, analyses.

The backlog lives in the private repository
[`endurancetrio/endurancetrio-planning`](https://github.com/endurancetrio/endurancetrio-planning),
tracked as GitHub Issues on its Project board.

A draft is never a second copy of an issue:

- A draft exists only until its planning issue exists.
- Once the issue is created, delete the draft. Nothing is archived and no brief is kept.
- The planning issue is the only specification. Change the issue, not a file.

Drafts may start from `.github/ISSUE_TEMPLATE/backlog-item.md`, so the text moves into the issue
with little editing.

Handoff: *"Implement planning issue #N"*. The agent reads the issue itself, for example
`gh issue view N -R endurancetrio/endurancetrio-planning --json title,body`.

Content in this folder is gitignored — not committed. Nothing else under `tasks/` is part of this
workflow.
