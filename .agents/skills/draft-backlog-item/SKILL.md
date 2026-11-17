---
name: draft-backlog-item
description: Draft a backlog-item planning issue into tasks/ from an Inbox issue on the endurancetrio/endurancetrio-planning project board (https://github.com/orgs/endurancetrio/projects/1), using a senior-developer refinement interview. Never invents detail and never writes to GitHub. Validates the draft with the bundled checker.
---

# Draft a backlog item

Use this skill when the user gives an issue from
[`endurancetrio/endurancetrio-planning`](https://github.com/endurancetrio/endurancetrio-planning)
that is in the **Inbox** column of the
[EnduranceTrio Planning board](https://github.com/orgs/endurancetrio/projects/1), and asks for a
draft in `tasks/` following `.github/ISSUE_TEMPLATE/backlog-item.md`.

The issue may be a **Quick capture** (`capture.md`) with only a one- or two-line `## Idea`, or a
partly triaged item. Either way the goal is a draft that can be handed to a junior developer or
another agent **with no further input**.

## Hard rules

- **Validate the input and verify Inbox before drafting.** Fetch the issue by the provided number
  first. Then confirm it is open and on the board with Status **Inbox**. Nothing is interviewed or
  drafted until both checks pass.
- **Never invent detail.** Every statement in the draft must come from one of three sources:
    1. the provided issue (title, body, labels or other explicit content);
    2. the codebase, verified by search (paths, classes, methods, existing behaviour);
    3. an explicit answer from the product owner (PO) during the refinement interview.
       Anything else is an open question.
- **When in doubt, ask.** An assumption guessed into a draft becomes a bug in the implementation.
  A question is cheaper than a wrong specification.
- **Act as a senior developer in a refinement meeting.** Interview first; do not silently turn a
  vague capture into a polished specification.
- **Local output only.** Write exactly one file under `tasks/`. Scratch files (the refinement log)
  go only under `/tmp/opencode/`. Never run `gh issue edit`, never change a project field, never
  move the issue out of Inbox, never create a commit, never edit any other file.
- **A deferred or unanswered point is an open question, not a default.** If the PO answers "I don't
  know", defers, or stops answering, record it as an open question. Do not write the draft while
  any question is open; report the blockers instead.
- **Never assume an empty section means "not applicable".** Delete `### Out of scope` or
  `### Dependencies` only after the PO confirms there is genuinely nothing to put in them.
- **The draft is temporary.** Once its content is moved into the issue body, delete the draft (see
  `tasks/info.md`). State this in the final report.

## Input

The skill accepts exactly these two forms:

- `#N`: the issue number `N` in `endurancetrio/endurancetrio-planning`.
- `https://github.com/endurancetrio/endurancetrio-planning/issues/N`: extract `N` from the URL.

Any other form (another repository, a pull request, a bare number without `#`, a project URL) is
rejected: ask the user to restate the reference. The source repository and board are fixed:
issue repository `endurancetrio/endurancetrio-planning`, organisation `endurancetrio`, project #1.

## Procedure

### 1. Validate the input and fetch the issue

```shell
gh issue view N -R endurancetrio/endurancetrio-planning \
  --json number,title,body,labels,state,url
```

Stop and ask if the issue is not `OPEN`.

### 2. Verify the issue is in Inbox

The Projects v2 `items` connection has no filter argument, so page through the board and match the
issue number. The query is shipped in `scripts/project-items.graphql`. Run the first page with:

```shell
jq -n --argjson after null --rawfile q .agents/skills/draft-backlog-item/scripts/project-items.graphql \
  '{query:$q, variables:{after:$after}}' | gh api graphql --input -
```

For each following page, pass the previous `endCursor` as `AFTER` and use:

```shell
jq -n --arg after "$AFTER" --rawfile q .agents/skills/draft-backlog-item/scripts/project-items.graphql \
  '{query:$q, variables:{after:(if $after == "" then null else $after end)}}' | gh api graphql --input -
```

Continue until `hasNextPage` is false. For each node, read the value whose `field.name` is `Status`.
The issue must appear once, with `content.number` equal to `N` and Status `Inbox`. Otherwise stop
and
ask how to proceed.

### 3. Read the source material

- `.github/ISSUE_TEMPLATE/backlog-item.md`: the single source of truth for the draft's structure.
  The planning repository copy differs only in the frontmatter `about` line.
- `tasks/info.md`: the task-draft rules and the handoff convention.
- `AGENTS.md`: the planning and backlog conventions, and the code conventions the implementation
  must follow.
- When the issue reads like a Quick capture, the planning repository's `capture.md`:
  `gh api repos/endurancetrio/endurancetrio-planning/contents/.github/ISSUE_TEMPLATE/capture.md`.

### 4. Open the refinement log

Create `/tmp/opencode/refine-N.md`, or resume it if it exists. Record there:

- the **confirmed facts**: everything the issue states, listed back to the PO;
- every **answer** received, with the question it answers;
- every **open question** and every **blocker**.

The log is what lets an interrupted refinement resume without asking again. Delete it after the
draft has been validated.

### 5. Run the refinement interview

Ask the PO, in batches of at most five questions. Each question states the options, and where there
are alternatives, a recommendation. Use the `question` tool for decisions.

Interview checklist (ask only what the issue and the codebase do not already answer):

- **Problem and goal**: what must be built or fixed, why, and what outcome defines success.
- **Users and impact**: who is affected and which pages, endpoints or flows change.
- **Scope**: exactly what is in, and which files, classes, endpoints or tables may change.
- **Out of scope**: what must not change or is deferred. Confirm explicitly if nothing is excluded.
- **Approach**: the chosen design. Present alternatives with a recommendation; record the rejected
  alternatives and why.
- **Functional rules**: defaults, mandatory versus optional parameters, validation, normalisation,
  ordering, empty and no-match states, error handling, edge cases, backwards compatibility.
- **UI and accessibility**: controls, labels, responsive behaviour, metadata and SEO, and both
  locales (`messages.properties` and `messages_pt.properties`).
- **Data**: whether a migration is needed, existing columns and constraints, the data source of each
  dropdown or filter, and the cost of querying it.
- **Non-functional concerns**: performance, caching, security, analytics, observability.
- **Testing**: unit, Mockito, Spring slice or integration; which classes and scenarios.
- **Dependencies**: other issues, pull requests, migrations, prerequisites. Confirm explicitly if
  there are none.
- **Acceptance criteria**: one testable statement per bullet. The PO confirms or amends the list.

### 6. Confirm the complete scope

Before writing, read the whole intended scope back to the PO in one message: in scope, out of scope,
rules, approach and acceptance criteria. Write the draft only after an explicit approval. Any change
the PO asks for restarts this step.

### 7. Verify codebase facts

Every path, class, method, endpoint, template, message key and migration version in the draft must
be found by search before it is written. Use `glob` for file names, `grep` for symbols and keys, and
`read` to confirm the content. Record only what exists. A code fact never becomes a product
decision:
if the issue is silent on the desired behaviour, ask the PO rather than copying the current
behaviour.

### 8. Write the draft

Create exactly one file, `tasks/draft-<N>-<slug>.md`, where `<N>` is the issue number and `<slug>`
is a
short kebab-case summary of the issue title, for example
`tasks/draft-11-events-directory-search.md`. If the file already exists, ask before overwriting it.

Copy the frontmatter from `.github/ISSUE_TEMPLATE/backlog-item.md`, and set `title` to the issue
title or to a PO-approved refined title. Keep the H2 and H3 structure and order from the template:

`## Description` with `### Context`, `### Scope`, `### Out of scope`, `### Approach`,
`### Implementation notes`, `### Testing`, `### Dependencies`, then `## Acceptance Criteria`.

Rules while writing:

- Replace every template comment and placeholder with real content. Remove the comments entirely.
- No `TODO`, `TBD`, `TBA`, `FIXME`, `???`, "to be decided" or template prompt text may remain.
- `### Context` lists verified paths in backticks, one per bullet, with what each one does today.
- `### Approach` states the chosen design and names the rejected alternatives with the reason.
- `### Implementation notes` records conventions, constraints and the files to create or modify.
- `### Testing` names the tests and their level (unit, Mockito, Spring slice).
- Each `## Acceptance Criteria` bullet is a testable `- [ ]` statement. The item is done when every
  box
  can be ticked.

A short excerpt of a finished draft, from `tasks/draft-events-directory-search.md`, shows the level
of detail expected:

```markdown
- `endurancetrio-app/src/main/java/com/endurancetrio/app/event/web/EventWebController.java`: the
  `getYearsWithEvents(...)` method serves the year list at `/{language:en|pt}/events`.

- [ ] A free-text search field filters events by title (accent- and case-insensitive partial match).
```

### 9. Validate

```shell
python3 .agents/skills/draft-backlog-item/scripts/check-backlog-draft.py \
  --file tasks/draft-<N>-<slug>.md
```

Fix every `ERROR` and review every `WARNING`. When the checker itself has been edited, also run
`python3 .agents/skills/draft-backlog-item/scripts/check-backlog-draft.py --self-test`, which must
pass before the checker is trusted.

### 10. Clean up and report

Once the draft validates, delete `/tmp/opencode/refine-N.md`. Then report:

- the draft path;
- the source issue number, URL and its verified Inbox status;
- a summary of the questions asked and the answers received;
- the checker result;
- an explicit statement that nothing was written to GitHub and no commit was created;
- a reminder that the draft is temporary and must be deleted once its content is moved into the
  issue body;
- a pointer that the next step is `publish-backlog-draft` once the draft is approved.

If the refinement is blocked, do not write the draft. Report the open questions and stop.

## Deliverables

- One validated draft at `tasks/draft-<N>-<slug>.md`, following
  `.github/ISSUE_TEMPLATE/backlog-item.md`.
- A refinement that left no open question, with each decision recorded in the draft.
- A clean checker run, or a clear list of remaining warnings with reasons.
- No changes to the planning repository, no issue edits, no status changes, no commits.
