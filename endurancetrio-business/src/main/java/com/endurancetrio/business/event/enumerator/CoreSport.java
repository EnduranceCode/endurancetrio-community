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

package com.endurancetrio.business.event.enumerator;

import com.endurancetrio.business.event.dto.DistanceLegDTO;

/**
 * The {@link CoreSport} enum represents the core sport categories used in a
 * {@link DistanceLegDTO distance leg}.
 * <p>
 * It includes the following constants:
 * <ul>
 *   <li>{@link #SWIM} : used for swimming legs.</li>
 *   <li>{@link #BIKE} : used for cycling legs.</li>
 *   <li>{@link #RUN} : used for running legs.</li>
 * </ul>
 */
public enum CoreSport {
  SWIM("SWIM"),
  BIKE("BIKE"),
  RUN("RUN");

  private final String code;

  CoreSport(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  @Override
  public String toString() {
    return code;
  }
}
