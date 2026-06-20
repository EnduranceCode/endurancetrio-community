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

-- Drops tracking_data table and its sequence
DROP TABLE IF EXISTS endurancetrio_hub.tracking_data;
DROP SEQUENCE IF EXISTS endurancetrio_hub.seq_tracking_data_id;

-- Create sequence for the device_telemetry table primary key
CREATE SEQUENCE IF NOT EXISTS endurancetrio_hub.seq_device_telemetry_id
  START WITH 1 INCREMENT BY 5 CACHE 5;

-- Create the device_telemetry table
CREATE TABLE IF NOT EXISTS endurancetrio_hub.device_telemetry (
  id          BIGINT            DEFAULT nextval('endurancetrio_hub.seq_device_telemetry_id')  NOT NULL,
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
    FOREIGN KEY (account) REFERENCES endurancetrio_hub.tracker_account(owner)
);

-- Create indexes on the device_telemetry table
CREATE INDEX IF NOT EXISTS endurancetrio_hub.idx_device_telemetry_device
  ON endurancetrio_hub.device_telemetry(device);
CREATE INDEX IF NOT EXISTS endurancetrio_hub.idx_device_telemetry_device_time
  ON endurancetrio_hub.device_telemetry(device, record_time);
CREATE INDEX IF NOT EXISTS endurancetrio_hub.idx_device_telemetry_account
  ON endurancetrio_hub.device_telemetry(account);
CREATE INDEX IF NOT EXISTS endurancetrio_hub.idx_device_telemetry_account_device
  ON endurancetrio_hub.device_telemetry(account, device);
