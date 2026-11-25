--
-- Copyright (c) 2011-2026 Ricardo do Canto
--
-- This file is part of the EnduranceTrio project.
--
-- Licensed under the Functional Software License (FSL), Version 1.1, ALv2 Future License
-- (the "License");
--
-- You may not use this file except in compliance with the License. You may obtain a copy
-- of the License at https://fsl.software/
--
-- THE SOFTWARE IS PROVIDED "AS IS" AND WITHOUT WARRANTIES OF ANY KIND, EXPRESS OR
-- IMPLIED, INCLUDING WITHOUT LIMITATION WARRANTIES OF FITNESS FOR A PARTICULAR
-- PURPOSE, MERCHANTABILITY, TITLE OR NON-INFRINGEMENT.
--
-- IN NO EVENT WILL WE HAVE ANY LIABILITY TO YOU ARISING OUT OF OR RELATED TO THE
-- SOFTWARE, INCLUDING INDIRECT, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES,
-- EVEN IF WE HAVE BEEN INFORMED OF THEIR POSSIBILITY IN ADVANCE.
--

-- Description: Recreates the team_result table with the relay duration columns in the
--   IndividualResult field order, copying the existing rows and preserving the links in
--   team_result_individual_result.

SET search_path TO endurancetrio_hub;

-- Drop the FK from the link table so its rows survive the table recreation
ALTER TABLE team_result_individual_result
  DROP CONSTRAINT IF EXISTS fk_team_result_individual_result_team_result_id;

-- Drop the FKs and primary key from team_result to free up the constraint names
ALTER TABLE team_result DROP CONSTRAINT IF EXISTS fk_team_result_race_id;
ALTER TABLE team_result DROP CONSTRAINT IF EXISTS fk_team_result_team_result_id;
ALTER TABLE team_result DROP CONSTRAINT IF EXISTS fk_team_result_team_id;
ALTER TABLE team_result DROP CONSTRAINT IF EXISTS pk_team_result;

-- Drop the indexes on team_result
DROP INDEX IF EXISTS idx_team_result_race_id;
DROP INDEX IF EXISTS idx_team_result_team_id;
DROP INDEX IF EXISTS idx_team_result_source_result_id;
DROP INDEX IF EXISTS idx_team_result_race_id_created_at;

-- Disown the sequence so it survives the table rename
ALTER SEQUENCE IF EXISTS seq_team_result_id OWNED BY NONE;

-- Rename the old table so its rows can be copied
ALTER TABLE team_result RENAME TO team_result_old;

-- Recreate team_result with the duration columns in the IndividualResult field order
CREATE TABLE IF NOT EXISTS team_result (
  id               BIGINT    DEFAULT nextval('seq_team_result_id') NOT NULL,
  race_id          BIGINT    NOT NULL,
  rank             INTEGER,
  source_result_id BIGINT,
  penalty          TEXT,
  team_id          BIGINT    NOT NULL,
  team_name        TEXT      NOT NULL,
  athletes_count   INTEGER   NOT NULL,
  age_group        TEXT,
  para_class       TEXT,
  bib              TEXT,
  swim             INTEGER,
  first_bike       INTEGER,
  first_run        INTEGER,
  t1               INTEGER,
  bike             INTEGER,
  t2               INTEGER,
  run              INTEGER,
  second_run       INTEGER,
  t3               INTEGER,
  second_bike      INTEGER,
  total            INTEGER,
  points           INTEGER,
  cumulative_rank  INTEGER,
  version          INTEGER   NOT NULL DEFAULT 0,
  created_at       TIMESTAMP NOT NULL,
  updated_at       TIMESTAMP,
  CONSTRAINT pk_team_result PRIMARY KEY (id),
  CONSTRAINT fk_team_result_race_id
    FOREIGN KEY (race_id) REFERENCES race(id) ON DELETE CASCADE,
  CONSTRAINT fk_team_result_team_result_id
    FOREIGN KEY (source_result_id) REFERENCES team_result(id),
  CONSTRAINT fk_team_result_team_id
    FOREIGN KEY (team_id) REFERENCES team(id)
);

-- Reattach the sequence to the new table
ALTER SEQUENCE IF EXISTS seq_team_result_id OWNED BY team_result.id;

-- Copy every existing row; the new duration columns stay null
INSERT INTO team_result (
  id, race_id, rank, source_result_id, penalty, team_id, team_name, athletes_count,
  age_group, para_class, bib, total, points, cumulative_rank, version, created_at, updated_at
)
SELECT
  id, race_id, rank, source_result_id, penalty, team_id, team_name, athletes_count,
  age_group, para_class, bib, total, points, cumulative_rank, version, created_at, updated_at
FROM team_result_old;

-- Drop the old table
DROP TABLE IF EXISTS team_result_old;

-- Recreate the FK constraint on team_result_individual_result
ALTER TABLE team_result_individual_result
  ADD CONSTRAINT fk_team_result_individual_result_team_result_id
    FOREIGN KEY (team_result_id) REFERENCES team_result(id) ON DELETE CASCADE;

-- Recreate the indexes on team_result
CREATE INDEX IF NOT EXISTS idx_team_result_race_id ON team_result (race_id);
CREATE INDEX IF NOT EXISTS idx_team_result_team_id ON team_result (team_id);
CREATE INDEX IF NOT EXISTS idx_team_result_source_result_id ON team_result (source_result_id);
CREATE INDEX IF NOT EXISTS idx_team_result_race_id_created_at
  ON team_result (race_id, created_at DESC);
