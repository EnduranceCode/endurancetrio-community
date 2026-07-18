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

import com.endurancetrio.data.event.model.entity.Distance;
import com.endurancetrio.data.event.model.entity.Event;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

  /**
   * Finds an {@link Event} by its ID and year with the full object graph eagerly fetched (courses,
   * distances, and races). This avoids N+1 queries and ensures Hibernate constructs the correct
   * concrete subclass instances for the joined-table inheritance hierarchy of {@link Distance}.
   *
   * @param id   the event ID
   * @param year the year to filter events by
   * @return the event with its associated courses, distances, and races, or empty if not found
   */
  @Query(
      "SELECT DISTINCT e FROM Event e " + "LEFT JOIN FETCH e.courses c "
          + "LEFT JOIN FETCH c.distance " + "LEFT JOIN FETCH c.races "
          + "LEFT JOIN FETCH e.eventFiles " + "WHERE e.id = :id AND YEAR(e.startDate) = :year"
  )
  Optional<Event> findByIdAndYearWithGraph(@Param("id") Long id, @Param("year") int year);

  /**
   * Returns all distinct years extracted from the {@link Event#getStartDate() startDate} of
   * existing {@link Event events}, ordered descending.
   *
   * @return a list of distinct years in descending order
   */
  @Query("SELECT DISTINCT YEAR(e.startDate) FROM Event e ORDER BY YEAR(e.startDate) DESC")
  List<Integer> findDistinctYears();

  /**
   * Returns a {@link List} of {@link Event events} matching the given IDs, with their courses
   * eagerly fetched to avoid N+1 queries when mapping to DTOs that require sport codes.
   * <p>
   * The returned list may not preserve the order of the input IDs. The caller is responsible for
   * restoring the desired order after fetching.
   *
   * @param ids the IDs of the events to fetch
   * @return a {@link List} of {@link Event events} with courses loaded
   */
  @Query(
      "SELECT DISTINCT e FROM Event e LEFT JOIN FETCH e.courses WHERE e.id IN :ids"
  )
  List<Event> findEventsByIdInWithCourses(@Param("ids") Collection<Long> ids);

  /**
   * Returns a {@link Page} of IDs of the most recently added {@link Event events}, ordered by their
   * {@link com.endurancetrio.data.common.model.entity.AuditableEntity#getCreatedAt() createdAt}
   * descending and, as a tiebreaker, by {@link Event#getId() id} descending.
   * <p>
   * This query selects only IDs to avoid the PostgreSQL restriction that ORDER BY expressions
   * must appear in the SELECT list when DISTINCT is used, which occurs when fetching collections
   * with JOIN FETCH. Use {@link #findEventsByIdInWithCourses(Collection)} to load the full
   * entity graph for the returned IDs.
   *
   * @param pageable the pagination information; use
   *                 {@link PageRequest#of(int, int) PageRequest.of(0, limit)} to retrieve the most
   *                 recent N events
   * @return a {@link Page} of event IDs
   */
  @Query(
      value = "SELECT e.id FROM Event e ORDER BY e.createdAt DESC, e.id DESC",
      countQuery = "SELECT COUNT(e.id) FROM Event e"
  )
  Page<Long> findMostRecentAddedEventIds(Pageable pageable);
}
