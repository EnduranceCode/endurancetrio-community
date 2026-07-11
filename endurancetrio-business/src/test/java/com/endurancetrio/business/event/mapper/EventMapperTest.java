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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.endurancetrio.business.event.dto.EventDTO;
import com.endurancetrio.business.event.dto.EventFileDTO;
import com.endurancetrio.business.event.dto.EventOverviewDTO;
import com.endurancetrio.business.event.dto.OrganizerDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.data.event.model.entity.Course;
import com.endurancetrio.data.event.model.entity.Event;
import com.endurancetrio.data.event.model.entity.EventFile;
import com.endurancetrio.data.event.model.entity.Organizer;
import com.endurancetrio.data.event.model.entity.Race;
import com.endurancetrio.data.event.model.enumerator.FileType;
import com.endurancetrio.data.event.model.enumerator.RaceType;
import com.endurancetrio.data.event.model.enumerator.Sport;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventMapperTest {

  private static final Long EVENT_ID = 1L;
  private static final String TITLE = "Test Event";
  private static final LocalDate START_DATE = LocalDate.of(1984, Month.AUGUST, 15);
  private static final LocalDate END_DATE = LocalDate.of(1984, Month.AUGUST, 15);
  private static final String CITY = "Lisbon";
  private static final String COUNTY = "Lisbon";
  private static final String DISTRICT = "Lisbon";

  private Event entityTest;
  private OrganizerDTO organizerDTO;
  private RaceDTO raceDTO;
  private EventFileDTO eventFileDTO;

  @Mock
  private OrganizerMapper organizerMapper;

  @Mock
  private RaceMapper raceMapper;

  @Mock
  private EventFileMapper eventFileMapper;

  @InjectMocks
  private EventMapper underTest;

  @BeforeEach
  void setUp() {
    entityTest = new Event();
    entityTest.setId(EVENT_ID);
    entityTest.setTitle(TITLE);
    entityTest.setStartDate(START_DATE);
    entityTest.setEndDate(END_DATE);
    entityTest.setCity(CITY);
    entityTest.setCounty(COUNTY);
    entityTest.setDistrict(DISTRICT);

    Organizer organizer = new Organizer();
    organizer.setId(1L);
    organizer.setName("Test Club");
    entityTest.setOrganizers(Set.of(organizer));

    organizerDTO = new OrganizerDTO(1L, "Test Club", "CLUB", "Lisbon", "Lisbon", "Lisbon");

    Course course = new Course();
    Race race = new Race();
    race.setId(1L);
    course.addRace(race);
    entityTest.setCourses(Set.of(course));

    raceDTO = new RaceDTO(1L, "Test Race", "Subtitle", START_DATE, null, List.of(), List.of(),
        RaceType.INDIVIDUAL_PARENT, "INDIVIDUAL", null, null, "UNKNOWN"
    );

    EventFile eventFile = new EventFile();
    eventFile.setTitle("Regulamento");
    eventFile.setFileType(FileType.RULES);
    eventFile.setFileName("19840815NAC001-REG001-01.pdf");
    entityTest.setEventFiles(Set.of(eventFile));

    eventFileDTO = new EventFileDTO(1L, "Regulamento", "RULES", "19840815NAC001-REG001-01.pdf");
  }

  @Test
  void mapToEventDTOShouldMapNullToNull() {
    assertNull(underTest.mapToEventDTO(null));
  }

  @Test
  void mapToEventDTOShouldMapEntityWithCourses() {
    Course triathlon = new Course();
    triathlon.setSport(Sport.TRIATHLON);
    Course roadRunning = new Course();
    roadRunning.setSport(Sport.ROAD_RUNNING);

    entityTest.setCourses(Set.of(triathlon, roadRunning));

    EventDTO result = underTest.mapToEventDTO(entityTest);

    assertNotNull(result);
    assertEquals(EVENT_ID, result.id());
    assertEquals(TITLE, result.title());
    assertEquals(START_DATE, result.startDate());
    assertEquals(END_DATE, result.endDate());
    assertEquals(CITY, result.city());
    assertEquals(COUNTY, result.county());
    assertEquals(DISTRICT, result.district());
    assertEquals(List.of("ROAD_RUNNING", "TRIATHLON"), result.sportCodes());
  }

  @Test
  void mapToEventDTOShouldReturnDistinctSortedSportCodes() {
    Course triathlon1 = new Course();
    triathlon1.setSport(Sport.TRIATHLON);
    Course triathlon2 = new Course();
    triathlon2.setSport(Sport.TRIATHLON);
    Course roadRunning = new Course();
    roadRunning.setSport(Sport.ROAD_RUNNING);

    entityTest.setCourses(Set.of(triathlon1, triathlon2, roadRunning));

    EventDTO result = underTest.mapToEventDTO(entityTest);

    assertNotNull(result);
    assertEquals(List.of("ROAD_RUNNING", "TRIATHLON"), result.sportCodes());
  }

  @Test
  void mapToEventDTOShouldReturnEmptySportCodes() {
    entityTest.setCourses(Set.of());

    EventDTO result = underTest.mapToEventDTO(entityTest);

    assertNotNull(result);
    assertEquals(List.of(), result.sportCodes());
  }

  @Test
  void mapToEventOverviewDTO() {
    Organizer organizer = entityTest.getOrganizers().iterator().next();
    Race race = entityTest.getCourses().iterator().next().getRaces().iterator().next();
    EventFile eventFile = entityTest.getEventFiles().iterator().next();

    when(organizerMapper.map(organizer)).thenReturn(organizerDTO);
    when(raceMapper.map(race)).thenReturn(raceDTO);
    when(eventFileMapper.map(eventFile)).thenReturn(eventFileDTO);

    EventOverviewDTO result = underTest.mapToEventOverviewDTO(entityTest);

    verify(organizerMapper, times(1)).map(organizer);
    verify(raceMapper, times(1)).map(race);
    verify(eventFileMapper, times(1)).map(eventFile);

    assertNotNull(result);
    assertEquals(EVENT_ID, result.id());
    assertEquals(TITLE, result.title());
    assertEquals(START_DATE, result.startDate());
    assertEquals(END_DATE, result.endDate());
    assertEquals(CITY, result.city());
    assertEquals(COUNTY, result.county());
    assertEquals(DISTRICT, result.district());
    assertEquals(1, result.organizers().size());
    assertEquals(organizerDTO, result.organizers().getFirst());
    assertEquals(1, result.races().size());
    assertEquals(raceDTO, result.races().getFirst());
    assertEquals(1, result.files().size());
    assertEquals(eventFileDTO, result.files().getFirst());
  }

  @Test
  void mapNullEntity() {
    EventOverviewDTO result = underTest.mapToEventOverviewDTO(null);

    assertNull(result);
  }
}
