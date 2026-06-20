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

package com.endurancetrio.data.event.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link TeamResult} entity.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class TeamResultTest {

  private TeamResult underTest;

  @BeforeEach
  void setUp() {
    underTest = new TeamResult();
    underTest.setId(1L);
    underTest.setRank(1);
    underTest.setTeamName("Sport Clube");
    underTest.setAthletesCount(3);
    underTest.setBib("T01");
    underTest.setCumulativeRank(10);
    underTest.setTotal(Duration.ofMinutes(330));
    underTest.setPoints(3000);
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(1L, underTest.getId());
    assertEquals(1, underTest.getRank());
    assertNull(underTest.getRace());
    assertNull(underTest.getSourceResult());
    assertNull(underTest.getPenalty());
    assertNull(underTest.getTeam());
    assertEquals("Sport Clube", underTest.getTeamName());
    assertEquals(3, underTest.getAthletesCount());
    assertTrue(underTest.getIndividualResults().isEmpty());
    assertNull(underTest.getAgeGroup());
    assertNull(underTest.getParaClass());
    assertEquals("T01", underTest.getBib());
    assertEquals(Integer.valueOf(10), underTest.getCumulativeRank());
    assertEquals(Duration.ofMinutes(330), underTest.getTotal());
    assertEquals(3000, underTest.getPoints());
  }

  @Test
  void addIndividualResultShouldAddIndividualResult() {
    IndividualResult individualResult = new IndividualResult();
    individualResult.setId(100L);

    underTest.addIndividualResult(individualResult);

    assertEquals(1, underTest.getIndividualResults().size());
    assertTrue(underTest.getIndividualResults().contains(individualResult));
  }

  @Test
  void addIndividualResultShouldIgnoreNull() {
    underTest.addIndividualResult(null);

    assertTrue(underTest.getIndividualResults().isEmpty());
  }

  @Test
  void removeIndividualResultShouldRemoveIndividualResult() {
    IndividualResult individualResult = new IndividualResult();
    individualResult.setId(100L);
    underTest.getIndividualResults().add(individualResult);

    underTest.removeIndividualResult(individualResult);

    assertTrue(underTest.getIndividualResults().isEmpty());
  }

  @Test
  void removeIndividualResultShouldIgnoreNull() {
    IndividualResult individualResult = new IndividualResult();
    individualResult.setId(100L);
    underTest.getIndividualResults().add(individualResult);

    underTest.removeIndividualResult(null);

    assertFalse(underTest.getIndividualResults().isEmpty());
  }
}
