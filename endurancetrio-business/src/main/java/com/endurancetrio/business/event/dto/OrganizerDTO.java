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

package com.endurancetrio.business.event.dto;

/**
 * The {@link OrganizerDTO} represents an organizer of an event.
 *
 * @param id            the unique identifier of the organizer
 * @param name          the name of the organizer
 * @param organizerType the type of the organizer (e.g. CLUB, PRIVATE, PUBLIC)
 * @param city          the city of the organizer's headquarters
 * @param county        the county of the organizer's headquarters
 * @param district      the district of the organizer's headquarters
 */
public record OrganizerDTO(Long id, String name, String organizerType, String city, String county,
                           String district) {

  public OrganizerDTO {
    if (name == null || name.isBlank()) {
      throw new IllegalArgumentException("name must not be null or blank");
    }
    if (organizerType == null || organizerType.isBlank()) {
      throw new IllegalArgumentException("organizerType must not be null or blank");
    }
    if (city == null || city.isBlank()) {
      throw new IllegalArgumentException("city must not be null or blank");
    }
    if (county == null || county.isBlank()) {
      throw new IllegalArgumentException("county must not be null or blank");
    }
    if (district == null || district.isBlank()) {
      throw new IllegalArgumentException("district must not be null or blank");
    }
  }
}
