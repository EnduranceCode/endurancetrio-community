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

-- Description: Insert the data of the 1988 triathlon related events in Portugal

-- endurancetrio.event table
-- -------------------------
INSERT INTO
  endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district)
VALUES
  (24, '19880313APT001', '1º Biatlo de Porto de Mós', '1988-03-13', '1988-03-13', 'Porto de Mós', 'Porto de Mós', 'Leiria'),
  (25, '19880402APT001', '2º Biatlo do Porto', '1988-04-02', '1988-04-02', 'Porto', 'Porto', 'Porto'),
  (26, '19880417APT001', 'Biatlo Terras de Santa Maria', '1988-04-17', '1988-04-17', 'Santa Maria da Feira', 'Santa Maria da Feira', 'Aveiro'),
  (27, '19880515APT001', 'I Triatlo de Loulé', '1988-05-15', '1988-05-15', 'Loulé', 'Loulé', 'Faro'),
  (28, '19880522APT001', 'Campeonato Nacional de Triatlo Cat. B', '1988-05-22', '1988-05-22', 'Aguieira', 'Santa Comba Dão', 'Viseu'),
  (29, '19880605APT001', 'Triatlo do Ambiente', '1988-06-05', '1988-06-05', 'Oeiras', 'Oeiras', 'Lisboa'),
  (30, '19880612APT001', 'Triatlo Internacional de Cascais 1988', '1988-06-12', '1988-06-12', 'Cascais', 'Cascais', 'Lisboa'),
  (31, '19880626APT001', '1º Triatlo Promoção de Almodôvar', '1988-06-26', '1988-06-26', 'Almodôvar', 'Almodôvar', 'Beja'),
  (32, '19880703APT001', 'Triatlo do Porto', '1988-07-03', '1988-07-03', 'Porto', 'Porto', 'Porto'),
  (33, '19880716APT001', 'Triatlo da Costa Azul', '1988-07-16', '1988-07-16', 'Tróia', 'Grândola', 'Setúbal'),
  (34, '19880806APT001', '2º Triatlo do Coimbrão', '1988-08-06', '1988-08-06', 'Coimbrão', 'Leiria', 'Leiria'),
  (35, '19880814APT001', '1º Biatlo de Arruda dos Vinhos', '1988-08-14', '1988-08-14', 'Arruda dos Vinhos', 'Arruda dos Vinhos', 'Lisboa'),
  (36, '19880815APT001', 'V Triatlo de Peniche', '1988-08-14', '1988-08-14', 'Peniche', 'Peniche', 'Leiria'),
  (37, '19880904APT001', '1º Triatlo de Óbidos', '1988-09-04', '1988-09-04', 'Óbidos', 'Óbidos', 'Leiria'),
  (38, '19880918APT001', '2º Triatlo de Ponte de Sor', '1988-09-18', '1988-09-18', 'Ponde de Sor', 'Ponte de Sor', 'Portalegre'),
  (39, '19880925APT001', 'Triatlo de Lagos', '1988-09-25', '1988-09-25', 'Lagos', 'Lagos', 'Faro');


-- endurancetrio.organizer table
-- -----------------------------
INSERT INTO
  endurancetrio.organizer (id, name, city, county, district, organizer_type)
VALUES
  (22, 'Câmara Municipal de Porto de Mós', 'Porto de Mós', 'Porto de Mós', 'Leiria', 'PUBLIC'),
  (23, 'Câmara Municipal de Oliveira de Azemeis', 'Oliveira de Azeméis', 'Oliveira de Azeméis', 'Aveiro', 'PUBLIC'),
  (24, 'Câmara Municipal de São João da Madeira', 'São João da Madeira', 'São João da Madeira', 'Aveiro', 'PUBLIC'),
  (25, 'Jornal Correio de Azemeis', 'Oliveira de Azeméis', 'Oliveira de Azeméis', 'Aveiro', 'PRIVATE'),
  (26, 'Louletano Desportos Clube', 'Loulé', 'Loulé', 'Faro', 'CLUB'),
  (27, 'Câmara Municipal de Loulé', 'Loulé', 'Loulé', 'Faro', 'PUBLIC'),
  (28, 'Câmara Municipal de Oeiras', 'Oeiras', 'Oeiras', 'Lisboa', 'PUBLIC'),
  (29, 'Câmara Municipal de Almodôvar', 'Almodôvar', 'Almodôvar', 'Beja', 'PUBLIC'),
  (30, 'Centro de Desporto e Cultura do Coimbrão', 'Coimbrão', 'Leiria', 'Leiria', 'CLUB'),
  (31, 'Câmara Municipal de Arruda dos Vinhos', 'Arruda dos Vinhos', 'Arruda dos Vinhos', 'Lisboa', 'PUBLIC'),
  (32, 'Câmara Municipal de Óbidos', 'Óbidos', 'Óbidos', 'Leiria', 'PUBLIC'),
  (33, 'Câmara Municipal de Lagos', 'Lagos', 'Lagos', 'Faro', 'PUBLIC');


-- endurancetrio.event_organizer table
-- -----------------------------------
INSERT INTO
  endurancetrio.event_organizer (event_id, organizer_id)
VALUES
  (24, 22),
  (24, 12),
  (25, 11),
  (25, 12),
  (26, 17),
  (26, 23),
  (26, 24),
  (26, 25),
  (27, 26),
  (27, 27),
  (28, 12),
  (29, 28),
  (29, 12),
  (30, 3),
  (30, 12),
  (31, 29),
  (31, 12),
  (32, 17),
  (32, 12),
  (33, 21),
  (33, 12),
  (34, 30),
  (34, 12),
  (35, 31),
  (35, 12),
  (36, 2),
  (36, 12),
  (37, 32),
  (37, 12),
  (38, 16),
  (38, 12),
  (39, 18),
  (39, 33),
  (39, 19),
  (39, 12);


-- endurancetrio.event_file table
-- ------------------------------
INSERT INTO
  endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type)
VALUES
  (49, 24, 'Regulamento', 1, true, '19880313APT001-REG001-01.pdf', 'RULES'),
  (50, 24, 'Percursos', 1, true, '19880313APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (51, 26, 'Regulamento', 1, true, '19880417APT001-REG001-01.pdf', 'RULES'),
  (52, 26, 'Cartaz', 1, true, '19880417APT001-IMG001-01.png', 'POSTER'),
  (53, 26, 'Percursos', 1, true, '19880417APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (54, 27, 'Cartaz', 1, true, '19880515APT001-IMG001-01.png', 'POSTER'),
  (55, 27, 'Regulamento', 1, true, '19880515APT001-REG002-01.pdf', 'RULES'),
  (56, 27, 'Percursos', 1, true, '19880515APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (57, 28, 'Regulamento', 1, true, '19880522APT001-REG001-01.pdf', 'RULES'),
  (58, 28, 'Percursos', 1, true, '19880522APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (60, 29, 'Cartaz', 1, true, '19880605APT001-IMG001-01.png', 'POSTER'),
  (61, 29, 'Percursos', 1, true, '19880605APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (62, 30, 'Cartaz', 1, true, '19880612APT001-IMG001-01.png', 'POSTER'),
  (63, 30, 'Regulamento', 1, true, '19880612APT001-REG001-01.pdf', 'RULES'),
  (64, 30, 'Percursos', 1, true, '19880612APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (65, 31, 'Regulamento', 1, true, '19880626APT001-REG001-01.pdf', 'RULES'),
  (66, 31, 'Percursos', 1, true, '19880703APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (67, 32, 'Regulamento', 1, true, '19880703APT001-REG001-01.pdf', 'RULES'),
  (68, 33, 'Cartaz', 1, true, '19880716APT001-IMG001-01.png', 'POSTER'),
  (69, 33, 'Regulamento', 1, true, '19880716APT001-REG001-01.pdf', 'RULES'),
  (70, 34, 'Regulamento', 1, true, '19880806APT001-REG001-01.pdf', 'RULES'),
  (71, 34, 'Cartaz', 1, true, '19880806APT001-IMG001-01.png', 'POSTER'),
  (72, 34, 'Percursos', 1, true, '19880806APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (73, 35, 'Regulamento', 1, true, '19880814APT001-REG001-01.pdf', 'RULES'),
  (74, 35, 'Percursos', 1, true, '19880814APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (75, 36, 'Regulamento', 1, true, '19880815APT001-REG001-01.pdf', 'RULES'),
  (76, 39, 'Cartaz', 1, true, '19880925APT001-IMG001-01.png', 'POSTER'),
  (77, 39, 'Percursos', 1, true, '19880925APT001-MAP001-01.pdf', 'COURSE_MAPS');


-- endurancetrio.distance table
-- ----------------------------
INSERT INTO
  endurancetrio.distance (id, distance_type)
VALUES
  (25, 'STANDARD'),
  (26, 'STANDARD'),
  (27, 'STANDARD'),
  (28, 'STANDARD'),
  (29, 'MIDDLE_DISTANCE'),
  (30, 'SPRINT'),
  (31, 'STANDARD'),
  (32, 'SPRINT'),
  (33, 'STANDARD'),
  (34, 'STANDARD'),
  (35, 'STANDARD'),
  (36, 'STANDARD'),
  (37, 'STANDARD'),
  (38, 'STANDARD'),
  (39, 'STANDARD'),
  (40, 'STANDARD');


-- endurancetrio.biathlon_distance table
-- -------------------------------------
INSERT INTO
  endurancetrio.biathlon_distance (id, bike_distance, bike_laps, run_distance, run_laps)
VALUES
  (26, 40000, 1, 10000, 1);


-- endurancetrio.duathlon_distance table
-- -------------------------------------
INSERT INTO
  endurancetrio.duathlon_distance (id, first_run_distance, first_run_laps, bike_distance, bike_laps, second_run_distance, second_run_laps)
VALUES
  (25, 5200, 1, 42000, 1, 5200, 1),
  (27, 4000, 1, 50000, 1, 10000, 1),
  (36, 5200, 1, 31200, 1, 5000, 1);


-- endurancetrio.triathlon_distance table
-- --------------------------------------
INSERT INTO
  endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps)
VALUES
  (28, 1000, 1, 43000, 1, 10000, 1),
  (29, 2000, 1, 84000, 2, 20000, 1),
  (30, 500, 1, 25000, 1, 5000, 1),
  (31, 1000, 1, 42000, 1, 10000, 1),
  (32, 800, 1, 32000, 1, 8000, 1),
  (33, 1000, 1, 49000, 1, 10000, 1),
  (34, 1000, 1, 45000, 1, 10000, 1),
  (35, 1000, 1, 45000, 1, 10000, 1),
  (37, 1200, 1, 40000, 1, 11000, 1),
  (38, 800, 1, 38000, 1, 9000, 1),
  (39, 1000, 1, 40000, 1, 10000, 1),
  (40, 1000, 1, 41000, 1, 10000, 1);


-- endurancetrio.course table
-- --------------------------
INSERT INTO
  endurancetrio.course (id, event_id, title, sport, distance_id)
VALUES
  (25, 24, 'Duatlo Standard', 'DUATHLON', 25),
  (26, 25, 'Biatlo Standard', 'BIATHLON', 26),
  (27, 26, 'Duatlo Standard', 'DUATHLON', 27),
  (28, 27, 'Triatlo Standard', 'TRIATHLON', 28),
  (29, 28, 'Triatlo Média Distância', 'TRIATHLON', 29),
  (30, 29, 'Triatlo Sprint', 'TRIATHLON', 30),
  (31, 30, 'Triatlo Standard', 'TRIATHLON', 31),
  (32, 31, 'Triatlo Sprint', 'TRIATHLON', 32),
  (33, 32, 'Triatlo Standard', 'TRIATHLON', 33),
  (34, 33, 'Triatlo Standard', 'TRIATHLON', 34),
  (35, 34, 'Triatlo Standard', 'TRIATHLON', 35),
  (36, 35, 'Duatlo Standard', 'DUATHLON', 36),
  (37, 36, 'Triatlo Standard', 'TRIATHLON', 37),
  (38, 37, 'Triatlo Standard', 'TRIATHLON', 38),
  (39, 38, 'Triatlo Standard', 'TRIATHLON', 39),
  (40, 39, 'Triatlo Standard', 'TRIATHLON', 40);


-- endurancetrio.race table
-- ------------------------
INSERT INTO
  endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status)
VALUES
  (92, '19880313APT001-001', '1º Biatlo de Porto de Mós', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-03-13', '11:00:00', null, null, 'COMPLETED'),
  (93, '19880313APT001-002', '1º Biatlo de Porto de Mós', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-03-13', '11:00:00', null, null, 'COMPLETED'),
  (94, '19880313APT001-003', '1º Biatlo de Porto de Mós', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-03-13', '11:00:00', null, null, 'COMPLETED'),
  (95, '19880313APT001-004', '1º Biatlo de Porto de Mós', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-03-13', '11:00:00', null, null, 'PLANNED'),
  (96, '19880313APT001-005', '1º Biatlo de Porto de Mós', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-03-13', '11:00:00', null, null, 'COMPLETED'),
  (97, '19880402APT001-001', '2º Biatlo do Porto', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-04-02', '11:00:00', null, null, 'COMPLETED'),
  (98, '19880402APT001-002', '2º Biatlo do Porto', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-04-02', '11:00:00', null, null, 'COMPLETED'),
  (99, '19880402APT001-003', '2º Biatlo do Porto', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-04-02', '11:00:00', null, null, 'COMPLETED'),
  (100, '19880402APT001-004', '2º Biatlo do Porto', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-04-02', '11:00:00', null, null, 'PLANNED'),
  (101, '19880402APT001-005', '2º Biatlo do Porto', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-04-02', '11:00:00', null, null, 'COMPLETED'),
  (102, '19880417APT001-001', 'Biatlo Terras de Santa Maria', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-04-17', '09:00:00', null, null, 'COMPLETED'),
  (103, '19880417APT001-002', 'Biatlo Terras de Santa Maria', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-04-17', '09:00:00', null, null, 'COMPLETED'),
  (104, '19880417APT001-003', 'Biatlo Terras de Santa Maria', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-04-17', '09:00:00', null, null, 'COMPLETED'),
  (105, '19880417APT001-004', 'Biatlo Terras de Santa Maria', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-04-17', '09:00:00', null, null, 'PLANNED'),
  (106, '19880417APT001-005', 'Biatlo Terras de Santa Maria', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-04-17', '09:00:00', null, null, 'COMPLETED'),
  (107, '19880515APT001-001', 'I Triatlo de Loulé', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-05-15', '11:00:00', null, null, 'COMPLETED'),
  (108, '19880515APT001-002', 'I Triatlo de Loulé', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-05-15', '11:00:00', null, null, 'COMPLETED'),
  (109, '19880515APT001-003', 'I Triatlo de Loulé', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-05-15', '11:00:00', null, null, 'COMPLETED'),
  (110, '19880522APT001-001', 'Campeonato Nacional de Triatlo Categoria B', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-05-22', '11:00:00', null, null, 'COMPLETED'),
  (111, '19880522APT001-002', 'Campeonato Nacional de Triatlo Categoria B', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-05-22', '11:00:00', null, null, 'PLANNED'),
  (112, '19880522APT001-003', 'Campeonato Nacional de Triatlo Categoria B', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-05-22', '11:00:00', null, null, 'COMPLETED'),
  (113, '19880605APT001-001', 'Triatlo do Ambiente', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-06-05', '15:00:00', null, null, 'COMPLETED'),
  (114, '19880605APT001-002', 'Triatlo do Ambiente', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-06-05', '15:00:00', null, null, 'COMPLETED'),
  (115, '19880605APT001-003', 'Triatlo do Ambiente', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-06-05', '15:00:00', null, null, 'COMPLETED'),
  (116, '19880612APT001-001', 'Triatlo Internacional de Cascais 1988', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-06-12', '12:00:00', null, null, 'COMPLETED'),
  (117, '19880612APT001-002', 'Triatlo Internacional de Cascais 1988', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-06-12', '12:00:00', null, null, 'COMPLETED'),
  (118, '19880612APT001-003', 'Triatlo Internacional de Cascais 1988', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-06-12', '12:00:00', null, null, 'COMPLETED'),
  (119, '19880612APT001-004', 'Triatlo Internacional de Cascais 1988', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-06-12', '12:00:00', null, null, 'COMPLETED'),
  (120, '19880612APT001-005', 'Triatlo Internacional de Cascais 1988', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-06-12', '12:00:00', null, null, 'COMPLETED'),
  (121, '19880626APT001-001', '1º Triatlo Promoção de Almodôvar', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-06-26', '16:00:00', null, null, 'COMPLETED'),
  (122, '19880626APT001-002', '1º Triatlo Promoção de Almodôvar', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-06-26', '16:00:00', null, null, 'COMPLETED'),
  (123, '19880626APT001-003', '1º Triatlo Promoção de Almodôvar', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-06-26', '16:00:00', null, null, 'COMPLETED'),
  (124, '19880626APT001-004', '1º Triatlo Promoção de Almodôvar', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-06-26', '16:00:00', null, null, 'PLANNED'),
  (125, '19880626APT001-005', '1º Triatlo Promoção de Almodôvar', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-06-26', '16:00:00', null, null, 'COMPLETED'),
  (126, '19880703APT001-001', 'Triatlo do Porto', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-07-03', '11:00:00', null, null, 'COMPLETED'),
  (127, '19880703APT001-002', 'Triatlo do Porto', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-07-03', '11:00:00', null, null, 'COMPLETED'),
  (128, '19880703APT001-003', 'Triatlo do Porto', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-07-03', '11:00:00', null, null, 'COMPLETED'),
  (129, '19880716APT001-001', 'Triatlo da Costa Azul', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-07-16', '11:00:00', null, null, 'COMPLETED'),
  (130, '19880716APT001-002', 'Triatlo da Costa Azul', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-07-16', '11:00:00', null, null, 'COMPLETED'),
  (131, '19880716APT001-003', 'Triatlo da Costa Azul', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-07-16', '11:00:00', null, null, 'COMPLETED'),
  (132, '19880716APT001-004', 'Triatlo da Costa Azul', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-07-16', '11:00:00', null, null, 'PLANNED'),
  (133, '19880716APT001-005', 'Triatlo da Costa Azul', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-07-16', '11:00:00', null, null, 'COMPLETED'),
  (134, '19880806APT001-001', 'II Triatlo do Coimbrão', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-08-06', '15:30:00', null, null, 'COMPLETED'),
  (135, '19880806APT001-002', 'II Triatlo do Coimbrão', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-08-06', '15:30:00', null, null, 'COMPLETED'),
  (136, '19880806APT001-003', 'II Triatlo do Coimbrão', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-08-06', '15:30:00', null, null, 'COMPLETED'),
  (137, '19880806APT001-004', 'II Triatlo do Coimbrão', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-08-06', '15:30:00', null, null, 'PLANNED'),
  (138, '19880806APT001-005', 'II Triatlo do Coimbrão', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-08-06', '15:30:00', null, null, 'COMPLETED'),
  (139, '19880814APT001-001', 'I Biatlo de Arruda dos Vinhos', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-08-14', '10:00:00', null, null, 'COMPLETED'),
  (140, '19880814APT001-002', 'I Biatlo de Arruda dos Vinhos', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-08-14', '10:00:00', null, null, 'COMPLETED'),
  (141, '19880814APT001-003', 'I Biatlo de Arruda dos Vinhos', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-08-14', '10:00:00', null, null, 'COMPLETED'),
  (142, '19880814APT001-004', 'I Biatlo de Arruda dos Vinhos', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-08-14', '10:00:00', null, null, 'PLANNED'),
  (143, '19880814APT001-005', 'I Biatlo de Arruda dos Vinhos', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-08-14', '10:00:00', null, null, 'COMPLETED'),
  (144, '19880815APT001-001', 'V Triatlo de Peniche', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-08-15', '16:00:00', null, null, 'COMPLETED'),
  (145, '19880815APT001-002', 'V Triatlo de Peniche', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-08-15', '16:00:00', null, null, 'COMPLETED'),
  (146, '19880815APT001-003', 'V Triatlo de Peniche', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-08-15', '16:00:00', null, null, 'COMPLETED'),
  (147, '19880815APT001-004', 'V Triatlo de Peniche', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-08-15', '16:00:00', null, null, 'PLANNED'),
  (148, '19880815APT001-005', 'V Triatlo de Peniche', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-08-15', '16:00:00', null, null, 'COMPLETED'),
  (149, '19880904APT001-001', '1º Triatlo de Óbidos', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-09-04', '10:00:00', null, null, 'COMPLETED'),
  (150, '19880904APT001-002', '1º Triatlo de Óbidos', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-09-04', '10:00:00', null, null, 'COMPLETED'),
  (151, '19880904APT001-003', '1º Triatlo de Óbidos', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-09-04', '10:00:00', null, null, 'COMPLETED'),
  (152, '19880904APT001-004', '1º Triatlo de Óbidos', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-09-04', '10:00:00', null, null, 'PLANNED'),
  (153, '19880904APT001-005', '1º Triatlo de Óbidos', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-09-04', '10:00:00', null, null, 'COMPLETED'),
  (154, '19880918APT001-001', '2º Triatlo de Ponte de Sor', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-09-18', '10:00:00', null, null, 'COMPLETED'),
  (155, '19880918APT001-002', '2º Triatlo de Ponte de Sor', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-09-18', '10:00:00', null, null, 'COMPLETED'),
  (156, '19880918APT001-003', '2º Triatlo de Ponte de Sor', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-09-18', '10:00:00', null, null, 'COMPLETED'),
  (157, '19880918APT001-004', '2º Triatlo de Ponte de Sor', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-09-18', '10:00:00', null, null, 'COMPLETED'),
  (158, '19880918APT001-005', '2º Triatlo de Ponte de Sor', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-09-18', '10:00:00', null, null, 'COMPLETED'),
  (159, '19880925APT001-001', 'Triatlo de Lagos', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-09-25', '13:00:00', null, null, 'COMPLETED'),
  (160, '19880925APT001-002', 'Triatlo de Lagos', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-09-25', '13:00:00', null, null, 'COMPLETED'),
  (161, '19880925APT001-003', 'Triatlo de Lagos', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-09-25', '13:00:00', null, null, 'COMPLETED'),
  (162, '19880925APT001-004', 'Triatlo de Lagos', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-09-25', '13:00:00', null, null, 'PLANNED'),
  (163, '19880925APT001-005', 'Triatlo de Lagos', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-09-25', '13:00:00', null, null, 'COMPLETED');


-- endurancetrio.triathlon_based_race table
-- ----------------------------------------
INSERT INTO
  endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule)
VALUES
  (107, null, 'UNKNOWN'),
  (108, null, 'UNKNOWN'),
  (109, null, 'UNKNOWN'),
  (110, null, 'UNKNOWN'),
  (111, null, 'UNKNOWN'),
  (112, null, 'UNKNOWN'),
  (113, null, 'UNKNOWN'),
  (114, null, 'UNKNOWN'),
  (115, null, 'UNKNOWN'),
  (116, null, 'UNKNOWN'),
  (117, null, 'UNKNOWN'),
  (118, null, 'UNKNOWN'),
  (119, null, 'UNKNOWN'),
  (120, null, 'UNKNOWN'),
  (121, null, 'UNKNOWN'),
  (122, null, 'UNKNOWN'),
  (123, null, 'UNKNOWN'),
  (124, null, 'UNKNOWN'),
  (125, null, 'UNKNOWN'),
  (126, null, 'UNKNOWN'),
  (127, null, 'UNKNOWN'),
  (128, null, 'UNKNOWN'),
  (129, null, 'UNKNOWN'),
  (130, null, 'UNKNOWN'),
  (131, null, 'UNKNOWN'),
  (132, null, 'UNKNOWN'),
  (133, null, 'UNKNOWN'),
  (134, null, 'UNKNOWN'),
  (135, null, 'UNKNOWN'),
  (136, null, 'UNKNOWN'),
  (137, null, 'UNKNOWN'),
  (138, null, 'UNKNOWN'),
  (144, null, 'UNKNOWN'),
  (145, null, 'UNKNOWN'),
  (146, null, 'UNKNOWN'),
  (147, null, 'UNKNOWN'),
  (148, null, 'UNKNOWN'),
  (149, null, 'UNKNOWN'),
  (150, null, 'UNKNOWN'),
  (151, null, 'UNKNOWN'),
  (152, null, 'UNKNOWN'),
  (153, null, 'UNKNOWN'),
  (154, null, 'UNKNOWN'),
  (155, null, 'UNKNOWN'),
  (156, null, 'UNKNOWN'),
  (157, null, 'UNKNOWN'),
  (158, null, 'UNKNOWN'),
  (159, null, 'UNKNOWN'),
  (160, null, 'UNKNOWN'),
  (161, null, 'UNKNOWN'),
  (162, null, 'UNKNOWN'),
  (163, null, 'UNKNOWN');


-- endurancetrio.course_race table
-- -------------------------------
INSERT INTO
  endurancetrio.course_race (course_id, race_id)
VALUES
  (25, 92),
  (25, 93),
  (25, 94),
  (25, 95),
  (25, 96),
  (26, 97),
  (26, 98),
  (26, 99),
  (26, 100),
  (26, 101),
  (27, 102),
  (27, 103),
  (27, 104),
  (27, 105),
  (27, 106),
  (28, 107),
  (28, 108),
  (28, 109),
  (29, 110),
  (29, 111),
  (29, 112),
  (30, 113),
  (30, 114),
  (30, 115),
  (31, 116),
  (31, 117),
  (31, 118),
  (31, 119),
  (31, 120),
  (32, 121),
  (32, 122),
  (32, 123),
  (32, 124),
  (32, 125),
  (33, 126),
  (33, 127),
  (33, 128),
  (34, 129),
  (34, 130),
  (34, 131),
  (34, 132),
  (34, 133),
  (35, 134),
  (35, 135),
  (35, 136),
  (36, 139),
  (36, 140),
  (36, 141),
  (37, 144),
  (37, 145),
  (37, 146),
  (37, 147),
  (38, 148),
  (38, 149),
  (38, 150),
  (38, 151),
  (38, 152),
  (38, 153),
  (39, 154),
  (39, 155),
  (39, 156),
  (39, 157),
  (39, 158),
  (40, 159),
  (40, 160),
  (40, 161),
  (40, 162),
  (40, 163);


-- endurancetrio.race_hierarchy table
-- ----------------------------------
INSERT INTO
  endurancetrio.race_hierarchy (race_id, parent_race_id)
VALUES
  (93, 92),
  (94, 92),
  (95, 93),
  (96, 94),
  (98, 97),
  (99, 97),
  (100, 98),
  (101, 99),
  (103, 102),
  (104, 102),
  (105, 103),
  (106, 104),
  (108, 107),
  (109, 107),
  (111, 110),
  (112, 110),
  (114, 113),
  (115, 113),
  (117, 116),
  (118, 116),
  (122, 121),
  (123, 121),
  (124, 122),
  (125, 123),
  (127, 126),
  (128, 126),
  (130, 129),
  (131, 129),
  (132, 130),
  (133, 131),
  (135, 134),
  (136, 134),
  (137, 135),
  (138, 136),
  (140, 139),
  (141, 139),
  (142, 140),
  (143, 141),
  (145, 144),
  (146, 144),
  (147, 145),
  (148, 146),
  (150, 149),
  (151, 149),
  (152, 150),
  (153, 151),
  (155, 154),
  (156, 154),
  (157, 155),
  (158, 156),
  (160, 159),
  (161, 159),
  (162, 160),
  (163, 161);


-- endurancetrio.results_file table
-- --------------------------------
INSERT INTO
  endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name)
VALUES
  (32, 92, 'I Biatlo de Porto de Mós', 'Geral', 1, true, '19880313APT001-001A-01.pdf'),
  (33, 98, '2º Biatlo do Porto', 'Escalões Femininos', 1, true, '19880402APT001-002B-01.pdf'),
  (34, 99, '2º Biatlo do Porto', 'Masculinos', 1, true, '19880402APT001-003A-01.pdf'),
  (35, 99, '2º Biatlo do Porto', 'Escalões Masculinos', 1, true, '19880402APT001-003B-01.pdf'),
  (36, 101, '2º Biatlo do Porto', 'Equipas Masculinas', 1, true, '19880402APT001-005A-01.pdf'),
  (37, 102, 'Biatlo Terras de Santa Maria', 'Geral', 1, true, '19880417APT001-001A-01.pdf'),
  (38, 106, 'Biatlo Terras de Santa Maria', 'Equipas masculinas', 1, true, '19880417APT001-005A-01.pdf'),
  (39, 107, 'I Triatlo de Loulé', 'Geral', 1, true, '19880515APT001-001A-01.pdf'),
  (40, 110, 'Campeonato Nacional de Triatlo Cat. B', 'Geral', 1, true, '19880522APT001-001A-01.pdf'),
  (41, 113, 'Triatlo do Ambiente', 'Geral', 1, true, '19880605APT001-001A-01.pdf'),
  (42, 116, 'Triatlo Internacional de Cascais 1988', 'Geral', 1, true, '19880612APT001-001A-01.pdf'),
  (43, 121, '1º Triatlo Promoção de Almodôvar', 'Geral', 1, true, '19880626APT001-001A-01.pdf'),
  (44, 124, '1º Triatlo Promoção de Almodôvar', 'Equipas Masculinas', 1, true, '19880626APT001-004A-01.pdf'),
  (45, 126, 'Triatlo do Porto', 'Geral', 1, true, '19880703APT001-001A-01.pdf'),
  (46, 129, 'Triatlo da Costa Azul', 'Geral', 1, true, '19880716APT001-001A-01.pdf'),
  (47, 134, 'II Triatlo do Coimbrão', 'Geral', 1, true, '19880806APT001-001A-01.pdf'),
  (48, 139, 'I Biatlo de Arruda dos Vinhos', 'Geral', 1, true, '19880814APT001-001A-01.pdf'),
  (49, 144, 'V Triatlo de Peniche', 'Geral', 1, true, '19880815APT001-001A-01.pdf'),
  (50, 148, 'V Triatlo de Peniche', 'Equipas Masculinas', 1, true, '19880815APT001-005A-01.pdf'),
  (51, 149, '1º Triatlo de Óbidos', 'Geral', 1, true, '19880904APT001-001A-01.pdf'),
  (52, 153, '1º Triatlo de Óbidos', 'Equipas Masculinas', 1, true, '19880904APT001-005A-01.pdf'),
  (53, 154, '2º Triatlo de Ponte de Sor', 'Geral', 1, true, '19880918APT001-001A-01.pdf'),
  (54, 155, '2º Triatlo de Ponte de Sor', 'Escalões Femininos', 1, true, '19880918APT001-002B-01.pdf'),
  (55, 156, '2º Triatlo de Ponte de Sor', 'Escalões Masculinos', 1, true, '19880918APT001-003B-01.pdf'),
  (56, 158, '2º Triatlo de Ponte de Sor', 'Equipas Masculinas', 1, true, '19880918APT001-005A-01.pdf'),
  (57, 159, 'Triatlo de Lagos', 'Geral', 1, true, '19880925APT001-001A-01.pdf'),
  (58, 163, 'Triatlo de Lagos', 'Equipas Masculinas', 1, true, '19880925APT001-005A-01.pdf');
