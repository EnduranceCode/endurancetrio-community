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

import java.time.Duration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link DurationToIntegerConverter} converter.
 */
class DurationToIntegerConverterTest {

  private DurationToIntegerConverter underTest;

  @BeforeEach
  void setUp() {
    underTest = new DurationToIntegerConverter();
  }

  @Test
  void shouldConvertDurationToDatabaseColumn() {
    Duration duration = Duration.ofMinutes(90);
    Integer expected = 5_400_000;

    assertEquals(expected, underTest.convertToDatabaseColumn(duration));
  }

  @Test
  void shouldReturnNullWhenConvertingNullDurationToDatabaseColumn() {
    assertNull(underTest.convertToDatabaseColumn(null));
  }

  @Test
  void shouldConvertMillisecondsToDuration() {
    Integer milliseconds = 5_400_000;
    Duration expected = Duration.ofMinutes(90);

    assertEquals(expected, underTest.convertToEntityAttribute(milliseconds));
  }

  @Test
  void shouldReturnNullWhenConvertingNullMillisecondsToDuration() {
    assertNull(underTest.convertToEntityAttribute(null));
  }
}
