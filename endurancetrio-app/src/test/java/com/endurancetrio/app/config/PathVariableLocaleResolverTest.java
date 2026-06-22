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
 * THE SOFTWARE IS PROVIDED AS IS AND WITHOUT WARRANTIES OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING WITHOUT LIMITATION WARRANTIES OF FITNESS FOR A PARTICULAR
 * PURPOSE, MERCHANTABILITY, TITLE OR NON-INFRINGEMENT.
 *
 * IN NO EVENT WILL WE HAVE ANY LIABILITY TO YOU ARISING OUT OF OR RELATED TO THE
 * SOFTWARE, INCLUDING INDIRECT, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES,
 * EVEN IF BEEN INFORMED OF THEIR POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.app.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PathVariableLocaleResolverTest {

  private PathVariableLocaleResolver underTest;

  @BeforeEach
  void setUp() {
    underTest = new PathVariableLocaleResolver();
  }

  @Test
  void shouldResolveEnglishByDefault() {
    HttpServletRequest request = createRequest("/events", null, null);

    Locale result = underTest.resolveLocale(request);

    assertEquals(Locale.ENGLISH, result);
  }

  @Test
  void shouldResolvePortugueseFromPath() {
    HttpServletRequest request = createRequest("/pt/events", null, null);

    Locale result = underTest.resolveLocale(request);

    assertEquals(Locale.of("pt", "PT"), result);
  }

  @Test
  void shouldResolveEnglishFromPath() {
    HttpServletRequest request = createRequest("/en/events/1984", null, null);

    Locale result = underTest.resolveLocale(request);

    assertEquals(Locale.ENGLISH, result);
  }

  @Test
  void shouldReturnEnglishForApiPaths() {
    HttpServletRequest request = createRequest("/api/tracker/v1/devices", null, null);

    Locale result = underTest.resolveLocale(request);

    assertEquals(Locale.ENGLISH, result);
  }

  @Test
  void shouldResolvePortugueseFromAcceptLanguage() {
    HttpServletRequest request = createRequest("/events", "pt-PT,pt;q=0.9,en;q=0.5", null);

    Locale result = underTest.resolveLocale(request);

    assertEquals(Locale.of("pt", "PT"), result);
  }

  @Test
  void shouldResolveEnglishFromAcceptLanguageWhenNotPortuguese() {
    HttpServletRequest request = createRequest("/events", "fr-FR,fr;q=0.9", null);

    Locale result = underTest.resolveLocale(request);

    assertEquals(Locale.ENGLISH, result);
  }

  @Test
  void shouldResolveLocaleFromErrorRequestUri() {
    HttpServletRequest request = createRequest("/error", null, "/pt/events/404");

    Locale result = underTest.resolveLocale(request);

    assertEquals(Locale.of("pt", "PT"), result);
  }

  @Test
  void shouldResolveEnglishFromErrorRequestUri() {
    HttpServletRequest request = createRequest("/error", null, "/en/events/404");

    Locale result = underTest.resolveLocale(request);

    assertEquals(Locale.ENGLISH, result);
  }

  @Test
  void shouldStripContextPathFromErrorRequestUri() {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI)).thenReturn("/myapp/pt/events");
    when(request.getContextPath()).thenReturn("/myapp");
    when(request.getRequestURI()).thenReturn("/error");

    Locale result = underTest.resolveLocale(request);

    assertEquals(Locale.of("pt", "PT"), result);
  }

  @Test
  void setLocaleShouldNotThrow() {
    underTest.setLocale(mock(HttpServletRequest.class), null, Locale.ENGLISH);
  }

  private static HttpServletRequest createRequest(
      String requestUri, String acceptLanguage, String errorRequestUri) {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getRequestURI()).thenReturn(requestUri);
    if (acceptLanguage != null) {
      when(request.getHeader("Accept-Language")).thenReturn(acceptLanguage);
    }
    if (errorRequestUri != null) {
      when(request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI)).thenReturn(errorRequestUri);
    }
    return request;
  }
}
