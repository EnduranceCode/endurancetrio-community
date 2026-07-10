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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.endurancetrio.business.common.exception.EnduranceTrioError;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.insight.dto.ArticleDTO;
import com.endurancetrio.business.insight.dto.InsightPageDTO;
import com.endurancetrio.business.insight.fixtures.ArticleDTOFixture;
import com.endurancetrio.business.insight.fixtures.ArticleFixture;
import com.endurancetrio.business.insight.mapper.ArticleMapper;
import com.endurancetrio.data.insight.model.entity.Article;
import com.endurancetrio.data.insight.repository.ArticleRepository;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class InsightServiceMainTest {

  private static final Locale LOCALE = Locale.ENGLISH;
  private static final String SLUG = "test-article";
  private static final Long EVENT_ID = 1L;
  private static final Pageable PAGEABLE = PageRequest.of(0, 10);

  @Mock
  private ArticleRepository articleRepository;

  @Mock
  private ArticleMapper articleMapper;

  @InjectMocks
  private InsightServiceMain underTest;

  private Article testArticle;
  private ArticleDTO testArticleDTO;

  @BeforeEach
  void setUp() {
    testArticle = ArticleFixture.standard();
    testArticleDTO = ArticleDTOFixture.standard();
  }

  @Test
  void getArticlesShouldReturnPaginatedResults() {
    Page<Article> articlePage = new PageImpl<>(List.of(testArticle), PAGEABLE, 1L);
    when(articleRepository.findAllByOrderByPublishedDateDesc(PAGEABLE)).thenReturn(articlePage);
    when(articleMapper.map(testArticle, LOCALE)).thenReturn(testArticleDTO);

    InsightPageDTO result = underTest.getArticles(0, PAGEABLE, LOCALE);

    assertNotNull(result);
    assertEquals(1, result.articles().size());
    assertEquals(testArticleDTO, result.articles().getFirst());
    assertEquals(0, result.pagination().pageNumber());
    assertEquals(1, result.pagination().totalPages());
    assertEquals(1L, result.pagination().totalItems());
  }

  @Test
  void getArticlesShouldReturnEmptyPageWhenNoArticles() {
    Page<Article> articlePage = new PageImpl<>(List.of(), PAGEABLE, 0L);
    when(articleRepository.findAllByOrderByPublishedDateDesc(PAGEABLE)).thenReturn(articlePage);

    InsightPageDTO result = underTest.getArticles(0, PAGEABLE, LOCALE);

    assertNotNull(result);
    assertEquals(0, result.articles().size());
    assertEquals(0, result.pagination().totalItems());
  }

  @Test
  void getArticleBySlugShouldReturnArticle() {
    when(articleRepository.findBySlug(SLUG)).thenReturn(Optional.of(testArticle));
    when(articleMapper.map(testArticle, LOCALE)).thenReturn(testArticleDTO);

    ArticleDTO result = underTest.getArticleBySlug(SLUG, LOCALE);

    assertNotNull(result);
    assertEquals(testArticleDTO, result);
  }

  @Test
  void getArticleBySlugWithUnknownSlugShouldThrow() {
    when(articleRepository.findBySlug(SLUG)).thenReturn(Optional.empty());

    EnduranceTrioException exception = assertThrows(EnduranceTrioException.class,
        () -> underTest.getArticleBySlug(SLUG, LOCALE)
    );

    assertEquals(EnduranceTrioError.NOT_FOUND.getCode(), exception.getCode());
  }

  @Test
  void getArticlesByEventShouldReturnPaginatedResults() {
    Page<Article> articlePage = new PageImpl<>(List.of(testArticle), PAGEABLE, 1L);
    when(articleRepository.findByEventId(EVENT_ID, PAGEABLE)).thenReturn(articlePage);
    when(articleMapper.map(testArticle, LOCALE)).thenReturn(testArticleDTO);

    InsightPageDTO result = underTest.getArticlesByEvent(EVENT_ID, PAGEABLE, LOCALE);

    assertNotNull(result);
    assertEquals(1, result.articles().size());
    assertEquals(testArticleDTO, result.articles().getFirst());
    assertEquals(0, result.pagination().pageNumber());
    assertEquals(1, result.pagination().totalPages());
    assertEquals(1L, result.pagination().totalItems());
  }

  @Test
  void getArticlesByEventWithEmptyPage() {
    Page<Article> articlePage = new PageImpl<>(List.of(), PAGEABLE, 0L);
    when(articleRepository.findByEventId(EVENT_ID, PAGEABLE)).thenReturn(articlePage);

    InsightPageDTO result = underTest.getArticlesByEvent(EVENT_ID, PAGEABLE, LOCALE);

    assertNotNull(result);
    assertEquals(0, result.articles().size());
    assertEquals(0, result.pagination().totalItems());
  }
}
