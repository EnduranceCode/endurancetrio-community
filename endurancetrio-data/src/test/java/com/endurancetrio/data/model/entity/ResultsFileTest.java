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
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.endurancetrio.data.model.enumerator.OrganizerType;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link ResultsFileTest} entity.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is
 * to establish a testing culture from the very beginning of the project. It serves as a reminder
 * that every part of the application should be testable and that tests should always be present.
 */
class ResultsFileTest {

  ResultsFile underTest;

  Organizer testOrganizer;
  Event testEvent;

  @BeforeEach
  void setUp() {
    testOrganizer = new Organizer();
    testOrganizer.setId(1L);
    testOrganizer.setName("Câmara Municipal de Grândola");
    testOrganizer.setDistrict("Setúbal");
    testOrganizer.setCounty("Grândola");
    testOrganizer.setCity("Grândola");
    testOrganizer.setOrganizerType(OrganizerType.PUBLIC);

    Set<Organizer> organizers = new HashSet<>();
    organizers.add(testOrganizer);

    testEvent = new Event();
    testEvent.setId(1L);
    testEvent.setTitle("XVI Duatlo Jovem de Grândola");
    testEvent.setStartDate(LocalDate.parse("2010-03-06"));
    testEvent.setEndDate(LocalDate.parse("2010-03-07"));
    testEvent.setOrganizers(organizers);

    underTest = new ResultsFile();
    underTest.setId(1L);
    underTest.setTitle("XVI Duatlo Jovem de Grândola");
    underTest.setSubtitle("Benjamins Masculinos");
    underTest.setFileName("20100306FTP001-003A-01.pdf");
    underTest.setRevision(1);
    underTest.setActive(true);
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(1L, underTest.getId());
    assertEquals("XVI Duatlo Jovem de Grândola", underTest.getTitle());
    assertEquals("Benjamins Masculinos", underTest.getSubtitle());
    assertEquals("20100306FTP001-003A-01.pdf", underTest.getFileName());
    assertEquals(1, underTest.getRevision());
    assertTrue(underTest.getActive());
  }
}
