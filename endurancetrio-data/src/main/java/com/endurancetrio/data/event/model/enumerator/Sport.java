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

import com.endurancetrio.data.event.model.entity.Course;

/**
 * The {@link Sport} enum represent the possible sport classifications for a {@link Course course}.
 * <p>
 * It includes the following constants:
 * <ul>
 *  <li>{@link #OPEN_WATER} : used for open water swimming {@link Course courses}.</li>
 *  <li>{@link #ROAD_CYCLING} : used for  road cycling {@link Course courses};</li>
 *  <li>{@link #MOUNTAIN_BIKING} : used for mountain biking {@link Course courses}.</li>
 *  <li>{@link #ROAD_RUNNING} : used for road running {@link Course courses}.</li>
 *  <li>{@link #TRAIL_RUNNING} : used for trail running {@link Course courses}.</li>
 *  <li>{@link #TRIATHLON} : used for triathlon {@link Course courses}.</li>
 *  <li>{@link #DUATHLON} : used for duathlon {@link Course courses}.</li>
 *  <li>{@link #AQUATHLON} : used for aquathlon {@link Course courses}.</li>
 *  <li>{@link #AQUABIKE} : used for aquabike {@link Course courses}.</li>
 *  <li>{@link #CROSS_TRIATHLON} : used for cross triathlon {@link Course courses}.</li>
 *  <li>{@link #CROSS_DUATHLON} : used for cross duathlon {@link Course courses}.</li>
 *  <li>{@link #BIATHLON} : used for biathlon {@link Course courses}.</li>
 *  <li>{@link #DOUBLE_BIATHLON} : used for double biathlon {@link Course courses}.</li>
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
