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

package com.endurancetrio.app.common.web;

import static com.endurancetrio.app.common.constants.ControllerConstants.MSG_SERVER_ERROR;

import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.common.utils.ErrorPageUtils;
import com.endurancetrio.app.config.AppProperties;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * The {@link EnduranceTrioErrorController} handles requests forwarded by the servlet container to
 * the {@code /error} path after an unhandled exception or servlet-level error.
 * <p>
 * This controller ensures that all error pages rendered by the application use the same Thymeleaf
 * layout (head, navbar, footer) as regular content pages.
 */
@Controller
public class EnduranceTrioErrorController implements ErrorController {

  private static final String ERROR_LOG_TEMPLATE = "Error on {} ({}): {}";

  private static final Logger LOG = LoggerFactory.getLogger(EnduranceTrioErrorController.class);

  private final MessageService messageService;
  private final AppProperties appProperties;

  @Autowired
  public EnduranceTrioErrorController(MessageService messageService, AppProperties appProperties) {
    this.messageService = messageService;
    this.appProperties = appProperties;
  }

  /**
   * Handles requests to the {@code /error} path, populating the model with error attributes from
   * the servlet container's error dispatch.
   *
   * @param request the current HTTP request containing error attributes
   * @return a {@link ModelAndView} with the error page
   */
  @SuppressWarnings("java:S3752")
  @RequestMapping(value = "/error", method = {RequestMethod.GET, RequestMethod.POST})
  public ModelAndView handleError(@NonNull HttpServletRequest request) {
    Object statusAttr = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    int statusCode =
        statusAttr != null ? (Integer) statusAttr : HttpStatus.INTERNAL_SERVER_ERROR.value();

    Object messageAttr = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
    String message = messageAttr != null ? (String) messageAttr : MSG_SERVER_ERROR;

    HttpStatus status = HttpStatus.resolve(statusCode);
    if (status == null) {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    Object requestUri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
    if (status.is5xxServerError()) {
      Object exceptionAttr = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
      if (exceptionAttr instanceof Exception ex) {
        LOG.error(ERROR_LOG_TEMPLATE, requestUri, statusCode, message, ex);
      } else {
        LOG.error(ERROR_LOG_TEMPLATE, requestUri, statusCode, message);
      }
    } else {
      LOG.warn(ERROR_LOG_TEMPLATE, requestUri, statusCode, message);
    }

    return ErrorPageUtils.buildErrorModelAndView(status, message, null, request,
        messageService, appProperties
    );
  }
}
