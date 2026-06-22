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

import com.endurancetrio.data.event.model.enumerator.RaceStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link RaceStatusConverter} converter.
 */
class RaceStatusConverterTest {

  private RaceStatusConverter underTest;

  @BeforeEach
  void setUp() {
    underTest = new RaceStatusConverter();
  }

  @Test
  void shouldConvertRaceStatusToDatabaseColumn() {
    assertEquals("PLANNED", underTest.convertToDatabaseColumn(RaceStatus.PLANNED));
    assertEquals("COMPLETED", underTest.convertToDatabaseColumn(RaceStatus.COMPLETED));
  }

  @Test
  void shouldReturnNullWhenConvertingNullRaceStatusToDatabaseColumn() {
    assertNull(underTest.convertToDatabaseColumn(null));
  }

  @Test
  void shouldConvertCodeToRaceStatus() {
    assertEquals(RaceStatus.PLANNED, underTest.convertToEntityAttribute("PLANNED"));
    assertEquals(RaceStatus.COMPLETED, underTest.convertToEntityAttribute("COMPLETED"));
  }

  @Test
  void shouldThrowExceptionForInvalidCode() {
    assertThrows(IllegalArgumentException.class,
        () -> underTest.convertToEntityAttribute("INVALID"));
  }
}
