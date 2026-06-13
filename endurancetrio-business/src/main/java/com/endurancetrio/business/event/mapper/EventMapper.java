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
 * EVEN IF WE HAVE BEEN INFORMED OF THE POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.business.event.mapper;

import com.endurancetrio.business.event.dto.EventDTO;
import com.endurancetrio.business.event.dto.EventFileDTO;
import com.endurancetrio.business.event.dto.EventOverviewDTO;
import com.endurancetrio.business.event.dto.OrganizerDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.data.event.model.entity.Course;
import com.endurancetrio.data.event.model.entity.Event;
import com.endurancetrio.data.event.model.entity.Race;
import com.endurancetrio.data.event.model.enumerator.Sport;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {

  private final OrganizerMapper organizerMapper;
  private final RaceMapper raceMapper;
  private final EventFileMapper eventFileMapper;

  @Autowired
  public EventMapper(
      OrganizerMapper organizerMapper, RaceMapper raceMapper,
      EventFileMapper eventFileMapper
  ) {
    this.organizerMapper = organizerMapper;
    this.raceMapper = raceMapper;
    this.eventFileMapper = eventFileMapper;
  }

  /**
   * Maps an {@link Event} entity to an {@link EventDTO}.
   *
   * @param entity the Event entity to be mapped
   * @return the corresponding EventDTO, or {@code null} if the entity is {@code null}
   */
  public EventDTO mapToEventDTO(Event entity) {
    if (entity == null) {
      return null;
    }

    List<String> sportCodes = entity.getCourses()
        .stream()
        .map(Course::getSport)
        .filter(Objects::nonNull)
        .map(Sport::getCode)
        .distinct()
        .sorted()
        .toList();

    return new EventDTO(entity.getId(), entity.getTitle(), entity.getStartDate(),
        entity.getEndDate(), entity.getCity(), entity.getCounty(), entity.getDistrict(), sportCodes
    );
  }

  /**
   * Maps an {@link Event} entity to an {@link EventOverviewDTO}.
   *
   * @param entity the Event entity to be mapped
   * @return the corresponding EventOverviewDTO, or {@code null} if the entity is {@code null}
   */
  public EventOverviewDTO mapToEventOverviewDTO(Event entity) {
    if (entity == null) {
      return null;
    }

    List<OrganizerDTO> organizers = entity.getOrganizers()
        .stream()
        .map(organizerMapper::map)
        .filter(Objects::nonNull)
        .sorted(Comparator.comparing(OrganizerDTO::id))
        .toList();

    List<RaceDTO> races = getRaces(entity.getCourses()).stream()
        .map(raceMapper::map)
        .filter(Objects::nonNull)
        .sorted(Comparator.comparing(RaceDTO::id))
        .toList();

    List<EventFileDTO> files = entity.getEventFiles()
        .stream()
        .map(eventFileMapper::map)
        .filter(Objects::nonNull)
        .toList();

    return new EventOverviewDTO(entity.getId(), entity.getTitle(), entity.getStartDate(),
        entity.getEndDate(), entity.getCity(), entity.getCounty(), entity.getDistrict(), organizers,
        races, files
    );
  }

  /**
   * Extracts a distinct list of {@link Race} entities from the given set of
   * {@link Course courses}.
   *
   * @param courses the set of courses to extract races from
   * @return a distinct list of non-null races, or an empty list if courses is null or empty
   */
  private List<Race> getRaces(Set<Course> courses) {
    if (courses == null || courses.isEmpty()) {
      return List.of();
    }

    return courses.stream()
        .flatMap(course -> course.getRaces().stream())
        .filter(Objects::nonNull)
        .distinct()
        .toList();
  }
}
