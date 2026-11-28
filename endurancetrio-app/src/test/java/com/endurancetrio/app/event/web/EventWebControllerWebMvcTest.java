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

import com.endurancetrio.app.common.handler.EnduranceTrioExceptionHandlerWeb;
import com.endurancetrio.app.common.security.entrypoint.EnduranceTrioAuthEntryPoint;
import com.endurancetrio.app.common.security.provider.EnduranceTrioAuthProvider;
import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.competitor.fixtures.AthleteDTOFixtures;
import com.endurancetrio.app.competitor.fixtures.TeamDTOFixtures;
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.app.config.AppSecurityConfig;
import com.endurancetrio.app.config.WebConfig;
import com.endurancetrio.business.competitor.dto.AthleteDTO;
import com.endurancetrio.business.event.dto.EventOverviewDTO;
import com.endurancetrio.business.event.dto.IndividualRaceResultsDTO;
import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.dto.RaceResultsDTO;
import com.endurancetrio.business.event.dto.RelayRaceResultsDTO;
import com.endurancetrio.business.event.dto.RelayResultDTO;
import com.endurancetrio.business.event.dto.TeamRaceResultsDTO;
import com.endurancetrio.business.event.dto.TeamResultDTO;
import com.endurancetrio.business.event.service.EventService;
import com.endurancetrio.business.event.service.RaceService;
import com.endurancetrio.business.insight.service.InsightService;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.event.model.enumerator.RaceType;
import com.endurancetrio.data.event.model.enumerator.ResultStatus;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.format.support.FormattingConversionService;
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

  private static final RaceDTO RANK_RACE = new RaceDTO(RACE_ID, "Prova Coletiva", "Geral", EVENT_DATE, null,
    List.of("TRIATHLON"), List.of("SPRINT"), RaceType.TEAM_BY_RANK, "COLLECTIVE", null, null,
    ResultStatus.COMPLETE.getCode()
  );

  private static final RaceDTO INCOMPLETE_COLLECTIVE_RACE = new RaceDTO(RACE_ID, "Prova Coletiva", "Geral", EVENT_DATE,
    null, List.of("TRIATHLON"), List.of("SPRINT"), RaceType.TEAM_BY_TIME, "COLLECTIVE", null, null,
    ResultStatus.INCOMPLETE.getCode()
  );

  private static final RaceDTO RELAY_RACE = new RaceDTO(RACE_ID, "Prova de Estafetas", "Geral", EVENT_DATE, null,
      List.of("TRIATHLON"), List.of("SPRINT"), RaceType.TEAM_RELAY_PARENT, "RELAY", null, null,
      ResultStatus.COMPLETE.getCode()
  );

  private static final RaceDTO EMPTY_RELAY_RACE = new RaceDTO(RACE_ID, "Prova de Estafetas", "Geral", EVENT_DATE, null,
      List.of("TRIATHLON"), List.of("SPRINT"), RaceType.TEAM_RELAY_PARENT, "RELAY", null, null,
      ResultStatus.EMPTY.getCode()
  );

  private static final RaceDTO INDIVIDUAL_INCOMPLETE_RACE = new RaceDTO(RACE_ID, "Individual Race", "Geral", EVENT_DATE,
    null, List.of("TRIATHLON"), List.of("SPRINT"), RaceType.INDIVIDUAL_PARENT, "INDIVIDUAL", null, null,
    ResultStatus.INCOMPLETE.getCode()
  );

  private static final EventOverviewDTO EVENT_OVERVIEW = new EventOverviewDTO(EVENT_ID, "Triatlo de Peniche",
    EVENT_DATE, EVENT_DATE, "Peniche", "Peniche", "Leiria", List.of(), List.of(COLLECTIVE_RACE), List.of()
  );

  private static final EventOverviewDTO EVENT_OVERVIEW_WITH_EMPTY_RACE = new EventOverviewDTO(EVENT_ID,
    "Triatlo de Peniche", EVENT_DATE, EVENT_DATE, "Peniche", "Peniche", "Leiria", List.of(), List.of(EMPTY_RACE),
    List.of()
  );

  private static final EventOverviewDTO EVENT_OVERVIEW_WITH_RANK_RACE = new EventOverviewDTO(EVENT_ID,
    "Triatlo de Peniche", EVENT_DATE, EVENT_DATE, "Peniche", "Peniche", "Leiria", List.of(), List.of(RANK_RACE),
    List.of()
  );

  private static final EventOverviewDTO EVENT_OVERVIEW_WITH_INCOMPLETE_COLLECTIVE_RACE = new EventOverviewDTO(EVENT_ID,
    "Triatlo de Peniche", EVENT_DATE, EVENT_DATE, "Peniche", "Peniche", "Leiria", List.of(),
    List.of(INCOMPLETE_COLLECTIVE_RACE), List.of()
  );

  private static final EventOverviewDTO EVENT_OVERVIEW_WITH_INDIVIDUAL_INCOMPLETE_RACE = new EventOverviewDTO(EVENT_ID,
    "Triatlo de Peniche", EVENT_DATE, EVENT_DATE, "Peniche", "Peniche", "Leiria", List.of(),
    List.of(INDIVIDUAL_INCOMPLETE_RACE), List.of()
  );

  private static final EventOverviewDTO EVENT_OVERVIEW_WITH_RELAY_RACE = new EventOverviewDTO(EVENT_ID,
      "Triatlo de Peniche", EVENT_DATE, EVENT_DATE, "Peniche", "Peniche", "Leiria", List.of(), List.of(RELAY_RACE),
      List.of()
  );

  private static final EventOverviewDTO EVENT_OVERVIEW_WITH_EMPTY_RELAY_RACE = new EventOverviewDTO(EVENT_ID,
      "Triatlo de Peniche", EVENT_DATE, EVENT_DATE, "Peniche", "Peniche", "Leiria", List.of(),
      List.of(EMPTY_RELAY_RACE),
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

  @Autowired
  private FormattingConversionService conversionService;

  @BeforeEach
  void setUp() {
    when(eventService.getEventOverview(EVENT_ID, YEAR)).thenReturn(EVENT_OVERVIEW);
    when(insightService.getArticlesCountByEvent(EVENT_ID)).thenReturn(ARTICLES_COUNT);
  }

  @Test
  void mvcConversionServiceShouldResolvePtToPortugueseLocale() {
    // Spring's default converter would map "pt" to Locale.of("pt"), without the country.
    assertEquals(Locale.of("pt", "PT"), conversionService.convert("pt", Locale.class));
    assertEquals(Locale.ENGLISH, conversionService.convert("en", Locale.class));
  }

  @Test
  void eventInsightsWithNoArticlesShouldReturnNotFound() throws Exception {
    when(insightService.getArticlesCountByEvent(EVENT_ID)).thenReturn(0L);

    mockMvc.perform(get("/en/events/" + YEAR + "/" + EVENT_ID + "/insights")).andExpect(status().isNotFound());
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
    assertEquals(3, StringUtils.countOccurrencesOf(html, "data-sort=\"rank\""));
    assertEquals(1, StringUtils.countOccurrencesOf(html, ALPHA_CONTRIBUTOR));
    assertEquals(1, StringUtils.countOccurrencesOf(html, BRAVO_CONTRIBUTOR));
    assertEquals(1, StringUtils.countOccurrencesOf(html, CHARLIE_CONTRIBUTOR));
  }

  @Test
  void collectiveRaceShouldRenderDurationGapForWinnerAndTrailingTeam() throws Exception {
    var winner = new TeamResultDTO(1L, COLLECTIVE_RACE, 1, TeamDTOFixtures.standard(), "Team Alpha", "101", 2,
        Duration.ofMinutes(200), Duration.ZERO, null, null, null, null,
      List.of(contributor(10L, AthleteDTOFixtures.standard(), Duration.ofMinutes(100)))
    );
    var trailing = new TeamResultDTO(2L, COLLECTIVE_RACE, 2, TeamDTOFixtures.standard(), "Team Bravo", "102", 2,
        Duration.ofMinutes(210), Duration.ofMinutes(10), null, null, null, null,
      List.of(contributor(11L, AthleteDTOFixtures.athleteCavaleiro(), Duration.ofMinutes(105)))
    );

    when(raceService.getRaceResults(COLLECTIVE_RACE)).thenReturn(
      new RaceResultsDTO(COLLECTIVE_RACE, new TeamRaceResultsDTO(List.of(winner, trailing))));

    mockMvc.perform(get(RESULTS_PATH))
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("Gap")))
      .andExpect(content().string(containsString("00:00:00")))
      .andExpect(content().string(containsString("00:10:00")));
  }

  @Test
  void collectiveRaceShouldRenderIntegerGapForByRankRace() throws Exception {
    when(eventService.getEventOverview(EVENT_ID, YEAR)).thenReturn(EVENT_OVERVIEW_WITH_RANK_RACE);

    var winner = new TeamResultDTO(1L, RANK_RACE, 1, TeamDTOFixtures.standard(), "Team Alpha", "101", 2, null, null,
        null, null, 5, 0, List.of(contributor(10L, AthleteDTOFixtures.standard(), Duration.ofMinutes(100)))
    );
    var trailing = new TeamResultDTO(2L, RANK_RACE, 2, TeamDTOFixtures.standard(), "Team Bravo", "102", 2, null, null,
        null, null, 8, 3,
      List.of(contributor(11L, AthleteDTOFixtures.athleteCavaleiro(), Duration.ofMinutes(105)))
    );

    when(raceService.getRaceResults(RANK_RACE)).thenReturn(
      new RaceResultsDTO(RANK_RACE, new TeamRaceResultsDTO(List.of(winner, trailing))));

    mockMvc.perform(get(RESULTS_PATH))
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("Gap")))
      .andExpect(content().string(containsString("<span>0</span>")))
      .andExpect(content().string(containsString("<span>3</span>")));
  }

  @Test
  void collectiveRaceShouldOmitExpandControlsWhenTeamHasNoContributors() throws Exception {
    var withContributors = teamResult(1L, 1, "Team Alpha", "101", 1, Duration.ofMinutes(200), 5,
      List.of(contributor(10L, AthleteDTOFixtures.standard(), Duration.ofMinutes(200)))
    );
    var withoutContributors = teamResult(2L, 2, "Team Bravo", "102", 0, Duration.ofMinutes(210), 8, List.of());

    when(raceService.getRaceResults(COLLECTIVE_RACE)).thenReturn(
      new RaceResultsDTO(COLLECTIVE_RACE, new TeamRaceResultsDTO(List.of(withContributors, withoutContributors))));

    String html = mockMvc.perform(get(RESULTS_PATH))
      .andExpect(status().isOk())
      .andReturn()
      .getResponse()
      .getContentAsString();

    assertEquals(1, StringUtils.countOccurrencesOf(html, "class=\"expand\""));
    assertEquals(1, StringUtils.countOccurrencesOf(html, "data-expand-content"));
    assertEquals(1, StringUtils.countOccurrencesOf(html, "type=\"button\""));
  }

  @Test
  void collectiveRaceWithResultsShouldRenderIncompleteStatus() throws Exception {
    when(eventService.getEventOverview(EVENT_ID, YEAR)).thenReturn(EVENT_OVERVIEW_WITH_INCOMPLETE_COLLECTIVE_RACE);
    var team = teamResult(1L, 1, "Team Alpha", "101", 1, Duration.ofMinutes(200), 5,
      List.of(contributor(10L, AthleteDTOFixtures.standard(), Duration.ofMinutes(200)))
    );

    when(raceService.getRaceResults(INCOMPLETE_COLLECTIVE_RACE)).thenReturn(
      new RaceResultsDTO(INCOMPLETE_COLLECTIVE_RACE, new TeamRaceResultsDTO(List.of(team))));

    mockMvc.perform(get(RESULTS_PATH))
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("Help complete this race")));
  }

  @Test
  void collectiveRaceWithNoResultsShouldNotRenderIncompleteStatus() throws Exception {
    when(eventService.getEventOverview(EVENT_ID, YEAR)).thenReturn(EVENT_OVERVIEW_WITH_INCOMPLETE_COLLECTIVE_RACE);
    when(raceService.getRaceResults(INCOMPLETE_COLLECTIVE_RACE)).thenReturn(
      new RaceResultsDTO(INCOMPLETE_COLLECTIVE_RACE, new TeamRaceResultsDTO(List.of())));

    mockMvc.perform(get(RESULTS_PATH))
      .andExpect(status().isOk())
      .andExpect(content().string(not(containsString("Help complete this race"))));
  }

  @Test
  void individualRaceWithNoResultsShouldNotRenderIncompleteStatus() throws Exception {
    when(eventService.getEventOverview(EVENT_ID, YEAR)).thenReturn(EVENT_OVERVIEW_WITH_INDIVIDUAL_INCOMPLETE_RACE);
    when(raceService.getRaceResults(INDIVIDUAL_INCOMPLETE_RACE)).thenReturn(
      new RaceResultsDTO(INDIVIDUAL_INCOMPLETE_RACE, new IndividualRaceResultsDTO(Map.of(AgeGroup.OPEN, List.of()))));

    mockMvc.perform(get(RESULTS_PATH))
      .andExpect(status().isOk())
      .andExpect(content().string(not(containsString("Help complete this race"))));
  }

  @Test
  void individualRaceWithResultsShouldRenderIncompleteStatus() throws Exception {
    when(eventService.getEventOverview(EVENT_ID, YEAR)).thenReturn(EVENT_OVERVIEW_WITH_INDIVIDUAL_INCOMPLETE_RACE);
    var result = new IndividualResultDTO(10L, INDIVIDUAL_INCOMPLETE_RACE, 1, null, AthleteDTOFixtures.standard(),
      AgeGroup.OPEN, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      Duration.ofMinutes(100), Duration.ZERO, null
    );
    when(raceService.getRaceResults(INDIVIDUAL_INCOMPLETE_RACE)).thenReturn(
      new RaceResultsDTO(INDIVIDUAL_INCOMPLETE_RACE,
        new IndividualRaceResultsDTO(Map.of(AgeGroup.OPEN, List.of(result)))
      ));

    mockMvc.perform(get(RESULTS_PATH))
      .andExpect(status().isOk())
      .andExpect(content().string(containsString("Help complete this race")));
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

  @Test
  void relayRaceShouldRenderRelayTableWithExpandableContributors() throws Exception {
    when(eventService.getEventOverview(EVENT_ID, YEAR)).thenReturn(EVENT_OVERVIEW_WITH_RELAY_RACE);

    var alpha = relayResult(1L, 1, AgeGroup.OPEN, Duration.ofMinutes(150), Duration.ZERO,
        List.of(contributor(10L, AthleteDTOFixtures.standard(), Duration.ofMinutes(37)))
    );
    var bravo = relayResult(2L, 2, AgeGroup.OPEN, Duration.ofMinutes(160), Duration.ofMinutes(10), List.of());

    when(raceService.getRaceResults(RELAY_RACE)).thenReturn(
        new RaceResultsDTO(RELAY_RACE, new RelayRaceResultsDTO(Map.of(AgeGroup.OPEN, List.of(alpha, bravo)))));

    String html = mockMvc.perform(get(RESULTS_PATH))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Team Alpha")))
        .andExpect(content().string(containsString("Team Bravo")))
        .andExpect(content().string(containsString("OPEN")))
        .andExpect(content().string(containsString("00:00:00")))
        .andExpect(content().string(containsString("00:10:00")))
        .andExpect(content().string(containsString(ALPHA_CONTRIBUTOR)))
        .andReturn()
        .getResponse()
        .getContentAsString();

    assertEquals(1, StringUtils.countOccurrencesOf(html, "class=\"expand\""));
    assertEquals(1, StringUtils.countOccurrencesOf(html, "data-expand-content"));
    assertEquals(1, StringUtils.countOccurrencesOf(html, "data-sort=\"rank\""));
    assertEquals(1, StringUtils.countOccurrencesOf(html, ALPHA_CONTRIBUTOR));
  }

  @Test
  void relayRaceWithMultipleAgeGroupsShouldRenderTabs() throws Exception {
    when(eventService.getEventOverview(EVENT_ID, YEAR)).thenReturn(EVENT_OVERVIEW_WITH_RELAY_RACE);

    var open = relayResult(1L, 1, AgeGroup.OPEN, Duration.ofMinutes(150), Duration.ZERO, List.of());
    var sen = relayResult(2L, 1, AgeGroup.SEN, Duration.ofMinutes(160), Duration.ZERO, List.of());

    when(raceService.getRaceResults(RELAY_RACE)).thenReturn(new RaceResultsDTO(RELAY_RACE,
        new RelayRaceResultsDTO(Map.of(AgeGroup.OPEN, List.of(open), AgeGroup.SEN, List.of(sen)))
    ));

    mockMvc.perform(get(RESULTS_PATH))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Team Alpha")))
        .andExpect(content().string(containsString("Team Bravo")))
        .andExpect(content().string(containsString("SEN")));
  }

  @Test
  void relayRaceWithNoResultsShouldRenderResultStatus() throws Exception {
    when(eventService.getEventOverview(EVENT_ID, YEAR)).thenReturn(EVENT_OVERVIEW_WITH_EMPTY_RELAY_RACE);
    when(raceService.getRaceResults(EMPTY_RELAY_RACE)).thenReturn(
        new RaceResultsDTO(EMPTY_RELAY_RACE, new RelayRaceResultsDTO(Map.of(AgeGroup.OPEN, List.of()))));

    mockMvc.perform(get(RESULTS_PATH))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("This race was planned but had no registered participants")))
        .andExpect(content().string(not(containsString("data-expand"))));
  }

  private static RelayResultDTO relayResult(
      Long id, Integer rank, AgeGroup ageGroup, Duration total, Duration gap,
      List<IndividualResultDTO> contributors
  ) {
    return new RelayResultDTO(id, RELAY_RACE, rank, null, TeamDTOFixtures.standard(),
        id == 1 ? "Team Alpha" : "Team Bravo",
        ageGroup, null, "10" + id, 3, null, null, null, null, null, null, null, null, null, null, total, gap,
        contributors
    );
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
    return new TeamResultDTO(id, COLLECTIVE_RACE, rank, TeamDTOFixtures.standard(), teamName, bib, athletesCount, total,
        null, null, null, cumulativeRank, null, contributors
    );
  }

  @SpringBootConfiguration
  @Import(
    {
        AppSecurityConfig.class, WebConfig.class, MessageService.class, EnduranceTrioExceptionHandlerWeb.class,
        EventWebController.class
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
