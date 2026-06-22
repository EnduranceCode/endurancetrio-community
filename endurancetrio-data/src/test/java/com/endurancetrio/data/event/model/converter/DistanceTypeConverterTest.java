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

import com.endurancetrio.data.event.model.enumerator.DistanceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link DistanceTypeConverter} converter.
 */
class DistanceTypeConverterTest {

  private DistanceTypeConverter underTest;

  @BeforeEach
  void setUp() {
    underTest = new DistanceTypeConverter();
  }

  @Test
  void shouldConvertDistanceTypeToDatabaseColumn() {
    assertEquals("ULTRA_MARATHON", underTest.convertToDatabaseColumn(DistanceType.ULTRA_MARATHON));
    assertEquals("ULTRA_DISTANCE", underTest.convertToDatabaseColumn(DistanceType.ULTRA_DISTANCE));
    assertEquals("MARATHON", underTest.convertToDatabaseColumn(DistanceType.MARATHON));
    assertEquals("LONG_DISTANCE", underTest.convertToDatabaseColumn(DistanceType.LONG_DISTANCE));
    assertEquals("FULL_DISTANCE", underTest.convertToDatabaseColumn(DistanceType.FULL_DISTANCE));
    assertEquals("HALF_MARATHON", underTest.convertToDatabaseColumn(DistanceType.HALF_MARATHON));
    assertEquals("MIDDLE_DISTANCE", underTest.convertToDatabaseColumn(DistanceType.MIDDLE_DISTANCE));
    assertEquals("SHORT_DISTANCE", underTest.convertToDatabaseColumn(DistanceType.SHORT_DISTANCE));
    assertEquals("STANDARD", underTest.convertToDatabaseColumn(DistanceType.STANDARD));
    assertEquals("SPRINT", underTest.convertToDatabaseColumn(DistanceType.SPRINT));
    assertEquals("TEN_KM", underTest.convertToDatabaseColumn(DistanceType.TEN_KM));
    assertEquals("FIVE_KM", underTest.convertToDatabaseColumn(DistanceType.FIVE_KM));
    assertEquals("SUPER_SPRINT", underTest.convertToDatabaseColumn(DistanceType.SUPER_SPRINT));
    assertEquals("YOUTH", underTest.convertToDatabaseColumn(DistanceType.YOUTH));
  }

  @Test
  void shouldReturnNullWhenConvertingNullDistanceTypeToDatabaseColumn() {
    assertNull(underTest.convertToDatabaseColumn(null));
  }

  @Test
  void shouldConvertCodeToDistanceType() {
    assertEquals(DistanceType.ULTRA_MARATHON, underTest.convertToEntityAttribute("ULTRA_MARATHON"));
    assertEquals(DistanceType.ULTRA_DISTANCE, underTest.convertToEntityAttribute("ULTRA_DISTANCE"));
    assertEquals(DistanceType.MARATHON, underTest.convertToEntityAttribute("MARATHON"));
    assertEquals(DistanceType.LONG_DISTANCE, underTest.convertToEntityAttribute("LONG_DISTANCE"));
    assertEquals(DistanceType.FULL_DISTANCE, underTest.convertToEntityAttribute("FULL_DISTANCE"));
    assertEquals(DistanceType.HALF_MARATHON, underTest.convertToEntityAttribute("HALF_MARATHON"));
    assertEquals(DistanceType.MIDDLE_DISTANCE, underTest.convertToEntityAttribute("MIDDLE_DISTANCE"));
    assertEquals(DistanceType.SHORT_DISTANCE, underTest.convertToEntityAttribute("SHORT_DISTANCE"));
    assertEquals(DistanceType.STANDARD, underTest.convertToEntityAttribute("STANDARD"));
    assertEquals(DistanceType.SPRINT, underTest.convertToEntityAttribute("SPRINT"));
    assertEquals(DistanceType.TEN_KM, underTest.convertToEntityAttribute("TEN_KM"));
    assertEquals(DistanceType.FIVE_KM, underTest.convertToEntityAttribute("FIVE_KM"));
    assertEquals(DistanceType.SUPER_SPRINT, underTest.convertToEntityAttribute("SUPER_SPRINT"));
    assertEquals(DistanceType.YOUTH, underTest.convertToEntityAttribute("YOUTH"));
  }

  @Test
  void shouldThrowExceptionForInvalidCode() {
    assertThrows(IllegalArgumentException.class,
        () -> underTest.convertToEntityAttribute("INVALID"));
  }
}
