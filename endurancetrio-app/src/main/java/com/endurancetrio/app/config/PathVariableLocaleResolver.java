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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.LocaleResolver;

public class PathVariableLocaleResolver implements LocaleResolver {

  private static final Locale DEFAULT_LOCALE = Locale.ENGLISH;

  @Override
  @NonNull
  public Locale resolveLocale(HttpServletRequest request) {
    String uri = request.getRequestURI();
    String[] parts = uri.split("/");

    if (parts.length > 1) {
      String localePart = parts[1];
      if ("pt".equalsIgnoreCase(localePart)) {
        return new Locale("pt", "PT");
      }
    }

    String acceptLang = request.getHeader("Accept-Language");
    if (acceptLang != null && acceptLang.startsWith("pt")) {
      return new Locale("pt", "PT");
    }

    return DEFAULT_LOCALE;
  }

  @Override
  public void setLocale(@NonNull HttpServletRequest request, HttpServletResponse response, Locale locale) {
    // Not needed for this implementation
  }
}
