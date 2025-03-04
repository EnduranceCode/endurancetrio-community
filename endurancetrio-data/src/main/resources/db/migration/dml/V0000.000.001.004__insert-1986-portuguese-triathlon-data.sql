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
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (4, '19860531NAC001', 'Triatlo UNICEF Setúbal', '1986-05-31', '1986-05-31', 'Setúbal', 'Setúbal', 'Setúbal');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (5, '19860614NAC001', 'Triatlo de Cascais', '1986-06-14', '1986-06-14', 'Cascais', 'Cascais', 'Lisboa');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (6, '19860719NAC001', 'Triatlo de Espinho', '1986-07-19', '1986-07-19', 'Espinho', 'Espinho', 'Aveiro');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (7, '19860815NAC001', 'Triatlo de Peniche', '1986-08-15', '1986-08-15', 'Peniche', 'Peniche', 'Leiria');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (8, '19860913NAC001', 'Triatlo de Tancos', '1986-09-13', '1986-09-13', 'Santa Margarida', 'Vila Nova da Barquinha', 'Santarém');


-- endurancetrio.organizer table
-- -----------------------------
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (5, 'Organizador #5', 'Espinho', 'Espinho', 'Aveiro', 'PRIVATE');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (6, 'Pára-Clube Nacional "Os Bóinas Verdes"', 'Tancos', 'Santarém', 'Santarém', 'CLUB');


-- endurancetrio.event_organizer table
-- -----------------------------------
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (4, 3);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (5, 3);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (5, 4);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (6, 5);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (7, 2);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (8, 6);


-- endurancetrio.event_file table
-- ------------------------------
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (7, 4, 'Regulamento', 1, true, '19860531NAC001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (8, 4, 'Percursos', 1, true, '19860531NAC001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (9, 5, 'Cartaz', 1, true, '19860614NAC001-IMG001-01.jpg', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (10, 5, 'Regulamento', 1, true, '19860614NAC001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (11, 5, 'Percursos', 1, true, '19860614NAC001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (12, 7, 'Cartaz', 1, true, '19860815NAC001-IMG001-01.pdf', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (13, 7, 'Regulamento', 1, true, '19860815NAC001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (14, 8, 'Regulamento', 1, true, '19860913NAC001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (15, 8, 'Percursos', 1, true, '19860913NAC001-MAP001-01.pdf', 'COURSE_MAPS');


-- endurancetrio.distance table
-- ----------------------------
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (4, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (5, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (7, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (8, 'STANDARD');


-- endurancetrio.triathlon_distance table
-- --------------------------------------
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (4, 1000, 1, 40000, 1, 10000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (5, 1000, 1, 45000, 1, 10000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (7, 900, 1, 35000, 1, 8000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (8, 1000, 1, 45000, 1, 10000, 1);


-- endurancetrio.course table
-- --------------------------
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (4, 4, 'Triatlo Standard', 'TRIATHLON', 4);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (5, 5, 'Triatlo Standard', 'TRIATHLON', 5);
INSERT INTO endurancetrio.course (id, event_id, title, sport) VALUES (6, 6, 'Triatlo', 'TRIATHLON');
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (7, 7, 'Triatlo Standard', 'TRIATHLON', 7);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (8, 8, 'Triatlo Standard', 'TRIATHLON', 8);


-- endurancetrio.race table
-- ------------------------
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (10, '19860531NAC001-001', 'Triatlo UNICEF Setúbal', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1986-05-31', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (11, '19860531NAC001-002', 'Triatlo UNICEF Setúbal', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1986-05-31', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (12, '19860531NAC001-003', 'Triatlo UNICEF Setúbal', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1986-05-31', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (13, '19860614NAC001-001', 'Triatlo de Cascais', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1986-06-14', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (14, '19860614NAC001-002', 'Triatlo de Cascais', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1986-06-14', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (15, '19860614NAC001-003', 'Triatlo de Cascais', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1986-06-14', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (16, '19860815NAC001-001', 'Triatlo de Peniche', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1986-08-15', '15:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (17, '19860815NAC001-002', 'Triatlo de Peniche', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1986-08-15', '15:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (18, '19860815NAC001-003', 'Triatlo de Peniche', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1986-08-15', '15:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (19, '19860913NAC001-001', 'Triatlo de Tancos', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1986-09-13', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (20, '19860913NAC001-002', 'Triatlo de Tancos', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1986-09-13', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (21, '19860913NAC001-003', 'Triatlo de Tancos', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1986-09-13', '13:00:00', null, null, 'COMPLETED');


-- endurancetrio.triathlon_based_race table
-- ----------------------------------------
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (10, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (11, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (12, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (13, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (14, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (15, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (16, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (17, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (18, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (19, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (20, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (21, null, 'UNKNOWN');


-- endurancetrio.course_race table
-- -------------------------------
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (4, 10);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (4, 11);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (4, 12);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (5, 13);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (5, 14);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (5, 15);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (6, 16);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (6, 17);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (6, 18);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (7, 19);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (7, 20);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (7, 21);


-- endurancetrio.race_hierarchy table
-- ----------------------------------
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (10, 10);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (11, 10);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (13, 13);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (14, 13);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (16, 16);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (17, 16);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (19, 19);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (20, 19);


-- endurancetrio.results_file table
-- --------------------------------
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (9, 10, 'Triatlo UNICEF Setúbal', 'Geral', 1, true, '19860531NAC001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (10, 13, 'Triatlo de Cascais', 'Geral', 1, true, '19860614NAC001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (11, 16, 'Triatlo de Peniche', 'Geral', 1, true, '19860815NAC001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (12, 19, 'Triatlo de Tancos', 'Geral', 1, true, '19860913NAC001-001A-01.pdf');
