---
name: create-release-notes
description: Create release notes for a new EnduranceTrio Community release under tasks/release-notes, following .github/RELEASE_NOTES_TEMPLATE.md and the established release-note pattern, and validate them. Creating the GitHub release or tag is out of scope.
---

# Create release notes

Use this skill when a new EnduranceTrio Community release is being prepared. The output is a
single Markdown file in `tasks/release-notes/` whose full contents are copied and pasted,
verbatim, into the GitHub release body on
[`endurancetrio/endurancetrio-community`](https://github.com/endurancetrio/endurancetrio-community).

The release itself is created manually. **Do not** create the GitHub release, create or push
a tag, bump the version, commit, or push anything. Those actions are out of scope.

`.github/RELEASE_NOTES_TEMPLATE.md` is the single source of truth for the section structure.
Read it every time; never draft from memory. The bundled checker reads the same file, so a
future template edit is picked up automatically.

## Hard rules

- Write the file at `tasks/release-notes/release-notes-vX.Y.Z.md`. The folder is gitignored;
  never commit the file.
- The file is the exact release body: no YAML frontmatter, no wrapping code fence, no
  instructions, no local paths and no HTML comments. Delete every template comment.
- The first line is exactly `# EnduranceTrio Community - Release vX.Y.Z` (space, hyphen,
  space, then `v` and the version digits).
- A summary paragraph follows the title, before the first section. Start with
  "This release …", "This patch release …" or, for the first public release, "This is the
  first public release …". Name the headline changes; do not list files.
- Use every top-level section, in this exact order:
  `Breaking Changes`, `Features and Enhancements`, `Bug Fixes`, `Performance & Optimizations`,
  `Refactoring & Technical Debt`, `Documentation`, `Dependency Updates`.
- `### API & User Facing Changes` and `### Core Logic & Business Rules` belong only under
  `Features and Enhancements`. `### Critical & Production Fixes` and `### General Fixes`
  belong only under `Bug Fixes`. Add a subsection only when it has content; never add other
  H3 headings.
- An empty top-level section contains `None.` on its own. The first public release may
  append a short explanation after an em dash (`None — this is the initial release.`). An
  empty subsection is omitted; never write `None.` inside a subsection. Never leave a
  section blank.
- Every entry is a bullet in the form `- **Area** — short description.` (em dash `—`).
  Keep one change per bullet, lead with the affected area, and end the sentence with a period.
  Mention migration versions, event IDs, environment variables and classes in backticks.
- `Breaking Changes` must be actionable: explain the impact and include the exact manual step
  (for example a `psql`/Flyway command) inside a fenced `shell` block indented under the bullet.
- Finish with the fixed deployment footer, preceded by a `---` line:

  ```markdown
  ---

  **Deployment**: the Docker image for this release is published as `ghcr.io/endurancetrio/endurancetrio-community:X.Y.Z`. Set `PRD_VERSION=X.Y.Z` in the deployment `.env`.
  ```

  An optional `**Acknowledgments**: …` paragraph may follow the deployment footer,
  separated from it by a blank line, but only when the user or release manager explicitly
  asks for it.
- Write in English. Describe shipped, user- or deployment-relevant changes only. Exclude
  `tasks/**`, local AI tooling/agents/skills, task briefs, secrets, and changes that were
  reverted within the same release. Group repeated historical-data work (for example all
  events of a season) instead of listing every row.
- Never invent a change, capability, migration or dependency update. Every entry must be
  supported by the commit/PR range being released.

## Canonical shape

```markdown
# EnduranceTrio Community - Release v0.4.0

This release adds a configurable **Featured Events** section to the homepage and supports
one-off relay competitors. It also keeps team race types out of the home results feed.

## Breaking Changes

None.

## Features and Enhancements

### API & User Facing Changes

- **Featured Events homepage section** — renders a featured insight-article card next to a list of featured events, configurable through `FEATURED_EVENT_IDS` and `FEATURED_EVENT_ARTICLE_ID`.
- **São Martinho do Porto Triathlon article** — new bilingual PT/EN insight article linked to the four event pages, with gallery images.

### Core Logic & Business Rules

- **One-off relay competitors** — migration `V20260830.001` adds `relay_entry` and `relay_entry_athlete` to record relay entries and their contributing athletes.
- **Nullable team-result rank** — migration `V20260829.001` allows relay teams without an official placement.

## Bug Fixes

### General Fixes

- **Home results feed** — the "Latest Results" section no longer lists team race types.

## Performance & Optimizations

None.

## Refactoring & Technical Debt

- **Team model** — `RelayEntry` extends `Team`, carrying gender category, age group and contributing athletes.

## Documentation

- **Database reference** — `DATABASE.md` updated with the two new migrations.
- **Environment template** — `.env-template` documents the featured-events variables.

## Dependency Updates

None.

---

**Deployment**: the Docker image for this release is published as `ghcr.io/endurancetrio/endurancetrio-community:0.4.0`. Set `PRD_VERSION=0.4.0` in the deployment `.env`.
```

## Procedure

1. **Determine the version.** Use the version given by the user or task, without its leading
   `v` (for example `0.4.0`). If none was given, read the current project version from the
   frontend package and confirm it matches the Maven project version; only propose that
   version when it is not already released. If the values disagree, or the version is
   ambiguous, stop and ask. Never guess a version.

   ```shell
   jq -r .version endurancetrio-app/src/main/resources/webpack/package.json
   ./mvnw -q help:evaluate -Dexpression=project.version -DforceStdout
   ```

2. **Locate the previous release.** Fetch tags and compare versions:

   ```shell
   git fetch --tags origin
   git tag --list 'community-v*' --sort=-version:refname | head -1
   printf '%s\n%s\n' "<latest-without-prefix>" "<new-version>" | sort -V | tail -1
   ```

   The new version must be greater than the latest `community-v*` tag. A release that is not
   greater is a repeat; stop and ask. For the first release, use the full history.

3. **Collect everything shipped since that tag.** Inspect commits, merged pull requests and
   the diff:

   ```shell
   git log --oneline --no-merges "community-v<latest>..HEAD"
   git log --oneline --merges "community-v<latest>..HEAD"
   git diff --stat "community-v<latest>..HEAD"
   git log -1 --format=%cI community-v<latest>
   gh pr list --repo EnduranceCode/endurancetrio-community \
     --state merged --limit 100 --json number,title,mergedAt
   ```

   Only include pull requests merged after the previous tag. Read the relevant pull request
   bodies when a commit message is not enough. Skip anything outside the release range.

4. **Classify each change.** Put it in the single best section:

   | Section | Use it for |
   | --- | --- |
   | `Breaking Changes` | Removed/renamed schema or API contracts, required extensions or manual migrations, removed configuration, incompatible behaviour |
   | `Features and Enhancements / API & User Facing Changes` | Pages, endpoints, UI, authentication, SEO, newsletter, user-visible behaviour |
   | `Features and Enhancements / Core Logic & Business Rules` | Domain model, migrations, historical data, business rules |
   | `Bug Fixes / Critical & Production Fixes` | Data corruption, security, production outages, deployment blockers |
   | `Bug Fixes / General Fixes` | Minor UI and behaviour corrections |
   | `Performance & Optimizations` | Caching, indexes, query tuning, bundle size, JVM tuning |
   | `Refactoring & Technical Debt` | Internal restructuring, tooling/CI/build changes without user-visible behaviour |
   | `Documentation` | `docs/`, `DATABASE.md`, `AGENTS.md`, Javadoc and guides |
   | `Dependency Updates` | Java, Spring Boot, PostgreSQL, npm packages, integrated tracker releases |

   If a change could fit two sections, choose the one a release reader cares about most and
   do not duplicate it.

5. **Write the file.** Create `tasks/release-notes/release-notes-vX.Y.Z.md` using the
   canonical shape. Match the level of detail of `release-notes-v0.3.5.md`: complete enough
   to understand the release, without turning into a changelog of every commit.

6. **Validate before reporting.** Run the checker with the exact version:

   ```shell
   python3 .agents/skills/create-release-notes/scripts/check-release-notes.py \
     --version 0.4.0 \
     --file tasks/release-notes/release-notes-v0.4.0.md
   ```

   Every `ERROR` must be fixed. Review every `WARNING`, especially bullets that do not follow
   `- **Area** — description`; fix them unless there is a reason to keep the wording.

## Deliverables

Report the file path, the release version, the previous tag used as the range start, the
sections that received content, and the checker result. Also state the values needed for the
manual GitHub release: title `vX.Y.Z`, tag `community-vX.Y.Z`. Do not create the release or
tag, and do not commit the file.
