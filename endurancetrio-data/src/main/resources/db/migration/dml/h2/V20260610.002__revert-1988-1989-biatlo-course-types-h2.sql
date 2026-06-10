-- Description: Reverts courses incorrectly changed from DUATHLON to BIATHLON
-- for the 1988/1989 biatlo events, restoring original sport and titles.

UPDATE
  endurancetrio_community.course
SET
  sport = 'DUATHLON',
  title = CASE id
            WHEN 41 THEN 'Duatlo Sprint'
            WHEN 42 THEN 'Duatlo Jovem'
            WHEN 45 THEN 'Duatlo Sprint'
            ELSE 'Duatlo Standard'
          END
WHERE
  id IN (25, 27, 36, 41, 42, 43, 44, 45, 46, 47, 53);
