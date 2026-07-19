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
import com.endurancetrio.app.event.enumerator.IndividualResultColumns;
import com.endurancetrio.business.common.dto.ErrorDTO;

import com.endurancetrio.business.common.exception.EnduranceTrioError;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.event.dto.EventOverviewDTO;
import com.endurancetrio.business.event.dto.EventsPageDTO;
import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.dto.RaceResultsDTO;
import com.endurancetrio.business.event.dto.YearsWithEventsDTO;
import com.endurancetrio.business.event.service.EventService;
import com.endurancetrio.business.event.service.RaceService;
import com.endurancetrio.business.insight.dto.InsightPageDTO;
import com.endurancetrio.business.insight.service.InsightService;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The {@link EventWebController} handles Thymeleaf views for the events section.
 */
@EnduranceTrioWebController
public class EventWebController {

  private static final Logger LOG = LoggerFactory.getLogger(EventWebController.class);

  private static final String VIEW_EVENT_INSIGHTS = "event-insights";
  private static final String VIEW_EVENT_OVERVIEW = "event-overview";
  private static final String VIEW_EVENTS_BY_YEAR = "events-by-year";
  private static final String VIEW_RACE_RESULTS = "race-results";
  private static final String VIEW_YEARS_WITH_EVENTS = "years-with-events";

  private static final String ATTRIBUTE_ACTIVE_COLUMNS = "activeColumns";
  private static final String ATTRIBUTE_ARTICLES = "articles";
  private static final String ATTRIBUTE_ARTICLES_COUNT = "articlesCount";
  private static final String ATTRIBUTE_EVENT = "event";
  private static final String ATTRIBUTE_EVENTS = "events";
  private static final String ATTRIBUTE_RACE_ID = "raceId";
  private static final String ATTRIBUTE_RACE_ID_NEXT = "raceIdNext";
  private static final String ATTRIBUTE_RACE_ID_PREV = "raceIdPrev";
  private static final String ATTRIBUTE_RACE_RESULTS = "raceResults";
  private static final String ATTRIBUTE_YEARS_WITH_EVENTS = "yearsWithEvents";
  private static final String ATTRIBUTE_YEAR = "year";

  private static final int BATCH_SIZE = 3;
  private static final int PAGE_SIZE = 10;

  private final MessageService messageService;
  private final AppProperties appProperties;
  private final EventService eventService;
  private final InsightService insightService;
  private final RaceService raceService;

  @Autowired
  public EventWebController(
      MessageService messageService, AppProperties appProperties, EventService eventService,
      InsightService insightService, RaceService raceService
  ) {
    this.messageService = messageService;
    this.appProperties = appProperties;
    this.eventService = eventService;
    this.insightService = insightService;
    this.raceService = raceService;
  }

  /**
   * Returns the years-with-events listing page with batched year groups.
   *
   * @param language the language path variable ({@code en} or {@code pt})
   * @param page     the page number from the query string (default {@code 0})
   * @param request  the current HTTP request for building page metadata
   * @param model    the model to populate with view attributes
   * @return the years-with-events view name
   */
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

  /**
   * Returns the events-by-year listing page with paginated events for the given year.
   *
   * @param language the language path variable ({@code en} or {@code pt})
   * @param year     the year to filter events by
   * @param page     the page number from the query string (default {@code 0}, clamped to
   *                 non-negative)
   * @param request  the current HTTP request for building page metadata
   * @param model    the model to populate with view attributes
   * @return the events-by-year view name
   */
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

  /**
   * Returns the event-scoped insights page with related articles.
   *
   * @param language the language path variable ({@code en} or {@code pt})
   * @param year     the event year
   * @param id       the event ID
   * @param page     the page number from the query string (default {@code 0})
   * @param request  the current HTTP request for building page metadata
   * @param model    the model to populate with view attributes
   * @return the event insights view name
   */
  @GetMapping("/{language:en|pt}/events/{year}/{id}/insights")
  public String getEventInsights(
      @PathVariable String language, @PathVariable int year, @PathVariable Long id,
      @RequestParam(defaultValue = "0") int page, HttpServletRequest request, Model model
  ) {
    Locale locale = "pt".equalsIgnoreCase(language) ? LOCALE_PORTUGUESE : Locale.ENGLISH;

    EventOverviewDTO event = eventService.getEventOverview(id, year);

    PageMetadata metadata = PageMetadataUtils.create(VIEW_EVENT_INSIGHTS,
        messageService.getMessage("page.event.insights.metadata.title", new Object[]{event.title()}, locale),
        messageService.getMessage("page.event.insights.metadata.description", new Object[]{event.title()}, locale),
        request, appProperties
    );

    long articlesCount = insightService.getArticlesCountByEvent(id);

    if (articlesCount == 0) {
      String errorMsg = String.format("No articles found for event %d", id);
      LOG.warn(errorMsg);
      throw new EnduranceTrioException(new ErrorDTO(EnduranceTrioError.NOT_FOUND, errorMsg));
    }

    Pageable pageable = PageRequest.of(page, PAGE_SIZE);
    InsightPageDTO insightPage = insightService.getArticlesByEvent(id, pageable, locale);

    model.addAttribute(LANGUAGE, locale.getLanguage());
    model.addAttribute(METADATA, metadata);
    model.addAttribute(ATTRIBUTE_EVENT, event);
    model.addAttribute(ATTRIBUTE_ARTICLES, insightPage.articles());
    model.addAttribute(ATTRIBUTE_ARTICLES_COUNT, articlesCount);
    model.addAttribute(PAGINATION, insightPage.pagination());

    return VIEW_EVENT_INSIGHTS;
  }

  /**
   * Returns the event overview page for the given event.
   *
   * @param language the language path variable ({@code en} or {@code pt})
   * @param year     the event year
   * @param id       the event ID
   * @param request  the current HTTP request for building page metadata
   * @param model    the model to populate with view attributes
   * @return the event overview view name
   */
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

    long articlesCount = insightService.getArticlesCountByEvent(id);

    model.addAttribute(LANGUAGE, locale.getLanguage());
    model.addAttribute(METADATA, metadata);
    model.addAttribute(ATTRIBUTE_EVENT, event);
    model.addAttribute(ATTRIBUTE_ARTICLES_COUNT, articlesCount);

    return VIEW_EVENT_OVERVIEW;
  }

  /**
   * Returns the race results page for the given event and race.
   *
   * @param language the language path variable ({@code en} or {@code pt})
   * @param year     the event year
   * @param eventId  the event ID
   * @param raceId   the race ID
   * @param request  the current HTTP request for building page metadata
   * @param model    the model to populate with view attributes
   * @return the race results view name
   */
  @GetMapping("/{language:en|pt}/events/{year}/{eventId}/results/{raceId}")
  public String getRaceResults(
      @PathVariable String language, @PathVariable int year, @PathVariable Long eventId,
      @PathVariable Long raceId, HttpServletRequest request, Model model
  ) {
    Locale locale = "pt".equalsIgnoreCase(language) ? LOCALE_PORTUGUESE : Locale.ENGLISH;

    PageMetadata metadata = PageMetadataUtils.create(VIEW_RACE_RESULTS,
        messageService.getMessage("page.event.results.metadata.title", null, locale),
        messageService.getMessage("page.event.results.metadata.description", null, locale),
        request, appProperties
    );

    EventOverviewDTO event = eventService.getEventOverview(eventId, year);

    List<RaceDTO> races = event.races();
    int currentIndex = getCurrentRaceIndex(raceId, races);
    Long raceIdPrev = getPreviousRaceId(currentIndex, races);
    Long raceIdNext = getNextRaceId(currentIndex, races);

    RaceDTO currentRace = races.stream()
        .filter(race -> race.id().equals(raceId))
        .findFirst()
        .orElseThrow(() -> {
          String errorMsg = String.format("No race found with ID %d for event %d", raceId, eventId);
          LOG.warn(errorMsg);
          return new EnduranceTrioException(new ErrorDTO(EnduranceTrioError.NOT_FOUND, errorMsg));
        });

    RaceResultsDTO raceResults = raceService.getRaceResults(currentRace);
    Set<String> activeColumns = computeActiveColumns(raceResults);

    long articlesCount = insightService.getArticlesCountByEvent(eventId);

    model.addAttribute(LANGUAGE, locale.getLanguage());
    model.addAttribute(METADATA, metadata);
    model.addAttribute(ATTRIBUTE_EVENT, event);
    model.addAttribute(ATTRIBUTE_RACE_ID, raceId);
    model.addAttribute(ATTRIBUTE_RACE_ID_PREV, raceIdPrev);
    model.addAttribute(ATTRIBUTE_RACE_ID_NEXT, raceIdNext);
    model.addAttribute(ATTRIBUTE_RACE_RESULTS, raceResults);
    model.addAttribute(ATTRIBUTE_ACTIVE_COLUMNS, activeColumns);
    model.addAttribute(ATTRIBUTE_ARTICLES_COUNT, articlesCount);

    return VIEW_RACE_RESULTS;
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

  /**
   * Computes the set of column names that have at least one non-null value across all OPEN results.
   * <p>
   * Only the OPEN group is scanned so that column visibility is consistent across all age-group
   * tables.
   *
   * @param raceResults the race results
   * @return a set of column names with non-null data in the OPEN group
   */
  private static @NonNull Set<String> computeActiveColumns(RaceResultsDTO raceResults) {
    Set<String> active = new HashSet<>();

    if (raceResults.individualResults() != null) {
      List<IndividualResultDTO> openResults = raceResults.individualResults().get(AgeGroup.OPEN);
      if (openResults != null) {
        for (IndividualResultDTO result : openResults) {
          for (IndividualResultColumns column : IndividualResultColumns.values()) {
            if (column.isActive(result)) {
              active.add(column.getCode());
            }
          }
        }
      }
    }

    return active;
  }

  private static int getBatchGroupNextPage(int batchIndex, int totalBatches) {
    return switch (totalBatches - batchIndex) {
      case 0, 1 -> -1;
      case 2 -> batchIndex + 1;
      default -> batchIndex + 2;
    };
  }

  private static int getBatchGroupPreviousPage(int batchIndex) {
    return switch (batchIndex) {
      case 0 -> -1;
      case 1 -> 0;
      default -> batchIndex - 2;
    };
  }

  /**
   * Finds the index of the race with the given ID in the race list.
   *
   * @param raceId the race ID to search for
   * @param races  the list of races to search
   * @return the index of the matching race, or {@code -1} if not found
   */
  private static int getCurrentRaceIndex(Long raceId, List<RaceDTO> races) {
    int currentIndex = -1;
    for (int i = 0; i < races.size(); i++) {
      if (races.get(i).id().equals(raceId)) {
        currentIndex = i;
        break;
      }
    }
    return currentIndex;
  }

  /**
   * Returns the ID of the next race after the given position in the list.
   *
   * @param currentIndex the index of the current race
   * @param races        the list of races
   * @return the ID of the next race, or {@code null} if the current race is the last or was not
   *         found
   */
  private static @Nullable Long getNextRaceId(int currentIndex, List<RaceDTO> races) {
    return currentIndex >= 0 && currentIndex < races.size() - 1
        ? races.get(currentIndex + 1).id() : null;
  }

  /**
   * Returns the ID of the previous race before the given position in the list.
   *
   * @param currentIndex the index of the current race
   * @param races        the list of races
   * @return the ID of the previous race, or {@code null} if the current race is the first or was
   *         not found
   */
  private static @Nullable Long getPreviousRaceId(int currentIndex, List<RaceDTO> races) {
    return currentIndex > 0 ? races.get(currentIndex - 1).id() : null;
  }
}
