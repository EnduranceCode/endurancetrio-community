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
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.data.event.model.entity.Course;
import com.endurancetrio.data.event.model.entity.Event;
import com.endurancetrio.data.event.model.entity.Race;
import com.endurancetrio.data.event.model.entity.SingleSportDistance;
import com.endurancetrio.data.event.model.entity.TriathlonDistance;
import com.endurancetrio.data.event.model.enumerator.DistanceType;
import com.endurancetrio.data.event.model.enumerator.RaceType;
import com.endurancetrio.data.event.model.enumerator.Sport;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RaceMapperTest {

  private static final Long ID = 1L;
  private static final String TITLE = "Individual National Championship";
  private static final String SUBTITLE = "Elite Men";
  private static final LocalDate DATE = LocalDate.of(1984, Month.AUGUST, 15);
  private static final LocalTime TIME = LocalTime.of(16, 0);

  private static final Long EVENT_ID = 1L;
  private static final String EVENT_TITLE = "Test Event";
  private static final LocalDate START_DATE = DATE;
  private static final LocalDate END_DATE = DATE;
  private static final String CITY = "Lisbon";
  private static final String COUNTY = "Lisbon";
  private static final String DISTRICT = "Lisbon";

  private Race entityTest;
  private Course course;

  private RaceMapper underTest;

  @BeforeEach
  void setUp() {
    underTest = new RaceMapper();

    Event event = new Event();
    event.setId(EVENT_ID);
    event.setTitle(EVENT_TITLE);
    event.setStartDate(START_DATE);
    event.setEndDate(END_DATE);
    event.setCity(CITY);
    event.setCounty(COUNTY);
    event.setDistrict(DISTRICT);

    course = new Course();
    course.setSport(Sport.TRIATHLON);
    course.setEvent(event);

    entityTest = new Race();
    entityTest.setId(ID);
    entityTest.setTitle(TITLE);
    entityTest.setSubtitle(SUBTITLE);
    entityTest.setDate(DATE);
    entityTest.setTime(TIME);
    entityTest.setRaceType(RaceType.INDIVIDUAL_PARENT);
    entityTest.addCourse(course);
  }

  @Test
  void mapEntityWithSingleSportDistance() {
    SingleSportDistance distance = new SingleSportDistance();
    distance.setDistanceType(DistanceType.SPRINT);
    distance.setDistance(7500);
    distance.setLaps(1);
    course.setDistance(distance);

    RaceDTO result = underTest.map(entityTest);

    assertNotNull(result);
    assertEquals(ID, result.id());
    assertEquals(TITLE, result.title());
    assertEquals(SUBTITLE, result.subtitle());
    assertEquals(DATE, result.date());
    assertEquals(TIME, result.time());
    assertEquals(1, result.sports().size());
    assertTrue(result.sports().contains(Sport.TRIATHLON.getCode()));
    assertEquals(1, result.distanceTypes().size());
    assertTrue(result.distanceTypes().contains(DistanceType.SPRINT.getCode()));
    assertEquals("INDIVIDUAL", result.raceTypeGroup());
    assertNotNull(result.distanceMetadata());
    assertEquals(1, result.distanceMetadata().legs().size());
    assertNull(result.distanceMetadata().legs().getFirst().coreSport());
    assertEquals(7500, result.distanceMetadata().legs().getFirst().length());
  }

  @Test
  void mapEntityWithTriathlonDistance() {
    TriathlonDistance distance = new TriathlonDistance();
    distance.setDistanceType(DistanceType.STANDARD);
    distance.setSwimDistance(1500);
    distance.setSwimLaps(1);
    distance.setBikeDistance(40000);
    distance.setBikeLaps(2);
    distance.setRunDistance(10000);
    distance.setRunLaps(1);
    course.setDistance(distance);

    RaceDTO result = underTest.map(entityTest);

    assertNotNull(result);
    assertEquals(ID, result.id());
    assertEquals("INDIVIDUAL", result.raceTypeGroup());
    assertNotNull(result.distanceMetadata());
    assertEquals(3, result.distanceMetadata().legs().size());
    assertEquals("SWIM", result.distanceMetadata().legs().get(0).coreSport());
    assertEquals(1500, result.distanceMetadata().legs().get(0).length());
    assertEquals("BIKE", result.distanceMetadata().legs().get(1).coreSport());
    assertEquals(40000, result.distanceMetadata().legs().get(1).length());
    assertEquals("RUN", result.distanceMetadata().legs().get(2).coreSport());
    assertEquals(10000, result.distanceMetadata().legs().get(2).length());
  }

  @Test
  void mapTeamRaceType() {
    entityTest.setRaceType(RaceType.TEAM_BY_RANK);

    RaceDTO result = underTest.map(entityTest);

    assertNotNull(result);
    assertEquals("COLLECTIVE", result.raceTypeGroup());
  }

  @Test
  void mapRelayRaceType() {
    entityTest.setRaceType(RaceType.TEAM_RELAY_PARENT);

    RaceDTO result = underTest.map(entityTest);

    assertNotNull(result);
    assertEquals("RELAY", result.raceTypeGroup());
  }

  @Test
  void mapEntityWithNoCourses() {
    entityTest.setCourses(Set.of());

    RaceDTO result = underTest.map(entityTest);

    assertNotNull(result);
    assertTrue(result.sports().isEmpty());
    assertTrue(result.distanceTypes().isEmpty());
    assertNull(result.distanceMetadata());
  }

  @Test
  void mapNullEntity() {
    RaceDTO result = underTest.map((Race) null);

    assertNull(result);
  }

  @Test
  void mapWithEventEntityWithTriathlonDistance() {
    TriathlonDistance distance = new TriathlonDistance();
    distance.setDistanceType(DistanceType.STANDARD);
    distance.setSwimDistance(1500);
    distance.setSwimLaps(1);
    distance.setBikeDistance(40000);
    distance.setBikeLaps(2);
    distance.setRunDistance(10000);
    distance.setRunLaps(1);
    course.setDistance(distance);

    RaceDTO result = underTest.mapWithEvent(entityTest);

    assertNotNull(result);
    assertEquals(ID, result.id());
    assertEquals("INDIVIDUAL", result.raceTypeGroup());
    assertNotNull(result.distanceMetadata());
    assertEquals(3, result.distanceMetadata().legs().size());
    assertEquals("SWIM", result.distanceMetadata().legs().get(0).coreSport());
    assertEquals(1500, result.distanceMetadata().legs().get(0).length());
    assertEquals("BIKE", result.distanceMetadata().legs().get(1).coreSport());
    assertEquals(40000, result.distanceMetadata().legs().get(1).length());
    assertEquals("RUN", result.distanceMetadata().legs().get(2).coreSport());
    assertEquals(10000, result.distanceMetadata().legs().get(2).length());
    assertEquals(EVENT_ID, result.event().id());
    assertEquals(EVENT_TITLE, result.event().title());
    assertEquals(START_DATE, result.event().startDate());
    assertEquals(END_DATE, result.event().endDate());
    assertEquals(CITY, result.event().city());
    assertEquals(COUNTY, result.event().county());
    assertEquals(DISTRICT, result.event().district());
  }

  @Test
  void mapWithEventNullEntity() {
    RaceDTO result = underTest.mapWithEvent((Race) null);

    assertNull(result);
  }
}
