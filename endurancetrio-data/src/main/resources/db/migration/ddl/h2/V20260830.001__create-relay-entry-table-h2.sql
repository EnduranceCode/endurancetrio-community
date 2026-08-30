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

-- Description: Add support for one-off relay competitors and their member athletes

SET SCHEMA endurancetrio_hub;

-- Create the relay_entry table
CREATE TABLE IF NOT EXISTS endurancetrio_hub.relay_entry (
  id              BIGINT      NOT NULL,
  gender_category VARCHAR(32),
  age_group       VARCHAR(32),
  version         INTEGER     NOT NULL DEFAULT 0,
  created_at      TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at      TIMESTAMP,
  CONSTRAINT pk_relay_entry PRIMARY KEY (id),
  CONSTRAINT fk_relay_entry_team_id FOREIGN KEY (id) REFERENCES endurancetrio_hub.team(id) ON DELETE CASCADE
);

-- Create the relay_entry_athlete table
CREATE TABLE IF NOT EXISTS endurancetrio_hub.relay_entry_athlete (
  relay_entry_id BIGINT NOT NULL,
  athlete_id     BIGINT NOT NULL,
  CONSTRAINT pk_relay_entry_athlete PRIMARY KEY (relay_entry_id, athlete_id),
  CONSTRAINT fk_relay_entry_athlete_relay_entry_id
    FOREIGN KEY (relay_entry_id) REFERENCES endurancetrio_hub.relay_entry(id) ON DELETE CASCADE,
  CONSTRAINT fk_relay_entry_athlete_athlete_id
    FOREIGN KEY (athlete_id) REFERENCES endurancetrio_hub.athlete(id) ON DELETE CASCADE
);

-- Create index on the relay_entry_athlete table
CREATE INDEX IF NOT EXISTS endurancetrio_hub.idx_relay_entry_athlete_athlete_id
  ON endurancetrio_hub.relay_entry_athlete (athlete_id);
