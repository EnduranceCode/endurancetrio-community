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

import com.endurancetrio.data.event.model.entity.Event;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The {@link EventRepository} provides database access for {@link Event} entities.
 */
@Repository
public interface EventRepository extends JpaRepository<@NonNull Event, @NonNull Long> {

  /**
   * Returns all distinct years extracted from the {@link Event#getStartDate() startDate} of
   * existing {@link Event events}, ordered descending.
   *
   * @return a list of distinct years in descending order
   */
  @Query("SELECT DISTINCT YEAR(e.startDate) FROM Event e ORDER BY YEAR(e.startDate) DESC")
  List<Integer> findDistinctYears();

  /**
   * Returns a {@link Page} of {@link Event events} whose {@link Event#getStartDate() startDate}
   * falls within the given {@code year}, ordered by their start date descending.
   *
   * @param year     the year to filter events by
   * @param pageable the pagination information
   * @return a {@link Page} of {@link Event events} for the given year, ordered by start date
   * descending
   */
  @Query(
      value = "SELECT DISTINCT e FROM Event e LEFT JOIN FETCH e.courses "
          + "WHERE YEAR(e.startDate) = :year ORDER BY e.startDate DESC",
      countQuery = "SELECT COUNT(DISTINCT e) FROM Event e WHERE YEAR(e.startDate) = :year"
  )
  Page<Event> findByEventYear(@Param("year") int year, Pageable pageable);
}
