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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import com.endurancetrio.business.common.exception.EnduranceTrioError;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.dto.RaceResultsDTO;
import com.endurancetrio.business.event.mapper.IndividualResultMapper;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.event.model.entity.IndividualResult;
import com.endurancetrio.data.event.model.enumerator.Penalty;
import com.endurancetrio.data.event.repository.IndividualResultRepository;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RaceServiceMainTest {

  private static final Long RACE_ID = 1L;
  private static final Duration WINNER_TOTAL = Duration.ofMinutes(110);
  private static final Duration RUNNER_TOTAL = Duration.ofMinutes(120);

  @Mock
  private IndividualResultRepository individualResultRepository;

  @Mock
  private IndividualResultMapper individualResultMapper;

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
        distanceTypes, "INDIVIDUAL", null, null,
        "UNKNOWN"
    );
    collectiveRace = new RaceDTO(2L, "Team Race", "Teams", raceDate, raceTime, sports,
        distanceTypes, "COLLECTIVE", null, null,
        "UNKNOWN"
    );
    relayRace = new RaceDTO(3L, "Relay Race", "Relay", raceDate, raceTime, sports,
        distanceTypes, "RELAY", null, null,
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
    assertEquals(2, result.individualResults().size());
    assertTrue(result.individualResults().containsKey(AgeGroup.OPEN));
    assertTrue(result.individualResults().containsKey(AgeGroup.SEN));
    assertEquals(List.of(openDTO1, openDTO2), result.individualResults().get(AgeGroup.OPEN));
    assertEquals(List.of(senDTO), result.individualResults().get(AgeGroup.SEN));
  }

  @Test
  void getRaceResultsForIndividualRaceWithEmptyResultsShouldReturnEmptySingleEntry() {
    when(individualResultRepository.findByRaceIdWithGraph(RACE_ID))
        .thenReturn(List.of());

    RaceResultsDTO result = underTest.getRaceResults(individualRace);

    assertNotNull(result);
    assertEquals(individualRace, result.race());
    assertEquals(1, result.individualResults().size());
    assertTrue(result.individualResults().containsKey(AgeGroup.OPEN));
    assertTrue(result.individualResults().get(AgeGroup.OPEN).isEmpty());
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
    assertEquals(1, result.individualResults().size());
    assertTrue(result.individualResults().containsKey(AgeGroup.OPEN));
    assertEquals(List.of(openDTO), result.individualResults().get(AgeGroup.OPEN));
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
    assertEquals(1, result.individualResults().size());
    assertTrue(result.individualResults().containsKey(AgeGroup.OPEN));
    assertEquals(List.of(dnfDTO, dsqDTO), result.individualResults().get(AgeGroup.OPEN));
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

    when(individualResultRepository.findByRaceIdWithGraph(RACE_ID))
        .thenReturn(List.of(result1));

    var openDTO = mock(IndividualResultDTO.class);
    var senDTO = mock(IndividualResultDTO.class);

    when(individualResultMapper.map(individualRace, result1, 5, WINNER_TOTAL))
        .thenReturn(openDTO);
    when(individualResultMapper.map(individualRace, result1, 1, WINNER_TOTAL))
        .thenReturn(senDTO);

    RaceResultsDTO result = underTest.getRaceResults(individualRace);

    assertNotNull(result);
    assertEquals(2, result.individualResults().size());
    assertTrue(result.individualResults().containsKey(AgeGroup.OPEN));
    assertTrue(result.individualResults().containsKey(AgeGroup.SEN));
    assertEquals(List.of(openDTO), result.individualResults().get(AgeGroup.OPEN));
    assertEquals(List.of(senDTO), result.individualResults().get(AgeGroup.SEN));
  }

  @Test
  void getRaceResultsForCollectiveRaceShouldReturnNullIndividualResults() {
    RaceResultsDTO result = underTest.getRaceResults(collectiveRace);

    assertNotNull(result);
    assertEquals(collectiveRace, result.race());
    assertNull(result.individualResults());
    verifyNoInteractions(individualResultRepository);
  }

  @Test
  void getRaceResultsForRelayRaceShouldReturnNullIndividualResults() {
    RaceResultsDTO result = underTest.getRaceResults(relayRace);

    assertNotNull(result);
    assertEquals(relayRace, result.race());
    assertNull(result.individualResults());
    verifyNoInteractions(individualResultRepository);
  }

  @Test
  void getRaceResultsWithInvalidRaceTypeGroupShouldThrow() {
    var invalidRace = new RaceDTO(RACE_ID, "Bad Race", "Bad",
        LocalDate.of(2026, Month.JUNE, 1), null, List.of("TRIATHLON"),
        List.of("SPRINT"), "UNKNOWN", null, null,
        "UNKNOWN"
    );

    var exception = assertThrows(EnduranceTrioException.class,
        () -> underTest.getRaceResults(invalidRace)
    );

    assertEquals(EnduranceTrioError.INTERNAL_ERROR.getCode(), exception.getCode());
  }
}
