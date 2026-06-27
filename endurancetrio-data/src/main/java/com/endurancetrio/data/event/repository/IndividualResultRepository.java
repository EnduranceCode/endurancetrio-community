/*
 * Copyright (c) 2011-2026 Ricardo do Canto
 *
 * This file is part of the EnduranceTrio project.
 *
 * Licensed under the Functional Software License (FSL), Version 1.1, ALv2 Future License
 * (the "License");
 *
 * You may not use this file except in compliance with the License. You may obtain a copy
 * of the License at https://fsl.software/
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND WITHOUT WARRANTIES OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING WITHOUT LIMITATION WARRANTIES OF FITNESS FOR A PARTICULAR
 * PURPOSE, MERCHANTABILITY, TITLE OR NON-INFRINGEMENT.
 *
 * IN NO EVENT WILL WE HAVE ANY LIABILITY TO YOU ARISING OUT OF OR RELATED TO THE
 * SOFTWARE, INCLUDING INDIRECT, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES,
 * EVEN IF WE HAVE BEEN INFORMED OF THEIR POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.data.event.repository;

import com.endurancetrio.data.event.model.entity.IndividualResult;
import com.endurancetrio.data.event.model.entity.Race;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The {@link IndividualResultRepository} provides database access for {@link IndividualResult}
 * entities.
 */
@Repository
public interface IndividualResultRepository
    extends JpaRepository<@NonNull IndividualResult, @NonNull Long> {

  /**
   * Returns a {@link Page} of {@link Race races} that have at least one {@link IndividualResult}
   * recorded for the athlete with the given {@code athleteId}, ordered by their
   * {@link Race#getDate() date} descending and, as a tiebreaker, by their {@link Race#getId() id}
   * descending.
   *
   * @param athleteId the ID of the athlete to filter races by
   * @param pageable  the pagination information
   * @return a {@link Page} of {@link Race races} for the given athlete, ordered by date and, as a
   * tiebreaker, by id descending
   */
  @Query(
      value = "SELECT DISTINCT r FROM Race r "
          + "LEFT JOIN FETCH r.courses c "
          + "LEFT JOIN FETCH c.event e "
          + "WHERE EXISTS ("
          + "  SELECT 1 FROM IndividualResult ir "
          + "  WHERE ir.race = r AND ir.athlete.id = :athleteId"
          + ") "
          + "ORDER BY r.date DESC, r.id DESC",
      countQuery = "SELECT COUNT(DISTINCT r) FROM Race r "
          + "WHERE EXISTS ("
          + "  SELECT 1 FROM IndividualResult ir "
          + "  WHERE ir.race = r AND ir.athlete.id = :athleteId"
          + ")"
  )
  Page<Race> findRacesByAthleteId(@Param("athleteId") Long athleteId, Pageable pageable);
}
