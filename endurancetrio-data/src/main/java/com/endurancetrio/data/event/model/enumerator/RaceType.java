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
 * The {@link RaceType} enum represents the type of a {@link Race}
 * <p>
 * It includes the following constants:
 * <ul>
 *   <li>
 *     {@link #INDIVIDUAL_PARENT} : used for the individual {@link Race races} that have no parent
 *     {@link Race}, that are not derived from any other {@link Race}.
 *   </li>
 *   <li>
 *     {@link #INDIVIDUAL_DERIVED} : used for the individual {@link Race races} that are derived
 *     (or a subset) of another {@link Race race(s)}.
 *   </li>
 *   <li>
 *     {@link #TEAM_BY_RANK} : used for the collective {@link Race races} whose results are based
 *     on the rank of the team members on an individual {@link Race}.
 *   </li>
 *   <li>
 *     {@link #TEAM_BY_TIME} : used for the collective {@link Race races} whose results are based
 *     on the time of the team members on an individual {@link Race}.
 *   </li>
 *   <li>
 *     {@link #TEAM_BY_POINTS} : used for the collective {@link Race races} whose results are based
 *     on the points of the team members on an individual {@link Race}.
 *   </li>
 *   <li>
 *     {@link #TEAM_RELAY_PARENT} : used for the team relay {@link Race races}
 *     that have no parent {@link Race}, that are not derived from any other team relay {@link Race}.
 *   </li>
 *   <li>
 *     {@link #TEAM_RELAY_DERIVED} : used for the team relay {@link Race races}
 *     that are derived from another team relay {@link Race}.
 *   </li>
 *   <li>
 *     {@link #MIXED_RELAY_PARENT} : used for the mixed relay {@link Race races}
 *     that have no parent {@link Race}, that are not derived from any other mixed relay {@link Race}.
 *   </li>
 *   <li>
 *     {@link #MIXED_RELAY_DERIVED} : used for the mixed relay {@link Race races}
 *     that are derived from another mixed relay {@link Race}
 *   </li>
 * </ul>
 */
public enum RaceType {
  INDIVIDUAL_PARENT("INDIVIDUAL_PARENT"),
  INDIVIDUAL_DERIVED("INDIVIDUAL_DERIVED"),
  TEAM_BY_RANK("TEAM_BY_RANK"),
  TEAM_BY_TIME("TEAM_BY_TIME"),
  TEAM_BY_POINTS("TEAM_BY_POINTS"),
  TEAM_RELAY_PARENT("TEAM_RELAY_PARENT"),
  TEAM_RELAY_DERIVED("TEAM_RELAY_DERIVED"),
  MIXED_RELAY_PARENT("MIXED_RELAY_PARENT"),
  MIXED_RELAY_DERIVED("MIXED_RELAY_DERIVED");

  private final String code;

  RaceType(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
