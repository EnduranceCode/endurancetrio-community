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

package com.endurancetrio.business.insight.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import com.endurancetrio.business.insight.dto.ArticleDTO;
import com.endurancetrio.business.insight.dto.AuthorDTO;
import com.endurancetrio.business.insight.fixtures.ArticleContentFixture;
import com.endurancetrio.business.insight.fixtures.ArticleFixture;
import com.endurancetrio.business.insight.fixtures.AuthorFixture;
import com.endurancetrio.data.insight.model.entity.Article;
import java.util.Locale;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleMapperTest {

  private Article entityWithFeaturedImage;
  private Article entityWithPtContent;

  @Mock
  private AuthorMapper authorMapper;

  @InjectMocks
  private ArticleMapper underTest;

  @BeforeEach
  void setUp() {
    entityWithFeaturedImage = ArticleFixture.withFeaturedImage();
    entityWithPtContent = ArticleFixture.withPortugueseContent();
  }

  @Test
  void mapNullEntity() {
    assertNull(underTest.map(null, Locale.ENGLISH));
  }

  @Test
  void mapEntityWithMatchingLocale() {
    when(authorMapper.map(entityWithFeaturedImage.getAuthor())).thenReturn(
        new AuthorDTO(null, AuthorFixture.STANDARD_KNOWN_NAME, null, null, null));

    ArticleDTO result = underTest.map(entityWithFeaturedImage, Locale.ENGLISH);

    assertNotNull(result);
    assertEquals(ArticleFixture.STANDARD_ID, result.id());
    assertEquals(ArticleFixture.STANDARD_SLUG, result.slug());
    assertEquals(ArticleContentFixture.STANDARD_TITLE, result.title());
    assertNull(result.subtitle());
    assertEquals(ArticleContentFixture.STANDARD_INTRO_TEXT, result.introText());
    assertNull(result.fullText());
    assertEquals(AuthorFixture.STANDARD_KNOWN_NAME, result.authorName());
    assertEquals(ArticleFixture.STANDARD_PUBLISHED_DATE, result.publishedDate());
    assertEquals("/images/featured.jpg", result.featuredImage());
    assertEquals(1200, result.featuredImageWidth());
    assertEquals(630, result.featuredImageHeight());
    assertNull(result.metaTitle());
    assertNull(result.metaDescription());
    assertEquals(ArticleContentFixture.STANDARD_LOCALE, result.locale());
  }

  @Test
  void mapEntityWithFallbackLocale() {
    when(authorMapper.map(entityWithPtContent.getAuthor())).thenReturn(
        new AuthorDTO(null, AuthorFixture.STANDARD_KNOWN_NAME, null, null, null));

    ArticleDTO result = underTest.map(entityWithPtContent, Locale.FRENCH);

    assertNotNull(result);
    assertEquals(ArticleContentFixture.PT_TITLE, result.title());
    assertEquals(ArticleContentFixture.PT_INTRO_TEXT, result.introText());
    assertEquals(ArticleContentFixture.PT_LOCALE, result.locale());
  }

  @Test
  void mapEntityWithNoContent() {
    assertNull(underTest.map(ArticleFixture.withoutContent(), Locale.ENGLISH));
  }
}
