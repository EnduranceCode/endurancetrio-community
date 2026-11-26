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
import com.endurancetrio.data.event.model.enumerator.RaceType;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RelayResultDTOTest {

  private static final Long ID = 3L;
  private static final Integer RANK = 1;
  private static final Penalty PENALTY = Penalty.DNF;
  private static final String TEAM_NAME = "Team Alpha";
  private static final String BIB = "301";
  private static final Integer ATHLETES_COUNT = 2;
  private static final AgeGroup AGE_GROUP = AgeGroup.ELITE;
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
  private static final Duration GAP = Duration.ofSeconds(30);

  private IndividualResultDTO individualResult;
  private RelayResultDTO underTest;

  @BeforeEach
  void setUp() {
    RaceDTO race = new RaceDTO(2L, "Relay Men", "Relay", LocalDate.of(2026, Month.JUNE, 2), null, List.of("TRIATHLON"),
        List.of("OLYMPIC"), RaceType.TEAM_RELAY_PARENT, "RELAY", null, null, "UNKNOWN"
    );
    individualResult = new IndividualResultDTO(4L, race, 2, null, AthleteDTOFixtures.standard(), AGE_GROUP, null, null,
        null, "102", SWIM, null, null, null, BIKE, null, RUN, null, null, null, TOTAL, null, null
    );
    underTest = new RelayResultDTO(ID, race, RANK, PENALTY, TeamDTOFixtures.standard(), TEAM_NAME, AGE_GROUP, null, BIB,
        ATHLETES_COUNT, SWIM, FIRST_BIKE, FIRST_RUN, T1, BIKE, T2, RUN, SECOND_RUN, T3, SECOND_BIKE, TOTAL, GAP,
        List.of(individualResult)
    );
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(ID, underTest.id());
    assertNotNull(underTest.race());
    assertEquals(RANK, underTest.rank());
    assertEquals(PENALTY, underTest.penalty());
    assertEquals(TeamDTOFixtures.standard(), underTest.team());
    assertEquals(TEAM_NAME, underTest.teamName());
    assertEquals(BIB, underTest.bib());
    assertEquals(ATHLETES_COUNT, underTest.athletesCount());
    assertEquals(AGE_GROUP, underTest.ageGroup());
    assertNull(underTest.paraClass());
    assertEquals(SWIM, underTest.swim());
    assertEquals(FIRST_BIKE, underTest.firstBike());
    assertEquals(FIRST_RUN, underTest.firstRun());
    assertEquals(T1, underTest.t1());
    assertEquals(BIKE, underTest.bike());
    assertEquals(T2, underTest.t2());
    assertEquals(RUN, underTest.run());
    assertEquals(SECOND_RUN, underTest.secondRun());
    assertEquals(T3, underTest.t3());
    assertEquals(SECOND_BIKE, underTest.secondBike());
    assertEquals(TOTAL, underTest.total());
    assertEquals(GAP, underTest.gap());
    assertEquals(List.of(individualResult), underTest.individualResults());
  }
}
