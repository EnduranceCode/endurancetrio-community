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

-- Description: Reorders the columns of the individual_result table to align with
--   the field order of IndividualResultDTO.

SET SCHEMA endurancetrio_hub;

-- Drop the FK constraint referencing individual_result
ALTER TABLE team_result_individual_result DROP CONSTRAINT IF EXISTS fk_team_result_individual_result_individual_result_id;

-- Drop indexes on individual_result
DROP INDEX IF EXISTS endurancetrio_hub.idx_individual_result_race_id;
DROP INDEX IF EXISTS endurancetrio_hub.idx_individual_result_athlete_id;
DROP INDEX IF EXISTS endurancetrio_hub.idx_individual_result_source_result_id;
DROP INDEX IF EXISTS endurancetrio_hub.idx_individual_result_team_id;

-- Drop FKs from individual_result
ALTER TABLE endurancetrio_hub.individual_result DROP CONSTRAINT IF EXISTS fk_individual_result_race_id;
ALTER TABLE endurancetrio_hub.individual_result DROP CONSTRAINT IF EXISTS fk_individual_result_individual_result_id;
ALTER TABLE endurancetrio_hub.individual_result DROP CONSTRAINT IF EXISTS fk_individual_result_athlete_id;
ALTER TABLE endurancetrio_hub.individual_result DROP CONSTRAINT IF EXISTS fk_individual_result_team_id;

-- Drop the table (also drops the sequence owned by individual_result.id)
DROP TABLE IF EXISTS endurancetrio_hub.individual_result;

-- Recreate the sequence
CREATE SEQUENCE IF NOT EXISTS endurancetrio_hub.seq_individual_result_id START WITH 1 INCREMENT BY 1;

-- Recreate the individual_result table with the desired column order
CREATE TABLE IF NOT EXISTS endurancetrio_hub.individual_result (
  id               BIGINT       DEFAULT nextval('endurancetrio_hub.seq_individual_result_id') NOT NULL,
  race_id          BIGINT       NOT NULL,
  rank             INTEGER,
  source_result_id BIGINT,
  penalty          VARCHAR(50),
  athlete_id       BIGINT       NOT NULL,
  age_group        VARCHAR(32),
  para_class       VARCHAR(32),
  team_id          BIGINT,
  team_name        VARCHAR(255),
  bib              VARCHAR(32),
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
  version          INTEGER      NOT NULL DEFAULT 0,
  created_at       TIMESTAMP    NOT NULL,
  updated_at       TIMESTAMP,
  CONSTRAINT pk_individual_result PRIMARY KEY (id),
  CONSTRAINT fk_individual_result_race_id
    FOREIGN KEY (race_id) REFERENCES endurancetrio_hub.race(id) ON DELETE CASCADE,
  CONSTRAINT fk_individual_result_individual_result_id
    FOREIGN KEY (source_result_id) REFERENCES endurancetrio_hub.individual_result(id),
  CONSTRAINT fk_individual_result_athlete_id
    FOREIGN KEY (athlete_id) REFERENCES endurancetrio_hub.athlete(id) ON DELETE CASCADE,
  CONSTRAINT fk_individual_result_team_id
    FOREIGN KEY (team_id) REFERENCES endurancetrio_hub.team(id) ON DELETE CASCADE
);

-- Recreate indexes on the individual_result table
CREATE INDEX IF NOT EXISTS endurancetrio_hub.idx_individual_result_race_id
  ON endurancetrio_hub.individual_result (race_id);
CREATE INDEX IF NOT EXISTS endurancetrio_hub.idx_individual_result_athlete_id
  ON endurancetrio_hub.individual_result (athlete_id);
CREATE INDEX IF NOT EXISTS endurancetrio_hub.idx_individual_result_source_result_id
  ON endurancetrio_hub.individual_result (source_result_id);
CREATE INDEX IF NOT EXISTS endurancetrio_hub.idx_individual_result_team_id
  ON endurancetrio_hub.individual_result (team_id);

-- Recreate the FK constraint on team_result_individual_result
ALTER TABLE endurancetrio_hub.team_result_individual_result
  ADD CONSTRAINT fk_team_result_individual_result_individual_result_id
    FOREIGN KEY (individual_result_id) REFERENCES endurancetrio_hub.individual_result(id) ON DELETE CASCADE;
