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

-- Description: Inserts the data of the 1984 triathlon related events in Portugal
--

-- Set the search path to the schema `endurancetrio_community`
SET search_path TO endurancetrio_community;

-- event table
--
INSERT INTO
  event (id, event_reference, title, start_date, end_date, city, county, district)
VALUES
  (1, '19840815NAC001', 'Triatlo de Peniche', '1984-08-15', '1984-08-15', 'Peniche', 'Peniche', 'Leiria');

SELECT setval('seq_event_id', 2, FALSE);


-- organizer table
--
INSERT INTO
  organizer (id, name, city, county, district, organizer_type)
VALUES
  (1, 'Nuno Bello Conceição', 'Peniche', 'Peniche', 'Leiria', 'PRIVATE'),
  (2, 'Câmara Municipal de Peniche', 'Peniche', 'Peniche', 'Leiria', 'PUBLIC');

SELECT setval('seq_organizer_id', 3, FALSE);


-- event_organizer table
--
INSERT INTO
  event_organizer (event_id, organizer_id)
VALUES
  (1, 1),
  (1, 2);


-- event_file table
--
INSERT INTO
  event_file (id, event_id, title, revision, is_active, file_name, file_type)
VALUES
  (1, 1, 'Cartaz', 1, true, '19840815NAC001-IMG001-01.jpg', 'POSTER'),
  (2, 1, 'Regulamento', 1, true, '19840815NAC001-REG001-01.pdf', 'RULES');

SELECT setval('seq_event_file_id', 3, FALSE);


-- distance table
--
INSERT INTO
  distance (id, distance_type)
VALUES
  (1, 'SPRINT');

SELECT setval('seq_distance_id', 2, FALSE);


-- triathlon_distance table
--
INSERT INTO
  triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps)
VALUES
  (1, 600, 1, 17000, 1, 8000, 1);


-- course table
--
INSERT INTO
  course (id, event_id, title, sport, distance_id)
VALUES
  (1, 1, 'Triatlo Sprint', 'TRIATHLON', 1);

SELECT setval('seq_course_id', 2, FALSE);


-- age_group table
--
INSERT INTO
  age_group (id, title, short_title)
VALUES
  (1, 'Overall', 'OVR');

SELECT setval('seq_age_group_id', 2, FALSE);


-- race table
--
INSERT INTO
  race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, race_date, race_time, gun_time, air_temperature, race_status)
VALUES
  (1, '19840815NAC001-001', 'Triatlo de Peniche', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1984-08-15', '16:00:00', null, null, 'COMPLETED'),
  (2, '19840815NAC001-002', 'Triatlo de Peniche', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1984-08-15', '16:00:00', null, null, 'COMPLETED'),
  (3, '19840815NAC001-003', 'Triatlo de Peniche', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1984-08-15', '16:00:00', null, null, 'PLANNED');

SELECT setval('seq_race_id', 4, FALSE);


-- triathlon_based_race table
--
INSERT INTO
  triathlon_based_race (id, water_temperature, wetsuit_rule)
VALUES
  (1, null, 'UNKNOWN'),
  (2, null, 'UNKNOWN'),
  (3, null, 'UNKNOWN');


-- course_race table
--
INSERT INTO
  course_race (course_id, race_id)
VALUES
  (1, 1),
  (1, 2),
  (1, 3);


-- race_hierarchy table
--
INSERT INTO
  race_hierarchy (race_id, parent_race_id)
VALUES
  (2, 1),
  (3, 1);


-- results_file table
--
INSERT INTO
  results_file (id, race_id, title, subtitle, revision, is_active, file_name)
VALUES
  (1, 1, 'Triatlo de Peniche', 'Geral', 1, true, '19840815NAC001-001A-01.pdf');

SELECT setval('seq_results_file_id', 2, FALSE);
