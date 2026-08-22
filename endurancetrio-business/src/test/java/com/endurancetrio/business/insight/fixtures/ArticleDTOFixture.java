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

package com.endurancetrio.business.insight.fixtures;

import com.endurancetrio.business.insight.dto.ArticleDTO;

/**
 * Fixture class providing pre-configured {@link ArticleDTO} instances for unit tests.
 */
public class ArticleDTOFixture {

  public static final String FEATURED_IMAGE = "/img/insights/insights-001.jpg";
  public static final Integer FEATURED_IMAGE_WIDTH = 1200;
  public static final Integer FEATURED_IMAGE_HEIGHT = 628;

  public static final String META_TITLE = "Race Analysis 2024 - EnduranceTrio Insights";

  public static final String SPECIAL_TITLE = "How a \"poor\" country shaped my journey";
  public static final String SPECIAL_INTRO_TEXT = "<p>Intro with \"quotes\" & <b>html</b></p>";
  public static final String SPECIAL_AUTHOR_NAME = "Author O'Brien";

  private ArticleDTOFixture() {
  }

  /**
   * Creates a standard {@link ArticleDTO} with default test values.
   *
   * @return a standard ArticleDTO instance
   */
  public static ArticleDTO standard() {
    return new ArticleDTO(ArticleFixture.STANDARD_ID, ArticleFixture.STANDARD_SLUG,
        ArticleContentFixture.STANDARD_TITLE, null, ArticleContentFixture.STANDARD_INTRO_TEXT, null,
        AuthorFixture.STANDARD_KNOWN_NAME, ArticleFixture.STANDARD_PUBLISHED_DATE, null, null, null,
        null, null, ArticleContentFixture.STANDARD_LOCALE
    );
  }

  /**
   * Creates an {@link ArticleDTO} with the given ID and default test values.
   *
   * @param id the ID to assign to the DTO
   * @return an ArticleDTO instance with the given ID
   */
  public static ArticleDTO withId(Long id) {
    return new ArticleDTO(id, ArticleFixture.STANDARD_SLUG,
        ArticleContentFixture.STANDARD_TITLE, null, ArticleContentFixture.STANDARD_INTRO_TEXT, null,
        AuthorFixture.STANDARD_KNOWN_NAME, ArticleFixture.STANDARD_PUBLISHED_DATE, null, null, null,
        null, null, ArticleContentFixture.STANDARD_LOCALE
    );
  }

  /**
   * Creates an {@link ArticleDTO} with a featured image and its dimensions.
   *
   * @return an ArticleDTO instance with a featured image
   */
  public static ArticleDTO withFeaturedImage() {
    return new ArticleDTO(ArticleFixture.STANDARD_ID, ArticleFixture.STANDARD_SLUG,
        ArticleContentFixture.STANDARD_TITLE, null, ArticleContentFixture.STANDARD_INTRO_TEXT, null,
        AuthorFixture.STANDARD_KNOWN_NAME, ArticleFixture.STANDARD_PUBLISHED_DATE,
        FEATURED_IMAGE, FEATURED_IMAGE_WIDTH, FEATURED_IMAGE_HEIGHT, null, null,
        ArticleContentFixture.STANDARD_LOCALE
    );
  }

  /**
   * Creates an {@link ArticleDTO} without a published date.
   *
   * @return an ArticleDTO instance without a published date
   */
  public static ArticleDTO withoutPublishedDate() {
    return new ArticleDTO(ArticleFixture.STANDARD_ID, ArticleFixture.STANDARD_SLUG,
        ArticleContentFixture.STANDARD_TITLE, null, ArticleContentFixture.STANDARD_INTRO_TEXT, null,
        AuthorFixture.STANDARD_KNOWN_NAME, null, null, null, null, null, null,
        ArticleContentFixture.STANDARD_LOCALE
    );
  }

  /**
   * Creates an {@link ArticleDTO} with a meta title and a blank meta description.
   *
   * @return an ArticleDTO instance with a blank meta description
   */
  public static ArticleDTO withBlankMetaDescription() {
    return new ArticleDTO(ArticleFixture.STANDARD_ID, ArticleFixture.STANDARD_SLUG,
        ArticleContentFixture.STANDARD_TITLE, null, ArticleContentFixture.STANDARD_INTRO_TEXT, null,
        AuthorFixture.STANDARD_KNOWN_NAME, ArticleFixture.STANDARD_PUBLISHED_DATE, null, null, null,
        META_TITLE, "   ", ArticleContentFixture.STANDARD_LOCALE
    );
  }

  /**
   * Creates an {@link ArticleDTO} with a blank featured image.
   *
   * @return an ArticleDTO instance with a blank featured image
   */
  public static ArticleDTO withBlankFeaturedImage() {
    return new ArticleDTO(ArticleFixture.STANDARD_ID, ArticleFixture.STANDARD_SLUG,
        ArticleContentFixture.STANDARD_TITLE, null, ArticleContentFixture.STANDARD_INTRO_TEXT, null,
        AuthorFixture.STANDARD_KNOWN_NAME, ArticleFixture.STANDARD_PUBLISHED_DATE, "", null, null,
        null, null, ArticleContentFixture.STANDARD_LOCALE
    );
  }

  /**
   * Creates an {@link ArticleDTO} whose text fields contain double quotes, apostrophes and HTML.
   *
   * @return an ArticleDTO instance with special characters
   */
  public static ArticleDTO withSpecialCharacters() {
    return new ArticleDTO(ArticleFixture.STANDARD_ID, ArticleFixture.STANDARD_SLUG,
        SPECIAL_TITLE, null, SPECIAL_INTRO_TEXT, null,
        SPECIAL_AUTHOR_NAME, ArticleFixture.STANDARD_PUBLISHED_DATE, null, null, null,
        null, null, ArticleContentFixture.STANDARD_LOCALE
    );
  }
}
