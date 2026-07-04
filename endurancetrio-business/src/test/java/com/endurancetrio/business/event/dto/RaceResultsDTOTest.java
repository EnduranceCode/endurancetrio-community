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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.endurancetrio.business.competitor.fixtures.AthleteDTOFixtures;
import com.endurancetrio.business.competitor.fixtures.TeamDTOFixtures;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RaceResultsDTOTest {

  private static final AgeGroup AGE_GROUP = AgeGroup.ELITE;

  private RaceDTO race;
  private IndividualResultDTO individualResult;
  private RaceResultsDTO underTest;

  @BeforeEach
  void setUp() {
    race = new RaceDTO(1L, "Elite Men", "Elite", LocalDate.of(2026, Month.JUNE, 1), null,
        List.of("TRIATHLON"), List.of("OLYMPIC"), "INDIVIDUAL", null, null, "UNKNOWN"
    );
    individualResult = new IndividualResultDTO(1L, race, 1, null, AthleteDTOFixtures.standard(),
        AGE_GROUP, null, TeamDTOFixtures.standard(), "101", Duration.ofMinutes(20), null, null, null,
        Duration.ofMinutes(60), null, Duration.ofMinutes(35), null, null, null,
        Duration.ofMinutes(116).plusSeconds(20), Duration.ZERO, 1000
    );

    underTest = new RaceResultsDTO(race, Map.of(AGE_GROUP, List.of(individualResult)));
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(race, underTest.race());
    assertEquals(1, underTest.individualResults().size());
    assertTrue(underTest.individualResults().containsKey(AGE_GROUP));
    assertEquals(individualResult, underTest.individualResults().get(AGE_GROUP).getFirst());
  }

  @Test
  void individualResultsCanBeEmpty() {
    RaceResultsDTO dto = new RaceResultsDTO(race, Map.of());
    assertNotNull(dto);
    assertEquals(race, dto.race());
    assertTrue(dto.individualResults().isEmpty());
  }
}
