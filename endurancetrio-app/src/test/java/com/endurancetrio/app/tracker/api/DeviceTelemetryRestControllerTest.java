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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

import com.endurancetrio.app.common.response.EnduranceTrioResponse;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.tracker.dto.DeviceTelemetryDTO;
import com.endurancetrio.business.tracker.service.DeviceTelemetryService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@ExtendWith(MockitoExtension.class)
class DeviceTelemetryRestControllerTest {

  private static final String DEVICE = "SDABC";
  private static final String OWNER = "testOwner";

  @Mock
  private DeviceTelemetryService deviceTelemetryService;

  @InjectMocks
  private DeviceTelemetryRestController underTest;

  private DeviceTelemetryDTO testDTO;

  @BeforeEach
  void setUp() {
    testDTO = new DeviceTelemetryDTO(DEVICE, null, null, null, false);
  }

  @Test
  void getMostRecentRecordForEachDeviceShouldReturnList() {
    List<DeviceTelemetryDTO> expectedData = List.of(testDTO);
    when(deviceTelemetryService.findMostRecentRecordForEachDevice()).thenReturn(expectedData);

    ResponseEntity<EnduranceTrioResponse<List<DeviceTelemetryDTO>>> response = underTest.getMostRecentRecordForEachDevice();

    assertEquals(HttpStatus.OK, response.getStatusCode());
    assertNotNull(response.getBody());
    assertEquals(200, response.getBody().status());
    assertEquals(1, response.getBody().data().size());
  }

  @Test
  void saveShouldReturnCreated() {
    Authentication authentication = createMockAuthentication();

    try (MockedStatic<SecurityContextHolder> securityContext = mockStatic(
        SecurityContextHolder.class)) {
      SecurityContext context = createMockSecurityContext(authentication);
      securityContext.when(SecurityContextHolder::getContext).thenReturn(context);

      when(deviceTelemetryService.save(OWNER, testDTO)).thenReturn(testDTO);

      ResponseEntity<EnduranceTrioResponse<DeviceTelemetryDTO>> response = underTest.save(testDTO);

      assertNotNull(response);
      assertNotNull(response.getBody());
      assertEquals(HttpStatus.CREATED, response.getStatusCode());
      assertEquals(201, response.getBody().status());
      assertEquals(DEVICE, response.getBody().data().device());
    }
  }

  @Test
  void saveWithNullBodyShouldThrowBadRequest() {
    assertThrows(EnduranceTrioException.class, () -> underTest.save(null));
  }

  @Test
  void saveWithUnauthenticatedShouldThrowUnauthorized() {
    try (MockedStatic<SecurityContextHolder> securityContext = mockStatic(
        SecurityContextHolder.class)) {
      SecurityContext context = mockSecurityContextWithNullAuthentication();
      securityContext.when(SecurityContextHolder::getContext).thenReturn(context);

      assertThrows(EnduranceTrioException.class, () -> underTest.save(testDTO));
    }
  }

  private static Authentication createMockAuthentication() {
    Authentication authentication = org.mockito.Mockito.mock(Authentication.class);
    when(authentication.getName()).thenReturn(OWNER);
    when(authentication.isAuthenticated()).thenReturn(true);
    return authentication;
  }

  private static SecurityContext createMockSecurityContext(Authentication authentication) {
    SecurityContext context = org.mockito.Mockito.mock(SecurityContext.class);
    when(context.getAuthentication()).thenReturn(authentication);
    return context;
  }

  private static SecurityContext mockSecurityContextWithNullAuthentication() {
    SecurityContext context = org.mockito.Mockito.mock(SecurityContext.class);
    when(context.getAuthentication()).thenReturn(null);
    return context;
  }
}
