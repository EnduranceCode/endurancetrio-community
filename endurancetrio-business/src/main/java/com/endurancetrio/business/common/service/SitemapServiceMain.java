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

package com.endurancetrio.business.common.service;

import com.endurancetrio.business.common.dto.SitemapEntry;
import com.endurancetrio.data.common.model.entity.AuditableEntity;
import com.endurancetrio.data.event.model.entity.Event;
import com.endurancetrio.data.event.repository.EventRepository;
import com.endurancetrio.data.insight.model.entity.Article;
import com.endurancetrio.data.insight.repository.ArticleRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The {@link SitemapServiceMain} builds the sitemap entries for the static pages and for every
 * event and published article, each in both language variants.
 */
@Service
public class SitemapServiceMain implements SitemapService {

  private static final double PRIORITY_HOME = 1.0;
  private static final double PRIORITY_EVENT = 0.8;
  private static final double PRIORITY_LISTING = 0.7;
  private static final double PRIORITY_ABOUT = 0.6;
  private static final double PRIORITY_ATHLETES = 0.5;
  private static final double PRIORITY_POLICY = 0.3;

  private final ArticleRepository articleRepository;
  private final EventRepository eventRepository;

  @Autowired
  public SitemapServiceMain(ArticleRepository articleRepository, EventRepository eventRepository) {
    this.articleRepository = articleRepository;
    this.eventRepository = eventRepository;
  }

  @Override
  @Transactional(readOnly = true)
  public List<SitemapEntry> getSitemapEntries(String baseUrl) {
    List<SitemapEntry> entries = new ArrayList<>();
    entries.addAll(buildStaticPageEntries(baseUrl));
    entries.addAll(buildEventEntries(baseUrl));
    entries.addAll(buildArticleEntries(baseUrl));
    return entries;
  }

  /**
   * Builds the sitemap entries for the static pages, ordered by descending priority.
   *
   * @param baseUrl the normalized site base URL
   * @return the static page entries
   */
  private static List<SitemapEntry> buildStaticPageEntries(String baseUrl) {
    String today = LocalDate.now(ZoneOffset.UTC).toString();

    List<SitemapEntry> entries = new ArrayList<>();
    entries.add(new SitemapEntry(baseUrl + "/en/", baseUrl + "/pt/", today, PRIORITY_HOME));
    entries.add(new SitemapEntry(baseUrl + "/en/insights", baseUrl + "/pt/insights", today, PRIORITY_LISTING));
    entries.add(new SitemapEntry(baseUrl + "/en/events", baseUrl + "/pt/events", today, PRIORITY_LISTING));
    entries.add(new SitemapEntry(baseUrl + "/en/about", baseUrl + "/pt/about", today, PRIORITY_ABOUT));
    entries.add(new SitemapEntry(baseUrl + "/en/mission", baseUrl + "/pt/mission", today, PRIORITY_ABOUT));
    entries.add(new SitemapEntry(baseUrl + "/en/athletes", baseUrl + "/pt/athletes", today, PRIORITY_ATHLETES));
    entries.add(
        new SitemapEntry(baseUrl + "/en/privacy-policy", baseUrl + "/pt/privacy-policy", today, PRIORITY_POLICY));
    return entries;
  }

  /**
   * Builds the sitemap entries for every event overview page.
   *
   * @param baseUrl the normalized site base URL
   * @return the event overview entries
   */
  private List<SitemapEntry> buildEventEntries(String baseUrl) {
    List<SitemapEntry> entries = new ArrayList<>();
    for (Event event : eventRepository.findAllByOrderByStartDateDesc()) {
      String path = String.format("/events/%d/%d/overview", event.getStartDate().getYear(), event.getId());
      entries.add(
          new SitemapEntry(baseUrl + "/en" + path, baseUrl + "/pt" + path, lastModifiedOf(event), PRIORITY_EVENT));
    }
    return entries;
  }

  /**
   * Builds the sitemap entries for every published article.
   *
   * @param baseUrl the normalized site base URL
   * @return the article entries
   */
  private List<SitemapEntry> buildArticleEntries(String baseUrl) {
    List<SitemapEntry> entries = new ArrayList<>();
    for (Article article : articleRepository.findByPublishedDateIsNotNullOrderByPublishedDateDesc()) {
      String path = "/insights/" + article.getSlug();
      String lastModified = lastModifiedOf(article);
      entries.add(new SitemapEntry(baseUrl + "/en" + path, baseUrl + "/pt" + path, lastModified, PRIORITY_LISTING));
    }
    return entries;
  }

  /**
   * Returns the date the given entity was last modified, as an ISO-8601 date in UTC.
   * <p>
   * The last modification date is the entity's {@code updatedAt} when set, falling back to its
   * {@code createdAt}. It can be {@code null} only when neither auditing timestamp is populated.
   *
   * @param entity the entity whose last modification date is requested
   * @return the last modification date in ISO-8601 format, or {@code null} when unknown
   */
  private static String lastModifiedOf(AuditableEntity entity) {
    Instant lastModified = entity.getUpdatedAt() != null ? entity.getUpdatedAt() : entity.getCreatedAt();
    return lastModified == null ? null : lastModified.atOffset(ZoneOffset.UTC).toLocalDate().toString();
  }
}
