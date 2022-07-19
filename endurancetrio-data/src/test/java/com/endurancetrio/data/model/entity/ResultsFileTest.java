/*
 * Copyright (c) 2011-2022 Ricardo do Canto
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
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.endurancetrio.data.model.enumerator.OrganizerType;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ResultsFileTest {

  ResultsFile testResultsFile;

  Venue testVenue;
  Organizer testOrganizer;
  Event testEvent;

  @BeforeEach
  void setUp() {
    testVenue = new Venue();
    testVenue.setId(1L);
    testVenue.setDistrict("Setúbal");
    testVenue.setCounty("Grândola");
    testVenue.setCity("Grândola");
    testVenue.setTitle("Complexo Desportivo Municipal José Afonso");

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
    testEvent.setStartDate(LocalDate.parse("2010-03-07"));
    testEvent.setEndDate(LocalDate.parse("2010-03-08"));
    testEvent.setVenue(testVenue);
    testEvent.setOrganizers(organizers);

    testResultsFile = new ResultsFile();
    testResultsFile.setId(1L);
    testResultsFile.setEvent(testEvent);
    testResultsFile.setRaceTitle("XVI Duatlo Jovem de Grândola [Benjamins]");
    testResultsFile.setCompetitionTitle("Absolutos Masculinos");
    testResultsFile.setFileName("20100307FTP001-001-01.pdf");
    testResultsFile.setRevisionNumber(1);
    testResultsFile.setActive(true);
  }

  @Test
  void getId() {
    assertEquals(1L, testResultsFile.getId());
  }

  @Test
  void getEvent() {
    assertNotNull(testResultsFile.getEvent());
    assertEquals(1L, testResultsFile.getEvent().getId());
  }

  @Test
  void getRaceTitle() {
    assertEquals("XVI Duatlo Jovem de Grândola [Benjamins]", testResultsFile.getRaceTitle());
  }

  @Test
  void getCompetitionTitle() {
    assertEquals("Absolutos Masculinos", testResultsFile.getCompetitionTitle());
  }

  @Test
  void getFileName() {
    assertEquals("20100307FTP001-001-01.pdf", testResultsFile.getFileName());
  }

  @Test
  void getRevisionNumber() {
    assertEquals(1, testResultsFile.getRevisionNumber());
  }

  @Test
  void getActive() {
    assertTrue(testResultsFile.getActive());
  }
}