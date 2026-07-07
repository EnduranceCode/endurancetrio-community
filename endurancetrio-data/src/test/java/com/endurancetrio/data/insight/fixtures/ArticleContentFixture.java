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

package com.endurancetrio.data.insight.fixtures;

import com.endurancetrio.data.insight.model.entity.ArticleContent;

/**
 * Fixture class providing pre-configured {@link ArticleContent} entity instances for unit tests in
 * the {@code endurancetrio-data} module.
 * <p>
 * This class is duplicated in {@code endurancetrio-business} under {@code insight.fixtures} because
 * the modules do not share a test-jar dependency. Each module keeps its own copy of the fixtures it
 * needs, keeping the build simple and avoiding cross-module test-jar complications. If you add or
 * modify a factory method here, apply the same change to the business module's copy.
 */
public class ArticleContentFixture {

  public static final Long STANDARD_ID = 1L;
  public static final String STANDARD_LOCALE = "en";
  public static final String STANDARD_TITLE = "Race Analysis 2024";
  public static final String STANDARD_INTRO_TEXT = "This is the introduction.";

  private ArticleContentFixture() {
  }

  public static ArticleContent standard() {
    ArticleContent entity = new ArticleContent();
    entity.setId(STANDARD_ID);
    entity.setLocale(STANDARD_LOCALE);
    entity.setTitle(STANDARD_TITLE);
    entity.setIntroText(STANDARD_INTRO_TEXT);
    return entity;
  }
}
