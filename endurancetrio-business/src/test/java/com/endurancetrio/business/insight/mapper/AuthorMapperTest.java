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

package com.endurancetrio.business.insight.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.endurancetrio.business.insight.dto.AuthorDTO;
import com.endurancetrio.business.insight.fixtures.AuthorFixture;
import com.endurancetrio.data.competitor.model.entity.Athlete;
import com.endurancetrio.data.insight.model.entity.Author;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AuthorMapperTest {

  private static final Long ATHLETE_ID = 42L;

  private Author entityWithoutAthlete;
  private Author entityWithAthlete;

  private AuthorMapper underTest;

  @BeforeEach
  void setUp() {
    underTest = new AuthorMapper();

    entityWithoutAthlete = AuthorFixture.standard();

    entityWithAthlete = AuthorFixture.standard();
    Athlete athlete = new Athlete();
    athlete.setId(ATHLETE_ID);
    entityWithAthlete.setAthlete(athlete);
  }

  @Test
  void mapEntity() {
    AuthorDTO result = underTest.map(entityWithoutAthlete);

    assertNotNull(result);
    assertEquals(AuthorFixture.STANDARD_ID, result.id());
    assertEquals(AuthorFixture.STANDARD_KNOWN_NAME, result.knownName());
    assertEquals(AuthorFixture.STANDARD_BIO, result.bio());
    assertNull(result.profileFileName());
    assertNull(result.athleteId());
  }

  @Test
  void mapEntityWithAthlete() {
    AuthorDTO result = underTest.map(entityWithAthlete);

    assertNotNull(result);
    assertEquals(ATHLETE_ID, result.athleteId());
  }

  @Test
  void mapNullEntity() {
    AuthorDTO result = underTest.map(null);

    assertNull(result);
  }
}
