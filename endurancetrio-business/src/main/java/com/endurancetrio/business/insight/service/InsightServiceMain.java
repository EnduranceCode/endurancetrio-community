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
import com.endurancetrio.business.insight.dto.InsightPageDTO;
import com.endurancetrio.business.insight.mapper.ArticleMapper;
import com.endurancetrio.data.insight.repository.ArticleRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
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
  public InsightPageDTO getArticlesByAthleteId(Long athleteId, Pageable pageable, Locale locale) {
    var articlePage = articleRepository.findByAthleteId(athleteId, pageable)
        .map(entity -> articleMapper.map(entity, locale));

    return new InsightPageDTO(articlePage.getContent(), PaginationDTO.from(articlePage));
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
}
