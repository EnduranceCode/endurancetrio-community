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

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.endurancetrio.app.common.handler.EnduranceTrioExceptionHandlerWeb;
import com.endurancetrio.app.common.model.PageWindow;
import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.competitor.fixtures.AthleteDTOFixtures;
import com.endurancetrio.app.competitor.fixtures.TeamDTOFixtures;
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.app.insight.fixtures.ArticleDTOFixtures;
import com.endurancetrio.business.common.dto.PaginationDTO;
import com.endurancetrio.business.competitor.dto.AthleteDTO;
import com.endurancetrio.business.competitor.dto.TeamDTO;
import com.endurancetrio.business.event.dto.EventDTO;
import com.endurancetrio.business.event.dto.EventFilterDTO;
import com.endurancetrio.business.event.dto.EventOverviewDTO;
import com.endurancetrio.business.event.dto.EventsPageDTO;
import com.endurancetrio.business.event.dto.IndividualRaceResultsDTO;
import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.dto.RaceResultsDTO;
import com.endurancetrio.business.event.dto.TeamRaceResultsDTO;
import com.endurancetrio.business.event.dto.TeamResultDTO;
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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ExtendWith(MockitoExtension.class)
class EventWebControllerTest {

  private static final List<Integer> ALL_YEARS = List.of(1989, 1988, 1987, 1986, 1985, 1984, 1983,
      1982
  );
  private static final Map<String, List<String>> COUNTIES_BY_DISTRICT = Map.of("Lisboa", List.of("Cascais", "Lisboa"),
      "Leiria", List.of("Peniche", "Caldas da Rainha")
  );
  private static final LocalDate EVENT_DATE = LocalDate.of(1984, Month.AUGUST, 15);
  private static final EventDTO EVENT_DTO = new EventDTO(1L, "Triatlo de Peniche", EVENT_DATE,
      EVENT_DATE, "Peniche", "Peniche", "Leiria", List.of("TRIATHLON")
  );
  private static final EventsPageDTO PAGE_WITH_EVENTS = new EventsPageDTO(List.of(EVENT_DTO),
      new PaginationDTO(0, 1, 1, false, false)
  );
  private static final EventsPageDTO PAGE_WITH_EVENTS_PAGINATED = new EventsPageDTO(List.of(EVENT_DTO),
      new PaginationDTO(2, 5, 50, true, true)
  );
  private static final EventsPageDTO PAGE_EMPTY = new EventsPageDTO(List.of(),
      new PaginationDTO(0, 0, 0, false, false)
  );
  private static final EventOverviewDTO EVENT_OVERVIEW = new EventOverviewDTO(
      1L, "Triatlo de Peniche", EVENT_DATE, EVENT_DATE, "Peniche", "Peniche", "Leiria",
      List.of(), List.of(), List.of()
  );
  private static final RaceDTO RACE_1 = new RaceDTO(1L, "Triatlo de Peniche", "Geral", EVENT_DATE,
    null, List.of("TRIATHLON"), List.of("SPRINT"), RaceType.INDIVIDUAL_PARENT, "INDIVIDUAL", null,
      null, "UNKNOWN"
  );
  private static final RaceDTO RACE_2 = new RaceDTO(2L, "Triatlo de Peniche", "Estafetas",
      EVENT_DATE, null, List.of("TRIATHLON"), List.of("SPRINT"), RaceType.TEAM_RELAY_PARENT,
      "RELAY", null, null, "UNKNOWN"
  );
  private static final RaceDTO RACE_3 = new RaceDTO(3L, "Triatlo de Peniche", "Para", EVENT_DATE,
    null, List.of("TRIATHLON"), List.of("SPRINT"), RaceType.INDIVIDUAL_PARENT, "INDIVIDUAL", null,
    null, "UNKNOWN"
  );

  private static final EventOverviewDTO EVENT_OVERVIEW_WITH_RACES = new EventOverviewDTO(
      1L, "Triatlo de Peniche", EVENT_DATE, EVENT_DATE, "Peniche", "Peniche", "Leiria",
      List.of(), List.of(RACE_1, RACE_2, RACE_3), List.of()
  );

  private static final ArticleDTO INSIGHT_ARTICLE_EN = ArticleDTOFixtures.standard();
  private static final ArticleDTO INSIGHT_ARTICLE_PT = ArticleDTOFixtures.portuguese();
  private static final PaginationDTO INSIGHT_PAGINATION = new PaginationDTO(0, 1, 1, false, false);
  private static final Long ARTICLES_COUNT = 3L;
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
    appProperties.setSiteUrl("http://localhost");
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
  void eventsPageWithEnglishLocale() throws Exception {
    stubEventMessages("Events - EnduranceTrio", "Browse endurance sports events");
    stubEventDirectory(PAGE_WITH_EVENTS);

    mockMvc.perform(get("/en/events"))
        .andExpect(status().isOk())
        .andExpect(view().name("events"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attributeExists("metadata"))
        .andExpect(model().attribute("events", PAGE_WITH_EVENTS.events()))
        .andExpect(model().attribute("pagination", PAGE_WITH_EVENTS.pagination()))
        .andExpect(model().attribute("filter", new EventFilterDTO(null, null, null, null)))
        .andExpect(model().attribute("years", ALL_YEARS))
        .andExpect(model().attribute("countiesByDistrict", COUNTIES_BY_DISTRICT))
        .andExpect(model().attribute("isFiltered", false))
        .andExpect(model().attribute("eventsUrl", "/en/events"))
        .andExpect(model().attribute("paginationPreviousUrl", nullValue()))
        .andExpect(model().attribute("paginationNextUrl", nullValue()));
  }

  @Test
  void eventsPageWithPortugueseLocale() throws Exception {
    stubEventMessages("Eventos - EnduranceTrio", "Explore eventos de desportos de endurance");
    stubEventDirectory(PAGE_WITH_EVENTS);

    mockMvc.perform(get("/pt/events"))
        .andExpect(status().isOk()).andExpect(view().name("events"))
        .andExpect(model().attribute("language", "pt"))
        .andExpect(model().attributeExists("metadata")).andExpect(model().attribute("eventsUrl", "/pt/events"));
  }

  @Test
  void eventsPageMetadataHasCorrectTitle() throws Exception {
    stubEventMessages("Events - EnduranceTrio", "Browse endurance sports events");
    stubEventDirectory(PAGE_WITH_EVENTS);

    mockMvc.perform(get("/en/events"))
        .andExpect(model().attribute("metadata", hasProperty("title", equalTo("Events - EnduranceTrio")
            )
        ));
  }

  @Test
  void eventsPageWithEmptyList() throws Exception {
    stubEventMessages("Events - EnduranceTrio", "Browse endurance sports events");
    stubEventDirectory(PAGE_EMPTY);

    mockMvc.perform(get("/en/events"))
        .andExpect(status().isOk())
        .andExpect(view().name("events"))
        .andExpect(model().attribute("events", PAGE_EMPTY.events()))
        .andExpect(model().attribute("pagination", PAGE_EMPTY.pagination()))
        .andExpect(model().attribute("pageWindow", new PageWindow(List.of(), false, false)));
  }

  @Test
  void eventsPageSecondPage() throws Exception {
    stubEventMessages("Events - EnduranceTrio", "Browse endurance sports events");
    stubEventDirectory(PAGE_WITH_EVENTS);

    mockMvc.perform(get("/en/events").param("page", "1"))
        .andExpect(status().isOk())
        .andExpect(view().name("events"))
        .andExpect(model().attribute("pagination", PAGE_WITH_EVENTS.pagination()));

    verify(eventService).getEvents(PageRequest.of(1, 10), new EventFilterDTO(null, null, null, null));
  }

  @ParameterizedTest
  @ValueSource(strings = {"-1", "unknown", "  "})
  void eventsPageNegativePageShouldBeClamped(String page) throws Exception {
    stubEventMessages("Events - EnduranceTrio", "Browse endurance sports events");
    stubEventDirectory(PAGE_WITH_EVENTS);

    mockMvc.perform(get("/en/events").param("page", page)).andExpect(status().isOk()).andExpect(view().name("events"));

    verify(eventService).getEvents(PageRequest.of(0, 10), new EventFilterDTO(null, null, null, null));
  }

  @Test
  void eventsPageNormalizesFilters() throws Exception {
    stubEventMessages("Events - EnduranceTrio", "Browse endurance sports events");
    stubEventDirectory(PAGE_WITH_EVENTS);
    EventFilterDTO expected = new EventFilterDTO("São João", 1984, "Lisboa", "Cascais");

    mockMvc.perform(get("/en/events").param("q", "  São João  ")
            .param("year", " 1984 ")
            .param("district", " Lisboa ")
            .param("county", " Cascais "))
        .andExpect(status().isOk())
        .andExpect(model().attribute("filter", expected))
        .andExpect(model().attribute("isFiltered", true))
        .andExpect(model().attribute("metadata", hasProperty("canonicalUrl", equalTo("http://localhost/en/events"))))
        .andExpect(model().attribute("metadata", hasProperty("hreflangUrlEn", equalTo("http://localhost/en/events"))))
        .andExpect(model().attribute("metadata", hasProperty("hreflangUrlPt", equalTo("http://localhost/pt/events"))));

    verify(eventService).getEvents(PageRequest.of(0, 10), expected);
  }

  @Test
  void eventsPageIgnoresInvalidFilters() throws Exception {
    stubEventMessages("Events - EnduranceTrio", "Browse endurance sports events");
    stubEventDirectory(PAGE_WITH_EVENTS);

    mockMvc.perform(get("/en/events").param("q", "   ")
            .param("year", "unknown")
            .param("district", "Porto")
            .param("county", "Porto"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("filter", new EventFilterDTO(null, null, null, null)))
        .andExpect(model().attribute("isFiltered", false));

    verify(eventService).getEvents(PageRequest.of(0, 10), new EventFilterDTO(null, null, null, null));
  }

  @Test
  void eventsPageCapsSearchTerm() throws Exception {
    stubEventMessages("Events - EnduranceTrio", "Browse endurance sports events");
    stubEventDirectory(PAGE_WITH_EVENTS);
    String capped = "a".repeat(100);

    mockMvc.perform(get("/en/events").param("q", "a".repeat(101)))
        .andExpect(status().isOk())
        .andExpect(model().attribute("filter", new EventFilterDTO(capped, null, null, null)));

    verify(eventService).getEvents(PageRequest.of(0, 10), new EventFilterDTO(capped, null, null, null));
  }

  @Test
  void eventsPageDropsCountyMismatchedWithDistrict() throws Exception {
    stubEventMessages("Events - EnduranceTrio", "Browse endurance sports events");
    stubEventDirectory(PAGE_WITH_EVENTS);
    EventFilterDTO expected = new EventFilterDTO(null, null, "Leiria", null);

    mockMvc.perform(get("/en/events").param("district", "Leiria").param("county", "Cascais"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("filter", expected))
        .andExpect(model().attribute("isFiltered", true));

    verify(eventService).getEvents(PageRequest.of(0, 10), expected);
  }

  @Test
  void eventsPageBuildsNavigationUrls() throws Exception {
    stubEventMessages("Events - EnduranceTrio", "Browse endurance sports events");
    stubEventDirectory(PAGE_WITH_EVENTS_PAGINATED);
    String query = "q=Peniche&year=1984&district=Leiria&county=Peniche";

    mockMvc.perform(get("/en/events").param("page", "2")
            .param("q", "Peniche")
            .param("year", "1984")
            .param("district", "Leiria")
            .param("county", "Peniche"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("eventsUrl", "/en/events"))
        .andExpect(model().attribute("paginationUrls", hasEntry(0, "/en/events?page=0&" + query)))
        .andExpect(model().attribute("paginationUrls", hasEntry(4, "/en/events?page=4&" + query)))
        .andExpect(model().attribute("paginationPreviousUrl", equalTo("/en/events?page=1&" + query)))
        .andExpect(model().attribute("paginationNextUrl", equalTo("/en/events?page=3&" + query)))
        .andExpect(model().attribute("pageWindow", new PageWindow(List.of(0, 1, 2, 3, 4), false, false)));
  }

  @Test
  void eventsPageSetsMetadataWhenPagedWithoutFilters() throws Exception {
    stubEventMessages("Events - EnduranceTrio", "Browse endurance sports events");
    stubEventDirectory(PAGE_WITH_EVENTS);

    mockMvc.perform(get("/en/events").param("page", "1"))
        .andExpect(status().isOk())
        .andExpect(model().attribute("isFiltered", false))
        .andExpect(model().attribute("metadata", hasProperty("canonicalUrl", equalTo("http://localhost/en/events"))))
        .andExpect(model().attribute("metadata", hasProperty("hreflangUrlPt", equalTo("http://localhost/pt/events"))));
  }

  @Test
  void eventsYearPageShouldRedirectToFilteredDirectory() throws Exception {
    mockMvc.perform(get("/en/events/1984"))
        .andExpect(status().is3xxRedirection())
        .andExpect(redirectedUrl("/en/events?year=1984"));
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
    when(insightService.getArticlesCountByEvent(1L)).thenReturn(ARTICLES_COUNT);
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
    when(insightService.getArticlesCountByEvent(1L)).thenReturn(ARTICLES_COUNT);
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
  void getEventInsightsPageWithNoArticlesShouldThrowNotFound() {
    when(eventService.getEventOverview(1L, 1984)).thenReturn(EVENT_OVERVIEW);
    when(insightService.getArticlesCountByEvent(1L)).thenReturn(0L);

    assertThrows(Exception.class, () -> mockMvc.perform(get("/en/events/1984/1/insights")));
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
    when(raceService.getRaceResults(any(RaceDTO.class))).thenReturn(emptyIndividualResults());

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
    when(raceService.getRaceResults(any(RaceDTO.class))).thenReturn(emptyIndividualResults());

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
    when(raceService.getRaceResults(any(RaceDTO.class))).thenReturn(emptyIndividualResults());

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
    when(raceService.getRaceResults(any(RaceDTO.class))).thenReturn(emptyIndividualResults());

    mockMvc.perform(get("/en/events/1984/1/results/2"))
        .andExpect(status().isOk())
        .andExpect(view().name("race-results"))
        .andExpect(model().attribute("raceId", 2L))
        .andExpect(model().attribute("raceIdPrev", 1L))
        .andExpect(model().attribute("raceIdNext", 3L))
        .andExpect(model().attribute("activeColumns", org.hamcrest.Matchers.empty()));
  }

  @Test
  void getEventOverviewShouldReturnBadRequestForNonNumericYear() throws Exception {
    MockMvc advisedMockMvc = MockMvcBuilders.standaloneSetup(eventWebController)
        .setControllerAdvice(new EnduranceTrioExceptionHandlerWeb(messageService, appProperties))
        .build();

    advisedMockMvc.perform(get("/en/events/abc/1/overview"))
        .andExpect(status().isBadRequest())
        .andExpect(view().name("error/400"));
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
    when(raceService.getRaceResults(any(RaceDTO.class))).thenReturn(emptyIndividualResults());

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
      new RaceResultsDTO(RACE_1, new IndividualRaceResultsDTO(individualResults)));

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

  @Test
  void getRaceResultsShouldUseOnlyOpenGroupForActiveColumns() throws Exception {
    when(messageService.getMessage(eq("page.event.results.metadata.title"), any(), any())).thenReturn(
      "Results - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.results.metadata.description"), any(), any())).thenReturn(
      "View endurance sports event results");
    when(eventService.getEventOverview(1L, 1984)).thenReturn(EVENT_OVERVIEW_WITH_RACES);

    IndividualResultDTO openResult = new IndividualResultDTO(2L, RACE_1, 1, null, AthleteDTOFixtures.standard(),
      AgeGroup.OPEN, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
      Duration.ofMinutes(10), null, null
    );
    when(raceService.getRaceResults(any(RaceDTO.class))).thenReturn(new RaceResultsDTO(RACE_1,
      new IndividualRaceResultsDTO(
        Map.of(AgeGroup.OPEN, List.of(openResult), AgeGroup.CAT_A, List.of(getIndividualResultDTO())))
    ));

    mockMvc.perform(get("/en/events/1984/1/results/1"))
      .andExpect(status().isOk())
      .andExpect(view().name("race-results"))
      .andExpect(
        model().attribute("activeColumns", Matchers.containsInAnyOrder("rank", "athlete", "ageGroup", "total")));
  }

  @Test
  void getRaceResultsShouldDetectTeamColumnsWithValues() throws Exception {
    when(messageService.getMessage(eq("page.event.results.metadata.title"), any(), any())).thenReturn(
      "Results - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.results.metadata.description"), any(), any())).thenReturn(
      "View endurance sports event results");
    when(eventService.getEventOverview(1L, 1984)).thenReturn(EVENT_OVERVIEW_WITH_RACES);

    var fullTeam = teamResultDTO(1L, List.of());
    var partialTeam = new TeamResultDTO(2L, RACE_1, null, "", null, null, null, null, null, null, null, null,
      List.of()
    );

    when(raceService.getRaceResults(any(RaceDTO.class))).thenReturn(
      new RaceResultsDTO(RACE_1, new TeamRaceResultsDTO(List.of(fullTeam, partialTeam))));

    mockMvc.perform(get("/en/events/1984/1/results/1"))
      .andExpect(status().isOk())
      .andExpect(view().name("race-results"))
      .andExpect(model().attribute("activeColumns",
        Matchers.containsInAnyOrder("rank", "teamName", "bib", "total", "points", "cumulativeRank", "athletesCount")
      ))
      .andExpect(model().attribute("activeContributorColumns", Matchers.empty()));
  }

  @Test
  void getRaceResultsShouldComputeContributorColumnsAcrossAllTeams() throws Exception {
    when(messageService.getMessage(eq("page.event.results.metadata.title"), any(), any())).thenReturn(
      "Results - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.results.metadata.description"), any(), any())).thenReturn(
      "View endurance sports event results");
    when(eventService.getEventOverview(1L, 1984)).thenReturn(EVENT_OVERVIEW_WITH_RACES);

    var teamWithAgeGroup = teamResultDTO(1L, List.of(contributor(10L, AgeGroup.OPEN, null)));
    var teamWithParaClass = teamResultDTO(2L, List.of(contributor(11L, null, ParaClass.AQ_S1)));

    when(raceService.getRaceResults(any(RaceDTO.class))).thenReturn(
      new RaceResultsDTO(RACE_1, new TeamRaceResultsDTO(List.of(teamWithAgeGroup, teamWithParaClass))));

    mockMvc.perform(get("/en/events/1984/1/results/1"))
      .andExpect(status().isOk())
      .andExpect(view().name("race-results"))
      .andExpect(model().attribute("activeColumns",
        Matchers.containsInAnyOrder("rank", "teamName", "bib", "total", "points", "cumulativeRank", "athletesCount")
      ))
      .andExpect(model().attribute("activeContributorColumns",
        Matchers.containsInAnyOrder("rank", "athlete", "ageGroup", "paraClass", "total")
      ));
  }

  private static TeamResultDTO teamResultDTO(Long id, List<IndividualResultDTO> contributors) {
    return new TeamResultDTO(id, RACE_1, 1, "Team Alpha", "101", 3, Duration.ofMinutes(200), null, 100, null, 5, null,
      contributors
    );
  }

  private static IndividualResultDTO contributor(Long id, AgeGroup ageGroup, ParaClass paraClass) {
    return new IndividualResultDTO(id, RACE_1, 1, null, AthleteDTOFixtures.standard(), ageGroup, paraClass, null, null,
      null, null, null, null, null, null, null, null, null, null, null, Duration.ofMinutes(10), null, null
    );
  }

  private static RaceResultsDTO emptyIndividualResults() {
    return new RaceResultsDTO(null, new IndividualRaceResultsDTO(Map.of()));
  }

  private static @NonNull IndividualResultDTO getIndividualResultDTO() {
    AthleteDTO athlete = AthleteDTOFixtures.standard();
    TeamDTO team = TeamDTOFixtures.standard();

    return new IndividualResultDTO(1L, RACE_1, 1, Penalty.DNF, athlete, AgeGroup.CAT_A, ParaClass.AQ_S1, team,
        "Team Alpha", "101", Duration.ofMinutes(10), Duration.ofMinutes(20), Duration.ofMinutes(15),
        Duration.ofSeconds(30), Duration.ofMinutes(40), Duration.ofSeconds(45), Duration.ofMinutes(25),
        Duration.ofMinutes(5), Duration.ofSeconds(10), Duration.ofMinutes(50), Duration.ofHours(1),
        Duration.ofMinutes(2), 100
    );
  }

  private void stubEventDirectory(EventsPageDTO eventsPage) {
    when(eventService.getEventYears()).thenReturn(ALL_YEARS);
    when(eventService.getEventCountiesByDistrict()).thenReturn(COUNTIES_BY_DISTRICT);
    when(eventService.getEvents(any(Pageable.class), any(EventFilterDTO.class))).thenReturn(eventsPage);
  }

  private void stubEventMessages(String title, String description) {
    when(messageService.getMessage(eq("page.events.metadata.title"), any(), any())).thenReturn(title);
    when(messageService.getMessage(eq("page.events.metadata.description"), any(), any())).thenReturn(description);
  }
}
