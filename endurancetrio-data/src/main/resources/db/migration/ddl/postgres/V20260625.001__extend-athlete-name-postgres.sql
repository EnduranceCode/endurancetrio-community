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

-- Description: Renames full_name to long_name and adds birth_name to the athlete table,
--   rebuilding the table to maintain column order alignment with the entity fields.

SET search_path TO endurancetrio_hub;

-- Drop the FK constraint referencing the athlete table before renaming it
ALTER TABLE individual_result DROP CONSTRAINT IF EXISTS fk_individual_result_athlete_id;

-- Rename the existing table, drop its PK constraint to free the name for the new table,
-- and detach the sequence ownership
ALTER TABLE athlete RENAME TO athlete_old;
ALTER TABLE athlete_old DROP CONSTRAINT pk_athlete;
ALTER SEQUENCE seq_athlete_id OWNED BY NONE;

-- Recreate the athlete table with the desired column order
CREATE TABLE athlete (
  id            BIGINT    DEFAULT nextval('seq_athlete_id') NOT NULL,
  long_name     TEXT      NOT NULL,
  birth_name    TEXT,
  known_name    TEXT      NOT NULL,
  gender        TEXT      NOT NULL,
  country       TEXT,
  year_of_birth INTEGER,
  version       INTEGER   NOT NULL DEFAULT 0,
  created_at    TIMESTAMP NOT NULL,
  updated_at    TIMESTAMP,
  CONSTRAINT pk_athlete PRIMARY KEY (id)
);

-- Copy data from the old table
INSERT INTO athlete (
  id, long_name, known_name, gender, country, year_of_birth, version, created_at, updated_at
)
SELECT
  id, full_name, known_name, gender, country, year_of_birth, version, created_at, updated_at
FROM athlete_old;

-- Reattach the sequence to the new table
ALTER SEQUENCE seq_athlete_id OWNED BY athlete.id;

-- Drop the old table
DROP TABLE athlete_old;

-- Recreate the FK constraint referencing the recreated athlete table
ALTER TABLE individual_result
  ADD CONSTRAINT fk_individual_result_athlete_id
    FOREIGN KEY (athlete_id) REFERENCES athlete(id) ON DELETE CASCADE;
