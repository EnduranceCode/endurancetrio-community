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

package com.endurancetrio.business.common.dto;

/**
 * {@link SitemapEntry} represents a single {@code <url>} element of the generated
 * {@code /sitemap.xml}, pairing the English and Portuguese variants of the same page so the
 * sitemap template can emit hreflang annotations.
 *
 * @param urlEn        the absolute URL of the English variant (must be non-null and non-blank)
 * @param urlPt        the absolute URL of the Portuguese variant (must be non-null and non-blank)
 * @param lastModified the ISO-8601 date the page was last modified, or {@code null} when unknown
 * @param priority     the sitemap priority of the page, between 0.0 and 1.0
 */
public record SitemapEntry(String urlEn, String urlPt, String lastModified, double priority) {

  public SitemapEntry {
    if (urlEn == null || urlEn.isBlank()) {
      throw new IllegalArgumentException("urlEn must not be null or blank");
    }
    if (urlPt == null || urlPt.isBlank()) {
      throw new IllegalArgumentException("urlPt must not be null or blank");
    }
  }
}
