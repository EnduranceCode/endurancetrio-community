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

-- Description: Inserts deterministic athlete fixtures for the athlete directory repository tests, covering accent
--              folding, letter ranges, and genders. Runs before each test method of AthleteRepositoryFilterTest.
--

INSERT INTO endurancetrio_hub.athlete
  (id, long_name, birth_name, known_name, gender, country, year_of_birth, version, created_at)
VALUES
  (990001, 'Alvaro Fernandes', NULL, 'Álvaro Fernandes', 'MALE', 'POR', 1970, 0, NOW()),
  (990002, 'Eder Sousa', NULL, 'Éder Sousa', 'MALE', 'POR', 1971, 0, NOW()),
  (990003, 'Bruna Costa', NULL, 'Bruna Costa', 'FEMALE', 'POR', 1972, 0, NOW()),
  (990004, 'Joao Matos', NULL, 'João Matos', 'MALE', 'POR', 1973, 0, NOW()),
  (990005, 'Mario Silva', NULL, 'Mário Silva', 'MALE', 'POR', 1974, 0, NOW()),
  (990006, 'Rita Castro', 'Rita Maria Castro', 'Rita Castro', 'FEMALE', 'POR', 1975, 0, NOW()),
  (990007, 'Sofia Ramos', NULL, 'Sofia Ramos', 'FEMALE', 'POR', 1976, 0, NOW()),
  (990008, 'Percent% Athlete', NULL, 'Percent% Athlete', 'MALE', 'POR', 1977, 0, NOW()),
  (990009, 'PercentX Athlete', NULL, 'PercentX Athlete', 'MALE', 'POR', 1978, 0, NOW()),
  (990010, 'Under_score Athlete', NULL, 'Under_score Athlete', 'FEMALE', 'POR', 1979, 0, NOW()),
  (990011, 'UnderXscore Athlete', NULL, 'UnderXscore Athlete', 'FEMALE', 'POR', 1980, 0, NOW());
