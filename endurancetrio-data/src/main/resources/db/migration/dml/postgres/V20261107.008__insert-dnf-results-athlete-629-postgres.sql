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
-- SOFTWARE, INCLUDING DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES,
-- EVEN IF WE HAVE BEEN INFORMED OF THEIR POSSIBILITY IN ADVANCE.
--

-- Description: Insert the DNF results of athlete 629 for races 19 and 21

SET search_path TO endurancetrio_hub;

-- Insert missing individual results for races
INSERT INTO individual_result (id, race_id, rank, source_result_id, penalty, athlete_id, team_id, team_name, age_group, para_class, bib, swim, first_bike, first_run, t1, bike, t2, run, second_run, t3, second_bike, total, points, version, created_at, updated_at) VALUES (3173, 19, NULL, NULL, 'DNF', 629, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NOW(), NULL);
INSERT INTO individual_result (id, race_id, rank, source_result_id, penalty, athlete_id, team_id, team_name, age_group, para_class, bib, swim, first_bike, first_run, t1, bike, t2, run, second_run, t3, second_bike, total, points, version, created_at, updated_at) VALUES (3174, 21, NULL, 3173, 'DNF', 629, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 0, NOW(), NULL);

SELECT setval('seq_individual_result_id', 3175, FALSE);
