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

-- Description: Insert the data of the 1987 triathlon related events in Portugal

-- endurancetrio.event table
-- -------------------------
INSERT INTO
  endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district)
VALUES
  (9, '19870111APT001', 'I Duplo Biatlo de Tomar', '1987-01-11', '1987-01-11', 'Tomar', 'Tomar', 'Santarém'),
  (10, '19870118APT001', 'I Triatlo Experimental de Braga', '1987-01-18', '1987-01-18', 'Braga', 'Braga', 'Braga'),
  (11, '19870315APT001', 'I Biatlo de Torres Novas', '1987-03-15', '1987-03-15', 'Torres Novas', 'Torres Novas', 'Santarém'),
  (12, '19870329APT001', 'I Biatlo do Norte', '1987-03-29', '1987-03-29', 'Braga', 'Braga', 'Braga'),
  (13, '19870418APT001', '1º Biatlo do Concelho de Loures', '1987-04-18', '1987-04-18', 'Loures', 'Loures', 'Lisboa'),
  (14, '19870502APT001', 'Biatlo do Porto', '1987-05-02', '1987-05-02', 'Porto', 'Porto', 'Porto'),
  (15, '19870517APT001', 'Triatlo da Costa Azul', '1987-05-17', '1987-05-17', 'Tróia', 'Setúbal', 'Setúbal'),
  (16, '19870614APT001', 'Triatlo Internacional de Cascais 1987', '1987-06-14', '1987-06-14', 'Cascais', 'Cascais', 'Lisboa'),
  (17, '19870712APT001', '1º Triatlo Lions Torres Novas', '1987-07-12', '1987-07-12', 'Torres Novas', 'Torres Novas', 'Santarém'),
  (18, '19870726APT001', 'I Triatlo de Coimbrão', '1987-07-26', '1987-07-26', 'Coimbrão', 'Leiria', 'Leiria'),
  (19, '19870815APT001', 'IV Triatlo de Peniche', '1987-08-15', '1987-08-15', 'Peniche', 'Peniche', 'Leiria'),
  (20, '19870830APT001', 'I Triatlo de Ponte de Sor', '1987-08-30', '1987-08-30', 'Ponte de Sor', 'Ponte de Sor', 'Portalegre'),
  (21, '19870920APT001', 'I Triatlo da Associação de Comandos Porto', '1987-09-20', '1987-09-20', 'Porto', 'Porto', 'Porto'),
  (22, '19871004APT001', '1º Triatlo de Lagos', '1987-10-04', '1987-10-04', 'Lagos', 'Lagos', 'Faro'),
  (23, '19871018APT001', 'I Triatlo de Grândola', '1987-10-18', '1987-10-18', 'Grândola', 'Grândola', 'Setúbal');


-- endurancetrio.organizer table
-- -----------------------------
INSERT INTO
  endurancetrio.organizer (id, name, city, county, district, organizer_type)
VALUES
  (7, 'Clube de Actividades de Lazer e Manutenção', 'Tomar', 'Tomar', 'Santarém', 'CLUB'),
  (8, 'Câmara Municipal de Braga', 'Braga', 'Braga', 'Braga', 'PUBLIC'),
  (9, 'Clube Desportivo de Torres Novas', 'Torres Novas', 'Torres Novas', 'Santarém', 'CLUB'),
  (10, 'Centro de Cultura e Desporto do Pessoal da Câmara Municipal e Serviços Municipalizados de Loures', 'Loures', 'Loures', 'Lisboa', 'PUBLIC'),
  (11, 'Comissão Organizadora do Biatlo do Porto', 'Porto', 'Porto', 'Porto', 'PRIVATE'),
  (12, 'Associação Portuguesa de Triatlo', 'Loures', 'Loures', 'Lisboa', 'PRIVATE'),
  (13, 'Câmara Municipal de Setúbal', 'Setúbal', 'Setúbal', 'Setúbal', 'PUBLIC'),
  (14, 'Lions Club de Torres Novas', 'Torres Novas', 'Santarém', 'Santarém', 'CLUB'),
  (15, 'Comissão de Festas do Coimbrão', 'Coimbrão', 'Leiria', 'Leiria', 'CLUB'),
  (16, 'Câmara Municipal de Ponte de Sor', 'Ponte de Sôr', 'Ponte de Sor', 'Portalegre', 'PUBLIC'),
  (17, 'Associação de Comandos', 'Santa Maria da Feira', 'Santa Maria da Feira', 'Aveiro', 'CLUB'),
  (18, 'Junta de Freguesia de Santa Maria', 'Lagos', 'Lagos', 'Faro', 'PUBLIC'),
  (19, 'Núcleo de Amigos do Atletismo de Lagos', 'Lagos', 'Lagos', 'Faro', 'CLUB'),
  (20, 'Câmara Municipal de Grândola', 'Grândola', 'Grândola', 'Setúbal', 'PUBLIC'),
  (21, 'Clube de Ténis de Grândola', 'Grândola', 'Grândola', 'Setúbal', 'CLUB');


-- endurancetrio.event_organizer table
-- -----------------------------------
INSERT INTO
  endurancetrio.event_organizer (event_id, organizer_id)
VALUES
  (9, 7),
  (10, 8),
  (11, 9),
  (12, 8),
  (13, 10),
  (14, 11),
  (15, 12),
  (15, 13),
  (16, 3),
  (17, 14),
  (18, 15),
  (19, 2),
  (20, 16),
  (21, 17),
  (22, 18),
  (22, 19),
  (23, 20),
  (23, 21);


-- endurancetrio.event_file table
-- ------------------------------
INSERT INTO
  endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type)
VALUES
  (16, 9, 'Cartaz', 1, true, '19870111APT001-IMG001-01.png', 'POSTER'),
  (17, 9, 'Regulamento', 1, true, '19870111APT001-REG001-01.pdf', 'RULES'),
  (18, 12, 'Cartaz', 1, true, '19870329APT001-IMG001-01.png', 'POSTER'),
  (19, 13, 'Cartaz', 1, true, '19870418APT001-IMG001-01.png', 'POSTER'),
  (20, 13, 'Regulamento', 1, true, '19870418APT001-REG001-01.pdf', 'RULES'),
  (21, 13, 'Percursos', 1, true, '19870418APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (22, 14, 'Regulamento', 1, true, '19870502APT001-REG001-01.pdf', 'RULES'),
  (23, 14, 'Percursos da prova', 1, true, '19870502APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (24, 15, 'Cartaz', 1, true, '19870517APT001-IMG001-01.png', 'POSTER'),
  (25, 15, 'Regulamento', 1, true, '19870517APT001-REG001-01.pdf', 'RULES'),
  (26, 15, 'Percursos', 1, true, '19870517APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (27, 16, 'Cartaz', 1, true, '19870614APT001-IMG001-01.jpg', 'POSTER'),
  (28, 16, 'Regulamento', 1, true, '19870614APT001-REG001-01.pdf', 'RULES'),
  (29, 16, 'Percursos', 1, true, '19870614APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (30, 17, 'Cartaz', 1, true, '19870712APT001-IMG001-01.png', 'POSTER'),
  (31, 17, 'Regulamento', 1, true, '19870712APT001-REG001-01.pdf', 'RULES'),
  (32, 17, 'Percursos', 1, true, '19870712APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (33, 18, 'Cartaz', 1, true, '19870726APT001-IMG001-01.png', 'POSTER'),
  (34, 18, 'Regulamento', 1, true, '19870726APT001-REG001-01.pdf', 'RULES'),
  (35, 18, 'Percursos', 1, true, '19870726APT001-MAP001-01.pdf', 'RULES'),
  (36, 19, 'Cartaz', 1, true, '19870815APT001-IMG001-01.png', 'POSTER'),
  (37, 19, 'Regulamento', 1, true, '19870815APT001-REG001-01.pdf', 'RULES'),
  (38, 20, 'Cartaz', 1, true, '19870830APT001-IMG001-01.png', 'POSTER'),
  (39, 20, 'Regulamento', 1, true, '19870830APT001-REG001-01.pdf', 'RULES'),
  (40, 20, 'Percursos', 1, true, '19870830APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (41, 21, 'Cartaz', 1, true, '19870920APT001-IMG001-01.png', 'POSTER'),
  (42, 21, 'Regulamento', 1, true, '19870920APT001-REG001-01.pdf', 'RULES'),
  (43, 21, 'Percursos', 1, true, '19870920APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (44, 22, 'Cartaz', 1, true, '19871004APT001-IMG001-01.png', 'POSTER'),
  (45, 22, 'Regulamento', 1, true, '19871004APT001-REG001-01.pdf', 'RULES'),
  (46, 22, 'Percursos', 1, true, '19871004APT001-MAP001-01.pdf', 'COURSE_MAPS'),
  (47, 23, 'Cartaz', 1, true, '19871018APT001-IMG001-01.png', 'POSTER'),
  (48, 23, 'Regulamento', 1, true, '19871018APT001-REG001-01.pdf', 'RULES');


-- endurancetrio.distance table
-- ----------------------------
INSERT INTO
  endurancetrio.distance (id, distance_type)
VALUES
  (9, 'SPRINT'),
  (10, 'SPRINT'),
  (11, 'STANDARD'),
  (12, 'STANDARD'),
  (13, 'STANDARD'),
  (14, 'MIDDLE_DISTANCE'),
  (15, 'STANDARD'),
  (16, 'STANDARD'),
  (17, 'SPRINT'),
  (18, 'STANDARD'),
  (19, 'STANDARD'),
  (20, 'STANDARD'),
  (21, 'STANDARD'),
  (22, 'STANDARD'),
  (23, 'STANDARD'),
  (24, 'STANDARD');


-- endurancetrio.biathlon_distance table
-- -------------------------------------
INSERT INTO
  endurancetrio.biathlon_distance (id, bike_distance, bike_laps, run_distance, run_laps)
VALUES
  (11, 50000, 1, 10000, 1),
  (12, 40000, 1, 10000, 1),
  (13, 37000, 1, 8000, 1),
  (14, 66000, 1, 16000, 1);


-- endurancetrio.double_biathlon_distance table
-- --------------------------------------------
INSERT INTO
  endurancetrio.double_biathlon_distance (id, first_bike_distance, first_bike_laps, first_run_distance, first_run_laps, second_bike_distance, second_bike_laps, second_run_distance, second_run_laps)
VALUES
  (9, 15000, 1, 5000, 1, 15000, 1, 5000, 1);


-- endurancetrio.triathlon_distance table
-- --------------------------------------
INSERT INTO
  endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps)
VALUES
  (10, 250, 3, 20000, 1, 5000, 1),
  (15, 800, 1, 35000, 1, 8000, 1),
  (16, 1000, 1, 44000, 1, 11000, 1),
  (17, 500, 1, 19000, 1, 5000, 1),
  (18, 1000, 1, 45000, 1, 10000, 1),
  (19, 900, 1, 40000, 1, 9000, 1),
  (20, 800, 1, 35000, 1, 8000, 1),
  (21, 1000, 1, 45000, 1, 10000, 1),
  (22, 1000, 1, 45000, 1, 10000, 1),
  (23, 1000, 1, 46000, 1, 10000, 1),
  (24, 1000, 1, 48000, 1, 10000, 1);


-- endurancetrio.course table
-- --------------------------
INSERT INTO
  endurancetrio.course (id, event_id, title, sport, distance_id)
VALUES
  (9, 9, 'Duplo Biatlo Sprint', 'DOUBLE_BIATHLON', 9),
  (10, 10, 'Triatlo Sprint', 'TRIATHLON', 10),
  (11, 11, 'Biatlo Standard', 'BIATHLON', 11),
  (12, 12, 'Biatlo Standard', 'BIATHLON', 12),
  (13, 13, 'Biatlo Standard', 'BIATHLON', 13),
  (14, 14, 'Biatlo Média Distância', 'BIATHLON', 14),
  (15, 15, 'Triatlo Standard', 'TRIATHLON', 15),
  (16, 16, 'Triatlo Standard', 'TRIATHLON', 16),
  (17, 16, 'Triatlo Sprint', 'TRIATHLON', 17),
  (18, 17, 'Triatlo Standard', 'TRIATHLON', 18),
  (19, 18, 'Triatlo Standard', 'TRIATHLON', 19),
  (20, 19, 'Triatlo Standard', 'TRIATHLON', 20),
  (21, 20, 'Triatlo Standard', 'TRIATHLON', 21),
  (22, 21, 'Triatlo Standard', 'TRIATHLON', 22),
  (23, 22, 'Triatlo Standard', 'TRIATHLON', 23),
  (24, 23, 'Triatlo Standard', 'TRIATHLON', 24);


-- endurancetrio.race table
-- ------------------------
INSERT INTO
  endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status)
VALUES
  (22, '19870111APT001-001', 'I Duplo Biatlo de Tomar', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-01-11', '11:00:00', null, null, 'COMPLETED'),
  (23, '19870111APT001-002', 'I Duplo Biatlo de Tomar', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-01-11', '11:00:00', null, null, 'COMPLETED'),
  (24, '19870111APT001-003', 'I Duplo Biatlo de Tomar', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-01-11', '11:00:00', null, null, 'COMPLETED'),
  (25, '19870118APT001-001', 'I Triatlo Experimental de Braga', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-01-18', '10:00:00', null, null, 'COMPLETED'),
  (26, '19870118APT001-002', 'I Triatlo Experimental de Braga', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-01-18', '10:00:00', null, null, 'COMPLETED'),
  (27, '19870118APT001-003', 'I Triatlo Experimental de Braga', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-01-18', '10:00:00', null, null, 'COMPLETED'),
  (28, '19870315APT001-001', 'I Biatlo de Torres Novas', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-03-15', '10:00:00', null, null, 'COMPLETED'),
  (29, '19870315APT001-002', 'I Biatlo de Torres Novas', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-03-15', '10:00:00', null, null, 'PLANNED'),
  (30, '19870315APT001-003', 'I Biatlo de Torres Novas', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-03-15', '10:00:00', null, null, 'COMPLETED'),
  (31, '19870329APT001-001', 'I Biatlo do Norte', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-03-29', '13:00:00', null, null, 'COMPLETED'),
  (32, '19870329APT001-002', 'I Biatlo do Norte', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-03-29', '13:00:00', null, null, 'COMPLETED'),
  (33, '19870329APT001-003', 'I Biatlo do Norte', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-03-29', '13:00:00', null, null, 'COMPLETED'),
  (34, '19870418APT001-001', '1º Biatlo do Concelho de Loures', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-04-18', '15:00:00', null, null, 'COMPLETED'),
  (35, '19870418APT001-002', '1º Biatlo do Concelho de Loures', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-04-18', '15:00:00', null, null, 'COMPLETED'),
  (36, '19870418APT001-003', '1º Biatlo do Concelho de Loures', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-04-18', '15:00:00', null, null, 'COMPLETED'),
  (37, '19870418APT001-004', '1º Biatlo do Concelho de Loures', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_RANK', '1987-04-18', '15:00:00', null, null, 'PLANNED'),
  (38, '19870418APT001-005', '1º Biatlo do Concelho de Loures', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_RANK', '1987-04-18', '15:00:00', null, null, 'COMPLETED'),
  (39, '19870502APT001-001', 'Biatlo do Porto', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-05-02', '13:00:00', null, null, 'COMPLETED'),
  (40, '19870502APT001-002', 'Biatlo do Porto', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-05-02', '13:00:00', null, null, 'COMPLETED'),
  (41, '19870502APT001-003', 'Biatlo do Porto', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-05-02', '13:00:00', null, null, 'COMPLETED'),
  (42, '19870502APT001-004', 'Biatlo do Porto', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-05-02', '13:00:00', null, null, 'PLANNED'),
  (43, '19870502APT001-005', 'Biatlo do Porto', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-05-02', '13:00:00', null, null, 'COMPLETED'),
  (44, '19870517APT001-001', 'Triatlo da Costa Azul', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-05-17', '10:00:00', null, null, 'COMPLETED'),
  (45, '19870517APT001-002', 'Triatlo da Costa Azul', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-05-17', '10:00:00', null, null, 'COMPLETED'),
  (46, '19870517APT001-003', 'Triatlo da Costa Azul', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-05-17', '10:00:00', null, null, 'COMPLETED'),
  (47, '19870517APT001-004', 'Triatlo da Costa Azul', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-05-17', '10:00:00', null, null, 'PLANNED'),
  (48, '19870517APT001-005', 'Triatlo da Costa Azul', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-05-17', '10:00:00', null, null, 'COMPLETED'),
  (49, '19870614APT001-001', 'Triatlo Internacional de Cascais 1987', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-06-14', '13:00:00', null, null, 'COMPLETED'),
  (50, '19870614APT001-002', 'Triatlo Internacional de Cascais 1987', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-06-14', '13:00:00', null, null, 'COMPLETED'),
  (51, '19870614APT001-003', 'Triatlo Internacional de Cascais 1987', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-06-14', '13:00:00', null, null, 'COMPLETED'),
  (52, '19870614APT001-004', 'Triatlo Internacional de Cascais 1987', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-06-14', '13:00:00', null, null, 'COMPLETED'),
  (53, '19870614APT001-005', 'Triatlo Internacional de Cascais 1987', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-06-14', '13:00:00', null, null, 'COMPLETED'),
  (54, '19870614APT001-006', 'Prova de Promoção', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-06-14', '13:00:00', null, null, 'COMPLETED'),
  (55, '19870614APT001-007', 'Prova de Promoção', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-06-14', '13:00:00', null, null, 'COMPLETED'),
  (56, '19870614APT001-008', 'Prova de Promoção', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-06-14', '13:00:00', null, null, 'COMPLETED'),
  (57, '19870712APT001-001', '1º Triatlo Lions Torres Novas', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-07-12', '12:00:00', null, null, 'COMPLETED'),
  (58, '19870712APT001-002', '1º Triatlo Lions Torres Novas', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-07-12', '12:00:00', null, null, 'COMPLETED'),
  (59, '19870712APT001-003', '1º Triatlo Lions Torres Novas', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-07-12', '12:00:00', null, null, 'COMPLETED'),
  (60, '19870712APT001-004', '1º Triatlo Lions Torres Novas', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-07-12', '12:00:00', null, null, 'PLANNED'),
  (61, '19870712APT001-005', '1º Triatlo Lions Torres Novas', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-07-12', '12:00:00', null, null, 'COMPLETED'),
  (62, '19870726APT001-001', 'I Triatlo de Coimbrão', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-07-26', '15:30:00', null, null, 'COMPLETED'),
  (63, '19870726APT001-002', 'I Triatlo de Coimbrão', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-07-26', '15:30:00', null, null, 'COMPLETED'),
  (64, '19870726APT001-003', 'I Triatlo de Coimbrão', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-07-26', '15:30:00', null, null, 'COMPLETED'),
  (65, '19870726APT001-004', 'I Triatlo de Coimbrão', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-07-26', '15:30:00', null, null, 'PLANNED'),
  (66, '19870726APT001-005', 'I Triatlo de Coimbrão', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-07-26', '15:30:00', null, null, 'COMPLETED'),
  (67, '19870815APT001-001', 'IV Triatlo de Peniche', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-08-15', '10:00:00', null, null, 'COMPLETED'),
  (68, '19870815APT001-002', 'IV Triatlo de Peniche', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-08-15', '10:00:00', null, null, 'COMPLETED'),
  (69, '19870815APT001-003', 'IV Triatlo de Peniche', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-08-15', '10:00:00', null, null, 'COMPLETED'),
  (70, '19870815APT001-004', 'IV Triatlo de Peniche', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-08-15', '10:00:00', null, null, 'PLANNED'),
  (71, '19870815APT001-005', 'IV Triatlo de Peniche', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-08-15', '10:00:00', null, null, 'COMPLETED'),
  (72, '19870830APT001-001', 'I Triatlo de Ponte de Sor', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-08-30', '16:00:00', null, null, 'COMPLETED'),
  (73, '19870830APT001-002', 'I Triatlo de Ponte de Sor', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-08-30', '16:00:00', null, null, 'COMPLETED'),
  (74, '19870830APT001-003', 'I Triatlo de Ponte de Sor', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-08-30', '16:00:00', null, null, 'COMPLETED'),
  (75, '19870830APT001-004', 'I Triatlo de Ponte de Sor', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-08-30', '16:00:00', null, null, 'COMPLETED'),
  (76, '19870830APT001-005', 'I Triatlo de Ponte de Sor', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-08-30', '16:00:00', null, null, 'COMPLETED'),
  (77, '19870920APT001-001', 'I Triatlo da Associação de Comandos Porto', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-09-20', '11:00:00', null, null, 'COMPLETED'),
  (78, '19870920APT001-002', 'I Triatlo da Associação de Comandos Porto', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-09-20', '11:00:00', null, null, 'COMPLETED'),
  (79, '19870920APT001-003', 'I Triatlo da Associação de Comandos Porto', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-09-20', '11:00:00', null, null, 'COMPLETED'),
  (80, '19870920APT001-004', 'I Triatlo da Associação de Comandos Porto', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-09-20', '11:00:00', null, null, 'COMPLETED'),
  (81, '19870920APT001-005', 'I Triatlo da Associação de Comandos Porto', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-09-20', '11:00:00', null, null, 'COMPLETED'),
  (82, '19871004APT001-001', '1º Triatlo de Lagos', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-10-04', '12:30:00', null, null, 'COMPLETED'),
  (83, '19871004APT001-002', '1º Triatlo de Lagos', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-10-04', '12:30:00', null, null, 'COMPLETED'),
  (84, '19871004APT001-003', '1º Triatlo de Lagos', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-10-04', '12:30:00', null, null, 'COMPLETED'),
  (85, '19871004APT001-004', '1º Triatlo de Lagos', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-10-04', '12:30:00', null, null, 'COMPLETED'),
  (86, '19871004APT001-005', '1º Triatlo de Lagos', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-10-04', '12:30:00', null, null, 'COMPLETED'),
  (87, '19871018APT001-001', 'I Triatlo de Grândola', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-10-18', '11:00:00', null, null, 'COMPLETED'),
  (88, '19871018APT001-002', 'I Triatlo de Grândola', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-10-18', '11:00:00', null, null, 'COMPLETED'),
  (89, '19871018APT001-003', 'I Triatlo de Grândola', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-10-18', '11:00:00', null, null, 'COMPLETED'),
  (90, '19871018APT001-004', 'I Triatlo de Grândola', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-10-18', '11:00:00', null, null, 'PLANNED'),
  (91, '19871018APT001-005', 'I Triatlo de Grândola', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-10-18', '11:00:00', null, null, 'COMPLETED');


-- endurancetrio.triathlon_based_race table
-- ----------------------------------------
INSERT INTO
  endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule)
VALUES
  (44, null, 'UNKNOWN'),
  (45, null, 'UNKNOWN'),
  (46, null, 'UNKNOWN'),
  (47, null, 'UNKNOWN'),
  (48, null, 'UNKNOWN'),
  (49, null, 'UNKNOWN'),
  (50, null, 'UNKNOWN'),
  (51, null, 'UNKNOWN'),
  (52, null, 'UNKNOWN'),
  (53, null, 'UNKNOWN'),
  (54, null, 'UNKNOWN'),
  (55, null, 'UNKNOWN'),
  (56, null, 'UNKNOWN'),
  (57, null, 'UNKNOWN'),
  (58, null, 'UNKNOWN'),
  (59, null, 'UNKNOWN'),
  (60, null, 'UNKNOWN'),
  (61, null, 'UNKNOWN'),
  (62, null, 'UNKNOWN'),
  (63, null, 'UNKNOWN'),
  (64, null, 'UNKNOWN'),
  (65, null, 'UNKNOWN'),
  (66, null, 'UNKNOWN'),
  (67, null, 'UNKNOWN'),
  (68, null, 'UNKNOWN'),
  (69, null, 'UNKNOWN'),
  (70, null, 'UNKNOWN'),
  (71, null, 'UNKNOWN'),
  (72, null, 'UNKNOWN'),
  (73, null, 'UNKNOWN'),
  (74, null, 'UNKNOWN'),
  (75, null, 'UNKNOWN'),
  (76, null, 'UNKNOWN'),
  (77, null, 'UNKNOWN'),
  (78, null, 'UNKNOWN'),
  (79, null, 'UNKNOWN'),
  (80, null, 'UNKNOWN'),
  (81, null, 'UNKNOWN'),
  (82, null, 'UNKNOWN'),
  (83, null, 'UNKNOWN'),
  (84, null, 'UNKNOWN'),
  (85, null, 'UNKNOWN'),
  (86, null, 'UNKNOWN'),
  (87, null, 'UNKNOWN'),
  (88, null, 'UNKNOWN'),
  (89, null, 'UNKNOWN'),
  (90, null, 'UNKNOWN'),
  (91, null, 'UNKNOWN');


-- endurancetrio.course_race table
-- -------------------------------
INSERT INTO
  endurancetrio.course_race (course_id, race_id)
VALUES
  (9, 22),
  (9, 23),
  (9, 24),
  (10, 25),
  (10, 26),
  (10, 27),
  (11, 28),
  (11, 29),
  (11, 30),
  (12, 31),
  (12, 32),
  (12, 33),
  (13, 34),
  (13, 35),
  (13, 36),
  (13, 37),
  (13, 38),
  (14, 39),
  (14, 40),
  (14, 41),
  (14, 42),
  (14, 43),
  (15, 44),
  (15, 45),
  (15, 46),
  (15, 47),
  (15, 48),
  (16, 49),
  (16, 50),
  (16, 51),
  (16, 52),
  (16, 53),
  (17, 54),
  (17, 55),
  (17, 56),
  (18, 57),
  (18, 58),
  (18, 59),
  (18, 60),
  (18, 61),
  (19, 62),
  (19, 63),
  (19, 64),
  (19, 65),
  (19, 66),
  (20, 67),
  (20, 68),
  (20, 69),
  (21, 72),
  (21, 73),
  (21, 74),
  (21, 75),
  (21, 76),
  (22, 77),
  (22, 78),
  (22, 79),
  (22, 80),
  (22, 81),
  (23, 82),
  (23, 83),
  (23, 84),
  (23, 85),
  (23, 86),
  (24, 87),
  (24, 88),
  (24, 89),
  (24, 90),
  (24, 91);


-- endurancetrio.race_hierarchy table
-- ----------------------------------
INSERT INTO
  endurancetrio.race_hierarchy (race_id, parent_race_id)
VALUES
  (23, 22),
  (24, 22),
  (26, 25),
  (27, 25),
  (29, 28),
  (30, 28),
  (32, 31),
  (33, 31),
  (35, 34),
  (36, 34),
  (37, 35),
  (38, 36),
  (40, 39),
  (41, 39),
  (42, 40),
  (43, 41),
  (45, 44),
  (46, 44),
  (47, 45),
  (48, 46),
  (50, 49),
  (51, 49),
  (52, 50),
  (53, 51),
  (55, 54),
  (56, 54),
  (58, 57),
  (59, 57),
  (60, 58),
  (61, 59),
  (63, 62),
  (64, 62),
  (65, 63),
  (66, 64),
  (68, 67),
  (69, 67),
  (70, 68),
  (71, 69),
  (73, 72),
  (74, 72),
  (75, 73),
  (76, 74),
  (78, 77),
  (79, 77),
  (80, 78),
  (81, 79),
  (83, 82),
  (84, 82),
  (85, 82),
  (86, 82),
  (88, 87),
  (89, 87),
  (90, 88),
  (91, 89);


-- endurancetrio.results_file table
-- --------------------------------
INSERT INTO
  endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name)
VALUES
  (13, 22, 'I Duplo Biatlo de Tomar', 'Geral', 1, true, '19870111APT001-001A-01.pdf'),
  (14, 25, 'I Triatlo Experimental de Braga', 'Geral', 1, true, '19870118APT001-001A-01.pdf'),
  (15, 28, 'I Biatlo de Torres Novas', 'Geral', 1, true, '19870315APT001-001A-01.pdf'),
  (16, 34, '1º Biatlo do Concelho de Loures', 'Geral', 1, true, '19870418APT001-001A-01.pdf'),
  (17, 38, '1º Biatlo do Concelho de Loures', 'Equipas Masculinas', 1, true, '19870418APT001-005A-01.pdf'),
  (18, 39, 'Biatlo do Porto', 'Geral', 1, true, '19870502APT001-001A-01.pdf'),
  (19, 44, 'Triatlo da Costa Azul Tróia', 'Geral', 1, true, '19870517APT001-001A-01.pdf'),
  (20, 49, 'Triatlo Internacional de Cascais 1987', 'Geral', 1, true, '19870614APT001-001A-01.pdf'),
  (21, 57, '1º Triatlo Lions Torres Novas', 'Geral', 1, true, '19870712APT001-001A-01.pdf'),
  (22, 62, 'I Triatlo de Coimbrão', 'Geral', 1, true, '19870726APT001-001A-01.pdf'),
  (23, 63, 'I Triatlo de Coimbrão', 'Escalões Femininos', 1, true, '19870726APT001-002B-01.pdf'),
  (24, 66, 'I Triatlo de Coimbrão', 'Equipas Masculinas', 1, true, '19870726APT001-005A-01.pdf'),
  (25, 67, 'IV Triatlo de Peniche', 'Geral', 1, true, '19870815APT001-001A-01.pdf'),
  (26, 72, 'I Triatlo de Ponte de Sor', 'Geral', 1, true, '19870830APT001-001A-01.pdf'),
  (27, 77, 'I Triatlo da Associação de Comandos Porto', 'Geral', 1, true, '19870920APT001-001A-01.pdf'),
  (28, 82, '1º Triatlo de Lagos', 'Geral', 1, true, '19871004APT001-001A-01.pdf'),
  (29, 85, '1º Triatlo de Lagos', 'Equipas Femininas', 1, true, '19871004APT001-004A-01.pdf'),
  (30, 86, '1º Triatlo de Lagos', 'Equipas Masculinas', 1, true, '19871004APT001-005A-01.pdf'),
  (31, 87, 'I Triatlo de Grândola', 'Geral', 1, true, '19871018APT001-001A-01.pdf');
