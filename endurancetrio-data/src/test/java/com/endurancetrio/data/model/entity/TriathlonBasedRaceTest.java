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
import static org.junit.jupiter.api.Assertions.assertNull;

import com.endurancetrio.data.model.enumerator.GenderCategory;
import com.endurancetrio.data.model.enumerator.RaceStatus;
import com.endurancetrio.data.model.enumerator.RaceType;
import com.endurancetrio.data.model.enumerator.WetsuitRule;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link TriathlonBasedRaceTest} entity.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class TriathlonBasedRaceTest {

  private TriathlonBasedRace underTest;

  private Course testCourse;
  private AgeGroup testAgeGroup;
  private ResultsFile testResultsFile;

  @BeforeEach
  void setUp() {
    testCourse = new Course();
    testCourse.setId(1L);

    testAgeGroup = new AgeGroup();
    testAgeGroup.setId(1L);
    testAgeGroup.setTitle("Geral");
    testAgeGroup.setShortTitle("OVERALL");

    testResultsFile = new ResultsFile();
    testResultsFile.setId(1L);

    underTest = new TriathlonBasedRace();
    underTest.setId(1L);
    underTest.setRaceReference("19840815NAC001-001");
    underTest.setCourses(Set.of(testCourse));
    underTest.setRaceType(RaceType.INDIVIDUAL_PARENT);
    underTest.setTitle("Triatlo de Peniche");
    underTest.setSubtitle("Geral");
    underTest.setGenderCategory(GenderCategory.OPEN);
    underTest.setAgeGroup(testAgeGroup);
    underTest.setDate(LocalDate.parse("1984-08-15"));
    underTest.setTime(LocalTime.parse("16:00:00"));
    underTest.setRaceStatus(RaceStatus.COMPLETED);
    underTest.setAirTemperature(25.0);
    underTest.setResultsFiles(Set.of(testResultsFile));
    underTest.setWaterTemperature(20.0);
    underTest.setWetsuitRule(WetsuitRule.OPTIONAL);
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(1L, underTest.getId());
    assertEquals("19840815NAC001-001", underTest.getRaceReference());
    assertNotNull(underTest.getCourses());
    assertEquals(1, underTest.getCourses().size());
    assertEquals(testCourse, underTest.getCourses().iterator().next());
    assertNotNull(underTest.getParentRaces());
    assertEquals(0, underTest.getParentRaces().size());
    assertEquals("Triatlo de Peniche", underTest.getTitle());
    assertEquals("Geral", underTest.getSubtitle());
    assertEquals(GenderCategory.OPEN, underTest.getGenderCategory());
    assertNotNull(underTest.getAgeGroup());
    assertEquals(testAgeGroup.getShortTitle(), underTest.getAgeGroup().getShortTitle());
    assertEquals(LocalDate.parse("1984-08-15"), underTest.getDate());
    assertEquals(LocalTime.parse("16:00:00"), underTest.getTime());
    assertEquals(RaceStatus.COMPLETED, underTest.getRaceStatus());
    assertNull(underTest.getGunTime());
    assertEquals(25.0, underTest.getAirTemperature());
    assertNotNull(underTest.getResultsFiles());
    assertEquals(1, underTest.getResultsFiles().size());
    assertEquals(testResultsFile, underTest.getResultsFiles().iterator().next());
    assertEquals(20.0, underTest.getWaterTemperature());
    assertEquals(WetsuitRule.OPTIONAL, underTest.getWetsuitRule());
  }
}
