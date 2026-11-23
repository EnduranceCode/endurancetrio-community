/*
 * Copyright (c) 2011-2026 Ricardo do Canto
 *
 * This file is part of the EnduranceTrio project.
 *
 * Licensed under the Functional Software License (FSL), Version 1.1, ALv2 Future License
 * (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://fsl.software/
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

import com.endurancetrio.data.event.model.entity.TeamResult;
import com.endurancetrio.data.event.repository.projection.ResultPlacement;
import java.util.Collection;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * The {@link TeamResultRepository} provides database access for {@link TeamResult} entities.
 */
@Repository
public interface TeamResultRepository extends JpaRepository<@NonNull TeamResult, @NonNull Long> {

  /**
   * Returns the {@link TeamResult team results} of the race with the given {@code raceId}, with
   * their contributing individual results eagerly fetched.
   * <p>
   * The {@code individualResults} collection and, for each contributor, the athlete, team and
   * source result are fetched to avoid N+1 queries when mapping to DTOs. For derived results
   * (where a contributor's {@code sourceResult} is not null), the performance fields must be read
   * from the source result. Rows are ordered by rank ascending.
   *
   * @param raceId the ID of the race to fetch team results for
   * @return the team results of the given race, in classification order, possibly empty
   */
  @Query(
    "SELECT DISTINCT tr FROM TeamResult tr "
      + "LEFT JOIN FETCH tr.individualResults ir "
      + "LEFT JOIN FETCH ir.athlete "
      + "LEFT JOIN FETCH ir.team "
      + "LEFT JOIN FETCH ir.sourceResult sr "
      + "LEFT JOIN FETCH sr.team "
      + "WHERE tr.race.id = :raceId "
      + "ORDER BY tr.rank ASC"
  )
  List<TeamResult> findByRaceIdWithGraph(@Param("raceId") Long raceId);

  /**
   * Returns the team placement (rank and penalty) for each of the given individual results that is
   * linked to a {@link TeamResult} in the <em>same</em> race.
   * <p>
   * The {@code tr.race = ir.race} guard excludes the cross-race team classifications
   * ({@code TEAM_BY_RANK}, {@code TEAM_BY_TIME}, {@code TEAM_BY_POINTS}), for which the individual
   * result belongs to an individual race and the team result to the team race.
   * <p>
   * Only a scalar projection is selected, so no entity is hydrated and no collection is initialised.
   * The caller must avoid passing an empty collection; the query is not defined for it.
   *
   * @param individualResultIds the IDs of the individual results to resolve
   * @return the matching placements, possibly empty
   */
  @Query(
      "SELECT ir.id AS individualResultId, tr.rank AS rank, tr.penalty AS penalty "
          + "FROM TeamResult tr JOIN tr.individualResults ir "
          + "WHERE ir.id IN :individualResultIds AND tr.race = ir.race"
  )
  List<ResultPlacement> findTeamPlacementsByIndividualResultIds(
      @Param("individualResultIds") Collection<Long> individualResultIds
  );
}
