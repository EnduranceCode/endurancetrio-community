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

-- Description: Inserts deterministic article fixtures for the article summary repository tests, covering multiple
--              published dates, several contents per article and an article without content. Runs before each test
--              method of ArticleRepositoryDataJpaTest.

INSERT INTO endurancetrio_hub.article
  (id, slug, author_id, published_date, featured_image, featured_image_width, featured_image_height, version, created_at, updated_at)
VALUES
  (990001, 'summary-oldest', 2, '2026-01-01 10:00:00', '/img/insights/summary-oldest.jpg', 1200, 628, 0, NOW(), NULL),
  (990002, 'summary-middle', 2, '2026-02-01 10:00:00', '/img/insights/summary-middle.jpg', 1200, 628, 0, NOW(), NULL),
  (990003, 'summary-newest', 2, '2026-03-01 10:00:00', '/img/insights/summary-newest.jpg', 1200, 628, 0, NOW(), NULL),
  (990004, 'summary-no-content', 2, '2026-04-01 10:00:00', NULL, NULL, NULL, 0, NOW(), NULL);

INSERT INTO endurancetrio_hub.article_content
  (id, article_id, locale, title, subtitle, intro_text, full_text, meta_title, meta_description, version, created_at, updated_at)
VALUES
  (990001, 990001, 'pt', 'Summary Oldest PT', NULL, '<p>Oldest intro</p>', '<p>Oldest full text</p>', NULL, NULL, 0, NOW(), NULL),
  (990002, 990002, 'pt', 'Summary Middle PT', NULL, '<p>Middle PT intro</p>', '<p>Middle PT full text</p>', NULL, NULL, 0, NOW(), NULL),
  (990003, 990002, 'en', 'Summary Middle EN', NULL, '<p>Middle EN intro</p>', '<p>Middle EN full text</p>', NULL, NULL, 0, NOW(), NULL),
  (990004, 990003, 'en', 'Summary Newest EN', NULL, '<p>Newest intro</p>', '<p>Newest full text</p>', NULL, NULL, 0, NOW(), NULL);
