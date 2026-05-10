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

import com.endurancetrio.app.common.model.PageMetadata;
import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.config.AppProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ExtendWith(MockitoExtension.class)
class EventWebControllerTest {

  @Mock
  MessageService messageService;

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

    eventWebController = new EventWebController(messageService, appProperties);

    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/views/");
    viewResolver.setSuffix(".html");

    mockMvc = MockMvcBuilders.standaloneSetup(eventWebController)
        .setViewResolvers(viewResolver)
        .build();
  }

  @Test
  void eventYearsPageWithEnglishLocale() throws Exception {
    when(messageService.getMessage(eq("page.events.metadata.title"), any(), any()))
        .thenReturn("Events - EnduranceTrio");
    when(messageService.getMessage(eq("page.events.metadata.description"), any(), any()))
        .thenReturn("Browse endurance sports events by year");

    mockMvc.perform(get("/en/events"))
        .andExpect(status().isOk())
        .andExpect(view().name("events"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attributeExists("metadata"));
  }

  @Test
  void eventYearsPageWithPortugueseLocale() throws Exception {
    when(messageService.getMessage(eq("page.events.metadata.title"), any(), any()))
        .thenReturn("Eventos - EnduranceTrio");
    when(messageService.getMessage(eq("page.events.metadata.description"), any(), any()))
        .thenReturn("Explore eventos de desportos de endurance por ano");

    mockMvc.perform(get("/pt/events"))
        .andExpect(status().isOk())
        .andExpect(view().name("events"))
        .andExpect(model().attribute("language", "pt"))
        .andExpect(model().attributeExists("metadata"));
  }

  @Test
  void eventYearsPageMetadataHasCorrectTitle() throws Exception {
    when(messageService.getMessage(eq("page.events.metadata.title"), any(), any()))
        .thenReturn("Events - EnduranceTrio");
    when(messageService.getMessage(eq("page.events.metadata.description"), any(), any()))
        .thenReturn("Browse endurance sports events by year");

    mockMvc.perform(get("/en/events"))
        .andExpect(model().attribute("metadata",
            org.hamcrest.Matchers.hasProperty("title",
                org.hamcrest.Matchers.is("Events - EnduranceTrio"))));
  }
}
