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
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.endurancetrio.data.config.JpaConfig;
import com.endurancetrio.data.insight.model.entity.Article;
import com.endurancetrio.data.insight.model.entity.ArticleContent;
import com.endurancetrio.data.insight.model.entity.Author;
import com.endurancetrio.data.insight.repository.projection.ArticleContentSummary;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.util.List;
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
  void findContentSummariesByAthleteIdSortsArticlesWithoutPublishedDateLast() {
    Author author = entityManager.createQuery("SELECT au FROM Author au JOIN au.athlete at WHERE at.id = :athleteId",
        Author.class
    ).setParameter("athleteId", ATHLETE_WITH_FIVE_ARTICLES).getSingleResult();

    var unpublished = new Article();
    unpublished.setSlug("summary-unpublished");
    unpublished.setAuthor(author);

    var content = new ArticleContent();
    content.setLocale("en");
    content.setTitle("Summary Unpublished EN");
    content.setIntroText("<p>Unpublished intro</p>");
    content.setFullText("<p>Unpublished full text</p>");
    unpublished.addContent(content);
    entityManager.persist(unpublished);
    entityManager.flush();

    List<ArticleContentSummary> rows = repository.findContentSummariesByAthleteId(ATHLETE_WITH_FIVE_ARTICLES);

    assertEquals(unpublished.getId(), rows.getLast().articleId());
    assertNull(rows.getLast().publishedDate());
  }

  @Test
  void findContentSummariesByAthleteIdIssuesOneStatementRegardlessOfArticleCount() {
    Statistics statistics = statistics();

    long oneArticleStatements = statementCount(statistics, ATHLETE_WITH_ONE_ARTICLE);
    long fiveArticleStatements = statementCount(statistics, ATHLETE_WITH_FIVE_ARTICLES);

    assertEquals(1, oneArticleStatements);
    assertEquals(oneArticleStatements, fiveArticleStatements);
  }

  private Statistics statistics() {
    return entityManagerFactory.unwrap(SessionFactory.class).getStatistics();
  }

  private long statementCount(Statistics statistics, Long athleteId) {
    statistics.clear();
    List<ArticleContentSummary> rows = repository.findContentSummariesByAthleteId(athleteId);

    // Touch every projected column to prove that consuming the rows issues no further statements.
    rows.forEach(row -> {
      row.articleId();
      row.locale();
      row.title();
      row.introText();
      row.slug();
      row.featuredImage();
      row.publishedDate();
    });

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
