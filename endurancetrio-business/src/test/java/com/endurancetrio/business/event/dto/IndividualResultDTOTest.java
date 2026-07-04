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
import static org.junit.jupiter.api.Assertions.assertNull;

import com.endurancetrio.business.competitor.fixtures.AthleteDTOFixtures;
import com.endurancetrio.business.competitor.fixtures.TeamDTOFixtures;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.event.model.enumerator.Penalty;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class IndividualResultDTOTest {

  private static final Long ID = 1L;
  private static final Integer RANK = 1;
  private static final Penalty PENALTY = Penalty.DNF;
  private static final String BIB = "101";
  private static final Duration SWIM = Duration.ofMinutes(20);
  private static final Duration BIKE = Duration.ofMinutes(60);
  private static final Duration RUN = Duration.ofMinutes(35);
  private static final Duration TOTAL = Duration.ofMinutes(116).plusSeconds(20);
  private static final Duration GAP = Duration.ZERO;
  private static final Integer POINTS = 1000;

  private IndividualResultDTO underTest;

  @BeforeEach
  void setUp() {
    RaceDTO race = new RaceDTO(1L, "Elite Men", "Elite", LocalDate.of(2026, Month.JUNE, 1), null,
        List.of("TRIATHLON"), List.of("OLYMPIC"), "INDIVIDUAL", null, null, "UNKNOWN"
    );
    underTest = new IndividualResultDTO(ID, race, RANK, PENALTY, AthleteDTOFixtures.standard(),
        AgeGroup.ELITE, null, TeamDTOFixtures.standard(), BIB, SWIM, null, null, null, BIKE, null,
        RUN, null, null, null, TOTAL, GAP, POINTS
    );
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(ID, underTest.id());
    assertNotNull(underTest.race());
    assertEquals(RANK, underTest.rank());
    assertEquals(PENALTY, underTest.penalty());
    assertNotNull(underTest.athlete());
    assertEquals(AgeGroup.ELITE, underTest.ageGroup());
    assertNull(underTest.paraClass());
    assertNotNull(underTest.team());
    assertEquals(BIB, underTest.bib());
    assertEquals(SWIM, underTest.swim());
    assertNull(underTest.firstBike());
    assertNull(underTest.firstRun());
    assertNull(underTest.t1());
    assertEquals(BIKE, underTest.bike());
    assertNull(underTest.t2());
    assertEquals(RUN, underTest.run());
    assertNull(underTest.secondRun());
    assertNull(underTest.t3());
    assertNull(underTest.secondBike());
    assertEquals(TOTAL, underTest.total());
    assertEquals(GAP, underTest.gap());
    assertEquals(POINTS, underTest.points());
  }
}
