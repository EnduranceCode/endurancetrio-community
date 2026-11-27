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

package com.endurancetrio.app.competitor.web;

import static com.endurancetrio.app.common.constants.AppConstants.LANGUAGE;
import static com.endurancetrio.app.common.constants.AppConstants.LOCALE_PORTUGUESE;
import static com.endurancetrio.app.common.constants.AppConstants.METADATA;
import static com.endurancetrio.app.common.constants.AppConstants.PAGINATION;

import com.endurancetrio.app.common.annotation.EnduranceTrioWebController;
import com.endurancetrio.app.common.model.PageMetadata;
import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.common.utils.PageMetadataUtils;
import com.endurancetrio.app.common.utils.PaginationUtils;
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.business.competitor.dto.AthleteDTO;
import com.endurancetrio.business.competitor.dto.AthleteFilterDTO;
import com.endurancetrio.business.competitor.dto.AthleteResultsPageDTO;
import com.endurancetrio.business.competitor.dto.AthletesPageDTO;
import com.endurancetrio.business.competitor.enumerator.AthleteLetterRange;
import com.endurancetrio.business.competitor.service.AthleteService;
import com.endurancetrio.business.insight.dto.ArticleSummaryDTO;
import com.endurancetrio.business.insight.service.InsightService;
import com.endurancetrio.data.competitor.model.enumerator.AthleteGender;
import jakarta.servlet.http.HttpServletRequest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.OptionalInt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * The {@link AthleteWebController} handles Thymeleaf views for the athletes section.
 */
@EnduranceTrioWebController
public class AthleteWebController {

  private static final String ATTRIBUTE_ARTICLES = "articles";
  private static final String ATTRIBUTE_ATHLETE = "athlete";
  private static final String ATTRIBUTE_ATHLETES = "athletes";
  private static final String ATTRIBUTE_ATHLETES_URL = "athletesUrl";
  private static final String ATTRIBUTE_FILTER = "filter";
  private static final String ATTRIBUTE_FILTERED = "isFiltered";
  private static final String ATTRIBUTE_GENDER_URLS = "genderUrls";
  private static final String ATTRIBUTE_LETTER_RANGE_URLS = "letterRangeUrls";
  private static final String ATTRIBUTE_LETTER_RANGES = "letterRanges";
  private static final String ATTRIBUTE_PAGE_WINDOW = "pageWindow";
  private static final String ATTRIBUTE_PAGINATION_NEXT_URL = "paginationNextUrl";
  private static final String ATTRIBUTE_PAGINATION_PREVIOUS_URL = "paginationPreviousUrl";
  private static final String ATTRIBUTE_PAGINATION_URLS = "paginationUrls";
  private static final String ATTRIBUTE_RESULTS = "results";
  private static final String VIEW_ATHLETE_PROFILE = "athlete-profile";
  private static final String VIEW_ATHLETES = "athletes";

  private static final int MAX_SEARCH_TERM_LENGTH = 100;
  private static final int PAGE_SIZE = 30;
  private static final int PROFILE_PAGE_SIZE = 10;

  private final MessageService messageService;
  private final AppProperties appProperties;
  private final AthleteService athleteService;
  private final InsightService insightService;

  @Autowired
  public AthleteWebController(
      MessageService messageService, AppProperties appProperties,
      AthleteService athleteService, InsightService insightService
  ) {
    this.messageService = messageService;
    this.appProperties = appProperties;
    this.athleteService = athleteService;
    this.insightService = insightService;
  }

  /**
   * Returns the athletes listing page with paginated athletes filtered by letter range, gender,
   * and free-text search term, ordered by known name. Invalid letter-range and gender values are
   * ignored. The search term is trimmed and capped in length but otherwise applied verbatim as an
   * accent-insensitive partial match against known and long names. Also builds the pagination and
   * filter-toggle URLs used by the view. An out-of-range page redirects to the last valid page, or
   * to the bare listing URL when there are no results, retaining the active filters.
   *
   * @param language    the language path variable ({@code en} or {@code pt})
   * @param page        the page number from the query string (default {@code 0}, clamped to
   *                    non-negative)
   * @param letterRange the optional letter-range identifier ({@code a_f}, case-insensitive)
   * @param gender      the optional gender filter ({@code MALE} or {@code FEMALE},
   *                    case-insensitive)
   * @param searchTerm  the optional free-text search term (query parameter {@code q})
   * @param request     the current HTTP request for building page metadata
   * @param model       the model to populate with view attributes
   * @return the athletes view name, or a redirect view name for an out-of-range page
   */
  @GetMapping("/{language:en|pt}/athletes")
  public String getAthletes(
      @PathVariable String language, @RequestParam(defaultValue = "0") int page,
      @RequestParam(required = false) String letterRange, @RequestParam(required = false) String gender,
      @RequestParam(name = "q", required = false) String searchTerm, HttpServletRequest request, Model model
  ) {
    Locale locale = resolveLocale(language);

    PageMetadata metadata = PageMetadataUtils.create(VIEW_ATHLETES,
        messageService.getMessage("page.athletes.metadata.title", null, locale),
        messageService.getMessage("page.athletes.metadata.description", null, locale), request,
        appProperties
    );

    int clampedPage = Math.max(0, page);
    AthleteFilterDTO filter = normalizeFilter(letterRange, gender, searchTerm);
    AthletesPageDTO athletesPage = athleteService.getAthletes(PageRequest.of(clampedPage, PAGE_SIZE), filter);
    int totalPages = athletesPage.pagination().totalPages();
    boolean isFiltered = hasActiveFilter(filter);

    String athletesPath = buildAthletesPath(locale);

    String redirectView = buildRedirectView(athletesPath, clampedPage, totalPages, filter);
    if (redirectView != null) {
      return redirectView;
    }

    applyMetadataUrls(metadata, locale, isFiltered, clampedPage, appProperties);

    model.addAttribute(LANGUAGE, locale.getLanguage());
    model.addAttribute(METADATA, metadata);
    model.addAttribute(PAGINATION, athletesPage.pagination());
    model.addAttribute(ATTRIBUTE_ATHLETES, athletesPage.athletes());
    model.addAttribute(ATTRIBUTE_PAGE_WINDOW, PaginationUtils.pageWindow(clampedPage, totalPages));
    model.addAttribute(ATTRIBUTE_FILTER, filter);
    model.addAttribute(ATTRIBUTE_LETTER_RANGES, athletesPage.letterRanges());
    model.addAttribute(ATTRIBUTE_FILTERED, isFiltered);
    model.addAttribute(ATTRIBUTE_PAGINATION_URLS, buildPaginationUrls(athletesPath, clampedPage, totalPages, filter));
    model.addAttribute(ATTRIBUTE_PAGINATION_PREVIOUS_URL,
        paginationUrl(athletesPage.pagination().hasPrevious(), athletesPath, clampedPage - 1, filter)
    );
    model.addAttribute(ATTRIBUTE_PAGINATION_NEXT_URL,
        paginationUrl(athletesPage.pagination().hasNext(), athletesPath, clampedPage + 1, filter)
    );
    model.addAttribute(ATTRIBUTE_ATHLETES_URL, athletesPath);
    model.addAttribute(ATTRIBUTE_LETTER_RANGE_URLS, buildLetterRangeUrls(athletesPath, filter));
    model.addAttribute(ATTRIBUTE_GENDER_URLS, buildGenderUrls(athletesPath, filter));

    return VIEW_ATHLETES;
  }

  /**
   * Returns the athlete profile page for a specific athlete, including their races and all
   * authored insight articles. An out-of-range page redirects to the last valid page, or to the bare
   * profile URL when there are no results.
   *
   * @param language the language path variable ({@code en} or {@code pt})
   * @param id       the ID of the athlete
   * @param page     the page number from the query string (default {@code 0}, clamped to
   *                 non-negative)
   * @param request  the current HTTP request for building page metadata
   * @param model    the model to populate with view attributes
   * @return the athlete profile view name, or a redirect view name for an out-of-range page
   */
  @GetMapping("/{language:en|pt}/athletes/{id:\\d+}")
  public String getAthleteById(
      @PathVariable String language,
      @PathVariable Long id,
      @RequestParam(defaultValue = "0") int page,
      HttpServletRequest request, Model model
  ) {
    Locale locale = resolveLocale(language);

    AthleteDTO athlete = athleteService.getAthleteById(id);

    PageMetadata metadata = PageMetadataUtils.create(VIEW_ATHLETE_PROFILE,
        messageService.getMessage("page.athlete.profile.metadata.title",
            new Object[]{athlete.knownName()}, locale
        ),
        messageService.getMessage("page.athlete.profile.metadata.description",
            new Object[]{athlete.knownName()}, locale
        ), request, appProperties
    );

    int clampedPage = Math.max(0, page);
    Pageable pageable = PageRequest.of(clampedPage, PROFILE_PAGE_SIZE);
    AthleteResultsPageDTO athleteResults = athleteService.getAthleteResults(id, pageable);

    int totalPages = athleteResults.pagination().totalPages();

    String athletePath = UriComponentsBuilder.fromPath("/")
        .pathSegment(locale.getLanguage(), ATTRIBUTE_ATHLETES, String.valueOf(id))
        .build()
        .toUriString();

    OptionalInt redirectTargetPage = PaginationUtils.redirectTargetPage(clampedPage, totalPages);
    if (redirectTargetPage.isPresent()) {
      int targetPage = redirectTargetPage.getAsInt();
      return "redirect:" + buildAthleteUrl(athletePath, targetPage == 0 ? null : targetPage);
    }

    List<ArticleSummaryDTO> athleteArticles = insightService.getArticlesByAthleteId(id, Pageable.unpaged(), locale);

    Map<Integer, String> paginationUrls = new LinkedHashMap<>();
    for (Integer pageIndex : PaginationUtils.pageWindow(clampedPage, totalPages).pages()) {
      paginationUrls.put(pageIndex, buildAthleteUrl(athletePath, pageIndex));
    }

    model.addAttribute(LANGUAGE, locale.getLanguage());
    model.addAttribute(METADATA, metadata);
    model.addAttribute(PAGINATION, athleteResults.pagination());
    model.addAttribute(ATTRIBUTE_ARTICLES, athleteArticles);
    model.addAttribute(ATTRIBUTE_ATHLETE, athlete);
    model.addAttribute(ATTRIBUTE_RESULTS, athleteResults.results());
    model.addAttribute(ATTRIBUTE_PAGE_WINDOW, PaginationUtils.pageWindow(clampedPage, totalPages));
    model.addAttribute(ATTRIBUTE_PAGINATION_URLS, paginationUrls);
    model.addAttribute(ATTRIBUTE_PAGINATION_PREVIOUS_URL,
        athleteResults.pagination().hasPrevious() ? buildAthleteUrl(athletePath, clampedPage - 1) : null
    );
    model.addAttribute(ATTRIBUTE_PAGINATION_NEXT_URL,
        athleteResults.pagination().hasNext() ? buildAthleteUrl(athletePath, clampedPage + 1) : null
    );

    return VIEW_ATHLETE_PROFILE;
  }

  /**
   * Replaces the default metadata URLs with listing URLs when the view is filtered or paginated.
   *
   * @param metadata      the page metadata to update
   * @param locale        the resolved locale, whose language is used for the canonical URL
   * @param isFiltered    whether an active filter is applied
   * @param clampedPage   the non-negative requested page number
   * @param appProperties the application configuration used to resolve the site URL
   */
  private static void applyMetadataUrls(
      PageMetadata metadata, Locale locale, boolean isFiltered, int clampedPage, AppProperties appProperties
  ) {
    if (isFiltered || clampedPage > 0) {
      String baseUrl = PageMetadataUtils.getBaseUrl(appProperties);
      metadata.setCanonicalUrl(buildAbsoluteAthletesUrl(baseUrl, locale.getLanguage()));
      metadata.setHreflangUrlEn(buildAbsoluteAthletesUrl(baseUrl, "en"));
      metadata.setHreflangUrlPt(buildAbsoluteAthletesUrl(baseUrl, "pt"));
    }
  }

  /**
   * Builds an absolute URL for the athletes listing in the given language.
   *
   * @param baseUrl  the site base URL
   * @param language the language path segment
   * @return the absolute athletes listing URL
   */
  private static String buildAbsoluteAthletesUrl(String baseUrl, String language) {
    return UriComponentsBuilder.fromUriString(baseUrl).pathSegment(language, VIEW_ATHLETES).build().toUriString();
  }

  /**
   * Builds a context-relative athlete profile URL, optionally carrying a page query parameter.
   *
   * @param path the profile path, already containing the language and athlete ID
   * @param page the 0-based page number, or {@code null} to omit the query parameter
   * @return the encoded, context-relative profile URL
   */
  private static String buildAthleteUrl(String path, Integer page) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromPath(path);
    if (page != null) {
      builder.queryParam("page", page);
    }
    return builder.build().encode().toUriString();
  }

  /**
   * Builds the context-relative path of the athletes listing for the given locale.
   *
   * @param locale the resolved locale, whose language is used as the first path segment
   * @return the context-relative listing path
   */
  private static String buildAthletesPath(Locale locale) {
    return UriComponentsBuilder.fromPath("/")
        .pathSegment(locale.getLanguage(), VIEW_ATHLETES)
        .build()
        .toUriString();
  }

  /**
   * Builds a context-relative athletes listing URL carrying the page number and any active filters.
   * A {@code null} page omits the {@code page} query parameter.
   *
   * @param path   the listing path, already containing the language segment
   * @param page   the 0-based page number, or {@code null} to omit the query parameter
   * @param filter the normalized filter supplying the optional query parameters
   * @return the encoded, context-relative listing URL
   */
  private static String buildAthletesUrl(String path, Integer page, AthleteFilterDTO filter) {
    UriComponentsBuilder builder = UriComponentsBuilder.fromPath(path);
    if (page != null) {
      builder.queryParam("page", page);
    }

    if (filter.letterRange() != null) {
      builder.queryParam("letterRange", filter.letterRange());
    }

    if (filter.gender() != null) {
      builder.queryParam("gender", filter.gender().getCode());
    }

    if (filter.searchTerm() != null) {
      builder.queryParam("q", filter.searchTerm());
    }

    return builder.build().encode().toUriString();
  }

  /**
   * Builds the filter-toggle URLs for every gender option, plus an {@code ALL} entry that clears the
   * active gender while retaining the other filters.
   *
   * @param athletesPath the context-relative listing path
   * @param filter       the currently active filter
   * @return a map of gender code to listing URL
   */
  private static Map<String, String> buildGenderUrls(String athletesPath, AthleteFilterDTO filter) {
    Map<String, String> genderUrls = new LinkedHashMap<>();
    genderUrls.put("ALL",
        buildAthletesUrl(athletesPath, null, new AthleteFilterDTO(filter.letterRange(), null, filter.searchTerm()))
    );
    for (AthleteGender athleteGender : AthleteGender.values()) {
      AthleteGender selectedGender = filter.gender() == athleteGender ? null : athleteGender;
      genderUrls.put(athleteGender.getCode(), buildAthletesUrl(athletesPath, null,
              new AthleteFilterDTO(filter.letterRange(), selectedGender, filter.searchTerm())
          )
      );
    }
    return genderUrls;
  }

  /**
   * Builds the filter-toggle URLs for every letter range, plus an {@code ALL} entry that clears the
   * active letter range while retaining the other filters.
   *
   * @param athletesPath the context-relative listing path
   * @param filter       the currently active filter
   * @return a map of letter-range ID to listing URL
   */
  private static Map<String, String> buildLetterRangeUrls(String athletesPath, AthleteFilterDTO filter) {
    Map<String, String> letterRangeUrls = new LinkedHashMap<>();
    letterRangeUrls.put("ALL",
        buildAthletesUrl(athletesPath, null, new AthleteFilterDTO(null, filter.gender(), filter.searchTerm()))
    );
    for (AthleteLetterRange range : AthleteLetterRange.values()) {
      String rangeId =
          filter.letterRange() != null && filter.letterRange().equals(range.getId()) ? null : range.getId();
      letterRangeUrls.put(range.getId(),
          buildAthletesUrl(athletesPath, null, new AthleteFilterDTO(rangeId, filter.gender(), filter.searchTerm()))
      );
    }
    return letterRangeUrls;
  }

  /**
   * Builds the URLs of the page indices in the pagination window for the current request.
   *
   * @param athletesPath the context-relative listing path
   * @param clampedPage  the non-negative requested page number
   * @param totalPages   the total number of pages
   * @param filter       the currently active filter, retained on every URL
   * @return a map of 0-based page index to listing URL
   */
  private static Map<Integer, String> buildPaginationUrls(
      String athletesPath, int clampedPage, int totalPages, AthleteFilterDTO filter
  ) {
    Map<Integer, String> paginationUrls = new LinkedHashMap<>();
    for (Integer pageIndex : PaginationUtils.pageWindow(clampedPage, totalPages).pages()) {
      paginationUrls.put(pageIndex, buildAthletesUrl(athletesPath, pageIndex, filter));
    }
    return paginationUrls;
  }

  /**
   * Returns the redirect view name for an out-of-range page, or {@code null} when the page is valid.
   * The redirect targets the last valid page, or the bare listing URL when there are no results.
   *
   * @param athletesPath the context-relative listing path
   * @param clampedPage  the non-negative requested page number
   * @param totalPages   the total number of pages
   * @param filter       the currently active filter, retained on the redirect
   * @return the redirect view name, or {@code null} when no redirect is needed
   */
  private static String buildRedirectView(
      String athletesPath, int clampedPage, int totalPages, AthleteFilterDTO filter
  ) {
    OptionalInt redirectTargetPage = PaginationUtils.redirectTargetPage(clampedPage, totalPages);
    if (redirectTargetPage.isEmpty()) {
      return null;
    }
    int targetPage = redirectTargetPage.getAsInt();
    return "redirect:" + buildAthletesUrl(athletesPath, targetPage == 0 ? null : targetPage, filter);
  }

  /**
   * Tells whether any of the three listing filters is active.
   *
   * @param filter the normalized filter
   * @return {@code true} when a letter range, gender or search term is set
   */
  private static boolean hasActiveFilter(AthleteFilterDTO filter) {
    return filter.letterRange() != null || filter.gender() != null || filter.searchTerm() != null;
  }

  /**
   * Normalizes the raw request parameters into an {@link AthleteFilterDTO}. The letter range and
   * gender are validated against their enumerations, and the search term is trimmed and capped in
   * length. The search term content is otherwise kept verbatim: it is treated as plain data by the
   * persistence layer (Criteria API binding with LIKE escaping) and escaped by the view layer on
   * output.
   *
   * @param letterRange the raw letter-range request parameter
   * @param gender      the raw gender request parameter
   * @param searchTerm  the raw search-term request parameter
   * @return the normalized filter
   */
  private static AthleteFilterDTO normalizeFilter(String letterRange, String gender, String searchTerm) {
    AthleteLetterRange normalizedRange = AthleteLetterRange.fromId(letterRange);

    AthleteGender normalizedGender;
    if (gender != null) {
      try {
        normalizedGender = AthleteGender.valueOf(gender.trim().toUpperCase(Locale.ROOT));
      } catch (IllegalArgumentException _) {
        normalizedGender = null;
      }
    } else {
      normalizedGender = null;
    }

    return new AthleteFilterDTO(normalizedRange == null ? null : normalizedRange.getId(), normalizedGender,
        normalizeSearchTerm(searchTerm)
    );
  }

  /**
   * Trims a raw search term and caps its length. The content is otherwise kept verbatim.
   *
   * @param searchTerm the raw search-term request parameter, possibly {@code null}
   * @return the normalized search term, or {@code null} when none was supplied
   */
  private static String normalizeSearchTerm(String searchTerm) {
    if (searchTerm == null) {
      return null;
    }

    String normalized = searchTerm.trim();
    return normalized.length() > MAX_SEARCH_TERM_LENGTH ? normalized.substring(0, MAX_SEARCH_TERM_LENGTH) : normalized;
  }

  /**
   * Builds the listing URL for an adjacent page, or {@code null} when that page does not exist.
   *
   * @param hasPage      whether the adjacent page exists
   * @param athletesPath the context-relative listing path
   * @param page         the 0-based adjacent page number
   * @param filter       the currently active filter, retained on the URL
   * @return the listing URL, or {@code null} when there is no adjacent page
   */
  private static String paginationUrl(boolean hasPage, String athletesPath, int page, AthleteFilterDTO filter) {
    return hasPage ? buildAthletesUrl(athletesPath, page, filter) : null;
  }

  /**
   * Resolves the locale for a language path segment, treating anything other than {@code pt} as
   * English.
   *
   * @param language the language path variable ({@code en} or {@code pt})
   * @return the resolved locale
   */
  private static Locale resolveLocale(String language) {
    return "pt".equalsIgnoreCase(language) ? LOCALE_PORTUGUESE : Locale.ENGLISH;
  }
}
