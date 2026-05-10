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

package com.endurancetrio.app.common.web;

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
class AboutWebControllerTest {

  @Mock
  MessageService messageService;

  AppProperties appProperties;

  AboutWebController aboutWebController;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    appProperties = new AppProperties();
    appProperties.getOpenGraph().setDefaultImg("/img/endurancetrio-open-graph.png");
    appProperties.getOpenGraph().setDefaultImgWidth(1200);
    appProperties.getOpenGraph().setDefaultImgHeight(628);
    appProperties.getSocial().setFacebookPageId("1692877750958091");
    appProperties.getSocial().setTwitterSite("@EnduranceTrio");

    aboutWebController = new AboutWebController(messageService, appProperties);

    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/views/");
    viewResolver.setSuffix(".html");

    mockMvc = MockMvcBuilders.standaloneSetup(aboutWebController)
        .setViewResolvers(viewResolver)
        .build();
  }

  @Test
  void aboutPageWithEnglishLocale() throws Exception {
    when(messageService.getMessage(eq("page.about.metadata.title"), any(), any()))
        .thenReturn("About - EnduranceTrio");
    when(messageService.getMessage(eq("page.about.metadata.description"), any(), any()))
        .thenReturn("A central hub for endurance sports data and resources");

    mockMvc.perform(get("/en/about"))
        .andExpect(status().isOk())
        .andExpect(view().name("about"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attributeExists("metadata"));
  }

  @Test
  void aboutPageWithPortugueseLocale() throws Exception {
    when(messageService.getMessage(eq("page.about.metadata.title"), any(), any()))
        .thenReturn("Sobre - EnduranceTrio");
    when(messageService.getMessage(eq("page.about.metadata.description"), any(), any()))
        .thenReturn("Uma plataforma central para dados e recursos de desportos de endurance");

    mockMvc.perform(get("/pt/about"))
        .andExpect(status().isOk())
        .andExpect(view().name("about"))
        .andExpect(model().attribute("language", "pt"))
        .andExpect(model().attributeExists("metadata"));
  }

  @Test
  void aboutPageMetadataHasCorrectTitle() throws Exception {
    when(messageService.getMessage(eq("page.about.metadata.title"), any(), any()))
        .thenReturn("About - EnduranceTrio");
    when(messageService.getMessage(eq("page.about.metadata.description"), any(), any()))
        .thenReturn("A central hub for endurance sports data and resources");

    mockMvc.perform(get("/en/about"))
        .andExpect(model().attribute("metadata",
            org.hamcrest.Matchers.hasProperty("title",
                org.hamcrest.Matchers.is("About - EnduranceTrio"))));
  }
}
