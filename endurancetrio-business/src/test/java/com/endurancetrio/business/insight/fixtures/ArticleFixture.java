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

import com.endurancetrio.data.insight.model.entity.Article;
import com.endurancetrio.data.insight.model.entity.ArticleContent;
import com.endurancetrio.data.insight.model.entity.Author;
import java.time.LocalDateTime;
import java.time.Month;

/**
 * Fixture class providing pre-configured {@link Article} entity instances for unit tests in the
 * {@code endurancetrio-business} module.
 * <p>
 * This class is duplicated in {@code endurancetrio-data} under {@code insight.fixtures} because the
 * modules do not share a test-jar dependency. Each module keeps its own copy of the fixtures it
 * needs, keeping the build simple and avoiding cross-module test-jar complications. If you add or
 * modify a factory method here, apply the same change to the data module's copy.
 */
public class ArticleFixture {

  public static final Long STANDARD_ID = 1L;
  public static final String STANDARD_SLUG = "race-analysis-2024";
  public static final LocalDateTime STANDARD_PUBLISHED_DATE = LocalDateTime.of(2024, Month.JUNE, 15,
      10, 0
  );

  private ArticleFixture() {
  }

  /**
   * Creates a standard {@link Article} entity with default test values.
   *
   * @return a standard Article entity instance
   */
  public static Article standard() {
    Article entity = base();
    entity.addContent(ArticleContentFixture.standard());
    return entity;
  }

  /**
   * Creates an {@link Article} entity with the given ID and default test values.
   *
   * @param id the ID to assign to the article
   * @return an Article entity instance with the given ID
   */
  public static Article withId(Long id) {
    Article entity = base();
    entity.setId(id);
    return entity;
  }

  /**
   * Creates an {@link Article} entity without any {@link ArticleContent}.
   *
   * @return an Article entity instance with no content
   */
  public static Article withoutContent() {
    return base();
  }

  /**
   * Creates an {@link Article} entity with a single Portuguese {@link ArticleContent}.
   *
   * @return an Article entity instance with Portuguese content
   */
  public static Article withPortugueseContent() {
    ArticleContent content = new ArticleContent();
    content.setLocale(ArticleContentFixture.PT_LOCALE);
    content.setTitle(ArticleContentFixture.PT_TITLE);
    content.setIntroText(ArticleContentFixture.PT_INTRO_TEXT);

    Article entity = base();
    entity.addContent(content);
    return entity;
  }

  /**
   * Creates an {@link Article} entity with standard content and a featured image.
   *
   * @return an Article entity instance with a featured image
   */
  public static Article withFeaturedImage() {
    ArticleContent content = ArticleContentFixture.standard();

    Article entity = base();
    entity.setFeaturedImage("/images/featured.jpg");
    entity.setFeaturedImageWidth(1200);
    entity.setFeaturedImageHeight(630);
    entity.addContent(content);
    return entity;
  }

  private static Article base() {
    Author author = AuthorFixture.standard();
    Article entity = new Article();
    entity.setId(STANDARD_ID);
    entity.setSlug(STANDARD_SLUG);
    entity.setAuthor(author);
    entity.setPublishedDate(STANDARD_PUBLISHED_DATE);
    return entity;
  }
}
