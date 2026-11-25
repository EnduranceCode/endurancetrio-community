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

package com.endurancetrio.business.event.mapper;

import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.dto.TeamResultDTO;
import com.endurancetrio.data.event.model.entity.IndividualResult;
import com.endurancetrio.data.event.model.entity.TeamResult;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

@Component
public class TeamResultMapper {

  private final IndividualResultMapper individualResultMapper;

  public TeamResultMapper(IndividualResultMapper individualResultMapper) {
    this.individualResultMapper = individualResultMapper;
  }

  /**
   * Maps a {@link TeamResult} entity to a {@link TeamResultDTO}.
   * <p>
   * The winner is the first row of the classification (lowest rank), so its values are the gap
   * reference. For {@code TEAM_BY_RANK} the reference is the smallest sum of the contributors'
   * ranks ({@link TeamResult#getCumulativeRank() cumulativeRank}) and the gap is
   * {@code team - winner}; for {@code TEAM_BY_TIME} it is the lowest sum of the contributors' times
   * ({@link TeamResult#getTotal() total}) with the same gap direction; for {@code TEAM_BY_POINTS}
   * it is the highest sum of the contributors' points ({@link TeamResult#getPoints() points}) and
   * the gap is reversed ({@code winner - team}) so trailing teams have a positive gap. Only the
   * gap component matching the race type is populated. The winner's own gap is zero, and a gap is
   * null when either value is missing.
   * <p>
   * Contributors are mapped through
   * {@link IndividualResultMapper#map(RaceDTO, IndividualResult, Integer, Duration)} and ordered by
   * rank (unranked last). Their own gaps are left unset because the team reference is an aggregate,
   * not an individual time.
   *
   * @param race   the race context to attach to the result DTO
   * @param entity the team result entity to map; may be null
   * @param winner the winning team result, used as the gap reference
   * @return the mapped {@link TeamResultDTO}, or {@code null} if {@code entity} is null
   */
  public TeamResultDTO map(RaceDTO race, TeamResult entity, TeamResult winner) {
    if (entity == null) {
      return null;
    }

    List<IndividualResultDTO> individualResults = mapContributors(race, entity);

    Duration totalGap = null;
    Integer pointsGap = null;
    Integer cumulativeRankGap = null;

    switch (race.raceType()) {
      case TEAM_BY_RANK -> cumulativeRankGap = getGap(entity.getCumulativeRank(), winner.getCumulativeRank());
      case TEAM_BY_TIME -> totalGap = getGap(entity.getTotal(), winner.getTotal());
      case TEAM_BY_POINTS -> pointsGap = getGap(winner.getPoints(), entity.getPoints());
      default -> throw new IllegalArgumentException("Unsupported collective race type: " + race.raceType());
    }

    return new TeamResultDTO(entity.getId(), race, entity.getRank(), entity.getTeamName(), entity.getBib(),
      entity.getAthletesCount(), entity.getTotal(), totalGap, entity.getPoints(), pointsGap, entity.getCumulativeRank(),
      cumulativeRankGap, individualResults
    );
  }

  /**
   * Returns the difference between a duration and the reference duration.
   *
   * @param value     the duration to subtract from
   * @param reference the reference duration to subtract
   * @return the difference, or {@code null} if either duration is null
   */
  private static @Nullable Duration getGap(Duration value, Duration reference) {
    if (value == null || reference == null) {
      return null;
    }
    return value.minus(reference);
  }

  /**
   * Returns the difference between an integer metric and the reference metric. Callers pass the
   * winner first for {@code TEAM_BY_POINTS}, where higher is better, so trailing teams get a
   * positive gap.
   *
   * @param value     the metric to subtract from
   * @param reference the reference metric to subtract
   * @return the difference, or {@code null} if either metric is null
   */
  private static @Nullable Integer getGap(Integer value, Integer reference) {
    if (value == null || reference == null) {
      return null;
    }
    return value - reference;
  }

  /**
   * Maps the contributing individual results of a team result, ordered by rank (unranked last).
   * <p>
   * A {@link TeamResult} normally initialises its contributor collection and JPA populates it even
   * when empty, but the setter accepts null, so both null and empty collections yield an empty
   * list instead of a null pointer exception.
   *
   * @param race   the race context to attach to the contributor DTOs
   * @param entity the team result whose contributors are to be mapped
   * @return the mapped contributors, possibly empty
   */
  private List<IndividualResultDTO> mapContributors(RaceDTO race, TeamResult entity) {
    if (entity.getIndividualResults() == null || entity.getIndividualResults().isEmpty()) {
      return List.of();
    }

    return entity.getIndividualResults()
      .stream()
      .sorted(Comparator.comparing(IndividualResult::getRank, Comparator.nullsLast(Integer::compareTo)))
      .map(result -> individualResultMapper.map(race, result, result.getRank(), null))
      .toList();
  }
}
