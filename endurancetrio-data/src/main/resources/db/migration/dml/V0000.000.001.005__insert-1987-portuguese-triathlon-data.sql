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

-- Description: Insert the data of the 1987 triathlon related events in Portugal

-- endurancetrio.event table
-- -------------------------
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (9, '19870111APT001', 'I Duplo Biatlo de Tomar', '1987-01-11', '1987-01-11', 'Tomar', 'Tomar', 'Santarém');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (10, '19870118APT001', 'I Triatlo Experimental de Braga', '1987-01-18', '1987-01-18', 'Braga', 'Braga', 'Braga');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (11, '19870315APT001', 'I Biatlo de Torres Novas', '1987-03-15', '1987-03-15', 'Torres Novas', 'Torres Novas', 'Santarém');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (12, '19870329APT001', 'I Biatlo do Norte', '1987-03-29', '1987-03-29', 'Braga', 'Braga', 'Braga');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (13, '19870418APT001', '1º Biatlo do Concelho de Loures', '1987-04-18', '1987-04-18', 'Loures', 'Loures', 'Lisboa');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (14, '19870502APT001', 'Biatlo do Porto', '1987-05-02', '1987-05-02', 'Porto', 'Porto', 'Porto');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (15, '19870517APT001', 'Triatlo da Costa Azul', '1987-05-17', '1987-05-17', 'Tróia', 'Setúbal', 'Setúbal');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (16, '19870614APT001', 'Triatlo Internacional de Cascais 1987', '1987-06-14', '1987-06-14', 'Cascais', 'Cascais', 'Lisboa');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (17, '19870712APT001', '1º Triatlo Lions Torres Novas', '1987-07-12', '1987-07-12', 'Torres Novas', 'Torres Novas', 'Santarém');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (18, '19870726APT001', 'I Triatlo de Coimbrão', '1987-07-26', '1987-07-26', 'Coimbrão', 'Leiria', 'Leiria');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (19, '19870815APT001', 'IV Triatlo de Peniche', '1987-08-15', '1987-08-15', 'Peniche', 'Peniche', 'Leiria');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (20, '19870830APT001', 'I Triatlo de Ponte de Sor', '1987-08-30', '1987-08-30', 'Ponte de Sor', 'Ponte de Sor', 'Portalegre');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (21, '19870920APT001', 'I Triatlo da Associação de Comandos Porto', '1987-09-20', '1987-09-20', 'Porto', 'Porto', 'Porto');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (22, '19871004APT001', '1º Triatlo de Lagos', '1987-10-04', '1987-10-04', 'Lagos', 'Lagos', 'Faro');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (23, '19871018APT001', 'I Triatlo de Grândola', '1987-10-18', '1987-10-18', 'Grândola', 'Grândola', 'Setúbal');


-- endurancetrio.organizer table
-- -----------------------------
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (7, 'Clube de Actividades de Lazer e Manutenção', 'Tomar', 'Tomar', 'Santarém', 'CLUB');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (8, 'Câmara Municipal de Braga', 'Braga', 'Braga', 'Braga', 'PUBLIC');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (9, 'Clube Desportivo de Torres Novas', 'Torres Novas', 'Torres Novas', 'Santarém', 'CLUB');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (10, 'Centro de Cultura e Desporto do Pessoal da Câmara Municipal e Serviços Municipalizados de Loures', 'Loures', 'Loures', 'Lisboa', 'PUBLIC');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (11, 'Comissão Organizadora do Biatlo do Porto', 'Porto', 'Porto', 'Porto', 'PRIVATE');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (12, 'Associação Portuguesa de Triatlo', 'Loures', 'Loures', 'Lisboa', 'PRIVATE');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (13, 'Câmara Municipal de Setúbal', 'Setúbal', 'Setúbal', 'Setúbal', 'PUBLIC');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (14, 'Lions Club de Torres Novas', 'Torres Novas', 'Santarém', 'Santarém', 'CLUB');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (15, 'Comissão de Festas do Coimbrão', 'Coimbrão', 'Leiria', 'Leiria', 'CLUB');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (16, 'Câmara Municipal de Ponte de Sor', 'Ponte de Sôr', 'Ponte de Sor', 'Portalegre', 'PUBLIC');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (17, 'Associação de Comandos', 'Santa Maria da Feira', 'Santa Maria da Feira', 'Aveiro', 'CLUB');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (18, 'Junta de Freguesia de Santa Maria', 'Lagos', 'Lagos', 'Faro', 'PUBLIC');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (19, 'Núcleo de Amigos do Atletismo de Lagos', 'Lagos', 'Lagos', 'Faro', 'CLUB');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (20, 'Câmara Municipal de Grândola', 'Grândola', 'Grândola', 'Setúbal', 'PUBLIC');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (21, 'Clube de Ténis de Grândola', 'Grândola', 'Grândola', 'Setúbal', 'CLUB');


-- endurancetrio.event_organizer table
-- -----------------------------------
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (9, 7);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (10, 8);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (11, 9);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (12, 8);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (13, 10);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (14, 11);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (15, 12);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (15, 13);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (16, 3);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (17, 14);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (18, 15);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (19, 2);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (20, 16);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (21, 17);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (22, 18);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (22, 19);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (23, 20);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (23, 21);


-- endurancetrio.event_file table
-- ------------------------------
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (16, 9, 'Cartaz', 1, true, '19870111APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (17, 9, 'Regulamento', 1, true, '19870111APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (18, 12, 'Cartaz', 1, true, '19870329APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (19, 13, 'Cartaz', 1, true, '19870418APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (20, 13, 'Regulamento', 1, true, '19870418APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (21, 13, 'Percursos', 1, true, '19870418APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (22, 14, 'Regulamento', 1, true, '19870502APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (23, 14, 'Percursos da prova', 1, true, '19870502APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (24, 15, 'Cartaz', 1, true, '19870517APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (25, 15, 'Regulamento', 1, true, '19870517APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (26, 15, 'Percursos', 1, true, '19870517APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (27, 16, 'Cartaz', 1, true, '19870614APT001-IMG001-01.jpg', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (28, 16, 'Regulamento', 1, true, '19870614APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (29, 16, 'Percursos', 1, true, '19870614APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (30, 17, 'Cartaz', 1, true, '19870712APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (31, 17, 'Regulamento', 1, true, '19870712APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (32, 17, 'Percursos', 1, true, '19870712APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (33, 18, 'Cartaz', 1, true, '19870726APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (34, 18, 'Regulamento', 1, true, '19870726APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (35, 18, 'Percursos', 1, true, '19870726APT001-MAP001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (36, 19, 'Cartaz', 1, true, '19870815APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (37, 19, 'Regulamento', 1, true, '19870815APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (38, 20, 'Cartaz', 1, true, '19870830APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (39, 20, 'Regulamento', 1, true, '19870830APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (40, 20, 'Percursos', 1, true, '19870830APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (41, 21, 'Cartaz', 1, true, '19870920APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (42, 21, 'Regulamento', 1, true, '19870920APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (43, 21, 'Percursos', 1, true, '19870920APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (44, 22, 'Cartaz', 1, true, '19871004APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (45, 22, 'Regulamento', 1, true, '19871004APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (46, 22, 'Percursos', 1, true, '19871004APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (47, 23, 'Cartaz', 1, true, '19871018APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (48, 23, 'Regulamento', 1, true, '19871018APT001-REG001-01.pdf', 'RULES');


-- endurancetrio.distance table
-- ----------------------------
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (9, 'SPRINT');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (10, 'SPRINT');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (11, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (12, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (13, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (14, 'MIDDLE_DISTANCE');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (15, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (16, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (17, 'SPRINT');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (18, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (19, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (20, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (21, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (22, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (23, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (24, 'STANDARD');


-- endurancetrio.biathlon_distance table
-- -------------------------------------
INSERT INTO endurancetrio.biathlon_distance (id, bike_distance, bike_laps, run_distance, run_laps) VALUES (11, 50000, 1, 10000, 1);
INSERT INTO endurancetrio.biathlon_distance (id, bike_distance, bike_laps, run_distance, run_laps) VALUES (12, 40000, 1, 10000, 1);
INSERT INTO endurancetrio.biathlon_distance (id, bike_distance, bike_laps, run_distance, run_laps) VALUES (13, 37000, 1, 8000, 1);
INSERT INTO endurancetrio.biathlon_distance (id, bike_distance, bike_laps, run_distance, run_laps) VALUES (14, 66000, 1, 16000, 1);


-- endurancetrio.double_biathlon_distance table
-- --------------------------------------------
INSERT INTO endurancetrio.double_biathlon_distance (id, first_bike_distance, first_bike_laps, first_run_distance, first_run_laps, second_bike_distance, second_bike_laps, second_run_distance, second_run_laps) VALUES (9, 15000, 1, 5000, 1, 15000, 1, 5000, 1);


-- endurancetrio.triathlon_distance table
-- --------------------------------------
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (10, 250, 3, 20000, 1, 5000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (15, 800, 1, 35000, 1, 8000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (16, 1000, 1, 44000, 1, 11000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (17, 500, 1, 19000, 1, 5000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (18, 1000, 1, 45000, 1, 10000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (19, 900, 1, 40000, 1, 9000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (20, 800, 1, 35000, 1, 8000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (21, 1000, 1, 45000, 1, 10000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (22, 1000, 1, 45000, 1, 10000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (23, 1000, 1, 46000, 1, 10000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (24, 1000, 1, 48000, 1, 10000, 1);


-- endurancetrio.course table
-- --------------------------
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (9, 9, 'Duplo Biatlo Sprint', 'DOUBLE_BIATHLON', 9);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (10, 10, 'Triatlo Sprint', 'TRIATHLON', 10);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (11, 11, 'Biatlo Standard', 'BIATHLON', 11);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (12, 12, 'Biatlo Standard', 'BIATHLON', 12);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (13, 13, 'Biatlo Standard', 'BIATHLON', 13);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (14, 14, 'Biatlo Média Distância', 'BIATHLON', 14);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (15, 15, 'Triatlo Standard', 'TRIATHLON', 15);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (16, 16, 'Triatlo Standard', 'TRIATHLON', 16);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (17, 16, 'Triatlo Sprint', 'TRIATHLON', 17);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (18, 17, 'Triatlo Standard', 'TRIATHLON', 18);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (19, 18, 'Triatlo Standard', 'TRIATHLON', 19);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (20, 19, 'Triatlo Standard', 'TRIATHLON', 20);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (21, 20, 'Triatlo Standard', 'TRIATHLON', 21);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (22, 21, 'Triatlo Standard', 'TRIATHLON', 22);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (23, 22, 'Triatlo Standard', 'TRIATHLON', 23);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (24, 23, 'Triatlo Standard', 'TRIATHLON', 24);


-- endurancetrio.race table
-- ------------------------
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (22, '19870111APT001-001', 'I Duplo Biatlo de Tomar', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-01-11', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (23, '19870111APT001-002', 'I Duplo Biatlo de Tomar', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-01-11', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (24, '19870111APT001-003', 'I Duplo Biatlo de Tomar', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-01-11', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (25, '19870118APT001-001', 'I Triatlo Experimental de Braga', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-01-18', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (26, '19870118APT001-002', 'I Triatlo Experimental de Braga', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-01-18', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (27, '19870118APT001-003', 'I Triatlo Experimental de Braga', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-01-18', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (28, '19870315APT001-001', 'I Biatlo de Torres Novas', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-03-15', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (29, '19870315APT001-002', 'I Biatlo de Torres Novas', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-03-15', '10:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (30, '19870315APT001-003', 'I Biatlo de Torres Novas', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-03-15', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (31, '19870329APT001-001', 'I Biatlo do Norte', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-03-29', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (32, '19870329APT001-002', 'I Biatlo do Norte', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-03-29', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (33, '19870329APT001-003', 'I Biatlo do Norte', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-03-29', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (34, '19870418APT001-001', '1º Biatlo do Concelho de Loures', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-04-18', '15:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (35, '19870418APT001-002', '1º Biatlo do Concelho de Loures', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-04-18', '15:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (36, '19870418APT001-003', '1º Biatlo do Concelho de Loures', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-04-18', '15:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (37, '19870418APT001-004', '1º Biatlo do Concelho de Loures', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_RANK', '1987-04-18', '15:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (38, '19870418APT001-005', '1º Biatlo do Concelho de Loures', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_RANK', '1987-04-18', '15:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (39, '19870502APT001-001', 'Biatlo do Porto', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-05-02', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (40, '19870502APT001-002', 'Biatlo do Porto', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-05-02', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (41, '19870502APT001-003', 'Biatlo do Porto', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-05-02', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (42, '19870502APT001-004', 'Biatlo do Porto', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-05-02', '13:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (43, '19870502APT001-005', 'Biatlo do Porto', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-05-02', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (44, '19870517APT001-001', 'Triatlo da Costa Azul', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-05-17', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (45, '19870517APT001-002', 'Triatlo da Costa Azul', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-05-17', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (46, '19870517APT001-003', 'Triatlo da Costa Azul', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-05-17', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (47, '19870517APT001-004', 'Triatlo da Costa Azul', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-05-17', '10:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (48, '19870517APT001-005', 'Triatlo da Costa Azul', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-05-17', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (49, '19870614APT001-001', 'Triatlo Internacional de Cascais 1987', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-06-14', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (50, '19870614APT001-002', 'Triatlo Internacional de Cascais 1987', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-06-14', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (51, '19870614APT001-003', 'Triatlo Internacional de Cascais 1987', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-06-14', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (52, '19870614APT001-004', 'Triatlo Internacional de Cascais 1987', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-06-14', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (53, '19870614APT001-005', 'Triatlo Internacional de Cascais 1987', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-06-14', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (54, '19870614APT001-006', 'Prova de Promoção', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-06-14', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (55, '19870614APT001-007', 'Prova de Promoção', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-06-14', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (56, '19870614APT001-008', 'Prova de Promoção', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-06-14', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (57, '19870712APT001-001', '1º Triatlo Lions Torres Novas', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-07-12', '12:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (58, '19870712APT001-002', '1º Triatlo Lions Torres Novas', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-07-12', '12:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (59, '19870712APT001-003', '1º Triatlo Lions Torres Novas', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-07-12', '12:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (60, '19870712APT001-004', '1º Triatlo Lions Torres Novas', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-07-12', '12:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (61, '19870712APT001-005', '1º Triatlo Lions Torres Novas', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-07-12', '12:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (62, '19870726APT001-001', 'I Triatlo de Coimbrão', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-07-26', '15:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (63, '19870726APT001-002', 'I Triatlo de Coimbrão', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-07-26', '15:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (64, '19870726APT001-003', 'I Triatlo de Coimbrão', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-07-26', '15:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (65, '19870726APT001-004', 'I Triatlo de Coimbrão', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-07-26', '15:30:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (66, '19870726APT001-005', 'I Triatlo de Coimbrão', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-07-26', '15:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (67, '19870815APT001-001', 'IV Triatlo de Peniche', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-08-15', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (68, '19870815APT001-002', 'IV Triatlo de Peniche', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-08-15', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (69, '19870815APT001-003', 'IV Triatlo de Peniche', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-08-15', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (70, '19870815APT001-004', 'IV Triatlo de Peniche', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-08-15', '10:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (71, '19870815APT001-005', 'IV Triatlo de Peniche', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-08-15', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (72, '19870830APT001-001', 'I Triatlo de Ponte de Sor', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-08-30', '16:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (73, '19870830APT001-002', 'I Triatlo de Ponte de Sor', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-08-30', '16:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (74, '19870830APT001-003', 'I Triatlo de Ponte de Sor', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-08-30', '16:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (75, '19870830APT001-004', 'I Triatlo de Ponte de Sor', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-08-30', '16:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (76, '19870830APT001-005', 'I Triatlo de Ponte de Sor', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-08-30', '16:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (77, '19870920APT001-001', 'I Triatlo da Associação de Comandos Porto', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-09-20', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (78, '19870920APT001-002', 'I Triatlo da Associação de Comandos Porto', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-09-20', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (79, '19870920APT001-003', 'I Triatlo da Associação de Comandos Porto', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-09-20', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (80, '19870920APT001-004', 'I Triatlo da Associação de Comandos Porto', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-09-20', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (81, '19870920APT001-005', 'I Triatlo da Associação de Comandos Porto', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-09-20', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (82, '19871004APT001-001', '1º Triatlo de Lagos', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-10-04', '12:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (83, '19871004APT001-002', '1º Triatlo de Lagos', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-10-04', '12:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (84, '19871004APT001-003', '1º Triatlo de Lagos', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-10-04', '12:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (85, '19871004APT001-004', '1º Triatlo de Lagos', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-10-04', '12:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (86, '19871004APT001-005', '1º Triatlo de Lagos', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-10-04', '12:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (87, '19871018APT001-001', 'I Triatlo de Grândola', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1987-10-18', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (88, '19871018APT001-002', 'I Triatlo de Grândola', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1987-10-18', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (89, '19871018APT001-003', 'I Triatlo de Grândola', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1987-10-18', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (90, '19871018APT001-004', 'I Triatlo de Grândola', 'Equipas Femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1987-10-18', '11:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (91, '19871018APT001-005', 'I Triatlo de Grândola', 'Equipas Masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1987-10-18', '11:00:00', null, null, 'COMPLETED');


-- endurancetrio.triathlon_based_race table
-- ----------------------------------------
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (44, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (45, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (46, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (47, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (48, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (49, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (50, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (51, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (52, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (53, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (54, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (55, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (56, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (57, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (58, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (59, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (60, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (61, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (62, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (63, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (64, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (65, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (66, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (67, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (68, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (69, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (70, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (71, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (72, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (73, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (74, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (75, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (76, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (77, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (78, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (79, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (80, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (81, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (82, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (83, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (84, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (85, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (86, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (87, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (88, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (89, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (90, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (91, null, 'UNKNOWN');


-- endurancetrio.course_race table
-- -------------------------------
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (9, 22);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (9, 23);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (9, 24);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (10, 25);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (10, 26);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (10, 27);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (11, 28);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (11, 29);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (11, 30);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (12, 31);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (12, 32);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (12, 33);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (13, 34);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (13, 35);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (13, 36);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (13, 37);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (13, 38);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (14, 39);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (14, 40);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (14, 41);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (14, 42);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (14, 43);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (15, 44);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (15, 45);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (15, 46);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (15, 47);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (15, 48);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (16, 49);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (16, 50);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (16, 51);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (16, 52);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (16, 53);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (17, 54);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (17, 55);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (17, 56);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (18, 57);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (18, 58);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (18, 59);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (18, 60);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (18, 61);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (19, 62);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (19, 63);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (19, 64);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (19, 65);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (19, 66);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (20, 67);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (20, 68);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (20, 69);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (21, 72);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (21, 73);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (21, 74);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (21, 75);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (21, 76);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (22, 77);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (22, 78);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (22, 79);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (22, 80);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (22, 81);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (23, 82);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (23, 83);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (23, 84);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (23, 85);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (23, 86);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (24, 87);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (24, 88);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (24, 89);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (24, 90);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (24, 91);


-- endurancetrio.race_hierarchy table
-- ----------------------------------
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (23, 22);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (24, 22);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (26, 25);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (27, 25);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (29, 28);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (30, 28);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (32, 31);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (33, 31);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (35, 34);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (36, 34);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (37, 35);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (38, 36);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (40, 39);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (41, 39);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (42, 40);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (43, 41);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (45, 44);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (46, 44);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (47, 45);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (48, 46);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (50, 49);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (51, 49);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (52, 50);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (53, 51);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (55, 54);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (56, 54);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (58, 57);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (59, 57);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (60, 58);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (61, 59);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (63, 62);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (64, 62);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (65, 63);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (66, 64);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (68, 67);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (69, 67);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (70, 68);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (71, 69);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (73, 72);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (74, 72);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (75, 73);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (76, 74);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (78, 77);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (79, 77);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (80, 78);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (81, 79);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (83, 82);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (84, 82);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (85, 82);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (86, 82);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (88, 87);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (89, 87);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (90, 88);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (91, 89);


-- endurancetrio.results_file table
-- --------------------------------
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (13, 22, 'I Duplo Biatlo de Tomar', 'Geral', 1, true, '19870111APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (14, 25, 'I Triatlo Experimental de Braga', 'Geral', 1, true, '19870118APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (15, 28, 'I Biatlo de Torres Novas', 'Geral', 1, true, '19870315APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (16, 34, '1º Biatlo do Concelho de Loures', 'Geral', 1, true, '19870418APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (17, 38, '1º Biatlo do Concelho de Loures', 'Equipas Masculinas', 1, true, '19870418APT001-005A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (18, 39, 'Biatlo do Porto', 'Geral', 1, true, '19870502APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (19, 44, 'Triatlo da Costa Azul Tróia', 'Geral', 1, true, '19870517APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (20, 49, 'Triatlo Internacional de Cascais 1987', 'Geral', 1, true, '19870614APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (21, 57, '1º Triatlo Lions Torres Novas', 'Geral', 1, true, '19870712APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (22, 62, 'I Triatlo de Coimbrão', 'Geral', 1, true, '19870726APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (23, 63, 'I Triatlo de Coimbrão', 'Escalões Femininos', 1, true, '19870726APT001-002B-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (24, 66, 'I Triatlo de Coimbrão', 'Equipas Masculinas', 1, true, '19870726APT001-005A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (25, 67, 'IV Triatlo de Peniche', 'Geral', 1, true, '19870815APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (26, 72, 'I Triatlo de Ponte de Sor', 'Geral', 1, true, '19870830APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (27, 77, 'I Triatlo da Associação de Comandos Porto', 'Geral', 1, true, '19870920APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (28, 82, '1º Triatlo de Lagos', 'Geral', 1, true, '19871004APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (29, 85, '1º Triatlo de Lagos', 'Equipas Femininas', 1, true, '19871004APT001-004A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (30, 86, '1º Triatlo de Lagos', 'Equipas Masculinas', 1, true, '19871004APT001-005A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (31, 87, 'I Triatlo de Grândola', 'Geral', 1, true, '19871018APT001-001A-01.pdf');
