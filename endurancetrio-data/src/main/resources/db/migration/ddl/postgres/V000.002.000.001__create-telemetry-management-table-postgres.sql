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

-- Description: Creates the EnduranceTrio application telemetry management database table
--

-- The schema `endurancetrio_hub` must exist before running this script
SET search_path TO endurancetrio_hub;

-- Drops tracking_data table and its sequence
DROP TABLE IF EXISTS tracking_data;
DROP SEQUENCE IF EXISTS seq_tracking_data_id;

-- Create sequence for device_telemetry table primary key
CREATE SEQUENCE IF NOT EXISTS seq_device_telemetry_id START WITH 1 INCREMENT BY 5 CACHE 5;

-- Create the device_telemetry table
CREATE TABLE IF NOT EXISTS device_telemetry (
  id          BIGINT            DEFAULT nextval('seq_device_telemetry_id') NOT NULL,
  account     VARCHAR(50)       NOT NULL,
  device      VARCHAR(50)       NOT NULL,
  record_time TIMESTAMP         NOT NULL,
  latitude    DOUBLE PRECISION  NOT NULL,
  longitude   DOUBLE PRECISION  NOT NULL,
  active      BOOLEAN           NOT NULL,
  version     INTEGER           NOT NULL DEFAULT 0,
  created_at  TIMESTAMP         NOT NULL,
  updated_at  TIMESTAMP,
  CONSTRAINT pk_device_telemetry PRIMARY KEY (id),
  CONSTRAINT fk_device_telemetry_tracker_account_owner
    FOREIGN KEY (account) REFERENCES tracker_account(owner)
);

-- Create a dependency between the seq_device_telemetry_id sequence and the device_telemetry table primary key
ALTER SEQUENCE IF EXISTS seq_device_telemetry_id OWNED BY device_telemetry.id;

-- Create indexes on the device_telemetry table
CREATE INDEX IF NOT EXISTS idx_device_telemetry_device ON device_telemetry(device);
CREATE INDEX IF NOT EXISTS idx_device_telemetry_device_time ON device_telemetry(device, record_time);
CREATE INDEX IF NOT EXISTS idx_device_telemetry_account ON device_telemetry(account);
CREATE INDEX IF NOT EXISTS idx_device_telemetry_account_device ON device_telemetry(account, device);
