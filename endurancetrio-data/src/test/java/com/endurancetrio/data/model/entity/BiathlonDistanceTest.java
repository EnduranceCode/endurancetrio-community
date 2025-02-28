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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link BiathlonDistance} entity.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class BiathlonDistanceTest {

  private BiathlonDistance underTest;

  @BeforeEach
  void setUp() {
    underTest = new BiathlonDistance();
    underTest.setId(1L);
    underTest.setDistanceType(DistanceType.STANDARD);
    underTest.setBikeDistance(40000);
    underTest.setBikeLaps(2);
    underTest.setRunDistance(5000);
    underTest.setRunLaps(2);
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(1L, underTest.getId());
    assertEquals(DistanceType.STANDARD, underTest.getDistanceType());
    assertEquals(40000, underTest.getBikeDistance());
    assertEquals(2, underTest.getBikeLaps());
    assertEquals(5000, underTest.getRunDistance());
    assertEquals(2, underTest.getRunLaps());
  }
}
