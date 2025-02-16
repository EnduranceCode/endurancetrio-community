/*
 * Copyright (c) 2011-2025 Ricardo do Canto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.endurancetrio.data.model.enumerator;

import com.endurancetrio.data.model.entity.Race;

/**
 * The {@link RaceType} enum represents the type of a {@link Race}
 * <p>
 * It includes the following constants:
 * <ul>
 *   <li>
 *     {@link #INDIVIDUAL_PARENT} : used for the {@link Race races} that have no parent {@link Race},
 *     that are not derived from any other {@link Race}.
 *   </li>
 *   <li>
 *     {@link #INDIVIDUAL_DERIVED} : used for the {@link Race races} that are derived
 *     from another {@link Race race(s)}.
 *   </li>
 *   <li>
 *     {@link #TEAM_BY_RANK} : used for the {@link Race races} whose results are based
 *     on the rank of the team members on an individual {@link Race}.
 *   </li>
 *   <li>
 *     {@link #TEAM_BY_TIME} : used for the {@link Race races} whose results are based
 *     on the time of the team members on an individual {@link Race}.
 *   </li>
 *   <li>
 *     {@link #TEAM_BY_POINTS} : used for the {@link Race races} whose results are based
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
