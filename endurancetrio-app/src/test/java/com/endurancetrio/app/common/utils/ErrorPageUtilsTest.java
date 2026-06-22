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

package com.endurancetrio.app.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.endurancetrio.app.common.model.PageMetadata;
import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.config.AppProperties;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

class ErrorPageUtilsTest {

  private static final String ERROR_VIEW_PREFIX = "error/";
  private static final Locale LOCALE_PORTUGUESE = Locale.of("pt", "PT");

  @Test
  void errorViewShouldReturn403() {
    assertEquals(ERROR_VIEW_PREFIX + "403", ErrorPageUtils.errorView(403));
  }

  @Test
  void errorViewShouldReturn404() {
    assertEquals(ERROR_VIEW_PREFIX + "404", ErrorPageUtils.errorView(404));
  }

  @Test
  void errorViewShouldReturn500ForServerError() {
    assertEquals(ERROR_VIEW_PREFIX + "500", ErrorPageUtils.errorView(500));
  }

  @Test
  void errorViewShouldReturn500ForUnknownStatus() {
    assertEquals(ERROR_VIEW_PREFIX + "500", ErrorPageUtils.errorView(999));
  }

  @Test
  void createErrorPageMetadataShouldReturnMetadataFor403() {
    MessageService messageService = mock(MessageService.class);
    AppProperties appProperties = createAppProperties();
    HttpServletRequest request = createRequest("http://localhost:8080/en/events", "/en/events");

    when(messageService.getMessage("page.error.403.metadata.title", null, Locale.ENGLISH)).thenReturn(
        "Access Denied - EnduranceTrio");
    when(messageService.getMessage("page.error.403.metadata.description", null,
        Locale.ENGLISH
    )).thenReturn("You don't have permission to access this page");

    PageMetadata result = ErrorPageUtils.createErrorPageMetadata(403, request, Locale.ENGLISH,
        messageService, appProperties
    );

    assertNotNull(result);
    assertEquals("Access Denied - EnduranceTrio", result.getTitle());
    assertEquals("You don't have permission to access this page", result.getDescription());
  }

  @Test
  void createErrorPageMetadataShouldReturnMetadataFor404() {
    MessageService messageService = mock(MessageService.class);
    AppProperties appProperties = createAppProperties();
    HttpServletRequest request = createRequest("http://localhost:8080/en/events", "/en/events");

    when(messageService.getMessage("page.error.404.metadata.title", null, Locale.ENGLISH)).thenReturn(
        "Page Not Found - EnduranceTrio");
    when(messageService.getMessage("page.error.404.metadata.description", null,
        Locale.ENGLISH
    )).thenReturn("The requested page was not found");

    PageMetadata result = ErrorPageUtils.createErrorPageMetadata(404, request, Locale.ENGLISH,
        messageService, appProperties
    );

    assertNotNull(result);
    assertEquals("Page Not Found - EnduranceTrio", result.getTitle());
    assertEquals("The requested page was not found", result.getDescription());
  }

  @Test
  void createErrorPageMetadataShouldDefaultTo500() {
    MessageService messageService = mock(MessageService.class);
    AppProperties appProperties = createAppProperties();
    HttpServletRequest request = createRequest("http://localhost:8080/en/events", "/en/events");

    when(messageService.getMessage("page.error.500.metadata.title", null, Locale.ENGLISH)).thenReturn(
        "Server Error - EnduranceTrio");
    when(messageService.getMessage("page.error.500.metadata.description", null,
        Locale.ENGLISH
    )).thenReturn("An internal server error occurred");

    PageMetadata result = ErrorPageUtils.createErrorPageMetadata(500, request, Locale.ENGLISH,
        messageService, appProperties
    );

    assertNotNull(result);
    assertEquals("Server Error - EnduranceTrio", result.getTitle());
    assertEquals("An internal server error occurred", result.getDescription());
  }

  @Test
  void createErrorPageMetadataShouldDefaultTo500ForUnknownCode() {
    MessageService messageService = mock(MessageService.class);
    AppProperties appProperties = createAppProperties();
    HttpServletRequest request = createRequest("http://localhost:8080/en/events", "/en/events");

    when(messageService.getMessage("page.error.500.metadata.title", null, Locale.ENGLISH)).thenReturn(
        "Server Error - EnduranceTrio");
    when(messageService.getMessage("page.error.500.metadata.description", null,
        Locale.ENGLISH
    )).thenReturn("An internal server error occurred");

    PageMetadata result = ErrorPageUtils.createErrorPageMetadata(999, request, Locale.ENGLISH,
        messageService, appProperties
    );

    assertNotNull(result);
    assertEquals("Server Error - EnduranceTrio", result.getTitle());
  }

  @Test
  void buildErrorModelAndViewShouldReturnModelWithStandardAttributes() {
    MessageService messageService = mock(MessageService.class);
    AppProperties appProperties = createAppProperties();
    HttpServletRequest request = createRequest("http://localhost:8080/en/events", "/en/events");

    when(messageService.getMessage("page.error.404.metadata.title", null, Locale.ENGLISH)).thenReturn(
        "Page Not Found - EnduranceTrio");
    when(messageService.getMessage("page.error.404.metadata.description", null,
        Locale.ENGLISH
    )).thenReturn("The requested page was not found");

    ModelAndView result = ErrorPageUtils.buildErrorModelAndView(HttpStatus.NOT_FOUND,
        "Resource not found", null, request, messageService, appProperties
    );

    assertNotNull(result);
    assertEquals("error/404", result.getViewName());
    assertEquals("en", result.getModel().get("language"));
    assertNotNull(result.getModel().get("metadata"));
    assertEquals(404, result.getModel().get("status"));
    assertEquals("Not Found", result.getModel().get("reason"));
    assertEquals("Resource not found", result.getModel().get("message"));
    assertNull(result.getModel().get("errors"));
  }

  @Test
  void buildErrorModelAndViewShouldIncludeErrorsWhenProvided() {
    MessageService messageService = mock(MessageService.class);
    AppProperties appProperties = createAppProperties();
    HttpServletRequest request = createRequest("http://localhost:8080/en/events", "/en/events");
    var errors = List.of(
        new com.endurancetrio.business.common.dto.ErrorDTO(
            com.endurancetrio.business.common.exception.EnduranceTrioError.CONFLICT, "duplicate key"
        )
    );

    when(messageService.getMessage("page.error.500.metadata.title", null, Locale.ENGLISH)).thenReturn(
        "Server Error - EnduranceTrio");
    when(messageService.getMessage("page.error.500.metadata.description", null,
        Locale.ENGLISH
    )).thenReturn("An internal server error occurred");

    ModelAndView result = ErrorPageUtils.buildErrorModelAndView(HttpStatus.CONFLICT, "Conflict",
        errors, request, messageService, appProperties
    );

    assertNotNull(result);
    assertEquals("error/500", result.getViewName());
    assertNotNull(result.getModel().get("errors"));
    assertEquals(errors, result.getModel().get("errors"));
  }

  @Test
  void buildErrorModelAndViewShouldResolveEnglishLocale() {
    MessageService messageService = mock(MessageService.class);
    AppProperties appProperties = createAppProperties();
    HttpServletRequest request = createRequest("http://localhost:8080/en/events", "/en/events");

    when(messageService.getMessage("page.error.500.metadata.title", null, Locale.ENGLISH)).thenReturn(
        "Server Error - EnduranceTrio");
    when(messageService.getMessage("page.error.500.metadata.description", null,
        Locale.ENGLISH
    )).thenReturn("An internal server error occurred");

    ModelAndView result = ErrorPageUtils.buildErrorModelAndView(HttpStatus.INTERNAL_SERVER_ERROR,
        "error", null, request, messageService, appProperties
    );

    assertEquals("en", result.getModel().get("language"));
  }

  @Test
  void buildErrorModelAndViewShouldResolvePortugueseLocaleFromErrorRequestUri() {
    MessageService messageService = mock(MessageService.class);
    AppProperties appProperties = createAppProperties();
    HttpServletRequest request = mock(HttpServletRequest.class);

    when(request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI)).thenReturn("/pt/events/404");
    when(request.getRequestURI()).thenReturn("/error");
    when(request.getContextPath()).thenReturn("");
    when(request.getRequestURL()).thenReturn(
        new StringBuffer("http://localhost:8080/error"));

    when(messageService.getMessage("page.error.500.metadata.title", null, LOCALE_PORTUGUESE)).thenReturn(
        "Erro do Servidor - EnduranceTrio");
    when(messageService.getMessage("page.error.500.metadata.description", null,
        LOCALE_PORTUGUESE
    )).thenReturn("Ocorreu um erro interno do servidor");

    ModelAndView result = ErrorPageUtils.buildErrorModelAndView(HttpStatus.INTERNAL_SERVER_ERROR,
        "erro", null, request, messageService, appProperties
    );

    assertEquals("pt", result.getModel().get("language"));
  }

  @Test
  void buildErrorModelAndViewShouldResolveEnglishWithContextPath() {
    MessageService messageService = mock(MessageService.class);
    AppProperties appProperties = createAppProperties();
    HttpServletRequest request = mock(HttpServletRequest.class);

    when(request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI)).thenReturn("/myapp/en/events");
    when(request.getContextPath()).thenReturn("/myapp");
    when(request.getRequestURI()).thenReturn("/error");
    when(request.getRequestURL()).thenReturn(
        new StringBuffer("http://localhost:8080/error"));

    when(messageService.getMessage("page.error.500.metadata.title", null, Locale.ENGLISH)).thenReturn(
        "Server Error - EnduranceTrio");
    when(messageService.getMessage("page.error.500.metadata.description", null,
        Locale.ENGLISH
    )).thenReturn("Error");

    ModelAndView result = ErrorPageUtils.buildErrorModelAndView(HttpStatus.INTERNAL_SERVER_ERROR,
        "error", null, request, messageService, appProperties
    );

    assertEquals("en", result.getModel().get("language"));
  }

  @Test
  void constructorShouldThrow() {
    assertThrows(InvocationTargetException.class, () -> {
      var constructor = ErrorPageUtils.class.getDeclaredConstructor();
      constructor.setAccessible(true);
      constructor.newInstance();
    });
  }

  private static AppProperties createAppProperties() {
    AppProperties appProperties = new AppProperties();
    appProperties.getOpenGraph().setDefaultImg("/img/endurancetrio-open-graph.png");
    appProperties.getOpenGraph().setDefaultImgWidth(1200);
    appProperties.getOpenGraph().setDefaultImgHeight(628);
    appProperties.getSocial().setFacebookPageId("1692877750958091");
    appProperties.getSocial().setTwitterSite("@EnduranceTrio");
    appProperties.getGoogle().setAdsenseId("ca-pub-test");
    appProperties.getKoFi().setUserId("test-kofi");
    appProperties.setCopyrightYear("2026");
    appProperties.setVersion("0.4.0");
    return appProperties;
  }

  private static HttpServletRequest createRequest(String url, String uri) {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getRequestURL()).thenReturn(new StringBuffer(url));
    when(request.getRequestURI()).thenReturn(uri);
    return request;
  }
}
