# EnduranceTrio Community — Agent Guide

## Quick start

```shell
cp endurancetrio-app/src/main/resources/template-secrets.yaml \
   endurancetrio-app/src/main/resources/application-secrets.yaml
./launch-app.sh   # builds with -Dfrontend.build.script=build:dev, runs with local profile
```

IntelliJ: `.run/EnduranceTrioApplication.run.xml` (Temurin 21, local profile, pre-launch frontend build).

## Build & test

- `./mvnw clean install` — full build (all 3 modules, automatically generates frontend assets)
- `./mvnw test` — all tests; `./mvnw test -pl endurancetrio-business` — single module
- Tests use H2 in PostgreSQL-compatibility mode (H2-specific Flyway migrations). No external DB.
- `./mvnw verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar -Dsonar.projectKey=endurancecode_endurancetrio-community` — manual SonarQube Cloud analysis

## Architecture

Three Maven modules: `app` (Spring Boot, controllers, security) → `business` (services, DTOs) → `data` (JPA entities, Flyway migrations). Only `app` produces an executable JAR.

Entrypoint: `com.endurancetrio.app.EnduranceTrioApplication` (port 8080, Thymeleaf + REST API).

## Key conventions

- Controller pattern: `interface ThingAPI` → `class ThingRestController implements ThingAPI`
- Web controllers: `@EnduranceTrioWebController` for Thymeleaf views
- API controllers: `@EnduranceTrioRestController` for REST endpoints
- Exception handlers are scoped by annotation: `EnduranceTrioExceptionHandlerAuth` and `EnduranceTrioExceptionHandlerAPI` handle `@EnduranceTrioRestController`; `EnduranceTrioExceptionHandlerWeb` handles `@EnduranceTrioWebController`
- Service pattern: `interface ThingService` → `class ThingServiceMain` (not `Impl`)
- Method order in services: CRUD (CREATE → READ → UPDATE → DELETE → UTILITY), then alphabetical
- Entity base classes: `BaseEntity<T>` (id, equals, hashCode), `AuditableEntity` (version, createdAt, updatedAt)
- Flyway: `V{major}.{minor}.{patch}.{order}__description.sql` in `endurancetrio-data/src/main/resources/db/migration/{ddl,dml}/{h2,postgres}/`
- DB naming: snake_case, plural tables, constraint prefixes (`pk_`, `fk_`, `uk_`, `chk_`, `idx_`, `seq_`)
- Config files (`*.yaml`, `*.properties`): keys ordered alphabetically (root-level and nested). See [`docs/development.md`](docs/development.md#configuration-file-key-ordering)

## Tracker integration (dual-repo)

Tracker domain code lives in [`endurancetrio-tracker`](https://github.com/EnduranceCode/endurancetrio-tracker) and is incorporated via `git merge` (see [`docs/tracker-integration.md`](docs/tracker-integration.md)). The `tracker` remote is pre-configured. Issues found in tracker code go upstream; stage them as markdown in `issues/` using the tracker's issue template format.

## Frontend

- Source: `endurancetrio-app/src/main/resources/webpack/` (JS, SCSS, ES modules with Webpack)
- CSS framework: Bulma. Import via `bulma/sass/utilities` wrapper at `scss/utilities/endurance-bulma-utilities.scss`
- Icon system: SVGs → CSS `mask-image` data URI classes (`.et-{name}`). Add icons in `icons.config.json`, then `npm run generate:icons` (runs automatically via `prebuild:*` hooks)
- `WEBPACK_OUTPUT_DIR` env var overrides output path (watch mode writes to `target/classes/static/`)
- Formatting: `npm run format` (ESLint + Prettier)

## Authentication (API)

Dual-header scheme on `/api/**`:
- `ET-Owner: <account-name>`
- `Authorization: Bearer <api-key>`

API keys are bcrypt hashes (cost 12). First account via `FIRST_OWNER` + `FIRST_HASH` env vars.

## Configuration

| File                                | Purpose                                                  |
|-------------------------------------|----------------------------------------------------------|
| `application.yaml`                  | defaults (datasource, JPA, Flyway, port 8080)            |
| `application-{local,dev,prod}.yaml` | profile-specific overrides                               |
| `application-secrets.yaml`          | credentials (gitignored, from `template-secrets.yaml`)   |
| `application-test.yaml`             | H2 in-memory DB for tests                                |
| `sonar-project.properties`          | SonarQube Cloud project key, host, CPD exclusions        |
| `pom.xml`                           | `<sonar.organization>endurancecode</sonar.organization>` |

Profiles: `local` (localhost PG), `dev`/`prod` (env-var PG), `test` (H2). Activate via `-Dspring.profiles.active=local` or `-Dspring-boot.run.profiles=local`.

## Docker

- Image: `ghcr.io/endurancetrio/endurancetrio-community`, multi-stage `eclipse-temurin:21-jre-alpine`
- Entrypoint drops privileges via PUID/PGID env vars. G1GC JVM tuning.
- CI: `.github/workflows/publish-image.yml` — triggers on release publish or `workflow_dispatch`

## Commit messages

Subject: ≤50 chars, capitalized, imperative mood, no period. Body optional (72-char wrap, explains what/why vs. how). Follows [cbea.ms/git-commit](https://cbea.ms/git-commit/).
