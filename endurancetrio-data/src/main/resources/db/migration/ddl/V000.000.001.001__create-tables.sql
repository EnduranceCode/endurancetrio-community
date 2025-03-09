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

-- Description: Creates the tables for the EnduranceTrio application

CREATE SCHEMA IF NOT EXISTS endurancetrio;

CREATE SEQUENCE IF NOT EXISTS endurancetrio.sq_event        START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS endurancetrio.sq_organizer    START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS endurancetrio.sq_event_file   START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS endurancetrio.sq_course       START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS endurancetrio.sq_age_group    START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS endurancetrio.sq_distance     START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS endurancetrio.sq_race         START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS endurancetrio.sq_results_file START WITH 1 INCREMENT BY 1;

CREATE TABLE IF NOT EXISTS endurancetrio.event (
    id                BIGINT  PRIMARY KEY,
    event_reference   VARCHAR NOT NULL UNIQUE,
    title             VARCHAR NOT NULL,
    start_date        DATE    NOT NULL,
    end_date          DATE    NOT NULL,
    district          VARCHAR NOT NULL,
    county            VARCHAR NOT NULL,
    city              VARCHAR NOT NULL
);

ALTER TABLE endurancetrio.event ALTER COLUMN id SET DEFAULT NEXT VALUE FOR endurancetrio.sq_event;

CREATE INDEX IF NOT EXISTS endurancetrio.idx_event_reference ON endurancetrio.event(event_reference);

CREATE TABLE IF NOT EXISTS endurancetrio.organizer (
    id              BIGINT  PRIMARY KEY,
    name            VARCHAR NOT NULL,
    district        VARCHAR NOT NULL,
    county          VARCHAR NOT NULL,
    city            VARCHAR NOT NULL,
    organizer_type  VARCHAR NOT NULL
);

ALTER TABLE endurancetrio.organizer ALTER COLUMN id SET DEFAULT NEXT VALUE FOR endurancetrio.sq_organizer;

CREATE TABLE IF NOT EXISTS endurancetrio.event_organizer (
    event_id      BIGINT NOT NULL,
    organizer_id  BIGINT NOT NULL,
    PRIMARY KEY (event_id, organizer_id),
    FOREIGN KEY (event_id) REFERENCES event(id) ON DELETE CASCADE,
    FOREIGN KEY (organizer_id) REFERENCES organizer(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS endurancetrio.event_file (
    id        BIGINT  PRIMARY KEY,
    event_id  BIGINT  NOT NULL,
    title     VARCHAR NOT NULL,
    file_type VARCHAR NOT NULL,
    file_name VARCHAR NOT NULL UNIQUE,
    revision  INT NOT NULL,
    is_active BOOLEAN NOT NULL,
    FOREIGN KEY (event_id) REFERENCES endurancetrio.event(id) ON DELETE CASCADE
);

ALTER TABLE endurancetrio.event_file ALTER COLUMN id SET DEFAULT NEXT VALUE FOR endurancetrio.sq_event_file;

CREATE INDEX IF NOT EXISTS endurancetrio.idx_event_id ON endurancetrio.event_file(event_id);

CREATE TABLE IF NOT EXISTS endurancetrio.distance (
    id            BIGINT PRIMARY KEY,
    distance_type VARCHAR NOT NULL
);

ALTER TABLE endurancetrio.distance ALTER COLUMN id SET DEFAULT NEXT VALUE FOR endurancetrio.sq_distance;

CREATE TABLE IF NOT EXISTS endurancetrio.aquabike_distance (
    id            BIGINT PRIMARY KEY,
    swim_distance INTEGER,
    swim_laps     INTEGER,
    bike_distance INTEGER,
    bike_laps     INTEGER,
    FOREIGN KEY (id) REFERENCES endurancetrio.distance(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS endurancetrio.aquathlon_distance (
    id            BIGINT PRIMARY KEY,
    swim_distance INTEGER,
    swim_laps     INTEGER,
    run_distance  INTEGER,
    run_laps      INTEGER,
    FOREIGN KEY (id) REFERENCES endurancetrio.distance(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS endurancetrio.biathlon_distance (
    id            BIGINT PRIMARY KEY,
    bike_distance INTEGER,
    bike_laps     INTEGER,
    run_distance  INTEGER,
    run_laps      INTEGER,
    FOREIGN KEY (id) REFERENCES endurancetrio.distance(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS endurancetrio.double_biathlon_distance (
    id                    BIGINT PRIMARY KEY,
    first_bike_distance   INTEGER,
    first_bike_laps       INTEGER,
    first_run_distance    INTEGER,
    first_run_laps        INTEGER,
    second_bike_distance  INTEGER,
    second_bike_laps      INTEGER,
    second_run_distance   INTEGER,
    second_run_laps       INTEGER,
    FOREIGN KEY (id) REFERENCES endurancetrio.distance(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS endurancetrio.duathlon_distance (
    id                  BIGINT PRIMARY KEY,
    first_run_distance  INTEGER,
    first_run_laps      INTEGER,
    bike_distance       INTEGER,
    bike_laps           INTEGER,
    second_run_distance INTEGER,
    second_run_laps     INTEGER,
    FOREIGN KEY (id) REFERENCES endurancetrio.distance(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS endurancetrio.single_sport_distance (
    id       BIGINT PRIMARY KEY,
    distance INTEGER,
    laps     INTEGER,
    FOREIGN KEY (id) REFERENCES endurancetrio.distance(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS endurancetrio.triathlon_distance (
    id            BIGINT PRIMARY KEY,
    swim_distance INTEGER,
    swim_laps     INTEGER,
    bike_distance INTEGER,
    bike_laps     INTEGER,
    run_distance  INTEGER,
    run_laps      INTEGER,
    FOREIGN KEY (id) REFERENCES endurancetrio.distance(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS endurancetrio.course (
    id          BIGINT  PRIMARY KEY,
    event_id    BIGINT  NOT NULL,
    title       VARCHAR NOT NULL,
    sport       VARCHAR NOT NULL,
    distance_id BIGINT,
    FOREIGN KEY (event_id) REFERENCES endurancetrio.event(id) ON DELETE CASCADE,
    FOREIGN KEY (distance_id) REFERENCES endurancetrio.distance(id) ON DELETE SET NULL
);

ALTER TABLE endurancetrio.course ALTER COLUMN id SET DEFAULT NEXT VALUE FOR endurancetrio.sq_course;

CREATE INDEX IF NOT EXISTS endurancetrio.idx_event_id ON endurancetrio.course(event_id);

CREATE TABLE IF NOT EXISTS endurancetrio.age_group (
    id          BIGINT  PRIMARY KEY,
    title       VARCHAR NOT NULL,
    short_title VARCHAR NOT NULL
);

ALTER TABLE endurancetrio.age_group ALTER COLUMN id SET DEFAULT NEXT VALUE FOR endurancetrio.sq_age_group;

CREATE TABLE IF NOT EXISTS endurancetrio.race (
    id              BIGINT  PRIMARY KEY,
    race_reference  VARCHAR NOT NULL UNIQUE,
    race_type       VARCHAR NOT NULL,
    title           VARCHAR NOT NULL,
    subtitle        VARCHAR NOT NULL,
    gender_category VARCHAR NOT NULL,
    age_group_id    BIGINT  NOT NULL,
    date            DATE    NOT NULL,
    time            TIME,
    race_status     VARCHAR NOT NULL,
    gun_time        TIME,
    air_temperature DOUBLE PRECISION,
    CONSTRAINT endurancetrio.fk_race_age_group FOREIGN KEY (age_group_id) REFERENCES endurancetrio.age_group(id)
);

ALTER TABLE endurancetrio.race ALTER COLUMN id SET DEFAULT NEXT VALUE FOR endurancetrio.sq_race;

CREATE INDEX IF NOT EXISTS endurancetrio.idx_race_reference ON endurancetrio.race(race_reference);
CREATE INDEX IF NOT EXISTS endurancetrio.idx_age_group_id ON endurancetrio.race(age_group_id);

CREATE TABLE IF NOT EXISTS endurancetrio.course_race (
    course_id BIGINT NOT NULL,
    race_id   BIGINT NOT NULL,
    PRIMARY KEY (course_id, race_id),
    FOREIGN KEY (course_id) REFERENCES endurancetrio.course(id) ON DELETE CASCADE,
    FOREIGN KEY (race_id) REFERENCES endurancetrio.race(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS endurancetrio.race_hierarchy (
    race_id         BIGINT NOT NULL,
    parent_race_id  BIGINT NOT NULL,
    PRIMARY KEY (race_id, parent_race_id),
    CONSTRAINT endurancetrio.fk_race_hierarchy_race FOREIGN KEY (race_id) REFERENCES endurancetrio.race(id),
    CONSTRAINT endurancetrio.fk_race_hierarchy_parent_race FOREIGN KEY (parent_race_id) REFERENCES endurancetrio.race(id)
);

CREATE TABLE endurancetrio.triathlon_based_race (
    id                BIGINT PRIMARY KEY,
    water_temperature DOUBLE PRECISION,
    wetsuit_rule      VARCHAR NOT NULL,
    CONSTRAINT endurancetrio.fk_triathlon_based_race FOREIGN KEY (id) REFERENCES endurancetrio.race(id)
);

CREATE TABLE IF NOT EXISTS endurancetrio.results_file (
    id        BIGINT  PRIMARY KEY,
    race_id   BIGINT  NOT NULL,
    title     VARCHAR NOT NULL,
    subtitle  VARCHAR NOT NULL,
    file_name VARCHAR NOT NULL UNIQUE,
    revision  INTEGER NOT NULL,
    is_active BOOLEAN NOT NULL,
    FOREIGN KEY (race_id) REFERENCES endurancetrio.race(id) ON DELETE CASCADE
);

ALTER TABLE endurancetrio.results_file ALTER COLUMN id SET DEFAULT NEXT VALUE FOR endurancetrio.sq_results_file;

CREATE INDEX IF NOT EXISTS endurancetrio.idx_race_id ON endurancetrio.results_file(race_id);
