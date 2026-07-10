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

package com.endurancetrio.data.insight.repository;

import com.endurancetrio.data.insight.model.entity.Article;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The {@link ArticleRepository} provides database access for {@link Article} entities.
 */
@Repository
public interface ArticleRepository extends JpaRepository<@NonNull Article, @NonNull Long> {

  /**
   * Returns a {@link Page} of all {@link Article articles} ordered by their published date
   * descending.
   *
   * @param pageable the pagination information
   * @return a {@link Page} of {@link Article articles} ordered by published date descending
   */
  Page<Article> findAllByOrderByPublishedDateDesc(@NonNull Pageable pageable);

  /**
   * Returns a {@link Page} of {@link Article articles} authored by the athlete with the given ID,
   * ordered by published date descending.
   *
   * @param athleteId the ID of the {@link com.endurancetrio.data.competitor.model.entity.Athlete}
   * @param pageable  the pagination information
   * @return a {@link Page} of {@link Article articles} authored by the given athlete
   */
  @Query(
      "SELECT DISTINCT a FROM Article a JOIN a.author au JOIN au.athlete at "
          + "WHERE at.id = :athleteId ORDER BY a.publishedDate DESC"
  )
  Page<Article> findByAthleteId(@Param("athleteId") Long athleteId, @NonNull Pageable pageable);

  /**
   * Returns a {@link Page} of {@link Article articles} associated with a given event ID.
   *
   * @param eventId  the ID of the {@link com.endurancetrio.data.event.model.entity.Event}
   * @param pageable the pagination information
   * @return a {@link Page} of {@link Article articles} associated with the given event
   */
  @Query("SELECT DISTINCT a FROM Article a JOIN a.relatedEvents e WHERE e.id = :eventId")
  Page<Article> findByEventId(@Param("eventId") Long eventId, @NonNull Pageable pageable);

  /**
   * Finds an {@link Article} by its unique URL slug.
   *
   * @param slug the URL slug of the {@link Article}
   * @return an {@link Optional} containing the {@link Article} if found, or empty if not found
   */
  Optional<Article> findBySlug(String slug);
}
