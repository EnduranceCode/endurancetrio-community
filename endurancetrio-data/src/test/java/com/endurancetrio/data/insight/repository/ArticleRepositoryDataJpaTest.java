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

package com.endurancetrio.data.insight.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.endurancetrio.data.config.JpaConfig;
import com.endurancetrio.data.event.model.entity.Event;
import com.endurancetrio.data.insight.model.entity.Article;
import com.endurancetrio.data.insight.model.entity.ArticleContent;
import com.endurancetrio.data.insight.model.entity.Author;
import com.endurancetrio.data.insight.repository.projection.ArticleContentSummary;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.hibernate.SessionFactory;
import org.hibernate.stat.Statistics;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = ArticleRepositoryDataJpaTest.TestApplication.class)
@TestPropertySource(properties = "spring.jpa.properties.hibernate.generate_statistics=true")
@Sql(scripts = "/sql/article-summary-before.sql")
@Sql(scripts = "/sql/article-summary-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class ArticleRepositoryDataJpaTest {

  private static final Long ATHLETE_WITH_FIVE_ARTICLES = 1L;
  private static final Long ATHLETE_WITH_ONE_ARTICLE = 4L;

  @Autowired
  private ArticleRepository repository;

  @Autowired
  private EntityManager entityManager;

  @Autowired
  private EntityManagerFactory entityManagerFactory;

  @Test
  void findContentSummariesByAthleteIdReturnsOneRowPerContentOrderedByPublishedDateDesc() {
    List<ArticleContentSummary> rows = repository.findContentSummariesByAthleteId(ATHLETE_WITH_FIVE_ARTICLES);

    // Six content rows for the four articles with content. The article without content is excluded.
    assertEquals(6, rows.size());
    assertEquals(List.of(2L, 990003L, 990002L, 990001L),
        rows.stream().map(ArticleContentSummary::articleId).distinct().toList()
    );
    assertEquals(List.of("pt", "en"), rows.subList(0, 2).stream().map(ArticleContentSummary::locale).toList());
    assertEquals(List.of("pt", "en"), rows.subList(3, 5).stream().map(ArticleContentSummary::locale).toList());

    ArticleContentSummary newest = rows.get(2);
    assertEquals(990003L, newest.articleId());
    assertEquals("en", newest.locale());
    assertEquals("Summary Newest EN", newest.title());
    assertEquals("<p>Newest intro</p>", newest.introText());
    assertEquals("summary-newest", newest.slug());
    assertEquals("/img/insights/summary-newest.jpg", newest.featuredImage());
    assertEquals(LocalDateTime.of(2026, 3, 1, 10, 0), newest.publishedDate());

    assertTrue(rows.stream().noneMatch(row -> row.articleId().equals(990004L)));
  }

  @Test
  void findContentSummariesByAthleteIdExcludesArticlesWithoutPublishedDate() {
    Author author = entityManager.createQuery("SELECT au FROM Author au JOIN au.athlete at WHERE at.id = :athleteId",
        Author.class
    ).setParameter("athleteId", ATHLETE_WITH_FIVE_ARTICLES).getSingleResult();
    int publishedRowCount = repository.findContentSummariesByAthleteId(ATHLETE_WITH_FIVE_ARTICLES).size();

    var unpublished = article("summary-unpublished", author);
    var content = new ArticleContent();
    content.setLocale("en");
    content.setTitle("Summary Unpublished EN");
    content.setIntroText("<p>Unpublished intro</p>");
    content.setFullText("<p>Unpublished full text</p>");
    unpublished.addContent(content);
    persist(unpublished);

    List<ArticleContentSummary> rows = repository.findContentSummariesByAthleteId(ATHLETE_WITH_FIVE_ARTICLES);

    assertEquals(publishedRowCount, rows.size());
    assertTrue(rows.stream().noneMatch(row -> row.articleId().equals(unpublished.getId())));
    assertTrue(rows.stream().noneMatch(row -> row.publishedDate() == null));
  }

  @Test
  void findContentSummariesByAthleteIdIssuesOneStatementRegardlessOfArticleCount() {
    Statistics statistics = statistics();

    long oneArticleStatements = statementCount(statistics, ATHLETE_WITH_ONE_ARTICLE);
    long fiveArticleStatements = statementCount(statistics, ATHLETE_WITH_FIVE_ARTICLES);

    assertEquals(1, oneArticleStatements);
    assertEquals(oneArticleStatements, fiveArticleStatements);
  }

  @Test
  void findAllByOrderByPublishedDateDescExcludesArticlesWithoutPublishedDate() {
    List<Article> published = repository.findByPublishedDateIsNotNullOrderByPublishedDateDesc();
    Article unpublished = persist(article("insights-unpublished", author()));

    List<Article> result = repository.findAllByOrderByPublishedDateDesc(PageRequest.of(0, 20))
        .getContent();

    assertEquals(published.size(), result.size());
    assertTrue(result.containsAll(published));
    assertFalse(result.contains(unpublished));
    assertOrderedByPublishedDateDesc(result);
  }

  @Test
  void findPublishedBySlugExcludesArticleWithoutPublishedDate() {
    Article published = repository.findByPublishedDateIsNotNullOrderByPublishedDateDesc().getFirst();
    Article unpublished = persist(article("slug-unpublished", author()));

    assertTrue(repository.findPublishedBySlug(unpublished.getSlug()).isEmpty());
    assertEquals(published, repository.findPublishedBySlug(published.getSlug()).orElseThrow());
  }

  @Test
  void findPublishedByIdsExcludesArticlesWithoutPublishedDate() {
    List<Long> publishedIds = repository.findByPublishedDateIsNotNullOrderByPublishedDateDesc()
        .stream().map(Article::getId).toList();
    Article unpublished = persist(article("featured-unpublished", author()));
    List<Long> ids = new ArrayList<>(publishedIds);
    ids.add(unpublished.getId());

    List<Long> resultIds = repository.findPublishedByIds(ids).stream().map(Article::getId).toList();

    assertEquals(publishedIds.size(), resultIds.size());
    assertTrue(resultIds.containsAll(publishedIds));
    assertFalse(resultIds.contains(unpublished.getId()));
  }

  @Test
  void findPublishedByIdsWithEmptyIdsReturnsEmpty() {
    assertTrue(repository.findPublishedByIds(List.of()).isEmpty());
  }

  @Test
  void countByEventIdExcludesArticlesWithoutPublishedDate() {
    Event event = eventWithPublishedArticle();
    long publishedCount = repository.countByEventId(event.getId());
    assertTrue(publishedCount > 0);

    Article unpublished = article("event-count-unpublished", author());
    unpublished.getRelatedEvents().add(event);
    persist(unpublished);

    assertEquals(publishedCount, repository.countByEventId(event.getId()));
  }

  @Test
  void findByEventIdExcludesArticlesWithoutPublishedDate() {
    Event event = eventWithPublishedArticle();
    List<Long> publishedIds = repository.findByEventId(event.getId(), PageRequest.of(0, 20))
        .getContent().stream().map(Article::getId).toList();
    assertFalse(publishedIds.isEmpty());

    Article unpublished = article("event-list-unpublished", author());
    unpublished.getRelatedEvents().add(event);
    persist(unpublished);

    List<Article> result = repository.findByEventId(event.getId(), PageRequest.of(0, 20)).getContent();

    assertEquals(publishedIds.size(), result.size());
    assertFalse(result.contains(unpublished));
    assertOrderedByPublishedDateDesc(result);
  }

  private void assertOrderedByPublishedDateDesc(List<Article> articles) {
    List<LocalDateTime> dates = articles.stream().map(Article::getPublishedDate).toList();
    assertTrue(dates.stream().allMatch(Objects::nonNull));
    assertEquals(dates.stream().sorted(Comparator.reverseOrder()).toList(), dates);
  }

  private Article article(String slug, Author author) {
    Article article = new Article();
    article.setSlug(slug);
    article.setAuthor(author);
    return article;
  }

  private Author author() {
    return entityManager.createQuery("SELECT au FROM Author au ORDER BY au.id", Author.class)
        .getResultList().getFirst();
  }

  private Event eventWithPublishedArticle() {
    return repository.findByPublishedDateIsNotNullOrderByPublishedDateDesc().stream()
        .flatMap(article -> article.getRelatedEvents().stream())
        .findFirst()
        .orElseThrow();
  }

  private Article persist(Article article) {
    entityManager.persist(article);
    entityManager.flush();
    return article;
  }

  private Statistics statistics() {
    return entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
  }

  private long statementCount(Statistics statistics, Long athleteId) {
    statistics.clear();
    List<ArticleContentSummary> rows = repository.findContentSummariesByAthleteId(athleteId);

    // Touch every projected column to prove that consuming the rows issues no further statements.
    List<Object> consumedValues = rows.stream()
        .flatMap(row -> Stream.<Object>of(row.articleId(), row.locale(), row.title(), row.introText(),
            row.slug(), row.featuredImage(), row.publishedDate()
        ))
        .toList();

    assertEquals(rows.size() * 7, consumedValues.size());

    return statistics.getPrepareStatementCount();
  }

  @SpringBootConfiguration
  @EnableAutoConfiguration
  @EntityScan("com.endurancetrio.data")
  @EnableJpaRepositories("com.endurancetrio.data")
  @Import(JpaConfig.class)
  static class TestApplication {

  }
}
