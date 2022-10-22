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

/**
 * The {@link FileType} enum defines the possible type classification of an
 * {@link com.endurancetrio.data.model.entity.Event event}'s
 * {@link com.endurancetrio.data.model.entity.EventFile file}.
 * <p>
 * The constant {@link #GUIDE} must be used for
 * {@link com.endurancetrio.data.model.entity.EventFile files} that contains information and
 * guidance for to the {@link com.endurancetrio.data.model.entity.Event event}.
 * <p>
 * The constant {@link #RULES} must be used for
 * {@link com.endurancetrio.data.model.entity.EventFile files} that contains the regulations or
 * rules of the {@link com.endurancetrio.data.model.entity.Event event}.
 * <p>
 * The constant {@link #MAP} must be used for
 * {@link com.endurancetrio.data.model.entity.EventFile files} that contains maps or course maps of
 * the {@link com.endurancetrio.data.model.entity.Event event}.
 * <p>
 * The constant {@link #COVER} must be used for
 * {@link com.endurancetrio.data.model.entity.EventFile file} that contains the cover image of the
 * {@link com.endurancetrio.data.model.entity.Event event}.
 * <p>
 * The constant {@link #POSTER} must be used for
 * {@link com.endurancetrio.data.model.entity.EventFile file} that contains the poster image of the
 * {@link com.endurancetrio.data.model.entity.Event event}.
 */
public enum FileType {

  GUIDE("GUI"),
  RULES("RUL"),
  MAP("MAP"),
  COVER("COV"),
  POSTER("POS");

  private final String code;

  FileType(String code) {
    this.code = code;
  }

  public String getCode() {
    return this.code;
  }
}
