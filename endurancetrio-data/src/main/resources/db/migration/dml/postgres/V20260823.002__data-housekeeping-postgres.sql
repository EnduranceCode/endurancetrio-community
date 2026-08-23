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

-- Description: Data Housekeeping

SET search_path TO endurancetrio_hub;

-- Update existing athletes
UPDATE athlete SET country = 'FRA' WHERE id = 82;
UPDATE athlete SET long_name = 'Paulo Sousa e Gomes' WHERE id = 98;
UPDATE athlete SET country = null WHERE id = 284;
UPDATE athlete SET long_name = 'Ruth Catherine Hunt', country = 'HKG', year_of_birth = 1955 WHERE id = 302;
UPDATE athlete SET country = 'DEN', year_of_birth = 1958 WHERE id = 306;

-- Update existing race's result_status
UPDATE race SET result_status = 'COMPLETE' WHERE id IN (19, 20, 21);
