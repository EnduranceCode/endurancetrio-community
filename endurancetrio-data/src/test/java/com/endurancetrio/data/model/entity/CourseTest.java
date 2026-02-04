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

package com.endurancetrio.data.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.endurancetrio.data.model.enumerator.DistanceType;
import com.endurancetrio.data.model.enumerator.GenderCategory;
import com.endurancetrio.data.model.enumerator.RaceStatus;
import com.endurancetrio.data.model.enumerator.RaceType;
import com.endurancetrio.data.model.enumerator.Sport;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link Course} entity.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class CourseTest {

  private Course underTest;

  private Event testEvent;
  private DuathlonDistance testDistance;
  private Race testRace;

  @BeforeEach
  void setUp() {
    testEvent = new Event();
    testEvent.setId(1L);

    testDistance = new DuathlonDistance();
    testDistance.setId(1L);
    testDistance.setDistanceType(DistanceType.YOUTH);
    testDistance.setFirstRunDistance(320);
    testDistance.setFirstRunLaps(1);
    testDistance.setBikeDistance(900);
    testDistance.setBikeLaps(1);
    testDistance.setSecondRunDistance(180);
    testDistance.setSecondRunLaps(1);

    AgeGroup testAgeGroup = new AgeGroup();
    testAgeGroup.setId(1L);
    testAgeGroup.setTitle("Benjamins");
    testAgeGroup.setShortTitle("BEN");

    ResultsFile testResultsFile = new ResultsFile();
    testResultsFile.setId(1L);

    testRace = new Race();
    testRace.setId(1L);
    testRace.setRaceReference("20100307FTP001-003");
    testRace.setRaceType(RaceType.INDIVIDUAL_DERIVED);
    testRace.setParentRaces(new HashSet<>());
    testRace.setTitle("XVI Duatlo Jovem de Grândola");
    testRace.setSubtitle("Benjamins Masculinos");
    testRace.setGenderCategory(GenderCategory.MALE);
    testRace.setAgeGroup(testAgeGroup);
    testRace.setDate(LocalDate.parse("2010-03-06"));
    testRace.setTime(LocalTime.parse("14:30:00"));
    testRace.setRaceStatus(RaceStatus.COMPLETED);
    testRace.setGunTime(LocalTime.parse("14:33:30"));
    testRace.setAirTemperature(18.0);
    testRace.setResultsFiles(Set.of(testResultsFile));

    underTest = new Course();
    underTest.setId(1L);
    underTest.setEvent(testEvent);
    underTest.setTitle("Duatlo Benjamins");
    underTest.setSport(Sport.DUATHLON);
    underTest.setDistance(testDistance);
    underTest.addRace(testRace);
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(1L, underTest.getId());
    assertEquals(testEvent, underTest.getEvent());
    assertEquals("Duatlo Benjamins", underTest.getTitle());
    assertEquals(Sport.DUATHLON, underTest.getSport());
    assertEquals(testDistance, underTest.getDistance());
    assertEquals(testRace, underTest.getRaces().iterator().next());
  }
}
