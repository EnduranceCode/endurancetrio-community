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

import com.endurancetrio.data.event.model.enumerator.Sport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link SportConverter} converter.
 */
class SportConverterTest {

  private SportConverter underTest;

  @BeforeEach
  void setUp() {
    underTest = new SportConverter();
  }

  @Test
  void shouldConvertSportToDatabaseColumn() {
    assertEquals("OPEN_WATER", underTest.convertToDatabaseColumn(Sport.OPEN_WATER));
    assertEquals("ROAD_CYCLING", underTest.convertToDatabaseColumn(Sport.ROAD_CYCLING));
    assertEquals("MOUNTAIN_BIKING", underTest.convertToDatabaseColumn(Sport.MOUNTAIN_BIKING));
    assertEquals("ROAD_RUNNING", underTest.convertToDatabaseColumn(Sport.ROAD_RUNNING));
    assertEquals("TRAIL_RUNNING", underTest.convertToDatabaseColumn(Sport.TRAIL_RUNNING));
    assertEquals("TRIATHLON", underTest.convertToDatabaseColumn(Sport.TRIATHLON));
    assertEquals("DUATHLON", underTest.convertToDatabaseColumn(Sport.DUATHLON));
    assertEquals("AQUATHLON", underTest.convertToDatabaseColumn(Sport.AQUATHLON));
    assertEquals("AQUABIKE", underTest.convertToDatabaseColumn(Sport.AQUABIKE));
    assertEquals("CROSS_TRIATHLON", underTest.convertToDatabaseColumn(Sport.CROSS_TRIATHLON));
    assertEquals("CROSS_DUATHLON", underTest.convertToDatabaseColumn(Sport.CROSS_DUATHLON));
    assertEquals("BIATHLON", underTest.convertToDatabaseColumn(Sport.BIATHLON));
    assertEquals("DOUBLE_BIATHLON", underTest.convertToDatabaseColumn(Sport.DOUBLE_BIATHLON));
    assertEquals("SWIMRUN", underTest.convertToDatabaseColumn(Sport.SWIMRUN));
  }

  @Test
  void shouldReturnNullWhenConvertingNullSportToDatabaseColumn() {
    assertNull(underTest.convertToDatabaseColumn(null));
  }

  @Test
  void shouldConvertCodeToSport() {
    assertEquals(Sport.OPEN_WATER, underTest.convertToEntityAttribute("OPEN_WATER"));
    assertEquals(Sport.ROAD_CYCLING, underTest.convertToEntityAttribute("ROAD_CYCLING"));
    assertEquals(Sport.MOUNTAIN_BIKING, underTest.convertToEntityAttribute("MOUNTAIN_BIKING"));
    assertEquals(Sport.ROAD_RUNNING, underTest.convertToEntityAttribute("ROAD_RUNNING"));
    assertEquals(Sport.TRAIL_RUNNING, underTest.convertToEntityAttribute("TRAIL_RUNNING"));
    assertEquals(Sport.TRIATHLON, underTest.convertToEntityAttribute("TRIATHLON"));
    assertEquals(Sport.DUATHLON, underTest.convertToEntityAttribute("DUATHLON"));
    assertEquals(Sport.AQUATHLON, underTest.convertToEntityAttribute("AQUATHLON"));
    assertEquals(Sport.AQUABIKE, underTest.convertToEntityAttribute("AQUABIKE"));
    assertEquals(Sport.CROSS_TRIATHLON, underTest.convertToEntityAttribute("CROSS_TRIATHLON"));
    assertEquals(Sport.CROSS_DUATHLON, underTest.convertToEntityAttribute("CROSS_DUATHLON"));
    assertEquals(Sport.BIATHLON, underTest.convertToEntityAttribute("BIATHLON"));
    assertEquals(Sport.DOUBLE_BIATHLON, underTest.convertToEntityAttribute("DOUBLE_BIATHLON"));
    assertEquals(Sport.SWIMRUN, underTest.convertToEntityAttribute("SWIMRUN"));
  }

  @Test
  void shouldThrowExceptionForInvalidCode() {
    assertThrows(IllegalArgumentException.class,
        () -> underTest.convertToEntityAttribute("INVALID"));
  }
}
