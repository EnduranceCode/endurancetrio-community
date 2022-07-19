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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.endurancetrio.data.model.enumerator.OrganizerType;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventTest {

  Event testEvent;

  Venue testVenue;
  Organizer firstTestOrganizer;
  Organizer secondTestOrganizer;
  Organizer thirdTestOrganizer;

  @BeforeEach
  void setUp() {
    testVenue = new Venue();
    testVenue.setId(1L);
    testVenue.setDistrict("Setúbal");
    testVenue.setCounty("Grândola");
    testVenue.setCity("Grândola");
    testVenue.setTitle("Complexo Desportivo Municipal José Afonso");

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
    thirdTestOrganizer.setName("Federação de Tratlo de Portugal");
    thirdTestOrganizer.setDistrict("Lisboa");
    thirdTestOrganizer.setCounty("Oeiras");
    thirdTestOrganizer.setCity("Caxias");
    thirdTestOrganizer.setOrganizerType(OrganizerType.PUBLIC);

    Set<Organizer> organizers = new HashSet<>();
    organizers.add(firstTestOrganizer);
    organizers.add(secondTestOrganizer);
    organizers.add(thirdTestOrganizer);

    testEvent = new Event();
    testEvent.setId(1L);
    testEvent.setTitle("XVI Duatlo Jovem de Grândola");
    testEvent.setStartDate(LocalDate.parse("2010-03-07"));
    testEvent.setEndDate(LocalDate.parse("2010-03-08"));
    testEvent.setVenue(testVenue);
    testEvent.setOrganizers(organizers);
  }

  @Test
  void getId() {
    assertEquals(1L, testEvent.getId());
  }

  @Test
  void getTitle() {
    assertEquals("XVI Duatlo Jovem de Grândola", testEvent.getTitle());
  }

  @Test
  void getStartDate() {
    assertEquals(LocalDate.parse("2010-03-07"), testEvent.getStartDate());
  }

  @Test
  void getEndDate() {
    assertEquals(LocalDate.parse("2010-03-08"), testEvent.getEndDate());
  }

  @Test
  void getVenue() {
    assertNotNull(testEvent.getVenue());
    assertEquals("Complexo Desportivo Municipal José Afonso", testEvent.getVenue().getTitle());
  }

  @Test
  void getOrganizers() {
    assertNotNull(testEvent.getOrganizers());
    assertFalse(testEvent.getOrganizers().isEmpty());
    assertTrue(testEvent.getOrganizers().contains(firstTestOrganizer));
    assertTrue(testEvent.getOrganizers().contains(secondTestOrganizer));
    assertTrue(testEvent.getOrganizers().contains(thirdTestOrganizer));
    assertEquals(3, testEvent.getOrganizers().size());
  }
}