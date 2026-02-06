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
import com.endurancetrio.data.event.model.entity.Organizer;

/**
 * The {@link OrganizerType} enum is used to classify an {@link Event}'s {@link Organizer}.
 * <p>
 * It includes the following constants:
 * <ul>
 *   <li>{@link #CLUB} : used for {@link Organizer organizers} that are a sport club.</li>
 *   <li>{@link #PRIVATE} : used for private {@link Organizer organizers}.</li>
 *   <li>{@link #PUBLIC} : used for public or governmental {@link Organizer organizers}.</li>
 * </ul>
 */
public enum OrganizerType {
  CLUB("CLUB"),
  PRIVATE("PRIVATE"),
  PUBLIC("PUBLIC");

  final String code;

  OrganizerType(String code) {
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
