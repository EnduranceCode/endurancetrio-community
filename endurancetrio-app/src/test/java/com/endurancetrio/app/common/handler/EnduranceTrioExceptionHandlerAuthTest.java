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

package com.endurancetrio.app.common.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.endurancetrio.app.common.response.EnduranceTrioResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;

@ExtendWith(MockitoExtension.class)
class EnduranceTrioExceptionHandlerAuthTest {

  @InjectMocks
  private EnduranceTrioExceptionHandlerAuth underTest;

  @Test
  void authExceptionShouldReturn401() {
    AuthenticationException exception = new BadCredentialsException("Invalid credentials");

    ResponseEntity<EnduranceTrioResponse<String>> response = underTest.authException(exception);

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    assertEquals(401, response.getBody().status());
    assertEquals("Unauthorized", response.getBody().reason());
  }

  @Test
  void accessDeniedExceptionShouldReturn403() {
    AccessDeniedException exception = new AccessDeniedException("Access denied");

    ResponseEntity<EnduranceTrioResponse<String>> response = underTest.handleAuthorizationException(
        exception);

    assertNotNull(response.getBody());
    assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    assertEquals(403, response.getBody().status());
    assertEquals("Forbidden", response.getBody().reason());
  }
}
