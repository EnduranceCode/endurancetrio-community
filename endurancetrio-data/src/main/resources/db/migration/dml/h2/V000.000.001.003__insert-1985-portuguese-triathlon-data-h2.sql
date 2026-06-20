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

-- Description: Inserts the data of the 1985 triathlon related events in Portugal

-- endurancetrio_hub.event table
--
INSERT INTO
  endurancetrio_hub.event (id, event_reference, title, start_date, end_date, city, county, district)
VALUES
  (2, '19850629NAC001', 'Triatlo de Cascais', '1985-06-29', '1985-06-29', 'Cascais', 'Cascais', 'Lisboa'),
  (3, '19850815NAC001', 'Triatlo de Peniche', '1985-08-15', '1985-08-15', 'Peniche', 'Peniche', 'Leiria');

ALTER SEQUENCE endurancetrio_hub.seq_event_id RESTART WITH 4;


-- endurancetrio_hub.organizer table
--
INSERT INTO
  endurancetrio_hub.organizer (id, name, city, county, district, organizer_type)
VALUES
  (3, 'Empresa Pública Notícias e Capital', 'Lisboa', 'Lisboa', 'Lisboa', 'PRIVATE'),
  (4, 'Câmara Municipal de Cascais', 'Cascais', 'Cascais', 'Lisboa', 'PUBLIC');

ALTER SEQUENCE endurancetrio_hub.seq_organizer_id RESTART WITH 5;


-- endurancetrio_hub.event_organizer table
--
INSERT INTO
  endurancetrio_hub.event_organizer (event_id, organizer_id)
VALUES
  (2, 3),
  (2, 4),
  (3, 1),
  (3, 2);


-- endurancetrio_hub.event_file table
--
INSERT INTO
  endurancetrio_hub.event_file (id, event_id, title, revision, is_active, file_name, file_type)
VALUES
  (3, 2, 'Cartaz', 1, true, '19850629NAC001-IMG001-01.jpg', 'POSTER'),
  (4, 2, 'Regulamento', 1, true, '19850629NAC001-REG001-01.pdf', 'RULES'),
  (5, 2, 'Percurso', 1, true, '19850629NAC001-MAP001-01,pdf', 'RULES'),
  (6, 3, 'Cartaz', 1, true, '19850815NAC001-IMG001-01.jpg', 'POSTER');

ALTER SEQUENCE endurancetrio_hub.seq_event_file_id RESTART WITH 7;


-- endurancetrio_hub.distance table
--
INSERT INTO
  endurancetrio_hub.distance (id, distance_type)
VALUES
  (2, 'STANDARD'),
  (3, 'SPRINT');

ALTER SEQUENCE endurancetrio_hub.seq_distance_id RESTART WITH 5;


-- endurancetrio_hub.triathlon_distance table
--
INSERT INTO
  endurancetrio_hub.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps)
VALUES
  (2, 1000, 1, 45000, 1, 10000, 1),
  (3, 600, 1, 17000, 1, 8000, 1);


-- endurancetrio_hub.course table
--
INSERT INTO
  endurancetrio_hub.course (id, event_id, title, sport, distance_id)
VALUES
  (2, 2, 'Triatlo Standard', 'TRIATHLON', 2),
  (3, 3, 'Triatlo Sprint', 'TRIATHLON', 3);

ALTER SEQUENCE endurancetrio_hub.seq_course_id RESTART WITH 4;


-- endurancetrio_hub.race table
--
INSERT INTO
  endurancetrio_hub.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, race_date, race_time, gun_time, air_temperature, race_status)
VALUES
  (4, '19850629NAC001-001', 'Triatlo de Cascais', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1985-06-29', '10:30:00', null, null, 'COMPLETED'),
  (5, '19850629NAC001-002', 'Triatlo de Cascais', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1985-06-29', '10:30:00', null, null, 'COMPLETED'),
  (6, '19850629NAC001-003', 'Triatlo de Cascais', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1985-06-29', '10:30:00', null, null, 'COMPLETED'),
  (7, '19850815NAC001-001', 'Triatlo de Peniche', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1985-08-15', '10:00:00', null, null, 'COMPLETED'),
  (8, '19850815NAC001-002', 'Triatlo de Peniche', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1985-08-15', '10:00:00', null, null, 'COMPLETED'),
  (9, '19850815NAC001-003', 'Triatlo de Peniche', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1985-08-15', '10:00:00', null, null, 'COMPLETED');

ALTER SEQUENCE endurancetrio_hub.seq_race_id RESTART WITH 10;


-- endurancetrio_hub.triathlon_based_race table
--
INSERT INTO
  endurancetrio_hub.triathlon_based_race (id, water_temperature, wetsuit_rule)
VALUES
  (4, null, 'UNKNOWN'),
  (5, null, 'UNKNOWN'),
  (6, null, 'UNKNOWN'),
  (7, null, 'UNKNOWN'),
  (8, null, 'UNKNOWN'),
  (9, null, 'UNKNOWN');


-- endurancetrio_hub.course_race table
--
INSERT INTO
  endurancetrio_hub.course_race (course_id, race_id)
VALUES
  (2, 4),
  (2, 5),
  (2, 6),
  (3, 7),
  (3, 8),
  (3, 9);


-- endurancetrio_hub.race_hierarchy table
--
INSERT INTO
  endurancetrio_hub.race_hierarchy (race_id, parent_race_id)
VALUES
  (5, 4),
  (6, 4),
  (8, 7),
  (9, 7);


-- endurancetrio_hub.results_file table
--
INSERT INTO
  endurancetrio_hub.results_file (id, race_id, title, subtitle, revision, is_active, file_name)
VALUES
  (2, 4, 'Triatlo de Cascais', 'Geral', 1, true, '19850629NAC001-001A-01.pdf'),
  (3, 7, 'Triatlo de Peniche', 'Geral', 1, true, '19850815NAC001-001A-01.pdf');

ALTER SEQUENCE endurancetrio_hub.seq_results_file_id RESTART WITH 4;
