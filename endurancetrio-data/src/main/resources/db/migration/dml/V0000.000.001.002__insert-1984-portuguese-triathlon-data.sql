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

-- Description: Insert the data of the 1984 triathlon related events in Portugal

-- endurancetrio.event table
-- -------------------------
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (1, '19840815NAC001', 'Triatlo de Peniche', '1984-08-15', '1984-08-15', 'Peniche', 'Peniche', 'Leiria');


-- endurancetrio.organizer table
-- -----------------------------
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (1, 'Nuno Bello Conceição', 'Peniche', 'Peniche', 'Leiria', 'PRIVATE');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (2, 'Câmara Municipal de Peniche', 'Peniche', 'Peniche', 'Leiria', 'PUBLIC');


-- endurancetrio.event_organizer table
-- -----------------------------------
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (1, 1);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (1, 2);


-- endurancetrio.event_file table
-- ------------------------------
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (1, 1, 'Cartaz', 1, true, '19840815NAC001-IMG001-01.jpg', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (2, 1, 'Regulamento', 1, true, '19840815NAC001-REG001-01.pdf', 'RULES');


-- endurancetrio.distance table
-- ----------------------------
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (1, 'SPRINT');


-- endurancetrio.triathlon_distance table
-- --------------------------------------
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (1, 600, 1, 17000, 1, 8000, 1);


-- endurancetrio.course table
-- --------------------------
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (1, 1, 'Triatlo Sprint', 'TRIATHLON', 1);


-- endurancetrio.age_group table
-- --------------------------
INSERT INTO endurancetrio.age_group (id, title, short_title) VALUES (1, 'Overall', 'OVR');


-- endurancetrio.race table
-- ------------------------
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (1, '19840815NAC001-001', 'Triatlo de Peniche', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1984-08-15', '16:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (2, '19840815NAC001-002', 'Triatlo de Peniche', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1984-08-15', '16:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (3, '19840815NAC001-003', 'Triatlo de Peniche', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1984-08-15', '16:00:00', null, null, 'PLANNED');


-- endurancetrio.triathlon_based_race table
-- ----------------------------------------
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (1, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (2, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (3, null, 'UNKNOWN');


-- endurancetrio.course_race table
-- -------------------------------
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (1, 1);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (1, 2);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (1, 3);


-- endurancetrio.race_hierarchy table
-- ----------------------------------
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (2, 1);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (3, 1);


-- endurancetrio.results_file table
-- --------------------------------
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (1, 1, 'Triatlo de Peniche', 'Geral', 1, true, '19840815NAC001-001A-01.pdf');
