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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.endurancetrio.business.competitor.dto.AthleteDTO;
import com.endurancetrio.business.competitor.dto.TeamDTO;
import com.endurancetrio.business.competitor.fixtures.AthleteDTOFixtures;
import com.endurancetrio.business.competitor.fixtures.AthleteFixture;
import com.endurancetrio.business.competitor.fixtures.TeamDTOFixtures;
import com.endurancetrio.business.competitor.fixtures.TeamFixture;
import com.endurancetrio.business.competitor.mapper.AthleteMapper;
import com.endurancetrio.business.competitor.mapper.TeamMapper;
import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.data.competitor.model.entity.Athlete;
import com.endurancetrio.data.competitor.model.entity.Team;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.competitor.model.enumerator.ParaClass;
import com.endurancetrio.data.event.model.entity.IndividualResult;
import com.endurancetrio.data.event.model.enumerator.Penalty;
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
class IndividualResultMapperTest {

  private static final Long ENTITY_ID = 1L;
  private static final Long SOURCE_ID = 99L;
  private static final Integer PASSED_RANK = 1;
  private static final Integer ENTITY_RANK = 5;
  private static final Duration WINNER_TOTAL = Duration.ofMinutes(110);
  private static final Duration ENTITY_TOTAL = Duration.ofMinutes(120);
  private static final Duration ENTITY_GAP = Duration.ofMinutes(10);
  private static final Duration SOURCE_TOTAL = Duration.ofMinutes(130);
  private static final Duration SOURCE_GAP = Duration.ofMinutes(20);

  @Mock
  private AthleteMapper athleteMapper;

  @Mock
  private TeamMapper teamMapper;

  @InjectMocks
  private IndividualResultMapper underTest;

  private RaceDTO raceDTO;
  private IndividualResult entity;
  private IndividualResult sourceEntity;
  private AthleteDTO athleteDTO;
  private TeamDTO teamDTO;

  @BeforeEach
  void setUp() {
    raceDTO = new RaceDTO(1L, "Elite Men", "Elite", LocalDate.of(2026, Month.JUNE, 1),
        LocalTime.of(10, 0), List.of("TRIATHLON"), List.of("OLYMPIC"), "INDIVIDUAL", null, null,
        "UNKNOWN"
    );

    Athlete athlete = AthleteFixture.standard();
    athleteDTO = AthleteDTOFixtures.standard();

    Team teamEntity = TeamFixture.standard();
    teamDTO = TeamDTOFixtures.standard();

    sourceEntity = new IndividualResult();
    sourceEntity.setId(SOURCE_ID);
    sourceEntity.setRank(3);
    sourceEntity.setPenalty(Penalty.DNF);
    sourceEntity.setAthlete(athlete);
    sourceEntity.setTeam(teamEntity);
    sourceEntity.setTeamName("Team Alpha");
    sourceEntity.setAgeGroup(AgeGroup.SEN);
    sourceEntity.setParaClass(ParaClass.WT_PTS4);
    sourceEntity.setBib("202");
    sourceEntity.setTotal(SOURCE_TOTAL);
    sourceEntity.setPoints(300);

    entity = new IndividualResult();
    entity.setId(ENTITY_ID);
    entity.setRank(ENTITY_RANK);
    entity.setPenalty(null);
    entity.setAthlete(athlete);
    entity.setTeam(teamEntity);
    entity.setTeamName("Team Alpha");
    entity.setAgeGroup(AgeGroup.ELITE);
    entity.setParaClass(null);
    entity.setBib("101");
    entity.setSwim(Duration.ofMinutes(20));
    entity.setFirstBike(null);
    entity.setFirstRun(null);
    entity.setT1(Duration.ofSeconds(45));
    entity.setBike(Duration.ofMinutes(60));
    entity.setT2(Duration.ofSeconds(35));
    entity.setRun(Duration.ofMinutes(35));
    entity.setSecondRun(null);
    entity.setT3(null);
    entity.setSecondBike(null);
    entity.setTotal(ENTITY_TOTAL);
    entity.setPoints(500);
  }

  @Test
  void mapNullEntity() {
    assertNull(underTest.map(raceDTO, null, PASSED_RANK, WINNER_TOTAL));
  }

  @Test
  void mapWithoutSourceResult() {
    stubMappers();

    IndividualResultDTO result = underTest.map(raceDTO, entity, PASSED_RANK, WINNER_TOTAL);

    assertNotNull(result);
    assertEquals(ENTITY_ID, result.id());
    assertEquals(raceDTO, result.race());
    assertEquals(PASSED_RANK, result.rank());
    assertNull(result.penalty());
    assertEquals(athleteDTO, result.athlete());
    assertEquals(AgeGroup.ELITE, result.ageGroup());
    assertNull(result.paraClass());
    assertEquals(teamDTO, result.team());
    assertEquals("101", result.bib());
    assertEquals(Duration.ofMinutes(20), result.swim());
    assertNull(result.firstBike());
    assertNull(result.firstRun());
    assertEquals(Duration.ofSeconds(45), result.t1());
    assertEquals(Duration.ofMinutes(60), result.bike());
    assertEquals(Duration.ofSeconds(35), result.t2());
    assertEquals(Duration.ofMinutes(35), result.run());
    assertNull(result.secondRun());
    assertNull(result.t3());
    assertNull(result.secondBike());
    assertEquals(ENTITY_TOTAL, result.total());
    assertEquals(ENTITY_GAP, result.gap());
    assertEquals(500, result.points());
  }

  @Test
  void mapWithSourceResult() {
    stubMappers();

    entity.setSourceResult(sourceEntity);

    IndividualResultDTO result = underTest.map(raceDTO, entity, PASSED_RANK, WINNER_TOTAL);

    assertNotNull(result);
    assertEquals(ENTITY_ID, result.id());
    assertEquals(raceDTO, result.race());
    assertEquals(PASSED_RANK, result.rank());
    assertEquals(Penalty.DNF, result.penalty());
    assertEquals(athleteDTO, result.athlete());
    assertEquals(AgeGroup.SEN, result.ageGroup());
    assertEquals(ParaClass.WT_PTS4, result.paraClass());
    assertEquals(teamDTO, result.team());
    assertEquals("202", result.bib());
    assertNull(result.swim());
    assertNull(result.firstBike());
    assertNull(result.firstRun());
    assertNull(result.t1());
    assertNull(result.bike());
    assertNull(result.t2());
    assertNull(result.run());
    assertNull(result.secondRun());
    assertNull(result.t3());
    assertNull(result.secondBike());
    assertEquals(SOURCE_TOTAL, result.total());
    assertEquals(SOURCE_GAP, result.gap());
    assertEquals(300, result.points());
  }

  @Test
  void mapWithRank() {
    stubMappers();

    entity.setRank(ENTITY_RANK);

    IndividualResultDTO result = underTest.map(raceDTO, entity, PASSED_RANK, WINNER_TOTAL);

    assertNotNull(result);
    assertEquals(PASSED_RANK, result.rank());
  }

  @Test
  void mapWhenEntityHasNoRank() {
    stubMappers();

    entity.setRank(null);

    IndividualResultDTO result = underTest.map(raceDTO, entity, PASSED_RANK, WINNER_TOTAL);

    assertNotNull(result);
    assertNull(result.rank());
  }

  @Test
  void mapGapComputation() {
    stubMappers();

    entity.setTotal(ENTITY_TOTAL);
    entity.setSourceResult(null);

    IndividualResultDTO result = underTest.map(raceDTO, entity, PASSED_RANK, WINNER_TOTAL);

    assertNotNull(result);
    assertEquals(ENTITY_GAP, result.gap());
  }

  @Test
  void mapGapWithNullTotal() {
    stubMappers();

    entity.setTotal(null);

    IndividualResultDTO result = underTest.map(raceDTO, entity, PASSED_RANK, WINNER_TOTAL);

    assertNotNull(result);
    assertNull(result.gap());
  }

  @Test
  void mapGapWithNullWinnerTotal() {
    stubMappers();

    IndividualResultDTO result = underTest.map(raceDTO, entity, PASSED_RANK, null);

    assertNotNull(result);
    assertNull(result.gap());
  }

  private void stubMappers() {
    when(athleteMapper.map(any())).thenReturn(athleteDTO);
    when(teamMapper.map(any(), any())).thenReturn(teamDTO);
  }
}
