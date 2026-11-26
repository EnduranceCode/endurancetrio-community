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

import com.endurancetrio.business.competitor.mapper.TeamMapper;
import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.dto.RelayResultDTO;
import com.endurancetrio.data.event.model.entity.IndividualResult;
import com.endurancetrio.data.event.model.entity.TeamResult;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

/**
 * The {@link RelayResultMapper} maps a stored relay {@link TeamResult} to a {@link RelayResultDTO}.
 */
@Component
public class RelayResultMapper {

  private final IndividualResultMapper individualResultMapper;
  private final TeamMapper teamMapper;

  public RelayResultMapper(IndividualResultMapper individualResultMapper, TeamMapper teamMapper) {
    this.individualResultMapper = individualResultMapper;
    this.teamMapper = teamMapper;
  }

  /**
   * Maps a {@link TeamResult} entity to a {@link RelayResultDTO}.
   * <p>
   * If the entity has a {@link TeamResult#getSourceResult() source result} (a derived relay race),
   * the classification and performance values are read from that source - the original result from
   * the feeder race - while the current row's id, rank, team name and athlete count are preserved.
   * The race context is always the given {@code race}. The {@code team} and the contributing
   * individual results are resolved through the same source chain, as in
   * {@link IndividualResultMapper}.
   * <p>
   * The given {@code rank} is the classification position to display: the stored rank for the OPEN
   * group, or the age-group position when the results are grouped by age group. As in
   * {@link IndividualResultMapper}, it is only kept when the entity has a stored rank; an unranked
   * row stays unranked. The {@code gap} is the time difference between the row's total and
   * {@code winnerTotal}, resolved through the source-result chain.
   * <p>
   * Contributors are mapped through
   * {@link IndividualResultMapper#map(RaceDTO, IndividualResult, Integer, Duration)} and ordered by
   * rank (unranked last). Contributor gaps are left unset because there is no race winner reference
   * on a relay row.
   *
   * @param race        the race context to attach to the result DTO
   * @param entity      the relay team result entity to map; may be null
   * @param rank        the classification position to display
   * @param winnerTotal the overall winner's total duration, used for gap computation
   * @return the mapped {@link RelayResultDTO}, or {@code null} if {@code entity} is null
   */
  public RelayResultDTO map(RaceDTO race, TeamResult entity, Integer rank, Duration winnerTotal) {
    if (entity == null) {
      return null;
    }

    Integer displayedRank = entity.getRank() == null ? null : rank;
    Duration gap = getGap(entity, winnerTotal);

    TeamResult source = entity.getSourceResult() == null ? entity : entity.getSourceResult();

    List<IndividualResultDTO> contributors = mapContributors(race, source);

    return new RelayResultDTO(entity.getId(), race, displayedRank, source.getPenalty(),
        teamMapper.map(source.getTeam()), entity.getTeamName(), source.getAgeGroup(), source.getParaClass(),
        source.getBib(), entity.getAthletesCount(), source.getSwim(), source.getFirstBike(), source.getFirstRun(),
        source.getT1(), source.getBike(), source.getT2(), source.getRun(), source.getSecondRun(), source.getT3(),
        source.getSecondBike(), source.getTotal(), gap,
        contributors
    );
  }

  /**
   * Computes the time gap between the given relay result and the winner.
   * <p>
   * The total duration is resolved from the source-result chain: if the entity has a
   * {@link TeamResult#getSourceResult() source result}, that source's total is used; otherwise the
   * entity's own total is used.
   *
   * @param entity      the relay result to compute the gap for, must not be null
   * @param winnerTotal the overall winner's total duration
   * @return the gap as a {@link Duration}, or {@code null} if either total is null
   */
  private static @Nullable Duration getGap(TeamResult entity, Duration winnerTotal) {
    TeamResult result = entity.getSourceResult() == null ? entity : entity.getSourceResult();

    if (result.getTotal() == null || winnerTotal == null) {
      return null;
    }
    return result.getTotal().minus(winnerTotal);
  }

  /**
   * Maps the contributing individual results of a relay row, ordered by rank (unranked last).
   * <p>
   * A {@link TeamResult} normally initialises its contributor collection and JPA populates it even
   * when empty, but the setter accepts null, so both null and empty collections yield an empty
   * list instead of a null pointer exception.
   *
   * @param race   the race context to attach to the contributor DTOs
   * @param entity the relay team result whose contributors are to be mapped
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
