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

-- Description: Creates the tables for the EnduranceTrio application
--

-- The schema `endurancetrio_community` must exist before running this script
SET search_path TO endurancetrio_community;

-- Create sequence for the event table primary key
CREATE SEQUENCE IF NOT EXISTS seq_event_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the event table
CREATE TABLE IF NOT EXISTS event (
  id              BIGINT      DEFAULT nextval('seq_event_id') NOT NULL,
  event_reference VARCHAR(14) NOT NULL,
  title           TEXT        NOT NULL,
  start_date      DATE        NOT NULL,
  end_date        DATE        NOT NULL,
  district        TEXT        NOT NULL,
  county          TEXT        NOT NULL,
  city            TEXT        NOT NULL,
  CONSTRAINT pk_event PRIMARY KEY (id),
  CONSTRAINT uk_event_event_reference UNIQUE (event_reference)
);

-- Create a dependency between the seq_event_id sequence and the event table primary key
ALTER SEQUENCE IF EXISTS seq_event_id OWNED BY event.id;

-- Create sequence for the organizer table primary key
CREATE SEQUENCE IF NOT EXISTS seq_organizer_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the organizer table
CREATE TABLE IF NOT EXISTS organizer (
  id              BIGINT  DEFAULT nextval('seq_organizer_id') NOT NULL,
  name            TEXT    NOT NULL,
  district        TEXT    NOT NULL,
  county          TEXT    NOT NULL,
  city            TEXT    NOT NULL,
  organizer_type  TEXT    NOT NULL,
  CONSTRAINT pk_organizer PRIMARY KEY (id)
);

-- Create a dependency between the seq_organizer_id sequence and the organizer table primary key
ALTER SEQUENCE IF EXISTS seq_organizer_id OWNED BY organizer.id;

-- Create the event_organizer table
CREATE TABLE IF NOT EXISTS event_organizer (
  event_id      BIGINT  NOT NULL,
  organizer_id  BIGINT  NOT NULL,
  CONSTRAINT pk_event_organizer PRIMARY KEY (event_id, organizer_id),
  CONSTRAINT fk_event_organizer_event_id
    FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE,
  CONSTRAINT fk_event_organizer_organizer_id
    FOREIGN KEY (organizer_id) REFERENCES organizer(id) ON DELETE CASCADE
);

-- Create sequence for the event_file table primary key
CREATE SEQUENCE IF NOT EXISTS seq_event_file_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the event_file table
CREATE TABLE IF NOT EXISTS event_file (
  id        BIGINT      DEFAULT nextval('seq_event_file_id')  NOT NULL,
  event_id  BIGINT      NOT NULL,
  title     TEXT        NOT NULL,
  file_type TEXT        NOT NULL,
  file_name VARCHAR(30) NOT NULL,
  revision  INT         NOT NULL,
  is_active BOOLEAN     NOT NULL,
  CONSTRAINT pk_event_file PRIMARY KEY (id),
  CONSTRAINT fk_event_file_event_id FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE,
  CONSTRAINT uk_event_file_file_name UNIQUE (file_name)
);

-- Create a dependency between the seq_event_file_id sequence and the event_file table primary key
ALTER SEQUENCE IF EXISTS seq_event_file_id OWNED BY event_file.id;

-- Create index on the event_file table
CREATE INDEX IF NOT EXISTS idx_event_file_event_id ON event_file (event_id);

-- Create sequence for the distance table primary key
CREATE SEQUENCE IF NOT EXISTS seq_distance_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the distance table
CREATE TABLE IF NOT EXISTS distance (
  id            BIGINT  DEFAULT nextval('seq_distance_id')  NOT NULL,
  distance_type TEXT    NOT NULL,
  CONSTRAINT pk_distance PRIMARY KEY (id)
);

-- Create a dependency between the seq_distance_id sequence and the distance table primary key
ALTER SEQUENCE IF EXISTS seq_distance_id OWNED BY distance.id;

-- Create the aquabike_distance table
CREATE TABLE IF NOT EXISTS aquabike_distance (
  id            BIGINT  NOT NULL,
  swim_distance INTEGER,
  swim_laps     INTEGER,
  bike_distance INTEGER,
  bike_laps     INTEGER,
  CONSTRAINT pk_aquabike_distance PRIMARY KEY (id),
  CONSTRAINT fk_aquabike_distance_id FOREIGN KEY (id) REFERENCES distance(id) ON DELETE CASCADE
);

-- Create the aquathlon_distance table
CREATE TABLE IF NOT EXISTS aquathlon_distance (
  id            BIGINT  NOT NULL,
  swim_distance INTEGER,
  swim_laps     INTEGER,
  run_distance  INTEGER,
  run_laps      INTEGER,
  CONSTRAINT pk_aquathlon_distance PRIMARY KEY (id),
  CONSTRAINT fk_aquathlon_distance_id FOREIGN KEY (id) REFERENCES distance(id) ON DELETE CASCADE
);

-- Create the biathlon_distance table
CREATE TABLE IF NOT EXISTS biathlon_distance (
  id            BIGINT  NOT NULL,
  bike_distance INTEGER,
  bike_laps     INTEGER,
  run_distance  INTEGER,
  run_laps      INTEGER,
  CONSTRAINT pk_biathlon_distance PRIMARY KEY (id),
  CONSTRAINT fk_biathlon_distance_id FOREIGN KEY (id) REFERENCES distance(id) ON DELETE CASCADE
);

-- Create the double_biathlon_distance table
CREATE TABLE IF NOT EXISTS double_biathlon_distance (
  id                    BIGINT  NOT NULL,
  first_bike_distance   INTEGER,
  first_bike_laps       INTEGER,
  first_run_distance    INTEGER,
  first_run_laps        INTEGER,
  second_bike_distance  INTEGER,
  second_bike_laps      INTEGER,
  second_run_distance   INTEGER,
  second_run_laps       INTEGER,
  CONSTRAINT pk_double_biathlon_distance PRIMARY KEY (id),
  CONSTRAINT fk_double_biathlon_distance_id
    FOREIGN KEY (id) REFERENCES distance(id) ON DELETE CASCADE
);

-- Create the duathlon_distance table
CREATE TABLE IF NOT EXISTS duathlon_distance (
  id                  BIGINT  NOT NULL,
  first_run_distance  INTEGER,
  first_run_laps      INTEGER,
  bike_distance       INTEGER,
  bike_laps           INTEGER,
  second_run_distance INTEGER,
  second_run_laps     INTEGER,
  CONSTRAINT pk_duathlon_distance PRIMARY KEY (id),
  CONSTRAINT fk_duathlon_distance_id FOREIGN KEY (id) REFERENCES distance(id) ON DELETE CASCADE
);

-- Create the single_sport_distance table
CREATE TABLE IF NOT EXISTS single_sport_distance (
  id        BIGINT  NOT NULL,
  distance  INTEGER,
  laps      INTEGER,
  CONSTRAINT pk_single_sport_distance PRIMARY KEY (id),
  CONSTRAINT fk_single_sport_distance_id FOREIGN KEY (id) REFERENCES distance(id) ON DELETE CASCADE
);

-- Create the triathlon_distance table
CREATE TABLE IF NOT EXISTS triathlon_distance (
  id            BIGINT  NOT NULL,
  swim_distance INTEGER,
  swim_laps     INTEGER,
  bike_distance INTEGER,
  bike_laps     INTEGER,
  run_distance  INTEGER,
  run_laps      INTEGER,
  CONSTRAINT pk_triathlon_distance PRIMARY KEY (id),
  CONSTRAINT fk_triathlon_distance_id FOREIGN KEY (id) REFERENCES distance(id) ON DELETE CASCADE
);

-- Create sequence for the course table primary key
CREATE SEQUENCE IF NOT EXISTS seq_course_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the course table
CREATE TABLE IF NOT EXISTS course (
 id           BIGINT  DEFAULT nextval('seq_course_id')  NOT NULL,
 event_id     BIGINT  NOT NULL,
 title        TEXT    NOT NULL,
 sport        TEXT    NOT NULL,
 distance_id  BIGINT,
 CONSTRAINT pk_course PRIMARY KEY (id),
 CONSTRAINT fk_course_event_id
   FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE,
 CONSTRAINT fk_course_distance_id
   FOREIGN KEY (distance_id) REFERENCES distance(id) ON DELETE CASCADE
);

-- Create a dependency between the seq_course_id sequence and the course table primary key
ALTER SEQUENCE IF EXISTS seq_course_id OWNED BY course.id;

-- Create index on the course table
CREATE INDEX IF NOT EXISTS idx_course_event_id ON course(event_id);

-- Create sequence for the age_group table primary key
CREATE SEQUENCE IF NOT EXISTS seq_age_group_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the age_group table
CREATE TABLE IF NOT EXISTS age_group (
  id          BIGINT  DEFAULT nextval('seq_age_group_id') NOT NULL,
  title       TEXT    NOT NULL,
  short_title TEXT    NOT NULL,
  CONSTRAINT pk_age_group PRIMARY KEY (id)
);

-- Create a dependency between the seq_age_group_id sequence and the age_group table primary key
ALTER SEQUENCE IF EXISTS seq_age_group_id OWNED BY age_group.id;

-- Create sequence for the race table primary key
CREATE SEQUENCE IF NOT EXISTS seq_race_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the race table
CREATE TABLE IF NOT EXISTS race (
  id              BIGINT      DEFAULT nextval('seq_race_id')  NOT NULL,
  race_reference  VARCHAR(18) NOT NULL,
  race_type       TEXT        NOT NULL,
  title           TEXT        NOT NULL,
  subtitle        TEXT        NOT NULL,
  gender_category TEXT        NOT NULL,
  age_group_id    BIGINT      NOT NULL,
  race_date       DATE        NOT NULL,
  race_time       TIME,
  race_status     TEXT        NOT NULL,
  gun_time        TIME,
  air_temperature NUMERIC(5,2),
  CONSTRAINT pk_race PRIMARY KEY (id),
  CONSTRAINT fk_race_age_group_id
    FOREIGN KEY (age_group_id) REFERENCES age_group(id) ON DELETE CASCADE,
  CONSTRAINT uk_race_race_reference UNIQUE (race_reference)
);

-- Create a dependency between the seq_race_id sequence and the race table primary key
ALTER SEQUENCE IF EXISTS seq_race_id OWNED BY race.id;

-- Create index on the race table
CREATE INDEX IF NOT EXISTS idx_race_age_group_id ON race(age_group_id);

-- Create the course_race table
CREATE TABLE IF NOT EXISTS course_race (
  course_id BIGINT NOT NULL,
  race_id   BIGINT NOT NULL,
  CONSTRAINT pk_course_race PRIMARY KEY (course_id, race_id),
  CONSTRAINT fk_course_race_course_id
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE,
  CONSTRAINT fk_course_race_race_id
    FOREIGN KEY (race_id) REFERENCES race(id) ON DELETE CASCADE
);

-- Create the race_hierarchy table
CREATE TABLE IF NOT EXISTS race_hierarchy (
  race_id         BIGINT  NOT NULL,
  parent_race_id  BIGINT  NOT NULL,
  CONSTRAINT pk_race_hierarchy PRIMARY KEY (race_id, parent_race_id),
  CONSTRAINT fk_race_hierarchy_race_id
    FOREIGN KEY (race_id) REFERENCES race(id) ON DELETE CASCADE,
  CONSTRAINT fk_race_hierarchy_race_parent_id
    FOREIGN KEY (parent_race_id) REFERENCES race(id) ON DELETE CASCADE
);

-- Create the triathlon_based_race table
CREATE TABLE IF NOT EXISTS triathlon_based_race (
  id                BIGINT        NOT NULL,
  water_temperature NUMERIC(5,2),
  wetsuit_rule      TEXT          NOT NULL,
  CONSTRAINT pk_triathlon_based_race PRIMARY KEY (id),
  CONSTRAINT fk_triathlon_based_race_id FOREIGN KEY (id) REFERENCES race(id) ON DELETE CASCADE
);

-- Create sequence for the results_file table primary key
CREATE SEQUENCE IF NOT EXISTS seq_results_file_id START WITH 1 INCREMENT BY 1 CACHE 1;

-- Create the results_file table
CREATE TABLE IF NOT EXISTS results_file (
  id        BIGINT      DEFAULT nextval('seq_results_file_id')  NOT NULL,
  race_id   BIGINT      NOT NULL,
  title     TEXT        NOT NULL,
  subtitle  TEXT        NOT NULL,
  file_name VARCHAR(27) NOT NULL,
  revision  INTEGER     NOT NULL,
  is_active BOOLEAN     NOT NULL,
  CONSTRAINT pk_results_file PRIMARY KEY (id),
  CONSTRAINT fk_results_file_race_id FOREIGN KEY (race_id) REFERENCES race(id) ON DELETE CASCADE,
  CONSTRAINT uk_results_file_file_name UNIQUE (file_name)
);

-- Create a dependency between the seq_results_file_id sequence and the results_file table primary key
ALTER SEQUENCE IF EXISTS seq_results_file_id OWNED BY results_file.id;

-- Create index on the results_file table
CREATE INDEX IF NOT EXISTS idx_results_file_race_id ON results_file(race_id);

-- Create the tracker_account table
CREATE TABLE IF NOT EXISTS tracker_account (
  owner       TEXT      NOT NULL,
  account_key TEXT      NOT NULL,
  enabled     BOOLEAN   NOT NULL,
  version     INTEGER   NOT NULL DEFAULT 0,
  created_at  TIMESTAMP NOT NULL,
  updated_at  TIMESTAMP,
  CONSTRAINT pk_tracker_account PRIMARY KEY (owner),
  CONSTRAINT uk_tracker_account_key UNIQUE (account_key)
);

-- Create sequence for the tracking_data table primary key
CREATE SEQUENCE IF NOT EXISTS seq_tracking_data_id  START WITH 1 INCREMENT BY 5 CACHE 5;

-- Create the tracking_data table
CREATE TABLE IF NOT EXISTS tracking_data (
  id          BIGINT        DEFAULT nextval('seq_tracking_data_id') NOT NULL,
  account     TEXT          NOT NULL,
  device      TEXT          NOT NULL,
  record_time TIMESTAMP     NOT NULL,
  latitude    NUMERIC(9,6)  NOT NULL,
  longitude   NUMERIC(9,6)  NOT NULL,
  active      BOOLEAN       NOT NULL,
  version     INTEGER       NOT NULL DEFAULT 0,
  created_at  TIMESTAMP     NOT NULL,
  updated_at  TIMESTAMP,
  CONSTRAINT pk_tracking_data PRIMARY KEY (id),
  CONSTRAINT fk_tracking_data_tracker_account_owner
    FOREIGN KEY (account) REFERENCES tracker_account(owner),
  CONSTRAINT uk_tracking_data_account_device_time UNIQUE (account, device, record_time)
);

-- Create a dependency between the seq_tracking_data_id sequence and the tracking_data table primary key
ALTER SEQUENCE IF EXISTS seq_tracking_data_id OWNED BY tracking_data.id;

-- Create indexes on the tracking_data table
CREATE INDEX IF NOT EXISTS idx_tracking_data_device ON tracking_data(device);
CREATE INDEX IF NOT EXISTS idx_tracking_data_device_time ON tracking_data(device, record_time);
CREATE INDEX IF NOT EXISTS idx_tracking_data_account ON tracking_data(account);
CREATE INDEX IF NOT EXISTS idx_tracking_data_account_device ON tracking_data(account, device);
