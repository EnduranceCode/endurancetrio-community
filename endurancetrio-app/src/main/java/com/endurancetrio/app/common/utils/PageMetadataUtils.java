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

import com.endurancetrio.app.common.model.PageMetadata;
import com.endurancetrio.app.config.AppProperties;
import jakarta.servlet.http.HttpServletRequest;

/**
 * The {@link PageMetadataUtils} class provides utility methods for building
 * {@link com.endurancetrio.app.common.model.PageMetadata PageMetadata} instances across controller
 * classes in the application. The constructed metadata is shared between HTML {@code <head>}
 * elements and other layout fragments (e.g. navigation bar, footer).
 */
public final class PageMetadataUtils {

  private PageMetadataUtils() {
    throw new IllegalStateException("Utility Class");
  }

  /**
   * Extracts the base URL (scheme, host, and port) from the given request.
   *
   * @param request the current HTTP request
   * @return the base URL, e.g. {@code http://localhost:8080}
   */
  public static String getBaseUrl(HttpServletRequest request) {
    String canonicalUrl = request.getRequestURL().toString();
    return canonicalUrl.substring(0, canonicalUrl.indexOf(request.getRequestURI()));
  }

  /**
   * Creates a {@link PageMetadata} instance populated with the given page data and application-wide
   * configuration properties.
   *
   * @param view       the template view name
   * @param title      the page title
   * @param description the page description
   * @param request    the current HTTP request
   * @param properties the application configuration properties
   * @return a fully populated {@link PageMetadata} instance
   */
  public static PageMetadata create(
      String view, String title, String description, HttpServletRequest request,
      AppProperties properties
  ) {
    String appVersion = properties.getVersion();
    String baseUrl = getBaseUrl(request);

    PageMetadata metadata = new PageMetadata();
    metadata.setAppVersion(appVersion == null || appVersion.isBlank() ? "" : appVersion);
    metadata.setCopyrightYear(properties.getCopyrightYear());
    metadata.setDescription(description);
    metadata.setCanonicalUrl(request.getRequestURL().toString());
    metadata.setOgImage(baseUrl + properties.getOpenGraph().getDefaultImg());
    metadata.setFacebookPageId(properties.getSocial().getFacebookPageId());
    metadata.setGoogleAdsenseId(properties.getGoogle().getAdsenseId());
    metadata.setKofiUserId(properties.getKoFi().getUserId());
    metadata.setOgImageHeight(properties.getOpenGraph().getDefaultImgHeight());
    metadata.setOgImageWidth(properties.getOpenGraph().getDefaultImgWidth());
    metadata.setTitle(title);
    metadata.setTwitterSite(properties.getSocial().getTwitterSite());
    metadata.setView(view);

    return metadata;
  }
}
