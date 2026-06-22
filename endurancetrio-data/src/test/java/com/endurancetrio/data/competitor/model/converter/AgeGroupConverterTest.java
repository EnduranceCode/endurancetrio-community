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

package com.endurancetrio.data.competitor.model.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link AgeGroupConverter} converter.
 */
class AgeGroupConverterTest {

  private AgeGroupConverter underTest;

  @BeforeEach
  void setUp() {
    underTest = new AgeGroupConverter();
  }

  @Test
  void shouldConvertAgeGroupToDatabaseColumn() {
    assertEquals("OPEN", underTest.convertToDatabaseColumn(AgeGroup.OPEN));
    assertEquals("SEN", underTest.convertToDatabaseColumn(AgeGroup.SEN));
    assertEquals("ELITE", underTest.convertToDatabaseColumn(AgeGroup.ELITE));
  }

  @Test
  void shouldReturnNullWhenConvertingNullAgeGroupToDatabaseColumn() {
    assertNull(underTest.convertToDatabaseColumn(null));
  }

  @Test
  void shouldConvertCodeToAgeGroup() {
    assertEquals(AgeGroup.OPEN, underTest.convertToEntityAttribute("OPEN"));
    assertEquals(AgeGroup.SEN, underTest.convertToEntityAttribute("SEN"));
    assertEquals(AgeGroup.ELITE, underTest.convertToEntityAttribute("ELITE"));
  }

  @Test
  void shouldThrowExceptionForInvalidCode() {
    assertThrows(IllegalArgumentException.class,
        () -> underTest.convertToEntityAttribute("INVALID")
    );
  }
}
