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

-- Description: merge athlete IDs 552 into 230 and 711 into 629

SET search_path TO endurancetrio_hub;

-- Merge athlete IDs 552 into 230
BEGIN;

UPDATE individual_result SET athlete_id = 230 WHERE athlete_id = 552;
UPDATE author SET athlete_id = 230 WHERE athlete_id = 552;
DELETE FROM relay_entry_athlete old_link USING relay_entry_athlete kept_link
  WHERE old_link.athlete_id = 552
    AND kept_link.relay_entry_id = old_link.relay_entry_id
    AND kept_link.athlete_id = 230;
UPDATE relay_entry_athlete SET athlete_id = 230 WHERE athlete_id = 552;
DELETE FROM athlete WHERE id = 552;

COMMIT;

-- Merge athlete IDs 711 into 629
BEGIN;

UPDATE individual_result SET athlete_id = 629 WHERE athlete_id = 711;
UPDATE author SET athlete_id = 629 WHERE athlete_id = 711;
DELETE FROM relay_entry_athlete old_link USING relay_entry_athlete kept_link
  WHERE old_link.athlete_id = 711
    AND kept_link.relay_entry_id = old_link.relay_entry_id
    AND kept_link.athlete_id = 629;
UPDATE relay_entry_athlete SET athlete_id = 629 WHERE athlete_id = 711;
DELETE FROM athlete WHERE id = 711;

COMMIT;

-- Correct the name of athlete of the athletes ID 48 and ID 629
UPDATE athlete SET long_name = 'José Zacarias Martins', known_name = 'José Zacarias Martins' WHERE id = 48;
UPDATE athlete SET long_name = 'Luis Anjos', known_name = 'Luis Anjos', country = 'POR' WHERE id = 629;

-- Correct athlete ID 384 to 250 for races 22 and 34
UPDATE individual_result SET athlete_id = 250 WHERE athlete_id = 384 AND race_id IN (22, 24, 34, 36);
