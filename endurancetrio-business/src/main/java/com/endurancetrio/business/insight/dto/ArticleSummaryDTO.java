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

import java.time.LocalDateTime;

/**
 * {@link ArticleSummaryDTO} carries the article data rendered by the athlete profile article
 * cards. Unlike {@link ArticleDTO}, it does not carry the article's full text, author or meta
 * fields.
 *
 * @param id            the unique identifier of the article
 * @param slug          the unique URL slug of the article (must be non-null and non-blank)
 * @param title         the article title in the requested locale (must be non-null and non-blank)
 * @param introText     the introductory HTML content (must be non-null and non-blank)
 * @param featuredImage the optional URL of the featured image
 * @param publishedDate the optional publication date of the article
 */
public record ArticleSummaryDTO(
    Long id,
    String slug,
    String title,
    String introText,
    String featuredImage,
    LocalDateTime publishedDate
) {

  public ArticleSummaryDTO {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException("title must not be null or blank");
    }
    if (slug == null || slug.isBlank()) {
      throw new IllegalArgumentException("slug must not be null or blank");
    }
    if (introText == null || introText.isBlank()) {
      throw new IllegalArgumentException("introText must not be null or blank");
    }
  }
}
