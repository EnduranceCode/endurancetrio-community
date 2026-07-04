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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * The {@link RaceDTO} represents a race within an event, including its schedule, sport categories,
 * distance types, and optional distance metadata.
 *
 * @param id               the unique identifier of the race
 * @param title            the title of the race
 * @param subtitle         the subtitle of the race
 * @param date             the date of the race
 * @param time             an optional start time for the race
 * @param sports           the sport codes associated with the race
 * @param distanceTypes    the distance type codes associated with the race
 * @param raceTypeGroup    the race type group code
 * @param distanceMetadata optional metadata describing the distance composition
 * @param event            the event that the race belongs to (may be null)
 * @param resultStatus     the result data availability status code
 */
public record RaceDTO(Long id, String title, String subtitle, LocalDate date, LocalTime time,
                      List<String> sports, List<String> distanceTypes, String raceTypeGroup,
                      DistanceMetadataDTO distanceMetadata, EventDTO event,
                      String resultStatus) {

  public RaceDTO {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException("title must not be null or blank");
    }
    if (subtitle == null || subtitle.isBlank()) {
      throw new IllegalArgumentException("subtitle must not be null or blank");
    }
    if (date == null) {
      throw new IllegalArgumentException("date must not be null");
    }
    if (sports == null) {
      throw new IllegalArgumentException("sports must not be null");
    }
    if (distanceTypes == null) {
      throw new IllegalArgumentException("distanceTypes must not be null");
    }
    if (raceTypeGroup == null || raceTypeGroup.isBlank()) {
      throw new IllegalArgumentException("raceTypeGroup must not be null or blank");
    }
    if (resultStatus == null || resultStatus.isBlank()) {
      throw new IllegalArgumentException("resultStatus must not be null or blank");
    }
  }
}
