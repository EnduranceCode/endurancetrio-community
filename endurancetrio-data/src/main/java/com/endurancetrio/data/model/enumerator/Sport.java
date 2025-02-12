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
 * The {@link Sport} enum defines the possible sport classification of an {@link Course course}.
 * <p>
 * It includes the following types:
 * <ul>
 *  <li>{@link #OPEN_WATER} : used for open water swimming {@link Course courses};</li>
 *  <li>{@link #ROAD_CYCLING} : used for  road cycling {@link Course courses};</li>
 *  <li>{@link #MOUNTAIN_BIKING} : used for mountain biking {@link Course courses};</li>
 *  <li>{@link #ROAD_RUNNING} : used for road running {@link Course courses};</li>
 *  <li>{@link #TRAIL_RUNNING} : used for trail running {@link Course courses};</li>
 *  <li>{@link #TRIATHLON} : used for triathlon {@link Course courses};</li>
 *  <li>{@link #DUATHLON} : used for duathlon {@link Course courses};</li>
 *  <li>{@link #AQUATHLON} : used for aquathlon {@link Course courses};</li>
 *  <li>{@link #AQUABIKE} : used for aquabike {@link Course courses};</li>
 *  <li>{@link #CROSS_TRIATHLON} : used for cross triathlon {@link Course courses};</li>
 *  <li>{@link #CROSS_DUATHLON} : used for cross duathlon {@link Course courses};</li>
 *  <li>{@link #BIATHLON} : used for biathlon {@link Course courses};</li>
 *  <li>{@link #DOUBLE_BIATHLON} : used for double biathlon {@link Course courses};</li>
 *  <li>{@link #SWIMRUN} : used for swimrun {@link Course courses}.</li>
 * </ul>
 */
public enum Sport {
  OPEN_WATER("OPEN_WATER"),
  ROAD_CYCLING("ROAD_CYCLING"),
  MOUNTAIN_BIKING("MOUNTAIN_BIKING"),
  ROAD_RUNNING("ROAD_RUNNING"),
  TRAIL_RUNNING("TRAIL_RUNNING"),
  TRIATHLON("TRIATHLON"),
  DUATHLON("DUATHLON"),
  AQUATHLON("AQUATHLON"),
  AQUABIKE("AQUABIKE"),
  CROSS_TRIATHLON("CROSS_TRIATHLON"),
  CROSS_DUATHLON("CROSS_DUATHLON"),
  BIATHLON("BIATHLON"),
  DOUBLE_BIATHLON("DOUBLE_BIATHLON"),
  SWIMRUN("SWIMRUN");

  private final String code;

  Sport(String code) {
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
