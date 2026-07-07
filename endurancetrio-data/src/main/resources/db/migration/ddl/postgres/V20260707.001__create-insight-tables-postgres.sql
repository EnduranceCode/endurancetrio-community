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

SET search_path TO endurancetrio_hub;

-- Create sequence for the author table primary key
CREATE SEQUENCE IF NOT EXISTS seq_author_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the author table
CREATE TABLE IF NOT EXISTS author (
  id                BIGINT    DEFAULT nextval('seq_author_id') NOT NULL,
  known_name        TEXT      NOT NULL,
  bio               TEXT,
  profile_file_name TEXT,
  athlete_id        BIGINT,
  version           INTEGER   NOT NULL DEFAULT 0,
  created_at        TIMESTAMP NOT NULL,
  updated_at        TIMESTAMP,
  CONSTRAINT pk_author PRIMARY KEY (id),
  CONSTRAINT uk_author_known_name UNIQUE (known_name),
  CONSTRAINT fk_author_athlete_id
    FOREIGN KEY (athlete_id) REFERENCES athlete(id) ON DELETE SET NULL
);

-- Create a dependency between the seq_author_id sequence and the author table primary key
ALTER SEQUENCE IF EXISTS seq_author_id OWNED BY author.id;

-- Create sequence for the article table primary key
CREATE SEQUENCE IF NOT EXISTS seq_article_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the article table
CREATE TABLE IF NOT EXISTS article (
  id                    BIGINT    DEFAULT nextval('seq_article_id') NOT NULL,
  slug                  TEXT      NOT NULL,
  author_id             BIGINT    NOT NULL,
  published_date        TIMESTAMP,
  featured_image        TEXT,
  featured_image_width  INTEGER,
  featured_image_height INTEGER,
  version               INTEGER   NOT NULL DEFAULT 0,
  created_at            TIMESTAMP NOT NULL,
  updated_at            TIMESTAMP,
  CONSTRAINT pk_article PRIMARY KEY (id),
  CONSTRAINT uk_article_slug UNIQUE (slug),
  CONSTRAINT fk_article_author_id
    FOREIGN KEY (author_id) REFERENCES author(id) ON DELETE CASCADE
);

-- Create a dependency between the seq_article_id sequence and the article table primary key
ALTER SEQUENCE IF EXISTS seq_article_id OWNED BY article.id;

-- Create sequence for the article_content table primary key
CREATE SEQUENCE IF NOT EXISTS seq_article_content_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the article_content table
CREATE TABLE IF NOT EXISTS article_content (
  id               BIGINT    DEFAULT nextval('seq_article_content_id') NOT NULL,
  article_id       BIGINT    NOT NULL,
  locale           TEXT      NOT NULL,
  title            TEXT      NOT NULL,
  subtitle         TEXT,
  intro_text       TEXT      NOT NULL,
  full_text        TEXT,
  meta_title       TEXT,
  meta_description TEXT,
  version          INTEGER   NOT NULL DEFAULT 0,
  created_at       TIMESTAMP NOT NULL,
  updated_at       TIMESTAMP,
  CONSTRAINT pk_article_content PRIMARY KEY (id),
  CONSTRAINT uk_article_content_article_id_locale UNIQUE (article_id, locale),
  CONSTRAINT fk_article_content_article_id
    FOREIGN KEY (article_id) REFERENCES article(id) ON DELETE CASCADE
);

-- Create a dependency between the seq_article_content_id sequence and the article_content table
ALTER SEQUENCE IF EXISTS seq_article_content_id OWNED BY article_content.id;

-- Create the article_event join table
CREATE TABLE IF NOT EXISTS article_event (
  article_id BIGINT NOT NULL,
  event_id   BIGINT NOT NULL,
  CONSTRAINT pk_article_event PRIMARY KEY (article_id, event_id),
  CONSTRAINT fk_article_event_article_id
    FOREIGN KEY (article_id) REFERENCES article(id) ON DELETE CASCADE,
  CONSTRAINT fk_article_event_event_id
    FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE
);

-- Create indexes on the article_content table
CREATE INDEX IF NOT EXISTS idx_article_content_article_id ON article_content (article_id);

-- Create indexes on the article_event table
CREATE INDEX IF NOT EXISTS idx_article_event_article_id ON article_event (article_id);
CREATE INDEX IF NOT EXISTS idx_article_event_event_id ON article_event (event_id);
