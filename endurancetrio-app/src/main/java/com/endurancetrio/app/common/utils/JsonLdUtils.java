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
import com.endurancetrio.business.insight.dto.ArticleDTO;
import tools.jackson.databind.json.JsonMapper;

/**
 * The {@link JsonLdUtils} class provides utility methods for building JSON-LD structured data
 * payloads rendered inside HTML {@code <script type="application/ld+json">} elements by shared
 * template fragments.
 */
public final class JsonLdUtils {

  private static final JsonMapper JSON_MAPPER = JsonMapper.builder().build();

  private JsonLdUtils() {
    throw new IllegalStateException("Utility Class");
  }

  /**
   * Builds the schema.org {@code Article} JSON-LD payload for the given article, ready to be
   * embedded in an {@code <script type="application/ld+json">} element. The {@code datePublished}
   * property is omitted when the article has no published date, since an empty value would fail
   * structured-data validation.
   *
   * @param article  the article to describe
   * @param metadata the page metadata providing the canonical URL and default Open Graph image
   * @param baseUrl  the application base URL used to resolve relative featured image URLs
   * @return a serialized JSON-LD string describing the article
   */
  public static String buildArticleJsonLd(ArticleDTO article, PageMetadata metadata, String baseUrl) {
    boolean hasMetaDescription = article.metaDescription() != null && !article.metaDescription().isBlank();
    boolean hasFeaturedImage = article.featuredImage() != null && !article.featuredImage().isBlank();

    var node = JSON_MAPPER.createObjectNode();
    node.put("@context", "https://schema.org");
    node.put("@type", "Article");
    node.put("headline", article.title());
    node.put("description", hasMetaDescription ? article.metaDescription() : article.introText());
    node.put("url", metadata.getCanonicalUrl());
    node.put("inLanguage", article.locale());
    node.putObject("author").put("@type", "Person").put("name", article.authorName());
    if (article.publishedDate() != null) {
      node.put("datePublished", article.publishedDate().toLocalDate().toString());
    }
    node.put("image", hasFeaturedImage ? baseUrl + article.featuredImage() : metadata.getOgImage());

    return JSON_MAPPER.writeValueAsString(node);
  }
}
