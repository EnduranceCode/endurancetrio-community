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

package com.endurancetrio.data.event.model.enumerator;

import com.endurancetrio.data.event.model.entity.Race;

/**
 * The {@link RaceStatus} enum represents the status of a {@link Race}
 * <p>
 * It includes the following constants:
 * <ul>
 *   <li>
 *     {@link #PLANNED} : used for the {@link Race races} that are planned but not yet
 *     (or were ever) completed.
 *   </li>
 *   <li>{@link #COMPLETED} : used for the {@link Race races} that were completed.</li>
 * </ul>
 */
public enum RaceStatus {
  PLANNED("PLANNED"),
  COMPLETED("COMPLETED");

  private final String code;

  RaceStatus(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
