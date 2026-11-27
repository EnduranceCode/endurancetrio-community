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

import com.endurancetrio.business.insight.dto.ArticleSummaryDTO;

/**
 * Fixture class providing pre-configured {@link ArticleSummaryDTO} instances for unit tests.
 */
public class ArticleSummaryDTOFixture {

  private ArticleSummaryDTOFixture() {
  }

  /**
   * Creates a standard {@link ArticleSummaryDTO} with default test values.
   *
   * @return a standard ArticleSummaryDTO instance
   */
  public static ArticleSummaryDTO standard() {
    return new ArticleSummaryDTO(ArticleFixture.STANDARD_ID, ArticleFixture.STANDARD_SLUG,
        ArticleContentFixture.STANDARD_TITLE, ArticleContentFixture.STANDARD_INTRO_TEXT,
        ArticleDTOFixture.FEATURED_IMAGE, ArticleFixture.STANDARD_PUBLISHED_DATE
    );
  }

  /**
   * Creates an {@link ArticleSummaryDTO} with the given ID and default test values.
   *
   * @param id the ID to assign to the DTO
   * @return an ArticleSummaryDTO instance with the given ID
   */
  public static ArticleSummaryDTO withId(Long id) {
    return new ArticleSummaryDTO(id, ArticleFixture.STANDARD_SLUG, ArticleContentFixture.STANDARD_TITLE,
        ArticleContentFixture.STANDARD_INTRO_TEXT, ArticleDTOFixture.FEATURED_IMAGE,
        ArticleFixture.STANDARD_PUBLISHED_DATE
    );
  }
}
