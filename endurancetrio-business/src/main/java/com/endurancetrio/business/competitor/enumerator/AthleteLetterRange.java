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

package com.endurancetrio.business.competitor.enumerator;

import java.util.Locale;

/**
 * Fixed first-letter ranges used by the athlete directory.
 */
public enum AthleteLetterRange {
  A_F('A', 'F'),
  G_L('G', 'L'),
  M_R('M', 'R'),
  S_Z('S', 'Z');

  private final char start;
  private final char end;

  AthleteLetterRange(char start, char end) {
    this.start = start;
    this.end = end;
  }

  public String getId() {
    return name();
  }

  public char getStart() {
    return start;
  }

  public char getEnd() {
    return end;
  }

  /**
   * Resolves a case-insensitive range identifier.
   *
   * @param id the range identifier
   * @return the matching range, or {@code null} when unknown
   */
  public static AthleteLetterRange fromId(String id) {
    if (id == null || id.isBlank()) {
      return null;
    }
    try {
      return valueOf(id.trim().toUpperCase(Locale.ROOT));
    } catch (IllegalArgumentException exception) {
      return null;
    }
  }
}
