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

-- Description: Inserts sample insight articles for development and testing


-- Author 1: linked to athlete ID 4
--
INSERT INTO
  endurancetrio_hub.author (id, known_name, bio, profile_file_name, athlete_id, version, created_at, updated_at)
VALUES
  (1, 'Paulo Cavaleiro', null, null, 4, 0, NOW(), null);

-- Author 2: linked to athlete ID 1
--
INSERT INTO
  endurancetrio_hub.author (id, known_name, bio, profile_file_name, athlete_id, version, created_at, updated_at)
VALUES
  (2, 'Paulo Paula Carvalho', null, null, 1, 0, NOW(), null);


-- Article 1: bilingual (EN + PT), linked to event ID 1
--
INSERT INTO
  endurancetrio_hub.article (id, slug, author_id, published_date, featured_image, featured_image_width, featured_image_height, version, created_at, updated_at)
VALUES
  (1, 'lorem-ipsum-1', 1, '2026-07-06 10:00:00', '/img/insights/lorem-ipsum-1.jpg', 1200, 628, 0, NOW(), null);

-- EN content for Article 1
INSERT INTO
  endurancetrio_hub.article_content (id, article_id, locale, title, subtitle, intro_text, full_text, meta_title, meta_description, version, created_at, updated_at)
VALUES
  (
    1,
    1,
    'en',
    'Lorem Ipsum Dolor Sit Amet',
    'Consectetur adipiscing elit sed do eiusmod',
    '<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>',
    '<p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p><p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p><p>Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.</p><p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident.</p><p>Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus.</p><p>Omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae.</p>',
    'Lorem ipsum dolor sit amet',
    'Lorem ipsum dolor sit amet consectetur adipiscing elit',
    0,
    NOW(),
    null
  );

-- PT content for Article 1
INSERT INTO
  endurancetrio_hub.article_content (id, article_id, locale, title, subtitle, intro_text, full_text, meta_title, meta_description, version, created_at, updated_at)
VALUES
  (
    2,
    1,
    'pt',
    'Lorem Ipsum Dolor Sit Amet',
    'Consectetur adipiscing elit sed do eiusmod',
    '<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>',
    '<p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p><p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p><p>Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.</p><p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident.</p><p>Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus.</p><p>Omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae.</p>',
    'Lorem ipsum dolor sit amet',
    'Lorem ipsum dolor sit amet consectetur adipiscing elit',
    0,
    NOW(),
    null
  );

-- Link Article 1 to event ID 1
INSERT INTO
  endurancetrio_hub.article_event (article_id, event_id)
VALUES
  (1, 1);


-- Article 2: Portuguese only, linked to event ID 1
--
INSERT INTO
  endurancetrio_hub.article (id, slug, author_id, published_date, featured_image, featured_image_width, featured_image_height, version, created_at, updated_at)
VALUES
  (2, 'lorem-ipsum-2', 2, '2026-07-06 11:00:00', '/img/insights/lorem-ipsum-2.jpg', 1200, 628, 0, NOW(), null);

-- PT content for Article 2 (no EN content — tests fallback behaviour)
INSERT INTO
  endurancetrio_hub.article_content (id, article_id, locale, title, subtitle, intro_text, full_text, meta_title, meta_description, version, created_at, updated_at)
VALUES
  (
    3,
    2,
    'pt',
    'Lorem Ipsum Dolor Sit Amet',
    'Consectetur adipiscing elit sed do eiusmod',
    '<p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.</p>',
    '<p>Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.</p><p>Sed ut perspiciatis unde omnis iste natus error sit voluptatem accusantium doloremque laudantium, totam rem aperiam, eaque ipsa quae ab illo inventore veritatis et quasi architecto beatae vitae dicta sunt explicabo.</p><p>Nemo enim ipsam voluptatem quia voluptas sit aspernatur aut odit aut fugit, sed quia consequuntur magni dolores eos qui ratione voluptatem sequi nesciunt.</p><p>At vero eos et accusamus et iusto odio dignissimos ducimus qui blanditiis praesentium voluptatum deleniti atque corrupti quos dolores et quas molestias excepturi sint occaecati cupiditate non provident.</p><p>Et harum quidem rerum facilis est et expedita distinctio. Nam libero tempore, cum soluta nobis est eligendi optio cumque nihil impedit quo minus id quod maxime placeat facere possimus.</p><p>Omnis voluptas assumenda est, omnis dolor repellendus. Temporibus autem quibusdam et aut officiis debitis aut rerum necessitatibus saepe eveniet ut et voluptates repudiandae sint et molestiae non recusandae.</p>',
    'Lorem ipsum dolor sit amet',
    'Lorem ipsum dolor sit amet consectetur adipiscing elit',
    0,
    NOW(),
    null
  );

-- Link Article 2 to event ID 1
INSERT INTO
  endurancetrio_hub.article_event (article_id, event_id)
VALUES
  (2, 1);

ALTER SEQUENCE endurancetrio_hub.seq_author_id RESTART WITH 3;
ALTER SEQUENCE endurancetrio_hub.seq_article_id RESTART WITH 3;
ALTER SEQUENCE endurancetrio_hub.seq_article_content_id RESTART WITH 4;
