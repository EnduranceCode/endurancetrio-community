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

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.util.ContentCachingResponseWrapper;

@ExtendWith(MockitoExtension.class)
class HtmlResponseCachingFilterTest {

  private HtmlResponseCachingFilter underTest;

  private HttpServletRequest request;
  private HttpServletResponse response;
  private FilterChain filterChain;

  @BeforeEach
  void setUp() {
    underTest = new HtmlResponseCachingFilter();
    request = mock(HttpServletRequest.class);
    response = mock(HttpServletResponse.class);
    filterChain = mock(FilterChain.class);
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
          "/", "/en", "/en/", "/en/events/1984", "/pt/eventos/1984", "/en/events;jsessionid=ABC123"
      }
  )
  void shouldWrapPageResponses(String uri) throws Exception {
    when(request.getRequestURI()).thenReturn(uri);

    ServletResponse chainResponse = invokeFilterAndCaptureChainResponse();

    assertInstanceOf(ContentCachingResponseWrapper.class, chainResponse);
  }

  @ParameterizedTest
  @ValueSource(
      strings = {
          "/css/styles.css", "/js/app.js", "/img/logo.png", "/webjars/bootstrap/5.3.0/js/bootstrap.min.js",
          "/favicon.ico", "/favicon.svg", "/apple-touch-icon.png", "/robots.txt", "/sitemap.xml",
          "/api/tracker/v1/devices", "/actuator/health", "/v3/api-docs", "/v3/api-docs.yaml",
          "/v3/api-docs/swagger-config", "/swagger-ui/index.html"
      }
  )
  void shouldPassNonPageRequestsThroughWithoutWrapping(String uri) throws Exception {
    when(request.getRequestURI()).thenReturn(uri);

    ServletResponse chainResponse = invokeFilterAndCaptureChainResponse();

    assertSame(response, chainResponse);
  }

  @Test
  void shouldCopyCachedBodyToResponseAfterSuccessfulRendering() throws Exception {
    when(request.getRequestURI()).thenReturn("/en/events/1984");
    ByteArrayOutputStream realBody = new ByteArrayOutputStream();
    stubChainWriting(realBody, "<html><body>partial</body></html>", null);

    underTest.doFilter(request, response, filterChain);

    assertEquals("<html><body>partial</body></html>", realBody.toString(UTF_8));
    assertEquals(0, capturedWrapper().getContentSize());
  }

  @Test
  void shouldDiscardCachedBodyWhenRenderingFails() throws Exception {
    when(request.getRequestURI()).thenReturn("/en/events/1984");
    ByteArrayOutputStream realBody = new ByteArrayOutputStream();
    ServletException failure = new ServletException("render failed");
    stubChainWriting(realBody, "<html><body>partial</body></html>", failure);

    ServletException thrown = assertThrows(ServletException.class,
        () -> underTest.doFilter(request, response, filterChain)
    );

    assertSame(failure, thrown);
    assertEquals("", realBody.toString(UTF_8));
    assertEquals(0, capturedWrapper().getContentSize());
  }

  @Test
  void shouldCopyEntireCachedBodyWithoutSizeCap() throws Exception {
    when(request.getRequestURI()).thenReturn("/en/events/1984");
    ByteArrayOutputStream realBody = new ByteArrayOutputStream();
    String body = "x".repeat(100_000);
    stubChainWriting(realBody, body, null);

    underTest.doFilter(request, response, filterChain);

    assertEquals(body, realBody.toString(UTF_8));
  }

  private ServletResponse invokeFilterAndCaptureChainResponse() throws Exception {
    underTest.doFilter(request, response, filterChain);

    ArgumentCaptor<ServletResponse> captor = ArgumentCaptor.forClass(ServletResponse.class);
    verify(filterChain).doFilter(eq(request), captor.capture());
    return captor.getValue();
  }

  private ContentCachingResponseWrapper capturedWrapper() throws Exception {
    ArgumentCaptor<ServletResponse> captor = ArgumentCaptor.forClass(ServletResponse.class);
    verify(filterChain).doFilter(eq(request), captor.capture());
    return (ContentCachingResponseWrapper) captor.getValue();
  }

  private void stubChainWriting(ByteArrayOutputStream realBody, String body, Exception failure) throws Exception {
    when(response.getOutputStream()).thenReturn(capturingOutputStream(realBody));
    doAnswer(invocation -> {
      ServletResponse chainResponse = invocation.getArgument(1);
      chainResponse.getOutputStream().write(body.getBytes(UTF_8));
      if (failure != null) {
        throw failure;
      }
      return null;
    }).when(filterChain).doFilter(eq(request), any(ServletResponse.class));
  }

  private static ServletOutputStream capturingOutputStream(ByteArrayOutputStream target) {
    return new ServletOutputStream() {
      @Override
      public void write(int b) {
        target.write(b);
      }

      @Override
      public boolean isReady() {
        return true;
      }

      @Override
      public void setWriteListener(WriteListener listener) {
        // This in-memory stream is written synchronously, so async write listeners are not used.
      }
    };
  }
}
