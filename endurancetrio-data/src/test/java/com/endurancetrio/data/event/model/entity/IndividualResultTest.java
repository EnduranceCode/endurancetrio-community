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
import static org.junit.jupiter.api.Assertions.assertNull;

import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link IndividualResult} entity.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class IndividualResultTest {

  private IndividualResult underTest;

  @BeforeEach
  void setUp() {
    underTest = new IndividualResult();
    underTest.setId(1L);
    underTest.setRank(1);
    underTest.setTeamName("Individual");
    underTest.setBib("101");
    underTest.setSwim(Duration.ofMinutes(20));
    underTest.setBike(Duration.ofMinutes(60));
    underTest.setRun(Duration.ofMinutes(30));
    underTest.setTotal(Duration.ofMinutes(110));
    underTest.setPoints(1000);
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(1L, underTest.getId());
    assertEquals(1, underTest.getRank());
    assertNull(underTest.getRace());
    assertNull(underTest.getSourceResult());
    assertNull(underTest.getPenalty());
    assertNull(underTest.getAthlete());
    assertNull(underTest.getTeam());
    assertEquals("Individual", underTest.getTeamName());
    assertNull(underTest.getAgeGroup());
    assertNull(underTest.getParaClass());
    assertEquals("101", underTest.getBib());
    assertEquals(Duration.ofMinutes(20), underTest.getSwim());
    assertNull(underTest.getFirstBike());
    assertNull(underTest.getFirstRun());
    assertNull(underTest.getT1());
    assertEquals(Duration.ofMinutes(60), underTest.getBike());
    assertNull(underTest.getT2());
    assertEquals(Duration.ofMinutes(30), underTest.getRun());
    assertNull(underTest.getSecondRun());
    assertNull(underTest.getT3());
    assertNull(underTest.getSecondBike());
    assertEquals(Duration.ofMinutes(110), underTest.getTotal());
    assertEquals(1000, underTest.getPoints());
  }
}
