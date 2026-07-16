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
}
