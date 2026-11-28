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

import java.util.Set;

/**
 * Shared request-URI classification for the servlet filters in this package. URIs addressed here
 * are non-page resources (static assets, API endpoints, actuator endpoints and API documentation)
 * and must not take part in page-specific response handling.
 */
final class PageRequestUtils {

  private static final Set<String> NON_PAGE_ROOT_FILES = Set.of("/favicon.ico", "/favicon.svg", "/apple-touch-icon.png",
      "/robots.txt", "/sitemap.xml"
  );

  private PageRequestUtils() {
  }

  /**
   * Removes path parameters (matrix parameters such as {@code ;jsessionid=...}) from every segment
   * of the given request URI.
   *
   * @param uri the raw request URI
   * @return the URI without path parameters
   */
  static String stripPathParameters(String uri) {
    return uri.indexOf(';') < 0 ? uri : uri.replaceAll(";[^/]*", "");
  }

  /**
   * Determines whether the given URI addresses a static asset, API or other non-page endpoint.
   *
   * @param uri the request URI, with path parameters already stripped
   * @return {@code true} when the URI does not address an HTML page
   */
  static boolean isNonPageRequest(String uri) {
    return uri.startsWith("/css/") || uri.startsWith("/js/") || uri.startsWith("/img/") || uri.startsWith("/webjars/")
        || NON_PAGE_ROOT_FILES.contains(uri) || uri.startsWith("/api/") || uri.startsWith("/actuator")
        || uri.startsWith("/v3/api-docs") || uri.startsWith("/swagger-ui");
  }
}
