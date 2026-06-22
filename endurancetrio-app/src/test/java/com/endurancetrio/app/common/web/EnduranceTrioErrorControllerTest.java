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
 * EVEN IF WE HAVE BEEN INFORMED OF THEIR POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.app.common.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.config.AppProperties;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.servlet.ModelAndView;

@ExtendWith(MockitoExtension.class)
class EnduranceTrioErrorControllerTest {

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

  private EnduranceTrioErrorController underTest;

  private HttpServletRequest request;

  @BeforeEach
  void setUp() {
    underTest = new EnduranceTrioErrorController(messageService, appProperties);
    request = mock(HttpServletRequest.class);

    when(appProperties.getOpenGraph()).thenReturn(openGraph);
    when(appProperties.getSocial()).thenReturn(social);
    when(appProperties.getKoFi()).thenReturn(kofi);
    when(appProperties.getGoogle()).thenReturn(google);
    when(appProperties.getCopyrightYear()).thenReturn("2026");
    when(appProperties.getVersion()).thenReturn("0.4.0");
    when(openGraph.getDefaultImg()).thenReturn("/img/endurancetrio-open-graph.png");
    when(openGraph.getDefaultImgWidth()).thenReturn(1200);
    when(openGraph.getDefaultImgHeight()).thenReturn(628);
    when(request.getRequestURL()).thenReturn(new StringBuffer("http://localhost:8080/error"));
    when(request.getRequestURI()).thenReturn("/error");
  }

  @Test
  void handleErrorShouldReturn404Page() {
    when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(404);
    when(request.getAttribute(RequestDispatcher.ERROR_MESSAGE)).thenReturn("Not Found");
    when(request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI)).thenReturn(
        "/en/events/unknown");

    when(messageService.getMessage(eq("page.error.404.metadata.title"), any(), any())).thenReturn(
        "Page Not Found");
    when(messageService.getMessage(eq("page.error.404.metadata.description"), any(),
        any()
    )).thenReturn("Not found");

    ModelAndView result = underTest.handleError(request);

    assertNotNull(result);
    assertEquals("error/404", result.getViewName());
    assertEquals(404, result.getModel().get("status"));
    assertEquals("Not Found", result.getModel().get("message"));
    assertEquals("en", result.getModel().get("language"));
  }

  @Test
  void handleErrorShouldReturn403Page() {
    when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(403);
    when(request.getAttribute(RequestDispatcher.ERROR_MESSAGE)).thenReturn("Forbidden");
    when(request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI)).thenReturn("/pt/events/admin");

    when(messageService.getMessage(eq("page.error.403.metadata.title"), any(), any())).thenReturn(
        "Forbidden");
    when(messageService.getMessage(eq("page.error.403.metadata.description"), any(),
        any()
    )).thenReturn("Access denied");

    ModelAndView result = underTest.handleError(request);

    assertNotNull(result);
    assertEquals("error/403", result.getViewName());
    assertEquals(403, result.getModel().get("status"));
    assertEquals("pt", result.getModel().get("language"));
  }

  @Test
  void handleErrorShouldReturn500PageForServerError() {
    when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(500);
    when(request.getAttribute(RequestDispatcher.ERROR_MESSAGE)).thenReturn("Internal error");
    when(request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI)).thenReturn("/en/events");

    when(messageService.getMessage(eq("page.error.500.metadata.title"), any(), any())).thenReturn(
        "Server Error");
    when(messageService.getMessage(eq("page.error.500.metadata.description"), any(),
        any()
    )).thenReturn("Error occurred");

    ModelAndView result = underTest.handleError(request);

    assertNotNull(result);
    assertEquals("error/500", result.getViewName());
    assertEquals(500, result.getModel().get("status"));
  }

  @Test
  void handleErrorShouldDefaultTo500WhenNoStatusCode() {
    when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(null);
    when(request.getAttribute(RequestDispatcher.ERROR_MESSAGE)).thenReturn(null);
    when(request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI)).thenReturn("/en/events");

    when(messageService.getMessage(eq("page.error.500.metadata.title"), any(), any())).thenReturn(
        "Server Error");
    when(messageService.getMessage(eq("page.error.500.metadata.description"), any(),
        any()
    )).thenReturn("Error");

    ModelAndView result = underTest.handleError(request);

    assertNotNull(result);
    assertEquals("error/500", result.getViewName());
    assertEquals(500, result.getModel().get("status"));
    assertEquals("An internal server error occurred", result.getModel().get("message"));
  }

  @Test
  void handleErrorShouldUseDefaultMessageWhenNull() {
    when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(500);
    when(request.getAttribute(RequestDispatcher.ERROR_MESSAGE)).thenReturn(null);
    when(request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI)).thenReturn("/en/events");

    when(messageService.getMessage(eq("page.error.500.metadata.title"), any(), any())).thenReturn(
        "Server Error");
    when(messageService.getMessage(eq("page.error.500.metadata.description"), any(),
        any()
    )).thenReturn("Error");

    ModelAndView result = underTest.handleError(request);

    assertNotNull(result);
    assertEquals("An internal server error occurred", result.getModel().get("message"));
  }

  @Test
  void handleErrorShouldDefaultTo500WhenUnresolvableStatusCode() {
    when(request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE)).thenReturn(999);
    when(request.getAttribute(RequestDispatcher.ERROR_MESSAGE)).thenReturn("Weird error");
    when(request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI)).thenReturn("/en/events");

    when(messageService.getMessage(eq("page.error.500.metadata.title"), any(), any())).thenReturn(
        "Server Error");
    when(messageService.getMessage(eq("page.error.500.metadata.description"), any(),
        any()
    )).thenReturn("Error");

    ModelAndView result = underTest.handleError(request);

    assertNotNull(result);
    assertEquals("error/500", result.getViewName());
    assertEquals(500, result.getModel().get("status"));
  }
}
