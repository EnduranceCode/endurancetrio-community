-- Description: Moves race 148 (V Triatlo de Peniche, Equipas masculinas) from
-- course 38 to course 37 (Triatlo Standard), correcting the course assignment.

UPDATE endurancetrio_community.course_race SET course_id = 37 WHERE race_id = 148;
