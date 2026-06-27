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
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.endurancetrio.business.competitor.fixtures.AthleteDTOFixtures;
import com.endurancetrio.business.competitor.fixtures.AthleteFixture;
import com.endurancetrio.data.competitor.model.enumerator.AthleteGender;
import com.endurancetrio.data.competitor.model.enumerator.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AthleteDTOTest {

  private AthleteDTO underTest;

  @BeforeEach
  void setUp() {
    underTest = AthleteDTOFixtures.standard();
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(AthleteFixture.STANDARD_ID, underTest.id());
    assertEquals(AthleteFixture.STANDARD_LONG_NAME, underTest.longName());
    assertNull(underTest.birthName());
    assertEquals(AthleteFixture.STANDARD_KNOWN_NAME, underTest.knownName());
    assertEquals(AthleteFixture.STANDARD_GENDER, underTest.gender());
    assertEquals(AthleteFixture.STANDARD_COUNTRY, underTest.country());
    assertEquals(AthleteFixture.STANDARD_YEAR_OF_BIRTH, underTest.yearOfBirth());
  }

  @Test
  void shouldRejectNullLongName() {
    assertThrows(IllegalArgumentException.class,
        () -> new AthleteDTO(1L, null, null, "Known", AthleteGender.MALE, Country.POR, 1961)
    );
  }

  @Test
  void shouldRejectBlankLongName() {
    assertThrows(IllegalArgumentException.class,
        () -> new AthleteDTO(1L, " ", null, "Known", AthleteGender.MALE, Country.POR, 1961)
    );
  }

  @Test
  void shouldRejectNullKnownName() {
    assertThrows(IllegalArgumentException.class,
        () -> new AthleteDTO(1L, "Long Name", null, null, AthleteGender.MALE, Country.POR, 1961)
    );
  }

  @Test
  void shouldRejectBlankKnownName() {
    assertThrows(IllegalArgumentException.class,
        () -> new AthleteDTO(1L, "Long Name", null, " ", AthleteGender.MALE, Country.POR, 1961)
    );
  }

  @Test
  void shouldRejectNullGender() {
    assertThrows(IllegalArgumentException.class,
        () -> new AthleteDTO(1L, "Long Name", null, "Known", null, Country.POR, 1961)
    );
  }

  @Test
  void birthNameCanBeNull() {
    AthleteDTO athlete = new AthleteDTO(1L, "Long Name", null, "Known", AthleteGender.MALE,
        Country.POR, 1961
    );
    assertNull(athlete.birthName());
  }

  @Test
  void countryCanBeNull() {
    AthleteDTO athlete = new AthleteDTO(1L, "Long Name", null, "Known", AthleteGender.MALE, null,
        1961
    );
    assertNull(athlete.country());
  }

  @Test
  void yearOfBirthCanBeNull() {
    AthleteDTO athlete = new AthleteDTO(1L, "Long Name", null, "Known", AthleteGender.MALE,
        Country.POR, null
    );
    assertNull(athlete.yearOfBirth());
  }

  @Test
  void standardFixtureShouldHaveNonNullDependencies() {
    assertNotNull(AthleteDTOFixtures.standard().knownName());
    assertNotNull(AthleteDTOFixtures.standard().gender());
    assertNotNull(AthleteDTOFixtures.athleteCavaleiro().knownName());
    assertNotNull(AthleteDTOFixtures.athleteCavaleiro().gender());
    assertNotNull(AthleteDTOFixtures.athleteBello().knownName());
    assertNotNull(AthleteDTOFixtures.athleteBello().gender());
  }
}
