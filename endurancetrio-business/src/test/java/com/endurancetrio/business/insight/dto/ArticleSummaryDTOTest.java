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

package com.endurancetrio.business.insight.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.endurancetrio.business.insight.fixtures.ArticleContentFixture;
import com.endurancetrio.business.insight.fixtures.ArticleDTOFixture;
import com.endurancetrio.business.insight.fixtures.ArticleFixture;
import com.endurancetrio.business.insight.fixtures.ArticleSummaryDTOFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ArticleSummaryDTOTest {

  private ArticleSummaryDTO underTest;

  @BeforeEach
  void setUp() {
    underTest = ArticleSummaryDTOFixture.standard();
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(ArticleFixture.STANDARD_ID, underTest.id());
    assertEquals(ArticleFixture.STANDARD_SLUG, underTest.slug());
    assertEquals(ArticleContentFixture.STANDARD_TITLE, underTest.title());
    assertEquals(ArticleContentFixture.STANDARD_INTRO_TEXT, underTest.introText());
    assertEquals(ArticleDTOFixture.FEATURED_IMAGE, underTest.featuredImage());
    assertEquals(ArticleFixture.STANDARD_PUBLISHED_DATE, underTest.publishedDate());
  }

  @Test
  void shouldRejectNullTitle() {
    assertThrows(IllegalArgumentException.class,
        () -> new ArticleSummaryDTO(ArticleFixture.STANDARD_ID, ArticleFixture.STANDARD_SLUG, null,
            ArticleContentFixture.STANDARD_INTRO_TEXT, null, null
        )
    );
  }

  @Test
  void shouldRejectBlankTitle() {
    assertThrows(IllegalArgumentException.class,
        () -> new ArticleSummaryDTO(ArticleFixture.STANDARD_ID, ArticleFixture.STANDARD_SLUG, " ",
            ArticleContentFixture.STANDARD_INTRO_TEXT, null, null
        )
    );
  }

  @Test
  void shouldRejectNullSlug() {
    assertThrows(IllegalArgumentException.class,
        () -> new ArticleSummaryDTO(ArticleFixture.STANDARD_ID, null, ArticleContentFixture.STANDARD_TITLE,
            ArticleContentFixture.STANDARD_INTRO_TEXT, null, null
        )
    );
  }

  @Test
  void shouldRejectBlankSlug() {
    assertThrows(IllegalArgumentException.class,
        () -> new ArticleSummaryDTO(ArticleFixture.STANDARD_ID, "\t", ArticleContentFixture.STANDARD_TITLE,
            ArticleContentFixture.STANDARD_INTRO_TEXT, null, null
        )
    );
  }

  @Test
  void shouldRejectNullIntroText() {
    assertThrows(IllegalArgumentException.class,
        () -> new ArticleSummaryDTO(ArticleFixture.STANDARD_ID, ArticleFixture.STANDARD_SLUG,
            ArticleContentFixture.STANDARD_TITLE, null, null, null
        )
    );
  }

  @Test
  void shouldRejectBlankIntroText() {
    assertThrows(IllegalArgumentException.class,
        () -> new ArticleSummaryDTO(ArticleFixture.STANDARD_ID, ArticleFixture.STANDARD_SLUG,
            ArticleContentFixture.STANDARD_TITLE, "", null, null
        )
    );
  }

  @Test
  void nullableFieldsCanBeNull() {
    ArticleSummaryDTO dto = new ArticleSummaryDTO(ArticleFixture.STANDARD_ID, ArticleFixture.STANDARD_SLUG,
        ArticleContentFixture.STANDARD_TITLE, ArticleContentFixture.STANDARD_INTRO_TEXT, null, null
    );

    assertNull(dto.featuredImage());
    assertNull(dto.publishedDate());
  }
}
