--
-- Copyright (c) 2011-2025 Ricardo do Canto
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- Description: Insert the data of the 1986 triathlon related events in Portugal

-- endurancetrio.event table
-- -------------------------
INSERT INTO
  endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district)
VALUES
  (4, '19860531NAC001', 'Triatlo UNICEF Setúbal', '1986-05-31', '1986-05-31', 'Setúbal', 'Setúbal', 'Setúbal'),
  (5, '19860614NAC001', 'Triatlo de Cascais', '1986-06-14', '1986-06-14', 'Cascais', 'Cascais', 'Lisboa'),
  (6, '19860719NAC001', 'Triatlo de Espinho', '1986-07-19', '1986-07-19', 'Espinho', 'Espinho', 'Aveiro'),
  (7, '19860815NAC001', 'Triatlo de Peniche', '1986-08-15', '1986-08-15', 'Peniche', 'Peniche', 'Leiria'),
  (8, '19860913NAC001', 'Triatlo de Tancos', '1986-09-13', '1986-09-13', 'Santa Margarida', 'Vila Nova da Barquinha', 'Santarém');


-- endurancetrio.organizer table
-- -----------------------------
INSERT INTO
  endurancetrio.organizer (id, name, city, county, district, organizer_type)
VALUES
  (5, 'Organizador #5', 'Espinho', 'Espinho', 'Aveiro', 'PRIVATE'),
  (6, 'Pára-Clube Nacional "Os Bóinas Verdes"', 'Tancos', 'Santarém', 'Santarém', 'CLUB');


-- endurancetrio.event_organizer table
-- -----------------------------------
INSERT INTO
  endurancetrio.event_organizer (event_id, organizer_id)
VALUES
  (4, 3),
  (5, 3),
  (5, 4),
  (6, 5),
  (7, 2),
  (8, 6);


-- endurancetrio.event_file table
-- ------------------------------
INSERT INTO
  endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type)
VALUES
  (7, 4, 'Regulamento', 1, true, '19860531NAC001-REG001-01.pdf', 'RULES'),
  (8, 4, 'Percursos', 1, true, '19860531NAC001-MAP001-01.pdf', 'COURSE_MAPS'),
  (9, 5, 'Cartaz', 1, true, '19860614NAC001-IMG001-01.jpg', 'POSTER'),
  (10, 5, 'Regulamento', 1, true, '19860614NAC001-REG001-01.pdf', 'RULES'),
  (11, 5, 'Percursos', 1, true, '19860614NAC001-MAP001-01.pdf', 'COURSE_MAPS'),
  (12, 7, 'Cartaz', 1, true, '19860815NAC001-IMG001-01.pdf', 'POSTER'),
  (13, 7, 'Regulamento', 1, true, '19860815NAC001-REG001-01.pdf', 'RULES'),
  (14, 8, 'Regulamento', 1, true, '19860913NAC001-REG001-01.pdf', 'RULES'),
  (15, 8, 'Percursos', 1, true, '19860913NAC001-MAP001-01.pdf', 'COURSE_MAPS');


-- endurancetrio.distance table
-- ----------------------------
INSERT INTO
  endurancetrio.distance (id, distance_type)
VALUES
  (4, 'STANDARD'),
  (5, 'STANDARD'),
  (7, 'STANDARD'),
  (8, 'STANDARD');


-- endurancetrio.triathlon_distance table
-- --------------------------------------
INSERT INTO
  endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps)
VALUES
  (4, 1000, 1, 40000, 1, 10000, 1),
  (5, 1000, 1, 45000, 1, 10000, 1),
  (7, 900, 1, 35000, 1, 8000, 1),
  (8, 1000, 1, 45000, 1, 10000, 1);


-- endurancetrio.course table
-- --------------------------
INSERT INTO
  endurancetrio.course (id, event_id, title, sport, distance_id)
VALUES
  (4, 4, 'Triatlo Standard', 'TRIATHLON', 4),
  (5, 5, 'Triatlo Standard', 'TRIATHLON', 5),
  (6, 6, 'Triatlo', 'TRIATHLON', null),
  (7, 7, 'Triatlo Standard', 'TRIATHLON', 7),
  (8, 8, 'Triatlo Standard', 'TRIATHLON', 8);


-- endurancetrio.race table
-- ------------------------
INSERT INTO
  endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status)
VALUES
  (10, '19860531NAC001-001', 'Triatlo UNICEF Setúbal', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1986-05-31', '13:00:00', null, null, 'COMPLETED'),
  (11, '19860531NAC001-002', 'Triatlo UNICEF Setúbal', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1986-05-31', '13:00:00', null, null, 'COMPLETED'),
  (12, '19860531NAC001-003', 'Triatlo UNICEF Setúbal', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1986-05-31', '13:00:00', null, null, 'COMPLETED'),
  (13, '19860614NAC001-001', 'Triatlo de Cascais', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1986-06-14', '13:00:00', null, null, 'COMPLETED'),
  (14, '19860614NAC001-002', 'Triatlo de Cascais', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1986-06-14', '13:00:00', null, null, 'COMPLETED'),
  (15, '19860614NAC001-003', 'Triatlo de Cascais', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1986-06-14', '13:00:00', null, null, 'COMPLETED'),
  (16, '19860815NAC001-001', 'Triatlo de Peniche', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1986-08-15', '15:00:00', null, null, 'COMPLETED'),
  (17, '19860815NAC001-002', 'Triatlo de Peniche', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1986-08-15', '15:00:00', null, null, 'COMPLETED'),
  (18, '19860815NAC001-003', 'Triatlo de Peniche', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1986-08-15', '15:00:00', null, null, 'COMPLETED'),
  (19, '19860913NAC001-001', 'Triatlo de Tancos', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1986-09-13', '13:00:00', null, null, 'COMPLETED'),
  (20, '19860913NAC001-002', 'Triatlo de Tancos', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1986-09-13', '13:00:00', null, null, 'COMPLETED'),
  (21, '19860913NAC001-003', 'Triatlo de Tancos', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1986-09-13', '13:00:00', null, null, 'COMPLETED');


-- endurancetrio.triathlon_based_race table
-- ----------------------------------------
INSERT INTO
  endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule)
VALUES
  (10, null, 'UNKNOWN'),
  (11, null, 'UNKNOWN'),
  (12, null, 'UNKNOWN'),
  (13, null, 'UNKNOWN'),
  (14, null, 'UNKNOWN'),
  (15, null, 'UNKNOWN'),
  (16, null, 'UNKNOWN'),
  (17, null, 'UNKNOWN'),
  (18, null, 'UNKNOWN'),
  (19, null, 'UNKNOWN'),
  (20, null, 'UNKNOWN'),
  (21, null, 'UNKNOWN');


-- endurancetrio.course_race table
-- -------------------------------
INSERT INTO
  endurancetrio.course_race (course_id, race_id)
VALUES
  (4, 10),
  (4, 11),
  (4, 12),
  (5, 13),
  (5, 14),
  (5, 15),
  (6, 16),
  (6, 17),
  (6, 18),
  (7, 19),
  (7, 20),
  (7, 21);


-- endurancetrio.race_hierarchy table
-- ----------------------------------
INSERT INTO
  endurancetrio.race_hierarchy (race_id, parent_race_id)
VALUES
  (10, 10),
  (11, 10),
  (13, 13),
  (14, 13),
  (16, 16),
  (17, 16),
  (19, 19),
  (20, 19);


-- endurancetrio.results_file table
-- --------------------------------
INSERT INTO
  endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name)
VALUES
  (9, 10, 'Triatlo UNICEF Setúbal', 'Geral', 1, true, '19860531NAC001-001A-01.pdf'),
  (10, 13, 'Triatlo de Cascais', 'Geral', 1, true, '19860614NAC001-001A-01.pdf'),
  (11, 16, 'Triatlo de Peniche', 'Geral', 1, true, '19860815NAC001-001A-01.pdf'),
  (12, 19, 'Triatlo de Tancos', 'Geral', 1, true, '19860913NAC001-001A-01.pdf');
