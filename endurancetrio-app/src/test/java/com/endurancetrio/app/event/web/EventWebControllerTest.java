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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.competitor.fixtures.AthleteDTOFixtures;
import com.endurancetrio.app.competitor.fixtures.TeamDTOFixtures;
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.app.insight.fixtures.ArticleDTOFixtures;
import com.endurancetrio.business.common.dto.PaginationDTO;
import com.endurancetrio.business.competitor.dto.AthleteDTO;
import com.endurancetrio.business.competitor.dto.TeamDTO;
import com.endurancetrio.business.event.dto.EventDTO;
import com.endurancetrio.business.event.dto.EventOverviewDTO;
import com.endurancetrio.business.event.dto.EventsPageDTO;
import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.dto.RaceResultsDTO;
import com.endurancetrio.business.event.dto.YearsWithEventsDTO;
import com.endurancetrio.business.event.service.EventService;
import com.endurancetrio.business.event.service.RaceService;
import com.endurancetrio.business.insight.dto.ArticleDTO;
import com.endurancetrio.business.insight.dto.InsightPageDTO;
import com.endurancetrio.business.insight.service.InsightService;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.competitor.model.enumerator.ParaClass;
import com.endurancetrio.data.event.model.enumerator.Penalty;
import com.endurancetrio.data.event.model.enumerator.RaceType;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import org.hamcrest.Matchers;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ExtendWith(MockitoExtension.class)
class EventWebControllerTest {

  private static final List<Integer> ALL_YEARS = List.of(1989, 1988, 1987, 1986, 1985, 1984, 1983,
      1982
  );
  private static final YearsWithEventsDTO PAGE_0 = new YearsWithEventsDTO(List.of(1989, 1988, 1987),
      List.of(1986, 1985, 1984), List.of(), 0, 3, 8, 3, -1, 2
  );
  private static final YearsWithEventsDTO PAGE_1 = new YearsWithEventsDTO(List.of(1986, 1985, 1984),
      List.of(1983, 1982), List.of(1989, 1988, 1987), 1, 3, 8, 3, 0, 2
  );
  private static final YearsWithEventsDTO PAGE_2 = new YearsWithEventsDTO(List.of(1983, 1982),
      List.of(), List.of(1986, 1985, 1984), 2, 3, 8, 3, 0, -1
  );
  private static final LocalDate EVENT_DATE = LocalDate.of(1984, Month.AUGUST, 15);
  private static final EventDTO EVENT_DTO = new EventDTO(1L, "Triatlo de Peniche", EVENT_DATE,
      EVENT_DATE, "Peniche", "Peniche", "Leiria", List.of("TRIATHLON")
  );
  private static final EventsPageDTO PAGE_WITH_EVENTS = new EventsPageDTO(List.of(EVENT_DTO),
      new PaginationDTO(0, 10, 1, true, false)
  );
  private static final EventsPageDTO PAGE_WITH_EVENTS_PT = new EventsPageDTO(List.of(EVENT_DTO),
      new PaginationDTO(0, 10, 1, true, false)
  );
  private static final EventsPageDTO PAGE_WITH_EVENTS_TITLE_TEST = new EventsPageDTO(
      List.of(EVENT_DTO), new PaginationDTO(0, 10, 55, true, false)
  );
  private static final EventsPageDTO PAGE_EMPTY = new EventsPageDTO(List.of(),
      new PaginationDTO(0, 0, 0, false, false)
  );
  private static final EventOverviewDTO EVENT_OVERVIEW = new EventOverviewDTO(
      1L, "Triatlo de Peniche", EVENT_DATE, EVENT_DATE, "Peniche", "Peniche", "Leiria",
      List.of(), List.of(), List.of()
  );
  private static final RaceDTO RACE_1 = new RaceDTO(1L, "Triatlo de Peniche", "Geral", EVENT_DATE,
      null, List.of("TRIATHLON"), List.of("SPRINT"), RaceType.INDIVIDUAL_PARENT, "STANDARD", null,
      null, "UNKNOWN"
  );
  private static final RaceDTO RACE_2 = new RaceDTO(2L, "Triatlo de Peniche", "Estafetas",
      EVENT_DATE, null, List.of("TRIATHLON"), List.of("SPRINT"), RaceType.TEAM_RELAY_PARENT,
      "RELAY", null, null, "UNKNOWN"
  );
  private static final RaceDTO RACE_3 = new RaceDTO(3L, "Triatlo de Peniche", "Para", EVENT_DATE,
      null, List.of("TRIATHLON"), List.of("SPRINT"), RaceType.INDIVIDUAL_PARENT, "PARA", null, null,
      "UNKNOWN"
  );

  private static final EventOverviewDTO EVENT_OVERVIEW_WITH_RACES = new EventOverviewDTO(
      1L, "Triatlo de Peniche", EVENT_DATE, EVENT_DATE, "Peniche", "Peniche", "Leiria",
      List.of(), List.of(RACE_1, RACE_2, RACE_3), List.of()
  );

  private static final ArticleDTO INSIGHT_ARTICLE_EN = ArticleDTOFixtures.standard();
  private static final ArticleDTO INSIGHT_ARTICLE_PT = ArticleDTOFixtures.portuguese();
  private static final PaginationDTO INSIGHT_PAGINATION = new PaginationDTO(0, 1, 1, false, false);
  private static final InsightPageDTO EVENT_INSIGHT_PAGE = new InsightPageDTO(
      List.of(INSIGHT_ARTICLE_EN), INSIGHT_PAGINATION);
  private static final InsightPageDTO EVENT_INSIGHT_PAGE_PT = new InsightPageDTO(
      List.of(INSIGHT_ARTICLE_PT), INSIGHT_PAGINATION);

  @Mock
  MessageService messageService;

  @Mock
  EventService eventService;

  @Mock
  InsightService insightService;

  @Mock
  RaceService raceService;

  AppProperties appProperties;

  EventWebController eventWebController;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    appProperties = new AppProperties();
    appProperties.getOpenGraph().setDefaultImg("/img/endurancetrio-open-graph.png");
    appProperties.getOpenGraph().setDefaultImgWidth(1200);
    appProperties.getOpenGraph().setDefaultImgHeight(628);
    appProperties.getSocial().setFacebookPageId("1692877750958091");
    appProperties.getSocial().setTwitterSite("@EnduranceTrio");

    eventWebController = new EventWebController(messageService, appProperties, eventService,
        insightService, raceService
    );

    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/views/");
    viewResolver.setSuffix(".html");

    mockMvc = MockMvcBuilders.standaloneSetup(eventWebController)
        .setViewResolvers(viewResolver)
        .build();
  }

  @Test
  void eventYearsPageWithEnglishLocale() throws Exception {
    when(messageService.getMessage(eq("page.events.metadata.title"), any(), any())).thenReturn(
        "Events - EnduranceTrio");
    when(
        messageService.getMessage(eq("page.events.metadata.description"), any(), any())).thenReturn(
        "Browse endurance sports events by year");
    when(eventService.getEventYears()).thenReturn(ALL_YEARS);

    mockMvc.perform(get("/en/events"))
        .andExpect(status().isOk())
        .andExpect(view().name("years-with-events"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attributeExists("metadata"))
        .andExpect(model().attribute("yearsWithEvents", PAGE_0));
  }

  @Test
  void eventYearsPageWithPortugueseLocale() throws Exception {
    when(messageService.getMessage(eq("page.events.metadata.title"), any(), any())).thenReturn(
        "Eventos - EnduranceTrio");
    when(
        messageService.getMessage(eq("page.events.metadata.description"), any(), any())).thenReturn(
        "Explore eventos de desportos de endurance por ano");
    when(eventService.getEventYears()).thenReturn(ALL_YEARS);

    mockMvc.perform(get("/pt/events"))
        .andExpect(status().isOk())
        .andExpect(view().name("years-with-events"))
        .andExpect(model().attribute("language", "pt"))
        .andExpect(model().attributeExists("metadata"))
        .andExpect(model().attribute("yearsWithEvents", PAGE_0));
  }

  @Test
  void eventYearsPageMetadataHasCorrectTitle() throws Exception {
    when(messageService.getMessage(eq("page.events.metadata.title"), any(), any())).thenReturn(
        "Events - EnduranceTrio");
    when(
        messageService.getMessage(eq("page.events.metadata.description"), any(), any())).thenReturn(
        "Browse endurance sports events by year");
    when(eventService.getEventYears()).thenReturn(ALL_YEARS);

    mockMvc.perform(get("/en/events"))
        .andExpect(model().attribute("metadata", org.hamcrest.Matchers.hasProperty("title",
                org.hamcrest.Matchers.is("Events - EnduranceTrio")
            )
        ));
  }

  @Test
  void eventYearsPageSecondPage() throws Exception {
    when(messageService.getMessage(eq("page.events.metadata.title"), any(), any())).thenReturn(
        "Events - EnduranceTrio");
    when(
        messageService.getMessage(eq("page.events.metadata.description"), any(), any())).thenReturn(
        "Browse endurance sports events by year");
    when(eventService.getEventYears()).thenReturn(ALL_YEARS);

    mockMvc.perform(get("/en/events").param("page", "1"))
        .andExpect(status().isOk())
        .andExpect(view().name("years-with-events"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attribute("yearsWithEvents", PAGE_1));
  }

  @Test
  void eventYearsPageThirdPage() throws Exception {
    when(messageService.getMessage(eq("page.events.metadata.title"), any(), any())).thenReturn(
        "Events - EnduranceTrio");
    when(
        messageService.getMessage(eq("page.events.metadata.description"), any(), any())).thenReturn(
        "Browse endurance sports events by year");
    when(eventService.getEventYears()).thenReturn(ALL_YEARS);

    mockMvc.perform(get("/en/events").param("page", "2"))
        .andExpect(status().isOk())
        .andExpect(view().name("years-with-events"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attribute("yearsWithEvents", PAGE_2));
  }

  @Test
  void eventsYearPageWithEnglishLocale() throws Exception {
    when(messageService.getMessage(eq("page.events.year.metadata.title"), any(), any())).thenReturn(
        "Events by year - EnduranceTrio");
    when(messageService.getMessage(eq("page.events.year.metadata.description"), any(),
        any()
    )).thenReturn("Browse the endurance sports events by year");
    when(eventService.getEventsByYear(eq(1984), ArgumentMatchers.any())).thenReturn(
        PAGE_WITH_EVENTS);

    mockMvc.perform(get("/en/events/1984"))
        .andExpect(status().isOk())
        .andExpect(view().name("events-by-year"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attributeExists("metadata"))
        .andExpect(model().attribute("year", 1984))
        .andExpect(model().attribute("events", PAGE_WITH_EVENTS.events()))
        .andExpect(model().attribute("pagination", PAGE_WITH_EVENTS.pagination()));
  }

  @Test
  void eventsYearPageWithPortugueseLocale() throws Exception {
    when(messageService.getMessage(eq("page.events.year.metadata.title"), any(), any())).thenReturn(
        "Eventos por ano - EnduranceTrio");
    when(messageService.getMessage(eq("page.events.year.metadata.description"), any(),
        any()
    )).thenReturn("Explore eventos de desportos de endurance por ano");
    when(eventService.getEventsByYear(eq(1984), ArgumentMatchers.any())).thenReturn(
        PAGE_WITH_EVENTS_PT);

    mockMvc.perform(get("/pt/events/1984"))
        .andExpect(status().isOk())
        .andExpect(view().name("events-by-year"))
        .andExpect(model().attribute("language", "pt"))
        .andExpect(model().attributeExists("metadata"))
        .andExpect(model().attribute("year", 1984))
        .andExpect(model().attribute("events", PAGE_WITH_EVENTS_PT.events()))
        .andExpect(model().attribute("pagination", PAGE_WITH_EVENTS_PT.pagination()));
  }

  @Test
  void eventsYearPageMetadataHasCorrectTitle() throws Exception {
    when(messageService.getMessage(eq("page.events.year.metadata.title"), any(), any())).thenReturn(
        "Events by year - EnduranceTrio");
    when(messageService.getMessage(eq("page.events.year.metadata.description"), any(),
        any()
    )).thenReturn("Browse the endurance sports events by year");
    when(eventService.getEventsByYear(eq(1984), ArgumentMatchers.any())).thenReturn(
        PAGE_WITH_EVENTS_TITLE_TEST);

    mockMvc.perform(get("/en/events/1984"))
        .andExpect(model().attribute("metadata", org.hamcrest.Matchers.hasProperty("title",
                org.hamcrest.Matchers.is("Events by year - EnduranceTrio")
            )
        ));
  }

  @Test
  void eventsYearPageEmptyYear() throws Exception {
    when(messageService.getMessage(eq("page.events.year.metadata.title"), any(), any())).thenReturn(
        "Events by year - EnduranceTrio");
    when(messageService.getMessage(eq("page.events.year.metadata.description"), any(),
        any()
    )).thenReturn("Browse the endurance sports events by year");
    when(eventService.getEventsByYear(eq(1985), ArgumentMatchers.any())).thenReturn(PAGE_EMPTY);

    mockMvc.perform(get("/en/events/1985"))
        .andExpect(status().isOk())
        .andExpect(view().name("events-by-year"))
        .andExpect(model().attribute("year", 1985))
        .andExpect(model().attribute("events", PAGE_EMPTY.events()))
        .andExpect(model().attribute("pagination", PAGE_EMPTY.pagination()));
  }

  @Test
  void eventsYearPageNegativePageShouldBeClamped() throws Exception {
    when(messageService.getMessage(eq("page.events.year.metadata.title"), any(), any())).thenReturn(
        "Events by year - EnduranceTrio");
    when(messageService.getMessage(eq("page.events.year.metadata.description"), any(),
        any()
    )).thenReturn("Browse the endurance sports events by year");
    when(eventService.getEventsByYear(eq(1984), ArgumentMatchers.any())).thenReturn(
        PAGE_WITH_EVENTS);

    mockMvc.perform(get("/en/events/1984").param("page", "-1"))
        .andExpect(status().isOk())
        .andExpect(view().name("events-by-year"))
        .andExpect(model().attribute("year", 1984))
        .andExpect(model().attribute("events", PAGE_WITH_EVENTS.events()))
        .andExpect(model().attribute("pagination", PAGE_WITH_EVENTS.pagination()));
  }

  @Test
  void getEventInsightsPageWithEnglishLocale() throws Exception {
    when(messageService.getMessage(eq("page.event.insights.metadata.title"), any(),
        any()
    )).thenReturn("Event Insights - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.insights.metadata.description"), any(),
        any()
    )).thenReturn("Articles related to this event");
    when(eventService.getEventOverview(1L, 1984)).thenReturn(EVENT_OVERVIEW);
    when(insightService.getArticlesByEvent(eq(1L), any(Pageable.class), any())).thenReturn(
        EVENT_INSIGHT_PAGE);

    mockMvc.perform(get("/en/events/1984/1/insights"))
        .andExpect(status().isOk())
        .andExpect(view().name("event-insights"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attributeExists("metadata"))
        .andExpect(model().attribute("event", EVENT_OVERVIEW))
        .andExpect(model().attribute("articles", EVENT_INSIGHT_PAGE.articles()))
        .andExpect(model().attribute("pagination", INSIGHT_PAGINATION));
  }

  @Test
  void getEventInsightsPageWithPortugueseLocale() throws Exception {
    when(messageService.getMessage(eq("page.event.insights.metadata.title"), any(),
        any()
    )).thenReturn("Perspetivas do Evento - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.insights.metadata.description"), any(),
        any()
    )).thenReturn("Artigos relacionados com este evento");
    when(eventService.getEventOverview(1L, 1984)).thenReturn(EVENT_OVERVIEW);
    when(insightService.getArticlesByEvent(eq(1L), any(Pageable.class), any())).thenReturn(
        EVENT_INSIGHT_PAGE_PT);

    mockMvc.perform(get("/pt/events/1984/1/insights"))
        .andExpect(status().isOk())
        .andExpect(view().name("event-insights"))
        .andExpect(model().attribute("language", "pt"))
        .andExpect(model().attributeExists("metadata"))
        .andExpect(model().attribute("event", EVENT_OVERVIEW))
        .andExpect(model().attribute("articles", EVENT_INSIGHT_PAGE_PT.articles()))
        .andExpect(model().attribute("pagination", INSIGHT_PAGINATION));
  }

  @Test
  void getEventOverviewPageWithEnglishLocale() throws Exception {
    when(
        messageService.getMessage(eq("page.event.overview.metadata.title"), any(), any())).thenReturn(
        "Event Details - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.overview.metadata.description"), any(),
        any()
    )).thenReturn("View endurance sports event details");
    when(eventService.getEventOverview(1L, 1984)).thenReturn(EVENT_OVERVIEW);

    mockMvc.perform(get("/en/events/1984/1/overview"))
        .andExpect(status().isOk())
        .andExpect(view().name("event-overview"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attributeExists("metadata"));
  }

  @Test
  void getEventOverviewPageWithPortugueseLocale() throws Exception {
    when(
        messageService.getMessage(eq("page.event.overview.metadata.title"), any(), any())).thenReturn(
        "Detalhes do Evento - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.overview.metadata.description"), any(),
        any()
    )).thenReturn("Detalhes do evento de desportos de endurance");
    when(eventService.getEventOverview(1L, 1984)).thenReturn(EVENT_OVERVIEW);

    mockMvc.perform(get("/pt/events/1984/1/overview"))
        .andExpect(status().isOk())
        .andExpect(view().name("event-overview"))
        .andExpect(model().attribute("language", "pt"))
        .andExpect(model().attributeExists("metadata"));
  }

  @Test
  void getRaceResultsPageWithEnglishLocale() throws Exception {
    when(messageService.getMessage(eq("page.event.results.metadata.title"), any(),
        any()
    )).thenReturn("Results - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.results.metadata.description"), any(),
        any()
    )).thenReturn("View endurance sports event results");
    when(eventService.getEventOverview(1L, 1984)).thenReturn(EVENT_OVERVIEW_WITH_RACES);
    when(raceService.getRaceResults(any(RaceDTO.class))).thenReturn(
        new RaceResultsDTO(null, Map.of()));

    mockMvc.perform(get("/en/events/1984/1/results/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("race-results"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attributeExists("metadata"))
        .andExpect(model().attribute("raceId", 1L))
        .andExpect(model().attribute("activeColumns", org.hamcrest.Matchers.empty()));
  }

  @Test
  void getRaceResultsPageWithPortugueseLocale() throws Exception {
    when(messageService.getMessage(eq("page.event.results.metadata.title"), any(),
        any()
    )).thenReturn("Resultados - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.results.metadata.description"), any(),
        any()
    )).thenReturn("Resultados de eventos de desportos de endurance");
    when(eventService.getEventOverview(1L, 1984)).thenReturn(EVENT_OVERVIEW_WITH_RACES);
    when(raceService.getRaceResults(any(RaceDTO.class))).thenReturn(
        new RaceResultsDTO(null, Map.of()));

    mockMvc.perform(get("/pt/events/1984/1/results/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("race-results"))
        .andExpect(model().attribute("language", "pt"))
        .andExpect(model().attributeExists("metadata"))
        .andExpect(model().attribute("raceId", 1L))
        .andExpect(model().attribute("activeColumns", org.hamcrest.Matchers.empty()));
  }

  @Test
  void getRaceResultsFirstRaceShouldHaveNextButNoPrevious() throws Exception {
    when(messageService.getMessage(eq("page.event.results.metadata.title"), any(),
        any()
    )).thenReturn("Results - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.results.metadata.description"), any(),
        any()
    )).thenReturn("View endurance sports event results");
    when(eventService.getEventOverview(1L, 1984)).thenReturn(EVENT_OVERVIEW_WITH_RACES);
    when(raceService.getRaceResults(any(RaceDTO.class))).thenReturn(
        new RaceResultsDTO(null, Map.of()));

    mockMvc.perform(get("/en/events/1984/1/results/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("race-results"))
        .andExpect(model().attribute("raceId", 1L))
        .andExpect(model().attribute("raceIdPrev", org.hamcrest.Matchers.nullValue()))
        .andExpect(model().attribute("raceIdNext", 2L))
        .andExpect(model().attribute("activeColumns", org.hamcrest.Matchers.empty()));
  }

  @Test
  void getRaceResultsMiddleRaceShouldHavePreviousAndNext() throws Exception {
    when(messageService.getMessage(eq("page.event.results.metadata.title"), any(),
        any()
    )).thenReturn("Results - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.results.metadata.description"), any(),
        any()
    )).thenReturn("View endurance sports event results");
    when(eventService.getEventOverview(1L, 1984)).thenReturn(EVENT_OVERVIEW_WITH_RACES);
    when(raceService.getRaceResults(any(RaceDTO.class))).thenReturn(
        new RaceResultsDTO(null, Map.of()));

    mockMvc.perform(get("/en/events/1984/1/results/2"))
        .andExpect(status().isOk())
        .andExpect(view().name("race-results"))
        .andExpect(model().attribute("raceId", 2L))
        .andExpect(model().attribute("raceIdPrev", 1L))
        .andExpect(model().attribute("raceIdNext", 3L))
        .andExpect(model().attribute("activeColumns", org.hamcrest.Matchers.empty()));
  }

  @Test
  void getRaceResultsLastRaceShouldHavePreviousButNoNext() throws Exception {
    when(messageService.getMessage(eq("page.event.results.metadata.title"), any(),
        any()
    )).thenReturn("Results - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.results.metadata.description"), any(),
        any()
    )).thenReturn("View endurance sports event results");
    when(eventService.getEventOverview(1L, 1984)).thenReturn(EVENT_OVERVIEW_WITH_RACES);
    when(raceService.getRaceResults(any(RaceDTO.class))).thenReturn(
        new RaceResultsDTO(null, Map.of()));

    mockMvc.perform(get("/en/events/1984/1/results/3"))
        .andExpect(status().isOk())
        .andExpect(view().name("race-results"))
        .andExpect(model().attribute("raceId", 3L))
        .andExpect(model().attribute("raceIdPrev", 2L))
        .andExpect(model().attribute("raceIdNext", org.hamcrest.Matchers.nullValue()))
        .andExpect(model().attribute("activeColumns", org.hamcrest.Matchers.empty()));
  }

  @Test
  void getRaceResultsShouldDetectAllColumnsWithFullResults() throws Exception {
    when(messageService.getMessage(eq("page.event.results.metadata.title"), any(),
        any()
    )).thenReturn("Results - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.results.metadata.description"), any(),
        any()
    )).thenReturn("View endurance sports event results");
    when(eventService.getEventOverview(1L, 1984)).thenReturn(EVENT_OVERVIEW_WITH_RACES);

    IndividualResultDTO fullResult = getIndividualResultDTO();

    Map<AgeGroup, List<IndividualResultDTO>> individualResults = Map.of(AgeGroup.OPEN,
        List.of(fullResult)
    );
    when(raceService.getRaceResults(any(RaceDTO.class))).thenReturn(
        new RaceResultsDTO(RACE_1, individualResults));

    mockMvc.perform(get("/en/events/1984/1/results/1"))
        .andExpect(status().isOk())
        .andExpect(view().name("race-results"))
        .andExpect(model().attribute("activeColumns",
            Matchers.containsInAnyOrder("rank", "athlete", "ageGroup", "paraClass", "team", "bib",
                "swim", "firstBike", "firstRun", "t1", "bike", "t2", "run", "secondRun", "t3",
                "secondBike", "total", "gap", "points"
            )
        ));
  }

  private static @NonNull IndividualResultDTO getIndividualResultDTO() {
    AthleteDTO athlete = AthleteDTOFixtures.standard();
    TeamDTO team = TeamDTOFixtures.standard();

    return new IndividualResultDTO(1L, RACE_1, 1, Penalty.DNF, athlete, AgeGroup.CAT_A,
        ParaClass.AQ_S1, team, "101", Duration.ofMinutes(10), Duration.ofMinutes(20),
        Duration.ofMinutes(15), Duration.ofSeconds(30), Duration.ofMinutes(40),
        Duration.ofSeconds(45), Duration.ofMinutes(25), Duration.ofMinutes(5),
        Duration.ofSeconds(10), Duration.ofMinutes(50), Duration.ofHours(1), Duration.ofMinutes(2),
        100
    );
  }
}
