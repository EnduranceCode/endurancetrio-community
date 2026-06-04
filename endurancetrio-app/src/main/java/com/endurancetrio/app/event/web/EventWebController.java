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

import com.endurancetrio.app.common.annotation.EnduranceTrioWebController;
import com.endurancetrio.app.common.model.PageMetadata;
import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.common.utils.PageMetadataUtils;
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.business.event.dto.EventYearsDTO;
import com.endurancetrio.business.event.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@EnduranceTrioWebController
public class EventWebController {

  private static final String VIEW = "events";
  private static final Locale PORTUGUESE_LOCALE = Locale.of("pt", "PT");
  private static final int BATCH_SIZE = 3;

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
  public String eventsYears(
      @PathVariable String language, @RequestParam(defaultValue = "0") int page,
      HttpServletRequest request, Model model
  ) {
    Locale locale = "pt".equalsIgnoreCase(language) ? PORTUGUESE_LOCALE : Locale.ENGLISH;

    PageMetadata metadata = PageMetadataUtils.create(VIEW,
        messageService.getMessage("page.events.metadata.title", null, locale),
        messageService.getMessage("page.events.metadata.description", null, locale), request,
        appProperties
    );

    EventYearsDTO eventYears = getEventYearsDTO(page);

    model.addAttribute("language", locale.getLanguage());
    model.addAttribute("metadata", metadata);
    model.addAttribute("eventYears", eventYears);

    return VIEW;
  }

  @GetMapping("/{language:en|pt}/events/{year}")
  public String eventsYear(
      @PathVariable String language, @PathVariable int year, HttpServletRequest request,
      Model model
  ) {
    Locale locale = "pt".equalsIgnoreCase(language) ? PORTUGUESE_LOCALE : Locale.ENGLISH;

    PageMetadata metadata = PageMetadataUtils.create(VIEW,
        messageService.getMessage("page.events.year.metadata.title", null, locale),
        messageService.getMessage("page.events.year.metadata.description", null, locale), request,
        appProperties
    );

    model.addAttribute("language", locale.getLanguage());
    model.addAttribute("metadata", metadata);

    return "events-year";
  }

  /**
   * Builds an {@link EventYearsDTO} for the given page.
   * <p>
   * The raw {@code page} parameter (from the URL query string) is clamped to the valid range
   * {@code [0, totalBatches - 1]} to produce the batch index. This index is used to slice the full
   * year list into the current batch ({@link EventYearsDTO#years()}), the next batch
   * ({@link EventYearsDTO#nextYears()}), and the previous batch
   * ({@link EventYearsDTO#previousYears()}). Two-batch (desktop) navigation pages are also computed
   * via {@link EventYearsDTO#batchGroupPreviousPage()} and
   * {@link EventYearsDTO#batchGroupNextPage()}.
   *
   * @param page the requested page from the URL query {@code page} parameter (may be out of
   *             bounds)
   * @return a fully populated {@link EventYearsDTO}
   */
  private @NonNull EventYearsDTO getEventYearsDTO(int page) {
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

    return new EventYearsDTO(years, nextYears, previousYears, batchIndex, totalBatches, totalYears,
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
