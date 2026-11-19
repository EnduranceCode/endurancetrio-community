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
import com.endurancetrio.app.common.model.PageWindow;
import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.common.utils.PageMetadataUtils;
import com.endurancetrio.app.common.utils.PaginationUtils;
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.app.event.enumerator.IndividualResultColumns;
import com.endurancetrio.business.common.dto.ErrorDTO;
import com.endurancetrio.business.common.exception.EnduranceTrioError;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.event.dto.EventFilterDTO;
import com.endurancetrio.business.event.dto.EventOverviewDTO;
import com.endurancetrio.business.event.dto.EventsPageDTO;
import com.endurancetrio.business.event.dto.IndividualResultDTO;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.dto.RaceResultsDTO;
import com.endurancetrio.business.event.service.EventService;
import com.endurancetrio.business.event.service.RaceService;
import com.endurancetrio.business.insight.dto.InsightPageDTO;
import com.endurancetrio.business.insight.service.InsightService;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The {@link EventWebController} handles Thymeleaf views for the events section.
 */
@EnduranceTrioWebController
public class EventWebController {

  private static final Logger LOG = LoggerFactory.getLogger(EventWebController.class);

  private static final String VIEW_EVENT_INSIGHTS = "event-insights";
  private static final String VIEW_EVENT_OVERVIEW = "event-overview";
  private static final String VIEW_EVENTS = "events";
  private static final String VIEW_RACE_RESULTS = "race-results";

  private static final String ATTRIBUTE_ACTIVE_COLUMNS = "activeColumns";
  private static final String ATTRIBUTE_ARTICLES = "articles";
  private static final String ATTRIBUTE_ARTICLES_COUNT = "articlesCount";
  private static final String ATTRIBUTE_COUNTIES_BY_DISTRICT = "countiesByDistrict";
  private static final String ATTRIBUTE_EVENT = "event";
  private static final String ATTRIBUTE_EVENTS = "events";
  private static final String ATTRIBUTE_EVENTS_URL = "eventsUrl";
  private static final String ATTRIBUTE_FILTER = "filter";
  private static final String ATTRIBUTE_FILTERED = "isFiltered";
  private static final String ATTRIBUTE_PAGE_WINDOW = "pageWindow";
  private static final String ATTRIBUTE_PAGINATION_NEXT_URL = "paginationNextUrl";
  private static final String ATTRIBUTE_PAGINATION_PREVIOUS_URL = "paginationPreviousUrl";
  private static final String ATTRIBUTE_PAGINATION_URLS = "paginationUrls";
  private static final String ATTRIBUTE_RACE_ID = "raceId";
  private static final String ATTRIBUTE_RACE_ID_NEXT = "raceIdNext";
  private static final String ATTRIBUTE_RACE_ID_PREV = "raceIdPrev";
  private static final String ATTRIBUTE_RACE_RESULTS = "raceResults";
  private static final String ATTRIBUTE_YEARS = "years";

  private static final int MAX_SEARCH_TERM_LENGTH = 100;
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
   * Returns the searchable events directory with paginated events filtered by year, district,
   * county and a free-text title search. All filters are optional and invalid values are ignored.
   * Also builds the pagination URLs used by the view and sets canonical metadata for filtered or
   * paged listings.
   *
   * @param language the language path variable ({@code en} or {@code pt})
   * @param page     the page number from the query string (default {@code 0}, clamped to
   *                 non-negative)
   * @param q        the optional free-text search term
   * @param year     the optional year filter
   * @param district the optional district filter
   * @param county   the optional county filter
   * @param request  the current HTTP request for building page metadata
   * @param model    the model to populate with view attributes
   * @return the events view name
   */
  @GetMapping("/{language:en|pt}/events")
  public String getEvents(
      @PathVariable String language, @RequestParam(defaultValue = "0") int page,
      @RequestParam(required = false) String q, @RequestParam(required = false) String year,
      @RequestParam(required = false) String district,
      @RequestParam(required = false) String county, HttpServletRequest request, Model model
  ) {
    Locale locale = "pt".equalsIgnoreCase(language) ? LOCALE_PORTUGUESE : Locale.ENGLISH;

    PageMetadata metadata = PageMetadataUtils.create(VIEW_EVENTS,
        messageService.getMessage("page.events.metadata.title", null, locale),
        messageService.getMessage("page.events.metadata.description", null, locale), request,
        appProperties
    );

    int clampedPage = Math.max(0, page);
    Pageable pageable = PageRequest.of(clampedPage, PAGE_SIZE);
    List<Integer> years = eventService.getEventYears();
    Map<String, List<String>> countiesByDistrict = eventService.getEventCountiesByDistrict();
    EventFilterDTO filter = normalizeFilter(q, year, district, county, years, countiesByDistrict);
    EventsPageDTO eventsPage = eventService.getEvents(pageable, filter);
    boolean isFiltered = filter.q() != null || filter.year() != null || filter.district() != null
        || filter.county() != null;

    if (isFiltered || clampedPage > 0) {
      String baseUrl = PageMetadataUtils.getBaseUrl(request);
      metadata.setCanonicalUrl(buildAbsoluteEventsUrl(baseUrl, locale.getLanguage()));
      metadata.setHreflangUrlEn(buildAbsoluteEventsUrl(baseUrl, "en"));
      metadata.setHreflangUrlPt(buildAbsoluteEventsUrl(baseUrl, "pt"));
    }

    String eventsPath = UriComponentsBuilder.fromPath("/")
        .pathSegment(locale.getLanguage(), VIEW_EVENTS)
        .build()
        .toUriString();

    int totalPages = eventsPage.pagination().totalPages();
    PageWindow pageWindow = PaginationUtils.pageWindow(clampedPage, totalPages);
    Map<Integer, String> paginationUrls = new LinkedHashMap<>();
    for (Integer pageIndex : pageWindow.pages()) {
      paginationUrls.put(pageIndex, buildEventsUrl(eventsPath, pageIndex, filter));
    }

    model.addAttribute(LANGUAGE, locale.getLanguage());
    model.addAttribute(METADATA, metadata);
    model.addAttribute(PAGINATION, eventsPage.pagination());
    model.addAttribute(ATTRIBUTE_EVENTS, eventsPage.events());
    model.addAttribute(ATTRIBUTE_PAGE_WINDOW, pageWindow);
    model.addAttribute(ATTRIBUTE_FILTER, filter);
    model.addAttribute(ATTRIBUTE_YEARS, years);
    model.addAttribute(ATTRIBUTE_COUNTIES_BY_DISTRICT, countiesByDistrict);
    model.addAttribute(ATTRIBUTE_FILTERED, isFiltered);
    model.addAttribute(ATTRIBUTE_PAGINATION_URLS, paginationUrls);
    model.addAttribute(ATTRIBUTE_PAGINATION_PREVIOUS_URL,
        eventsPage.pagination().hasPrevious() ? buildEventsUrl(eventsPath, clampedPage - 1, filter) : null
    );
    model.addAttribute(ATTRIBUTE_PAGINATION_NEXT_URL,
        eventsPage.pagination().hasNext() ? buildEventsUrl(eventsPath, clampedPage + 1, filter) : null
    );
    model.addAttribute(ATTRIBUTE_EVENTS_URL, eventsPath);

    return VIEW_EVENTS;
  }

  /**
   * Redirects an old year-based events URL to the searchable events directory with the year filter
   * applied.
   *
   * @param language the language path variable ({@code en} or {@code pt})
   * @param year     the year to filter events by
   * @return the redirect view name
   */
  @GetMapping("/{language:en|pt}/events/{year}")
  public String redirectEventsByYear(@PathVariable String language, @PathVariable int year) {
    return "redirect:" + UriComponentsBuilder.fromPath("/")
        .pathSegment(language, VIEW_EVENTS)
        .queryParam("year", year)
        .build()
        .encode()
        .toUriString();
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

    Object[] messageArgs = {event.title(), String.valueOf(year)};

    PageMetadata metadata = PageMetadataUtils.create(VIEW_EVENT_INSIGHTS,
        messageService.getMessage("page.event.insights.metadata.title", messageArgs, locale),
        messageService.getMessage("page.event.insights.metadata.description", messageArgs, locale),
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

    EventOverviewDTO event = eventService.getEventOverview(id, year);

    Object[] messageArgs = {event.title(), String.valueOf(year), event.city()};

    PageMetadata metadata = PageMetadataUtils.create(VIEW_EVENT_OVERVIEW,
        messageService.getMessage("page.event.overview.metadata.title", messageArgs, locale),
        messageService.getMessage("page.event.overview.metadata.description", messageArgs, locale),
        request, appProperties
    );

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

    Object[] messageArgs = {currentRace.title(), event.title(), String.valueOf(year)};

    PageMetadata metadata = PageMetadataUtils.create(VIEW_RACE_RESULTS,
        messageService.getMessage("page.event.results.metadata.title", messageArgs, locale),
        messageService.getMessage("page.event.results.metadata.description", messageArgs, locale),
        request, appProperties
    );

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
   * Builds an absolute URL for the events directory in the given language.
   *
   * @param baseUrl  the scheme, host and port of the current request
   * @param language the language path segment
   * @return the absolute events-directory URL
   */
  private static String buildAbsoluteEventsUrl(String baseUrl, String language) {
    return UriComponentsBuilder.fromUriString(baseUrl).pathSegment(language, VIEW_EVENTS).build()
        .toUriString();
  }

  /**
   * Builds a relative events-directory URL that preserves the active filters.
   *
   * @param path   the localized events-directory path
   * @param page   the page index, or {@code null} to omit the page parameter
   * @param filter the active filters
   * @return the encoded events-directory URL
   */
  private static String buildEventsUrl(String path, Integer page, EventFilterDTO filter) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromPath(path);
    if (page != null) {
      builder.queryParam("page", page);
    }
    if (filter.q() != null) {
      builder.queryParam("q", filter.q());
    }
    if (filter.year() != null) {
      builder.queryParam("year", filter.year());
    }
    if (filter.district() != null) {
      builder.queryParam("district", filter.district());
    }
    if (filter.county() != null) {
      builder.queryParam("county", filter.county());
    }

    return builder.build().encode().toUriString();
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

  /**
   * Normalizes a county against the available district/county pairs. A county that does not belong
   * to the selected district is ignored.
   *
   * @param county            the raw county request parameter
   * @param district          the normalized district, or {@code null} when no district is selected
   * @param countiesByDistrict the available counties grouped by district
   * @return the normalized county, or {@code null} when it is blank or not available
   */
  private static @Nullable String normalizeCounty(
      String county, @Nullable String district, Map<String, List<String>> countiesByDistrict
  ) {
    if (county == null || county.isBlank()) {
      return null;
    }

    String normalized = county.trim();
    List<String> allowedCounties = district == null
        ? countiesByDistrict.values().stream().flatMap(List::stream).distinct().toList()
        : countiesByDistrict.get(district);

    return allowedCounties != null && allowedCounties.contains(normalized) ? normalized : null;
  }

  /**
   * Normalizes a district against the districts present in the archive.
   *
   * @param district           the raw district request parameter
   * @param countiesByDistrict the available counties grouped by district
   * @return the normalized district, or {@code null} when it is blank or not available
   */
  private static @Nullable String normalizeDistrict(
      String district, Map<String, List<String>> countiesByDistrict
  ) {
    if (district == null || district.isBlank()) {
      return null;
    }

    String normalized = district.trim();
    return countiesByDistrict.containsKey(normalized) ? normalized : null;
  }

  /**
   * Normalizes the raw request parameters into an {@link EventFilterDTO}. Free-text input is
   * trimmed and capped in length, while year, district and county values are validated against the
   * filter values present in the archive.
   *
   * @param q                  the raw search-term request parameter
   * @param year               the raw year request parameter
   * @param district           the raw district request parameter
   * @param county             the raw county request parameter
   * @param years              the years present in the archive
   * @param countiesByDistrict the counties present in the archive, grouped by district
   * @return the normalized filter
   */
  private static @NonNull EventFilterDTO normalizeFilter(
      String q, String year, String district, String county, List<Integer> years,
      Map<String, List<String>> countiesByDistrict
  ) {
    String normalizedDistrict = normalizeDistrict(district, countiesByDistrict);

    return new EventFilterDTO(
        normalizeSearchTerm(q), normalizeYear(year, years), normalizedDistrict,
        normalizeCounty(county, normalizedDistrict, countiesByDistrict)
    );
  }

  /**
   * Trims a free-text search term and caps its length.
   *
   * @param searchTerm the raw search-term request parameter
   * @return the normalized search term, or {@code null} when it is blank
   */
  private static @Nullable String normalizeSearchTerm(String searchTerm) {
    if (searchTerm == null) {
      return null;
    }

    String normalized = searchTerm.trim();
    if (normalized.isEmpty()) {
      return null;
    }

    return normalized.length() > MAX_SEARCH_TERM_LENGTH
        ? normalized.substring(0, MAX_SEARCH_TERM_LENGTH) : normalized;
  }

  /**
   * Normalizes a year against the years present in the archive.
   *
   * @param year  the raw year request parameter
   * @param years the years present in the archive
   * @return the normalized year, or {@code null} when it is blank, not numeric or not available
   */
  private static @Nullable Integer normalizeYear(String year, List<Integer> years) {
    if (year == null || year.isBlank()) {
      return null;
    }

    try {
      Integer normalized = Integer.valueOf(year.trim());
      return years.contains(normalized) ? normalized : null;
    } catch (NumberFormatException exception) {
      return null;
    }
  }
}
