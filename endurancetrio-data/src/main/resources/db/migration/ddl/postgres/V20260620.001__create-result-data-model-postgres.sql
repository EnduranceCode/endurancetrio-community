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

-- Description: Creates the athlete, team, para_class, individual_result, team_result,
--   and team_result_individual_result tables

SET search_path TO endurancetrio_hub;

-- Drop the age_group_id column from the race table
DROP INDEX IF EXISTS idx_race_age_group_id;
ALTER TABLE race DROP CONSTRAINT IF EXISTS fk_race_age_group_id;
ALTER TABLE race DROP COLUMN IF EXISTS age_group_id;

-- Drop the old age_group table and recreate it with the new schema
DROP TABLE IF EXISTS age_group;

-- Recreate the sequence (DROP TABLE above dropped it via OWNED BY)
CREATE SEQUENCE IF NOT EXISTS seq_age_group_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the age_group table with the new schema
CREATE TABLE IF NOT EXISTS age_group (
  id              BIGINT    DEFAULT nextval('seq_age_group_id') NOT NULL,
  code            TEXT      NOT NULL,
  denomination_en TEXT      NOT NULL,
  denomination_pt TEXT      NOT NULL,
  version         INTEGER   NOT NULL DEFAULT 0,
  created_at      TIMESTAMP NOT NULL,
  updated_at      TIMESTAMP,
  CONSTRAINT pk_age_group PRIMARY KEY (id)
);

-- Create a dependency between the seq_age_group_id sequence and the age_group table primary key
ALTER SEQUENCE IF EXISTS seq_age_group_id OWNED BY age_group.id;

-- Create sequence for the para_class table primary key
CREATE SEQUENCE IF NOT EXISTS seq_para_class_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the para_class table
CREATE TABLE IF NOT EXISTS para_class (
  id              BIGINT    DEFAULT nextval('seq_para_class_id') NOT NULL,
  code            TEXT      NOT NULL,
  denomination_en TEXT      NOT NULL,
  denomination_pt TEXT      NOT NULL,
  version         INTEGER   NOT NULL DEFAULT 0,
  created_at      TIMESTAMP NOT NULL,
  updated_at      TIMESTAMP,
  CONSTRAINT pk_para_class PRIMARY KEY (id)
  );

-- Create a dependency between the seq_para_class_id sequence and the para_class table primary key
ALTER SEQUENCE IF EXISTS seq_para_class_id OWNED BY para_class.id;

-- Create sequence for the athlete table primary key
CREATE SEQUENCE IF NOT EXISTS seq_athlete_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the athlete table
CREATE TABLE IF NOT EXISTS athlete (
  id            BIGINT    DEFAULT nextval('seq_athlete_id') NOT NULL,
  full_name     TEXT      NOT NULL,
  known_name    TEXT,
  gender        TEXT,
  country       TEXT,
  year_of_birth INTEGER,
  version       INTEGER   NOT NULL DEFAULT 0,
  created_at    TIMESTAMP NOT NULL,
  updated_at    TIMESTAMP,
  CONSTRAINT pk_athlete PRIMARY KEY (id)
);

-- Create a dependency between the seq_athlete_id sequence and the athlete table primary key
ALTER SEQUENCE IF EXISTS seq_athlete_id OWNED BY athlete.id;

-- Create sequence for the team table primary key
CREATE SEQUENCE IF NOT EXISTS seq_team_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the team table
CREATE TABLE IF NOT EXISTS team (
  id         BIGINT    DEFAULT nextval('seq_team_id') NOT NULL,
  full_name  TEXT      NOT NULL,
  short_name TEXT,
  city       TEXT,
  county     TEXT,
  district   TEXT,
  version    INTEGER   NOT NULL DEFAULT 0,
  created_at TIMESTAMP NOT NULL,
  updated_at TIMESTAMP,
  CONSTRAINT pk_team PRIMARY KEY (id)
);

-- Create a dependency between the seq_team_id sequence and the team table primary key
ALTER SEQUENCE IF EXISTS seq_team_id OWNED BY team.id;

-- Create sequence for the individual_result table primary key
CREATE SEQUENCE IF NOT EXISTS seq_individual_result_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the individual_result table
CREATE TABLE IF NOT EXISTS individual_result (
  id               BIGINT    DEFAULT nextval('seq_individual_result_id') NOT NULL,
  race_id          BIGINT    NOT NULL,
  rank             INTEGER,
  source_result_id BIGINT,
  penalty          TEXT,
  athlete_id       BIGINT    NOT NULL,
  team_id          BIGINT,
  team_name        TEXT,
  age_group        TEXT,
  para_class       TEXT,
  bib              TEXT,
  swim             INTEGER,
  first_bike       INTEGER,
  first_run        INTEGER,
  t1               INTEGER,
  bike             INTEGER,
  t2               INTEGER,
  second_bike      INTEGER,
  run              INTEGER,
  t3               INTEGER,
  second_run       INTEGER,
  total            INTEGER,
  points           INTEGER,
  version          INTEGER   NOT NULL DEFAULT 0,
  created_at       TIMESTAMP NOT NULL,
  updated_at       TIMESTAMP,
  CONSTRAINT pk_individual_result PRIMARY KEY (id),
  CONSTRAINT fk_individual_result_race_id
    FOREIGN KEY (race_id) REFERENCES race(id) ON DELETE CASCADE,
  CONSTRAINT fk_individual_result_individual_result_id
    FOREIGN KEY (source_result_id) REFERENCES individual_result(id),
  CONSTRAINT fk_individual_result_athlete_id
    FOREIGN KEY (athlete_id) REFERENCES athlete(id) ON DELETE CASCADE,
  CONSTRAINT fk_individual_result_team_id
    FOREIGN KEY (team_id) REFERENCES team(id) ON DELETE CASCADE
);

-- Create a dependency between the seq_individual_result_id sequence and the individual_result table primary key
ALTER SEQUENCE IF EXISTS seq_individual_result_id OWNED BY individual_result.id;

-- Create indexes on the individual_result table
CREATE INDEX IF NOT EXISTS idx_individual_result_race_id ON individual_result (race_id);
CREATE INDEX IF NOT EXISTS idx_individual_result_athlete_id ON individual_result (athlete_id);
CREATE INDEX IF NOT EXISTS idx_individual_result_source_result_id ON individual_result (source_result_id);
CREATE INDEX IF NOT EXISTS idx_individual_result_team_id ON individual_result (team_id);

-- Create sequence for the team_result table primary key
CREATE SEQUENCE IF NOT EXISTS seq_team_result_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the team_result table
CREATE TABLE IF NOT EXISTS team_result (
  id               BIGINT    DEFAULT nextval('seq_team_result_id') NOT NULL,
  race_id          BIGINT    NOT NULL,
  rank             INTEGER   NOT NULL,
  source_result_id BIGINT,
  penalty          TEXT,
  team_id          BIGINT    NOT NULL,
  team_name        TEXT      NOT NULL,
  athletes_count   INTEGER   NOT NULL,
  age_group        TEXT,
  para_class       TEXT,
  bib              TEXT,
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

-- Create a dependency between the seq_team_result_id sequence and the team_result table primary key
ALTER SEQUENCE IF EXISTS seq_team_result_id OWNED BY team_result.id;

-- Create indexes on the team_result table
CREATE INDEX IF NOT EXISTS idx_team_result_race_id ON team_result (race_id);
CREATE INDEX IF NOT EXISTS idx_team_result_team_id ON team_result (team_id);
CREATE INDEX IF NOT EXISTS idx_team_result_source_result_id ON team_result (source_result_id);

-- Create the team_result_individual_result table
CREATE TABLE IF NOT EXISTS team_result_individual_result (
  team_result_id      BIGINT NOT NULL,
  individual_result_id BIGINT NOT NULL,
  CONSTRAINT pk_team_result_individual_result PRIMARY KEY (team_result_id, individual_result_id),
  CONSTRAINT fk_team_result_individual_result_team_result_id
    FOREIGN KEY (team_result_id) REFERENCES team_result(id) ON DELETE CASCADE,
  CONSTRAINT fk_team_result_individual_result_individual_result_id
    FOREIGN KEY (individual_result_id) REFERENCES individual_result(id) ON DELETE CASCADE
);
-- Create indexes on the team_result_individual_result table
CREATE INDEX IF NOT EXISTS idx_team_result_individual_result_individual_result_id
  ON team_result_individual_result (individual_result_id);
