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

-- Description: Reverts courses incorrectly changed from DUATHLON to BIATHLON
-- for the 1988/1989 biatlo events, restoring original sport and titles.

-- Set the search path to the schema `endurancetrio_hub`
SET search_path TO endurancetrio_hub;

UPDATE
  course
SET
  sport = 'DUATHLON',
  title = CASE id
            WHEN 41 THEN 'Duatlo Sprint'
            WHEN 42 THEN 'Duatlo Jovem'
            WHEN 45 THEN 'Duatlo Sprint'
            ELSE 'Duatlo Standard'
          END
WHERE
  id IN (25, 27, 36, 41, 42, 43, 44, 45, 46, 47, 53);
