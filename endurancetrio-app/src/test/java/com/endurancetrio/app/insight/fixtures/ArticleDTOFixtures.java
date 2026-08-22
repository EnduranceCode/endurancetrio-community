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

package com.endurancetrio.app.insight.fixtures;

import com.endurancetrio.business.insight.dto.ArticleDTO;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * Fixture class providing pre-configured {@link ArticleDTO} instances for unit tests in the
 * {@code endurancetrio-app} module.
 * <p>
 * This class is duplicated in {@code endurancetrio-business} under {@code insight.fixtures} because
 * the modules do not share a test-jar dependency. Each module keeps its own copy of the fixtures it
 * needs, keeping the build simple and avoiding cross-module test-jar complications. If you add or
 * modify a factory method here, apply the same change to the business module's copy.
 */
public class ArticleDTOFixtures {

  public static final Long STANDARD_ID = 1L;
  public static final String STANDARD_SLUG = "race-analysis-2024";
  public static final String STANDARD_LOCALE = "en";
  public static final String STANDARD_TITLE = "Race Analysis 2024";
  public static final String STANDARD_INTRO_TEXT = "This is the introduction.";
  public static final String STANDARD_AUTHOR_NAME = "John Doe";
  public static final LocalDateTime STANDARD_PUBLISHED_DATE = LocalDateTime.of(2024, Month.JUNE, 15,
      10, 0
  );

  public static final String PT_LOCALE = "pt";
  public static final String PT_TITLE = "Análise da Corrida 2024";
  public static final String PT_INTRO_TEXT = "Esta é a introdução.";

  public static final String META_TITLE = "Race Analysis 2024 - EnduranceTrio Insights";
  public static final String META_DESCRIPTION = "An in-depth analysis of the 2024 race season.";

  public static final String FEATURED_IMAGE = "/img/insights/insights-001.jpg";
  public static final Integer FEATURED_IMAGE_WIDTH = 1200;
  public static final Integer FEATURED_IMAGE_HEIGHT = 628;

  public static final String SPECIAL_TITLE = "How a \"poor\" country shaped my journey";
  public static final String SPECIAL_INTRO_TEXT = "<p>Intro with \"quotes\" & <b>html</b></p>";
  public static final String SPECIAL_AUTHOR_NAME = "Author O'Brien";

  private ArticleDTOFixtures() {
  }

  /**
   * Creates a standard {@link ArticleDTO} with default English test values.
   *
   * @return a standard ArticleDTO instance
   */
  public static ArticleDTO standard() {
    return new ArticleDTO(STANDARD_ID, STANDARD_SLUG, STANDARD_TITLE, null, STANDARD_INTRO_TEXT,
        null, STANDARD_AUTHOR_NAME, STANDARD_PUBLISHED_DATE, null, null, null, null, null,
        STANDARD_LOCALE
    );
  }

  /**
   * Creates an {@link ArticleDTO} with a meta title and meta description.
   *
   * @return an ArticleDTO instance with SEO metadata
   */
  public static ArticleDTO withMetaTitle() {
    return new ArticleDTO(STANDARD_ID, STANDARD_SLUG, STANDARD_TITLE, null, STANDARD_INTRO_TEXT,
        null, STANDARD_AUTHOR_NAME, STANDARD_PUBLISHED_DATE, null, null, null, META_TITLE,
        META_DESCRIPTION, STANDARD_LOCALE
    );
  }

  /**
   * Creates a Portuguese {@link ArticleDTO} with default Portuguese test values.
   *
   * @return a Portuguese ArticleDTO instance
   */
  public static ArticleDTO portuguese() {
    return new ArticleDTO(STANDARD_ID, STANDARD_SLUG, PT_TITLE, null, PT_INTRO_TEXT, null,
        STANDARD_AUTHOR_NAME, STANDARD_PUBLISHED_DATE, null, null, null, null, null, PT_LOCALE
    );
  }

  /**
   * Creates an {@link ArticleDTO} with a featured image and its dimensions.
   *
   * @return an ArticleDTO instance with a featured image
   */
  public static ArticleDTO withFeaturedImage() {
    return new ArticleDTO(STANDARD_ID, STANDARD_SLUG, STANDARD_TITLE, null, STANDARD_INTRO_TEXT, null,
        STANDARD_AUTHOR_NAME, STANDARD_PUBLISHED_DATE, FEATURED_IMAGE, FEATURED_IMAGE_WIDTH, FEATURED_IMAGE_HEIGHT,
        null, null, STANDARD_LOCALE
    );
  }

  /**
   * Creates an {@link ArticleDTO} without a published date.
   *
   * @return an ArticleDTO instance without a published date
   */
  public static ArticleDTO withoutPublishedDate() {
    return new ArticleDTO(STANDARD_ID, STANDARD_SLUG, STANDARD_TITLE, null, STANDARD_INTRO_TEXT, null,
        STANDARD_AUTHOR_NAME, null, null, null, null, null, null, STANDARD_LOCALE
    );
  }

  /**
   * Creates an {@link ArticleDTO} with a meta title and a blank meta description.
   *
   * @return an ArticleDTO instance with a blank meta description
   */
  public static ArticleDTO withBlankMetaDescription() {
    return new ArticleDTO(STANDARD_ID, STANDARD_SLUG, STANDARD_TITLE, null, STANDARD_INTRO_TEXT, null,
        STANDARD_AUTHOR_NAME, STANDARD_PUBLISHED_DATE, null, null, null, META_TITLE, "   ", STANDARD_LOCALE
    );
  }

  /**
   * Creates an {@link ArticleDTO} with a blank featured image.
   *
   * @return an ArticleDTO instance with a blank featured image
   */
  public static ArticleDTO withBlankFeaturedImage() {
    return new ArticleDTO(STANDARD_ID, STANDARD_SLUG, STANDARD_TITLE, null, STANDARD_INTRO_TEXT, null,
        STANDARD_AUTHOR_NAME, STANDARD_PUBLISHED_DATE, "", null, null, null, null, STANDARD_LOCALE
    );
  }

  /**
   * Creates an {@link ArticleDTO} whose text fields contain double quotes, apostrophes and HTML.
   *
   * @return an ArticleDTO instance with special characters
   */
  public static ArticleDTO withSpecialCharacters() {
    return new ArticleDTO(STANDARD_ID, STANDARD_SLUG, SPECIAL_TITLE, null, SPECIAL_INTRO_TEXT, null,
        SPECIAL_AUTHOR_NAME, STANDARD_PUBLISHED_DATE, null, null, null, null, null, STANDARD_LOCALE
    );
  }
}
