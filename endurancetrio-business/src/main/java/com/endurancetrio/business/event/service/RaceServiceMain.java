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

import com.endurancetrio.business.event.dto.EmptyRaceResultsDTO;
import com.endurancetrio.business.event.dto.IndividualRaceResultsDTO;
import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.dto.RaceResultsDTO;
import com.endurancetrio.business.event.dto.TeamRaceResultsDTO;
import com.endurancetrio.business.event.dto.TeamResultDTO;
import com.endurancetrio.business.event.enumerator.RaceTypeGroup;
import com.endurancetrio.business.event.mapper.IndividualResultMapper;
import com.endurancetrio.business.event.mapper.RaceMapper;
import com.endurancetrio.business.event.mapper.TeamResultMapper;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.event.model.entity.IndividualResult;
import com.endurancetrio.data.event.model.entity.Race;
import com.endurancetrio.data.event.model.entity.TeamResult;
import com.endurancetrio.data.event.repository.IndividualResultRepository;
import com.endurancetrio.data.event.repository.RaceRepository;
import com.endurancetrio.data.event.repository.TeamResultRepository;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RaceServiceMain implements RaceService {

  private final IndividualResultRepository individualResultRepository;
  private final IndividualResultMapper individualResultMapper;
  private final RaceRepository raceRepository;
  private final RaceMapper raceMapper;
  private final TeamResultMapper teamResultMapper;
  private final TeamResultRepository teamResultRepository;

  @Autowired
  public RaceServiceMain(
    IndividualResultRepository individualResultRepository, IndividualResultMapper individualResultMapper,
    RaceRepository raceRepository, RaceMapper raceMapper, TeamResultMapper teamResultMapper,
    TeamResultRepository teamResultRepository
  ) {
    this.individualResultRepository = individualResultRepository;
    this.individualResultMapper = individualResultMapper;
    this.raceRepository = raceRepository;
    this.raceMapper = raceMapper;
    this.teamResultMapper = teamResultMapper;
    this.teamResultRepository = teamResultRepository;
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
    return switch (RaceTypeGroup.from(race.raceType())) {
      case RaceTypeGroup.INDIVIDUAL -> buildIndividualRaceResults(race);
      case RaceTypeGroup.COLLECTIVE -> buildTeamRaceResults(race);
      case RaceTypeGroup.RELAY -> buildRelayRaceResults(race);
    };
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
   * @return a {@link RaceResultsDTO} holding an {@link IndividualRaceResultsDTO} with the results,
   *         or with a single empty OPEN group when no results exist for the race
   */
  private RaceResultsDTO buildIndividualRaceResults(RaceDTO race) {
    List<IndividualResult> results = individualResultRepository.findByRaceIdWithGraph(race.id());

    final Map<AgeGroup, List<IndividualResultDTO>> individualResults = new LinkedHashMap<>();

    if (results.isEmpty()) {
      individualResults.put(AgeGroup.OPEN, List.of());
      return new RaceResultsDTO(race, new IndividualRaceResultsDTO(individualResults));
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

    return new RaceResultsDTO(race, new IndividualRaceResultsDTO(orderByAgeGroupOrdinal(individualResults)));
  }

  /**
   * Stub placeholder for relay race results.
   * <p>
   * Currently returns a {@link RaceResultsDTO} holding an {@link EmptyRaceResultsDTO}.
   *
   * @param race the race whose relay results are to be built
   * @return a {@link RaceResultsDTO} with no results
   */
  private RaceResultsDTO buildRelayRaceResults(RaceDTO race) {
    return new RaceResultsDTO(race, new EmptyRaceResultsDTO());
  }

  /**
   * Builds the team race results for the given {@code race} from the stored
   * {@link TeamResult team result} rows.
   * <p>
   * The rows come back ordered by rank ascending, so the first row is the winner: its cumulative
   * rank, total or points (depending on the race type) is the reference used by the mapper to
   * compute each team's gap. Rank, total and points are read as stored; nothing is recalculated.
   *
   * @param race the race whose team results are to be built
   * @return a {@link RaceResultsDTO} holding a {@link TeamRaceResultsDTO}, which is empty when the
   *         race has no team results
   */
  private RaceResultsDTO buildTeamRaceResults(RaceDTO race) {
    List<TeamResult> teamResults = teamResultRepository.findByRaceIdWithGraph(race.id());

    if (teamResults.isEmpty()) {
      return new RaceResultsDTO(race, new TeamRaceResultsDTO(List.of()));
    }

    TeamResult winner = teamResults.getFirst();

    List<TeamResultDTO> results = teamResults.stream()
      .map(teamResult -> teamResultMapper.map(race, teamResult, winner))
      .toList();

    return new RaceResultsDTO(race, new TeamRaceResultsDTO(results));
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

  /**
   * Reorders the given map so that the {@link AgeGroup#OPEN} key, if present, comes first, followed
   * by the remaining keys sorted by their {@link AgeGroup} declaration order (ordinal).
   * <p>
   * Returns a new {@link LinkedHashMap}; the input map is not modified.
   *
   * @param map the map keyed by {@link AgeGroup} to reorder
   * @param <T> the type of the map values
   * @return a new {@link LinkedHashMap} with the keys reordered as described
   */
  private <T> Map<AgeGroup, T> orderByAgeGroupOrdinal(Map<AgeGroup, T> map) {
    Map<AgeGroup, T> ordered = new LinkedHashMap<>();
    if (map.containsKey(AgeGroup.OPEN)) {
      ordered.put(AgeGroup.OPEN, map.get(AgeGroup.OPEN));
    }

    map.entrySet()
        .stream()
        .filter(entry -> entry.getKey() != AgeGroup.OPEN)
        .sorted(Comparator.comparingInt(entry -> entry.getKey().ordinal()))
        .forEach(entry -> ordered.put(entry.getKey(), entry.getValue()));

    return ordered;
  }
}
