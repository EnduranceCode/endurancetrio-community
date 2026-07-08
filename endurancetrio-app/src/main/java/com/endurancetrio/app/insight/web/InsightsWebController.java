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

package com.endurancetrio.app.insight.web;

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
import com.endurancetrio.business.event.service.EventService;
import com.endurancetrio.business.insight.dto.ArticleDTO;
import com.endurancetrio.business.insight.dto.InsightPageDTO;
import com.endurancetrio.business.insight.service.InsightService;
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
 * The {@link InsightsWebController} handles Thymeleaf views for the Insights section.
 */
@EnduranceTrioWebController
public class InsightsWebController {

  private static final String VIEW_EVENT_INSIGHTS = "event-insights";
  private static final String VIEW_INSIGHT_ARTICLE = "insight-article";
  private static final String VIEW_INSIGHTS = "insights";

  private static final String ATTRIBUTE_ARTICLE = "article";
  private static final String ATTRIBUTE_ARTICLES = "articles";
  private static final String ATTRIBUTE_EVENT = "event";

  private static final int PAGE_SIZE = 10;

  private final MessageService messageService;
  private final AppProperties appProperties;
  private final InsightService insightService;
  private final EventService eventService;

  @Autowired
  public InsightsWebController(
      MessageService messageService, AppProperties appProperties, InsightService insightService,
      EventService eventService
  ) {
    this.messageService = messageService;
    this.appProperties = appProperties;
    this.insightService = insightService;
    this.eventService = eventService;
  }

  /**
   * Returns the insights listing page with paginated articles.
   *
   * @param language the language path variable ({@code en} or {@code pt})
   * @param page     the page number from the query string (default {@code 0})
   * @param request  the current HTTP request for building page metadata
   * @param model    the model to populate with view attributes
   * @return the insights view name
   */
  @GetMapping("/{language:en|pt}/insights")
  public String getInsights(
      @PathVariable String language,
      @RequestParam(defaultValue = "0") int page, HttpServletRequest request, Model model
  ) {
    Locale locale = "pt".equalsIgnoreCase(language) ? LOCALE_PORTUGUESE : Locale.ENGLISH;

    PageMetadata metadata = PageMetadataUtils.create(VIEW_INSIGHTS,
        messageService.getMessage("page.insights.metadata.title", null, locale),
        messageService.getMessage("page.insights.metadata.description", null, locale), request,
        appProperties
    );

    Pageable pageable = PageRequest.of(page, PAGE_SIZE);
    InsightPageDTO insightPage = insightService.getArticles(page, pageable, locale);

    model.addAttribute(LANGUAGE, locale.getLanguage());
    model.addAttribute(METADATA, metadata);
    model.addAttribute(PAGINATION, insightPage.pagination());
    model.addAttribute(ATTRIBUTE_ARTICLES, insightPage.articles());

    return VIEW_INSIGHTS;
  }

  /**
   * Returns the article detail page for the given slug.
   *
   * @param language the language path variable ({@code en} or {@code pt})
   * @param slug     the article URL slug
   * @param request  the current HTTP request for building page metadata
   * @param model    the model to populate with view attributes
   * @return the insight detail view name
   */
  @GetMapping("/{language:en|pt}/insights/{slug}")
  public String getInsightDetail(
      @PathVariable String language, @PathVariable String slug,
      HttpServletRequest request, Model model
  ) {
    Locale locale = "pt".equalsIgnoreCase(language) ? LOCALE_PORTUGUESE : Locale.ENGLISH;

    ArticleDTO article = insightService.getArticleBySlug(slug, locale);
    String metaTitle = article.metaTitle() != null ? article.metaTitle() : article.title();

    PageMetadata metadata = PageMetadataUtils.create(VIEW_INSIGHT_ARTICLE, metaTitle,
        messageService.getMessage("page.insights.detail.metadata.description", null, locale),
        request, appProperties
    );

    model.addAttribute(LANGUAGE, locale.getLanguage());
    model.addAttribute(METADATA, metadata);
    model.addAttribute(ATTRIBUTE_ARTICLE, article);

    return VIEW_INSIGHT_ARTICLE;
  }

  /**
   * Returns the event-scoped insights page with related articles.
   *
   * @param language the language path variable ({@code en} or {@code pt})
   * @param year     the event year
   * @param id       the event ID
   * @param request  the current HTTP request for building page metadata
   * @param model    the model to populate with view attributes
   * @return the event insights view name
   */
  @GetMapping("/{language:en|pt}/events/{year}/{id}/insights")
  public String getEventInsights(
      @PathVariable String language, @PathVariable int year,
      @PathVariable Long id, HttpServletRequest request, Model model
  ) {
    Locale locale = "pt".equalsIgnoreCase(language) ? LOCALE_PORTUGUESE : Locale.ENGLISH;

    EventOverviewDTO event = eventService.getEventOverview(id, year);

    PageMetadata metadata = PageMetadataUtils.create(VIEW_EVENT_INSIGHTS,
        messageService.getMessage("page.insights.event.metadata.title", null, locale),
        messageService.getMessage("page.insights.event.metadata.description", null, locale),
        request, appProperties
    );

    InsightPageDTO insightPage = insightService.getArticlesByEvent(id, locale);

    model.addAttribute(LANGUAGE, locale.getLanguage());
    model.addAttribute(METADATA, metadata);
    model.addAttribute(ATTRIBUTE_EVENT, event);
    model.addAttribute(ATTRIBUTE_ARTICLES, insightPage.articles());

    return VIEW_EVENT_INSIGHTS;
  }
}
