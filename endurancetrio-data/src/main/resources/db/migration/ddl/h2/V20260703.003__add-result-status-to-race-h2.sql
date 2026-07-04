SET SCHEMA endurancetrio_hub;

ALTER TABLE endurancetrio_hub.race
  ADD COLUMN result_status VARCHAR(32) NOT NULL DEFAULT 'PENDING' AFTER air_temperature;
