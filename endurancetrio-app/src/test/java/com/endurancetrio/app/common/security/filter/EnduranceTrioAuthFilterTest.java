/*
 * Copyright (c) 2025-2025 Ricardo do Canto
 *
 * This file is part of the EnduranceTrio Tracker project.
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

package com.endurancetrio.app.common.security.filter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.endurancetrio.app.common.security.token.EnduranceTrioAuthToken;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.AuthenticationEntryPoint;

@ExtendWith(MockitoExtension.class)
class EnduranceTrioAuthFilterTest {

  private static final String VALID_KEY = "validKey123";
  private static final String OWNER = "testOwner";

  @Mock
  private AuthenticationManager authManager;

  @Mock
  private AuthenticationEntryPoint entryPoint;

  @InjectMocks
  private EnduranceTrioAuthFilter underTest;

  private HttpServletRequest request;
  private HttpServletResponse response;
  private FilterChain filterChain;

  @BeforeEach
  void setUp() {
    request = mock(HttpServletRequest.class);
    response = new MockHttpServletResponse();
    filterChain = mock(FilterChain.class);
  }

  @Test
  void validRequestShouldAuthenticate() throws Exception {
    when(request.getHeader("Authorization")).thenReturn("Bearer " + VALID_KEY);
    when(request.getHeader("ET-Owner")).thenReturn(OWNER);

    Authentication authResult = mock(Authentication.class);
    when(authResult.isAuthenticated()).thenReturn(true);
    when(authManager.authenticate(any(EnduranceTrioAuthToken.class))).thenReturn(authResult);

    underTest.doFilterInternal(request, response, filterChain);

    verify(authManager).authenticate(any(EnduranceTrioAuthToken.class));
    verify(filterChain).doFilter(request, response);
    verify(entryPoint, never()).commence(any(), any(), any());
  }

  @Test
  void missingAuthHeaderShouldSkipAuth() throws Exception {
    when(request.getHeader("Authorization")).thenReturn(null);
    when(request.getHeader("ET-Owner")).thenReturn(null);

    underTest.doFilterInternal(request, response, filterChain);

    verify(authManager, never()).authenticate(any());
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void missingOwnerHeaderShouldSkipAuth() throws Exception {
    when(request.getHeader("Authorization")).thenReturn("Bearer " + VALID_KEY);
    when(request.getHeader("ET-Owner")).thenReturn(null);

    underTest.doFilterInternal(request, response, filterChain);

    verify(authManager, never()).authenticate(any());
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void invalidAuthHeaderShouldSkipAuth() throws Exception {
    when(request.getHeader("Authorization")).thenReturn("Invalid header");
    when(request.getHeader("ET-Owner")).thenReturn(OWNER);

    underTest.doFilterInternal(request, response, filterChain);

    verify(authManager, never()).authenticate(any());
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void blankOwnerHeaderShouldSkipAuth() throws Exception {
    when(request.getHeader("Authorization")).thenReturn("Bearer " + VALID_KEY);
    when(request.getHeader("ET-Owner")).thenReturn("");

    underTest.doFilterInternal(request, response, filterChain);

    verify(authManager, never()).authenticate(any());
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void authenticationFailureShouldCallEntryPoint() throws Exception {
    when(request.getHeader("Authorization")).thenReturn("Bearer " + VALID_KEY);
    when(request.getHeader("ET-Owner")).thenReturn(OWNER);

    BadCredentialsException exception = new BadCredentialsException("Invalid key");
    when(authManager.authenticate(any(EnduranceTrioAuthToken.class))).thenThrow(exception);

    underTest.doFilterInternal(request, response, filterChain);

    verify(entryPoint).commence(request, response, exception);
    verify(filterChain, never()).doFilter(request, response);
  }

  @Test
  void lowercaseHeadersShouldBeChecked() throws Exception {
    when(request.getHeader("Authorization")).thenReturn(null);
    when(request.getHeader("authorization")).thenReturn("Bearer " + VALID_KEY);
    when(request.getHeader("ET-Owner")).thenReturn(null);
    when(request.getHeader("et-owner")).thenReturn(OWNER);

    Authentication authResult = mock(Authentication.class);
    when(authResult.isAuthenticated()).thenReturn(true);
    when(authManager.authenticate(any(EnduranceTrioAuthToken.class))).thenReturn(authResult);

    underTest.doFilterInternal(request, response, filterChain);

    verify(authManager).authenticate(any(EnduranceTrioAuthToken.class));
    verify(filterChain).doFilter(request, response);
  }
}
