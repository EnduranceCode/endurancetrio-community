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

-- Description: Adds indexes supporting event directory filters and ordering. The year index is
--   backed by a generated start_year column so the H2 counterpart can use the same index.

SET search_path TO endurancetrio_hub;

ALTER TABLE event
  ADD COLUMN start_year INTEGER GENERATED ALWAYS AS (CAST(EXTRACT(YEAR FROM start_date) AS INTEGER)) STORED;

CREATE INDEX IF NOT EXISTS idx_event_district_county ON event (district, county);

CREATE INDEX IF NOT EXISTS idx_event_start_date_id ON event (start_date DESC, id DESC);

CREATE INDEX IF NOT EXISTS idx_event_start_year ON event (start_year);
