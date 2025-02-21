/*
 * Copyright (c) 2011-2025 Ricardo do Canto
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
package com.endurancetrio.data.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.endurancetrio.data.model.enumerator.GenderCategory;
import com.endurancetrio.data.model.enumerator.RaceStatus;
import com.endurancetrio.data.model.enumerator.RaceType;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link Race} entity.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class RaceTest {

  private Race underTest;

  private Course testCourse;
  private AgeGroup testAgeGroup;
  private ResultsFile testResultsFile;

  @BeforeEach
  void setUp() {
    testCourse = new Course();
    testCourse.setId(1L);

    testAgeGroup = new AgeGroup();
    testAgeGroup.setId(1L);
    testAgeGroup.setTitle("Benjamins");
    testAgeGroup.setShortTitle("BEN");

    ResultsFile testParentResultFile = new ResultsFile();
    testParentResultFile.setId(1L);
    testResultsFile = new ResultsFile();
    testResultsFile.setId(2L);

    Race testParentRace = new Race();
    testParentRace.setId(1L);
    testParentRace.setRaceReference("20100307FTP001-001");
    testParentRace.addCourse(testCourse);
    testParentRace.setRaceType(RaceType.INDIVIDUAL_PARENT);
    testParentRace.addParentRace(testParentRace);
    testParentRace.setTitle("XVI Duatlo Jovem de Grândola");
    testParentRace.setSubtitle("Benjamins Geral");
    testParentRace.setGenderCategory(GenderCategory.OPEN);
    testParentRace.setAgeGroup(testAgeGroup);
    testParentRace.setDate(LocalDate.parse("2010-03-06"));
    testParentRace.setTime(LocalTime.parse("14:30:00"));
    testParentRace.setRaceStatus(RaceStatus.COMPLETED);
    testParentRace.setGunTime(LocalTime.parse("14:33:30"));
    testParentRace.setAirTemperature(18.0);
    testParentRace.setResultsFiles(Set.of(testParentResultFile));

    underTest = new Race();
    underTest.setId(2L);
    underTest.setRaceReference("20100307FTP001-003");
    underTest.addCourse(testCourse);
    underTest.setRaceType(RaceType.INDIVIDUAL_DERIVED);
    underTest.addParentRace(testParentRace);
    underTest.setTitle("XVI Duatlo Jovem de Grândola");
    underTest.setSubtitle("Benjamins Masculinos");
    underTest.setGenderCategory(GenderCategory.MALE);
    underTest.setAgeGroup(testAgeGroup);
    underTest.setDate(LocalDate.parse("2010-03-06"));
    underTest.setTime(LocalTime.parse("14:30:00"));
    underTest.setRaceStatus(RaceStatus.COMPLETED);
    underTest.setGunTime(LocalTime.parse("14:33:30"));
    underTest.setAirTemperature(18.0);
    underTest.getResultsFiles().add(testResultsFile);
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(2L, underTest.getId());
    assertEquals("20100307FTP001-003", underTest.getRaceReference());
    assertNotNull(underTest.getCourses());
    assertEquals(1, underTest.getCourses().size());
    assertEquals(testCourse, underTest.getCourses().iterator().next());
    assertNotNull(underTest.getParentRaces());
    assertEquals(1, underTest.getParentRaces().size());
    assertEquals("XVI Duatlo Jovem de Grândola", underTest.getTitle());
    assertEquals("Benjamins Masculinos", underTest.getSubtitle());
    assertEquals(GenderCategory.MALE, underTest.getGenderCategory());
    assertNotNull(underTest.getAgeGroup());
    assertEquals(testAgeGroup.getShortTitle(), underTest.getAgeGroup().getShortTitle());
    assertEquals(LocalDate.parse("2010-03-06"), underTest.getDate());
    assertEquals(LocalTime.parse("14:30:00"), underTest.getTime());
    assertEquals(RaceStatus.COMPLETED, underTest.getRaceStatus());
    assertEquals(LocalTime.parse("14:33:30"), underTest.getGunTime());
    assertEquals(18.0, underTest.getAirTemperature());
    assertNotNull(underTest.getResultsFiles());
    assertEquals(1, underTest.getResultsFiles().size());
    assertEquals(testResultsFile, underTest.getResultsFiles().iterator().next());
  }
}
