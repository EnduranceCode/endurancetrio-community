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

-- Description: Removes the DEFAULT CURRENT_TIMESTAMP from created_at on event domain tables
--

SET SCHEMA endurancetrio_hub;

ALTER TABLE event                    ALTER COLUMN created_at DROP DEFAULT;
ALTER TABLE organizer                ALTER COLUMN created_at DROP DEFAULT;
ALTER TABLE age_group                ALTER COLUMN created_at DROP DEFAULT;
ALTER TABLE distance                 ALTER COLUMN created_at DROP DEFAULT;
ALTER TABLE aquabike_distance        ALTER COLUMN created_at DROP DEFAULT;
ALTER TABLE aquathlon_distance       ALTER COLUMN created_at DROP DEFAULT;
ALTER TABLE biathlon_distance        ALTER COLUMN created_at DROP DEFAULT;
ALTER TABLE double_biathlon_distance ALTER COLUMN created_at DROP DEFAULT;
ALTER TABLE duathlon_distance        ALTER COLUMN created_at DROP DEFAULT;
ALTER TABLE single_sport_distance    ALTER COLUMN created_at DROP DEFAULT;
ALTER TABLE triathlon_distance       ALTER COLUMN created_at DROP DEFAULT;
ALTER TABLE course                   ALTER COLUMN created_at DROP DEFAULT;
ALTER TABLE race                     ALTER COLUMN created_at DROP DEFAULT;
ALTER TABLE triathlon_based_race     ALTER COLUMN created_at DROP DEFAULT;
ALTER TABLE event_file               ALTER COLUMN created_at DROP DEFAULT;
ALTER TABLE results_file             ALTER COLUMN created_at DROP DEFAULT;
