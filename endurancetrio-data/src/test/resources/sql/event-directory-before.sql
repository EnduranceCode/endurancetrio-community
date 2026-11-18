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

-- Description: Inserts deterministic event fixtures for the event directory repository tests, covering date ordering,
--              combined filters, accent folding, wildcard escaping, and distinct district/county pairs. Runs before
--              each test method of EventRepositoryFilterTest.
--

INSERT INTO endurancetrio_hub.event
  (id, event_reference, title, start_date, end_date, district, county, city, version, created_at)
VALUES
  (990101, '20240601TST101', 'Triatlo de São João', '2024-06-01', '2024-06-01', 'Test District A', 'County Águeda', 'City A', 0, NOW()),
  (990102, '20240601TST102', 'Triatlo das Areias', '2024-06-01', '2024-06-01', 'Test District A', 'County Águeda', 'City B', 0, NOW()),
  (990103, '20230910TST103', 'Maratona da Costa Nova', '2023-09-10', '2023-09-10', 'Test District A', 'County B', 'City C', 0, NOW()),
  (990104, '20240820TST104', 'Meia Maratona do Litoral', '2024-08-20', '2024-08-20', 'Test District B', 'County C', 'City D', 0, NOW()),
  (990105, '20220501TST105', 'Triatlo Percent%', '2022-05-01', '2022-05-01', 'Test District B', 'County C', 'City E', 0, NOW()),
  (990106, '20220502TST106', 'Triatlo Under_score', '2022-05-02', '2022-05-02', 'Test District B', 'County C', 'City F', 0, NOW());
