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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.endurancetrio.data.model.enumerator.DistanceType;
import com.endurancetrio.data.model.enumerator.FileType;
import com.endurancetrio.data.model.enumerator.OrganizerType;
import com.endurancetrio.data.model.enumerator.Sport;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link Event} entity.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is
 * to establish a testing culture from the very beginning of the project. It serves as a reminder
 * that every part of the application should be testable and that tests should always be present.
 */
class EventTest {

  Event underTest;

  Organizer firstTestOrganizer;
  Organizer secondTestOrganizer;
  Organizer thirdTestOrganizer;
  EventFile testEventFile;
  DuathlonDistance testDistance;
  Course testCourse;

  @BeforeEach
  void setUp() {
    firstTestOrganizer = new Organizer();
    firstTestOrganizer.setId(1L);
    firstTestOrganizer.setName("Câmara Municipal de Grândola");
    firstTestOrganizer.setDistrict("Setúbal");
    firstTestOrganizer.setCounty("Grândola");
    firstTestOrganizer.setCity("Grândola");
    firstTestOrganizer.setOrganizerType(OrganizerType.PUBLIC);

    secondTestOrganizer = new Organizer();
    secondTestOrganizer.setId(2L);
    secondTestOrganizer.setName("Amiciclo");
    secondTestOrganizer.setDistrict("Setúbal");
    secondTestOrganizer.setCounty("Grândola");
    secondTestOrganizer.setCity("Grândola");
    secondTestOrganizer.setOrganizerType(OrganizerType.CLUB);

    thirdTestOrganizer = new Organizer();
    thirdTestOrganizer.setId(3L);
    thirdTestOrganizer.setName("Federação de Triatlo de Portugal");
    thirdTestOrganizer.setDistrict("Lisboa");
    thirdTestOrganizer.setCounty("Oeiras");
    thirdTestOrganizer.setCity("Caxias");
    thirdTestOrganizer.setOrganizerType(OrganizerType.PUBLIC);

    Set<Organizer> organizers = Set.of(firstTestOrganizer, secondTestOrganizer, thirdTestOrganizer);

    testEventFile = new EventFile();
    testEventFile.setId(1L);
    testEventFile.setFileType(FileType.RULES);
    testEventFile.setTitle("Regulamento");
    testEventFile.setFileName("20100307FTP001-REG001.pdf");
    testEventFile.setRevision(1);
    testEventFile.setActive(true);

    testDistance = new DuathlonDistance();
    testDistance.setId(1L);
    testDistance.setDistanceType(DistanceType.YOUTH);
    testDistance.setFirstRunDistance(320);
    testDistance.setFirstRunLaps(1);
    testDistance.setBikeDistance(900);
    testDistance.setBikeLaps(1);
    testDistance.setSecondRunDistance(180);
    testDistance.setSecondRunLaps(1);

    testCourse = new Course();
    testCourse.setId(1L);
    testCourse.setTitle("Duatlo Benjamins");
    testCourse.setSport(Sport.DUATHLON);
    testCourse.setDistance(testDistance);

    underTest = new Event();
    underTest.setId(1L);
    underTest.setEventReference("20100307FTP001");
    underTest.setTitle("XVI Duatlo Jovem de Grândola");
    underTest.setStartDate(LocalDate.parse("2010-03-06"));
    underTest.setEndDate(LocalDate.parse("2010-03-07"));
    underTest.setDistrict("Setúbal");
    underTest.setCounty("Grândola");
    underTest.setCity("Grândola");
    underTest.setOrganizers(organizers);
    underTest.getEventFiles().add(testEventFile);
    underTest.addCourse(testCourse);
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(1L, underTest.getId());
    assertEquals("20100307FTP001", underTest.getEventReference());
    assertEquals("XVI Duatlo Jovem de Grândola", underTest.getTitle());
    assertEquals(LocalDate.parse("2010-03-06"), underTest.getStartDate());
    assertEquals(LocalDate.parse("2010-03-07"), underTest.getEndDate());
    assertEquals("Setúbal", underTest.getDistrict());
    assertEquals("Grândola", underTest.getCounty());
    assertEquals("Grândola", underTest.getCity());
    assertNotNull(underTest.getOrganizers());
    assertFalse(underTest.getOrganizers().isEmpty());
    assertTrue(underTest.getOrganizers().contains(firstTestOrganizer));
    assertTrue(underTest.getOrganizers().contains(secondTestOrganizer));
    assertTrue(underTest.getOrganizers().contains(thirdTestOrganizer));
    assertEquals(3, underTest.getOrganizers().size());
    assertNotNull(underTest.getEventFiles());
    assertFalse(underTest.getEventFiles().isEmpty());
    assertEquals(1, underTest.getEventFiles().size());
    assertNotNull(underTest.getCourses());
    assertEquals(1, underTest.getCourses().size());
  }
}
