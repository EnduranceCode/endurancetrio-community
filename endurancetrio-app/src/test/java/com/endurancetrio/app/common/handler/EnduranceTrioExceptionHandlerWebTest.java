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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.business.common.dto.ErrorDTO;
import com.endurancetrio.business.common.exception.EnduranceTrioError;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpServletResponseWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.ContentCachingResponseWrapper;

@ExtendWith(MockitoExtension.class)
class EnduranceTrioExceptionHandlerWebTest {

  @Mock
  private MessageService messageService;

  @Mock
  private AppProperties appProperties;

  @Mock
  private AppProperties.OpenGraph openGraph;

  @Mock
  private AppProperties.Social social;

  @Mock
  private AppProperties.Google google;

  @Mock
  private AppProperties.KoFi kofi;

  private EnduranceTrioExceptionHandlerWeb underTest;

  private HttpServletRequest request;

  private ContentCachingResponseWrapper cachedResponse;

  @BeforeEach
  void setUp() {
    underTest = new EnduranceTrioExceptionHandlerWeb(messageService, appProperties);
    request = mock(HttpServletRequest.class);
    cachedResponse = mock(ContentCachingResponseWrapper.class);

    when(appProperties.getOpenGraph()).thenReturn(openGraph);
    when(appProperties.getSocial()).thenReturn(social);
    when(appProperties.getKoFi()).thenReturn(kofi);
    when(appProperties.getGoogle()).thenReturn(google);
    when(appProperties.getCopyrightYear()).thenReturn("2026");
    when(appProperties.getVersion()).thenReturn("0.4.0");
    when(openGraph.getDefaultImg()).thenReturn("/img/endurancetrio-open-graph.png");
    when(openGraph.getDefaultImgWidth()).thenReturn(1200);
    when(openGraph.getDefaultImgHeight()).thenReturn(628);
    when(appProperties.getSiteUrl()).thenReturn("http://localhost:8080");
    when(request.getRequestURI()).thenReturn("/en/events");
  }

  @Test
  void handleEnduranceTrioExceptionShouldReturnNotFound() {
    EnduranceTrioException exception = new EnduranceTrioException(
        new ErrorDTO(EnduranceTrioError.NOT_FOUND));

    when(messageService.getMessage(eq("page.error.404.metadata.title"), any(), any())).thenReturn(
        "Page Not Found");
    when(messageService.getMessage(eq("page.error.404.metadata.description"), any(),
        any()
    )).thenReturn(
        "Not found");

    ModelAndView result = underTest.handleEnduranceTrioException(exception, request, cachedResponse);

    assertNotNull(result);
    assertEquals("error/404", result.getViewName());
    assertEquals("The requested resource was not found", result.getModel().get("message"));
    verify(cachedResponse).resetBuffer();
  }

  @Test
  void handleEnduranceTrioExceptionShouldReturnForbidden() {
    EnduranceTrioException exception = new EnduranceTrioException(
        new ErrorDTO(EnduranceTrioError.FORBIDDEN));

    when(messageService.getMessage(eq("page.error.403.metadata.title"), any(), any())).thenReturn(
        "Forbidden");
    when(messageService.getMessage(eq("page.error.403.metadata.description"), any(),
        any()
    )).thenReturn(
        "Access denied");

    ModelAndView result = underTest.handleEnduranceTrioException(exception, request, cachedResponse);

    assertNotNull(result);
    assertEquals("error/403", result.getViewName());
    verify(cachedResponse).resetBuffer();
  }

  @Test
  void handleEnduranceTrioExceptionShouldReturnInternalServerErrorForUnknownCode() {
    EnduranceTrioException exception = new EnduranceTrioException(
        new ErrorDTO(999, "Unknown", "Unknown error"));

    when(messageService.getMessage(eq("page.error.500.metadata.title"), any(), any())).thenReturn(
        "Server Error");
    when(messageService.getMessage(eq("page.error.500.metadata.description"), any(),
        any()
    )).thenReturn(
        "Error occurred");

    ModelAndView result = underTest.handleEnduranceTrioException(exception, request, cachedResponse);

    assertNotNull(result);
    assertEquals("error/500", result.getViewName());
    verify(cachedResponse).resetBuffer();
  }

  @Test
  void handleEnduranceTrioExceptionShouldIncludeErrorsInModel() {
    ErrorDTO error = new ErrorDTO(EnduranceTrioError.BAD_REQUEST, "invalid field");
    EnduranceTrioException exception = new EnduranceTrioException(error);

    when(messageService.getMessage(eq("page.error.400.metadata.title"), any(), any())).thenReturn("Bad Request");
    when(messageService.getMessage(eq("page.error.400.metadata.description"), any(),
        any()
    )).thenReturn(
        "Error");

    ModelAndView result = underTest.handleEnduranceTrioException(exception, request, cachedResponse);

    assertNotNull(result);
    assertEquals("error/400", result.getViewName());
    assertNotNull(result.getModel().get("errors"));
    verify(cachedResponse).resetBuffer();
  }

  @SuppressWarnings("ConstantConditions")
  @Test
  void handleTypeMismatchShouldReturnBadRequest() {
    MethodArgumentTypeMismatchException ex = new MethodArgumentTypeMismatchException("abc", Integer.class, "year", null,
        null
    );

    when(messageService.getMessage(eq("page.error.400.message"), any(), any())).thenReturn("Bad request parameters");
    when(messageService.getMessage(eq("page.error.400.metadata.title"), any(), any())).thenReturn("Bad Request");
    when(messageService.getMessage(eq("page.error.400.metadata.description"), any(), any())).thenReturn(
        "Error occurred");

    ModelAndView result = underTest.handleTypeMismatch(ex, request, cachedResponse);

    assertNotNull(result);
    assertEquals("error/400", result.getViewName());
    assertEquals(HttpStatus.BAD_REQUEST, result.getStatus());
    assertEquals("Bad request parameters", result.getModel().get("message"));
    verify(cachedResponse).resetBuffer();
  }

  @Test
  void handleDataIntegrityShouldReturnConflict() {
    DataIntegrityViolationException ex = new DataIntegrityViolationException(
        "constraint violation");

    when(messageService.getMessage(eq("page.error.500.metadata.title"), any(), any())).thenReturn(
        "Server Error");
    when(messageService.getMessage(eq("page.error.500.metadata.description"), any(),
        any()
    )).thenReturn(
        "Error");

    ModelAndView result = underTest.handleDataIntegrity(ex, request, cachedResponse);

    assertNotNull(result);
    assertEquals("error/500", result.getViewName());
    assertEquals(HttpStatus.CONFLICT.value(), result.getModel().get("status"));
    assertEquals("A conflict occurred with the current state of the resource",
        result.getModel().get("message")
    );
    verify(cachedResponse).resetBuffer();
  }

  @Test
  void handleUnhandledExceptionShouldReturnInternalServerError() {
    Exception ex = new RuntimeException("Unexpected error");

    when(messageService.getMessage(eq("page.error.500.metadata.title"), any(), any())).thenReturn(
        "Server Error");
    when(messageService.getMessage(eq("page.error.500.metadata.description"), any(),
        any()
    )).thenReturn(
        "Error");

    ModelAndView result = underTest.handleUnhandledException(ex, request, cachedResponse);

    assertNotNull(result);
    assertEquals("error/500", result.getViewName());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), result.getModel().get("status"));
    assertEquals("An internal server error occurred", result.getModel().get("message"));
    verify(cachedResponse).resetBuffer();
  }

  @Test
  void shouldNotResetBufferWhenResponseIsNotCachingWrapper() {
    Exception ex = new RuntimeException("Unexpected error");
    HttpServletResponse plainResponse = mock(HttpServletResponse.class);

    when(messageService.getMessage(eq("page.error.500.metadata.title"), any(), any())).thenReturn("Server Error");
    when(messageService.getMessage(eq("page.error.500.metadata.description"), any(), any())).thenReturn("Error");

    underTest.handleUnhandledException(ex, request, plainResponse);

    verify(plainResponse, never()).resetBuffer();
  }

  @Test
  void shouldResetBufferThroughNestedResponseWrappers() {
    Exception ex = new RuntimeException("Unexpected error");
    HttpServletResponseWrapper outerResponse = new HttpServletResponseWrapper(cachedResponse);

    when(messageService.getMessage(eq("page.error.500.metadata.title"), any(), any())).thenReturn("Server Error");
    when(messageService.getMessage(eq("page.error.500.metadata.description"), any(), any())).thenReturn("Error");

    underTest.handleUnhandledException(ex, request, outerResponse);

    verify(cachedResponse).resetBuffer();
  }
}
