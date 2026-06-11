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
import java.util.List;

/**
 * The {@link EventOverviewDTO} provides a summary of an event, including its basic details,
 * location, organizers, and races.
 *
 * @param id         the unique identifier of the event
 * @param title      the title of the event
 * @param startDate  the start date of the event
 * @param endDate    the end date of the event
 * @param city       the city where the event takes place
 * @param county     the county where the event takes place
 * @param district   the district where the event takes place
 * @param organizers the organizers of the event
 * @param races      the races associated with the event
 * @param files      the files associated with the event
 */
public record EventOverviewDTO(Long id, String title, LocalDate startDate, LocalDate endDate,
                               String city, String county, String district,
                               List<OrganizerDTO> organizers, List<RaceDTO> races,
                               List<EventFileDTO> files) {

  public EventOverviewDTO {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException("title must not be null or blank");
    }
    if (startDate == null) {
      throw new IllegalArgumentException("startDate must not be null");
    }
    if (endDate == null) {
      throw new IllegalArgumentException("endDate must not be null");
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
    if (organizers == null) {
      throw new IllegalArgumentException("organizers must not be null");
    }
    if (races == null) {
      throw new IllegalArgumentException("races must not be null");
    }
    if (files == null) {
      throw new IllegalArgumentException("files must not be null");
    }
  }
}
