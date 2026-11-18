---
name: publish-backlog-draft
description: Publish a validated backlog-item draft from tasks/ to its linked planning issue and move that issue from Inbox to Backlog on the EnduranceTrio Planning board. Dry-run by default; writes only after an explicit green light. Never commits or pushes.
---

# Publish a backlog draft

Use this skill when a backlog-item draft under `tasks/` has been refined and validated, and the
product owner wants its content moved into the linked planning issue and the issue moved to
**Backlog**.

The skill is the second half of the planning workflow:

1. `draft-backlog-item` creates `tasks/draft-<N>-<slug>.md` and validates it.
2. `publish-backlog-draft` moves that content into issue `#N` and moves the issue to **Backlog**.

All logic lives in `scripts/publish-backlog-draft.py`. Do not reimplement its steps by hand.

## Hard rules

- **Dry run first.** Run the script without `--apply`. It performs only reads, prints the plan and
  the body diff, and writes nothing.
- **Writes need an explicit green light.** Show the dry-run plan, then ask for approval. Only
  `approved`, `proceed` or `yes` counts. Any other reply means stop.
- **Never invent metadata.** Issue type and Effort come from the product owner's answers, passed as
  `--issue-type` and `--effort`. If the script reports a blocker, ask; do not guess.
- **The draft is the source of truth.** The issue title and body are replaced by the draft's
  frontmatter title and body. Nothing is merged or appended.
- **Sub-task breakdown must be analysed before publishing.** The dry run warns when
  `### Implementation notes` records neither a breakdown nor the reason none is feasible. Analyse
  whether the work can be split into small, independently committable steps that each leave the build
  green. If it can, write the breakdown into the draft; if it cannot, add an explicit one-sentence
  reason using the exact phrase
  `No independently committable split is feasible:`. Do not publish while the warning is open.
- **Never move an issue that is not in Inbox.** The only exception is a rerun after a completed
  publication, which the script reports as "nothing to write".
- **Delete the draft only with approval, and only after verification.** Pass `--delete-draft` only
  when the user approved deletion, and never when verification failed.
- **No commits, no pushes, no other file edits.** Scratch files live only in `/tmp/opencode/publish-<N>/`.
  The one exception is editing the draft under `tasks/` to add the sub-task breakdown or its
  infeasibility reason before the publish dry run is rerun.
- **One draft per invocation.**

## Input

Exactly one local draft path under `tasks/`, for example `tasks/draft-11-events-directory-search.md`.
A leading `@` (as in `@tasks/...`) is stripped. A bare issue number, a URL, or a path outside
`tasks/` is rejected: ask the user to restate the reference.

The target is fixed: issue `#N` in `endurancetrio/endurancetrio-planning`, and the
[EnduranceTrio Planning board](https://github.com/orgs/endurancetrio/projects/1), organisation
`endurancetrio`, project `1`.

## Procedure

### 1. Dry run

```shell
python3 .agents/skills/publish-backlog-draft/scripts/publish-backlog-draft.py \
  --draft tasks/draft-<N>-<slug>.md
```

The script:

1. validates the draft with the draft-backlog-item checker (errors stop; warnings are printed);
2. checks `gh` authentication and the `project` token scope;
3. reads the issue and requires it to be `OPEN`;
4. reads the board, following every page, and requires exactly one item for `#N` in **Inbox**;
5. resolves the Status, Effort (organization issue field) and Issue type options from GitHub;
6. stops with a list of blockers if Issue type or Effort is missing and cannot be set;
7. prints the plan: title change, body diff summary (full diff in `/tmp/opencode/publish-<N>/body.diff`),
   Issue type, Effort and the Status transition;
8. warns when `### Implementation notes` has neither an ordered, independently committable sub-task
   list with a green build at every step nor a `No independently committable split is feasible:`
   statement.

### 2. Resolve blockers

When the dry run reports a blocker, ask the product owner for the value and rerun with it:

- **Issue type missing:** ask which of the listed types to use, then pass `--issue-type <name>`.
- **Effort missing:** ask which option to use, then pass `--effort <name>`.
- **Effort field has no options:** the organization's Effort issue field is not configured. Report
  that High, Medium and Low must be added to it, then stop.
- **Status not Inbox:** stop and ask how to proceed.

### 3. Resolve the sub-task warning

When the dry run reports a missing breakdown, analyse the draft itself against the current code and
decide whether a step-by-step split is feasible and makes sense:

- **Feasible:** write an ordered list into the draft's `### Implementation notes`. Derive each step
  from the draft's Scope, Approach and file list, and make it a small, independently committable
  change that leaves the build green. Name any temporary compatibility shim and the later step that
  removes it. Do not change what the issue will deliver. Validate the edited draft with the
  `draft-backlog-item` checker, then rerun the publish dry run.
- **Not feasible or not sensible:** write one sentence into `### Implementation notes` starting with
  the exact phrase `No independently committable split is feasible:` and the reason (for example,
  a single atomic migration with no safe intermediate state). Validate and rerun the dry run.

Do not publish while the warning is open.

### 4. Ask for the green light

Present the dry-run plan and ask for approval, stating explicitly:

- the issue URL and the title and body changes;
- the Issue type and Effort values that will be set;
- the Status change `Inbox → Backlog`;
- whether the draft will be deleted afterwards.

### 5. Apply

After approval, run the same command with the chosen flags:

```shell
python3 .agents/skills/publish-backlog-draft/scripts/publish-backlog-draft.py \
  --draft tasks/draft-<N>-<slug>.md \
  --issue-type <Type> --effort <Effort> --apply [--delete-draft]
```

Omit `--issue-type` or `--effort` when the values were already set. `--delete-draft` is added only
when the user approved deleting the draft.

When applying, the script:

1. re-reads the issue and board item and aborts if they changed since the dry run;
2. backs up the original title and body to `/tmp/opencode/publish-<N>/original.json` and
   `original-body.md`;
3. updates the issue title and body, if they changed;
4. sets Issue type and Effort, if they were missing;
5. moves Status to **Backlog**, last, so the item only reaches Backlog once its content is in place;
6. verifies title, body, Issue type, Effort and Status by reading GitHub again.

### 6. Handle failures

- **Exit 1:** nothing was written, or the input was invalid. Report the message.
- **Exit 2:** a write or the verification failed. The script prints the restore command with the
  original title and body. Keep the draft, report the state, and ask how to proceed.
- **Rerun after success:** the script reports "nothing to write". This makes an interrupted run
  safe to repeat.

### 7. Report

Report:

- the issue number, URL and new status;
- the verification results;
- the writes performed, and the backup location;
- whether the draft was deleted;
- an explicit statement that no commit was created and nothing was pushed.

## Deliverables

- The sub-task breakdown was analysed and recorded in the draft before publication.
- The planning issue's title and body match the validated draft.
- Issue type and Effort are set.
- The issue is in **Backlog** on the board.
- The draft is deleted only when the user approved it and verification passed.
- No commits, no pushes, and no repository file edits.
