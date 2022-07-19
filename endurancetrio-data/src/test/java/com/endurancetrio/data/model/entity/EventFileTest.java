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
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.endurancetrio.data.model.enumerator.FileType;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventFileTest {

  EventFile testEventFile;

  Venue testVenue;
  Event testEvent;

  @BeforeEach
  void setUp() {
    testVenue = new Venue();
    testVenue.setId(1L);
    testVenue.setDistrict("Setúbal");
    testVenue.setCounty("Grândola");
    testVenue.setCity("Grândola");
    testVenue.setTitle("Complexo Desportivo Municipal José Afonso");

    testEvent = new Event();
    testEvent.setId(1L);
    testEvent.setTitle("XVI Duatlo Jovem de Grândola");
    testEvent.setStartDate(LocalDate.parse("2010-03-07"));
    testEvent.setEndDate(LocalDate.parse("2010-03-08"));
    testEvent.setVenue(testVenue);

    testEventFile = new EventFile();
    testEventFile.setId(1L);
    testEventFile.setEvent(testEvent);
    testEventFile.setTitle("Regulamento");
    testEventFile.setFileType(FileType.RULES);
    testEventFile.setFileName("20100306FTP001-RUL001.pdf");
    testEventFile.setRevisionNumber(1);
    testEventFile.setActive(true);
  }

  @Test
  void getId() {
    assertEquals(1L, testEventFile.getId());
  }

  @Test
  void getEvent() {
    assertEquals(testEvent, testEventFile.getEvent());
  }

  @Test
  void getTitle() {
    assertEquals("Regulamento", testEventFile.getTitle());
  }

  @Test
  void getFileType() {
    assertEquals(FileType.RULES, testEventFile.getFileType());
  }

  @Test
  void getFileName() {
    assertEquals("20100306FTP001-RUL001.pdf", testEventFile.getFileName());
  }

  @Test
  void getRevisionNumber() {
    assertEquals(1, testEventFile.getRevisionNumber());
  }

  @Test
  void getActive() {
    assertTrue(testEventFile.getActive());
  }
}