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

-- Description: merge team IDs 59, 66 and 94 into team ID 10 and rename team 10 to NAC

SET SCHEMA endurancetrio_hub;

BEGIN;

UPDATE team SET full_name = 'Núcleo Atletismo Cucujães', short_name = 'NAC', city = 'Cucujães', county = 'Oliveira de Azeméis', district = 'Aveiro' WHERE id = 10;

UPDATE individual_result SET team_id = 10 WHERE team_id IN (59, 66, 94);
UPDATE team_result SET team_id = 10 WHERE team_id IN (59, 66, 94);
DELETE FROM team WHERE id IN (59, 66, 94);

COMMIT;

UPDATE individual_result SET team_name = 'NAC Martano' WHERE team_name = 'Nac. Martano';
UPDATE team_result SET team_name = 'NAC Martano' WHERE team_name = 'Nac. Martano';
UPDATE individual_result SET team_name = 'NAC Martano' WHERE team_name = 'N. Martano';
UPDATE team_result SET team_name = 'NAC Martano' WHERE team_name = 'N. Martano';
