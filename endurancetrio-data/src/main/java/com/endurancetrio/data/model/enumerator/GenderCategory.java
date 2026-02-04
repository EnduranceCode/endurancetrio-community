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

package com.endurancetrio.data.model.enumerator;

import com.endurancetrio.data.model.entity.Race;

/**
 * The {@link GenderCategory} enum represents the Gender Category of a {@link Race}.
 * <p>
 * It includes the following constants:
 * <ul>
 *   <li>{@link #OPEN} : used for the {@link Race races} that are open for both genders.</li>
 *   <li>{@link #FEMALE} : used for the {@link Race races} where only women can participate.</li>
 *   <li>{@link #MALE} : used for the {@link Race races} where only men can participate.</li>
 *   <li>
 *     {@link #MIXED} : used for {@link Race races} whose competitors are a mix from both genders
 *     (e.g., mixed relays).
 *   </li>
 * </ul>
 */
public enum GenderCategory {
  OPEN("OPEN"),
  FEMALE("FEMALE"),
  MALE("MALE"),
  MIXED("MIXED");

  private final String code;

  GenderCategory(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
