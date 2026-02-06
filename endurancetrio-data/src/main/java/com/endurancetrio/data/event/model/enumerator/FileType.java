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

import com.endurancetrio.data.event.model.entity.Event;
import com.endurancetrio.data.event.model.entity.EventFile;

/**
 * The {@link FileType} enum is used to classify the type of file that is associated with an
 * {@link Event event}.
 * <p>
 * It includes the following constants:
 * <ul>
 *   <li>
 *     {@link #COURSE_MAPS} : used for {@link EventFile files} that contains an {@link Event}'s
 *     map or course map.
 *   </li>
 *   <li>
 *     {@link #COVER_IMAGE} : used for {@link EventFile files} that contains an {@link Event}'s
 *     cover image.
 *   </li>
 *   <li>
 *     {@link #GUIDE} : used for {@link EventFile files} that contains information and guidance
 *     about the {@link Event}.
 *   </li>
 *   <li>
 *     {@link #POSTER} : used for {@link EventFile file} that contains an {@link Event}'s
 *     poster image.
 *   </li>
 *   <li>
 *     {@link #RULES} : used for {@link EventFile files} that contains an {@link Event}'s
 *     regulations or rules.
 *   </li>
 *   <li>
 *     {@link #START_LIST} : used for {@link EventFile files} that contains an {@link Event}'s
 *     start list.
 *   </li>
 * </ul>
 */
public enum FileType {

  COURSE_MAPS("COURSE_MAPS"),
  COVER_IMAGE("COVER_IMAGE"),
  GUIDE("GUIDE"),
  POSTER("POSTER"),
  RULES("RULES"),
  START_LIST("START_LIST");

  private final String code;

  FileType(String code) {
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }

  @Override
  public String toString() {
    return code;
  }
}
