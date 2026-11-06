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
-- IMPLIED, INCLUDING WITHOUT LIMITATION WARRANTIES OF MERCHANTABILITY, TITLE OR NON-INFRINGEMENT.
--
-- IN NO EVENT WILL WE HAVE ANY LIABILITY TO YOU ARISING OUT OF OR RELATED TO THE
-- SOFTWARE, INCLUDING DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES,
-- EVEN IF WE HAVE BEEN INFORMED OF THEIR POSSIBILITY.
--

-- Description: Correct team_result data and merge athlete IDs 455 into 314

SET SCHEMA endurancetrio_hub;

UPDATE team_result SET race_id = 53 WHERE race_id = 49;

BEGIN;

UPDATE individual_result SET athlete_id = 314 WHERE athlete_id = 455;
UPDATE author SET athlete_id = 314 WHERE athlete_id = 455;
DELETE FROM relay_entry_athlete
 WHERE athlete_id = 314
   AND relay_entry_id IN (
     SELECT relay_entry_id
       FROM relay_entry_athlete
      WHERE athlete_id = 314
   );
UPDATE relay_entry_athlete SET athlete_id = 314 WHERE athlete_id = 455;
DELETE FROM athlete WHERE id = 455;

COMMIT;
