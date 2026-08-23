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

package com.endurancetrio.data.competitor.repository;

import com.endurancetrio.data.competitor.model.entity.Athlete;
import com.endurancetrio.data.competitor.repository.projection.AthleteFirstLetterCount;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AthleteRepository extends JpaRepository<@NonNull Athlete, @NonNull Long>,
    JpaSpecificationExecutor<Athlete> {

  /**
   * Returns a {@link Page} of all {@link Athlete athletes} ordered by their
   * {@link Athlete#getKnownName() knownName} in ascending order.
   *
   * @param pageable the pagination information
   * @return a {@link Page} of all {@link Athlete athletes} ordered by known name
   */
  @Query("SELECT a FROM Athlete a ORDER BY a.knownName")
  Page<Athlete> findAllOrderedByKnownName(Pageable pageable);

  /**
   * Returns the global count of athletes for each accent-folded first letter of their known name.
   *
   * @return the first-letter histogram
   */
  @Query(
      value = """
          SELECT SUBSTRING(TRANSLATE(LOWER(a.known_name),
            'áàâãäåéèêëíìîïóòôõöøúùûüýÿñçšž',
            'aaaaaaeeeeiiiiooooouuuuyyyncsz'), 1, 1) AS letter,
                 COUNT(a.id) AS total
          FROM {h-schema}athlete a
          GROUP BY SUBSTRING(TRANSLATE(LOWER(a.known_name),
            'áàâãäåéèêëíìîïóòôõöøúùûüýÿñçšž',
            'aaaaaaeeeeiiiiooooouuuuyyyncsz'), 1, 1)
          """, nativeQuery = true
  )
  List<AthleteFirstLetterCount> findGlobalFirstLetterCounts();
}
