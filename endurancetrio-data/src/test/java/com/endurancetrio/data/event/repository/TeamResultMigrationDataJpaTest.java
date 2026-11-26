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
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = TeamResultMigrationDataJpaTest.TestApplication.class)
class TeamResultMigrationDataJpaTest {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  private static final List<String> DURATION_COLUMNS = List.of("swim", "first_bike", "first_run", "t1", "bike", "t2",
      "run", "second_run", "t3", "second_bike", "total"
  );

  @Test
  void teamResultHasDurationColumnsInIndividualResultOrder() {
    List<String> teamResultColumns = durationColumnsOf("team_result");
    List<String> individualResultColumns = durationColumnsOf("individual_result");

    assertEquals(DURATION_COLUMNS, teamResultColumns);
    assertEquals(DURATION_COLUMNS, individualResultColumns);
  }

  @Test
  void migrationPreservesExistingTeamResultRows() {
    Integer rowCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM endurancetrio_hub.team_result", Integer.class);

    assertTrue(rowCount != null && rowCount > 0, "team_result must contain rows after migration");
  }

  @Test
  void migrationPreservesTeamResultIndividualResultLinks() {
    Integer linkCount = jdbcTemplate.queryForObject(
        "SELECT COUNT(*) FROM endurancetrio_hub.team_result_individual_result", Integer.class);

    assertTrue(linkCount != null && linkCount > 0, "team_result_individual_result links must remain");
  }

  private List<String> durationColumnsOf(String tableName) {
    return jdbcTemplate.queryForList(
        "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS " + "WHERE UPPER(TABLE_SCHEMA) = 'ENDURANCETRIO_HUB' "
            + "AND UPPER(TABLE_NAME) = UPPER(?) " + "ORDER BY ORDINAL_POSITION", String.class, tableName
    ).stream().map(String::toLowerCase).filter(DURATION_COLUMNS::contains).toList();
  }

  @SpringBootConfiguration
  @EnableAutoConfiguration
  @org.springframework.boot.persistence.autoconfigure.EntityScan("com.endurancetrio.data")
  @EnableJpaRepositories("com.endurancetrio.data")
  static class TestApplication {

  }
}
