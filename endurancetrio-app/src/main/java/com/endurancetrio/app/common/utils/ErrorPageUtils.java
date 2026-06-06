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

import static com.endurancetrio.app.common.constants.AppConstants.LANGUAGE;
import static com.endurancetrio.app.common.constants.AppConstants.METADATA;
import static com.endurancetrio.app.common.constants.AppConstants.LOCALE_PORTUGUESE;

import com.endurancetrio.app.common.model.PageMetadata;
import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.business.common.dto.ErrorDTO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * Utility class with shared methods for building and rendering error pages.
 * <p>
 * Centralizes logic that is common between {@code EnduranceTrioErrorController} and
 * {@code EnduranceTrioExceptionHandlerWeb} to avoid code duplication.
 */
public final class ErrorPageUtils {

  private static final String ERROR_VIEW_PREFIX = "error/";

  private ErrorPageUtils() {
    throw new IllegalStateException("Utility Class");
  }

  /**
   * Resolves an HTTP status code to the corresponding error view name.
   *
   * @param statusCode the HTTP status code (e.g. 403, 404, 500)
   * @return the Thymeleaf view name for the error page (e.g. {@code error/403})
   */
  public static String errorView(int statusCode) {
    return switch (statusCode) {
      case 403 -> ERROR_VIEW_PREFIX + "403";
      case 404 -> ERROR_VIEW_PREFIX + "404";
      default -> ERROR_VIEW_PREFIX + "500";
    };
  }

  /**
   * Creates a {@link PageMetadata} object for an error page using the appropriate i18n message keys
   * based on the HTTP status code.
   *
   * @param statusCode     the HTTP status code (e.g. 403, 404, 500)
   * @param request        the current HTTP request, used for URL construction
   * @param locale         the resolved locale for i18n messages
   * @param messageService the service used to retrieve i18n messages
   * @param appProperties  the application properties for metadata configuration
   * @return a fully populated {@link PageMetadata} instance
   */
  public static PageMetadata createErrorPageMetadata(
      int statusCode, @NonNull HttpServletRequest request, @NonNull Locale locale,
      @NonNull MessageService messageService, @NonNull AppProperties appProperties
  ) {
    String title;
    String description;
    switch (statusCode) {
      case 403 -> {
        title = messageService.getMessage("page.error.403.metadata.title", null, locale);
        description = messageService.getMessage("page.error.403.metadata.description", null,
            locale
        );
      }
      case 404 -> {
        title = messageService.getMessage("page.error.404.metadata.title", null, locale);
        description = messageService.getMessage("page.error.404.metadata.description", null,
            locale
        );
      }
      default -> {
        title = messageService.getMessage("page.error.500.metadata.title", null, locale);
        description = messageService.getMessage("page.error.500.metadata.description", null,
            locale
        );
      }
    }
    return PageMetadataUtils.create("error", title, description, request, appProperties);
  }

  /**
   * Builds a complete {@link ModelAndView} for an error page. Resolves the locale, creates the page
   * metadata, and populates the model with standard error attributes.
   *
   * @param status         the HTTP status of the response
   * @param message        the human-readable error message
   * @param errors         an optional list of structured {@link ErrorDTO} objects, may be null
   * @param request        the current HTTP request
   * @param messageService the service used to retrieve i18n messages
   * @param appProperties  the application properties for metadata configuration
   * @return a fully initialised {@link ModelAndView} ready for rendering
   */
  public static ModelAndView buildErrorModelAndView(
      @NonNull HttpStatus status, @NonNull String message, List<ErrorDTO> errors,
      @NonNull HttpServletRequest request, @NonNull MessageService messageService,
      @NonNull AppProperties appProperties
  ) {
    Locale locale = resolveLocale(request);
    String language = locale.getLanguage();

    PageMetadata metadata = createErrorPageMetadata(status.value(), request, locale, messageService,
        appProperties
    );

    ModelAndView mav = new ModelAndView(errorView(status.value()));
    mav.addObject(LANGUAGE, language);
    mav.addObject(METADATA, metadata);
    mav.addObject("status", status.value());
    mav.addObject("reason", status.getReasonPhrase());
    mav.addObject("message", message);
    if (errors != null && !errors.isEmpty()) {
      mav.addObject("errors", errors);
    }
    return mav;
  }

  /**
   * Resolves the request locale by extracting the language prefix from the URL path. Supports
   * {@code pt} (Portuguese) and falls back to English for any other value.
   * <p>
   * When dispatched via the servlet error mechanism, the original request URI is preferred over the
   * forwarded {@code /error} path.
   *
   * @param request the current HTTP request
   * @return the resolved {@link Locale} (Portuguese or English)
   */
  private static Locale resolveLocale(@NonNull HttpServletRequest request) {
    String requestUri = (String) request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
    if (requestUri == null) {
      requestUri = request.getRequestURI();
    }
    String contextPath = request.getContextPath();
    if (contextPath != null && !contextPath.isEmpty()) {
      requestUri = requestUri.substring(contextPath.length());
    }
    if (requestUri.startsWith("/")) {
      requestUri = requestUri.substring(1);
    }
    int slashIndex = requestUri.indexOf('/');
    String language = slashIndex > 0 ? requestUri.substring(0, slashIndex) : requestUri;

    if ("pt".equalsIgnoreCase(language)) {
      return LOCALE_PORTUGUESE;
    }
    return Locale.ENGLISH;
  }
}
