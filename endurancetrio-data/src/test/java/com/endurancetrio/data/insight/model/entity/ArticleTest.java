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
 * EVEN IF WE HAVE BEEN INFORMED OF THE POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.data.insight.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.endurancetrio.data.insight.fixtures.ArticleContentFixture;
import com.endurancetrio.data.insight.fixtures.ArticleFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link Article} entity.
 * <p>
 * This test may seem redundant since it only verifies getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class ArticleTest {

  private Article underTest;

  @BeforeEach
  void setUp() {
    underTest = ArticleFixture.standard();
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(ArticleFixture.STANDARD_ID, underTest.getId());
    assertEquals(ArticleFixture.STANDARD_SLUG, underTest.getSlug());
    assertNotNull(underTest.getAuthor());
    assertEquals(ArticleFixture.STANDARD_PUBLISHED_DATE, underTest.getPublishedDate());
    assertNotNull(underTest.getContents());
    assertFalse(underTest.getContents().isEmpty());
    assertEquals(1, underTest.getContents().size());
    assertNotNull(underTest.getRelatedEvents());
    assertTrue(underTest.getRelatedEvents().isEmpty());
  }

  @Test
  void addContentShouldSetBidirectionalReference() {
    ArticleContent newContent = ArticleContentFixture.standard();
    newContent.setId(2L);
    newContent.setLocale("pt");
    newContent.setTitle("Análise de Corrida 2024");

    underTest.addContent(newContent);

    assertTrue(underTest.getContents().contains(newContent));
    assertEquals(underTest, newContent.getArticle());
  }

  @Test
  void removeContentShouldClearBidirectionalReference() {
    ArticleContent existingContent = underTest.getContents().iterator().next();
    underTest.removeContent(existingContent);

    assertFalse(underTest.getContents().contains(existingContent));
    assertNull(existingContent.getArticle());
  }
}
