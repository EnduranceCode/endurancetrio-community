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

package com.endurancetrio.app.tracker.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.endurancetrio.app.common.response.EnduranceTrioResponse;
import com.endurancetrio.business.tracker.dto.RouteDTO;
import com.endurancetrio.business.tracker.dto.RouteMetricsDTO;
import com.endurancetrio.business.tracker.dto.RouteSegmentDTO;
import com.endurancetrio.business.tracker.service.RouteService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class RouteRestControllerTest {

  private static final Long ROUTE_ID = 1L;
  private static final String REFERENCE = "SMP";

  @Mock
  private RouteService routeService;

  @InjectMocks
  private RouteRestController underTest;

  private RouteDTO testRoute;

  @BeforeEach
  void setUp() {
    RouteSegmentDTO segment = new RouteSegmentDTO(1L, 1, "SDABC", "SDDEF");
    testRoute = new RouteDTO(ROUTE_ID, REFERENCE, List.of(segment));
  }

  @Test
  void findAllShouldReturnList() {
    List<RouteDTO> expectedRoutes = List.of(testRoute);
    when(routeService.findAll()).thenReturn(expectedRoutes);

    ResponseEntity<EnduranceTrioResponse<List<RouteDTO>>> response = underTest.findAll();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(200, response.getBody().status());
    assertEquals(1, response.getBody().data().size());
  }

  @Test
  void createShouldReturnCreated() {
    when(routeService.create(testRoute)).thenReturn(testRoute);

    ResponseEntity<EnduranceTrioResponse<RouteDTO>> response = underTest.create(testRoute);

    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(201, response.getBody().status());
    assertEquals(REFERENCE, response.getBody().data().reference());
  }

  @Test
  void updateShouldReturnOk() {
    when(routeService.update(ROUTE_ID, testRoute)).thenReturn(testRoute);

    ResponseEntity<EnduranceTrioResponse<RouteDTO>> response = underTest.update(ROUTE_ID,
        testRoute
    );

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(200, response.getBody().status());
    assertEquals(REFERENCE, response.getBody().data().reference());
  }

  @Test
  void findByIdShouldReturnRoute() {
    when(routeService.findById(ROUTE_ID)).thenReturn(testRoute);

    ResponseEntity<EnduranceTrioResponse<RouteDTO>> response = underTest.findById(ROUTE_ID);

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertEquals(ROUTE_ID, response.getBody().data().id());
  }

  @Test
  void getRouteMetricsShouldReturnMetrics() {
    RouteMetricsDTO metrics = new RouteMetricsDTO(List.of());
    when(routeService.getRouteMetrics(ROUTE_ID)).thenReturn(metrics);

    ResponseEntity<EnduranceTrioResponse<RouteMetricsDTO>> response = underTest.getRouteMetrics(
        ROUTE_ID);

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody().data());
  }
}
