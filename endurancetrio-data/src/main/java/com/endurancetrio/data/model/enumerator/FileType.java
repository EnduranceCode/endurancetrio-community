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

import com.endurancetrio.data.model.entity.Event;
import com.endurancetrio.data.model.entity.EventFile;

/**
 * The {@link FileType} enum is used to classify the type of file that is associated with an
 * {@link Event event}.
 * <p>
 * It includes the following types:
 * <ul>
 *   <li>
 *     {@link #COURSE_MAPS} : used for {@link EventFile files} that contains
 *     an {@link Event event}'s map or course map;
 *   </li>
 *   <li>
 *     {@link #COVER_IMAGE} : used for {@link EventFile file} that contains an {@link Event event}'s
 *     cover image;
 *   </li>
 *   <li>
 *     {@link #GUIDE} : used for {@link EventFile files} that contains information and guidance
 *     about the {@link Event event};
 *   </li>
 *   <li>
 *     {@link #POSTER} : used for {@link EventFile file} that contains an {@link Event event}'s
 *     poster image;
 *   </li>
 *   <li>
 *     {@link #RULES} : used for {@link EventFile files} that contains an {@link Event event}'s
 *     regulations or rules;
 *   </li>
 *   <li>
 *     {@link #START_LIST} : used for {@link EventFile files} that contains an {@link Event event}'s
 *     start list;
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
