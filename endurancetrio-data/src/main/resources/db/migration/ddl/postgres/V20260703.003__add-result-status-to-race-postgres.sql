SET search_path TO endurancetrio_hub;

-- Drop FK constraints referencing race
ALTER TABLE triathlon_based_race DROP CONSTRAINT IF EXISTS fk_triathlon_based_race_id;
ALTER TABLE results_file DROP CONSTRAINT IF EXISTS fk_results_file_race_id;
ALTER TABLE course_race DROP CONSTRAINT IF EXISTS fk_course_race_race_id;
ALTER TABLE race_hierarchy DROP CONSTRAINT IF EXISTS fk_race_hierarchy_race_id;
ALTER TABLE race_hierarchy DROP CONSTRAINT IF EXISTS fk_race_hierarchy_race_parent_id;
ALTER TABLE individual_result DROP CONSTRAINT IF EXISTS fk_individual_result_race_id;
ALTER TABLE team_result DROP CONSTRAINT IF EXISTS fk_team_result_race_id;

-- Disown the sequence so it survives the table rename
ALTER SEQUENCE IF EXISTS seq_race_id OWNED BY NONE;

-- Rename old table
ALTER TABLE race RENAME TO race_old;

-- Drop constraints on the renamed table to free up index/constraint names
ALTER TABLE race_old DROP CONSTRAINT IF EXISTS pk_race;
ALTER TABLE race_old DROP CONSTRAINT IF EXISTS uk_race_race_reference;

-- Recreate race with result_status after air_temperature
CREATE TABLE IF NOT EXISTS race (
  id              BIGINT        DEFAULT nextval('seq_race_id') NOT NULL,
  race_reference  VARCHAR(18)   NOT NULL,
  race_type       TEXT          NOT NULL,
  title           TEXT          NOT NULL,
  subtitle        TEXT          NOT NULL,
  gender_category TEXT          NOT NULL,
  race_date       DATE          NOT NULL,
  race_time       TIME,
  race_status     TEXT          NOT NULL,
  gun_time        TIME,
  air_temperature NUMERIC(5,2),
  result_status   TEXT          NOT NULL DEFAULT 'PENDING',
  version         INTEGER       NOT NULL DEFAULT 0,
  created_at      TIMESTAMP     NOT NULL,
  updated_at      TIMESTAMP,
  CONSTRAINT pk_race PRIMARY KEY (id),
  CONSTRAINT uk_race_race_reference UNIQUE (race_reference)
);

-- Reattach the sequence to the new table
ALTER SEQUENCE IF EXISTS seq_race_id OWNED BY race.id;

-- Copy existing data (result_status gets the DEFAULT 'PENDING')
INSERT INTO race (id, race_reference, race_type, title, subtitle, gender_category,
                  race_date, race_time, race_status, gun_time, air_temperature,
                  version, created_at, updated_at)
SELECT id,
       race_reference,
       race_type,
       title,
       subtitle,
       gender_category,
       race_date,
       race_time,
       race_status,
       gun_time,
       air_temperature,
       version,
       created_at,
       updated_at
FROM race_old;

-- Drop old table
DROP TABLE IF EXISTS race_old;

-- Recreate FK constraints
ALTER TABLE triathlon_based_race
  ADD CONSTRAINT fk_triathlon_based_race_id
    FOREIGN KEY (id) REFERENCES race(id) ON DELETE CASCADE;

ALTER TABLE results_file
  ADD CONSTRAINT fk_results_file_race_id
    FOREIGN KEY (race_id) REFERENCES race(id) ON DELETE CASCADE;

ALTER TABLE course_race
  ADD CONSTRAINT fk_course_race_race_id
    FOREIGN KEY (race_id) REFERENCES race(id) ON DELETE CASCADE;

ALTER TABLE race_hierarchy
  ADD CONSTRAINT fk_race_hierarchy_race_id
    FOREIGN KEY (race_id) REFERENCES race(id) ON DELETE CASCADE;

ALTER TABLE race_hierarchy
  ADD CONSTRAINT fk_race_hierarchy_race_parent_id
    FOREIGN KEY (parent_race_id) REFERENCES race(id) ON DELETE CASCADE;

ALTER TABLE individual_result
  ADD CONSTRAINT fk_individual_result_race_id
    FOREIGN KEY (race_id) REFERENCES race(id) ON DELETE CASCADE;

ALTER TABLE team_result
  ADD CONSTRAINT fk_team_result_race_id
    FOREIGN KEY (race_id) REFERENCES race(id) ON DELETE CASCADE;
