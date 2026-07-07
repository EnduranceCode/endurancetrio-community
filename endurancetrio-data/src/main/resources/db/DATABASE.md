# EnduranceTrio application database

This file documents the process to manage the database used with **EnduranceTrio** application.

## Database Naming Conventions

This project follows consistent naming conventions for database objects to ensure clarity,
maintainability, and cross-database compatibility.

### Table and Column Names

| Element                 | Naming Convention                                                    | Example                                                    |
|-------------------------|----------------------------------------------------------------------|------------------------------------------------------------|
| Table                   | Use snake_case and plural nouns                                      | tracking_data, owners                                      |
| Column                  | Use snake_case with descriptive names                                | device, created_at                                         |

### Constraints

| Element                 | Naming Convention                                                    | Example                                                    |
|-------------------------|----------------------------------------------------------------------|------------------------------------------------------------|
| Primary Key             | pk_{table}                                                           | pk\_tracking\_data                                         |
| Foreign Key             | fk\_{table}\_{referenced\_table}\_{referenced_column}                | fk\_tracking\_data\_owners\_id                             |
| Unique Key              | uk\_{table}\_{column}                                                | uk\_owners\_alias                                          |
| Check Constraint        | chk\_{table}\_{description}                                          | chk\_tracking\_data\_valid\_latitude                       |

### Indexes

| Element                 | Naming Convention                                                    | Example                                                    |
|-------------------------|----------------------------------------------------------------------|------------------------------------------------------------|
| Standard Index          | idx\_{table}\_{column}                                               | idx\_tracking\_data\_device                                |
| Composite Index         | idx\_{table}\_{column}[\_{column}...] with columns in query order    | idx\_tracking\_data\_device\_owner                         |
| Unique Index            | uk\_{table}\_{column}[\_{column}...]                                 | uk\_tracking\_data\_device\_record\_time                   |

### Sequences

| Element                 | Naming Convention                                                    | Example                                                    |
|-------------------------|----------------------------------------------------------------------|------------------------------------------------------------|
| Sequence                | seq\_{table}\_{column}                                               | seq\_tracking\_data_id                                     |

### Naming Limitations

- **Length Limit** - Keep names under 50 characters to ensure compatibility across H2 and PostgreSQL
- **Readability** - Use clear and descriptive names and prioritize clarity over brevity
- **Ordering** - For composite indexes, list columns in the order they are most frequently queried
- **Case** - Use lowercase letters to avoid case-sensitivity issues
- **Character Set** - Use only alphanumeric characters and underscores
- **Avoid** - Database-specific keywords and special characters

## Database Migration

The [database migrations](https://en.wikipedia.org/wiki/Schema_migration) are made using
[Flyway](https://www.red-gate.com/products/flyway/).

The migration scripts are separated by type and database:

* **[DDL](https://en.wikipedia.org/wiki/Data_definition_language)** - These scripts are
  database-specific and contain schema definition (e.g., `CREATE TABLE`, `ALTER TABLE`)
  and are stored in:
    * [`migration/ddl/h2`](migration/ddl/h2): DDL scripts for the **H2** database
      (local/dev environments).
    * [`migration/ddl/postgres`](migration/ddl/postgres): DDL scripts for the **PostgreSQL**
      database (production environment).
* **[DML](https://en.wikipedia.org/wiki/Data_manipulation_language)** - These scripts contain
  data manipulation (e.g., `INSERT`, `UPDATE`) and are stored in:
    * [`migration/dml`](migration/dml)

The [migration scripts](https://documentation.red-gate.com/flyway/quickstart-how-flyway-works/why-database-migrations)
must respect the following naming convention (which is compatible with the
[Flyway naming patterns](https://www.red-gate.com/blog/database-devops/flyway-naming-patterns-matter)):

    Vyyyymmdd.nnn__free-description.sql

> ***yyyymmdd*** : Date of the script's creation (e.g., `20260531`)
>
> ***nnn*** : Sequence number for scripts created on the same date (e.g., `001`, `002`)
>
> ***free-description*** : A short free description of the scripts actions with words separated
> with dashes

### Legacy Naming Convention (retired)

Previously, the project used a version-based naming convention that tied migration versions to the
project release version:

    Vxxx.yyy.zzz.nnn__free-description.sql

Existing scripts using this convention will not be renamed and remain fully functional. All new
scripts must use the current timestamp-based convention.

### Spring Boot Configuration

To make this structure work, the `spring.flyway.locations` property is set with the adequate values
in the `application-{profile}.yaml` files, enabling Flyway to use the correct folders
for each database.

#### H2 Database (test profile)

```yaml
spring:
  flyway:
    locations:
      - classpath:db/migration/ddl/h2
      - classpath:db/migration/dml/h2
```

#### PostgreSQL Database

```yaml
spring:
  flyway:
    locations:
      - classpath:db/migration/ddl/postgres
      - classpath:db/migration/dml/postgres
```

## Migration Scripts

The migration scripts are duplicated, when necessary for tests, for each supported database
(H2 and PostgreSQL) to ensure full compatibility with the targeted databases.

Scripts **1 to 14** use the retired legacy naming convention. Scripts **15 onwards** use the current
timestamp-based naming convention.

1. Creates the tables for the **EnduranceTrio** application:
   - [V000.000.001.001__create-tables-h2.sql](migration/ddl/h2/V000.000.001.001__create-tables-h2.sql)
   - [V000.000.001.001__create-tables-postgres.sql](migration/ddl/postgres/V000.000.001.001__create-tables-postgres.sql)
2. Inserts the data of the 1984 triathlon related events in Portugal:
   - [V000.000.001.002__insert-1984-portuguese-triathlon-data-h2.sql](migration/dml/h2/V000.000.001.002__insert-1984-portuguese-triathlon-data-h2.sql)
   - [V000.000.001.002__insert-1984-portuguese-triathlon-data-postgres.sql](migration/dml/postgres/V000.000.001.002__insert-1984-portuguese-triathlon-data-postgres.sql)
3. Inserts the data of the 1985 triathlon related events in Portugal:
   - [V000.000.001.002__insert-1985-portuguese-triathlon-data-h2.sql](migration/dml/h2/V000.000.001.003__insert-1985-portuguese-triathlon-data-h2.sql)
   - [V000.000.001.002__insert-1985-portuguese-triathlon-data-postgres.sql](migration/dml/postgres/V000.000.001.003__insert-1985-portuguese-triathlon-data-postgres.sql)
4. Inserts the data of the 1986 triathlon related events in Portugal:
   - [V000.000.001.002__insert-1986-portuguese-triathlon-data-h2.sql](migration/dml/h2/V000.000.001.004__insert-1986-portuguese-triathlon-data-h2.sql)
   - [V000.000.001.002__insert-1986-portuguese-triathlon-data-postgres.sql](migration/dml/postgres/V000.000.001.004__insert-1986-portuguese-triathlon-data-postgres.sql)
5. Inserts the data of the 1987 triathlon related events in Portugal:
   - [V000.000.001.002__insert-1987-portuguese-triathlon-data-h2.sql](migration/dml/h2/V000.000.001.005__insert-1987-portuguese-triathlon-data-h2.sql)
   - [V000.000.001.002__insert-1987-portuguese-triathlon-data-postgres.sql](migration/dml/postgres/V000.000.001.005__insert-1987-portuguese-triathlon-data-postgres.sql)
6. Inserts the data of the 1988 triathlon related events in Portugal:
   - [V000.000.001.002__insert-1988-portuguese-triathlon-data-h2.sql](migration/dml/h2/V000.000.001.006__insert-1988-portuguese-triathlon-data-h2.sql)
   - [V000.000.001.002__insert-1988-portuguese-triathlon-data-postgres.sql](migration/dml/postgres/V000.000.001.006__insert-1988-portuguese-triathlon-data-postgres.sql)
7. Inserts the data of the 1989 triathlon related events in Portugal:
   - [V000.000.001.002__insert-1989-portuguese-triathlon-data-h2.sql](migration/dml/h2/V000.000.001.007__insert-1989-portuguese-triathlon-data-h2.sql)
   - [V000.000.001.002__insert-1989-portuguese-triathlon-data-postgres.sql](migration/dml/postgres/V000.000.001.007__insert-1989-portuguese-triathlon-data-postgres.sql)
8. Inserts *Tracker* test data into **EnduranceTrio** application database tables:
   - [V000.000.001.008__insert-test-data-h2.sql](migration/dml/h2/V000.000.001.008__insert-test-data-h2.sql)
   - [V000.000.001.008__insert-test-data-postgres.sql](migration/dml/postgres/V000.000.001.008__insert-test-data-postgres.sql)
9. Creates the **EnduranceTrio** application telemetry management database table:
   - [V000.002.000.001__create-telemetry-management-table-h2.sql](migration/ddl/h2/V000.002.000.001__create-telemetry-management-table-h2.sql)
   - [V000.002.000.001__create-telemetry-management-table-postgres.sql](migration/ddl/postgres/V000.002.000.001__create-telemetry-management-table-postgres.sql)
10. Inserts test data into **EnduranceTrio** application telemetry management database table:
    - [V000.002.000.002__insert-telemetry-management-test-data-h2.sql](migration/dml/h2/V000.002.000.002__insert-telemetry-management-test-data-h2.sql)
    - [V000.002.000.002__insert-telemetry-management-test-data-postgres.sql](migration/dml/postgres/V000.002.000.002__insert-telemetry-management-test-data-postgres.sql)
11. Creates the **EnduranceTrio** application route database tables:
    - [V000.002.000.001__create-route-tables-h2.sql](migration/ddl/h2/V000.002.000.003__create-route-tables-h2.sql)
    - [V000.002.000.001__create-route-tables-postgres.sql](migration/ddl/postgres/V000.002.000.003__create-route-tables-postgres.sql)
12. Inserts *Tracker* test data into **EnduranceTrio** application route database tables:
    - [V000.002.002.002__insert-route-test-data-h2.sql](migration/dml/h2/V000.002.000.004__insert-route-test-data-h2.sql)
    - [V000.002.002.002__insert-route-test-data-postgres.sql](migration/dml/postgres/V000.002.000.004__insert-route-test-data-postgres.sql)
13. Adds auditable columns to the **EnduranceTrio** application event domain database tables:
    - [V000.004.000.001__add-auditable-columns-to-event-tables-h2.sql](migration/ddl/h2/V000.004.000.001__add-auditable-columns-to-event-tables-h2.sql)
    - [V000.004.000.001__add-auditable-columns-to-event-tables-postgres.sql](migration/ddl/postgres/V000.004.000.001__add-auditable-columns-to-event-tables-postgres.sql)
14. Removes the `DEFAULT CURRENT_TIMESTAMP` from the `created_at` column on event domain tables:
    - [V000.004.000.002__remove-created-at-default-from-event-tables-h2.sql](migration/ddl/h2/V000.004.000.002__remove-created-at-default-from-event-tables-h2.sql)
    - [V000.004.000.002__remove-created-at-default-from-event-tables-postgres.sql](migration/ddl/postgres/V000.004.000.002__remove-created-at-default-from-event-tables-postgres.sql)
15. Fixes the course sport and title for biatlo events inserted in 1988 and 1989:
    - [V20260607.001__fix-1988-1989-biatlo-course-types-h2.sql](migration/dml/h2/V20260607.001__fix-1988-1989-biatlo-course-types-h2.sql)
    - [V20260607.001__fix-1988-1989-biatlo-course-types-postgres.sql](migration/dml/postgres/V20260607.001__fix-1988-1989-biatlo-course-types-postgres.sql)
16. Swaps the course IDs for the course_race junction table:
    - [V20260610.001__swap-course-ids-course-race-h2.sql](migration/dml/h2/V20260610.001__swap-course-ids-course-race-h2.sql)
    - [V20260610.001__swap-course-ids-course-race-postgres.sql](migration/dml/postgres/V20260610.001__swap-course-ids-course-race-postgres.sql)
17. Reverts the course type fixes for biatlo events inserted in 1988 and 1989:
    - [V20260610.002__revert-1988-1989-biatlo-course-types-h2.sql](migration/dml/h2/V20260610.002__revert-1988-1989-biatlo-course-types-h2.sql)
    - [V20260610.002__revert-1988-1989-biatlo-course-types-postgres.sql](migration/dml/postgres/V20260610.002__revert-1988-1989-biatlo-course-types-postgres.sql)
18. Moves race 148 to course 37:
    - [V20260610.003__move-race-148-to-course-37-h2.sql](migration/dml/h2/V20260610.003__move-race-148-to-course-37-h2.sql)
    - [V20260610.003__move-race-148-to-course-37-postgres.sql](migration/dml/postgres/V20260610.003__move-race-148-to-course-37-postgres.sql)
19. Fixes the event-organizer relationships:
    - [V20260610.004__fix-event-organizers-h2.sql](migration/dml/h2/V20260610.004__fix-event-organizers-h2.sql)
    - [V20260610.004__fix-event-organizers-postgres.sql](migration/dml/postgres/V20260610.004__fix-event-organizers-postgres.sql)
20. Creates the result data model tables:
    - [V20260620.001__create-result-data-model-h2.sql](migration/ddl/h2/V20260620.001__create-result-data-model-h2.sql)
    - [V20260620.001__create-result-data-model-postgres.sql](migration/ddl/postgres/V20260620.001__create-result-data-model-postgres.sql)
21. Renames full_name to long_name and adds birth_name to the athlete table:
    - [V20260625.001__extend-athlete-name-h2.sql](migration/ddl/h2/V20260625.001__extend-athlete-name-h2.sql)
    - [V20260625.001__extend-athlete-name-postgres.sql](migration/ddl/postgres/V20260625.001__extend-athlete-name-postgres.sql)
22. Inserts athlete and individual_result data for the 1984 Peniche triathlon event:
    - [V20260626.001__insert-1984-peniche-results-h2.sql](migration/dml/h2/V20260626.001__insert-1984-peniche-results-h2.sql)
    - [V20260626.001__insert-1984-peniche-results-postgres.sql](migration/dml/postgres/V20260626.001__insert-1984-peniche-results-postgres.sql)
23. Reorders the columns of the individual_result table to align with the field order of IndividualResultDTO:
    - [V20260703.001__reorder-individual-result-columns-h2.sql](migration/ddl/h2/V20260703.001__reorder-individual-result-columns-h2.sql)
    - [V20260703.001__reorder-individual-result-columns-postgres.sql](migration/ddl/postgres/V20260703.001__reorder-individual-result-columns-postgres.sql)
24. Re-inserts individual_result data for the 1984 Peniche triathlon event with columns in the new order:
    - [V20260703.002__reinsert-individual-result-data-h2.sql](migration/dml/h2/V20260703.002__reinsert-individual-result-data-h2.sql)
    - [V20260703.002__reinsert-individual-result-data-postgres.sql](migration/dml/postgres/V20260703.002__reinsert-individual-result-data-postgres.sql)
25. Adds `result_status` column to the `race` table:
    - [V20260703.003__add-result-status-to-race-h2.sql](migration/ddl/h2/V20260703.003__add-result-status-to-race-h2.sql)
    - [V20260703.003__add-result-status-to-race-postgres.sql](migration/ddl/postgres/V20260703.003__add-result-status-to-race-postgres.sql)
26. Sets `result_status` on existing race data:
    - [V20260703.004__set-result-status-on-existing-races-h2.sql](migration/dml/h2/V20260703.004__set-result-status-on-existing-races-h2.sql)
    - [V20260703.004__set-result-status-on-existing-races-postgres.sql](migration/dml/postgres/V20260703.004__set-result-status-on-existing-races-postgres.sql)
27. Creates the insight section database tables:
    - [V20260707.001__create-insight-tables-h2.sql](migration/ddl/h2/V20260707.001__create-insight-tables-h2.sql)
    - [V20260707.001__create-insight-tables-postgres.sql](migration/ddl/postgres/V20260707.001__create-insight-tables-postgres.sql)

