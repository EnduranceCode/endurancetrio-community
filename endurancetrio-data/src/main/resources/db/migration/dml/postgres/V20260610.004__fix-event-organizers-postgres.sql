-- Description: Corrects organizer assignments for events 53 and 54.
-- Event 53 (Triatlo de Almodôvar): organizer 37 -> 34
-- Event 54 (Triatlo de Coimbra): organizer 37 -> 40

SET search_path TO endurancetrio_community;

UPDATE event_organizer SET organizer_id = 40 WHERE event_id = 54;
UPDATE event_organizer SET organizer_id = 34 WHERE event_id = 53 AND organizer_id = 37;
