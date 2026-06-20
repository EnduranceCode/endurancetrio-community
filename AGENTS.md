# AGENTS.md

## Project

Spring Boot 4 / Java 21 multi-module Maven project. REST API for IoT device telemetry tracking with
API key auth.

## Modules (dependency order: data -> business -> app)

| Module                   | Role                                                |
|--------------------------|-----------------------------------------------------|
| `endurancetrio-data`     | JPA entities, repositories, Flyway migrations       |
| `endurancetrio-business` | Services, DTOs, mappers, validators                 |
| `endurancetrio-app`      | Spring Boot app, REST controllers, security, config |

Entrypoint: `com.endurancetrio.app.TrackerApplication` (scans `com.endurancetrio`, `@EntityScan` +
`@EnableJpaRepositories` on `com.endurancetrio.data`).

## Commands

| Action                 | Command                                                                                                              |
|------------------------|----------------------------------------------------------------------------------------------------------------------|
| Build all              | `./mvnw clean install`                                                                                               |
| Build skip tests       | `./mvnw clean package -DskipTests`                                                                                   |
| Run (local)            | `./launch-app.sh` (compiles + runs with `local` profile, port 8081)                                                   |
| Run single module      | `./mvnw -pl endurancetrio-app -am spring-boot:run -Dspring-boot.run.profiles=local`                                  |
| Run all tests          | `./mvnw verify`                                                                                                      |
| Run single test class  | `./mvnw -pl endurancetrio-business test -Dtest=RouteServiceMainTest`                                                 |
| Version bump           | `mvn versions:set -DnewVersion=X.Y.Z` (then `versions:commit` or `versions:revert`)                                  |
| Generate API key       | `openssl rand -base64 32`                                                                                            |
| API key bcrypt hash    | `python3 -c "import bcrypt; print(bcrypt.hashpw('KEY'.encode('utf-8'), bcrypt.gensalt(rounds=12)).decode('utf-8'))"` |
| Docker build & push CI | Triggered by GitHub release or `workflow_dispatch` — see `.github/workflows/publish-image.yml`                       |

## Local setup

1. `cp endurancetrio-app/src/main/resources/template-secrets.yaml endurancetrio-app/src/main/resources/application-secrets.yaml` and fill DB credentials
2. PostgreSQL must be running with schema `endurancetrio_tracker`
3. Activate initial account via `FIRST_OWNER` + `FIRST_HASH` env vars on first start

## Testing

- Tests use H2 in PostgreSQL compatibility mode (`jdbc:h2:mem:endurancetrio_tracker;MODE=PostgreSQL;DATABASE_TO_LOWER=TRUE`)
- Test config: `endurancetrio-app/src/test/resources/application-test.yaml`
- Business tests use `@ExtendWith(MockitoExtension.class)` (pure Mockito, no Spring context)
- Flyway migrations for H2 are in `db/migration/ddl/h2` and `db/migration/dml/h2`
- Tests do NOT require PostgreSQL — H2 is fully self-contained
- Full testing conventions documented in [docs/development.md#testing](docs/development.md#testing)

## API

- All internal endpoints under `/api/tracker/v1/`; proxy strips `/api` prefix for external access
- Dual-header auth: `Authorization: Bearer <key>` + `ET-Owner: <owner>`
- Swagger UI at `/swagger-ui/index.html` (port 8081). Two groups: `Tracker Domain (LOCAL)` vs `Tracker Domain (PROD)`
- Actuator at `/actuator/health` (port 8081)

## Flyway

- Version format: `Vyyyymmdd.nnn__description.sql`
- DDL and DML scripts are duplicated per database (`h2/` vs `postgres/`)
- Schema: `endurancetrio_tracker`
- Baseline version: `0`
- New migrations must be added to both `h2` and `postgres` directories

## Tag naming

- Tags follow the pattern `tracker-vX.Y.Z` where X is major, Y is minor, Z is patch
- Tags must be **annotated** (use `git tag -a`)
- Push with `git push origin tracker-vX.Y.Z`
- Example: `tracker-v1.0.0`

## Code conventions

- **No `Impl` suffix** — service implementations use `Main` (e.g., `RouteServiceMain`). Descriptive suffixes preferred.
- **Controller pattern**: `DomainAPI` interface (OpenAPI annotations) + `DomainRestController` impl (with `@EnduranceTrioRestController`)
- **Method ordering**: Controllers follow Swagger display order. Services grouped by entity then CRUD (CREATE, READ, UPDATE, DELETE, UTILITY). Private methods after all public, alphabetically.
- **Flyway naming**: `V<yyyymmdd>.<seq>__description.sql`
- **DB naming**: snake_case, plural table names, constraints prefixed: `pk_`, `fk_`, `uk_`, `chk_`, `idx_`
- **Javadoc HTML**: must use valid HTML — no self-closing tags (e.g., `<a href="..." />`), no stray closing tags. Verify with `mvn javadoc:aggregate`

## Profiles

| Profile | Config file              | DB                                                         |
|---------|--------------------------|------------------------------------------------------------|
| `local` | `application-local.yaml` | PostgreSQL (localhost:5432)                                |
| `dev`   | `application-dev.yaml`   | PostgreSQL (env vars `DB_URL`, `DB_USERNAME`, `DB_SECRET`) |
| `prod`  | `application-prod.yaml`  | PostgreSQL (env vars)                                      |

## Docker

- Multi-stage build (`eclipse-temurin:21-jre-alpine`)
- Runs as non-root user `endurancetrio` with PUID/PGID host mapping via entrypoint
- Image published to `ghcr.io/endurancetrio/endurancetrio-tracker`

## Commit messages

Subject: Use a "[TRACKER]" prefix, ≤50 chars, capitalized, imperative mood, no period. Body
optional (72-char wrap, explains what/why vs. how).
Follows [cbea.ms/git-commit](https://cbea.ms/git-commit/).
