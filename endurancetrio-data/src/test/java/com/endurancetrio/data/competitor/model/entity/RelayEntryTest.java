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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.endurancetrio.data.competitor.fixtures.RelayEntryFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link RelayEntry} entity.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class RelayEntryTest {

  private RelayEntry underTest;

  @BeforeEach
  void setUp() {
    underTest = RelayEntryFixture.standard();
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(RelayEntryFixture.STANDARD_ID, underTest.getId());
    assertEquals(RelayEntryFixture.STANDARD_FULL_NAME, underTest.getFullName());
    assertEquals(RelayEntryFixture.STANDARD_GENDER_CATEGORY, underTest.getGenderCategory());
    assertEquals(RelayEntryFixture.STANDARD_AGE_GROUP, underTest.getAgeGroup());
  }

  @Test
  void relayEntryShouldBeATeam() {
    assertInstanceOf(Team.class, underTest);
  }

  @Test
  void athletesShouldBeEmptyByDefault() {
    assertTrue(underTest.getAthletes().isEmpty());
  }

  @Test
  void addAthleteShouldAddAthlete() {
    Athlete athlete = new Athlete();
    athlete.setId(100L);

    underTest.addAthlete(athlete);

    assertEquals(1, underTest.getAthletes().size());
    assertTrue(underTest.getAthletes().contains(athlete));
  }

  @Test
  void addAthleteShouldIgnoreNull() {
    underTest.addAthlete(null);

    assertTrue(underTest.getAthletes().isEmpty());
  }

  @Test
  void removeAthleteShouldRemoveAthlete() {
    Athlete athlete = new Athlete();
    athlete.setId(100L);
    underTest.getAthletes().add(athlete);

    underTest.removeAthlete(athlete);

    assertTrue(underTest.getAthletes().isEmpty());
  }

  @Test
  void removeAthleteShouldIgnoreNull() {
    Athlete athlete = new Athlete();
    athlete.setId(100L);
    underTest.getAthletes().add(athlete);

    underTest.removeAthlete(null);

    assertFalse(underTest.getAthletes().isEmpty());
  }
}
