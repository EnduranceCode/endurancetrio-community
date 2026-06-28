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
 * EVEN IF YOU HAVE BEEN INFORMED OF THE POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.data.event.repository;

import com.endurancetrio.data.event.model.entity.Race;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The {@link RaceRepository} provides database access for {@link Race} entities.
 */
@Repository
public interface RaceRepository extends JpaRepository<@NonNull Race, @NonNull Long> {

  /**
   * Finds a {@link Race} by its ID with its courses and their distances eagerly fetched, avoiding
   * N+1 queries when accessing the full race object graph.
   *
   * @param id the ID of the race to find
   * @return the {@link Race} with its courses and distances loaded, or empty if not found
   */
  @Query(
      "SELECT r FROM Race r "
          + "LEFT JOIN FETCH r.courses c "
          + "LEFT JOIN FETCH c.distance "
          + "WHERE r.id = :id"
  )
  Optional<Race> findByIdWithGraph(@Param("id") Long id);
}
