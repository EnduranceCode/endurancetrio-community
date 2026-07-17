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

-- Description: Adds indexes to support "most recently added" queries on events and race
--   results. The event index speeds up the home page query that orders by created_at DESC.
--   The result indexes speed up the landing page query that orders by the most recent result
--   creation date via correlated MAX subqueries.

SET search_path TO endurancetrio_hub;

-- Index for findRacesWithMostRecentAddedResults: supports MAX(created_at) subquery on IndividualResult
CREATE INDEX IF NOT EXISTS idx_individual_result_race_id_created_at
  ON individual_result (race_id, created_at DESC);

-- Index for findRacesWithMostRecentAddedResults: supports MAX(created_at) subquery on TeamResult
CREATE INDEX IF NOT EXISTS idx_team_result_race_id_created_at
  ON team_result (race_id, created_at DESC);
