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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class VenueTest {

  Venue testVenue;

  @BeforeEach
  void setUp() {
    testVenue = new Venue();
    testVenue.setId(1L);
    testVenue.setDistrict("Setúbal");
    testVenue.setCounty("Grândola");
    testVenue.setCity("Grândola");
    testVenue.setTitle("Complexo Desportivo Municipal José Afonso");
  }

  @Test
  void getId() {
    assertEquals(1L, testVenue.getId());
  }

  @Test
  void getDistrict() {
    assertEquals("Setúbal", testVenue.getDistrict());
  }

  @Test
  void getCounty() {
    assertEquals("Grândola", testVenue.getCounty());
  }

  @Test
  void getCity() {
    assertEquals("Grândola", testVenue.getCity());
  }

  @Test
  void getTitle() {
    assertEquals("Complexo Desportivo Municipal José Afonso", testVenue.getTitle());
  }
}