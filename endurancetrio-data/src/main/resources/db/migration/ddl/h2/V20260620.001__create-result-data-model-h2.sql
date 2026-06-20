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

-- Description: Creates the athlete, team, para_class, individual_result, team_result and
--   team_result_individual_result tables

SET SCHEMA endurancetrio_community;

-- Drop the age_group_id column from the race table
ALTER TABLE endurancetrio_community.race DROP CONSTRAINT IF EXISTS fk_race_age_group_id;
ALTER TABLE endurancetrio_community.race DROP COLUMN IF EXISTS age_group_id;

-- Drop the old age_group table and recreate it with the new schema
DROP TABLE IF EXISTS endurancetrio_community.age_group;

-- Recreate the sequence (DROP TABLE above dropped it via OWNED BY)
CREATE SEQUENCE IF NOT EXISTS endurancetrio_community.seq_age_group_id START WITH 1 INCREMENT BY 1;

-- Create the age_group table with the new schema
CREATE TABLE IF NOT EXISTS endurancetrio_community.age_group (
  id              BIGINT       DEFAULT nextval('endurancetrio_community.seq_age_group_id') NOT NULL,
  code            VARCHAR(50)  NOT NULL,
  denomination_en VARCHAR(255) NOT NULL,
  denomination_pt VARCHAR(255) NOT NULL,
  version         INTEGER      NOT NULL DEFAULT 0,
  created_at      TIMESTAMP    NOT NULL,
  updated_at      TIMESTAMP,
  CONSTRAINT pk_age_group PRIMARY KEY (id)
);

-- Create sequence for the para_class table primary key
CREATE SEQUENCE IF NOT EXISTS endurancetrio_community.seq_para_class_id START WITH 1 INCREMENT BY 1;

-- Create the para_class table
CREATE TABLE IF NOT EXISTS endurancetrio_community.para_class (
  id              BIGINT       DEFAULT nextval('endurancetrio_community.seq_para_class_id') NOT NULL,
  code            VARCHAR(50)  NOT NULL,
  denomination_en VARCHAR(255) NOT NULL,
  denomination_pt VARCHAR(255) NOT NULL,
  version         INTEGER      NOT NULL DEFAULT 0,
  created_at      TIMESTAMP    NOT NULL,
  updated_at      TIMESTAMP,
  CONSTRAINT pk_para_class PRIMARY KEY (id)
  );

-- Create sequence for the athlete table primary key
CREATE SEQUENCE IF NOT EXISTS endurancetrio_community.seq_athlete_id START WITH 1 INCREMENT BY 1;

-- Create the athlete table
CREATE TABLE IF NOT EXISTS endurancetrio_community.athlete (
  id            BIGINT       DEFAULT nextval('endurancetrio_community.seq_athlete_id') NOT NULL,
  full_name     VARCHAR(255) NOT NULL,
  known_name    VARCHAR(255),
  gender        VARCHAR(50),
  country       VARCHAR(50),
  year_of_birth INTEGER,
  version       INTEGER      NOT NULL DEFAULT 0,
  created_at    TIMESTAMP    NOT NULL,
  updated_at    TIMESTAMP,
  CONSTRAINT pk_athlete PRIMARY KEY (id)
);

-- Create sequence for the team table primary key
CREATE SEQUENCE IF NOT EXISTS endurancetrio_community.seq_team_id START WITH 1 INCREMENT BY 1;

-- Create the team table
CREATE TABLE IF NOT EXISTS endurancetrio_community.team (
  id         BIGINT       DEFAULT nextval('endurancetrio_community.seq_team_id') NOT NULL,
  full_name  VARCHAR(255) NOT NULL,
  short_name VARCHAR(255),
  city       VARCHAR(255),
  county     VARCHAR(255),
  district   VARCHAR(255),
  version    INTEGER      NOT NULL DEFAULT 0,
  created_at TIMESTAMP    NOT NULL,
  updated_at TIMESTAMP,
  CONSTRAINT pk_team PRIMARY KEY (id)
);

-- Create sequence for the individual_result table primary key
CREATE SEQUENCE IF NOT EXISTS endurancetrio_community.seq_individual_result_id START WITH 1 INCREMENT BY 1;

-- Create the individual_result table
CREATE TABLE IF NOT EXISTS endurancetrio_community.individual_result (
  id               BIGINT       DEFAULT nextval('endurancetrio_community.seq_individual_result_id') NOT NULL,
  race_id          BIGINT       NOT NULL,
  rank             INTEGER,
  source_result_id BIGINT,
  penalty          VARCHAR(50),
  athlete_id       BIGINT       NOT NULL,
  team_id          BIGINT,
  team_name        VARCHAR(255),
  age_group        VARCHAR(32),
  para_class       VARCHAR(32),
  bib              VARCHAR(32),
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
  version          INTEGER      NOT NULL DEFAULT 0,
  created_at       TIMESTAMP    NOT NULL,
  updated_at       TIMESTAMP,
  CONSTRAINT pk_individual_result PRIMARY KEY (id),
  CONSTRAINT fk_individual_result_race_id
    FOREIGN KEY (race_id) REFERENCES endurancetrio_community.race(id) ON DELETE CASCADE,
  CONSTRAINT fk_individual_result_individual_result_id
    FOREIGN KEY (source_result_id) REFERENCES endurancetrio_community.individual_result(id),
  CONSTRAINT fk_individual_result_athlete_id
    FOREIGN KEY (athlete_id) REFERENCES endurancetrio_community.athlete(id) ON DELETE CASCADE,
  CONSTRAINT fk_individual_result_team_id
    FOREIGN KEY (team_id) REFERENCES endurancetrio_community.team(id) ON DELETE CASCADE
);

-- Create indexes on the individual_result table
CREATE INDEX IF NOT EXISTS endurancetrio_community.idx_individual_result_race_id
  ON endurancetrio_community.individual_result (race_id);
CREATE INDEX IF NOT EXISTS endurancetrio_community.idx_individual_result_athlete_id
  ON endurancetrio_community.individual_result (athlete_id);
CREATE INDEX IF NOT EXISTS endurancetrio_community.idx_individual_result_source_result_id
  ON endurancetrio_community.individual_result (source_result_id);
CREATE INDEX IF NOT EXISTS endurancetrio_community.idx_individual_result_team_id
  ON endurancetrio_community.individual_result (team_id);

-- Create sequence for the team_result table primary key
CREATE SEQUENCE IF NOT EXISTS endurancetrio_community.seq_team_result_id START WITH 1 INCREMENT BY 1;

-- Create the team_result table
CREATE TABLE IF NOT EXISTS endurancetrio_community.team_result (
  id               BIGINT       DEFAULT nextval('endurancetrio_community.seq_team_result_id') NOT NULL,
  race_id          BIGINT       NOT NULL,
  rank             INTEGER      NOT NULL,
  source_result_id BIGINT,
  penalty          VARCHAR(50),
  team_id          BIGINT       NOT NULL,
  team_name        VARCHAR(255) NOT NULL,
  athletes_count   INTEGER      NOT NULL,
  age_group        VARCHAR(32),
  para_class       VARCHAR(32),
  bib              VARCHAR(32),
  total            INTEGER,
  points           INTEGER,
  cumulative_rank  INTEGER,
  version          INTEGER      NOT NULL DEFAULT 0,
  created_at       TIMESTAMP    NOT NULL,
  updated_at       TIMESTAMP,
  CONSTRAINT pk_team_result PRIMARY KEY (id),
  CONSTRAINT fk_team_result_race_id
    FOREIGN KEY (race_id) REFERENCES endurancetrio_community.race(id) ON DELETE CASCADE,
  CONSTRAINT fk_team_result_team_result_id
    FOREIGN KEY (source_result_id) REFERENCES endurancetrio_community.team_result(id),
  CONSTRAINT fk_team_result_team_id
    FOREIGN KEY (team_id) REFERENCES endurancetrio_community.team(id)
);

-- Create indexes on the team_result table
CREATE INDEX IF NOT EXISTS endurancetrio_community.idx_team_result_race_id
  ON endurancetrio_community.team_result (race_id);
CREATE INDEX IF NOT EXISTS endurancetrio_community.idx_team_result_team_id
  ON endurancetrio_community.team_result (team_id);
CREATE INDEX IF NOT EXISTS endurancetrio_community.idx_team_result_source_result_id
  ON endurancetrio_community.team_result (source_result_id);

-- Create the team_result_individual_result table
CREATE TABLE IF NOT EXISTS endurancetrio_community.team_result_individual_result (
  team_result_id       BIGINT NOT NULL,
  individual_result_id BIGINT NOT NULL,
  CONSTRAINT pk_team_result_individual_result PRIMARY KEY (team_result_id, individual_result_id),
  CONSTRAINT fk_team_result_individual_result_team_result_id
    FOREIGN KEY (team_result_id) REFERENCES endurancetrio_community.team_result(id) ON DELETE CASCADE,
  CONSTRAINT fk_team_result_individual_result_individual_result_id
    FOREIGN KEY (individual_result_id) REFERENCES endurancetrio_community.individual_result(id) ON DELETE CASCADE
);

-- Create indexes on the team_result_individual_result table
CREATE INDEX IF NOT EXISTS endurancetrio_community.idx_team_result_individual_result_individual_result_id
  ON endurancetrio_community.team_result_individual_result (individual_result_id);
