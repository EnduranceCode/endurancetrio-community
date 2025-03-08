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

-- Description: Insert the data of the 1988 triathlon related events in Portugal

-- endurancetrio.event table
-- -------------------------
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (24, '19880313APT001', '1º Biatlo de Porto de Mós', '1988-03-13', '1988-03-13', 'Porto de Mós', 'Porto de Mós', 'Leiria');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (25, '19880402APT001', '2º Biatlo do Porto', '1988-04-02', '1988-04-02', 'Porto', 'Porto', 'Porto');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (26, '19880417APT001', 'Biatlo Terras de Santa Maria', '1988-04-17', '1988-04-17', 'Santa Maria da Feira', 'Santa Maria da Feira', 'Aveiro');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (27, '19880515APT001', 'I Triatlo de Loulé', '1988-05-15', '1988-05-15', 'Loulé', 'Loulé', 'Faro');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (28, '19880522APT001', 'Campeonato Nacional de Triatlo Cat. B', '1988-05-22', '1988-05-22', 'Aguieira', 'Santa Comba Dão', 'Viseu');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (29, '19880605APT001', 'Triatlo do Ambiente', '1988-06-05', '1988-06-05', 'Oeiras', 'Oeiras', 'Lisboa');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (30, '19880612APT001', 'Triatlo Internacional de Cascais 1988', '1988-06-12', '1988-06-12', 'Cascais', 'Cascais', 'Lisboa');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (31, '19880626APT001', '1º Triatlo Promoção de Almodôvar', '1988-06-26', '1988-06-26', 'Almodôvar', 'Almodôvar', 'Beja');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (32, '19880703APT001', 'Triatlo do Porto', '1988-07-03', '1988-07-03', 'Porto', 'Porto', 'Porto');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (33, '19880716APT001', 'Triatlo da Costa Azul', '1988-07-16', '1988-07-16', 'Tróia', 'Grândola', 'Setúbal');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (34, '19880806APT001', '2º Triatlo do Coimbrão', '1988-08-06', '1988-08-06', 'Coimbrão', 'Leiria', 'Leiria');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (35, '19880814APT001', '1º Biatlo de Arruda dos Vinhos', '1988-08-14', '1988-08-14', 'Arruda dos Vinhos', 'Arruda dos Vinhos', 'Lisboa');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (36, '19880815APT001', 'V Triatlo de Peniche', '1988-08-14', '1988-08-14', 'Peniche', 'Peniche', 'Leiria');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (37, '19880904APT001', '1º Triatlo de Óbidos', '1988-09-04', '1988-09-04', 'Óbidos', 'Óbidos', 'Leiria');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (38, '19880918APT001', '2º Triatlo de Ponte de Sor', '1988-09-18', '1988-09-18', 'Ponde de Sor', 'Ponte de Sor', 'Portalegre');
INSERT INTO endurancetrio.event (id, event_reference, title, start_date, end_date, city, county, district) VALUES (39, '19880925APT001', 'Triatlo de Lagos', '1988-09-25', '1988-09-25', 'Lagos', 'Lagos', 'Faro');


-- endurancetrio.organizer table
-- -----------------------------
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (22, 'Câmara Municipal de Porto de Mós', 'Porto de Mós', 'Porto de Mós', 'Leiria', 'PUBLIC');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (23, 'Câmara Municipal de Oliveira de Azemeis', 'Oliveira de Azeméis', 'Oliveira de Azeméis', 'Aveiro', 'PUBLIC');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (24, 'Câmara Municipal de São João da Madeira', 'São João da Madeira', 'São João da Madeira', 'Aveiro', 'PUBLIC');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (25, 'Jornal Correio de Azemeis', 'Oliveira de Azeméis', 'Oliveira de Azeméis', 'Aveiro', 'PRIVATE');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (26, 'Louletano Desportos Clube', 'Loulé', 'Loulé', 'Faro', 'CLUB');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (27, 'Câmara Municipal de Loulé', 'Loulé', 'Loulé', 'Faro', 'PUBLIC');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (28, 'Câmara Municipal de Oeiras', 'Oeiras', 'Oeiras', 'Lisboa', 'PUBLIC');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (29, 'Câmara Municipal de Almodôvar', 'Almodôvar', 'Almodôvar', 'Beja', 'PUBLIC');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (30, 'Centro de Desporto e Cultura do Coimbrão', 'Coimbrão', 'Leiria', 'Leiria', 'CLUB');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (31, 'Câmara Municipal de Arruda dos Vinhos', 'Arruda dos Vinhos', 'Arruda dos Vinhos', 'Lisboa', 'PUBLIC');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (32, 'Câmara Municipal de Óbidos', 'Óbidos', 'Óbidos', 'Leiria', 'PUBLIC');
INSERT INTO endurancetrio.organizer (id, name, city, county, district, organizer_type) VALUES (33, 'Câmara Municipal de Lagos', 'Lagos', 'Lagos', 'Faro', 'PUBLIC');


-- endurancetrio.event_organizer table
-- -----------------------------------
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (24, 22);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (24, 12);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (25, 11);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (25, 12);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (26, 17);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (26, 23);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (26, 24);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (26, 25);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (27, 26);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (27, 27);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (28, 12);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (29, 28);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (29, 12);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (30, 3);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (30, 12);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (31, 29);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (31, 12);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (32, 17);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (32, 12);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (33, 21);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (33, 12);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (34, 30);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (34, 12);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (35, 31);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (35, 12);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (36, 2);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (36, 12);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (37, 32);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (37, 12);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (38, 16);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (38, 12);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (39, 18);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (39, 33);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (39, 19);
INSERT INTO endurancetrio.event_organizer (event_id, organizer_id) VALUES (39, 12);


-- endurancetrio.event_file table
-- ------------------------------
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (49, 24, 'Regulamento', 1, true, '19880313APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (50, 24, 'Percursos', 1, true, '19880313APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (51, 26, 'Regulamento', 1, true, '19880417APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (52, 26, 'Cartaz', 1, true, '19880417APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (53, 26, 'Percursos', 1, true, '19880417APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (54, 27, 'Cartaz', 1, true, '19880515APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (55, 27, 'Regulamento', 1, true, '19880515APT001-REG002-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (56, 27, 'Percursos', 1, true, '19880515APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (57, 28, 'Regulamento', 1, true, '19880522APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (58, 28, 'Percursos', 1, true, '19880522APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (60, 29, 'Cartaz', 1, true, '19880605APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (61, 29, 'Percursos', 1, true, '19880605APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (62, 30, 'Cartaz', 1, true, '19880612APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (63, 30, 'Regulamento', 1, true, '19880612APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (64, 30, 'Percursos', 1, true, '19880612APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (65, 31, 'Regulamento', 1, true, '19880626APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (66, 31, 'Percursos', 1, true, '19880703APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (67, 32, 'Regulamento', 1, true, '19880703APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (68, 33, 'Cartaz', 1, true, '19880716APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (69, 33, 'Regulamento', 1, true, '19880716APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (70, 34, 'Regulamento', 1, true, '19880806APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (71, 34, 'Cartaz', 1, true, '19880806APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (72, 34, 'Percursos', 1, true, '19880806APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (73, 35, 'Regulamento', 1, true, '19880814APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (74, 35, 'Percursos', 1, true, '19880814APT001-MAP001-01.pdf', 'COURSE_MAPS');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (75, 36, 'Regulamento', 1, true, '19880815APT001-REG001-01.pdf', 'RULES');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (76, 39, 'Cartaz', 1, true, '19880925APT001-IMG001-01.png', 'POSTER');
INSERT INTO endurancetrio.event_file (id, event_id, title, revision, is_active, file_name, file_type) VALUES (77, 39, 'Percursos', 1, true, '19880925APT001-MAP001-01.pdf', 'COURSE_MAPS');


-- endurancetrio.distance table
-- ----------------------------
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (25, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (26, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (27, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (28, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (29, 'MIDDLE_DISTANCE');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (30, 'SPRINT');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (31, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (32, 'SPRINT');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (33, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (34, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (35, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (36, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (37, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (38, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (39, 'STANDARD');
INSERT INTO endurancetrio.distance (id, distance_type) VALUES (40, 'STANDARD');


-- endurancetrio.biathlon_distance table
-- -------------------------------------
INSERT INTO endurancetrio.biathlon_distance (id, bike_distance, bike_laps, run_distance, run_laps) VALUES (26, 40000, 1, 10000, 1);


-- endurancetrio.triathlon_distance table
-- --------------------------------------
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (28, 1000, 1, 43000, 1, 10000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (29, 2000, 1, 84000, 2, 20000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (30, 500, 1, 25000, 1, 5000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (31, 1000, 1, 42000, 1, 10000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (32, 800, 1, 32000, 1, 8000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (33, 1000, 1, 49000, 1, 10000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (34, 1000, 1, 45000, 1, 10000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (35, 1000, 1, 45000, 1, 10000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (37, 1200, 1, 40000, 1, 11000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (38, 800, 1, 38000, 1, 9000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (39, 1000, 1, 40000, 1, 10000, 1);
INSERT INTO endurancetrio.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps) VALUES (40, 1000, 1, 41000, 1, 10000, 1);


-- endurancetrio.course table
-- --------------------------
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (25, 24, 'Duatlo Standard', 'DUATHLON', 25);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (26, 25, 'Biatlo Standard', 'BIATHLON', 26);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (27, 26, 'Duatlo Standard', 'DUATHLON', 27);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (28, 27, 'Triatlo Standard', 'TRIATHLON', 28);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (29, 28, 'Triatlo Média Distância', 'TRIATHLON', 29);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (30, 29, 'Triatlo Sprint', 'TRIATHLON', 30);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (31, 30, 'Triatlo Standard', 'TRIATHLON', 31);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (32, 31, 'Triatlo Sprint', 'TRIATHLON', 32);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (33, 32, 'Triatlo Standard', 'TRIATHLON', 33);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (34, 33, 'Triatlo Standard', 'TRIATHLON', 34);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (35, 34, 'Triatlo Standard', 'TRIATHLON', 35);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (36, 35, 'Duatlo Standard', 'DUATHLON', 36);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (37, 36, 'Triatlo Standard', 'TRIATHLON', 37);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (38, 37, 'Triatlo Standard', 'TRIATHLON', 38);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (39, 38, 'Triatlo Standard', 'TRIATHLON', 39);
INSERT INTO endurancetrio.course (id, event_id, title, sport, distance_id) VALUES (40, 39, 'Triatlo Standard', 'TRIATHLON', 40);


-- endurancetrio.race table
-- ------------------------
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (92, '19880313APT001-001', '1º Biatlo de Porto de Mós', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-03-13', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (93, '19880313APT001-002', '1º Biatlo de Porto de Mós', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-03-13', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (94, '19880313APT001-003', '1º Biatlo de Porto de Mós', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-03-13', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (95, '19880313APT001-004', '1º Biatlo de Porto de Mós', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-03-13', '11:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (96, '19880313APT001-005', '1º Biatlo de Porto de Mós', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-03-13', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (97, '19880402APT001-001', '2º Biatlo do Porto', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-04-02', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (98, '19880402APT001-002', '2º Biatlo do Porto', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-04-02', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (99, '19880402APT001-003', '2º Biatlo do Porto', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-04-02', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (100, '19880402APT001-004', '2º Biatlo do Porto', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-04-02', '11:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (101, '19880402APT001-005', '2º Biatlo do Porto', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-04-02', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (102, '19880417APT001-001', 'Biatlo Terras de Santa Maria', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-04-17', '09:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (103, '19880417APT001-002', 'Biatlo Terras de Santa Maria', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-04-17', '09:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (104, '19880417APT001-003', 'Biatlo Terras de Santa Maria', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-04-17', '09:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (105, '19880417APT001-004', 'Biatlo Terras de Santa Maria', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-04-17', '09:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (106, '19880417APT001-005', 'Biatlo Terras de Santa Maria', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-04-17', '09:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (107, '19880515APT001-001', 'I Triatlo de Loulé', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-05-15', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (108, '19880515APT001-002', 'I Triatlo de Loulé', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-05-15', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (109, '19880515APT001-003', 'I Triatlo de Loulé', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-05-15', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (110, '19880522APT001-001', 'Campeonato Nacional de Triatlo Categoria B', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-05-22', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (111, '19880522APT001-002', 'Campeonato Nacional de Triatlo Categoria B', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-05-22', '11:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (112, '19880522APT001-003', 'Campeonato Nacional de Triatlo Categoria B', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-05-22', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (113, '19880605APT001-001', 'Triatlo do Ambiente', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-06-05', '15:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (114, '19880605APT001-002', 'Triatlo do Ambiente', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-06-05', '15:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (115, '19880605APT001-003', 'Triatlo do Ambiente', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-06-05', '15:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (116, '19880612APT001-001', 'Triatlo Internacional de Cascais 1988', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-06-12', '12:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (117, '19880612APT001-002', 'Triatlo Internacional de Cascais 1988', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-06-12', '12:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (118, '19880612APT001-003', 'Triatlo Internacional de Cascais 1988', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-06-12', '12:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (119, '19880612APT001-004', 'Triatlo Internacional de Cascais 1988', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-06-12', '12:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (120, '19880612APT001-005', 'Triatlo Internacional de Cascais 1988', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-06-12', '12:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (121, '19880626APT001-001', '1º Triatlo Promoção de Almodôvar', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-06-26', '16:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (122, '19880626APT001-002', '1º Triatlo Promoção de Almodôvar', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-06-26', '16:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (123, '19880626APT001-003', '1º Triatlo Promoção de Almodôvar', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-06-26', '16:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (124, '19880626APT001-004', '1º Triatlo Promoção de Almodôvar', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-06-26', '16:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (125, '19880626APT001-005', '1º Triatlo Promoção de Almodôvar', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-06-26', '16:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (126, '19880703APT001-001', 'Triatlo do Porto', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-07-03', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (127, '19880703APT001-002', 'Triatlo do Porto', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-07-03', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (128, '19880703APT001-003', 'Triatlo do Porto', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-07-03', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (129, '19880716APT001-001', 'Triatlo da Costa Azul', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-07-16', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (130, '19880716APT001-002', 'Triatlo da Costa Azul', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-07-16', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (131, '19880716APT001-003', 'Triatlo da Costa Azul', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-07-16', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (132, '19880716APT001-004', 'Triatlo da Costa Azul', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-07-16', '11:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (133, '19880716APT001-005', 'Triatlo da Costa Azul', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-07-16', '11:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (134, '19880806APT001-001', 'II Triatlo do Coimbrão', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-08-06', '15:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (135, '19880806APT001-002', 'II Triatlo do Coimbrão', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-08-06', '15:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (136, '19880806APT001-003', 'II Triatlo do Coimbrão', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-08-06', '15:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (137, '19880806APT001-004', 'II Triatlo do Coimbrão', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-08-06', '15:30:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (138, '19880806APT001-005', 'II Triatlo do Coimbrão', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-08-06', '15:30:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (139, '19880814APT001-001', 'I Biatlo de Arruda dos Vinhos', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-08-14', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (140, '19880814APT001-002', 'I Biatlo de Arruda dos Vinhos', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-08-14', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (141, '19880814APT001-003', 'I Biatlo de Arruda dos Vinhos', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-08-14', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (142, '19880814APT001-004', 'I Biatlo de Arruda dos Vinhos', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-08-14', '10:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (143, '19880814APT001-005', 'I Biatlo de Arruda dos Vinhos', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-08-14', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (144, '19880815APT001-001', 'V Triatlo de Peniche', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-08-15', '16:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (145, '19880815APT001-002', 'V Triatlo de Peniche', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-08-15', '16:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (146, '19880815APT001-003', 'V Triatlo de Peniche', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-08-15', '16:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (147, '19880815APT001-004', 'V Triatlo de Peniche', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-08-15', '16:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (148, '19880815APT001-005', 'V Triatlo de Peniche', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-08-15', '16:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (149, '19880904APT001-001', '1º Triatlo de Óbidos', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-09-04', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (150, '19880904APT001-002', '1º Triatlo de Óbidos', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-09-04', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (151, '19880904APT001-003', '1º Triatlo de Óbidos', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-09-04', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (152, '19880904APT001-004', '1º Triatlo de Óbidos', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-09-04', '10:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (153, '19880904APT001-005', '1º Triatlo de Óbidos', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-09-04', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (154, '19880918APT001-001', '2º Triatlo de Ponte de Sor', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-09-18', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (155, '19880918APT001-002', '2º Triatlo de Ponte de Sor', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-09-18', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (156, '19880918APT001-003', '2º Triatlo de Ponte de Sor', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-09-18', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (157, '19880918APT001-004', '2º Triatlo de Ponte de Sor', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-09-18', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (158, '19880918APT001-005', '2º Triatlo de Ponte de Sor', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-09-18', '10:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (159, '19880925APT001-001', 'Triatlo de Lagos', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1988-09-25', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (160, '19880925APT001-002', 'Triatlo de Lagos', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1988-09-25', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (161, '19880925APT001-003', 'Triatlo de Lagos', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1988-09-25', '13:00:00', null, null, 'COMPLETED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (162, '19880925APT001-004', 'Triatlo de Lagos', 'Equipas femininas', 'FEMALE', 1, 'TEAM_BY_TIME', '1988-09-25', '13:00:00', null, null, 'PLANNED');
INSERT INTO endurancetrio.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, date, time, gun_time, air_temperature, race_status) VALUES (163, '19880925APT001-005', 'Triatlo de Lagos', 'Equipas masculinas', 'MALE', 1, 'TEAM_BY_TIME', '1988-09-25', '13:00:00', null, null, 'COMPLETED');


-- endurancetrio.triathlon_based_race table
-- ----------------------------------------
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (107, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (108, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (109, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (110, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (111, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (112, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (113, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (114, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (115, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (116, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (117, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (118, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (119, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (120, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (121, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (122, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (123, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (124, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (125, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (126, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (127, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (128, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (129, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (130, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (131, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (132, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (133, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (134, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (135, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (136, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (137, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (138, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (144, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (145, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (146, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (147, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (148, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (149, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (150, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (151, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (152, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (153, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (154, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (155, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (156, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (157, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (158, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (159, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (160, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (161, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (162, null, 'UNKNOWN');
INSERT INTO endurancetrio.triathlon_based_race (id, water_temperature, wetsuit_rule) VALUES (163, null, 'UNKNOWN');


-- endurancetrio.course_race table
-- -------------------------------
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (25, 92);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (25, 93);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (25, 94);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (25, 95);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (25, 96);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (26, 97);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (26, 98);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (26, 99);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (26, 100);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (26, 101);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (27, 102);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (27, 103);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (27, 104);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (27, 105);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (27, 106);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (28, 107);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (28, 108);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (28, 109);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (29, 110);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (29, 111);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (29, 112);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (30, 113);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (30, 114);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (30, 115);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (31, 116);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (31, 117);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (31, 118);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (31, 119);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (31, 120);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (32, 121);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (32, 122);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (32, 123);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (32, 124);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (32, 125);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (33, 126);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (33, 127);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (33, 128);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (34, 129);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (34, 130);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (34, 131);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (34, 132);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (34, 133);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (35, 134);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (35, 135);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (35, 136);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (36, 139);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (36, 140);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (36, 141);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (37, 144);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (37, 145);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (37, 146);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (37, 147);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (38, 148);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (38, 149);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (38, 150);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (38, 151);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (38, 152);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (38, 153);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (39, 154);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (39, 155);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (39, 156);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (39, 157);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (39, 158);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (40, 159);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (40, 160);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (40, 161);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (40, 162);
INSERT INTO endurancetrio.course_race (course_id, race_id) VALUES (40, 163);


-- endurancetrio.race_hierarchy table
-- ----------------------------------
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (93, 92);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (94, 92);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (95, 93);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (96, 94);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (98, 97);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (99, 97);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (100, 98);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (101, 99);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (103, 102);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (104, 102);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (105, 103);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (106, 104);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (108, 107);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (109, 107);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (111, 110);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (112, 110);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (114, 113);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (115, 113);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (117, 116);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (118, 116);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (122, 121);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (123, 121);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (124, 122);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (125, 123);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (127, 126);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (128, 126);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (130, 129);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (131, 129);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (132, 130);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (133, 131);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (135, 134);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (136, 134);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (137, 135);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (138, 136);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (140, 139);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (141, 139);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (142, 140);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (143, 141);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (145, 144);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (146, 144);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (147, 145);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (148, 146);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (150, 149);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (151, 149);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (152, 150);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (153, 151);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (155, 154);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (156, 154);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (157, 155);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (158, 156);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (160, 159);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (161, 159);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (162, 160);
INSERT INTO endurancetrio.race_hierarchy (race_id, parent_race_id) VALUES (163, 161);


-- endurancetrio.results_file table
-- --------------------------------
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (32, 92, 'I Biatlo de Porto de Mós', 'Geral', 1, true, '19880313APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (33, 98, '2º Biatlo do Porto', 'Escalões Femininos', 1, true, '19880402APT001-002B-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (34, 99, '2º Biatlo do Porto', 'Masculinos', 1, true, '19880402APT001-003A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (35, 99, '2º Biatlo do Porto', 'Escalões Masculinos', 1, true, '19880402APT001-003B-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (36, 101, '2º Biatlo do Porto', 'Equipas Masculinas', 1, true, '19880402APT001-005A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (37, 102, 'Biatlo Terras de Santa Maria', 'Geral', 1, true, '19880417APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (38, 106, 'Biatlo Terras de Santa Maria', 'Equipas masculinas', 1, true, '19880417APT001-005A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (39, 107, 'I Triatlo de Loulé', 'Geral', 1, true, '19880515APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (40, 110, 'Campeonato Nacional de Triatlo Cat. B', 'Geral', 1, true, '19880522APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (41, 113, 'Triatlo do Ambiente', 'Geral', 1, true, '19880605APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (42, 116, 'Triatlo Internacional de Cascais 1988', 'Geral', 1, true, '19880612APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (43, 121, '1º Triatlo Promoção de Almodôvar', 'Geral', 1, true, '19880626APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (44, 124, '1º Triatlo Promoção de Almodôvar', 'Equipas Masculinas', 1, true, '19880626APT001-004A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (45, 126, 'Triatlo do Porto', 'Geral', 1, true, '19880703APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (46, 129, 'Triatlo da Costa Azul', 'Geral', 1, true, '19880716APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (47, 134, 'II Triatlo do Coimbrão', 'Geral', 1, true, '19880806APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (48, 139, 'I Biatlo de Arruda dos Vinhos', 'Geral', 1, true, '19880814APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (49, 144, 'V Triatlo de Peniche', 'Geral', 1, true, '19880815APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (50, 148, 'V Triatlo de Peniche', 'Equipas Masculinas', 1, true, '19880815APT001-005A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (51, 149, '1º Triatlo de Óbidos', 'Geral', 1, true, '19880904APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (52, 153, '1º Triatlo de Óbidos', 'Equipas Masculinas', 1, true, '19880904APT001-005A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (53, 154, '2º Triatlo de Ponte de Sor', 'Geral', 1, true, '19880918APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (54, 155, '2º Triatlo de Ponte de Sor', 'Escalões Femininos', 1, true, '19880918APT001-002B-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (55, 156, '2º Triatlo de Ponte de Sor', 'Escalões Masculinos', 1, true, '19880918APT001-003B-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (56, 158, '2º Triatlo de Ponte de Sor', 'Equipas Masculinas', 1, true, '19880918APT001-005A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (57, 159, 'Triatlo de Lagos', 'Geral', 1, true, '19880925APT001-001A-01.pdf');
INSERT INTO endurancetrio.results_file (id, race_id, title, subtitle, revision, is_active, file_name) VALUES (58, 163, 'Triatlo de Lagos', 'Equipas Masculinas', 1, true, '19880925APT001-005A-01.pdf');
