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

package com.endurancetrio.business.event.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.endurancetrio.business.competitor.fixtures.AthleteDTOFixtures;
import com.endurancetrio.business.competitor.fixtures.TeamDTOFixtures;
import com.endurancetrio.business.event.enumerator.RaceTypeGroup;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.event.model.enumerator.RaceType;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RaceResultsDTOTest {

  private static final AgeGroup AGE_GROUP = AgeGroup.ELITE;
  private static final Map<AgeGroup, List<IndividualResultDTO>> EMPTY_RESULTS = Map.of();

  private RaceDTO race;
  private IndividualResultDTO individualResult;
  private RaceResultsDTO underTest;

  @BeforeEach
  void setUp() {
    race = new RaceDTO(1L, "Elite Men", "Elite", LocalDate.of(2026, Month.JUNE, 1), null,
        List.of("TRIATHLON"), List.of("OLYMPIC"), RaceType.INDIVIDUAL_PARENT, "INDIVIDUAL", null,
        null, "UNKNOWN"
    );
    individualResult = new IndividualResultDTO(1L, race, 1, null, AthleteDTOFixtures.standard(),
        AGE_GROUP, null, TeamDTOFixtures.standard(), "Team Alpha", "101", Duration.ofMinutes(20),
        null, null, null, Duration.ofMinutes(60), null, Duration.ofMinutes(35), null, null, null,
        Duration.ofMinutes(116).plusSeconds(20), Duration.ZERO, 1000
    );

    underTest = new RaceResultsDTO(race, new IndividualRaceResultsDTO(Map.of(AGE_GROUP, List.of(individualResult))));
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(race, underTest.race());
    assertEquals(RaceTypeGroup.INDIVIDUAL, underTest.results().resultType());
    IndividualRaceResultsDTO results = assertInstanceOf(IndividualRaceResultsDTO.class, underTest.results());
    assertEquals(1, results.individualResults().size());
    assertTrue(results.individualResults().containsKey(AGE_GROUP));
    assertEquals(individualResult, results.individualResults().get(AGE_GROUP).getFirst());
  }

  @Test
  void individualResultsCanContainOpenGroup() {
    RaceResultsDTO dto = new RaceResultsDTO(race,
      new IndividualRaceResultsDTO(Map.of(AgeGroup.OPEN, List.of(individualResult)))
    );

    assertEquals(RaceTypeGroup.INDIVIDUAL, dto.results().resultType());
    IndividualRaceResultsDTO results = assertInstanceOf(IndividualRaceResultsDTO.class, dto.results());
    assertNotNull(results.individualResults());
    assertTrue(results.individualResults().containsKey(AgeGroup.OPEN));
  }

  @Test
  void individualResultsCanBeEmpty() {
    RaceResultsDTO dto = new RaceResultsDTO(race, new IndividualRaceResultsDTO(EMPTY_RESULTS));

    assertNotNull(dto);
    assertEquals(race, dto.race());
    assertEquals(RaceTypeGroup.INDIVIDUAL, dto.results().resultType());
    IndividualRaceResultsDTO results = assertInstanceOf(IndividualRaceResultsDTO.class, dto.results());
    assertEquals(EMPTY_RESULTS, results.individualResults());
  }

  @Test
  void teamVariantShouldHoldContributors() {
    TeamResultDTO teamResult = new TeamResultDTO(2L, race, 1, TeamDTOFixtures.standard(), "Team Alpha", "201", 3,
        Duration.ofMinutes(200), null, 10, null, 5, null, List.of(individualResult)
    );

    RaceResultsDTO dto = new RaceResultsDTO(race, new TeamRaceResultsDTO(List.of(teamResult)));

    assertEquals(RaceTypeGroup.COLLECTIVE, dto.results().resultType());
    TeamRaceResultsDTO results = assertInstanceOf(TeamRaceResultsDTO.class, dto.results());
    assertEquals(List.of(teamResult), results.teamResults());
    assertEquals(List.of(individualResult), results.teamResults().getFirst().individualResults());
  }

  @Test
  void relayVariantShouldHoldDurationsAndContributors() {
    RaceDTO relayRace = new RaceDTO(2L, "Relay Men", "Relay", LocalDate.of(2026, Month.JUNE, 2), null,
        List.of("TRIATHLON"), List.of("OLYMPIC"), RaceType.TEAM_RELAY_PARENT, "RELAY", null, null, "UNKNOWN"
    );
    RelayResultDTO relayResult = new RelayResultDTO(3L, relayRace, 1, null, TeamDTOFixtures.standard(), "Team Alpha",
        AGE_GROUP, null, "301", 2, Duration.ofMinutes(10), Duration.ofMinutes(20), Duration.ofMinutes(15),
        Duration.ofMinutes(2), Duration.ofMinutes(40), Duration.ofMinutes(3), Duration.ofMinutes(30), null,
        Duration.ofMinutes(4), Duration.ofMinutes(25), Duration.ofMinutes(149), Duration.ofSeconds(20),
        List.of(individualResult)
    );

    Map<AgeGroup, List<RelayResultDTO>> relayResults = Map.of(AGE_GROUP, List.of(relayResult), AgeGroup.OPEN,
        List.of()
    );
    RaceResultsDTO dto = new RaceResultsDTO(relayRace, new RelayRaceResultsDTO(relayResults));

    assertEquals(RaceTypeGroup.RELAY, dto.results().resultType());
    RelayRaceResultsDTO results = assertInstanceOf(RelayRaceResultsDTO.class, dto.results());
    assertEquals(relayResults, results.relayResults());
    assertTrue(results.relayResults().containsKey(AGE_GROUP));
    assertTrue(results.relayResults().containsKey(AgeGroup.OPEN));
  }
}
