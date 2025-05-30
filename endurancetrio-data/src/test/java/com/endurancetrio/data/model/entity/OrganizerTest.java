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

import com.endurancetrio.data.model.enumerator.OrganizerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link Organizer} entity.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is
 * to establish a testing culture from the very beginning of the project. It serves as a reminder
 * that every part of the application should be testable and that tests should always be present.
 */
class OrganizerTest {

  Organizer underTest;

  @BeforeEach
  void setUp() {
    underTest = new Organizer();
    underTest.setId(1L);
    underTest.setName("Câmara Municipal de Grândola");
    underTest.setDistrict("Setúbal");
    underTest.setCounty("Grândola");
    underTest.setCity("Grândola");
    underTest.setOrganizerType(OrganizerType.PUBLIC);
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(1L, underTest.getId());
    assertEquals("Câmara Municipal de Grândola", underTest.getName());
    assertEquals("Setúbal", underTest.getDistrict());
    assertEquals("Grândola", underTest.getCounty());
    assertEquals("Grândola", underTest.getCity());
    assertEquals(OrganizerType.PUBLIC, underTest.getOrganizerType());
  }
}
