---
name: determine-version
description: Determine the correct next version for the current state of the EnduranceTrio Community codebase using SemVer (https://semver.org/). Verifies that the last community tag matches the last published GitHub release, analyses commits and changes since that tag, proposes a version with rationale, and—after authorization—applies it with set-version.sh (local tag, or tag pushed to origin).
---

# Determine the next version

Use this skill when the user wants to know what version the current codebase should carry next,
or wants to prepare a release version. It follows https://semver.org/ strictly and never guesses
silently: every proposal comes with the evidence and the reasoning behind it.

The skill never commits, tags or pushes until the user explicitly authorizes it through the
authorization request in step 6. It also never stages anything: `set-version.sh` is the only
mechanism used to change version numbers.

## Hard rules

- **Published release is canonical.** The latest GitHub release on
  `endurancetrio/endurancetrio-community` defines the last released version. If the local/remote
  `community-v*` tags or the current POM version disagree with it, stop and report the
  discrepancy before proposing anything.
- **SemVer only.** Versions are `MAJOR.MINOR.PATCH` (release, no `-SNAPSHOT`, no build
  metadata) unless the user explicitly overrides. `0.x.y` is still treated numerically.
- **Evidence first.** The bump level (major/minor/patch) is derived from the commits and file
  changes since the last tag, and every proposal lists the specific commits/files that justify
  it. The user can override the level.
- **No mutation without authorization.** Proposing, dry-running and reporting are read-only.
  `set-version.sh` runs only after the explicit authorization in step 6, with the mode the user
  chose.
- **Clean tree required to apply.** If `git status --porcelain --untracked-files=no` is non-empty,
  the skill stops before invoking `set-version.sh` (the script would fail anyway).
- **Never push unless asked.** The push mode is an explicit option in the authorization request;
  the default is local tag only.
- **Origin is the push target** (per user choice). Use `--remote origin` when pushing. The
  script's own default is origin, but pass it explicitly so the intent is unambiguous.
- Report only commands actually run; never invent a check result.

## Remotes and naming conventions

- Release/publish repository: `endurancetrio/endurancetrio-community` (remote `community`).
- Source development repository: `EnduranceCode/endurancetrio-community` (remote `origin`).
- Release tags are prefixed `community-v` (e.g. `community-v0.3.8`). Tracker tags
  (`tracker-v*`) are out of scope and must be ignored.
- The POM version lives at the root `pom.xml` (`<version>` directly under `<project>`, not the
  Spring Boot parent version).

## Procedure

### 1. Verify tooling

```shell
git rev-parse --is-inside-work-tree
git remote -v
gh auth status
command -v set-version.sh || test -x ./set-version.sh
```

Stop if not inside a work tree, if `gh` is not authenticated, or if `set-version.sh` is missing.
Record which remotes exist (`origin`, `community`); if `community` is missing, note it and rely
on GitHub for the published side.

### 2. Find the last released version

Gather, in parallel where possible:

```shell
# Latest local community tag (by version)
git tag -l 'community-v*' | sort -V | tail -n 1

# Latest origin community tag
git ls-remote --tags origin 'refs/tags/community-v*' | grep -v '\^{}' | sed 's#.*/##' | sort -V | tail -n 1

# Latest community-remote community tag (if remote exists)
git ls-remote --tags community 'refs/tags/community-v*' 2>/dev/null | grep -v '\^{}' | sed 's#.*/##' | sort -V | tail -n 1

# Latest published GitHub release
gh release list -R endurancetrio/endurancetrio-community --limit 50
```

From the release list, take the entry whose tag starts with `community-v` with the highest
version (the list is not guaranteed to be version-sorted). Record its tag name as
`PUBLISHED_TAG` and its version as `PUBLISHED_VERSION`.

Let `LAST_TAG` be the maximum version among the tags found on local/origin/community. Let
`LAST_VERSION` be `LAST_TAG` with the `community-v` prefix stripped.

### 3. Alignment check (stop on mismatch)

Resolve the commit SHA for `LAST_TAG` on each side and compare:

```shell
git rev-parse "refs/tags/${LAST_TAG}^{commit}"                 # local
git ls-remote origin "refs/tags/${LAST_TAG}^{}" | awk '{print $1}' # origin (peeled)
git ls-remote community "refs/tags/${LAST_TAG}^{}" 2>/dev/null | awk '{print $1}'
```

For an annotated tag, peel it (`^{commit}`) before comparing. Also compare `LAST_TAG` against
`PUBLISHED_TAG`:

- `LAST_TAG` must equal `PUBLISHED_TAG`.
- The local/origin/community SHAs for `LAST_TAG` must all agree (when a side lacks the tag,
  report it as missing rather than a mismatch).
- The current POM version must equal `LAST_VERSION`:

  ```shell
  grep -m1 -E '^\s*<version>[^<]+</version>\s*$' pom.xml
  ```

  (This matches the project version, which is the first `<version>` after `<artifactId>`; verify
  by reading the surrounding lines.)

If any of these disagree, **stop**. Report a table of: side, tag found, commit SHA, POM version,
published release tag. Ask the user how to reconcile before proceeding. Do not propose a version
while the alignment is broken.

If everything agrees, record `BASE_TAG = LAST_TAG` and `BASE_VERSION = LAST_VERSION`. These are
the SemVer baseline.

### 4. Analyse the changes since the baseline

```shell
# Commit count and list
git rev-list --count "${BASE_TAG}..HEAD"
git log "${BASE_TAG}..HEAD" --format='%h %s'

# File changes
git diff --name-status "${BASE_TAG}..HEAD"
git diff --stat "${BASE_TAG}..HEAD"
```

Also capture the working-tree state:

```shell
git status --short --branch
git status --porcelain --untracked-files=no
```

Classify the changes for SemVer. Read the commit subjects and the changed paths; for ambiguous
cases read the actual diff (`git show <sha>` or `git diff BASE_TAG..HEAD -- <path>`). Look in
particular for:

- **Major (breaking) indicators**
  - Removed or renamed public API: REST endpoints under `.../api/`, controller methods, DTO
    fields/records, service method signatures, Thymeleaf model attributes consumed by templates.
  - Incompatible database changes: dropped/renamed columns or tables, changed column types,
    removed constraints, non-additive Flyway migrations.
  - Removed/renamed configuration keys, changed authentication/authorization behaviour.
  - Commit subjects containing `BREAKING`, `!`, "remove", "drop", "rename", "incompatible".
- **Minor (additive, backward-compatible) indicators**
  - New endpoints, pages, features, DTOs, fields (additive), new tables/columns, new
    migrations that only add.
  - New behaviours, new templates, new configuration keys.
  - Commit subjects containing "Add", "Implement", "Introduce", "Support".
- **Patch (fixes / internal) indicators**
  - Bug fixes, error-handling, performance, refactors with no behaviour change, dependency
    bumps, documentation, tests, styling/formatting, i18n text changes.
  - Commit subjects containing "Fix", "Refactor", "Update", "Bump", "Upgrade".

When a change set mixes levels, the **highest** level wins (SemVer: any breaking change makes it
major; otherwise any additive feature makes it minor; otherwise patch).

While the project is on `0.x.y`, note that SemVer allows breaking changes in minor releases, but
this skill still recommends a major bump for genuinely breaking changes and explains the choice;
the user may override.

### 5. Propose a version

Compute the candidate from `BASE_VERSION` (`X.Y.Z`):

- Major: `(X+1).0.0`
- Minor: `X.(Y+1).0`
- Patch: `X.Y.(Z+1)`

Present the proposal to the user with:

- `BASE_VERSION` (the last released version) and `BASE_TAG`.
- The commit count and the list of commits since the baseline (or a condensed, grouped list if
  long).
- The changed-files summary.
- The chosen bump level and the **specific evidence** (commits/paths) that justify it, plus any
  evidence that pointed the other way and why it was overruled.
- The suggested version.
- The working-tree status (clean / dirty; if dirty, list the tracked changes).

Then ask the user to confirm or override:

> Proposed version: `<suggested>`. Accept, or reply with a different `MAJOR.MINOR.PATCH`?

Only a valid `MAJOR.MINOR.PATCH` (optionally the user's own) proceeds. Record the final choice
as `NEW_VERSION`. If the user supplies a version that is not greater than `BASE_VERSION` under
SemVer ordering, warn and ask again.

### 6. Authorization to apply

Before asking, run a dry run of the local-tag mode so the user sees exactly what will change:

```shell
./set-version.sh "${NEW_VERSION}" --commit --tag --dry-run
```

Then present the authorization request. It must contain:

- `NEW_VERSION` and the tag that will be created (`community-v${NEW_VERSION}`).
- The mode options:
  1. **Set version + commit + tag (local only)** — `./set-version.sh NEW_VERSION --commit --tag`
  2. **Set version + commit + tag + push to origin** — `./set-version.sh NEW_VERSION --commit --tag --push --remote origin`
  3. **Cancel** — do nothing.
- The fact that `--commit` stages the version files (`git add -u`) and commits with message
  `Set version to ${NEW_VERSION}`.
- The clean-tree requirement and the current tree status.
- The dry-run output from above.

Ask for an explicit green light. Only `approved`, `proceed` or `yes` (possibly followed by the
chosen mode) counts. An ambiguous reply means no: leave the tree untouched, report the state,
and stop. If the user names a mode, use it; if they approve without naming a mode, default to
**local only**.

### 7. Apply (only after authorization)

Pre-flight:

```shell
if [ -n "$(git status --porcelain --untracked-files=no)" ]; then
    echo "Working tree has uncommitted tracked changes; aborting."; exit 1
fi
```

Run the chosen command and capture its output:

- Local only:
  ```shell
  ./set-version.sh "${NEW_VERSION}" --commit --tag
  ```
- Push:
  ```shell
  ./set-version.sh "${NEW_VERSION}" --commit --tag --push --remote origin
  ```

`set-version.sh` performs its own safety checks, fetches tags, and (in push mode) verifies
branch ancestry. Let it run; do not duplicate its steps. If it exits non-zero, report the exact
error and the step it failed on (`LAST_STEP`), and stop. Do not retry with modified flags
unless the user asks.

### 8. Verify and report

After a successful run:

```shell
git describe --tags --exact-match 2>/dev/null || git describe --tags
git rev-parse "refs/tags/community-v${NEW_VERSION}"^{commit}
git log -1 --format='%H%n%s'
git status --short --branch
```

If push mode was used, also confirm the remote has the tag:

```shell
git ls-remote origin "refs/tags/community-v${NEW_VERSION}"
```

Report:

- `NEW_VERSION` and the tag created.
- The commit hash and message.
- Whether the tag was pushed (and to which remote) or remains local.
- The files changed by the version bump (from the script output).
- Any follow-up hints the script printed (unpushed commit, local-only tag).
- State explicitly that no other changes were made.

## Deliverables

- A verified alignment between the last community tag, the origin/community remotes and the
  last published GitHub release (or a clear discrepancy report).
- A SemVer-based version proposal with evidence and rationale, confirmed by the user.
- On authorization, one version-bump commit (and annotated tag, optionally pushed to origin)
  created solely by `set-version.sh`, with a verification report.
- Nothing else: no staging, no extra commits, no pushes beyond the authorized mode.