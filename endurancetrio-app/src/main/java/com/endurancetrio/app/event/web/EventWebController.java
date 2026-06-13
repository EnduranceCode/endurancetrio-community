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

import static com.endurancetrio.app.common.constants.AppConstants.LANGUAGE;
import static com.endurancetrio.app.common.constants.AppConstants.LOCALE_PORTUGUESE;
import static com.endurancetrio.app.common.constants.AppConstants.METADATA;
import static com.endurancetrio.app.common.constants.AppConstants.PAGINATION;

import com.endurancetrio.app.common.annotation.EnduranceTrioWebController;
import com.endurancetrio.app.common.model.PageMetadata;
import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.common.utils.PageMetadataUtils;
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.business.event.dto.EventOverviewDTO;
import com.endurancetrio.business.event.dto.EventsPageDTO;
import com.endurancetrio.business.event.dto.YearsWithEventsDTO;
import com.endurancetrio.business.event.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@EnduranceTrioWebController
public class EventWebController {

  private static final String VIEW_EVENT_OVERVIEW = "event-overview";
  private static final String VIEW_EVENT_RESULTS = "event-results";
  private static final String VIEW_EVENTS_BY_YEAR = "events-by-year";
  private static final String VIEW_YEARS_WITH_EVENTS = "years-with-events";

  private static final String ATTRIBUTE_EVENT = "event";
  private static final String ATTRIBUTE_EVENTS = "events";
  private static final String ATTRIBUTE_YEARS_WITH_EVENTS = "yearsWithEvents";
  private static final String ATTRIBUTE_YEAR = "year";

  private static final int BATCH_SIZE = 3;
  private static final int PAGE_SIZE = 10;

  private final MessageService messageService;
  private final AppProperties appProperties;
  private final EventService eventService;

  @Autowired
  public EventWebController(
      MessageService messageService, AppProperties appProperties,
      EventService eventService
  ) {
    this.messageService = messageService;
    this.appProperties = appProperties;
    this.eventService = eventService;
  }

  @GetMapping("/{language:en|pt}/events")
  public String getYearsWithEvents(
      @PathVariable String language, @RequestParam(defaultValue = "0") int page,
      HttpServletRequest request, Model model
  ) {
    Locale locale = "pt".equalsIgnoreCase(language) ? LOCALE_PORTUGUESE : Locale.ENGLISH;

    PageMetadata metadata = PageMetadataUtils.create(VIEW_YEARS_WITH_EVENTS,
        messageService.getMessage("page.events.metadata.title", null, locale),
        messageService.getMessage("page.events.metadata.description", null, locale), request,
        appProperties
    );

    YearsWithEventsDTO eventYears = buildYearsWithEvents(page);

    model.addAttribute(LANGUAGE, locale.getLanguage());
    model.addAttribute(METADATA, metadata);
    model.addAttribute(ATTRIBUTE_YEARS_WITH_EVENTS, eventYears);

    return VIEW_YEARS_WITH_EVENTS;
  }

  @GetMapping("/{language:en|pt}/events/{year}")
  public String getEventsByYear(
      @PathVariable String language, @PathVariable int year,
      @RequestParam(defaultValue = "0") int page, HttpServletRequest request, Model model
  ) {
    Locale locale = "pt".equalsIgnoreCase(language) ? LOCALE_PORTUGUESE : Locale.ENGLISH;

    PageMetadata metadata = PageMetadataUtils.create(VIEW_EVENTS_BY_YEAR,
        messageService.getMessage("page.events.year.metadata.title", null, locale),
        messageService.getMessage("page.events.year.metadata.description", null, locale), request,
        appProperties
    );

    int clampedPage = Math.max(0, page);
    Pageable pageable = PageRequest.of(clampedPage, PAGE_SIZE);
    EventsPageDTO eventPage = eventService.getEventsByYear(year, pageable);

    model.addAttribute(LANGUAGE, locale.getLanguage());
    model.addAttribute(METADATA, metadata);
    model.addAttribute(PAGINATION, eventPage.pagination());
    model.addAttribute(ATTRIBUTE_YEAR, year);
    model.addAttribute(ATTRIBUTE_EVENTS, eventPage.events());

    return VIEW_EVENTS_BY_YEAR;
  }

  @GetMapping("/{language:en|pt}/events/{year}/{id}/overview")
  public String getEventOverview(
      @PathVariable String language, @PathVariable int year, @PathVariable Long id,
      HttpServletRequest request, Model model
  ) {
    Locale locale = "pt".equalsIgnoreCase(language) ? LOCALE_PORTUGUESE : Locale.ENGLISH;

    PageMetadata metadata = PageMetadataUtils.create(VIEW_EVENT_OVERVIEW,
        messageService.getMessage("page.event.overview.metadata.title", null, locale),
        messageService.getMessage("page.event.overview.metadata.description", null, locale), request,
        appProperties
    );

    EventOverviewDTO event = eventService.getEventOverview(id, year);

    model.addAttribute(LANGUAGE, locale.getLanguage());
    model.addAttribute(METADATA, metadata);
    model.addAttribute(ATTRIBUTE_EVENT, event);

    return VIEW_EVENT_OVERVIEW;
  }

  @GetMapping("/{language:en|pt}/events/{year}/{id}/results")
  public String getEventResults(
      @PathVariable String language, @PathVariable int year, @PathVariable Long id,
      HttpServletRequest request, Model model
  ) {
    Locale locale = "pt".equalsIgnoreCase(language) ? LOCALE_PORTUGUESE : Locale.ENGLISH;

    PageMetadata metadata = PageMetadataUtils.create(VIEW_EVENT_RESULTS,
        messageService.getMessage("page.event.results.metadata.title", null, locale),
        messageService.getMessage("page.event.results.metadata.description", null, locale),
        request, appProperties
    );

    EventOverviewDTO event = eventService.getEventOverview(id, year);

    model.addAttribute(LANGUAGE, locale.getLanguage());
    model.addAttribute(METADATA, metadata);
    model.addAttribute(ATTRIBUTE_EVENT, event);

    return VIEW_EVENT_RESULTS;
  }

  /**
   * Builds a {@link YearsWithEventsDTO} for the given page.
   * <p>
   * The raw {@code page} parameter (from the URL query string) is clamped to the valid range
   * {@code [0, totalBatches - 1]} to produce the batch index. This index is used to slice the full
   * year list into the current batch ({@link YearsWithEventsDTO#years()}), the next batch
   * ({@link YearsWithEventsDTO#nextYears()}), and the previous batch
   * ({@link YearsWithEventsDTO#previousYears()}). Two-batch (desktop) navigation pages are also
   * computed via {@link YearsWithEventsDTO#batchGroupPreviousPage()} and
   * {@link YearsWithEventsDTO#batchGroupNextPage()}.
   *
   * @param page the requested page from the URL query {@code page} parameter (may be out of
   *             bounds)
   * @return a fully populated {@link YearsWithEventsDTO}
   */
  private @NonNull YearsWithEventsDTO buildYearsWithEvents(int page) {
    List<Integer> allYears = eventService.getEventYears();
    int totalYears = allYears.size();

    int totalBatches = totalYears == 0 ? 0 : (totalYears + BATCH_SIZE - 1) / BATCH_SIZE;
    int batchIndex = Math.clamp(page, 0, Math.max(0, totalBatches - 1));

    int from = batchIndex * BATCH_SIZE;
    int to = Math.min(from + BATCH_SIZE, totalYears);
    List<Integer> years = allYears.subList(from, to);

    int nextFrom = from + BATCH_SIZE;
    int nextTo = Math.min(nextFrom + BATCH_SIZE, totalYears);
    List<Integer> nextYears =
        nextFrom < totalYears ? allYears.subList(nextFrom, nextTo) : List.of();

    int previousFrom = Math.max(0, from - BATCH_SIZE);
    List<Integer> previousYears = batchIndex > 0 ? allYears.subList(previousFrom, from) : List.of();

    int batchGroupPreviousPage = getBatchGroupPreviousPage(batchIndex);
    int batchGroupNextPage = getBatchGroupNextPage(batchIndex, totalBatches);

    return new YearsWithEventsDTO(years, nextYears, previousYears, batchIndex, totalBatches, totalYears,
        BATCH_SIZE, batchGroupPreviousPage, batchGroupNextPage
    );
  }

  private static int getBatchGroupPreviousPage(int batchIndex) {
    return switch (batchIndex) {
      case 0 -> -1;
      case 1 -> 0;
      default -> batchIndex - 2;
    };
  }

  private static int getBatchGroupNextPage(int batchIndex, int totalBatches) {
    return switch (totalBatches - batchIndex) {
      case 0, 1 -> -1;
      case 2 -> batchIndex + 1;
      default -> batchIndex + 2;
    };
  }
}
