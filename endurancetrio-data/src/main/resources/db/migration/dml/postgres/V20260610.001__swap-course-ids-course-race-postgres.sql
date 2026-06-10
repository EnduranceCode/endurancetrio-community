-- Description: Swaps course_id values 6 and 7 to 7 and 8 respectively in the
-- course_race junction table. Order is important: 7→8 first to free course_id 7,
-- then 6→7. Course 8 has no existing course_race entries so no PK conflicts arise.

SET search_path TO endurancetrio_community;

UPDATE course_race SET course_id = 8 WHERE course_id = 7;
UPDATE course_race SET course_id = 7 WHERE course_id = 6;
