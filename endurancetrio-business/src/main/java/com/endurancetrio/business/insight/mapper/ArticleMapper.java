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

import com.endurancetrio.business.insight.dto.ArticleDTO;
import com.endurancetrio.business.insight.dto.AuthorDTO;
import com.endurancetrio.data.insight.model.entity.Article;
import com.endurancetrio.data.insight.model.entity.ArticleContent;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper {

  private final AuthorMapper authorMapper;

  @Autowired
  public ArticleMapper(AuthorMapper authorMapper) {
    this.authorMapper = authorMapper;
  }

  /**
   * Maps an {@link Article} entity to an {@link ArticleDTO} for the given {@link Locale}.
   * <p>
   * The locale resolution follows a fallback strategy: first try to find {@link ArticleContent}
   * matching the requested locale; if none is found, fall back to the first available content; if
   * no content exists, returns {@code null}.
   *
   * @param entity the Article entity to be mapped
   * @param locale the requested locale for content resolution
   * @return the corresponding ArticleDTO, or {@code null} if the entity is {@code null} or no
   * content is available
   */
  public ArticleDTO map(Article entity, Locale locale) {
    if (entity == null) {
      return null;
    }

    ArticleContent content = resolveContent(entity, locale);
    if (content == null) {
      return null;
    }

    AuthorDTO author = authorMapper.map(entity.getAuthor());

    return new ArticleDTO(entity.getId(), entity.getSlug(), content.getTitle(),
        content.getSubtitle(), content.getIntroText(), content.getFullText(), author.knownName(),
        entity.getPublishedDate(), entity.getFeaturedImage(), entity.getFeaturedImageWidth(),
        entity.getFeaturedImageHeight(), content.getMetaTitle(), content.getMetaDescription(),
        content.getLocale()
    );
  }

  /**
   * Resolves the best matching {@link ArticleContent} for the given {@link Locale}.
   * <p>
   * The fallback strategy is:
   * <ol>
   *   <li>Find content where the locale exactly matches the requested locale.</li>
   *   <li>If not found, return the first available content (any locale).</li>
   *   <li>If no content exists, return {@code null}.</li>
   * </ol>
   *
   * @param entity the Article entity whose contents are searched
   * @param locale the requested locale
   * @return the matching ArticleContent, or {@code null} if none is available
   */
  private ArticleContent resolveContent(Article entity, Locale locale) {
    String requestedLocale = locale.getLanguage();

    for (ArticleContent content : entity.getContents()) {
      if (content.getLocale().equals(requestedLocale)) {
        return content;
      }
    }

    if (!entity.getContents().isEmpty()) {
      return entity.getContents().iterator().next();
    }

    return null;
  }
}
