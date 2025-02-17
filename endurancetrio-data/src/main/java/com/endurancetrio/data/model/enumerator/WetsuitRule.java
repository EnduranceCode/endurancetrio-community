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

import com.endurancetrio.data.model.entity.TriathlonBasedRace;

/**
 * The {@link WetsuitRule} enum represents the wetsuit rule for a {@link TriathlonBasedRace}.
 * <p>
 * It includes the following constants:
 * <ul>
 *   <li>
 *     {@link #FORBIDDEN} : used for the {@link TriathlonBasedRace races} where the use
 *     of wetsuits is forbidden.
 *   </li>
 *   <li>
 *     {@link #MANDATORY} : used for the {@link TriathlonBasedRace races} where the use
 *     of wetsuits is mandatory.
 *   </li>
 *   <li>
 *     {@link #OPTIONAL} : used for the {@link TriathlonBasedRace races} where the use
 *     of wetsuits is optional.
 *   </li>
 *   <li>
 *     {@link #UNKNOWN} : used for the {@link TriathlonBasedRace races} where the rule for the use
 *     of wetsuits is unknown.
 *   </li>
 * </ul>
 */
public enum WetsuitRule {
  FORBIDDEN("FORBIDDEN"),
  MANDATORY("MANDATORY"),
  OPTIONAL("OPTIONAL"),
  UNKNOWN("UNKNOWN");

  private final String code;

  WetsuitRule(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
