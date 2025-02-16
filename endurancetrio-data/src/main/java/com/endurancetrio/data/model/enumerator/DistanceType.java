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

import com.endurancetrio.data.model.entity.Course;

/**
 * The {@link DistanceType} enum defines the possible distance classification of an
 * {@link Course course}.
 * <p>
 * It includes the following constants:
 * <ul>
 *   <li>
 *     {@link #ULTRA_MARATHON} : used for running ultra distance {@link Course courses}
 *     (50K, 100K, etc.).
 *   </li>
 *   <li>
 *     {@link #ULTRA_DISTANCE} : used for ultra endurance {@link Course courses}
 *     (ultra-triathlons, ultra-cycling, etc.).
 *   </li>
 *   <li>
 *     {@link #MARATHON} : used for marathon {@link Course courses}.
 *   </li>
 *   <li>
 *     {@link #LONG_DISTANCE} : used for long distance {@link Course courses}.
 *   </li>
 *   <li>
 *     {@link #FULL_DISTANCE} : used for triathlon full distance
 *     and other multisport {@link Course courses}.
 *   </li>
 *   <li>
 *     {@link #HALF_MARATHON} : used for half-marathon {@link Course courses}.
 *   </li>
 *   <li>
 *     {@link #MIDDLE_DISTANCE} : used for triathlon middle distance
 *     and other multisport {@link Course courses}.
 *   </li>
 *   <li>
 *     {@link #SHORT_DISTANCE} : used for short distance {@link Course courses}.
 *   </li>
 *   <li>
 *     {@link #STANDARD} : used for triathlon and other multisport {@link Course courses}.
 *   </li>
 *   <li>
 *     {@link #SPRINT} : used for triathlon and other multisport {@link Course courses}.
 *   </li>
 *   <li>
 *     {@link #TEN_KM} : used for 10 km {@link Course courses}.
 *   </li>
 *   <li>
 *     {@link #FIVE_KM} : used for 5 km {@link Course courses}.
 *   </li>
 *   <li>
 *     {@link #SUPER_SPRINT} : used for triathlon super-sprint
 *     and other multisport {@link Course courses}.
 *   </li>
 *   <li>
 *     {@link #YOUTH} : used for youth and kids {@link Course courses}.
 *   </li>
 * </ul>
 */
public enum DistanceType {
  ULTRA_MARATHON("ULTRA_MARATHON"),
  ULTRA_DISTANCE("ULTRA_DISTANCE"),
  MARATHON("MARATHON"),
  LONG_DISTANCE("LONG_DISTANCE"),
  FULL_DISTANCE("FULL_DISTANCE"),
  HALF_MARATHON("HALF_MARATHON"),
  MIDDLE_DISTANCE("MIDDLE_DISTANCE"),
  SHORT_DISTANCE("SHORT_DISTANCE"),
  STANDARD("STANDARD"),
  SPRINT("SPRINT"),
  TEN_KM("TEN_KM"),
  FIVE_KM("FIVE_KM"),
  SUPER_SPRINT("SUPER_SPRINT"),
  YOUTH("YOUTH");

  private final String code;

  DistanceType(String code) {
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
