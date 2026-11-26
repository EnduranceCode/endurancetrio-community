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

import com.endurancetrio.business.competitor.dto.TeamDTO;
import com.endurancetrio.business.competitor.fixtures.RelayEntryFixture;
import com.endurancetrio.business.competitor.fixtures.TeamDTOFixtures;
import com.endurancetrio.business.competitor.mapper.TeamMapper;
import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.dto.RelayResultDTO;
import com.endurancetrio.data.competitor.model.entity.RelayEntry;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.event.model.entity.IndividualResult;
import com.endurancetrio.data.event.model.entity.TeamResult;
import com.endurancetrio.data.event.model.enumerator.Penalty;
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
class RelayResultMapperTest {

  private static final Integer PASSED_RANK = 2;
  private static final Duration WINNER_TOTAL = Duration.ofMinutes(140);
  private static final Duration SWIM = Duration.ofMinutes(10);
  private static final Duration FIRST_BIKE = Duration.ofMinutes(20);
  private static final Duration FIRST_RUN = Duration.ofMinutes(15);
  private static final Duration T1 = Duration.ofMinutes(2);
  private static final Duration BIKE = Duration.ofMinutes(40);
  private static final Duration T2 = Duration.ofMinutes(3);
  private static final Duration RUN = Duration.ofMinutes(30);
  private static final Duration SECOND_RUN = Duration.ofMinutes(5);
  private static final Duration T3 = Duration.ofMinutes(4);
  private static final Duration SECOND_BIKE = Duration.ofMinutes(25);
  private static final Duration TOTAL = Duration.ofMinutes(154);
  private static final Duration GAP = Duration.ofMinutes(14);

  @Mock
  private IndividualResultMapper individualResultMapper;

  @Mock
  private TeamMapper teamMapper;

  @InjectMocks
  private RelayResultMapper underTest;

  private RaceDTO race;
  private TeamResult entity;

  @BeforeEach
  void setUp() {
    race = new RaceDTO(1L, "Relay Race", "Relay", LocalDate.of(2026, Month.JUNE, 1), null, List.of("TRIATHLON"),
        List.of("OLYMPIC"), RaceType.TEAM_RELAY_PARENT, "RELAY", null, null, "UNKNOWN"
    );

    entity = new TeamResult();
    entity.setId(1L);
    entity.setRank(1);
    entity.setPenalty(Penalty.DNF);
    entity.setTeamName("Alpha");
    entity.setBib("101");
    entity.setAthletesCount(2);
    entity.setAgeGroup(AgeGroup.OPEN);
    entity.setSwim(SWIM);
    entity.setFirstBike(FIRST_BIKE);
    entity.setFirstRun(FIRST_RUN);
    entity.setT1(T1);
    entity.setBike(BIKE);
    entity.setT2(T2);
    entity.setRun(RUN);
    entity.setSecondRun(SECOND_RUN);
    entity.setT3(T3);
    entity.setSecondBike(SECOND_BIKE);
    entity.setTotal(TOTAL);
  }

  @Test
  void mapNullEntity() {
    assertNull(underTest.map(race, null, PASSED_RANK, WINNER_TOTAL));
  }

  @Test
  void mapRetainsStoredValuesAndSplits() {
    RelayResultDTO result = underTest.map(race, entity, entity.getRank(), WINNER_TOTAL);

    assertEquals(1L, result.id());
    assertEquals(race, result.race());
    assertEquals(1, result.rank());
    assertEquals(Penalty.DNF, result.penalty());
    assertNull(result.team());
    assertEquals("Alpha", result.teamName());
    assertEquals("101", result.bib());
    assertEquals(2, result.athletesCount());
    assertEquals(AgeGroup.OPEN, result.ageGroup());
    assertNull(result.paraClass());
    assertEquals(SWIM, result.swim());
    assertEquals(FIRST_BIKE, result.firstBike());
    assertEquals(FIRST_RUN, result.firstRun());
    assertEquals(T1, result.t1());
    assertEquals(BIKE, result.bike());
    assertEquals(T2, result.t2());
    assertEquals(RUN, result.run());
    assertEquals(SECOND_RUN, result.secondRun());
    assertEquals(T3, result.t3());
    assertEquals(SECOND_BIKE, result.secondBike());
    assertEquals(TOTAL, result.total());
    assertEquals(GAP, result.gap());
    assertTrue(result.individualResults().isEmpty());
  }

  @Test
  void mapUsesPassedRankWhenEntityHasRank() {
    RelayResultDTO result = underTest.map(race, entity, PASSED_RANK, WINNER_TOTAL);

    assertEquals(PASSED_RANK, result.rank());
  }

  @Test
  void mapWhenEntityHasNoRankKeepsRankNull() {
    entity.setRank(null);

    RelayResultDTO result = underTest.map(race, entity, PASSED_RANK, WINNER_TOTAL);

    assertNull(result.rank());
  }

  @Test
  void mapGapWithNullTotal() {
    entity.setTotal(null);

    assertNull(underTest.map(race, entity, entity.getRank(), WINNER_TOTAL).gap());
  }

  @Test
  void mapGapWithNullWinnerTotal() {
    assertNull(underTest.map(race, entity, entity.getRank(), null).gap());
  }

  @Test
  void mapUsesEntityTeamWhenNoSourceResult() {
    RelayEntry relayEntry = RelayEntryFixture.standard();
    entity.setTeam(relayEntry);
    TeamDTO teamDTO = TeamDTOFixtures.standard();
    when(teamMapper.map(relayEntry)).thenReturn(teamDTO);

    RelayResultDTO result = underTest.map(race, entity, entity.getRank(), WINNER_TOTAL);

    assertEquals(teamDTO, result.team());
  }

  @Test
  void mapContributorsOrderedByRank() {
    var rankedFirst = new IndividualResult();
    rankedFirst.setId(10L);
    rankedFirst.setRank(1);
    var rankedSecond = new IndividualResult();
    rankedSecond.setId(11L);
    rankedSecond.setRank(2);
    var unranked = new IndividualResult();
    unranked.setId(12L);
    entity.setIndividualResults(Set.of(unranked, rankedSecond, rankedFirst));

    var firstDTO = mock(IndividualResultDTO.class);
    var secondDTO = mock(IndividualResultDTO.class);
    var unrankedDTO = mock(IndividualResultDTO.class);

    when(individualResultMapper.map(race, rankedFirst, 1, null)).thenReturn(firstDTO);
    when(individualResultMapper.map(race, rankedSecond, 2, null)).thenReturn(secondDTO);
    when(individualResultMapper.map(race, unranked, null, null)).thenReturn(unrankedDTO);

    RelayResultDTO result = underTest.map(race, entity, entity.getRank(), WINNER_TOTAL);

    assertEquals(List.of(firstDTO, secondDTO, unrankedDTO), result.individualResults());
  }

  @Test
  void mapWithSourceResultUsesSourceValuesAndKeepsCurrentIdentity() {
    TeamResult source = new TeamResult();
    source.setId(99L);
    source.setPenalty(Penalty.DSQ);
    source.setBib("999");
    source.setAgeGroup(AgeGroup.SEN);
    source.setSwim(Duration.ofMinutes(11));
    source.setFirstBike(Duration.ofMinutes(21));
    source.setFirstRun(Duration.ofMinutes(16));
    source.setT1(Duration.ofMinutes(3));
    source.setBike(Duration.ofMinutes(41));
    source.setT2(Duration.ofMinutes(4));
    source.setRun(Duration.ofMinutes(31));
    source.setSecondRun(Duration.ofMinutes(6));
    source.setT3(Duration.ofMinutes(5));
    source.setSecondBike(Duration.ofMinutes(26));
    source.setTotal(Duration.ofMinutes(164));
    RelayEntry sourceEntry = RelayEntryFixture.standard();
    source.setTeam(sourceEntry);
    entity.setSourceResult(source);

    TeamDTO teamDTO = TeamDTOFixtures.standard();
    when(teamMapper.map(sourceEntry)).thenReturn(teamDTO);

    RelayResultDTO result = underTest.map(race, entity, entity.getRank(), WINNER_TOTAL);

    assertEquals(1L, result.id());
    assertEquals(1, result.rank());
    assertEquals(teamDTO, result.team());
    assertEquals("Alpha", result.teamName());
    assertEquals(2, result.athletesCount());
    assertEquals(Penalty.DSQ, result.penalty());
    assertEquals("999", result.bib());
    assertEquals(AgeGroup.SEN, result.ageGroup());
    assertEquals(Duration.ofMinutes(11), result.swim());
    assertEquals(Duration.ofMinutes(21), result.firstBike());
    assertEquals(Duration.ofMinutes(16), result.firstRun());
    assertEquals(Duration.ofMinutes(3), result.t1());
    assertEquals(Duration.ofMinutes(41), result.bike());
    assertEquals(Duration.ofMinutes(4), result.t2());
    assertEquals(Duration.ofMinutes(31), result.run());
    assertEquals(Duration.ofMinutes(6), result.secondRun());
    assertEquals(Duration.ofMinutes(5), result.t3());
    assertEquals(Duration.ofMinutes(26), result.secondBike());
    assertEquals(Duration.ofMinutes(164), result.total());
    assertEquals(Duration.ofMinutes(24), result.gap());
  }

  @Test
  void mapWithSourceResultMapsContributorsFromSource() {
    TeamResult source = new TeamResult();
    source.setId(99L);
    entity.setSourceResult(source);

    var sourceContributor = new IndividualResult();
    sourceContributor.setId(20L);
    sourceContributor.setRank(1);
    source.setIndividualResults(Set.of(sourceContributor));

    var entityContributor = new IndividualResult();
    entityContributor.setId(10L);
    entityContributor.setRank(1);
    entity.setIndividualResults(Set.of(entityContributor));

    var sourceDTO = mock(IndividualResultDTO.class);
    when(individualResultMapper.map(race, sourceContributor, 1, null)).thenReturn(sourceDTO);

    RelayResultDTO result = underTest.map(race, entity, entity.getRank(), WINNER_TOTAL);

    assertEquals(List.of(sourceDTO), result.individualResults());
  }
}
