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

package com.endurancetrio.business.competitor.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.endurancetrio.business.competitor.fixtures.TeamDTOFixtures;
import com.endurancetrio.business.competitor.fixtures.TeamFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamDTOTest {

  private TeamDTO underTest;

  @BeforeEach
  void setUp() {
    underTest = TeamDTOFixtures.standard();
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(TeamFixture.STANDARD_ID, underTest.id());
    assertEquals(TeamFixture.STANDARD_FULL_NAME, underTest.fullName());
    assertEquals(TeamFixture.STANDARD_SHORT_NAME, underTest.shortName());
    assertEquals(TeamFixture.STANDARD_CITY, underTest.city());
    assertEquals(TeamFixture.STANDARD_COUNTY, underTest.county());
    assertEquals(TeamFixture.STANDARD_DISTRICT, underTest.district());
  }

  @Test
  void allFieldsCanBeNull() {
    TeamDTO dto = new TeamDTO(null, null, null, null, null, null);
    assertNull(dto.id());
    assertNull(dto.fullName());
    assertNull(dto.shortName());
    assertNull(dto.city());
    assertNull(dto.county());
    assertNull(dto.district());
  }
}
