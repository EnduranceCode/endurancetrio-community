/*
 * Copyright (c) 2011-2026 Ricardo do Canto
 *
 * This file is part of the EnduranceTrio project.
 *
 * Licensed under the Functional Software License (FSL), Version 1.1, ALv2 Future License
 * (the "License");
 *
 * You may not use this file except in compliance with the License. You may obtain a copy
 * of the License at https://fsl.software/
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND WITHOUT WARRANTIES OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING WITHOUT LIMITATION WARRANTIES OF FITNESS FOR A PARTICULAR
 * PURPOSE, MERCHANTABILITY, TITLE OR NON-INFRINGEMENT.
 *
 * IN NO EVENT WILL WE HAVE ANY LIABILITY TO YOU ARISING OUT OF OR RELATED TO THE
 * SOFTWARE, INCLUDING INDIRECT, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES,
 * EVEN IF WE HAVE BEEN INFORMED OF THEIR POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.data.event.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.endurancetrio.data.event.model.entity.TriathlonDistance;
import com.endurancetrio.data.event.model.enumerator.DistanceType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link TriathlonDistance} entity.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class TriathlonDistanceTest {

  private TriathlonDistance underTest;

  @BeforeEach
  void setUp() {
    underTest = new TriathlonDistance();
    underTest.setId(1L);
    underTest.setDistanceType(DistanceType.SPRINT);
    underTest.setSwimDistance(600);
    underTest.setSwimLaps(1);
    underTest.setBikeDistance(17000);
    underTest.setBikeLaps(1);
    underTest.setRunDistance(8000);
    underTest.setRunLaps(1);
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(1L, underTest.getId());
    assertEquals(DistanceType.SPRINT, underTest.getDistanceType());
    assertEquals(600, underTest.getSwimDistance());
    assertEquals(1, underTest.getSwimLaps());
    assertEquals(17000, underTest.getBikeDistance());
    assertEquals(1, underTest.getBikeLaps());
    assertEquals(8000, underTest.getRunDistance());
    assertEquals(1, underTest.getRunLaps());
  }
}
