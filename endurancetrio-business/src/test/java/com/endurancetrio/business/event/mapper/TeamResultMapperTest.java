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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.dto.TeamResultDTO;
import com.endurancetrio.data.event.model.entity.IndividualResult;
import com.endurancetrio.data.event.model.entity.TeamResult;
import com.endurancetrio.data.event.model.enumerator.RaceType;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TeamResultMapperTest {

  private static final Duration WINNER_TOTAL = Duration.ofMinutes(190);

  @Mock
  private IndividualResultMapper individualResultMapper;

  @InjectMocks
  private TeamResultMapper underTest;

  private RaceDTO race;
  private TeamResult entity;
  private TeamResult winner;
  private IndividualResult rankedFirst;
  private IndividualResult rankedSecond;

  @BeforeEach
  void setUp() {
    race = race(RaceType.TEAM_BY_RANK);

    entity = new TeamResult();
    entity.setId(1L);
    entity.setRank(1);
    entity.setTeamName("Alpha");
    entity.setBib("101");
    entity.setTotal(Duration.ofMinutes(200));
    entity.setPoints(80);
    entity.setCumulativeRank(5);
    entity.setAthletesCount(2);

    winner = new TeamResult();
    winner.setId(2L);
    winner.setRank(1);
    winner.setTeamName("Winner");
    winner.setTotal(WINNER_TOTAL);
    winner.setPoints(100);
    winner.setCumulativeRank(3);

    rankedFirst = new IndividualResult();
    rankedFirst.setId(10L);
    rankedFirst.setRank(1);

    rankedSecond = new IndividualResult();
    rankedSecond.setId(11L);
    rankedSecond.setRank(2);
  }

  @Test
  void mapNullEntity() {
    assertNull(underTest.map(race, null, winner));
  }

  @Test
  void mapRetainsValuesAndOrdersContributorsByRank() {
    entity.setIndividualResults(Set.of(rankedSecond, rankedFirst));

    var firstDTO = mock(IndividualResultDTO.class);
    var secondDTO = mock(IndividualResultDTO.class);

    when(individualResultMapper.map(race, rankedFirst, 1, null)).thenReturn(firstDTO);
    when(individualResultMapper.map(race, rankedSecond, 2, null)).thenReturn(secondDTO);

    TeamResultDTO result = underTest.map(race, entity, winner);

    assertEquals(1L, result.id());
    assertEquals(race, result.race());
    assertEquals(1, result.rank());
    assertEquals("Alpha", result.teamName());
    assertEquals("101", result.bib());
    assertEquals(Duration.ofMinutes(200), result.total());
    assertEquals(80, result.points());
    assertEquals(5, result.cumulativeRank());
    assertEquals(2, result.athletesCount());
    assertEquals(List.of(firstDTO, secondDTO), result.individualResults());
  }

  @Test
  void mapOrdersUnrankedContributorsLast() {
    rankedSecond.setRank(null);
    entity.setIndividualResults(Set.of(rankedSecond, rankedFirst));

    var firstDTO = mock(IndividualResultDTO.class);
    var unrankedDTO = mock(IndividualResultDTO.class);

    when(individualResultMapper.map(race, rankedFirst, 1, null)).thenReturn(firstDTO);
    when(individualResultMapper.map(race, rankedSecond, null, null)).thenReturn(unrankedDTO);

    TeamResultDTO result = underTest.map(race, entity, winner);

    assertEquals(List.of(firstDTO, unrankedDTO), result.individualResults());
  }

  @Test
  void mapWithNullContributorsReturnsEmptyList() {
    entity.setIndividualResults(null);

    TeamResultDTO result = underTest.map(race, entity, winner);

    assertTrue(result.individualResults().isEmpty());
  }

  @Test
  void mapByRankUsesCumulativeRankGap() {
    TeamResultDTO result = underTest.map(race, entity, winner);

    assertEquals(2, result.cumulativeRankGap());
    assertNull(result.totalGap());
    assertNull(result.pointsGap());
  }

  @Test
  void mapByTimeUsesTotalGap() {
    race = race(RaceType.TEAM_BY_TIME);

    TeamResultDTO result = underTest.map(race, entity, winner);

    assertEquals(Duration.ofMinutes(10), result.totalGap());
    assertNull(result.cumulativeRankGap());
    assertNull(result.pointsGap());
    assertEquals(Duration.ZERO, underTest.map(race, winner, winner).totalGap());
  }

  @Test
  void mapByPointsUsesReversedPointsGap() {
    race = race(RaceType.TEAM_BY_POINTS);

    TeamResultDTO result = underTest.map(race, entity, winner);

    assertEquals(20, result.pointsGap());
    assertNull(result.cumulativeRankGap());
    assertNull(result.totalGap());
  }

  @Test
  void mapWithMissingMetricLeavesGapNull() {
    entity.setCumulativeRank(null);

    TeamResultDTO result = underTest.map(race, entity, winner);

    assertNull(result.cumulativeRankGap());
  }

  private static RaceDTO race(RaceType raceType) {
    return new RaceDTO(2L, "Team Race", "Teams", LocalDate.of(2026, Month.JUNE, 1), null, List.of("TRIATHLON"),
      List.of("OLYMPIC"), raceType, "COLLECTIVE", null, null, "UNKNOWN"
    );
  }
}
