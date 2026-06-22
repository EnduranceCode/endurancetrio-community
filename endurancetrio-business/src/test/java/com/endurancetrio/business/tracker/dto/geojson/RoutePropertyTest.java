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

package com.endurancetrio.business.tracker.dto.geojson;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class RoutePropertyTest {

  private static final Long ID = 1L;
  private static final String REFERENCE = "SMP";
  private static final Long TOTAL_DISTANCE = 1000L;

  @Test
  void shouldCreateWithAllFields() {
    RouteSegmentProperty segment = new RouteSegmentProperty(1, 500L);
    RouteProperty property = new RouteProperty(ID, REFERENCE, TOTAL_DISTANCE, List.of(segment));

    assertEquals(ID, property.id());
    assertEquals(REFERENCE, property.reference());
    assertEquals(TOTAL_DISTANCE, property.totalDistance());
    assertEquals(1, property.segments().size());
  }

  @Test
  void shouldHandleEmptySegments() {
    RouteProperty property = new RouteProperty(ID, REFERENCE, TOTAL_DISTANCE, List.of());

    assertEquals(0, property.segments().size());
  }
}
