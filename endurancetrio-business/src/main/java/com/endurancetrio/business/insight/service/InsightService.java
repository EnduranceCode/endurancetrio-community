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

package com.endurancetrio.business.insight.service;

import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.insight.dto.ArticleDTO;
import com.endurancetrio.business.insight.dto.InsightPageDTO;
import java.util.Locale;
import org.springframework.data.domain.Pageable;

/**
 * The {@link InsightService} defines the business operations for the Insights section, providing
 * access to article data with locale-aware content resolution.
 */
public interface InsightService {

  /**
   * Returns an {@link InsightPageDTO} containing a paginated list of all
   * {@link ArticleDTO articles}, ordered by published date descending.
   *
   * @param page     the 0-based page number, pre-clamped by the controller
   * @param pageable the pagination information (page number, page size, etc.)
   * @param locale   the requested locale for content resolution
   * @return an {@link InsightPageDTO} with the articles for the requested page and pagination
   * metadata
   */
  InsightPageDTO getArticles(int page, Pageable pageable, Locale locale);

  /**
   * Returns an {@link ArticleDTO} identified by its unique URL slug, with content resolved for the
   * requested locale.
   *
   * @param slug   the unique URL slug of the article
   * @param locale the requested locale for content resolution
   * @return the {@link ArticleDTO} with locale-resolved content
   * @throws EnduranceTrioException if no article with the given slug is found
   */
  ArticleDTO getArticleBySlug(String slug, Locale locale);

  /**
   * Returns an {@link InsightPageDTO} containing all {@link ArticleDTO articles} associated with
   * the given event ID, with content resolved for the requested locale.
   *
   * @param eventId the ID of the event to filter articles by
   * @param locale  the requested locale for content resolution
   * @return an {@link InsightPageDTO} with the articles linked to the given event
   */
  InsightPageDTO getArticlesByEvent(Long eventId, Locale locale);
}
