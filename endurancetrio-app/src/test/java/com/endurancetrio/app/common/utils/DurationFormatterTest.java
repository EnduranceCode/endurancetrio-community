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

package com.endurancetrio.app.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.InvocationTargetException;
import java.time.Duration;
import org.junit.jupiter.api.Test;

class DurationFormatterTest {

  @Test
  void formatShouldReturnEmptyStringWhenDurationIsNull() {
    assertEquals("", DurationFormatter.format(null));
  }

  @Test
  void formatShouldReturnZeroPaddedStringWhenDurationIsZero() {
    assertEquals("00:00:00", DurationFormatter.format(Duration.ZERO));
  }

  @Test
  void formatShouldReturnFormattedStringWhenDurationHasHoursMinutesAndSeconds() {
    Duration duration = Duration.ofHours(1).plusMinutes(2).plusSeconds(3);
    assertEquals("01:02:03", DurationFormatter.format(duration));
  }

  @Test
  void formatShouldReturnFormattedStringWhenDurationHasOnlyMinutesAndSeconds() {
    Duration duration = Duration.ofMinutes(45).plusSeconds(30);
    assertEquals("00:45:30", DurationFormatter.format(duration));
  }

  @Test
  void formatShouldReturnFormattedStringWhenDurationHasOnlySeconds() {
    Duration duration = Duration.ofSeconds(7);
    assertEquals("00:00:07", DurationFormatter.format(duration));
  }

  @Test
  void formatShouldHandleDurationsExceeding24Hours() {
    Duration duration = Duration.ofHours(25).plusMinutes(10).plusSeconds(5);
    assertEquals("25:10:05", DurationFormatter.format(duration));
  }

  @Test
  void constructorShouldThrow() {
    assertThrows(InvocationTargetException.class, () -> {
          var constructor = DurationFormatter.class.getDeclaredConstructor();
          constructor.setAccessible(true);
          constructor.newInstance();
        }
    );
  }
}
