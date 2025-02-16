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
