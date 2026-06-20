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

package com.endurancetrio.data.competitor.model.enumerator;

import com.endurancetrio.data.competitor.model.entity.Athlete;

/**
 * The {@link AthleteGender} enum represents the gender of an {@link Athlete}.
 * <p>
 * It includes the following constants:
 * <ul>
 *   <li>{@link #MALE} : used for male athletes.</li>
 *   <li>{@link #FEMALE} : used for female athletes.</li>
 * </ul>
 */
public enum AthleteGender {
  FEMALE("FEMALE", "F"),
  MALE("MALE", "M");

  private final String code;
  private final String shortCode;

  AthleteGender(String code, String shortCode) {
    this.code = code;
    this.shortCode = shortCode;
  }

  /**
   * Returns the full code of the gender (e.g., {@code "MALE"}, {@code "FEMALE"}).
   * <p>
   * This value is used for persistence and API interchange.
   *
   * @return the full gender code
   */
  public String getCode() {
    return code;
  }

  /**
   * Returns the short code of the gender (e.g., {@code "M"}, {@code "F"}).
   * <p>
   * This value is intended for compact display in UI tables, result sheets, and scoreboards.
   *
   * @return the short gender code
   */
  public String getShortCode() {
    return shortCode;
  }

  @Override
  public String toString() {
    return code;
  }
}
