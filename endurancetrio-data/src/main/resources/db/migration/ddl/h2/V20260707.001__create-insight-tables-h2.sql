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

-- Description: Creates the insight section database tables: author, article,
--   article_content, and article_event

SET SCHEMA endurancetrio_hub;

-- Create sequence for the author table primary key
CREATE SEQUENCE IF NOT EXISTS endurancetrio_hub.seq_author_id START WITH 1 INCREMENT BY 1;

-- Create the author table
CREATE TABLE IF NOT EXISTS endurancetrio_hub.author (
  id                BIGINT        DEFAULT nextval('endurancetrio_hub.seq_author_id') NOT NULL,
  known_name        VARCHAR(255)  NOT NULL,
  bio               VARCHAR(16384),
  profile_file_name VARCHAR(255),
  athlete_id        BIGINT,
  version           INTEGER       NOT NULL DEFAULT 0,
  created_at        TIMESTAMP     NOT NULL,
  updated_at        TIMESTAMP,
  CONSTRAINT pk_author PRIMARY KEY (id),
  CONSTRAINT uk_author_known_name UNIQUE (known_name),
  CONSTRAINT fk_author_athlete_id
    FOREIGN KEY (athlete_id) REFERENCES endurancetrio_hub.athlete(id) ON DELETE SET NULL
);

-- Create sequence for the article table primary key
CREATE SEQUENCE IF NOT EXISTS endurancetrio_hub.seq_article_id START WITH 1 INCREMENT BY 1;

-- Create the article table
CREATE TABLE IF NOT EXISTS endurancetrio_hub.article (
  id                    BIGINT       DEFAULT nextval('endurancetrio_hub.seq_article_id') NOT NULL,
  slug                  VARCHAR(255) NOT NULL,
  author_id             BIGINT       NOT NULL,
  published_date        TIMESTAMP,
  featured_image        VARCHAR(255),
  featured_image_width  INTEGER,
  featured_image_height INTEGER,
  version               INTEGER      NOT NULL DEFAULT 0,
  created_at            TIMESTAMP    NOT NULL,
  updated_at            TIMESTAMP,
  CONSTRAINT pk_article PRIMARY KEY (id),
  CONSTRAINT uk_article_slug UNIQUE (slug),
  CONSTRAINT fk_article_author_id
    FOREIGN KEY (author_id) REFERENCES endurancetrio_hub.author(id) ON DELETE CASCADE
);

-- Create sequence for the article_content table primary key
CREATE SEQUENCE IF NOT EXISTS endurancetrio_hub.seq_article_content_id START WITH 1 INCREMENT BY 1;

-- Create the article_content table
CREATE TABLE IF NOT EXISTS endurancetrio_hub.article_content (
  id               BIGINT         DEFAULT nextval('endurancetrio_hub.seq_article_content_id') NOT NULL,
  article_id       BIGINT         NOT NULL,
  locale           VARCHAR(5)     NOT NULL,
  title            VARCHAR(255)   NOT NULL,
  subtitle         VARCHAR(255),
  intro_text       VARCHAR(16384) NOT NULL,
  full_text        VARCHAR(16384),
  meta_title       VARCHAR(255),
  meta_description VARCHAR(255),
  version          INTEGER        NOT NULL DEFAULT 0,
  created_at       TIMESTAMP      NOT NULL,
  updated_at       TIMESTAMP,
  CONSTRAINT pk_article_content PRIMARY KEY (id),
  CONSTRAINT uk_article_content_article_id_locale UNIQUE (article_id, locale),
  CONSTRAINT fk_article_content_article_id
    FOREIGN KEY (article_id) REFERENCES endurancetrio_hub.article(id) ON DELETE CASCADE
);

-- Create the article_event join table
CREATE TABLE IF NOT EXISTS endurancetrio_hub.article_event (
  article_id BIGINT NOT NULL,
  event_id   BIGINT NOT NULL,
  CONSTRAINT pk_article_event PRIMARY KEY (article_id, event_id),
  CONSTRAINT fk_article_event_article_id
    FOREIGN KEY (article_id) REFERENCES endurancetrio_hub.article(id) ON DELETE CASCADE,
  CONSTRAINT fk_article_event_event_id
    FOREIGN KEY (event_id) REFERENCES endurancetrio_hub.event(id) ON DELETE CASCADE
);

-- Create indexes on the article_content table
CREATE INDEX IF NOT EXISTS endurancetrio_hub.idx_article_content_article_id
  ON endurancetrio_hub.article_content (article_id);

-- Create indexes on the article_event table
CREATE INDEX IF NOT EXISTS endurancetrio_hub.idx_article_event_article_id
  ON endurancetrio_hub.article_event (article_id);

CREATE INDEX IF NOT EXISTS endurancetrio_hub.idx_article_event_event_id
  ON endurancetrio_hub.article_event (event_id);
