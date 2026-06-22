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
 * THE SOFTWARE IS PROVIDED IS AND WITHOUT WARRANTIES OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING WITHOUT LIMITATION WARRANTIES OF FITNESS FOR A PARTICULAR
 * PURPOSE, MERCHANTABILITY, TITLE OR NON-INFRINGEMENT.
 *
 * IN NO EVENT WILL WE HAVE ANY LIABILITY TO YOU ARISING OUT OF OR RELATED TO THE
 * SOFTWARE, INCLUDING INDIRECT, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES,
 * EVEN IF HAVE BEEN INFORMED OF THEIR POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.app.config;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class LocaleRedirectFilterTest {

  private LocaleRedirectFilter underTest;

  private HttpServletRequest request;
  private HttpServletResponse response;
  private FilterChain filterChain;

  @BeforeEach
  void setUp() {
    underTest = new LocaleRedirectFilter();
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    filterChain = mock(FilterChain.class);
  }

  @Test
  void shouldPassStaticCssRequests() throws Exception {
    when(request.getRequestURI()).thenReturn("/css/styles.css");

    underTest.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    verify(response, never()).sendRedirect(any());
  }

  @Test
  void shouldPassStaticJsRequests() throws Exception {
    when(request.getRequestURI()).thenReturn("/js/app.js");

    underTest.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void shouldPassStaticImgRequests() throws Exception {
    when(request.getRequestURI()).thenReturn("/img/logo.png");

    underTest.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void shouldPassWebjarsRequests() throws Exception {
    when(request.getRequestURI()).thenReturn("/webjars/bootstrap/5.3.0/js/bootstrap.min.js");

    underTest.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void shouldPassFaviconRequests() throws Exception {
    when(request.getRequestURI()).thenReturn("/favicon.ico");

    underTest.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void shouldPassApiRequests() throws Exception {
    when(request.getRequestURI()).thenReturn("/api/tracker/v1/devices");

    underTest.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void shouldPassSwaggerUiRequests() throws Exception {
    when(request.getRequestURI()).thenReturn("/swagger-ui/index.html");

    underTest.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void shouldPassApiDocsRequests() throws Exception {
    when(request.getRequestURI()).thenReturn("/v3/api-docs");

    underTest.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }

  @Test
  void shouldRedirectToEnglishWhenNoLocaleInPathAndNoAcceptLanguage() throws Exception {
    when(request.getRequestURI()).thenReturn("/events");
    when(request.getHeader("Accept-Language")).thenReturn(null);

    underTest.doFilter(request, response, filterChain);

    verify(response).sendRedirect("/en/events");
    verify(filterChain, never()).doFilter(any(), any());
  }

  @Test
  void shouldRedirectToPortugueseWhenAcceptLanguageStartsWithPt() throws Exception {
    when(request.getRequestURI()).thenReturn("/events");
    when(request.getHeader("Accept-Language")).thenReturn("pt-PT,pt;q=0.9");

    underTest.doFilter(request, response, filterChain);

    verify(response).sendRedirect("/pt/events");
    verify(filterChain, never()).doFilter(any(), any());
  }

  @Test
  void shouldNotRedirectWhenPathHasEnglishLocale() throws Exception {
    when(request.getRequestURI()).thenReturn("/en/events/1984");

    underTest.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    verify(response, never()).sendRedirect(any());
  }

  @Test
  void shouldNotRedirectWhenPathHasPortugueseLocale() throws Exception {
    when(request.getRequestURI()).thenReturn("/pt/eventos/1984");

    underTest.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    verify(response, never()).sendRedirect(any());
  }

  @Test
  void shouldPassAppleTouchIconRequests() throws Exception {
    when(request.getRequestURI()).thenReturn("/apple-touch-icon.png");

    underTest.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
  }
}
