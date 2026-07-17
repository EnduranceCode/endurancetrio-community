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

package com.endurancetrio.business.event.service;

import com.endurancetrio.business.common.dto.ErrorDTO;
import com.endurancetrio.business.common.exception.EnduranceTrioError;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.dto.RaceResultsDTO;
import com.endurancetrio.business.event.enumerator.RaceTypeGroup;
import com.endurancetrio.business.event.mapper.IndividualResultMapper;
import com.endurancetrio.business.event.mapper.RaceMapper;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.event.model.entity.IndividualResult;
import com.endurancetrio.data.event.model.entity.Race;
import com.endurancetrio.data.event.repository.IndividualResultRepository;
import com.endurancetrio.data.event.repository.RaceRepository;
import java.time.Duration;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RaceServiceMain implements RaceService {

  private static final Logger LOG = LoggerFactory.getLogger(RaceServiceMain.class);

  private final IndividualResultRepository individualResultRepository;
  private final IndividualResultMapper individualResultMapper;
  private final RaceRepository raceRepository;
  private final RaceMapper raceMapper;

  @Autowired
  public RaceServiceMain(
      IndividualResultRepository individualResultRepository,
      IndividualResultMapper individualResultMapper,
      RaceRepository raceRepository,
      RaceMapper raceMapper
  ) {
    this.individualResultRepository = individualResultRepository;
    this.individualResultMapper = individualResultMapper;
    this.raceRepository = raceRepository;
    this.raceMapper = raceMapper;
  }

  @Override
  @Cacheable(
      value = "nonDerivedRecentRacesWithMostRecentAddedResults",
      key = "#pageable.pageNumber + '-' + #pageable.pageSize"
  )
  @Transactional(readOnly = true)
  public Page<RaceDTO> getNonDerivedRacesWithMostRecentAddedResults(Pageable pageable) {
    Page<Long> raceIds = raceRepository.findNonDerivedRaceIdsWithMostRecentAddedResults(pageable);

    if (raceIds.isEmpty()) {
      return new PageImpl<>(List.of(), pageable, 0);
    }

    List<Long> orderedRaceIds = raceIds.getContent();
    List<Race> raceEntities = raceRepository.findRacesByIdInWithCoursesAndEvent(orderedRaceIds);

    Map<Long, Race> raceEntitiesMap = HashMap.newHashMap(raceEntities.size());
    for (Race race : raceEntities) {
      raceEntitiesMap.put(race.getId(), race);
    }

    List<RaceDTO> races = orderedRaceIds.stream()
        .map(raceEntitiesMap::get)
        .filter(Objects::nonNull)
        .map(raceMapper::mapWithoutDistanceWithEvent)
        .toList();

    return new PageImpl<>(races, pageable, raceIds.getTotalElements());
  }

  @Override
  @Cacheable(value = "raceResults", key = "#race.id()")
  @Transactional(readOnly = true)
  public RaceResultsDTO getRaceResults(RaceDTO race) {
    try {
      return switch (RaceTypeGroup.valueOf(race.raceTypeGroup())) {
        case RaceTypeGroup.INDIVIDUAL -> buildIndividualRaceResults(race);
        case RaceTypeGroup.COLLECTIVE -> buildTeamRaceResults(race);
        case RaceTypeGroup.RELAY -> buildRelayRaceResults(race);
      };
    } catch (IllegalArgumentException | NullPointerException e) {
      String errorMsg = String.format("Invalid raceTypeGroup '%s' for race ID %s",
          race.raceTypeGroup(), race.id()
      );
      LOG.error(errorMsg, e);
      throw new EnduranceTrioException(new ErrorDTO(EnduranceTrioError.INTERNAL_ERROR, errorMsg));
    }
  }

  /**
   * Builds the individual race results for the given {@code race} by fetching all
   * {@link IndividualResult} records from the repository, grouping them by {@link AgeGroup}, and
   * mapping each entry to an {@link IndividualResultDTO}.
   * <p>
   * All results are first placed in the {@link AgeGroup#OPEN} group sorted by rank (with null-safe
   * handling for unranked entries) and penalty. Additional age-group segments are then extracted
   * via the source-result chain, each independently ranked starting from 1.
   *
   * @param race the race whose individual results are to be built
   * @return a {@link RaceResultsDTO} with the individual results, or an empty results DTO if no
   * results exist for the race
   */
  private RaceResultsDTO buildIndividualRaceResults(RaceDTO race) {
    List<IndividualResult> results = individualResultRepository.findByRaceIdWithGraph(race.id());

    final Map<AgeGroup, List<IndividualResultDTO>> individualResults = new LinkedHashMap<>();

    if (results.isEmpty()) {
      individualResults.put(AgeGroup.OPEN, List.of());
      return new RaceResultsDTO(race, individualResults);
    }

    final Duration overallWinnerTotal = getIndividualWinnerTotal(results.getFirst());

    Comparator<IndividualResult> resultComparator = Comparator.comparing(IndividualResult::getRank,
            Comparator.nullsLast(Integer::compareTo)
        )
        .thenComparing(IndividualResult::getPenalty,
            Comparator.nullsLast(Comparator.comparingInt(Enum::ordinal))
        );

    individualResults.put(AgeGroup.OPEN, results.stream()
        .sorted(resultComparator)
        .map(result -> individualResultMapper.map(race, result, result.getRank(),
            overallWinnerTotal
        ))
        .toList()
    );

    Map<AgeGroup, List<IndividualResult>> allAgeGroupResults = results.stream()
        .collect(Collectors.groupingBy(result -> {
              AgeGroup ageGroup = Optional.ofNullable(result.getSourceResult())
                  .map(IndividualResult::getAgeGroup)
                  .orElse(result.getAgeGroup());
              return ageGroup != null ? ageGroup : AgeGroup.OPEN;
            }, LinkedHashMap::new, Collectors.toList()
        ));

    allAgeGroupResults.forEach((ageGroup, ageGroupResults) -> {
      if (ageGroup == null || ageGroup == AgeGroup.OPEN) {
        return;
      }
      ageGroupResults.sort(resultComparator);
      Duration winnerTotal = getIndividualWinnerTotal(ageGroupResults.getFirst());
      AtomicInteger rank = new AtomicInteger(0);
      individualResults.put(ageGroup, ageGroupResults.stream()
          .map(result -> individualResultMapper.map(race, result, rank.incrementAndGet(),
              winnerTotal
          ))
          .toList()
      );
    });

    return new RaceResultsDTO(race, individualResults);
  }

  /**
   * Stub placeholder for team race results.
   * <p>
   * Currently returns an empty {@link RaceResultsDTO} with no results.
   *
   * @param race the race whose team results are to be built
   * @return an empty {@link RaceResultsDTO}
   */
  private RaceResultsDTO buildTeamRaceResults(RaceDTO race) {
    return new RaceResultsDTO(race, null);
  }

  /**
   * Stub placeholder for relay race results.
   * <p>
   * Currently returns an empty {@link RaceResultsDTO} with no results.
   *
   * @param race the race whose relay results are to be built
   * @return an empty {@link RaceResultsDTO}
   */
  private RaceResultsDTO buildRelayRaceResults(RaceDTO race) {
    return new RaceResultsDTO(race, null);
  }

  /**
   * Returns the total {@link Duration} of a winner's {@link IndividualResult}, resolving the source
   * result chain for inherited competitions.
   * <p>
   * If the given {@code result} has an associated
   * {@link IndividualResult#getSourceResult() source result}, the total is taken from that source
   * (the original result from the feeder race). Otherwise, the result's own total is used
   * directly.
   *
   * @param result the winner's individual result, must not be null
   * @return the winner's total duration, or null if neither the result nor its source has a total
   */
  private static @Nullable Duration getIndividualWinnerTotal(IndividualResult result) {
    if (result.getSourceResult() == null) {
      return result.getTotal();
    }
    return result.getSourceResult().getTotal();
  }
}
