/*
 * Copyright (c) 2011-2025 Ricardo do Canto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
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

public class LocaleRedirectFilter implements Filter {

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {

    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpServletResponse httpResponse = (HttpServletResponse) response;

    String uri = httpRequest.getRequestURI();
    if (uri.startsWith("/css/") || uri.startsWith("/js/") || uri.startsWith("/img/")) {
      chain.doFilter(request, response);
      return;
    }

    if (!uri.matches("^/(en|pt)(/.*)?$")) {
      String acceptLang = httpRequest.getHeader("Accept-Language");
      String defaultLocale = (acceptLang != null && acceptLang.startsWith("pt")) ? "pt" : "en";
      httpResponse.sendRedirect("/" + defaultLocale + uri);
      return;
    }

    chain.doFilter(request, response);
  }
}
