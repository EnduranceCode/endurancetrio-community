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

-- Description: Creates the EnduranceTrio application route database tables
--

-- Create sequence for the route table primary key
CREATE SEQUENCE IF NOT EXISTS endurancetrio_hub.seq_route_id START WITH 1 INCREMENT BY 5;

-- Create the route table
CREATE TABLE IF NOT EXISTS endurancetrio_hub.route (
  id          BIGINT      DEFAULT nextval('endurancetrio_hub.seq_route_id') NOT NULL,
  reference   VARCHAR(50) NOT NULL,
  version     INTEGER     NOT NULL DEFAULT 0,
  created_at  TIMESTAMP   NOT NULL,
  updated_at  TIMESTAMP,
  CONSTRAINT pk_route PRIMARY KEY (id),
  CONSTRAINT uk_route_reference UNIQUE (reference)
);

-- Create sequence for the route_segment table primary key
CREATE SEQUENCE IF NOT EXISTS endurancetrio_hub.seq_route_segment_id
  START WITH 1 INCREMENT BY 5;

-- Create the route_segment table
CREATE TABLE IF NOT EXISTS endurancetrio_hub.route_segment (
  id            BIGINT      DEFAULT nextval('endurancetrio_hub.seq_route_segment_id') NOT NULL,
  route_id      BIGINT      NOT NULL,
  segment_order INTEGER     NOT NULL,
  start_device  VARCHAR(50) NOT NULL,
  end_device    VARCHAR(50) NOT NULL,
  version       INTEGER     NOT NULL DEFAULT 0,
  created_at    TIMESTAMP   NOT NULL,
  updated_at    TIMESTAMP,
  CONSTRAINT pk_route_segment PRIMARY KEY (id),
  CONSTRAINT uk_route_segment_route_id_segment_order UNIQUE (route_id, segment_order)
);

-- Create indexes on the route_segment table
CREATE INDEX IF NOT EXISTS idx_route_segment_route_id
  ON endurancetrio_hub.route_segment(route_id);
CREATE INDEX IF NOT EXISTS idx_route_segment_route_id_segment_order
  ON endurancetrio_hub.route_segment(route_id, segment_order);
