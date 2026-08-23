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

package com.endurancetrio.data.competitor.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.endurancetrio.data.competitor.model.entity.Athlete;
import com.endurancetrio.data.competitor.repository.projection.AthleteFirstLetterCount;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = AthleteRepositoryFilterTest.TestApplication.class)
@Sql(scripts = "/sql/athlete-directory-before.sql")
@Sql(scripts = "/sql/athlete-directory-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class AthleteRepositoryFilterTest {

  private static final String FROM = "áàâãäåéèêëíìîïóòôõöøúùûüýÿñçšž";
  private static final String TO = "aaaaaaeeeeiiiiooooouuuuyyyncsz";

  @org.springframework.beans.factory.annotation.Autowired
  private AthleteRepository repository;

  @Test
  void foldedSearchFindsAccentedName() {
    var page = repository.findAll(search("joao"), sortedPageable());

    assertTrue(page.getContent().stream().map(Athlete::getKnownName).anyMatch("João Matos"::equals));
  }

  @Test
  void searchEscapesPercentWildcard() {
    var page = repository.findAll(search("percent\\%"), sortedPageable());

    assertEquals(List.of("Percent% Athlete"), page.getContent().stream().map(Athlete::getKnownName).toList());
  }

  @Test
  void searchEscapesUnderscoreWildcard() {
    var page = repository.findAll(search("under\\_score"), sortedPageable());

    assertEquals(List.of("Under_score Athlete"), page.getContent().stream().map(Athlete::getKnownName).toList());
  }

  @Test
  void histogramReturnsFoldedLetters() {
    List<AthleteFirstLetterCount> counts = repository.findGlobalFirstLetterCounts();

    assertTrue(counts.stream().anyMatch(count -> "a".equals(count.getLetter()) && count.getTotal() >= 2));
    assertTrue(counts.stream().anyMatch(count -> "j".equals(count.getLetter())));
  }

  private static Specification<Athlete> search(String term) {
    return (root, query, cb) -> {
      Expression<String> foldedTerm = folded(cb.literal(term), cb);
      Expression<String> pattern = cb.concat(cb.literal("%"), cb.concat(foldedTerm, cb.literal("%")));
      return cb.or(cb.like(folded(root.get("knownName"), cb), pattern, '\\'),
          cb.like(folded(root.get("longName"), cb), pattern, '\\')
      );
    };
  }

  private static Expression<String> folded(Expression<String> expression, CriteriaBuilder cb) {
    return cb.function("translate", String.class, cb.lower(expression), cb.literal(FROM), cb.literal(TO));
  }

  private static PageRequest sortedPageable() {
    return PageRequest.of(0, 30, Sort.by("knownName").and(Sort.by("id")));
  }

  @SpringBootConfiguration
  @EnableAutoConfiguration
  @EntityScan("com.endurancetrio.data")
  @EnableJpaRepositories("com.endurancetrio.data")
  static class TestApplication {

  }
}
