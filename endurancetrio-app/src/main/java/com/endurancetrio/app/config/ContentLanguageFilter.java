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
import jakarta.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Sets the {@code Content-Language} response header from the locale path segment so that
 * browsers (notably Firefox) honour the declared page language instead of guessing it from
 * the page content.
 */
public class ContentLanguageFilter implements Filter {

  private static final String HEADER_CONTENT_LANGUAGE = "Content-Language";

  private static final String HEADER_CONTENT_TYPE = "Content-Type";

  private static final String MEDIA_TYPE_HTML = "text/html";

  private static final Pattern LOCALE_PATH = Pattern.compile("^/(en|pt)(/|$)");

  private static final Set<String> BYPASS_PREFIXES = Set.of("/css/", "/js/", "/img/", "/webjars/", "/api/",
      "/v3/api-docs/", "/swagger-ui/"
  );

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;
    String uri = httpRequest.getRequestURI();
    ServletResponse filteredResponse = response;

    if (BYPASS_PREFIXES.stream().noneMatch(uri::startsWith)) {
      Matcher matcher = LOCALE_PATH.matcher(uri);
      if (matcher.find()) {
        filteredResponse = new ContentLanguageResponseWrapper(httpResponse, matcher.group(1));
      }
    }

    chain.doFilter(request, filteredResponse);
  }

  private static class ContentLanguageResponseWrapper extends HttpServletResponseWrapper {

    private final String language;

    ContentLanguageResponseWrapper(HttpServletResponse response, String language) {
      super(response);
      this.language = language;
    }

    @Override
    public void addHeader(String name, String value) {
      super.addHeader(name, value);
      tagIfHtml(name, value);
    }

    @Override
    public void setContentType(String type) {
      super.setContentType(type);
      tagIfHtml(HEADER_CONTENT_TYPE, type);
    }

    @Override
    public void setHeader(String name, String value) {
      super.setHeader(name, value);
      tagIfHtml(name, value);
    }

    private void tagIfHtml(String name, String value) {
      if (HEADER_CONTENT_TYPE.equalsIgnoreCase(name) && value != null && value.toLowerCase(Locale.ROOT)
          .startsWith(MEDIA_TYPE_HTML)) {
        super.setHeader(HEADER_CONTENT_LANGUAGE, language);
      }
    }
  }
}
