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
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.business.competitor.dto.AthleteRacesPageDTO;
import com.endurancetrio.business.competitor.dto.AthletesPageDTO;
import com.endurancetrio.business.competitor.service.AthleteService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The {@link AthleteWebController} handles Thymeleaf views for the athletes section.
 */
@EnduranceTrioWebController
public class AthleteWebController {

  private static final String ATTRIBUTE_ATHLETE = "athlete";
  private static final String ATTRIBUTE_ATHLETES = "athletes";
  private static final String ATTRIBUTE_RACES = "races";
  private static final String VIEW_ATHLETE_PROFILE = "athlete-profile";
  private static final String VIEW_ATHLETES = "athletes";

  private static final int PAGE_SIZE = 30;
  private static final int PROFILE_PAGE_SIZE = 10;

  private final MessageService messageService;
  private final AppProperties appProperties;
  private final AthleteService athleteService;

  @Autowired
  public AthleteWebController(
      MessageService messageService, AppProperties appProperties,
      AthleteService athleteService
  ) {
    this.messageService = messageService;
    this.appProperties = appProperties;
    this.athleteService = athleteService;
  }

  /**
   * Returns the athletes listing page with paginated athletes ordered by known name.
   *
   * @param language the language path variable ({@code en} or {@code pt})
   * @param page     the page number from the query string (default {@code 0}, clamped to
   *                 non-negative)
   * @param request  the current HTTP request for building page metadata
   * @param model    the model to populate with view attributes
   * @return the athletes view name
   */
  @GetMapping("/{language:en|pt}/athletes")
  public String getAthletes(
      @PathVariable String language,
      @RequestParam(defaultValue = "0") int page, HttpServletRequest request, Model model
  ) {
    Locale locale = "pt".equalsIgnoreCase(language) ? LOCALE_PORTUGUESE : Locale.ENGLISH;

    PageMetadata metadata = PageMetadataUtils.create(VIEW_ATHLETES,
        messageService.getMessage("page.athletes.metadata.title", null, locale),
        messageService.getMessage("page.athletes.metadata.description", null, locale), request,
        appProperties
    );

    int clampedPage = Math.max(0, page);
    Pageable pageable = PageRequest.of(clampedPage, PAGE_SIZE);
    AthletesPageDTO athletesPage = athleteService.getAthletes(pageable);

    model.addAttribute(LANGUAGE, locale.getLanguage());
    model.addAttribute(METADATA, metadata);
    model.addAttribute(PAGINATION, athletesPage.pagination());
    model.addAttribute(ATTRIBUTE_ATHLETES, athletesPage.athletes());

    return VIEW_ATHLETES;
  }

  /**
   * Returns the athlete profile page for a specific athlete, including their races.
   *
   * @param language the language path variable ({@code en} or {@code pt})
   * @param id       the ID of the athlete
   * @param page     the page number from the query string (default {@code 0}, clamped to
   *                 non-negative)
   * @param request  the current HTTP request for building page metadata
   * @param model    the model to populate with view attributes
   * @return the athlete profile view name
   */
  @GetMapping("/{language:en|pt}/athletes/{id}")
  public String getAthleteById(
      @PathVariable String language,
      @PathVariable Long id,
      @RequestParam(defaultValue = "0") int page,
      HttpServletRequest request, Model model
  ) {
    Locale locale = "pt".equalsIgnoreCase(language) ? LOCALE_PORTUGUESE : Locale.ENGLISH;

    PageMetadata metadata = PageMetadataUtils.create(VIEW_ATHLETE_PROFILE,
        messageService.getMessage("page.athlete.profile.metadata.title", null, locale),
        messageService.getMessage("page.athlete.profile.metadata.description", null, locale),
        request, appProperties
    );

    int clampedPage = Math.max(0, page);
    Pageable pageable = PageRequest.of(clampedPage, PROFILE_PAGE_SIZE);
    AthleteRacesPageDTO athleteRaces = athleteService.getAthleteRaces(id, pageable);

    model.addAttribute(LANGUAGE, locale.getLanguage());
    model.addAttribute(METADATA, metadata);
    model.addAttribute(ATTRIBUTE_ATHLETE, athleteService.getAthleteById(id));
    model.addAttribute(ATTRIBUTE_RACES, athleteRaces.races());
    model.addAttribute(PAGINATION, athleteRaces.pagination());

    return VIEW_ATHLETE_PROFILE;
  }
}
