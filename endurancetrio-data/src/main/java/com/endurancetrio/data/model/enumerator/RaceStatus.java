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
