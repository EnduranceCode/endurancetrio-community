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

-- Description: Insert the data of the 1985 triathlon related events in Portugal

-- endurancetrio.event table
-- -------------------------
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (2, '19850629NAC001', 'Triatlo de Cascais', '1985-06-29', '1985-06-29', 'Cascais', 'Cascais', 'Lisboa');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (3, '19850815NAC001', 'Triatlo de Peniche', '1985-08-15', '1985-08-15', 'Peniche', 'Peniche', 'Leiria');


-- endurancetrio.organizer table
-- -----------------------------
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (3, 'Empresa Pública Notícias e Capital', 'Lisboa', 'Lisboa', 'Lisboa', 'PRIVATE');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (4, 'Câmara Municipal de Cascais', 'Cascais', 'Cascais', 'Lisboa', 'PUBLIC');


-- endurancetrio.event_organizer table
-- -----------------------------------
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (2, 3);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (2, 4);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (3, 1);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (3, 2);


-- endurancetrio.event_file table
-- ------------------------------
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (3, 2, 'Cartaz', 1, true, '19850629NAC001-IMG001-01.jpg', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (4, 2, 'Regulamento', 1, true, '19850629NAC001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (5, 2, 'Percurso', 1, true, '19850629NAC001-MAP001-01,pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (6, 3, 'Cartaz', 1, true, '19850815NAC001-IMG001-01.jpg', 'POSTER');


-- endurancetrio.distance table
-- ----------------------------
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (2, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (3, 'SPRINT');


-- endurancetrio.triathlon_distance table
-- --------------------------------------
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (2, 1000, 1, 45000, 1, 10000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (3, 600, 1, 17000, 1, 8000, 1);


-- endurancetrio.course table
-- --------------------------
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (2, 2, 'Triatlo Standard', 'TRIATHLON', 2);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (3, 3, 'Triatlo Sprint', 'TRIATHLON', 3);


-- endurancetrio.race table
-- ------------------------
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (4, '19850629NAC001-001', 'Triatlo de Cascais', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1985-06-29', '10:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (5, '19850629NAC001-002', 'Triatlo de Cascais', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1985-06-29', '10:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (6, '19850629NAC001-003', 'Triatlo de Cascais', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1985-06-29', '10:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (7, '19850815NAC001-001', 'Triatlo de Peniche', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1985-08-15', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (8, '19850815NAC001-002', 'Triatlo de Peniche', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1985-08-15', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (9, '19850815NAC001-003', 'Triatlo de Peniche', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1985-08-15', '10:00:00', null, null, 'COMPLETED');


-- endurancetrio.triathlon_based_race table
-- ----------------------------------------
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (4, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (5, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (6, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (7, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (8, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (9, null, 'UNKNOWN');


-- endurancetrio.course_race table
-- -------------------------------
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (2, 4);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (2, 5);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (2, 6);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (3, 7);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (3, 8);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (3, 9);


-- endurancetrio.race_hierarchy table
-- ----------------------------------
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (5, 4);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (6, 4);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (8, 7);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (9, 7);


-- endurancetrio.results_file table
-- --------------------------------
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (2, 4, 'Triatlo de Cascais', 'Geral', 1, true, '19850629NAC001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (3, 7, 'Triatlo de Peniche', 'Geral', 1, true, '19850815NAC001-001A-01.pdf');
