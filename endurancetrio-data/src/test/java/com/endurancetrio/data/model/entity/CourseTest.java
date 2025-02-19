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

import com.endurancetrio.data.model.enumerator.DistanceType;
import com.endurancetrio.data.model.enumerator.Sport;
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
    testDistance.setType(DistanceType.YOUTH);
    testDistance.setFirstRunDistance(320);
    testDistance.setFirstRunLaps(1);
    testDistance.setBikeDistance(900);
    testDistance.setBikeLaps(1);
    testDistance.setSecondRunDistance(180);
    testDistance.setSecondRunLaps(1);

    testRace = new Race();
    testRace.setId(1L);

    underTest = new Course();
    underTest.setId(1L);
    underTest.setEvent(testEvent);
    underTest.setTitle("Duatlo Benjamins");
    underTest.setSport(Sport.DUATHLON);
    underTest.setDistance(testDistance);
    underTest.setRaces(Set.of(testRace));
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
