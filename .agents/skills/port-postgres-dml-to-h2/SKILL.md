---
name: port-postgres-dml-to-h2
description: Create the H2 counterpart of a PostgreSQL Flyway DML migration script for events dated 1989 or earlier (for example process-event-NN-results), verify it against the test database, and register both scripts in DATABASE.md.
---

# Port a PostgreSQL DML migration to H2

Use this skill when a DML script is added or changed under
`../../../endurancetrio-data/src/main/resources/db/migration/dml/postgres` **and it targets events
dated 1989 or earlier**. H2 is the database used by the tests, so those scripts must have an H2 twin
that loads exactly the same data.

## Scope (read this first)

Porting to H2 is limited to **events dated 1989-12-31 or earlier, 1989 included**. Confirm the date
before doing anything else:

- `event.start_date` (or `event_reference`, which starts with `YYYYMMDD`) for an event-level script.
- `race.start_date` / `race.race_reference` when the script targets specific races.

Then:

- **Event dated 1989 or earlier** → create the H2 twin and register the script pair in `DATABASE.md`.
- **Event dated 1990 or later** → the script is **PostgreSQL-only**. Do not create an H2 twin; do not
  modify the H2 directory. Register only the PostgreSQL script in `DATABASE.md`.

Scripts that are intentionally PostgreSQL-only for other reasons (for example a production-only
insight article) follow the same rule: PostgreSQL script only.

If the date cannot be determined, stop and ask instead of guessing.

## Rules

- The PostgreSQL script is the **single source of truth**. The H2 file has the **same version and
  free description** as the PostgreSQL file and only changes the database suffix:
  `...__description-postgres.sql` becomes `...__description-h2.sql`.
- The target directory is the sibling `h2` directory of the source `postgres` directory.
- Port the data literally: do not renumber IDs, reorder rows, re-escape literals or "improve" data.
  Any data correction belongs to its own migration applied to both databases.
- **No asymmetries are allowed.** After the dialect equivalences below, the two files must contain
  exactly the same statements, in the same order. A missing, extra or edited statement in H2 is a bug
  to fix in H2 — never an accepted difference, never something to copy forward into a new port, and
  never a reason to change the PostgreSQL script. Fixing one pair also means checking every other
  pair with the parity checker (see step 4).
- **Never replicate a duplication.** If the PostgreSQL script repeats a statement block by mistake
  (copy/paste leftovers, typically idempotent `UPDATE`s that do nothing on the second run), keep a
  **single copy** in H2. The PostgreSQL script cannot be corrected once it has run in production, so
  the repetition stays there and the H2 side is the clean one. Register the case in the `EXCEPTIONS`
  map of `check-h2-parity.py` with a short reason; the checker only accepts an exception when the
  distinct statements still match exactly, so a registered exception can never hide missing or
  altered data. Never add an exception for a difference that changes data.
- Only these dialect differences are expected:

  | PostgreSQL                                | H2                                                           |
  |-------------------------------------------|--------------------------------------------------------------|
  | `SET search_path TO endurancetrio_hub;`   | `SET SCHEMA endurancetrio_hub;`                              |
  | `SELECT setval('seq_name', 42, FALSE);`   | `ALTER SEQUENCE endurancetrio_hub.seq_name RESTART WITH 42;` |

- Legacy H2 scripts (roughly up to `V20260626.001`) qualify every object name with the schema
  (`endurancetrio_hub.event`) instead of setting the search path. That is equivalent and may stay;
  when editing such a file, follow its existing style. New scripts use `SET SCHEMA`.
- `NOW()`, `NULL`, string literals with `''` escaping and `INSERT`/`UPDATE` syntax are identical.
- If the PostgreSQL script contains other PostgreSQL-only syntax (`::` casts, `ON CONFLICT`,
  `E''` strings, etc.), rewrite it by hand and keep the resulting rows identical.
- The file header (copyright block and `-- Description: ...`) is copied verbatim, including any
  typo it already contains; do not edit the description while porting.

## Procedure

1. Identify the source script, for example:

   ```text
   endurancetrio-data/src/main/resources/db/migration/dml/postgres/V20261101.002__process-event-17-results-postgres.sql
   ```

2. Check the date of the event/races the script touches (see **Scope** above). If it is dated 1990
   or later, stop here: the file stays PostgreSQL-only and only needs the `DATABASE.md` entry.

3. Generate the H2 file with the bundled helper (it refuses to overwrite an existing file):

   ```shell
   .agents/skills/port-postgres-dml-to-h2/scripts/port-postgres-dml-to-h2.sh \
     endurancetrio-data/src/main/resources/db/migration/dml/postgres/V20261101.002__process-event-17-results-postgres.sql
   ```

   If the helper reports PostgreSQL-only syntax, convert those statements manually.

4. Review the printed diff. It must contain **only** the schema statement and the `setval` /
   `ALTER SEQUENCE` lines. Any other difference means the port is wrong or the helper needs a new
   rule; investigate before continuing.

   Then verify statement-level parity across **all** pairs (also runs for scripts you did not
   touch, and catches duplicated or missing statements that a plain `diff` can hide):

   ```shell
   .agents/skills/port-postgres-dml-to-h2/scripts/check-h2-parity.py
   ```

   Every pair must report `OK`. An `ASYMMETRY` line means the H2 side must be corrected; a
   `NO H2 TWIN` line is only acceptable for an intentionally PostgreSQL-only script (see **Scope**);
   `OK (DUPLICATES)` marks a PostgreSQL repetition that H2 deliberately does not carry, and it only
   passes because the distinct statements still match. The checker ignores the documented dialect
   equivalences (schema handling, `setval` / `ALTER SEQUENCE`, `ON CONFLICT ... DO NOTHING`), so
   anything else it reports is a real divergence.

5. Verify the H2 migration against the in-memory test database:

   ```shell
   ./mvnw test -pl endurancetrio-app -am -Dskip.npm=true -Dskip.installnodenpm=true \
     -Dtest=EnduranceTrioApplicationTest -Dsurefire.failIfNoSpecifiedTests=false
   ```

   The Flyway log must show the new script applied, and end with a line shaped like
   `Successfully applied NN migrations to schema "endurancetrio_hub", now at version v...`.
   `-Dskip.npm` and `-Dskip.installnodenpm` avoid the frontend build, which is irrelevant here.

6. Register the new pair in
   `../../../endurancetrio-data/src/main/resources/db/DATABASE.md`, appending the next sequential item to
   the numbered list (the numbers are historical, not Flyway versions):

   ```markdown
   54. Process the event ID 17 race results:
       - [V20261101.002__temp-process-event-17-results-h2.sql](migration/dml/h2/V20261101.002__temp-process-event-17-results-h2.sql)
       - [V20261101.002__process-event-17-results-postgres.sql](migration/dml/postgres/V20261101.002__process-event-17-results-postgres.sql)
   ```

   Use a 4-space indented list for the two links, H2 first, and a description consistent with the
   script's `-- Description:` line. For a PostgreSQL-only script (event dated 1990 or later, or
   intentionally production-only data such as an insight article), list only the PostgreSQL script.

7. Check the version number is unique across **all** migration directories. Version numbers are
   shared by DDL and DML scripts because Flyway records every location in one
   `flyway_schema_history` table:

   ```shell
   ls endurancetrio-data/src/main/resources/db/migration/{ddl,dml}/{h2,postgres} \
     | grep -v ':' | sed 's/__.*//' | sort -u
   ```

## Deliverables

- New `...-h2.sql` script in `migration/dml/h2` — **only for events dated 1989 or earlier**.
- `DATABASE.md` updated with the new script pair (PostgreSQL-only scripts get a single link).
- `check-h2-parity.py` reporting `OK` for every pair (`OK (DUPLICATES)` for the registered
  PostgreSQL repetitions).
- A green `EnduranceTrioApplicationTest` run showing the migration applied.

Report the created file, the event date that justified the port, the diff summary, the parity check
result, the Flyway result line and the DATABASE.md entry. Do not create a commit unless the user asks
for one.
