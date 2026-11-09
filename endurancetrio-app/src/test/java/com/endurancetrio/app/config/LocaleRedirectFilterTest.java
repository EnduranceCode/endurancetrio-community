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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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

  @ParameterizedTest
  @ValueSource(strings = {
      "/css/styles.css",
      "/js/app.js",
      "/img/logo.png",
      "/webjars/bootstrap/5.3.0/js/bootstrap.min.js",
      "/favicon.ico",
      "/apple-touch-icon.png",
      "/robots.txt",
      "/sitemap.xml",
      "/api/tracker/v1/devices",
      "/swagger-ui/index.html",
      "/v3/api-docs"
  })
  void shouldPassStaticAndApiRequestsThroughWithoutRedirect(String uri) throws Exception {
    when(request.getRequestURI()).thenReturn(uri);

    underTest.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(request, response);
    verify(response, never()).sendRedirect(any());
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
  void shouldRedirectToCleanPathWhenLocaleHasSessionId() throws Exception {
    when(request.getRequestURI()).thenReturn("/en;jsessionid=ABC123");

    underTest.doFilter(request, response, filterChain);

    verify(response).sendRedirect("/en");
    verify(filterChain, never()).doFilter(any(), any());
  }

  @Test
  void shouldRedirectToCleanPathWhenLocalePathHasSessionId() throws Exception {
    when(request.getRequestURI()).thenReturn("/en/;jsessionid=ABC123");

    underTest.doFilter(request, response, filterChain);

    verify(response).sendRedirect("/en/");
    verify(filterChain, never()).doFilter(any(), any());
  }

  @Test
  void shouldRedirectToCleanPathPreservingQueryString() throws Exception {
    when(request.getRequestURI()).thenReturn("/pt/eventos;jsessionid=ABC123");
    when(request.getQueryString()).thenReturn("page=2");

    underTest.doFilter(request, response, filterChain);

    verify(response).sendRedirect("/pt/eventos?page=2");
    verify(filterChain, never()).doFilter(any(), any());
  }

  @Test
  void shouldStripSessionIdBeforeApplyingLocaleRedirect() throws Exception {
    when(request.getRequestURI()).thenReturn("/events;jsessionid=ABC123");
    when(request.getQueryString()).thenReturn(null);

    underTest.doFilter(request, response, filterChain);

    verify(response).sendRedirect("/events");
    verify(filterChain, never()).doFilter(any(), any());
  }
}
