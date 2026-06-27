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
import static org.junit.jupiter.api.Assertions.assertNull;

import com.endurancetrio.data.competitor.fixtures.AthleteFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AthleteTest {

  private Athlete underTest;

  @BeforeEach
  void setUp() {
    underTest = AthleteFixture.standard();
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(AthleteFixture.STANDARD_ID, underTest.getId());
    assertEquals(AthleteFixture.STANDARD_LONG_NAME, underTest.getLongName());
    assertNull(underTest.getBirthName());
    assertEquals(AthleteFixture.STANDARD_KNOWN_NAME, underTest.getKnownName());
    assertEquals(AthleteFixture.STANDARD_GENDER, underTest.getGender());
    assertEquals(AthleteFixture.STANDARD_COUNTRY, underTest.getCountry());
    assertEquals(AthleteFixture.STANDARD_YEAR_OF_BIRTH, underTest.getYearOfBirth());
  }
}
