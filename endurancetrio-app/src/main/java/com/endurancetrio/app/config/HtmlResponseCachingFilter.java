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

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.web.util.ContentCachingResponseWrapper;

/**
 * Holds HTML page output in memory until rendering completes, then copies it to the real response.
 * Thymeleaf writes into the wrapper while it processes fragments; an error handler can therefore
 * discard a partially rendered page before returning a complete error page, which becomes the only
 * document the browser receives. Non-page resources are passed through untouched.
 */
public class HtmlResponseCachingFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    if (PageRequestUtils.isNonPageRequest(PageRequestUtils.stripPathParameters(httpRequest.getRequestURI()))) {
      chain.doFilter(request, response);
      return;
    }

    ContentCachingResponseWrapper wrappedResponse = new ContentCachingResponseWrapper(httpResponse);
    boolean renderingCompleted = false;
    try {
      chain.doFilter(request, wrappedResponse);
      renderingCompleted = true;
    } finally {
      if (!renderingCompleted) {
        // A failure escaping the chain (typically a Thymeleaf render error) leaves partial markup
        // in the cache. Discard it so the container can write a complete error page to a fresh
        // response.
        wrappedResponse.resetBuffer();
      }
      wrappedResponse.copyBodyToResponse();
    }
  }
}
