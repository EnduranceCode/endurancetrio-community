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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import com.endurancetrio.business.common.dto.SitemapEntry;
import com.endurancetrio.data.event.model.entity.Event;
import com.endurancetrio.data.event.repository.EventRepository;
import com.endurancetrio.data.insight.model.entity.Article;
import com.endurancetrio.data.insight.repository.ArticleRepository;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class SitemapServiceMainTest {

  private static final String BASE_URL = "https://endurancetrio.com";

  @Mock
  ArticleRepository articleRepository;

  @Mock
  EventRepository eventRepository;

  @InjectMocks
  SitemapServiceMain underTest;

  @Test
  void getSitemapEntriesShouldReturnStaticAndDynamicEntries() {
    Article article = new Article();
    article.setId(1L);
    article.setSlug("how-to-train");
    article.setPublishedDate(LocalDateTime.of(2026, Month.MAY, 1, 10, 0));
    ReflectionTestUtils.setField(article, "createdAt", Instant.parse("2026-04-20T10:00:00Z"));
    ReflectionTestUtils.setField(article, "updatedAt", Instant.parse("2026-05-01T12:00:00Z"));
    when(articleRepository.findByPublishedDateIsNotNullOrderByPublishedDateDesc()).thenReturn(
        List.of(article));

    Event event = new Event();
    event.setId(10L);
    event.setStartDate(LocalDate.of(2026, Month.AUGUST, 15));
    ReflectionTestUtils.setField(event, "createdAt", Instant.parse("2026-07-10T10:00:00Z"));
    when(eventRepository.findAllByOrderByStartDateDesc()).thenReturn(List.of(event));

    List<SitemapEntry> entries = underTest.getSitemapEntries(BASE_URL);

    assertEquals(9, entries.size());

    SitemapEntry home = entries.get(0);
    assertEquals("https://endurancetrio.com/en/", home.urlEn());
    assertEquals("https://endurancetrio.com/pt/", home.urlPt());
    assertEquals(LocalDate.now(ZoneOffset.UTC).toString(), home.lastModified());
    assertEquals(1.0, home.priority());

    SitemapEntry insights = entries.get(1);
    assertEquals("https://endurancetrio.com/en/insights", insights.urlEn());
    assertEquals("https://endurancetrio.com/pt/insights", insights.urlPt());
    assertEquals(0.7, insights.priority());

    SitemapEntry events = entries.get(2);
    assertEquals("https://endurancetrio.com/en/events", events.urlEn());
    assertEquals("https://endurancetrio.com/pt/events", events.urlPt());
    assertEquals(0.7, events.priority());

    SitemapEntry about = entries.get(3);
    assertEquals("https://endurancetrio.com/en/about", about.urlEn());
    assertEquals("https://endurancetrio.com/pt/about", about.urlPt());
    assertEquals(0.6, about.priority());

    SitemapEntry mission = entries.get(4);
    assertEquals("https://endurancetrio.com/en/mission", mission.urlEn());
    assertEquals("https://endurancetrio.com/pt/mission", mission.urlPt());
    assertEquals(0.6, mission.priority());

    SitemapEntry athletes = entries.get(5);
    assertEquals("https://endurancetrio.com/en/athletes", athletes.urlEn());
    assertEquals("https://endurancetrio.com/pt/athletes", athletes.urlPt());
    assertEquals(0.5, athletes.priority());

    SitemapEntry privacyPolicy = entries.get(6);
    assertEquals("https://endurancetrio.com/en/privacy-policy", privacyPolicy.urlEn());
    assertEquals("https://endurancetrio.com/pt/privacy-policy", privacyPolicy.urlPt());
    assertEquals(0.3, privacyPolicy.priority());

    SitemapEntry eventEntry = entries.get(7);
    assertEquals("https://endurancetrio.com/en/events/2026/10/overview", eventEntry.urlEn());
    assertEquals("https://endurancetrio.com/pt/events/2026/10/overview", eventEntry.urlPt());
    assertEquals("2026-07-10", eventEntry.lastModified());
    assertEquals(0.8, eventEntry.priority());

    SitemapEntry articleEntry = entries.get(8);
    assertEquals("https://endurancetrio.com/en/insights/how-to-train", articleEntry.urlEn());
    assertEquals("https://endurancetrio.com/pt/insights/how-to-train", articleEntry.urlPt());
    assertEquals("2026-05-01", articleEntry.lastModified());
    assertEquals(0.7, articleEntry.priority());
  }

  @Test
  void getSitemapEntriesShouldFallBackToCreatedAtWhenUpdatedAtIsNull() {
    Article article = new Article();
    article.setId(1L);
    article.setSlug("how-to-train");
    article.setPublishedDate(LocalDateTime.of(2026, Month.MAY, 1, 10, 0));
    ReflectionTestUtils.setField(article, "createdAt", Instant.parse("2026-04-20T10:00:00Z"));
    when(articleRepository.findByPublishedDateIsNotNullOrderByPublishedDateDesc()).thenReturn(
        List.of(article));

    Event event = new Event();
    event.setId(10L);
    event.setStartDate(LocalDate.of(2026, Month.AUGUST, 15));
    ReflectionTestUtils.setField(event, "createdAt", Instant.parse("2026-07-10T10:00:00Z"));
    when(eventRepository.findAllByOrderByStartDateDesc()).thenReturn(List.of(event));

    List<SitemapEntry> entries = underTest.getSitemapEntries(BASE_URL);

    assertEquals("2026-07-10", entries.get(7).lastModified());
    assertEquals("2026-04-20", entries.get(8).lastModified());
  }

  @Test
  void getSitemapEntriesShouldReturnOnlyStaticPagesWhenNoData() {
    when(articleRepository.findByPublishedDateIsNotNullOrderByPublishedDateDesc()).thenReturn(
        List.of());
    when(eventRepository.findAllByOrderByStartDateDesc()).thenReturn(List.of());

    List<SitemapEntry> entries = underTest.getSitemapEntries(BASE_URL);

    assertEquals(7, entries.size());
    assertEquals("https://endurancetrio.com/en/", entries.getFirst().urlEn());
    assertEquals("https://endurancetrio.com/en/privacy-policy", entries.getLast().urlEn());
  }
}
