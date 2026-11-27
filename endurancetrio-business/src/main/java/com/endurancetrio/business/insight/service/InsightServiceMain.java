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

import com.endurancetrio.business.common.dto.ErrorDTO;
import com.endurancetrio.business.common.dto.PaginationDTO;
import com.endurancetrio.business.common.exception.EnduranceTrioError;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.insight.dto.ArticleDTO;
import com.endurancetrio.business.insight.dto.ArticleSummaryDTO;
import com.endurancetrio.business.insight.dto.InsightPageDTO;
import com.endurancetrio.business.insight.mapper.ArticleMapper;
import com.endurancetrio.data.insight.repository.ArticleRepository;
import com.endurancetrio.data.insight.repository.projection.ArticleContentSummary;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InsightServiceMain implements InsightService {

  private static final Logger LOG = LoggerFactory.getLogger(InsightServiceMain.class);

  private final ArticleRepository articleRepository;
  private final ArticleMapper articleMapper;

  @Autowired
  public InsightServiceMain(ArticleRepository articleRepository, ArticleMapper articleMapper) {
    this.articleRepository = articleRepository;
    this.articleMapper = articleMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public ArticleDTO getArticleBySlug(String slug, Locale locale) {
    var article = articleRepository.findBySlug(slug).orElseThrow(() -> {
      String errorMsg = String.format("No article found with slug '%s'", slug);
      LOG.warn(errorMsg);
      return new EnduranceTrioException(new ErrorDTO(EnduranceTrioError.NOT_FOUND, errorMsg));
    });

    return articleMapper.map(article, locale);
  }

  @Override
  @Transactional(readOnly = true)
  public InsightPageDTO getArticles(int page, Pageable pageable, Locale locale) {
    var articlePage = articleRepository.findAllByOrderByPublishedDateDesc(pageable)
        .map(entity -> articleMapper.map(entity, locale));

    return new InsightPageDTO(articlePage.getContent(), PaginationDTO.from(articlePage));
  }

  @Override
  @Transactional(readOnly = true)
  public List<ArticleDTO> getArticlesByIds(List<Long> ids, Locale locale) {
    var entities = articleRepository.findAllById(ids);
    var idOrder = new ArrayList<>(ids);

    return entities.stream()
        .map(entity -> articleMapper.map(entity, locale))
        .filter(java.util.Objects::nonNull)
        .sorted(Comparator.comparingInt(dto -> idOrder.indexOf(dto.id())))
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<ArticleSummaryDTO> getArticlesByAthleteId(Long athleteId, Pageable pageable, Locale locale) {
    var contentsByArticle = articleRepository.findContentSummariesByAthleteId(athleteId)
        .stream()
        .collect(Collectors.groupingBy(ArticleContentSummary::articleId, LinkedHashMap::new, Collectors.toList()));

    return contentsByArticle.values().stream().map(contents -> mapToSummary(contents, locale)).toList();
  }

  @Override
  @Transactional(readOnly = true)
  public InsightPageDTO getArticlesByEvent(Long eventId, Pageable pageable, Locale locale) {
    var articlePage = articleRepository.findByEventId(eventId, pageable)
        .map(entity -> articleMapper.map(entity, locale));

    return new InsightPageDTO(articlePage.getContent(), PaginationDTO.from(articlePage));
  }

  @Override
  @Transactional(readOnly = true)
  public long getArticlesCountByEvent(Long eventId) {
    return articleRepository.countByEventId(eventId);
  }

  /**
   * Resolves the content for the requested {@link Locale} and maps the article to an
   * {@link ArticleSummaryDTO}.
   * <p>
   * The content is chosen with the same rule as {@link ArticleMapper}: the content whose locale matches
   * {@code locale.getLanguage()}, otherwise the first content in the list. The summary query uses an inner
   * join to {@code article_content}, so every group has at least one content row and this method always
   * returns a summary.
   *
   * @param contents the content rows of a single article, in the order returned by the repository
   * @param locale   the requested locale for content resolution
   * @return the summary built from the resolved content
   */
  private ArticleSummaryDTO mapToSummary(List<ArticleContentSummary> contents, Locale locale) {
    String requestedLocale = locale.getLanguage();

    ArticleContentSummary content = contents.stream()
        .filter(candidate -> candidate.locale().equals(requestedLocale))
        .findFirst()
        .orElse(contents.getFirst());

    return new ArticleSummaryDTO(content.articleId(), content.slug(), content.title(), content.introText(),
        content.featuredImage(), content.publishedDate()
    );
  }
}
