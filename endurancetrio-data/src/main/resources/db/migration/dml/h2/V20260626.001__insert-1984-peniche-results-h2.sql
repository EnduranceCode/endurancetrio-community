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

-- Description: Inserts athlete and individual_result data for the 1984 Peniche triathlon event

-- Fix race status for the races with id 2 and 3
--
UPDATE endurancetrio_hub.race SET race_status = 'PLANNED' WHERE id = 2;
UPDATE endurancetrio_hub.race SET race_status = 'COMPLETED' WHERE id = 3;

-- endurancetrio_hub.athlete table
--
INSERT INTO
  endurancetrio_hub.athlete (id, long_name, birth_name, known_name, gender, country, year_of_birth, version, created_at, updated_at)
VALUES
  (1, 'Paulo José Paula Carvalho', null, 'Paulo Paula Carvalho', 'MALE', 'POR', 1961, 0, NOW(), null),
  (2, 'Carlos Adão', null, 'Carlos Adão', 'MALE', 'POR', null, 0, NOW(), null),
  (3, 'António José Lamelas', null, 'António José Lamelas', 'MALE', 'POR', null, 0, NOW(), null),
  (4, 'Paulo Cavaleiro', null, 'Paulo Cavaleiro', 'MALE', 'POR', null, 0, NOW(), null),
  (5, 'Manuel Alexandre Correia', null, 'Manuel Alexandre Correia', 'MALE', 'POR', null, 0, NOW(), null),
  (6, 'José Manuel Trincão Marques', null, 'José Manuel Trincão Marques', 'MALE', 'POR', null, 0, NOW(), null),
  (7, 'Rizério Salgado', null, 'Rizério Salgado', 'MALE', 'POR', null, 0, NOW(), null),
  (8, 'Paulo Jorge Alves', null, 'Paulo Jorge Alves', 'MALE', 'POR', null, 0, NOW(), null),
  (9, 'Luis Nicolau Jesus', null, 'Luis Nicolau Jesus', 'MALE', 'POR', null, 0, NOW(), null),
  (10, 'Miguel Trigueiros Martel Lima', null, 'Miguel Martel Lima', 'MALE', 'POR', null, 0, NOW(), null),
  (11, 'António José Correia', null, 'António José Correia', 'MALE', 'POR', null, 0, NOW(), null),
  (12, 'Carlos Rodrigues', null, 'Carlos Rodrigues', 'MALE', 'POR', null, 0, NOW(), null),
  (13, 'Ricardo Jorge Aguiar', null, 'Ricardo Jorge Aguiar', 'MALE', 'POR', null, 0, NOW(), null),
  (14, 'Orlando Jesus Fernandes', null, 'Orlando Jesus Fernandes', 'MALE', 'POR', null, 0, NOW(), null),
  (15, 'Eduardo Neto', null, 'Eduardo Neto', 'MALE', 'POR', null, 0, NOW(), null),
  (16, 'João Amoroso', null, 'João Amoroso', 'MALE', 'POR', null, 0, NOW(), null),
  (17, 'Carlos Lemos', null, 'Carlos Lemos', 'MALE', 'POR', null, 0, NOW(), null),
  (18, 'Alfredo M. G. de Sousa', null, 'Alfredo M. G. de Sousa', 'MALE', 'POR', null, 0, NOW(), null),
  (19, 'António Matias', null, 'António Matias', 'MALE', 'POR', null, 0, NOW(), null),
  (20, 'Fernando Francisco', null, 'Fernando Francisco', 'MALE', 'POR', null, 0, NOW(), null),
  (21, 'Cândido Vera Costa', null, 'Cândido Vera Costa', 'MALE', 'POR', null, 0, NOW(), null),
  (22, 'Nuno Bello Conceição', null, 'Nuno Bello Conceição', 'MALE', 'POR', null, 0, NOW(), null),
  (23, 'Albertino Jesus Cintra', null, 'Albertino Jesus Cintra', 'MALE', 'POR', null, 0, NOW(), null),
  (24, 'Joaquim Matameu', null, 'Joaquim Matameu', 'MALE', 'POR', null, 0, NOW(), null),
  (25, 'Jaime Ribeiro', null, 'Jaime Ribeiro', 'MALE', 'POR', null, 0, NOW(), null),
  (26, 'Jorge Fernandes Graça', null, 'Jorge Fernandes Graça', 'MALE', 'POR', null, 0, NOW(), null),
  (27, 'Francisco Casimiro', null, 'Francisco Casimiro', 'MALE', 'POR', null, 0, NOW(), null),
  (28, 'José Manuel M. Tavares', null, 'José Manuel M. Tavares', 'MALE', 'POR', null, 0, NOW(), null),
  (29, 'Anónimo #1', null, 'Anónimo #1', 'MALE', null, null, 0, NOW(), null),
  (30, 'Anónimo #2', null, 'Anónimo #2', 'MALE', null, null, 0, NOW(), null);

ALTER SEQUENCE endurancetrio_hub.seq_athlete_id RESTART WITH 31;


-- endurancetrio_hub.individual_result table
--
INSERT INTO
  endurancetrio_hub.individual_result (id, race_id, rank, source_result_id, penalty, athlete_id, team_id, team_name, age_group, para_class, bib, swim, first_bike, first_run, t1, bike, t2, second_bike, run, t3, second_run, total, points, version, created_at, updated_at)
VALUES
  (1, 1, 1, null, null, 1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 4515000, null, 0, NOW(), null),
  (2, 1, 2, null, null, 2, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 4680000, null, 0, NOW(), null),
  (3, 1, 3, null, null, 3, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5055000, null, 0, NOW(), null),
  (4, 1, 4, null, null, 4, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5137000, null, 0, NOW(), null),
  (5, 1, 5, null, null, 5, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5205000, null, 0, NOW(), null),
  (6, 1, 6, null, null, 6, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5250000, null, 0, NOW(), null),
  (7, 1, 7, null, null, 7, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5290000, null, 0, NOW(), null),
  (8, 1, 8, null, null, 8, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5310000, null, 0, NOW(), null),
  (9, 1, 9, null, null, 9, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5340000, null, 0, NOW(), null),
  (10, 1, 10, null, null, 10, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5410000, null, 0, NOW(), null),
  (11, 1, 11, null, null, 11, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5470000, null, 0, NOW(), null),
  (12, 1, 12, null, null, 12, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5535000, null, 0, NOW(), null),
  (13, 1, 13, null, null, 13, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5572000, null, 0, NOW(), null),
  (14, 1, 14, null, null, 14, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5630000, null, 0, NOW(), null),
  (15, 1, 15, null, null, 15, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5650000, null, 0, NOW(), null),
  (16, 1, 16, null, null, 16, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5655000, null, 0, NOW(), null),
  (17, 1, 17, null, null, 17, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 5655000, null, 0, NOW(), null),
  (18, 1, 18, null, null, 18, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 6110000, null, 0, NOW(), null),
  (19, 1, 19, null, null, 19, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 6110000, null, 0, NOW(), null),
  (20, 1, 20, null, null, 20, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 6110000, null, 0, NOW(), null),
  (21, 1, 21, null, null, 21, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 6120000, null, 0, NOW(), null),
  (22, 1, 22, null, null, 22, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 6315000, null, 0, NOW(), null),
  (23, 1, 23, null, null, 23, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 6458000, null, 0, NOW(), null),
  (24, 1, 24, null, null, 24, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 6530000, null, 0, NOW(), null),
  (25, 1, 25, null, null, 25, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 6535000, null, 0, NOW(), null),
  (26, 1, 26, null, null, 26, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 6627000, null, 0, NOW(), null),
  (27, 1, 27, null, null, 27, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 6852000, null, 0, NOW(), null),
  (28, 1, 28, null, null, 28, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 6898000, null, 0, NOW(), null),
  (29, 1, null, null, 'DNF', 29, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (30, 1, null, null, 'DNF', 30, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (31, 3, 1, 1, null, 1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (32, 3, 2, 2, null, 2, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (33, 3, 3, 3, null, 3, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (34, 3, 4, 4, null, 4, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (35, 3, 5, 5, null, 5, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (36, 3, 6, 6, null, 6, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (37, 3, 7, 7, null, 7, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (38, 3, 8, 8, null, 8, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (39, 3, 9, 9, null, 9, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (40, 3, 10, 10, null, 10, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (41, 3, 11, 11, null, 11, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (42, 3, 12, 12, null, 12, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (43, 3, 13, 13, null, 13, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (44, 3, 14, 14, null, 14, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (45, 3, 15, 15, null, 15, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (46, 3, 16, 16, null, 16, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (47, 3, 17, 17, null, 17, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (48, 3, 18, 18, null, 18, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (49, 3, 19, 19, null, 19, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (50, 3, 20, 20, null, 20, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (51, 3, 21, 21, null, 21, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (52, 3, 22, 22, null, 22, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (53, 3, 23, 23, null, 23, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (54, 3, 24, 24, null, 24, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (55, 3, 25, 25, null, 25, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (56, 3, 26, 26, null, 26, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (57, 3, 27, 27, null, 27, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (58, 3, 28, 28, null, 28, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (59, 3, null, 29, null, 29, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null),
  (60, 3, null, 30, null, 30, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, 0, NOW(), null);

ALTER SEQUENCE endurancetrio_hub.seq_individual_result_id RESTART WITH 61;
