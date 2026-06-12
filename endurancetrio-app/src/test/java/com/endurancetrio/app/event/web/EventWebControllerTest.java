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
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.business.common.dto.PaginationDTO;
import com.endurancetrio.business.event.dto.EventDTO;
import com.endurancetrio.business.event.dto.YearsWithEventsDTO;
import com.endurancetrio.business.event.dto.EventsPageDTO;
import com.endurancetrio.business.event.service.EventService;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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

  @Mock
  MessageService messageService;

  @Mock
  EventService eventService;

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

    eventWebController = new EventWebController(messageService, appProperties, eventService);

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
  void getEventOverviewPageWithEnglishLocale() throws Exception {
    when(
        messageService.getMessage(eq("page.event.overview.metadata.title"), any(), any())).thenReturn(
        "Event Details - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.overview.metadata.description"), any(),
        any()
    )).thenReturn("View endurance sports event details");

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

    mockMvc.perform(get("/pt/events/1984/1/overview"))
        .andExpect(status().isOk())
        .andExpect(view().name("event-overview"))
        .andExpect(model().attribute("language", "pt"))
        .andExpect(model().attributeExists("metadata"));
  }

  @Test
  void getEventResultsPageWithEnglishLocale() throws Exception {
    when(messageService.getMessage(eq("page.event.results.metadata.title"), any(),
        any()
    )).thenReturn("Results - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.results.metadata.description"), any(),
        any()
    )).thenReturn("View endurance sports event results");

    mockMvc.perform(get("/en/events/1984/1/results"))
        .andExpect(status().isOk())
        .andExpect(view().name("event-results"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attributeExists("metadata"));
  }

  @Test
  void getEventResultsPageWithPortugueseLocale() throws Exception {
    when(messageService.getMessage(eq("page.event.results.metadata.title"), any(),
        any()
    )).thenReturn("Resultados - EnduranceTrio");
    when(messageService.getMessage(eq("page.event.results.metadata.description"), any(),
        any()
    )).thenReturn("Resultados de eventos de desportos de endurance");

    mockMvc.perform(get("/pt/events/1984/1/results"))
        .andExpect(status().isOk())
        .andExpect(view().name("event-results"))
        .andExpect(model().attribute("language", "pt"))
        .andExpect(model().attributeExists("metadata"));
  }
}
