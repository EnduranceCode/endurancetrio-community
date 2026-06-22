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

package com.endurancetrio.data.event.model.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.endurancetrio.data.event.model.enumerator.RaceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link RaceTypeConverter} converter.
 */
class RaceTypeConverterTest {

  private RaceTypeConverter underTest;

  @BeforeEach
  void setUp() {
    underTest = new RaceTypeConverter();
  }

  @Test
  void shouldConvertRaceTypeToDatabaseColumn() {
    assertEquals("INDIVIDUAL_PARENT", underTest.convertToDatabaseColumn(RaceType.INDIVIDUAL_PARENT));
    assertEquals("INDIVIDUAL_DERIVED", underTest.convertToDatabaseColumn(RaceType.INDIVIDUAL_DERIVED));
    assertEquals("TEAM_BY_RANK", underTest.convertToDatabaseColumn(RaceType.TEAM_BY_RANK));
    assertEquals("TEAM_BY_TIME", underTest.convertToDatabaseColumn(RaceType.TEAM_BY_TIME));
    assertEquals("TEAM_BY_POINTS", underTest.convertToDatabaseColumn(RaceType.TEAM_BY_POINTS));
    assertEquals("TEAM_RELAY_PARENT", underTest.convertToDatabaseColumn(RaceType.TEAM_RELAY_PARENT));
    assertEquals("TEAM_RELAY_DERIVED", underTest.convertToDatabaseColumn(RaceType.TEAM_RELAY_DERIVED));
    assertEquals("MIXED_RELAY_PARENT", underTest.convertToDatabaseColumn(RaceType.MIXED_RELAY_PARENT));
    assertEquals("MIXED_RELAY_DERIVED", underTest.convertToDatabaseColumn(RaceType.MIXED_RELAY_DERIVED));
  }

  @Test
  void shouldReturnNullWhenConvertingNullRaceTypeToDatabaseColumn() {
    assertNull(underTest.convertToDatabaseColumn(null));
  }

  @Test
  void shouldConvertCodeToRaceType() {
    assertEquals(RaceType.INDIVIDUAL_PARENT, underTest.convertToEntityAttribute("INDIVIDUAL_PARENT"));
    assertEquals(RaceType.INDIVIDUAL_DERIVED, underTest.convertToEntityAttribute("INDIVIDUAL_DERIVED"));
    assertEquals(RaceType.TEAM_BY_RANK, underTest.convertToEntityAttribute("TEAM_BY_RANK"));
    assertEquals(RaceType.TEAM_BY_TIME, underTest.convertToEntityAttribute("TEAM_BY_TIME"));
    assertEquals(RaceType.TEAM_BY_POINTS, underTest.convertToEntityAttribute("TEAM_BY_POINTS"));
    assertEquals(RaceType.TEAM_RELAY_PARENT, underTest.convertToEntityAttribute("TEAM_RELAY_PARENT"));
    assertEquals(RaceType.TEAM_RELAY_DERIVED, underTest.convertToEntityAttribute("TEAM_RELAY_DERIVED"));
    assertEquals(RaceType.MIXED_RELAY_PARENT, underTest.convertToEntityAttribute("MIXED_RELAY_PARENT"));
    assertEquals(RaceType.MIXED_RELAY_DERIVED, underTest.convertToEntityAttribute("MIXED_RELAY_DERIVED"));
  }

  @Test
  void shouldThrowExceptionForInvalidCode() {
    assertThrows(IllegalArgumentException.class,
        () -> underTest.convertToEntityAttribute("INVALID"));
  }
}
