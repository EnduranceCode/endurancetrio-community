# EnduranceTrio Community — Agent Guide

## Quick start

```shell
cp endurancetrio-app/src/main/resources/template-secrets.yaml endurancetrio-app/src/main/resources/application-secrets.yaml # edit to set DB username/password
./mvnw clean install                                                                                                        # full build
./launch-app.sh                                                                                                             # build + run with local profile
```

Or use the IntelliJ run config in `.run/EnduranceTrioApplication.run.xml` (local profile, Temurin 21).

## Build & test

```shell
./mvnw clean install                   # full build (all modules)
./mvnw test                            # run all tests
./mvnw test -pl endurancetrio-business # single module
```

Tests use H2 in PostgreSQL-compatibility mode with H2-specific Flyway migrations. No external DB
needed.

Flyway migrations run automatically on startup. Scripts live in
`endurancetrio-data/src/main/resources/db/migration/{ddl,dml}/{h2,postgres}/`.

## Architecture

Three Maven modules, one entry point:

- **`endurancetrio-app`** — runnable Spring Boot app (`EnduranceTrioApplication`), controllers (Thymeleaf web + REST API), security config, OpenAPI
- **`endurancetrio-business`** — service layer, DTOs, mappers, validators
- **`endurancetrio-data`** — JPA entities, repositories, Flyway migrations

Module dependency: `app → business → data`. Only `app` produces an executable JAR.

## Product direction

EnduranceTrio is a web platform and REST API for endurance sports data and resources, centered on
triathlon and related multisport disciplines with an initial focus on Portugal.

The main product goal is to build a structured and reliable dataset of Portuguese triathlon events,
results, athletes, and related content. Development starts with a strong historical foundation, but
the platform is intended to expand toward more recent competitions and increasingly timely race
data as the data model and ingestion workflows mature.

When making product, content, or UX changes, prefer language that reflects both sides of the
platform:

- historical preservation and structured sports data
- current and future competition coverage
- practical live-event tooling through the tracker domain

## Configuration

| File                                                 | Purpose                                              |
|------------------------------------------------------|------------------------------------------------------|
| `application.yaml`                                   | defaults (datasource, JPA, Flyway, server port 8080) |
| `application-{local,dev,prod}.yaml`                  | profile-specific overrides                           |
| `template-secrets.yaml` → `application-secrets.yaml` | DB credentials (gitignored)                          |
| `application-test.yaml`                              | H2 in-memory DB for tests                            |

## Spring profiles

| Profile | DB                   | Config file              |
|---------|----------------------|--------------------------|
| `local` | localhost PostgreSQL | `application-local.yaml` |
| `dev`   | env-var PostgreSQL   | `application-dev.yaml`   |
| `prod`  | env-var PostgreSQL   | `application-prod.yaml`  |
| `test`  | H2 in-memory         | `application-test.yaml`  |

Activate with `-Dspring.profiles.active=local` or `-Dspring-boot.run.profiles=local`.

## Tracker integration (dual-repo workflow)

The Tracker domain (IoT telemetry + route management) is developed in its own repository
[`endurancetrio-tracker`](https://github.com/EnduranceCode/endurancetrio-tracker) and
incorporated into this repository via `git merge`.

**Setup:** The `tracker` remote is already registered:

```shell
git remote add tracker https://github.com/EnduranceCode/endurancetrio-tracker.git
```

**To pull in new Tracker code,** follow the
[merge workflow in `docs/development.md`](./docs/development.md#tracker-domain-development).

**Tracker-only issues discovered in this repo:**
Issues that affect the Tracker domain code but should be fixed upstream in the
`endurancetrio-tracker` repository are staged as markdown files in the `issues/` folder
at the repo root, following the project's GitHub issue template format. When ready,
these are filed on the tracker repository's issue tracker.

## Authentication (API)

Dual-header scheme required on `/api/**`:

- `ET-Owner: <account-name>`
- `Authorization: Bearer <api-key>`

API keys are stored as bcrypt hashes (cost factor 12). First account can be initialized via
env vars `FIRST_OWNER` + `FIRST_HASH`.

## Key conventions

- Controller pattern: `interface XxxAPI` (OpenAPI annotations) → `class XxxRestController implements XxxAPI`
- Service pattern: `interface XxxService` → `class XxxServiceMain` (never `Impl`)
- Method ordering in services: CRUD (CREATE → READ → UPDATE → DELETE → UTILITY), then alphabetical within group
- Private methods: after all public methods, alphabetical
- Entity base classes: `BaseEntity<T>` provides `id`, `equals()`, `hashCode()`; `AuditableEntity` adds `version`, `createdAt`, `updatedAt`. Subclasses annotate with `@SequenceGenerator(name = "seq_endurancetrio_generator", ...)` at class level
- Flyway naming: `V{major}.{minor}.{patch}.{order}__description.sql`
- DB naming: snake_case, plural table names, constraint prefixes (`pk_`, `fk_`, `uk_`, `chk_`, `idx_`, `seq_`)

## Frontend

- Thymeleaf templates in `endurancetrio-app/src/main/resources/templates/`
- JS/SCSS source in `endurancetrio-app/src/main/resources/webpack/`
- Build: frontend assets are generated automatically during Maven builds into
  `endurancetrio-app/target/generated-resources/frontend/static/` and packaged under
  `BOOT-INF/classes/static/`; use `npm run build:dev` or `npm run build:prod` from the webpack
  dir for standalone one-shot builds
- Maven default: `frontend.build.script=build:prod`, so package builds produce minified assets
  without source maps and optimize copied raster images only
- IntelliJ: `.run/EnduranceTrioApplication.run.xml` invokes `.run/GenerateFrontendAssets.run.xml`
  before launch; run `.run/FrontendAssetsWatch.run.xml` in a separate tab for continuous frontend
  rebuilds — it sets `WEBPACK_OUTPUT_DIR` to write directly to `target/classes/static/`
- Shared `.run/` configs: `GenerateFrontendAssets` for one-time dev assets (with source maps),
  `FrontendAssetsWatch` for continuous frontend rebuilds
- CSS framework: Bulma
- Frontend color semantics and usage guidance live in `docs/color-system.md`
- Bulma utility customization is centralized in
  `endurancetrio-app/src/main/resources/webpack/src/scss/utilities/endurance-bulma-utilities.scss`
  and EnduranceTrio palette tokens live in
  `endurancetrio-app/src/main/resources/webpack/src/scss/utilities/endurancetrio-variables.scss`
- Project-maintained SCSS customizations for Bulma/third-party code use the `endurance-` prefix
  for file names, including Klaro, switch-control, and theme entrypoints

## Docker

- Image: `ghcr.io/endurancetrio/endurancetrio-community`
- Multi-stage build from `eclipse-temurin:21-jre-alpine`
- PUID/PGID env vars for host UID mapping
- Entrypoint: `/usr/local/bin/entrypoint.sh` (drops privileges, runs with G1GC)
- Compose template: `docker/deployment/docker-compose.yaml`

## CI / release

- Workflow `.github/workflows/publish-image.yml`
- Triggers: GitHub Releases (`published`) + manual `workflow_dispatch`
- Tags: semver on release, `sha-{sha}-{suffix}` on manual
- Requires repo secrets `GHCR_USERNAME` + `GHCR_SECRET` (write-packages scope in target org)

## Commit messages

Commit messages follow the rules described in
[How to Write a Git Commit Message](https://cbea.ms/git-commit/).

The subject line must be descriptive enough to stand on its own. A body is
optional — only add one when the commit's complexity cannot be adequately
captured in the subject line alone.

1. Limit the subject line to 50 characters
2. Capitalize the subject line
3. Do not end the subject line with a period
4. Use the imperative mood in the subject line
5. If a body is present, separate it from the subject with a blank line
6. If a body is present, wrap it at 72 characters
7. Use the body to explain what and why vs. how

## Pull requests

When creating a pull request, follow the checklist in
[`.github/PULL_REQUEST_TEMPLATE.md`](.github/PULL_REQUEST_TEMPLATE.md). The template covers type
of change, testing, documentation updates, and commit message conventions.
