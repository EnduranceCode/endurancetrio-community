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

import com.endurancetrio.data.model.enumerator.OrganizerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrganizerTest {

  Organizer testOrganizer;

  @BeforeEach
  void setUp() {
    testOrganizer = new Organizer();
    testOrganizer.setId(1L);
    testOrganizer.setName("Câmara Municipal de Grândola");
    testOrganizer.setDistrict("Setúbal");
    testOrganizer.setCounty("Grândola");
    testOrganizer.setCity("Grândola");
    testOrganizer.setOrganizerType(OrganizerType.PUBLIC);
  }

  @Test
  void getId() {
    assertEquals(1L, testOrganizer.getId());
  }

  @Test
  void getName() {
    assertEquals("Câmara Municipal de Grândola", testOrganizer.getName());
  }

  @Test
  void getDistrict() {
    assertEquals("Setúbal", testOrganizer.getDistrict());
  }

  @Test
  void getCounty() {
    assertEquals("Grândola", testOrganizer.getCounty());
  }

  @Test
  void getCity() {
    assertEquals("Grândola", testOrganizer.getCity());
  }

  @Test
  void getOrganizerType() {
    assertEquals(OrganizerType.PUBLIC, testOrganizer.getOrganizerType());
  }
}