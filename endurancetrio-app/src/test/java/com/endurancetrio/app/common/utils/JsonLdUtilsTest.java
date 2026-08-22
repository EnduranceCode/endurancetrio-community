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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.endurancetrio.app.common.model.PageMetadata;
import com.endurancetrio.app.insight.fixtures.ArticleDTOFixtures;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

class JsonLdUtilsTest {

  private static final String BASE_URL = "http://localhost:8080";

  @Test
  void buildArticleJsonLdShouldSerializeEscapedQuotesApostrophesAndHtml() {
    JsonNode json = parse(JsonLdUtils.buildArticleJsonLd(
        ArticleDTOFixtures.withSpecialCharacters(), createMetadata(), BASE_URL));

    assertEquals("https://schema.org", json.get("@context").asString());
    assertEquals("Article", json.get("@type").asString());
    assertEquals(ArticleDTOFixtures.SPECIAL_TITLE, json.get("headline").asString());
    assertEquals(ArticleDTOFixtures.SPECIAL_INTRO_TEXT, json.get("description").asString());
    assertEquals(BASE_URL + "/en/insights/" + ArticleDTOFixtures.STANDARD_SLUG,
        json.get("url").asString());
    assertEquals("en", json.get("inLanguage").asString());
    assertEquals("Person", json.get("author").get("@type").asString());
    assertEquals(ArticleDTOFixtures.SPECIAL_AUTHOR_NAME, json.get("author").get("name").asString());
    assertEquals("2024-06-15", json.get("datePublished").asString());
  }

  @Test
  void buildArticleJsonLdShouldPrefixBaseUrlToFeaturedImage() {
    JsonNode json = parse(JsonLdUtils.buildArticleJsonLd(
        ArticleDTOFixtures.withFeaturedImage(), createMetadata(), BASE_URL));

    assertEquals(BASE_URL + ArticleDTOFixtures.FEATURED_IMAGE, json.get("image").asString());
    assertEquals("2024-06-15", json.get("datePublished").asString());
  }

  @Test
  void buildArticleJsonLdShouldUseMetaDescriptionWhenPresent() {
    JsonNode json = parse(JsonLdUtils.buildArticleJsonLd(
        ArticleDTOFixtures.withMetaTitle(), createMetadata(), BASE_URL));

    assertEquals(ArticleDTOFixtures.META_DESCRIPTION, json.get("description").asString());
  }

  @Test
  void buildArticleJsonLdShouldFallBackToIntroTextWhenMetaDescriptionIsBlank() {
    JsonNode json = parse(JsonLdUtils.buildArticleJsonLd(
        ArticleDTOFixtures.withBlankMetaDescription(), createMetadata(), BASE_URL));

    assertEquals(ArticleDTOFixtures.STANDARD_INTRO_TEXT, json.get("description").asString());
  }

  @Test
  void buildArticleJsonLdShouldOmitDatePublishedWhenNullAndFallBackToDefaultOgImage() {
    String jsonLd = JsonLdUtils.buildArticleJsonLd(
        ArticleDTOFixtures.withoutPublishedDate(), createMetadata(), BASE_URL);
    JsonNode json = parse(jsonLd);

    assertFalse(json.has("datePublished"));
    assertEquals(BASE_URL + "/img/endurancetrio-open-graph.png", json.get("image").asString());
  }

  @Test
  void buildArticleJsonLdShouldFallBackToDefaultOgImageWhenFeaturedImageIsBlank() {
    JsonNode json = parse(JsonLdUtils.buildArticleJsonLd(
        ArticleDTOFixtures.withBlankFeaturedImage(), createMetadata(), BASE_URL));

    assertEquals(BASE_URL + "/img/endurancetrio-open-graph.png", json.get("image").asString());
  }

  private PageMetadata createMetadata() {
    PageMetadata metadata = new PageMetadata();
    metadata.setCanonicalUrl(BASE_URL + "/en/insights/race-analysis-2024");
    metadata.setOgImage(BASE_URL + "/img/endurancetrio-open-graph.png");
    return metadata;
  }

  private JsonNode parse(String jsonLd) {
    return JsonMapper.builder().build().readTree(jsonLd);
  }
}
