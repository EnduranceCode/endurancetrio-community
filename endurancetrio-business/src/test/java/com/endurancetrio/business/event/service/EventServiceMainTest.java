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

package com.endurancetrio.business.event.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.endurancetrio.business.event.dto.EventsPageDTO;
import com.endurancetrio.data.event.model.entity.Course;
import com.endurancetrio.data.event.model.entity.Event;
import com.endurancetrio.data.event.model.enumerator.Sport;
import com.endurancetrio.data.event.repository.EventRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class EventServiceMainTest {

  private static final List<Integer> ALL_YEARS = List.of(1989, 1988, 1987, 1986, 1985, 1984);
  private static final int YEAR = 1984;
  private static final Pageable PAGEABLE = PageRequest.of(0, 10);

  @Mock
  EventRepository eventRepository;

  EventServiceMain eventService;

  @BeforeEach
  void setUp() {
    eventService = new EventServiceMain(eventRepository);
  }

  @Test
  void getEventYearsShouldReturnAllYears() {
    when(eventRepository.findDistinctYears()).thenReturn(ALL_YEARS);

    List<Integer> result = eventService.getEventYears();

    assertNotNull(result);
    assertEquals(ALL_YEARS, result);
  }

  @Test
  void getEventYearsShouldReturnEmptyWhenNoYears() {
    when(eventRepository.findDistinctYears()).thenReturn(List.of());

    List<Integer> result = eventService.getEventYears();

    assertNotNull(result);
    assertEquals(List.of(), result);
  }

  @Test
  void getEventsByYearShouldReturnEventsWithSportCodes() {
    Event event1 = createEvent(1L, "Event 1", LocalDate.of(1984, 8, 15), LocalDate.of(1984, 8, 15),
        "City1", "County1", "District1",
        Set.of(createCourse(Sport.TRIATHLON), createCourse(Sport.ROAD_RUNNING))
    );
    Event event2 = createEvent(2L, "Event 2", LocalDate.of(1984, 8, 1), LocalDate.of(1984, 8, 1),
        "City2", "County2", "District2", Set.of(createCourse(Sport.DUATHLON))
    );

    Page<Event> eventPage = new PageImpl<>(List.of(event1, event2), PAGEABLE, 2L);
    when(eventRepository.findByEventYear(YEAR, PAGEABLE)).thenReturn(eventPage);

    EventsPageDTO result = eventService.getEventsByYear(YEAR, PAGEABLE);

    assertNotNull(result);
    assertEquals(2, result.events().size());
    assertEquals(1L, result.events().get(0).id());
    assertEquals(List.of("ROAD_RUNNING", "TRIATHLON"), result.events().get(0).sportCodes());
    assertEquals(2L, result.events().get(1).id());
    assertEquals(List.of("DUATHLON"), result.events().get(1).sportCodes());
    assertEquals(0, result.pagination().pageNumber());
    assertEquals(1, result.pagination().totalPages());
    assertEquals(2L, result.pagination().totalItems());
  }

  @Test
  void getEventsByYearShouldReturnDistinctSortedSportCodes() {
    Event event = createEvent(1L, "Event", LocalDate.of(1984, 6, 1), LocalDate.of(1984, 8, 15),
        "City", "County", "District",
        Set.of(createCourse(Sport.TRIATHLON), createCourse(Sport.TRIATHLON),
            createCourse(Sport.ROAD_RUNNING)
        )
    );

    Page<Event> eventPage = new PageImpl<>(List.of(event), PAGEABLE, 1L);
    when(eventRepository.findByEventYear(YEAR, PAGEABLE)).thenReturn(eventPage);

    EventsPageDTO result = eventService.getEventsByYear(YEAR, PAGEABLE);

    assertNotNull(result);
    assertEquals(1, result.events().size());
    assertEquals(List.of("ROAD_RUNNING", "TRIATHLON"), result.events().getFirst().sportCodes());
  }

  @Test
  void getEventsByYearShouldReturnEmptyPage() {
    Page<Event> eventPage = new PageImpl<>(List.of(), PAGEABLE, 0L);
    when(eventRepository.findByEventYear(YEAR, PAGEABLE)).thenReturn(eventPage);

    EventsPageDTO result = eventService.getEventsByYear(YEAR, PAGEABLE);

    assertNotNull(result);
    assertEquals(0, result.events().size());
    assertEquals(0, result.pagination().totalItems());
  }

  @Test
  void getEventsByYearShouldReturnEmptySportCodes() {
    Event event = createEvent(1L, "Event", LocalDate.of(1984, 6, 1), LocalDate.of(1984, 8, 15),
        "City", "County", "District", Set.of()
    );

    Page<Event> eventPage = new PageImpl<>(List.of(event), PAGEABLE, 1L);
    when(eventRepository.findByEventYear(YEAR, PAGEABLE)).thenReturn(eventPage);

    EventsPageDTO result = eventService.getEventsByYear(YEAR, PAGEABLE);

    assertNotNull(result);
    assertEquals(1, result.events().size());
    assertEquals(List.of(), result.events().getFirst().sportCodes());
  }

  private Event createEvent(
      Long id, String title, LocalDate startDate, LocalDate endDate,
      String city, String county, String district, Set<Course> courses
  ) {
    Event event = new Event();
    event.setId(id);
    event.setTitle(title);
    event.setStartDate(startDate);
    event.setEndDate(endDate);
    event.setCity(city);
    event.setCounty(county);
    event.setDistrict(district);
    event.setCourses(courses);
    return event;
  }

  private Course createCourse(Sport sport) {
    Course course = new Course();
    course.setSport(sport);
    return course;
  }
}
