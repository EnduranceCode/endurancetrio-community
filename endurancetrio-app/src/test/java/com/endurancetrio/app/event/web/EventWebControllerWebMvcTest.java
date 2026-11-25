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

package com.endurancetrio.app.event.web;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.endurancetrio.app.common.security.entrypoint.EnduranceTrioAuthEntryPoint;
import com.endurancetrio.app.common.security.provider.EnduranceTrioAuthProvider;
import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.competitor.fixtures.AthleteDTOFixtures;
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.app.config.AppSecurityConfig;
import com.endurancetrio.app.config.WebConfig;
import com.endurancetrio.business.competitor.dto.AthleteDTO;
import com.endurancetrio.business.event.dto.EventOverviewDTO;
import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.dto.RaceResultsDTO;
import com.endurancetrio.business.event.dto.TeamRaceResultsDTO;
import com.endurancetrio.business.event.dto.TeamResultDTO;
import com.endurancetrio.business.event.service.EventService;
import com.endurancetrio.business.event.service.RaceService;
import com.endurancetrio.business.insight.service.InsightService;
import com.endurancetrio.data.event.model.enumerator.RaceType;
import com.endurancetrio.data.event.model.enumerator.ResultStatus;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.StringUtils;

@WebMvcTest(useDefaultFilters = false)
@ActiveProfiles("test")
class EventWebControllerWebMvcTest {

  private static final Long EVENT_ID = 23L;
  private static final Long RACE_ID = 91L;
  private static final int YEAR = 1987;
  private static final String RESULTS_PATH = "/en/events/" + YEAR + "/" + EVENT_ID + "/results/" + RACE_ID;
  private static final long ARTICLES_COUNT = 3L;
  private static final LocalDate EVENT_DATE = LocalDate.of(YEAR, Month.JUNE, 6);

  private static final RaceDTO COLLECTIVE_RACE = new RaceDTO(RACE_ID, "Prova Coletiva", "Geral", EVENT_DATE, null,
    List.of("TRIATHLON"), List.of("SPRINT"), RaceType.TEAM_BY_TIME, "COLLECTIVE", null, null,
    ResultStatus.COMPLETE.getCode()
  );

  private static final RaceDTO EMPTY_RACE = new RaceDTO(RACE_ID, "Prova Coletiva", "Geral", EVENT_DATE, null,
    List.of("TRIATHLON"), List.of("SPRINT"), RaceType.TEAM_BY_TIME, "COLLECTIVE", null, null,
    ResultStatus.EMPTY.getCode()
  );

  private static final EventOverviewDTO EVENT_OVERVIEW = new EventOverviewDTO(EVENT_ID, "Triatlo de Peniche",
    EVENT_DATE, EVENT_DATE, "Peniche", "Peniche", "Leiria", List.of(), List.of(COLLECTIVE_RACE), List.of()
  );

  private static final EventOverviewDTO EVENT_OVERVIEW_WITH_EMPTY_RACE = new EventOverviewDTO(EVENT_ID,
    "Triatlo de Peniche", EVENT_DATE, EVENT_DATE, "Peniche", "Peniche", "Leiria", List.of(), List.of(EMPTY_RACE),
    List.of()
  );

  private static final String ALPHA_CONTRIBUTOR = AthleteDTOFixtures.standard().knownName();
  private static final String BRAVO_CONTRIBUTOR = AthleteDTOFixtures.athleteCavaleiro().knownName();
  private static final String CHARLIE_CONTRIBUTOR = AthleteDTOFixtures.athleteBello().knownName();

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private EventService eventService;

  @Autowired
  private InsightService insightService;

  @Autowired
  private RaceService raceService;

  @BeforeEach
  void setUp() {
    when(eventService.getEventOverview(EVENT_ID, YEAR)).thenReturn(EVENT_OVERVIEW);
    when(insightService.getArticlesCountByEvent(EVENT_ID)).thenReturn(ARTICLES_COUNT);
  }

  @Test
  void collectiveRaceShouldRenderTeamTableWithExpandableContributors() throws Exception {
    TeamResultDTO alpha = teamResult(1L, 1, "Team Alpha", "101", 2, Duration.ofMinutes(200), 5,
      List.of(contributor(10L, AthleteDTOFixtures.standard(), Duration.ofMinutes(100)),
        contributor(11L, AthleteDTOFixtures.athleteCavaleiro(), Duration.ofMinutes(100))
      )
    );
    TeamResultDTO bravo = teamResult(2L, 2, "Team Bravo", "102", 1, Duration.ofMinutes(210), 8,
      List.of(contributor(12L, AthleteDTOFixtures.athleteBello(), Duration.ofMinutes(210)))
    );

    when(raceService.getRaceResults(COLLECTIVE_RACE)).thenReturn(
      new RaceResultsDTO(COLLECTIVE_RACE, new TeamRaceResultsDTO(List.of(alpha, bravo))));

    String html = mockMvc.perform(get(RESULTS_PATH))
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("Team Alpha")))
      .andExpect(content().string(containsString("Team Bravo")))
      .andExpect(content().string(containsString("Cumulative Rank")))
      .andExpect(content().string(containsString(ALPHA_CONTRIBUTOR)))
      .andExpect(content().string(containsString(BRAVO_CONTRIBUTOR)))
      .andExpect(content().string(containsString(CHARLIE_CONTRIBUTOR)))
      .andReturn()
      .getResponse()
      .getContentAsString();

    assertEquals(2, StringUtils.countOccurrencesOf(html, "class=\"expand\""));
    assertEquals(2, StringUtils.countOccurrencesOf(html, "data-expand-content"));
    assertEquals(1, StringUtils.countOccurrencesOf(html, ALPHA_CONTRIBUTOR));
    assertEquals(1, StringUtils.countOccurrencesOf(html, BRAVO_CONTRIBUTOR));
    assertEquals(1, StringUtils.countOccurrencesOf(html, CHARLIE_CONTRIBUTOR));
  }

  @Test
  void collectiveRaceWithNoTeamResultsShouldRenderResultStatus() throws Exception {
    when(eventService.getEventOverview(EVENT_ID, YEAR)).thenReturn(EVENT_OVERVIEW_WITH_EMPTY_RACE);
    when(raceService.getRaceResults(EMPTY_RACE)).thenReturn(
      new RaceResultsDTO(EMPTY_RACE, new TeamRaceResultsDTO(List.of())));

    mockMvc.perform(get(RESULTS_PATH))
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("This race was planned but had no registered participants")))
      .andExpect(content().string(not(containsString("data-expand"))));
  }

  private static IndividualResultDTO contributor(Long id, AthleteDTO athlete, Duration total) {
    return new IndividualResultDTO(id, COLLECTIVE_RACE, null, null, athlete, null, null, null, null, null, null, null,
      null, null, null, null, null, null, null, null, total, null, null
    );
  }

  private static TeamResultDTO teamResult(
    Long id, Integer rank, String teamName, String bib, Integer athletesCount,
    Duration total, Integer cumulativeRank, List<IndividualResultDTO> contributors
  ) {
    return new TeamResultDTO(id, COLLECTIVE_RACE, rank, teamName, bib, athletesCount, total, null, null, null,
      cumulativeRank, null, contributors
    );
  }

  @SpringBootConfiguration
  @Import(
    {
      AppSecurityConfig.class, WebConfig.class, MessageService.class, EventWebController.class
    }
  )
  static class TestApplication {

    @Bean
    AppProperties appProperties() {
      AppProperties properties = new AppProperties();
      properties.setCopyrightYear("2026");
      properties.setSiteUrl("https://endurancetrio.com");
      properties.setVersion("0.4.0");
      properties.getOpenGraph().setDefaultImg("/img/endurancetrio-open-graph.png");
      properties.getOpenGraph().setDefaultImgWidth(1200);
      properties.getOpenGraph().setDefaultImgHeight(628);
      return properties;
    }

    @Bean
    EnduranceTrioAuthEntryPoint enduranceTrioAuthEntryPoint() {
      return mock(EnduranceTrioAuthEntryPoint.class);
    }

    @Bean
    EnduranceTrioAuthProvider enduranceTrioAuthProvider() {
      return mock(EnduranceTrioAuthProvider.class);
    }

    @Bean
    EventService eventService() {
      return mock(EventService.class);
    }

    @Bean
    InsightService insightService() {
      return mock(InsightService.class);
    }

    @Bean
    RaceService raceService() {
      return mock(RaceService.class);
    }
  }
}
