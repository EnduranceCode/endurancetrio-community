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
 * EVEN IF WE HAVE BEEN INFORMED OF THE POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.business.competitor.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.endurancetrio.business.competitor.dto.AthleteDTO;
import com.endurancetrio.business.competitor.fixtures.AthleteFixture;
import com.endurancetrio.data.competitor.model.entity.Athlete;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AthleteMapperTest {

  @InjectMocks
  private AthleteMapper underTest;

  private Athlete entityTest;

  @BeforeEach
  void setUp() {
    entityTest = AthleteFixture.standard();
  }

  @Test
  void shouldMapNullToNull() {
    assertNull(underTest.mapToAthleteDTO(null));
  }

  @Test
  void shouldMapEntityToDTO() {
    AthleteDTO result = underTest.mapToAthleteDTO(entityTest);

    assertNotNull(result);
    assertEquals(AthleteFixture.STANDARD_ID, result.id());
    assertEquals(AthleteFixture.STANDARD_LONG_NAME, result.longName());
    assertNull(result.birthName());
    assertEquals(AthleteFixture.STANDARD_KNOWN_NAME, result.knownName());
    assertEquals(AthleteFixture.STANDARD_GENDER, result.gender());
    assertEquals(AthleteFixture.STANDARD_COUNTRY, result.country());
    assertEquals(AthleteFixture.STANDARD_YEAR_OF_BIRTH, result.yearOfBirth());
  }

  @Test
  void shouldMapEntityWithNullOptionalsToDTO() {
    entityTest.setCountry(null);
    entityTest.setYearOfBirth(null);

    AthleteDTO result = underTest.mapToAthleteDTO(entityTest);

    assertNotNull(result);
    assertEquals(AthleteFixture.STANDARD_LONG_NAME, result.longName());
    assertEquals(AthleteFixture.STANDARD_KNOWN_NAME, result.knownName());
    assertEquals(AthleteFixture.STANDARD_GENDER, result.gender());
    assertNull(result.country());
    assertNull(result.yearOfBirth());
  }
}
