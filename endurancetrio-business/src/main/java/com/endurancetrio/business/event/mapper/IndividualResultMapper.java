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

import com.endurancetrio.business.competitor.mapper.AthleteMapper;
import com.endurancetrio.business.competitor.mapper.TeamMapper;
import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.data.event.model.entity.IndividualResult;
import java.time.Duration;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Component;

@Component
public class IndividualResultMapper {

  private final AthleteMapper athleteMapper;
  private final TeamMapper teamMapper;

  public IndividualResultMapper(AthleteMapper athleteMapper, TeamMapper teamMapper) {
    this.athleteMapper = athleteMapper;
    this.teamMapper = teamMapper;
  }

  /**
   * Maps an {@link IndividualResult} entity to an {@link IndividualResultDTO}.
   * <p>
   * If the entity has a {@link IndividualResult#getSourceResult() source result} (indicating an
   * inherited competition entry), the data is taken from that source - the original result from the
   * feeder race — while the given entity's own {@code rank} is preserved for the current race's
   * classification.
   * <p>
   * The time {@code gap} relative to the overall winner is computed via {@link #getGap}.
   *
   * @param race        the race context to attach to the result DTO
   * @param entity      the individual result entity to map; may be null. Its {@code id} is always
   *                    used as the DTO's id, regardless of the source-result chain
   * @param rank        the rank assigned in the current race (may be overridden if the entity
   *                    already has a rank)
   * @param winnerTotal the overall winner's total duration, used for gap computation
   * @return the mapped {@link IndividualResultDTO}, or {@code null} if {@code entity} is null
   */
  public IndividualResultDTO map(
      RaceDTO race, IndividualResult entity, Integer rank, Duration winnerTotal) {
    if (entity == null) {
      return null;
    }

    rank = entity.getRank() == null ? null : rank;
    Duration gap = getGap(entity, winnerTotal);

    IndividualResult sourceEntity = entity.getSourceResult();

    if (sourceEntity == null) {
      return new IndividualResultDTO(entity.getId(), race, rank, entity.getPenalty(),
          athleteMapper.map(entity.getAthlete()), entity.getAgeGroup(), entity.getParaClass(),
          teamMapper.map(entity.getTeam(), entity.getTeamName()), entity.getBib(), entity.getSwim(),
          entity.getFirstBike(), entity.getFirstRun(), entity.getT1(), entity.getBike(),
          entity.getT2(), entity.getRun(), entity.getSecondRun(), entity.getT3(),
          entity.getSecondBike(), entity.getTotal(), gap, entity.getPoints()
      );
    }

    return new IndividualResultDTO(entity.getId(), race, rank, sourceEntity.getPenalty(),
        athleteMapper.map(entity.getAthlete()), sourceEntity.getAgeGroup(),
        sourceEntity.getParaClass(),
        teamMapper.map(sourceEntity.getTeam(), sourceEntity.getTeamName()), sourceEntity.getBib(),
        sourceEntity.getSwim(), sourceEntity.getFirstBike(), sourceEntity.getFirstRun(),
        sourceEntity.getT1(), sourceEntity.getBike(), sourceEntity.getT2(), sourceEntity.getRun(),
        sourceEntity.getSecondRun(), sourceEntity.getT3(), sourceEntity.getSecondBike(),
        sourceEntity.getTotal(), gap, sourceEntity.getPoints()
    );
  }

  /**
   * Computes the time gap between the given result and the overall winner.
   * <p>
   * The total duration is resolved from the source-result chain: if the entity has a
   * {@link IndividualResult#getSourceResult()}, that source's total is used; otherwise the entity's
   * own total is used.
   *
   * @param entity      the individual result to compute the gap for, must not be null
   * @param winnerTotal the overall winner's total duration
   * @return the gap as a {@link Duration}, or {@code null} if either total is null
   */
  private static @Nullable Duration getGap(IndividualResult entity, Duration winnerTotal) {
    IndividualResult result = entity.getSourceResult() == null ? entity : entity.getSourceResult();

    if (result.getTotal() == null || winnerTotal == null) {
      return null;
    }
    return result.getTotal().minus(winnerTotal);
  }
}
