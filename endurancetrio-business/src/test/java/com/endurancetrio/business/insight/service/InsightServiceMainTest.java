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
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.endurancetrio.business.common.exception.EnduranceTrioError;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.insight.dto.ArticleDTO;
import com.endurancetrio.business.insight.dto.ArticleSummaryDTO;
import com.endurancetrio.business.insight.dto.InsightPageDTO;
import com.endurancetrio.business.insight.fixtures.ArticleDTOFixture;
import com.endurancetrio.business.insight.fixtures.ArticleFixture;
import com.endurancetrio.business.insight.mapper.ArticleMapper;
import com.endurancetrio.data.insight.model.entity.Article;
import com.endurancetrio.data.insight.repository.ArticleRepository;
import com.endurancetrio.data.insight.repository.projection.ArticleContentSummary;
import java.time.LocalDateTime;
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
  private static final Long ATHLETE_ID = 1L;
  private static final LocalDateTime PUBLISHED_DATE = LocalDateTime.of(2026, 8, 15, 10, 0);
  private static final Pageable PAGEABLE = PageRequest.of(0, 10);

  @Mock
  private ArticleRepository articleRepository;

  @Mock
  private ArticleMapper articleMapper;

  @InjectMocks
  private InsightServiceMain underTest;

  private Article testArticle;
  private ArticleDTO testArticleDTO;
  private Article secondArticle;
  private ArticleDTO secondArticleDTO;

  @BeforeEach
  void setUp() {
    testArticle = ArticleFixture.standard();
    testArticleDTO = ArticleDTOFixture.standard();

    secondArticle = ArticleFixture.withId(2L);
    secondArticleDTO = ArticleDTOFixture.withId(2L);
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
  void getArticlesByIdsShouldReturnArticlesInRequestedOrder() {
    var ids = List.of(2L, 1L);
    when(articleRepository.findAllById(ids)).thenReturn(List.of(testArticle, secondArticle));
    when(articleMapper.map(testArticle, LOCALE)).thenReturn(testArticleDTO);
    when(articleMapper.map(secondArticle, LOCALE)).thenReturn(secondArticleDTO);

    List<ArticleDTO> result = underTest.getArticlesByIds(ids, LOCALE);

    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals(Long.valueOf(2L), result.get(0).id());
    assertEquals(Long.valueOf(1L), result.get(1).id());
  }

  @Test
  void getArticlesByIdsShouldExcludeUnresolvableArticles() {
    var ids = List.of(1L);
    when(articleRepository.findAllById(ids)).thenReturn(List.of(testArticle));
    when(articleMapper.map(testArticle, LOCALE)).thenReturn(null);

    List<ArticleDTO> result = underTest.getArticlesByIds(ids, LOCALE);

    assertNotNull(result);
    assertTrue(result.isEmpty());
  }

  @Test
  void getArticlesByIdsWithEmptyIdList() {
    var ids = List.<Long>of();
    when(articleRepository.findAllById(ids)).thenReturn(List.of());

    List<ArticleDTO> result = underTest.getArticlesByIds(ids, LOCALE);

    assertNotNull(result);
    assertTrue(result.isEmpty());
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

  @Test
  void getArticlesByAthleteIdShouldResolveRequestedLocale() {
    when(articleRepository.findContentSummariesByAthleteId(ATHLETE_ID)).thenReturn(
        List.of(contentRow(1L, "pt", "Título"), contentRow(1L, "en", "Title")));

    List<ArticleSummaryDTO> result = underTest.getArticlesByAthleteId(ATHLETE_ID, PAGEABLE, LOCALE);

    assertEquals(1, result.size());
    assertEquals(1L, result.getFirst().id());
    assertEquals("Title", result.getFirst().title());
  }

  @Test
  void getArticlesByAthleteIdShouldFallBackToFirstContent() {
    when(articleRepository.findContentSummariesByAthleteId(ATHLETE_ID)).thenReturn(
        List.of(contentRow(1L, "pt", "Título")));

    List<ArticleSummaryDTO> result = underTest.getArticlesByAthleteId(ATHLETE_ID, PAGEABLE, Locale.FRENCH);

    assertEquals(1, result.size());
    assertEquals("Título", result.getFirst().title());
  }

  @Test
  void getArticlesByAthleteIdShouldKeepPublishedDateOrder() {
    when(articleRepository.findContentSummariesByAthleteId(ATHLETE_ID)).thenReturn(
        List.of(contentRow(2L, "en", "Second"), contentRow(1L, "en", "First")));

    List<ArticleSummaryDTO> result = underTest.getArticlesByAthleteId(ATHLETE_ID, PAGEABLE, LOCALE);

    assertEquals(List.of(2L, 1L), result.stream().map(ArticleSummaryDTO::id).toList());
  }

  @Test
  void getArticlesByAthleteIdWithEmptyList() {
    when(articleRepository.findContentSummariesByAthleteId(ATHLETE_ID)).thenReturn(List.of());

    List<ArticleSummaryDTO> result = underTest.getArticlesByAthleteId(ATHLETE_ID, PAGEABLE, LOCALE);

    assertTrue(result.isEmpty());
  }

  @Test
  void getArticlesCountByEventShouldReturnCount() {
    when(articleRepository.countByEventId(EVENT_ID)).thenReturn(3L);

    long result = underTest.getArticlesCountByEvent(EVENT_ID);

    assertEquals(3L, result);
  }

  @Test
  void getArticlesCountByEventWhenNoArticlesShouldReturnZero() {
    when(articleRepository.countByEventId(EVENT_ID)).thenReturn(0L);

    long result = underTest.getArticlesCountByEvent(EVENT_ID);

    assertEquals(0L, result);
  }

  private static ArticleContentSummary contentRow(Long articleId, String locale, String title) {
    return new ArticleContentSummary(articleId, locale, title, "<p>" + title + "</p>", "slug-" + articleId,
        "/img/" + articleId + ".jpg", PUBLISHED_DATE
    );
  }
}
