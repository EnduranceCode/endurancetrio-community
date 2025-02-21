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

import com.endurancetrio.data.model.enumerator.FileType;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link EventFile} entity.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is
 * to establish a testing culture from the very beginning of the project. It serves as a reminder
 * that every part of the application should be testable and that tests should always be present.
 */
class EventFileTest {

  EventFile underTest;

  Event testEvent;

  @BeforeEach
  void setUp() {
    testEvent = new Event();
    testEvent.setId(1L);
    testEvent.setTitle("XVI Duatlo Jovem de Grândola");
    testEvent.setStartDate(LocalDate.parse("2010-03-07"));
    testEvent.setEndDate(LocalDate.parse("2010-03-08"));

    underTest = new EventFile();
    underTest.setId(1L);
    underTest.setFileType(FileType.RULES);
    underTest.setTitle("Regulamento");
    underTest.setFileName("20100307FTP001-REG001.pdf");
    underTest.setRevisionNumber(1);
    underTest.setActive(true);
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(1L, underTest.getId());
    assertEquals(FileType.RULES, underTest.getFileType());
    assertEquals("Regulamento", underTest.getTitle());
    assertEquals("20100307FTP001-REG001.pdf", underTest.getFileName());
    assertEquals(1, underTest.getRevisionNumber());
    assertTrue(underTest.getActive());
  }
}
