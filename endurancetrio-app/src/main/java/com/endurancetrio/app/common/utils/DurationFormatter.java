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

import java.time.Duration;

/**
 * The {@link DurationFormatter} class provides utility methods for formatting {@link Duration}
 * instances to human-readable strings.
 */
public final class DurationFormatter {

  private DurationFormatter() {
    throw new IllegalStateException("Utility Class");
  }

  /**
   * Formats the given {@link Duration} to a {@code HH:mm:ss} string.
   * <p>
   * Returns an empty string if {@code duration} is {@code null}.
   *
   * @param duration the duration to format, may be null
   * @return the formatted time string, or an empty string if null
   */
  public static String format(Duration duration) {
    if (duration == null) {
      return "";
    }
    return String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutesPart(),
        duration.toSecondsPart()
    );
  }
}
