/*
 * Copyright (c) 2011-2022 Ricardo do Canto
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
 * The {@link FileType} enum defines the possible type classification of an {@link Event event}'s
 * {@link EventFile file}.
 * <p>
 * The constant {@link #COURSE_MAPS} must be used for {@link EventFile files} that contains an
 * {@link Event event}'s map or course map.
 * <p>
 * The constant {@link #COVER_IMAGE} must be used for {@link EventFile file} that contains an
 * {@link Event event}'s the cover image.
 * <p>
 * The constant {@link #GUIDE} must be used for {@link EventFile files} that contains information
 * and guidance for to the {@link Event event}.
 * <p>
 * The constant {@link #POSTER} must be used for {@link EventFile file} that contains an
 * {@link Event event}'s poster image.
 * <p>
 * The constant {@link #RULES} must be used for {@link EventFile files} that contains an
 * {@link Event event}'s regulations or rules.
 * <p>
 * The constant {@link #START_LIST} must be used for {@link EventFile files} that contains an
 * {@link Event event}'s start list.
 */
public enum FileType {

  COURSE_MAPS("COURSE_MAPS"),
  COVER_IMAGE("COVER_IMAGE"),
  GUIDE("GUIDE"),
  POSTER("POSTER"),
  RULES("RULES"),
  START_LIST("START_LIST"),;

  private final String code;

  FileType(String code) {
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }
}
