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
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FeatureTest {

  @Test
  void shouldCreateWithTypeGeometryAndProperties() {
    Geometry geometry = new PointGeometry(List.of(1.0, 2.0));
    Map<String, Object> properties = Map.of("key", "value");
    Feature feature = new Feature("Feature", geometry, properties);

    assertEquals("Feature", feature.type());
    assertEquals(geometry, feature.geometry());
    assertEquals(properties, feature.properties());
  }

  @Test
  void ofShouldCreateFeatureWithType() {
    Geometry geometry = new PointGeometry(List.of(1.0, 2.0));
    Feature feature = Feature.of(geometry, null);

    assertEquals("Feature", feature.type());
    assertEquals(geometry, feature.geometry());
    assertNull(feature.properties());
  }

  @Test
  void ofShouldCreateFeatureWithNullGeometry() {
    Feature feature = Feature.of(null, null);

    assertEquals("Feature", feature.type());
    assertNull(feature.geometry());
  }
}
