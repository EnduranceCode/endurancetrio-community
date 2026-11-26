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

import com.endurancetrio.business.competitor.fixtures.AthleteDTOFixtures;
import com.endurancetrio.business.competitor.fixtures.TeamDTOFixtures;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.event.model.enumerator.RaceType;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamResultDTOTest {

  private static final Long ID = 2L;
  private static final Integer RANK = 1;
  private static final String TEAM_NAME = "Team Alpha";
  private static final String BIB = "201";
  private static final Integer ATHLETES_COUNT = 3;
  private static final Duration TOTAL = Duration.ofMinutes(200);
  private static final Duration TOTAL_GAP = Duration.ofSeconds(30);
  private static final Integer POINTS = 10;
  private static final Integer POINTS_GAP = 2;
  private static final Integer CUMULATIVE_RANK = 5;
  private static final Integer CUMULATIVE_RANK_GAP = 1;

  private IndividualResultDTO individualResult;
  private TeamResultDTO underTest;

  @BeforeEach
  void setUp() {
    RaceDTO race = new RaceDTO(1L, "Team Race", "Teams", LocalDate.of(2026, Month.JUNE, 1), null, List.of("TRIATHLON"),
        List.of("OLYMPIC"), RaceType.TEAM_BY_RANK, "COLLECTIVE", null, null, "UNKNOWN"
    );
    individualResult = new IndividualResultDTO(3L, race, 2, null, AthleteDTOFixtures.standard(), AgeGroup.ELITE, null,
        null, null, "102", null, null, null, null, null, null, null, null, null, null, null, null, null
    );
    underTest = new TeamResultDTO(ID, race, RANK, TeamDTOFixtures.standard(), TEAM_NAME, BIB, ATHLETES_COUNT, TOTAL,
        TOTAL_GAP, POINTS, POINTS_GAP, CUMULATIVE_RANK, CUMULATIVE_RANK_GAP, List.of(individualResult)
    );
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(ID, underTest.id());
    assertNotNull(underTest.race());
    assertEquals(RANK, underTest.rank());
    assertEquals(TeamDTOFixtures.standard(), underTest.team());
    assertEquals(TEAM_NAME, underTest.teamName());
    assertEquals(BIB, underTest.bib());
    assertEquals(ATHLETES_COUNT, underTest.athletesCount());
    assertEquals(TOTAL, underTest.total());
    assertEquals(TOTAL_GAP, underTest.totalGap());
    assertEquals(POINTS, underTest.points());
    assertEquals(POINTS_GAP, underTest.pointsGap());
    assertEquals(CUMULATIVE_RANK, underTest.cumulativeRank());
    assertEquals(CUMULATIVE_RANK_GAP, underTest.cumulativeRankGap());
    assertEquals(List.of(individualResult), underTest.individualResults());
  }
}
