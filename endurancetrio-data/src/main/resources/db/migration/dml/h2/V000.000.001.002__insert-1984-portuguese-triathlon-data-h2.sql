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

-- endurancetrio_hub.event table
--
INSERT INTO
  endurancetrio_hub.event (id, event_reference, title, start_date, end_date, city, county, district)
VALUES
  (1, '19840815NAC001', 'Triatlo de Peniche', '1984-08-15', '1984-08-15', 'Peniche', 'Peniche', 'Leiria');

ALTER SEQUENCE endurancetrio_hub.seq_event_id RESTART WITH 2;


-- endurancetrio_hub.organizer table
--
INSERT INTO
  endurancetrio_hub.organizer (id, name, city, county, district, organizer_type)
VALUES
  (1, 'Nuno Bello Conceição', 'Peniche', 'Peniche', 'Leiria', 'PRIVATE'),
  (2, 'Câmara Municipal de Peniche', 'Peniche', 'Peniche', 'Leiria', 'PUBLIC');

ALTER SEQUENCE endurancetrio_hub.seq_organizer_id RESTART WITH 3;


-- endurancetrio_hub.event_organizer table
--
INSERT INTO
  endurancetrio_hub.event_organizer (event_id, organizer_id)
VALUES
  (1, 1),
  (1, 2);


-- endurancetrio_hub.event_file table
--
INSERT INTO
  endurancetrio_hub.event_file (id, event_id, title, revision, is_active, file_name, file_type)
VALUES
  (1, 1, 'Cartaz', 1, true, '19840815NAC001-IMG001-01.jpg', 'POSTER'),
  (2, 1, 'Regulamento', 1, true, '19840815NAC001-REG001-01.pdf', 'RULES');

ALTER SEQUENCE endurancetrio_hub.seq_event_file_id RESTART WITH 3;


-- endurancetrio_hub.distance table
--
INSERT INTO
  endurancetrio_hub.distance (id, distance_type)
VALUES
  (1, 'SPRINT');

ALTER SEQUENCE endurancetrio_hub.seq_distance_id RESTART WITH 2;


-- endurancetrio_hub.triathlon_distance table
--
INSERT INTO
  endurancetrio_hub.triathlon_distance (id, swim_distance, swim_laps, bike_distance, bike_laps, run_distance, run_laps)
VALUES
  (1, 600, 1, 17000, 1, 8000, 1);


-- endurancetrio_hub.course table
--
INSERT INTO
  endurancetrio_hub.course (id, event_id, title, sport, distance_id)
VALUES
  (1, 1, 'Triatlo Sprint', 'TRIATHLON', 1);

ALTER SEQUENCE endurancetrio_hub.seq_course_id RESTART WITH 2;


-- endurancetrio_hub.age_group table
--
INSERT INTO
  endurancetrio_hub.age_group (id, title, short_title)
VALUES
  (1, 'Overall', 'OVR');

ALTER SEQUENCE endurancetrio_hub.seq_age_group_id RESTART WITH 2;


-- endurancetrio_hub.race table
--
INSERT INTO
  endurancetrio_hub.race (id, race_reference, title, subtitle, gender_category, age_group_id, race_type, race_date, race_time, gun_time, air_temperature, race_status)
VALUES
  (1, '19840815NAC001-001', 'Triatlo de Peniche', 'Geral', 'OPEN', 1, 'INDIVIDUAL_PARENT', '1984-08-15', '16:00:00', null, null, 'COMPLETED'),
  (2, '19840815NAC001-002', 'Triatlo de Peniche', 'Femininos', 'FEMALE', 1, 'INDIVIDUAL_DERIVED', '1984-08-15', '16:00:00', null, null, 'COMPLETED'),
  (3, '19840815NAC001-003', 'Triatlo de Peniche', 'Masculinos', 'MALE', 1, 'INDIVIDUAL_DERIVED', '1984-08-15', '16:00:00', null, null, 'PLANNED');

ALTER SEQUENCE endurancetrio_hub.seq_race_id RESTART WITH 4;


-- endurancetrio_hub.triathlon_based_race table
--
INSERT INTO
  endurancetrio_hub.triathlon_based_race (id, water_temperature, wetsuit_rule)
VALUES
  (1, null, 'UNKNOWN'),
  (2, null, 'UNKNOWN'),
  (3, null, 'UNKNOWN');


-- endurancetrio_hub.course_race table
--
INSERT INTO
  endurancetrio_hub.course_race (course_id, race_id)
VALUES
  (1, 1),
  (1, 2),
  (1, 3);


-- endurancetrio_hub.race_hierarchy table
--
INSERT INTO
  endurancetrio_hub.race_hierarchy (race_id, parent_race_id)
VALUES
  (2, 1),
  (3, 1);


-- endurancetrio_hub.results_file table
--
INSERT INTO
  endurancetrio_hub.results_file (id, race_id, title, subtitle, revision, is_active, file_name)
VALUES
  (1, 1, 'Triatlo de Peniche', 'Geral', 1, true, '19840815NAC001-001A-01.pdf');

ALTER SEQUENCE endurancetrio_hub.seq_results_file_id RESTART WITH 2;
