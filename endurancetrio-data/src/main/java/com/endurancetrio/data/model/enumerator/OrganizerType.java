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
 * The {@link OrganizerType} enum holds the possible type classification of an
 * {@link com.endurancetrio.data.model.entity.Event event}'s
 * {@link com.endurancetrio.data.model.entity.Organizer organizer}.
 * <p>
 * The constant {@link #PUBLIC} must be used for public or governmental
 * {@link com.endurancetrio.data.model.entity.Organizer organizers}.
 * <p>
 * The constant {@link #CLUB} must be used for
 * {@link com.endurancetrio.data.model.entity.Organizer organizers} that are a sport club.
 * <p>
 * The constant {@link #PRIVATE} must be used for private
 * {@link com.endurancetrio.data.model.entity.Organizer organizers}.
 */
public enum OrganizerType {
  PUBLIC("PUBLIC"),
  CLUB("CLUB"),
  PRIVATE("PRIVATE");

  final String code;

  OrganizerType(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
