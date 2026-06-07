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

-- Description: Fixes course sport from DUATHLON to BIATHLON for biatlo events inserted
-- in 1988 and 1989, and corrects course titles from "Duatlo" to "Biatlo"

UPDATE
  endurancetrio_community.course
SET
  sport = 'BIATHLON',
  title = CASE id
            WHEN 41 THEN 'Biatlo Sprint'
            WHEN 42 THEN 'Biatlo Jovem'
            WHEN 45 THEN 'Biatlo Sprint'
            ELSE 'Biatlo Standard'
          END
WHERE
  id IN (25, 27, 36, 41, 42, 43, 44, 45, 46, 47, 53);
