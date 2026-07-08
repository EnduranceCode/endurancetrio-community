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

import com.endurancetrio.data.insight.model.entity.Article;
import com.endurancetrio.data.insight.model.entity.ArticleContent;
import com.endurancetrio.data.insight.model.entity.Author;
import java.time.LocalDateTime;

/**
 * {@link ArticleDTO} carries the full article data for the Insights section, combining the
 * {@link Article article} entity with its locale-resolved {@link ArticleContent content}.
 *
 * @param id                  the unique identifier of the article
 * @param slug                the unique URL slug of the article (must be non-null and non-blank)
 * @param title               the article title in the requested locale (must be non-null and
 *                            non-blank)
 * @param subtitle            the optional article subtitle in the requested locale
 * @param introText           the introductory HTML content (must be non-null and non-blank)
 * @param fullText            the optional full HTML content of the article
 * @param authorName          the display name of the author, resolved from
 *                            {@link Author#getKnownName() Author.knownName} (must be non-null and
 *                            non-blank)
 * @param publishedDate       the optional publication date of the article
 * @param featuredImage       the optional URL of the featured image
 * @param featuredImageWidth  the optional width of the featured image in pixels, used for OG meta
 *                            tags
 * @param featuredImageHeight the optional height of the featured image in pixels, used for OG meta
 *                            tags
 * @param metaTitle           the optional SEO meta title in the requested locale
 * @param metaDescription     the optional SEO meta description in the requested locale
 * @param locale              the locale code of the resolved content (e.g. "en" or "pt"), must be
 *                            non-null and non-blank
 */
public record ArticleDTO(Long id, String slug, String title, String subtitle, String introText,
                         String fullText, String authorName, LocalDateTime publishedDate,
                         String featuredImage, Integer featuredImageWidth,
                         Integer featuredImageHeight, String metaTitle, String metaDescription,
                         String locale) {

  public ArticleDTO {
    if (title == null || title.isBlank()) {
      throw new IllegalArgumentException("title must not be null or blank");
    }
    if (slug == null || slug.isBlank()) {
      throw new IllegalArgumentException("slug must not be null or blank");
    }
    if (introText == null || introText.isBlank()) {
      throw new IllegalArgumentException("introText must not be null or blank");
    }
    if (authorName == null || authorName.isBlank()) {
      throw new IllegalArgumentException("authorName must not be null or blank");
    }
    if (locale == null || locale.isBlank()) {
      throw new IllegalArgumentException("locale must not be null or blank");
    }
  }
}
