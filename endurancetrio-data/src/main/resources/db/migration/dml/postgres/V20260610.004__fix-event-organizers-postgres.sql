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

-- Description: Corrects organizer assignments for events 53 and 54.
-- Event 53 (Triatlo de Almodôvar): organizer 37 -> 34
-- Event 54 (Triatlo de Coimbra): organizer 37 -> 40

-- Set the search path to the schema `endurancetrio_hub`
SET search_path TO endurancetrio_hub;

UPDATE event_organizer SET organizer_id = 40 WHERE event_id = 54;
UPDATE event_organizer SET organizer_id = 34 WHERE event_id = 53 AND organizer_id = 37;
