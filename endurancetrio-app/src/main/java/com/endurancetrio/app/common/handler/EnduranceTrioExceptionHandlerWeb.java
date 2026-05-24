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

import static com.endurancetrio.app.common.constants.ControllerConstants.MSG_SERVER_ERROR;
import static com.endurancetrio.app.common.constants.ControllerConstants.STATUS_500;

import com.endurancetrio.app.common.annotation.EnduranceTrioWebController;
import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.common.utils.ErrorPageUtils;
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.business.common.exception.EnduranceTrioError;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * The {@link EnduranceTrioExceptionHandlerWeb} class is a global exception handler for Thymeleaf
 * web controllers in the EnduranceTrio application.
 * <p>
 * This class intercepts exceptions thrown from controllers annotated with
 * {@link EnduranceTrioWebController} and renders an HTML error page with consistent layout (head,
 * navbar, footer) and error information.
 */
@ControllerAdvice(annotations = EnduranceTrioWebController.class)
public class EnduranceTrioExceptionHandlerWeb {

  private static final Logger LOG = LoggerFactory.getLogger(EnduranceTrioExceptionHandlerWeb.class);

  private final MessageService messageService;
  private final AppProperties appProperties;

  @Autowired
  public EnduranceTrioExceptionHandlerWeb(
      MessageService messageService,
      AppProperties appProperties
  ) {
    this.messageService = messageService;
    this.appProperties = appProperties;
  }

  /**
   * Handles business logic exceptions defined within the EnduranceTrio domain. The HTTP status code
   * is resolved from the exception's error code.
   *
   * @param exception the domain exception containing error codes and messages
   * @param request   the current HTTP request
   * @return a {@link ModelAndView} with the error page
   */
  @ExceptionHandler(EnduranceTrioException.class)
  public ModelAndView handleEnduranceTrioException(
      @NonNull EnduranceTrioException exception, @NonNull HttpServletRequest request) {
    HttpStatus status = HttpStatus.resolve(exception.getCode());
    if (status == null) {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    String logMessage = "Handled Exception ({}): {}";
    if (status.is5xxServerError() || exception.getCause() != null) {
      LOG.error(logMessage, status.value(), exception.getMessage(), exception);
    } else {
      LOG.warn(logMessage, status.value(), exception.getMessage());
    }

    String responseMessage = EnduranceTrioError.fromCode(exception.getCode())
        .orElse(EnduranceTrioError.INTERNAL_ERROR)
        .getMessage();

    return ErrorPageUtils.buildErrorModelAndView(status, responseMessage, exception.getErrors(), request,
        messageService, appProperties
    );
  }

  /**
   * Intercepts database integrity violations, such as unique constraint conflicts or foreign key
   * violations.
   *
   * @param ex      the data integrity violation exception
   * @param request the current HTTP request
   * @return a {@link ModelAndView} with a conflict error page
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ModelAndView handleDataIntegrity(
      @NonNull DataIntegrityViolationException ex, @NonNull HttpServletRequest request) {
    LOG.warn("Database integrity violation: {}", ex.getMostSpecificCause().getMessage());
    return ErrorPageUtils.buildErrorModelAndView(HttpStatus.CONFLICT, EnduranceTrioError.CONFLICT.getMessage(),
        null, request, messageService, appProperties
    );
  }

  /**
   * Catch-all handler for any unexpected system exceptions. Maps unknown errors to a generic 500
   * Internal Server Error page.
   *
   * @param exception the unexpected exception
   * @param request   the current HTTP request
   * @return a {@link ModelAndView} with a generic error page
   */
  @ExceptionHandler(Exception.class)
  public ModelAndView handleUnhandledException(
      @NonNull Exception exception, @NonNull HttpServletRequest request) {
    LOG.error("Unhandled exception ({}): {}", STATUS_500, exception.getMessage(), exception);
    return ErrorPageUtils.buildErrorModelAndView(HttpStatus.INTERNAL_SERVER_ERROR, MSG_SERVER_ERROR, null,
        request, messageService, appProperties
    );
  }
}
