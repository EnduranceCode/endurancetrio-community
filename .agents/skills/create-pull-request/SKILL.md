---
name: create-pull-request
description: Create or update a GitHub pull request for EnduranceCode/endurancetrio-community that strictly follows .github/PULL_REQUEST_TEMPLATE.md and the house style of the repository's previous pull requests, validating the title and body before and after publishing.
---

# Create a pull request

Use this skill whenever a pull request must be opened or updated on GitHub for this
repository. The goal is that every pull request looks the same: the same sections, the
same checkbox list, the same title style and the same level of detail.

The template at `.github/PULL_REQUEST_TEMPLATE.md` is the **single source of truth**.
Read it every time; never draft a body from memory. The bundled checker reads the same
file, so a future template edit is picked up automatically.

## Hard rules

- The body uses **exactly** the template's H2 sections, in the template's order and with
  the template's wording: `Description`, `Type of Change`, `How Has This Been Tested?`,
  `Screenshots` (optional) and `Checklist`.
- Never add, rename, reorder or merge sections. In particular, do not use the old
  `### New Features` / `### Changes` subsections.
- Delete every HTML comment and every placeholder sentence from the template.
- `Screenshots` is included **only** for user-interface changes with real embedded
  images. When there is nothing to show, delete the whole section; never write `N/A`.
- `Type of Change` always lists all six options exactly as the template writes them, in
  order. Check every option that applies; at least one must be checked.
- `Checklist` always lists all four items exactly as the template writes them. The code
  style, build and commit-message items must be checked before the pull request is
  opened. The documentation item is checked only when documentation was updated.
- The description starts with a summary paragraph written in the present tense
  ("Adds…", "Fixes…", "Refactors…", "Integrates…"), followed by a `### Key changes`
  subsection with at least one `- **Area** — explanation` bullet (em dash `—`).
- The title is imperative, sentence case, without a Conventional Commits prefix and
  without a trailing period. Target 72 characters or fewer; 100 is the hard limit.
- The pull request targets `master` on `EnduranceCode/endurancetrio-community` (the
  `origin` remote). Never target the `org` remote, which is release-only.
- Never create a commit, push, merge or close a pull request unless the user asked for
  that action. This skill publishes the pull request; it does not change the code.
- Never claim a build or test result that was not actually observed in this session.

## Title

| Rule | Example |
| --- | --- |
| Imperative verb, capitalized | `Add athletes directory filter` |
| Describe the outcome, not the files | `Fix duplicate tab initialization on event pages` |
| No prefix, no period, no "WIP" | ~~`feat: add athletes page.`~~ |
| Target ≤72 characters | `Add race results for 1987 season events` |

Titles in this repository are not Conventional Commits. Tracker integrations use
`Integrate Tracker vX.Y.Z`.

## Body

Copy the template into a temporary file and fill it. This is the canonical shape, based
on the latest merged pull requests:

```markdown
## Description

Adds a featured events section to the homepage, highlights the São Martinho do Porto
Triathlon insight article, and cleans up the recent-results feed. Reference related
issues inline when they exist, for example `#42`.

### Key changes

- **Featured events section** — new `FEATURED_EVENT_IDS` configuration drives a homepage section that pairs an insight-article card with a list of featured events
- **Insight article** — new PostgreSQL DML migration inserts the bilingual PT/EN article plus its authors, event links and images; `DATABASE.md` updated
- **Recent results cleanup** — the "Latest Results" feed now excludes team race types
- **Tests** — `EventServiceMainTest` covers the new lookup; `HomeWebControllerTest` asserts the new model attributes

## Type of Change

- [ ] Bug fix
- [x] New feature
- [ ] Refactoring / code cleanup
- [ ] Build / tooling / CI
- [x] Documentation
- [ ] Translation / language

## How Has This Been Tested?

Full Maven build (`./mvnw clean install`) — BUILD SUCCESS. Java 21 (Temurin), Linux.

## Checklist

- [x] My code follows the project's code style and conventions (see `docs/development.md#code--naming-conventions` and `AGENTS.md#key-conventions`)
- [x] The build runs successfully (`./mvnw clean install`)
- [x] I have updated the documentation (`AGENTS.md`, `README.md`, `docs/`, `DATABASE.md`, etc.) if needed
- [x] The commit messages follow the project's convention (50-char subject, 72-char body wrap)
```

### Description

- First paragraph: what the pull request does and why, in the present tense. Mention
  breaking changes here if there are any (the template has no breaking-change checkbox).
- `### Key changes`: one bullet per meaningful area, ordered by importance. Start with a
  bold area name, an em dash, then the explanation. Mention database migrations, tests
  and documentation when they are part of the change. Do not list every file.
- Keep the bullet format `- **Area** — explanation`; the checker warns when it differs.

### Type of Change

Check every option that applies, not just one:

- **Bug fix** — corrects incorrect behaviour without adding a capability.
- **New feature** — new user-visible or API capability.
- **Refactoring / code cleanup** — no intended behaviour change.
- **Build / tooling / CI** — Maven, npm, Docker, scripts, workflows.
- **Documentation** — documentation-only changes or documentation added alongside code.
- **Translation / language** — message bundles, localized content.

### How Has This Been Tested?

State only what was actually run. The preferred one-liner is:

```text
Full Maven build (`./mvnw clean install`) — BUILD SUCCESS. Java 21 (Temurin), Linux.
```

Add short bullets for manual verification (profile, browser, endpoints) when relevant.
If the build was not run, say why instead of inventing a result.

### Screenshots

Include only for UI changes and only with real images. Delete the section otherwise.

## Procedure

1. **Confirm the preconditions.** The working tree must be clean and the current branch
   must contain commits relative to `origin/master`. If there are uncommitted changes or
   no commits, stop and ask the user; do not commit as part of this skill.

   ```shell
   git fetch origin master
   git status --short --branch
   git log --oneline origin/master..HEAD
   git diff --stat origin/master...HEAD
   ```

2. **Read the current template and inspect the change.** Read
   `.github/PULL_REQUEST_TEMPLATE.md`, then review the diff so the description and the
   type checkboxes reflect the real change.

3. **Verify the build.** Run `./mvnw clean install` unless a full build for the exact
   current `HEAD` was already run in this session. Record the real outcome for the
   `How Has This Been Tested?` section.

4. **Draft the body** in a temporary file outside the repository, for example
   `/tmp/opencode/pr-body-<branch>.md`, using the canonical shape above.

5. **Validate before publishing.** Pass the exact title and body file to the checker:

   ```shell
   python3 .agents/skills/create-pull-request/scripts/check-pr-body.py \
     --title "Add featured events to homepage" \
     --body-file /tmp/opencode/pr-body-feature-featured-events.md
   ```

   Every `ERROR` must be fixed. Review every `WARNING`; fix it unless there is a reason
   to keep the deviation. Do not publish while the checker reports an error.

6. **Push and create the pull request.** Push the branch only if it is missing on the
   remote, then create the pull request with the validated body file:

   ```shell
   git push -u origin HEAD   # only when the branch is not on the remote yet

   gh pr create \
     --repo EnduranceCode/endurancetrio-community \
     --base master \
     --head "$(git branch --show-current)" \
     --title "<validated title>" \
     --body-file /tmp/opencode/pr-body-<branch>.md
   ```

   Check for an existing open pull request for the branch first; if one exists, update
   it with `gh pr edit <number> --body-file …` instead of opening a duplicate:

   ```shell
   gh pr list --repo EnduranceCode/endurancetrio-community \
     --head "$(git branch --show-current)" --state open --json number,url,title
   ```

   Do not open a draft, add labels or request reviewers unless the user asks.

7. **Verify the published pull request.** Fetch the stored body back and validate it
   again, so the check covers exactly what GitHub received:

   ```shell
   gh pr view <number> --repo EnduranceCode/endurancetrio-community \
     --json title,body,baseRefName,headRefName,url \
     --jq '.url, .title, .baseRefName, .headRefName'
   gh pr view <number> --repo EnduranceCode/endurancetrio-community \
     --json body --jq .body > /tmp/opencode/pr-body-<branch>.md
   python3 .agents/skills/create-pull-request/scripts/check-pr-body.py \
     --title "<validated title>" \
     --body-file /tmp/opencode/pr-body-<branch>.md
   ```

   If the published body fails, fix it with `gh pr edit <number> --body-file …` and
   validate again.

## Deliverables

Report the pull request URL, title, the checked `Type of Change` options and the exact
testing line. Do not merge, close, approve or otherwise change the pull request unless
the user asks.
