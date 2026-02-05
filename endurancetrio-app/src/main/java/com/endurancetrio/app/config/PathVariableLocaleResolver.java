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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Locale;
import org.jspecify.annotations.NonNull;
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
        return Locale.of("pt", "PT");
      }
    }

    String acceptLang = request.getHeader("Accept-Language");
    if (acceptLang != null && acceptLang.startsWith("pt")) {
      return Locale.of("pt", "PT");
    }

    return DEFAULT_LOCALE;
  }

  @Override
  public void setLocale(@NonNull HttpServletRequest request, HttpServletResponse response, Locale locale) {
    // Not needed for this implementation
  }
}
