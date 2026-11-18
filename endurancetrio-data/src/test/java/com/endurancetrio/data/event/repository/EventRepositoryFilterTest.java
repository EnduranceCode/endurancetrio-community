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

package com.endurancetrio.data.event.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = EventRepositoryFilterTest.TestApplication.class)
@Sql(scripts = "/sql/event-directory-before.sql")
@Sql(scripts = "/sql/event-directory-after.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
class EventRepositoryFilterTest {

  private static final String DISTRICT_A = "Test District A";
  private static final String DISTRICT_B = "Test District B";

  @Autowired
  private EventRepository repository;

  @Test
  void distinctDistrictCountyPairsAreOrderedAndDeduplicated() {
    List<String> fixturePairs = repository.findDistinctDistrictCountyPairs()
        .stream()
        .filter(pair -> pair.getDistrict().startsWith("Test District"))
        .map(pair -> pair.getDistrict() + "/" + pair.getCounty())
        .toList();

    assertEquals(List.of("Test District A/County Águeda", "Test District A/County B", "Test District B/County C"),
        fixturePairs
    );
  }

  @Test
  void filteredIdsAreOrderedByStartDateThenId() {
    Page<Long> page = repository.findEventIdsByFilter(null, null, DISTRICT_A, null, PageRequest.of(0, 10));

    assertEquals(List.of(990102L, 990101L, 990103L), page.getContent());
  }

  @Test
  void countQueryMatchesFilteredResults() {
    Page<Long> page = repository.findEventIdsByFilter(null, null, DISTRICT_A, null, PageRequest.of(0, 1));

    assertEquals(List.of(990102L), page.getContent());
    assertEquals(3, page.getTotalElements());
    assertEquals(3, page.getTotalPages());
  }

  @Test
  void combinesAllFilters() {
    Page<Long> page = repository.findEventIdsByFilter("maratona", 2024, DISTRICT_B, "County C", PageRequest.of(0, 10));

    assertEquals(List.of(990104L), page.getContent());
  }

  @Test
  void foldedSearchFindsAccentedTitle() {
    Page<Long> page = repository.findEventIdsByFilter("sao joao", null, DISTRICT_A, null, PageRequest.of(0, 10));

    assertEquals(List.of(990101L), page.getContent());
  }

  @Test
  void searchIsCaseInsensitive() {
    Page<Long> page = repository.findEventIdsByFilter("MARATONA", null, DISTRICT_A, null, PageRequest.of(0, 10));

    assertEquals(List.of(990103L), page.getContent());
  }

  @Test
  void searchEscapesPercentWildcard() {
    Page<Long> page = repository.findEventIdsByFilter("percent\\%", null, null, null, PageRequest.of(0, 10));

    assertTrue(page.getContent().contains(990105L));
  }

  @Test
  void searchEscapesUnderscoreWildcard() {
    Page<Long> page = repository.findEventIdsByFilter("under\\_score", null, null, null, PageRequest.of(0, 10));

    assertTrue(page.getContent().contains(990106L));
  }

  @SpringBootConfiguration
  @EnableAutoConfiguration
  @EntityScan("com.endurancetrio.data")
  @EnableJpaRepositories("com.endurancetrio.data")
  static class TestApplication {

  }
}
