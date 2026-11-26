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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.endurancetrio.business.event.dto.IndividualRaceResultsDTO;
import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.dto.RaceResultsDTO;
import com.endurancetrio.business.event.dto.RelayRaceResultsDTO;
import com.endurancetrio.business.event.dto.RelayResultDTO;
import com.endurancetrio.business.event.dto.TeamRaceResultsDTO;
import com.endurancetrio.business.event.dto.TeamResultDTO;
import com.endurancetrio.business.event.enumerator.RaceTypeGroup;
import com.endurancetrio.business.event.mapper.IndividualResultMapper;
import com.endurancetrio.business.event.mapper.RaceMapper;
import com.endurancetrio.business.event.mapper.RelayResultMapper;
import com.endurancetrio.business.event.mapper.TeamResultMapper;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.event.model.entity.IndividualResult;
import com.endurancetrio.data.event.model.entity.Race;
import com.endurancetrio.data.event.model.entity.TeamResult;
import com.endurancetrio.data.event.model.enumerator.Penalty;
import com.endurancetrio.data.event.model.enumerator.RaceType;
import com.endurancetrio.data.event.model.enumerator.ResultStatus;
import com.endurancetrio.data.event.repository.IndividualResultRepository;
import com.endurancetrio.data.event.repository.RaceRepository;
import com.endurancetrio.data.event.repository.TeamResultRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class RaceServiceMainTest {

  private static final Long RACE_ID = 1L;
  private static final Duration WINNER_TOTAL = Duration.ofMinutes(110);
  private static final Duration RUNNER_TOTAL = Duration.ofMinutes(120);

  @Mock
  private IndividualResultRepository individualResultRepository;

  @Mock
  private IndividualResultMapper individualResultMapper;

  @Mock
  private RaceRepository raceRepository;

  @Mock
  private RaceMapper raceMapper;

  @Mock
  private RelayResultMapper relayResultMapper;

  @Mock
  private TeamResultMapper teamResultMapper;

  @Mock
  private TeamResultRepository teamResultRepository;

  @InjectMocks
  private RaceServiceMain underTest;

  private RaceDTO individualRace;
  private RaceDTO collectiveRace;
  private RaceDTO relayRace;
  private IndividualResult result1;
  private IndividualResult result2;

  @BeforeEach
  void setUp() {
    var raceDate = LocalDate.of(2026, Month.JUNE, 1);
    var raceTime = LocalTime.of(10, 0);
    var sports = List.of("TRIATHLON");
    var distanceTypes = List.of("OLYMPIC");

    individualRace = new RaceDTO(RACE_ID, "Elite Men", "Elite", raceDate, raceTime, sports,
        distanceTypes, RaceType.INDIVIDUAL_PARENT, "INDIVIDUAL", null, null,
        "UNKNOWN"
    );
    collectiveRace = new RaceDTO(2L, "Team Race", "Teams", raceDate, raceTime, sports,
        distanceTypes, RaceType.TEAM_BY_RANK, "COLLECTIVE", null, null,
        "UNKNOWN"
    );
    relayRace = new RaceDTO(3L, "Relay Race", "Relay", raceDate, raceTime, sports, distanceTypes,
        RaceType.TEAM_RELAY_PARENT, "RELAY", null, null,
        "UNKNOWN"
    );

    result1 = new IndividualResult();
    result1.setId(1L);
    result1.setRank(1);
    result1.setTotal(WINNER_TOTAL);
    result1.setAgeGroup(AgeGroup.OPEN);

    result2 = new IndividualResult();
    result2.setId(2L);
    result2.setRank(2);
    result2.setTotal(RUNNER_TOTAL);
    result2.setAgeGroup(AgeGroup.OPEN);
  }

  @Test
  void getRaceResultsForIndividualRaceShouldReturnResultsGroupedByAgeGroup() {
    result2.setAgeGroup(AgeGroup.SEN);

    when(individualResultRepository.findByRaceIdWithGraph(RACE_ID))
        .thenReturn(List.of(result1, result2));

    var openDTO1 = mock(IndividualResultDTO.class);
    var openDTO2 = mock(IndividualResultDTO.class);
    var senDTO = mock(IndividualResultDTO.class);

    when(individualResultMapper.map(individualRace, result1, 1, WINNER_TOTAL))
        .thenReturn(openDTO1);
    when(individualResultMapper.map(individualRace, result2, 2, WINNER_TOTAL))
        .thenReturn(openDTO2);
    when(individualResultMapper.map(individualRace, result2, 1, RUNNER_TOTAL))
        .thenReturn(senDTO);

    RaceResultsDTO result = underTest.getRaceResults(individualRace);

    assertNotNull(result);
    assertEquals(individualRace, result.race());
    IndividualRaceResultsDTO results = assertInstanceOf(IndividualRaceResultsDTO.class,
        result.results()
    );
    assertEquals(2, results.individualResults().size());
    assertTrue(results.individualResults().containsKey(AgeGroup.OPEN));
    assertTrue(results.individualResults().containsKey(AgeGroup.SEN));
    assertEquals(List.of(openDTO1, openDTO2), results.individualResults().get(AgeGroup.OPEN));
    assertEquals(List.of(senDTO), results.individualResults().get(AgeGroup.SEN));
  }

  @Test
  void getRaceResultsForIndividualRaceWithEmptyResultsShouldReturnEmptySingleEntry() {
    when(individualResultRepository.findByRaceIdWithGraph(RACE_ID))
        .thenReturn(List.of());

    RaceResultsDTO result = underTest.getRaceResults(individualRace);

    assertNotNull(result);
    assertEquals(individualRace, result.race());
    IndividualRaceResultsDTO results = assertInstanceOf(IndividualRaceResultsDTO.class,
        result.results()
    );
    assertEquals(1, results.individualResults().size());
    assertTrue(results.individualResults().containsKey(AgeGroup.OPEN));
    assertTrue(results.individualResults().get(AgeGroup.OPEN).isEmpty());
  }

  @Test
  void getRaceResultsWithSingleResultShouldReturnSingleEntry() {
    when(individualResultRepository.findByRaceIdWithGraph(RACE_ID))
        .thenReturn(List.of(result1));

    var openDTO = mock(IndividualResultDTO.class);

    when(individualResultMapper.map(individualRace, result1, 1, WINNER_TOTAL))
        .thenReturn(openDTO);

    RaceResultsDTO result = underTest.getRaceResults(individualRace);

    assertNotNull(result);
    IndividualRaceResultsDTO results = assertInstanceOf(IndividualRaceResultsDTO.class,
        result.results()
    );
    assertEquals(1, results.individualResults().size());
    assertTrue(results.individualResults().containsKey(AgeGroup.OPEN));
    assertEquals(List.of(openDTO), results.individualResults().get(AgeGroup.OPEN));
  }

  @Test
  void getRaceResultsShouldSortByRankAndPenalty() {
    result1.setRank(2);
    result1.setPenalty(Penalty.DNF);
    result2.setRank(2);
    result2.setPenalty(Penalty.DSQ);

    when(individualResultRepository.findByRaceIdWithGraph(RACE_ID))
        .thenReturn(List.of(result1, result2));

    var dnfDTO = mock(IndividualResultDTO.class);
    var dsqDTO = mock(IndividualResultDTO.class);

    when(individualResultMapper.map(individualRace, result1, 2, WINNER_TOTAL))
        .thenReturn(dnfDTO);
    when(individualResultMapper.map(individualRace, result2, 2, WINNER_TOTAL))
        .thenReturn(dsqDTO);

    RaceResultsDTO result = underTest.getRaceResults(individualRace);

    assertNotNull(result);
    IndividualRaceResultsDTO results = assertInstanceOf(IndividualRaceResultsDTO.class,
        result.results()
    );
    assertEquals(1, results.individualResults().size());
    assertTrue(results.individualResults().containsKey(AgeGroup.OPEN));
    assertEquals(List.of(dnfDTO, dsqDTO), results.individualResults().get(AgeGroup.OPEN));
  }

  @Test
  void getRaceResultsWithSourceResultShouldUseSourceAgeGroupForGrouping() {
    var source = new IndividualResult();
    source.setId(99L);
    source.setAgeGroup(AgeGroup.SEN);
    source.setTotal(WINNER_TOTAL);

    result1.setRank(5);
    result1.setAgeGroup(AgeGroup.OPEN);
    result1.setSourceResult(source);
    result1.setTotal(RUNNER_TOTAL);

    var openResult = new IndividualResult();
    openResult.setId(3L);
    openResult.setRank(6);
    openResult.setAgeGroup(AgeGroup.OPEN);
    openResult.setTotal(RUNNER_TOTAL.plusMinutes(10));

    when(individualResultRepository.findByRaceIdWithGraph(RACE_ID)).thenReturn(List.of(result1, openResult));

    var openDTO = mock(IndividualResultDTO.class);
    var otherOpenDTO = mock(IndividualResultDTO.class);
    var senDTO = mock(IndividualResultDTO.class);

    when(individualResultMapper.map(individualRace, result1, 5, WINNER_TOTAL))
        .thenReturn(openDTO);
    when(individualResultMapper.map(individualRace, openResult, 6, WINNER_TOTAL)).thenReturn(otherOpenDTO);
    when(individualResultMapper.map(individualRace, result1, 1, WINNER_TOTAL))
        .thenReturn(senDTO);

    RaceResultsDTO result = underTest.getRaceResults(individualRace);

    assertNotNull(result);
    IndividualRaceResultsDTO results = assertInstanceOf(IndividualRaceResultsDTO.class,
        result.results()
    );
    assertEquals(2, results.individualResults().size());
    assertTrue(results.individualResults().containsKey(AgeGroup.OPEN));
    assertTrue(results.individualResults().containsKey(AgeGroup.SEN));
    assertEquals(List.of(openDTO, otherOpenDTO), results.individualResults().get(AgeGroup.OPEN));
    assertEquals(List.of(senDTO), results.individualResults().get(AgeGroup.SEN));
  }

  @Test
  void getRaceResultsWhenAllResultsShareOneAgeGroupShouldKeepOnlyOpenGroup() {
    result1.setAgeGroup(AgeGroup.SEN);
    result2.setAgeGroup(AgeGroup.SEN);

    when(individualResultRepository.findByRaceIdWithGraph(RACE_ID)).thenReturn(List.of(result1, result2));

    var winnerDTO = mock(IndividualResultDTO.class);
    var runnerUpDTO = mock(IndividualResultDTO.class);

    when(individualResultMapper.map(individualRace, result1, 1, WINNER_TOTAL)).thenReturn(winnerDTO);
    when(individualResultMapper.map(individualRace, result2, 2, WINNER_TOTAL)).thenReturn(runnerUpDTO);

    RaceResultsDTO result = underTest.getRaceResults(individualRace);

    assertNotNull(result);
    IndividualRaceResultsDTO results = assertInstanceOf(IndividualRaceResultsDTO.class, result.results());
    assertEquals(1, results.individualResults().size());
    assertTrue(results.individualResults().containsKey(AgeGroup.OPEN));
    assertFalse(results.individualResults().containsKey(AgeGroup.SEN));
    assertEquals(List.of(winnerDTO, runnerUpDTO), results.individualResults().get(AgeGroup.OPEN));
  }

  @Test
  void getRaceResultsForCollectiveRaceShouldReturnStoredTeamResults() {
    var teamResult = teamResult(1L, 1, "Alpha");
    teamResult.setBib("101");
    teamResult.setTotal(Duration.ofMinutes(200));
    teamResult.setPoints(10);
    teamResult.setCumulativeRank(5);
    teamResult.setAthletesCount(3);
    teamResult.setIndividualResults(Set.of(result1));

    var contributorDTO = mock(IndividualResultDTO.class);
    var expected = new TeamResultDTO(1L, collectiveRace, 1, null, "Alpha", "101", 3, Duration.ofMinutes(200), null, 10,
        null, 5, null, List.of(contributorDTO)
    );

    when(teamResultRepository.findByRaceIdWithGraph(collectiveRace.id())).thenReturn(List.of(teamResult));
    when(teamResultMapper.map(collectiveRace, teamResult, teamResult)).thenReturn(expected);

    RaceResultsDTO result = underTest.getRaceResults(collectiveRace);

    assertNotNull(result);
    assertEquals(collectiveRace, result.race());
    TeamRaceResultsDTO results = assertInstanceOf(TeamRaceResultsDTO.class, result.results());
    assertEquals(RaceTypeGroup.COLLECTIVE, results.resultType());
    assertEquals(List.of(expected), results.teamResults());
    verifyNoInteractions(individualResultRepository);
  }

  @Test
  void getRaceResultsForCollectiveRaceShouldPreserveRepositoryOrder() {
    var rankOneAlpha = teamResult(3L, 1, "Alpha");
    var rankOneZulu = teamResult(2L, 1, "Zulu");
    var rankTwo = teamResult(1L, 2, "Bravo");
    var rankThree = teamResult(4L, 3, "Aardvark");

    when(teamResultRepository.findByRaceIdWithGraph(collectiveRace.id())).thenReturn(
      List.of(rankOneAlpha, rankOneZulu, rankTwo, rankThree));

    underTest.getRaceResults(collectiveRace);

    var captor = ArgumentCaptor.forClass(TeamResult.class);
    verify(teamResultMapper, times(4)).map(eq(collectiveRace), captor.capture(), eq(rankOneAlpha));

    assertEquals(List.of("Alpha", "Zulu", "Bravo", "Aardvark"),
      captor.getAllValues().stream().map(TeamResult::getTeamName).toList()
    );
  }

  @Test
  void getRaceResultsForCollectiveRaceWithNoTeamResultsShouldReturnEmptyTeamResults() {
    when(teamResultRepository.findByRaceIdWithGraph(collectiveRace.id())).thenReturn(List.of());

    RaceResultsDTO result = underTest.getRaceResults(collectiveRace);

    assertNotNull(result);
    assertEquals(collectiveRace, result.race());
    TeamRaceResultsDTO results = assertInstanceOf(TeamRaceResultsDTO.class, result.results());
    assertTrue(results.teamResults().isEmpty());
    verifyNoInteractions(individualResultRepository);
  }

  @Test
  void getRaceResultsForRelayRaceShouldReturnEmptyResults() {
    when(teamResultRepository.findByRaceIdWithGraph(relayRace.id())).thenReturn(List.of());

    RaceResultsDTO result = underTest.getRaceResults(relayRace);

    assertNotNull(result);
    assertEquals(relayRace, result.race());
    RelayRaceResultsDTO results = assertInstanceOf(RelayRaceResultsDTO.class, result.results());
    assertEquals(1, results.relayResults().size());
    assertTrue(results.relayResults().get(AgeGroup.OPEN).isEmpty());
    verifyNoInteractions(individualResultRepository);
    verifyNoInteractions(relayResultMapper);
  }

  @Test
  void getRaceResultsForRelayRaceShouldSortByRankAndPenalty() {
    var rankOne = teamResult(3L, 1, "Bravo");
    rankOne.setTotal(Duration.ofMinutes(150));
    var rankTwoDnf = teamResult(1L, 2, "Alpha");
    rankTwoDnf.setPenalty(Penalty.DNF);
    rankTwoDnf.setTotal(Duration.ofMinutes(160));
    var rankTwoDsq = teamResult(2L, 2, "Zulu");
    rankTwoDsq.setPenalty(Penalty.DSQ);
    rankTwoDsq.setTotal(Duration.ofMinutes(170));
    var unranked = teamResult(4L, null, "Aardvark");
    unranked.setTotal(Duration.ofMinutes(180));

    when(teamResultRepository.findByRaceIdWithGraph(relayRace.id())).thenReturn(
        List.of(rankOne, rankTwoDsq, rankTwoDnf, unranked));

    var rankOneDTO = mock(RelayResultDTO.class);
    var dnfDTO = mock(RelayResultDTO.class);
    var dsqDTO = mock(RelayResultDTO.class);
    var unrankedDTO = mock(RelayResultDTO.class);

    when(relayResultMapper.map(relayRace, rankOne, 1, Duration.ofMinutes(150))).thenReturn(rankOneDTO);
    when(relayResultMapper.map(relayRace, rankTwoDnf, 2, Duration.ofMinutes(150))).thenReturn(dnfDTO);
    when(relayResultMapper.map(relayRace, rankTwoDsq, 2, Duration.ofMinutes(150))).thenReturn(dsqDTO);
    when(relayResultMapper.map(relayRace, unranked, null, Duration.ofMinutes(150))).thenReturn(unrankedDTO);

    RaceResultsDTO result = underTest.getRaceResults(relayRace);

    assertNotNull(result);
    RelayRaceResultsDTO results = assertInstanceOf(RelayRaceResultsDTO.class, result.results());
    assertEquals(1, results.relayResults().size());
    assertEquals(List.of(rankOneDTO, dnfDTO, dsqDTO, unrankedDTO), results.relayResults().get(AgeGroup.OPEN));
    verifyNoInteractions(individualResultRepository);
  }

  @Test
  void getRaceResultsForDerivedRelayRaceWithSingleAgeGroupShouldKeepOnlyOpenGroup() {
    var source = teamResult(99L, 1, "Source");
    source.setAgeGroup(AgeGroup.SEN);
    source.setTotal(Duration.ofMinutes(140));
    var derived = teamResult(1L, 1, "Alpha");
    derived.setAgeGroup(AgeGroup.OPEN);
    derived.setSourceResult(source);

    when(teamResultRepository.findByRaceIdWithGraph(relayRace.id())).thenReturn(List.of(derived));

    var dto = mock(RelayResultDTO.class);
    when(relayResultMapper.map(relayRace, derived, 1, Duration.ofMinutes(140))).thenReturn(dto);

    RaceResultsDTO result = underTest.getRaceResults(relayRace);

    assertNotNull(result);
    RelayRaceResultsDTO results = assertInstanceOf(RelayRaceResultsDTO.class, result.results());
    assertEquals(1, results.relayResults().size());
    assertTrue(results.relayResults().containsKey(AgeGroup.OPEN));
    assertFalse(results.relayResults().containsKey(AgeGroup.SEN));
    assertEquals(List.of(dto), results.relayResults().get(AgeGroup.OPEN));
  }

  @Test
  void getRaceResultsForRelayRaceWhenAllResultsShareOneAgeGroupShouldKeepOnlyOpenGroup() {
    var winner = teamResult(1L, 1, "Alpha");
    winner.setAgeGroup(AgeGroup.SEN);
    winner.setTotal(Duration.ofMinutes(150));
    var runnerUp = teamResult(2L, 2, "Bravo");
    runnerUp.setAgeGroup(AgeGroup.SEN);
    runnerUp.setTotal(Duration.ofMinutes(160));

    when(teamResultRepository.findByRaceIdWithGraph(relayRace.id())).thenReturn(List.of(winner, runnerUp));

    var winnerDTO = mock(RelayResultDTO.class);
    var runnerUpDTO = mock(RelayResultDTO.class);

    when(relayResultMapper.map(relayRace, winner, 1, Duration.ofMinutes(150))).thenReturn(winnerDTO);
    when(relayResultMapper.map(relayRace, runnerUp, 2, Duration.ofMinutes(150))).thenReturn(runnerUpDTO);

    RaceResultsDTO result = underTest.getRaceResults(relayRace);

    assertNotNull(result);
    RelayRaceResultsDTO results = assertInstanceOf(RelayRaceResultsDTO.class, result.results());
    assertEquals(1, results.relayResults().size());
    assertTrue(results.relayResults().containsKey(AgeGroup.OPEN));
    assertFalse(results.relayResults().containsKey(AgeGroup.SEN));
    assertEquals(List.of(winnerDTO, runnerUpDTO), results.relayResults().get(AgeGroup.OPEN));
  }

  @Test
  void getRaceResultsForRelayRaceShouldRankAgeGroupsAndUseGroupWinnerForGaps() {
    var overallWinner = teamResult(1L, 1, "Overall");
    overallWinner.setAgeGroup(AgeGroup.OPEN);
    overallWinner.setTotal(Duration.ofMinutes(140));
    var senWinner = teamResult(2L, 2, "Sen Winner");
    senWinner.setTotal(Duration.ofMinutes(150));
    var senRunnerUp = teamResult(3L, 3, "Sen Runner Up");
    senRunnerUp.setTotal(Duration.ofMinutes(160));

    var senSource = teamResult(99L, 1, "Sen Source");
    senSource.setAgeGroup(AgeGroup.SEN);
    senSource.setTotal(Duration.ofMinutes(150));
    senWinner.setSourceResult(senSource);
    senRunnerUp.setSourceResult(senSource);

    when(teamResultRepository.findByRaceIdWithGraph(relayRace.id())).thenReturn(
        List.of(overallWinner, senWinner, senRunnerUp));

    var overallDTO = mock(RelayResultDTO.class);
    var senOpenDTO = mock(RelayResultDTO.class);
    var senRunnerUpOpenDTO = mock(RelayResultDTO.class);
    var senGroupWinnerDTO = mock(RelayResultDTO.class);
    var senGroupRunnerUpDTO = mock(RelayResultDTO.class);

    when(relayResultMapper.map(relayRace, overallWinner, 1, Duration.ofMinutes(140))).thenReturn(overallDTO);
    when(relayResultMapper.map(relayRace, senWinner, 2, Duration.ofMinutes(140))).thenReturn(senOpenDTO);
    when(relayResultMapper.map(relayRace, senRunnerUp, 3, Duration.ofMinutes(140))).thenReturn(senRunnerUpOpenDTO);
    when(relayResultMapper.map(relayRace, senWinner, 1, Duration.ofMinutes(150))).thenReturn(senGroupWinnerDTO);
    when(relayResultMapper.map(relayRace, senRunnerUp, 2, Duration.ofMinutes(150))).thenReturn(senGroupRunnerUpDTO);

    RaceResultsDTO result = underTest.getRaceResults(relayRace);

    assertNotNull(result);
    RelayRaceResultsDTO results = assertInstanceOf(RelayRaceResultsDTO.class, result.results());
    assertEquals(List.of(overallDTO, senOpenDTO, senRunnerUpOpenDTO), results.relayResults().get(AgeGroup.OPEN));
    assertEquals(List.of(senGroupWinnerDTO, senGroupRunnerUpDTO), results.relayResults().get(AgeGroup.SEN));
  }

  @Test
  void getRaceResultsForConsistentIndividualRaceShouldReturnIndividualResults() {
    when(individualResultRepository.findByRaceIdWithGraph(RACE_ID)).thenReturn(List.of());

    var consistentRace = new RaceDTO(RACE_ID, "Consistent Race", "Consistent", LocalDate.of(2026, Month.JUNE, 1), null,
      List.of("TRIATHLON"), List.of("SPRINT"), RaceType.INDIVIDUAL_PARENT, "INDIVIDUAL", null, null,
        "UNKNOWN"
    );

    RaceResultsDTO result = underTest.getRaceResults(consistentRace);

    assertNotNull(result);
    assertEquals(consistentRace, result.race());
    IndividualRaceResultsDTO results = assertInstanceOf(IndividualRaceResultsDTO.class,
        result.results()
    );
    assertTrue(results.individualResults().containsKey(AgeGroup.OPEN));
  }

  @Test
  void getNonDerivedRacesWithMostRecentAddedResultsShouldReturnData() {
    var race = new Race();
    race.setId(10L);
    race.setTitle("Test Race");
    race.setSubtitle("Sub");
    race.setDate(LocalDate.of(2026, Month.JULY, 1));
    race.setRaceType(RaceType.INDIVIDUAL_PARENT);
    race.setResultStatus(ResultStatus.COMPLETE);

    var raceDto = new RaceDTO(10L, "Test Race", "Sub", LocalDate.of(2026, Month.JULY, 1), null,
        List.of(), List.of(), RaceType.INDIVIDUAL_PARENT, "INDIVIDUAL", null, null, "COMPLETE"
    );

    Page<Long> idPage = new PageImpl<>(List.of(10L), PageRequest.of(0, 5), 1);

    when(raceRepository.findNonDerivedRaceIdsWithMostRecentAddedResults(
        PageRequest.of(0, 5))).thenReturn(idPage);
    when(raceRepository.findRacesByIdInWithCoursesAndEvent(List.of(10L))).thenReturn(List.of(race));
    when(raceMapper.mapWithoutDistanceWithEvent(race)).thenReturn(raceDto);

    Page<RaceDTO> result = underTest.getNonDerivedRacesWithMostRecentAddedResults(
        PageRequest.of(0, 5));

    assertNotNull(result);
    assertEquals(1, result.getContent().size());
    assertEquals(raceDto, result.getContent().getFirst());
  }

  @Test
  void getNonDerivedRacesWithMostRecentAddedResultsShouldReturnEmptyPage() {
    Page<Long> emptyIdPage = new PageImpl<>(List.of(), PageRequest.of(0, 5), 0);

    when(raceRepository.findNonDerivedRaceIdsWithMostRecentAddedResults(
        PageRequest.of(0, 5))).thenReturn(emptyIdPage);

    Page<RaceDTO> result = underTest.getNonDerivedRacesWithMostRecentAddedResults(
        PageRequest.of(0, 5));

    assertNotNull(result);
    assertTrue(result.getContent().isEmpty());
  }

  private static TeamResult teamResult(Long id, Integer rank, String teamName) {
    var teamResult = new TeamResult();
    teamResult.setId(id);
    teamResult.setRank(rank);
    teamResult.setTeamName(teamName);
    return teamResult;
  }
}
