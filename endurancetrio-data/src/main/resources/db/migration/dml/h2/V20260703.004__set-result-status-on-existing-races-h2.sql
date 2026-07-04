SET SCHEMA endurancetrio_hub;

UPDATE endurancetrio_hub.race SET result_status = 'INCOMPLETE' WHERE id IN (1, 3);

UPDATE endurancetrio_hub.race SET result_status = 'EMPTY' WHERE id = 2;
