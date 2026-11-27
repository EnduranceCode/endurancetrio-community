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

package com.endurancetrio.app.config;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ContentLanguageFilterTest {

  private static final String CONTENT_LANGUAGE = "Content-Language";

  private static final String HEADER_CONTENT_TYPE = "Content-Type";

  private ContentLanguageFilter underTest;

  private HttpServletRequest request;
  private HttpServletResponse response;
  private FilterChain filterChain;

  @BeforeEach
  void setUp() {
    underTest = new ContentLanguageFilter();
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    filterChain = mock(FilterChain.class);
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
          "/css/styles.css", "/js/app.js", "/img/logo.png", "/webjars/bootstrap/5.3.0/js/bootstrap.min.js",
          "/api/tracker/v1/devices", "/v3/api-docs", "/swagger-ui/index.html"
      }
  )
  void shouldNotSetHeaderForBypassedUris(String uri) throws Exception {
    when(request.getRequestURI()).thenReturn(uri);

    underTest.doFilter(request, response, filterChain);

    verify(response, never()).setHeader(anyString(), anyString());
    ArgumentCaptor<ServletResponse> captor = ArgumentCaptor.forClass(ServletResponse.class);
    verify(filterChain).doFilter(eq(request), captor.capture());
    assertSame(response, captor.getValue());
  }

  @ParameterizedTest
  @CsvSource(
      {
          "/en,en", "/en/,en", "/en/athletes,en", "/pt,pt", "/pt/,pt", "/pt/eventos/1984,pt"
      }
  )
  void shouldSetHeaderFromLocalePathSegmentForHtmlResponses(String uri, String expectedLanguage) throws Exception {
    when(request.getRequestURI()).thenReturn(uri);

    ServletResponse chainResponse = invokeFilterAndCaptureChainResponse();
    chainResponse.setContentType("text/html;charset=UTF-8");

    verify(response).setHeader(CONTENT_LANGUAGE, expectedLanguage);
  }

  @ParameterizedTest
  @ValueSource(strings = {"application/json", "text/plain;charset=UTF-8", "application/octet-stream"})
  void shouldNotSetHeaderForNonHtmlResponses(String contentType) throws Exception {
    when(request.getRequestURI()).thenReturn("/en/athletes");

    ServletResponse chainResponse = invokeFilterAndCaptureChainResponse();
    chainResponse.setContentType(contentType);

    verify(response, never()).setHeader(anyString(), anyString());
  }

  @Test
  void shouldSetHeaderWhenHtmlContentTypeIsSetAsHeader() throws Exception {
    when(request.getRequestURI()).thenReturn("/pt/athletes");

    ServletResponse chainResponse = invokeFilterAndCaptureChainResponse();
    ((HttpServletResponse) chainResponse).setHeader(HEADER_CONTENT_TYPE, "text/html;charset=UTF-8");

    verify(response).setHeader(CONTENT_LANGUAGE, "pt");
  }

  @Test
  void shouldNotSetHeaderUntilContentTypeIsSet() throws Exception {
    when(request.getRequestURI()).thenReturn("/en/athletes");

    invokeFilterAndCaptureChainResponse();

    verify(response, never()).setHeader(anyString(), anyString());
  }

  private ServletResponse invokeFilterAndCaptureChainResponse() throws Exception {
    underTest.doFilter(request, response, filterChain);

    ArgumentCaptor<ServletResponse> captor = ArgumentCaptor.forClass(ServletResponse.class);
    verify(filterChain).doFilter(eq(request), captor.capture());
    return captor.getValue();
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
          "/", "/events", "/robots.txt", "/sitemap.xml", "/favicon.ico", "/en;jsessionid=ABC123"
      }
  )
  void shouldNotSetHeaderWithoutMatchingLocaleSegment(String uri) throws Exception {
    when(request.getRequestURI()).thenReturn(uri);

    underTest.doFilter(request, response, filterChain);

    verify(response, never()).setHeader(anyString(), anyString());
    verify(filterChain).doFilter(request, response);
  }

  @Test
  void shouldAlwaysInvokeChain() throws Exception {
    when(request.getRequestURI()).thenReturn("/en/athletes");

    underTest.doFilter(request, response, filterChain);

    verify(filterChain).doFilter(eq(request), any(ServletResponse.class));
  }
}
