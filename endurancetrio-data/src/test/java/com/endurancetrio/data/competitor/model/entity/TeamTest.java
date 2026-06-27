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

package com.endurancetrio.data.competitor.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.endurancetrio.data.competitor.fixtures.TeamFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link Team} entity.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class TeamTest {

  private Team underTest;

  @BeforeEach
  void setUp() {
    underTest = TeamFixture.standard();
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(TeamFixture.STANDARD_ID, underTest.getId());
    assertEquals(TeamFixture.STANDARD_FULL_NAME, underTest.getFullName());
    assertEquals(TeamFixture.STANDARD_SHORT_NAME, underTest.getShortName());
    assertEquals(TeamFixture.STANDARD_CITY, underTest.getCity());
    assertEquals(TeamFixture.STANDARD_COUNTY, underTest.getCounty());
    assertEquals(TeamFixture.STANDARD_DISTRICT, underTest.getDistrict());
  }
}
