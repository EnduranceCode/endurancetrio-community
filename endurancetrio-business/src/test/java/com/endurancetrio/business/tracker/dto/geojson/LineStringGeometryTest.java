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

class LineStringGeometryTest {

  @Test
  void shouldReturnLineStringType() {
    LineStringGeometry geometry = new LineStringGeometry(List.of());

    assertEquals("LineString", geometry.getType());
  }

  @Test
  void shouldRetainCoordinates() {
    List<List<Double>> coordinates = List.of(
        List.of(1.0, 2.0),
        List.of(3.0, 4.0)
    );
    LineStringGeometry geometry = new LineStringGeometry(coordinates);

    assertEquals(coordinates, geometry.coordinates());
    assertEquals(2, geometry.coordinates().size());
  }

  @Test
  void shouldHandleEmptyCoordinates() {
    LineStringGeometry geometry = new LineStringGeometry(List.of());

    assertEquals(0, geometry.coordinates().size());
  }
}
