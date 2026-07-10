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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.app.insight.fixtures.ArticleDTOFixtures;
import com.endurancetrio.business.common.dto.PaginationDTO;
import com.endurancetrio.business.insight.dto.ArticleDTO;
import com.endurancetrio.business.insight.dto.InsightPageDTO;
import com.endurancetrio.business.insight.service.InsightService;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ExtendWith(MockitoExtension.class)
class InsightsWebControllerTest {

  private static final ArticleDTO ARTICLE_EN = ArticleDTOFixtures.standard();
  private static final ArticleDTO ARTICLE_PT = ArticleDTOFixtures.portuguese();
  private static final ArticleDTO ARTICLE_WITH_META = ArticleDTOFixtures.withMetaTitle();
  private static final PaginationDTO PAGINATION = new PaginationDTO(0, 1, 1, false, false);
  private static final InsightPageDTO INSIGHT_PAGE = new InsightPageDTO(List.of(ARTICLE_EN),
      PAGINATION
  );
  private static final InsightPageDTO INSIGHT_PAGE_PT = new InsightPageDTO(List.of(ARTICLE_PT),
      PAGINATION
  );

  @Mock
  MessageService messageService;

  @Mock
  InsightService insightService;

  AppProperties appProperties;

  InsightsWebController insightsWebController;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    appProperties = new AppProperties();
    appProperties.getOpenGraph().setDefaultImg("/img/endurancetrio-open-graph.png");
    appProperties.getOpenGraph().setDefaultImgWidth(1200);
    appProperties.getOpenGraph().setDefaultImgHeight(628);
    appProperties.getSocial().setFacebookPageId("1692877750958091");
    appProperties.getSocial().setTwitterSite("@EnduranceTrio");

    insightsWebController = new InsightsWebController(messageService, appProperties, insightService
    );

    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/views/");
    viewResolver.setSuffix(".html");

    mockMvc = MockMvcBuilders.standaloneSetup(insightsWebController)
        .setViewResolvers(viewResolver)
        .build();
  }

  @Test
  void getInsightsPageWithEnglishLocale() throws Exception {
    when(messageService.getMessage(eq("page.insights.metadata.title"), any(), any())).thenReturn(
        "Insights - EnduranceTrio");
    when(messageService.getMessage(eq("page.insights.metadata.description"), any(),
        any()
    )).thenReturn("Read the latest endurance sports insights");
    when(insightService.getArticles(eq(0), ArgumentMatchers.any(), any())).thenReturn(INSIGHT_PAGE);

    mockMvc.perform(get("/en/insights"))
        .andExpect(status().isOk())
        .andExpect(view().name("insights"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attributeExists("metadata"))
        .andExpect(model().attribute("pagination", PAGINATION))
        .andExpect(model().attribute("articles", INSIGHT_PAGE.articles()));
  }

  @Test
  void getInsightsPageWithPortugueseLocale() throws Exception {
    when(messageService.getMessage(eq("page.insights.metadata.title"), any(), any())).thenReturn(
        "Perspetivas - EnduranceTrio");
    when(messageService.getMessage(eq("page.insights.metadata.description"), any(),
        any()
    )).thenReturn("Leia as últimas perspetivas de desportos de endurance");
    when(insightService.getArticles(eq(0), ArgumentMatchers.any(), any())).thenReturn(
        INSIGHT_PAGE_PT);

    mockMvc.perform(get("/pt/insights"))
        .andExpect(status().isOk())
        .andExpect(view().name("insights"))
        .andExpect(model().attribute("language", "pt"))
        .andExpect(model().attributeExists("metadata"))
        .andExpect(model().attribute("pagination", PAGINATION))
        .andExpect(model().attribute("articles", INSIGHT_PAGE_PT.articles()));
  }

  @Test
  void getInsightsPageMetadataHasCorrectTitle() throws Exception {
    when(messageService.getMessage(eq("page.insights.metadata.title"), any(), any())).thenReturn(
        "Insights - EnduranceTrio");
    when(messageService.getMessage(eq("page.insights.metadata.description"), any(),
        any()
    )).thenReturn("Read the latest endurance sports insights");
    when(insightService.getArticles(eq(0), ArgumentMatchers.any(), any())).thenReturn(INSIGHT_PAGE);

    mockMvc.perform(get("/en/insights"))
        .andExpect(model().attribute("metadata", org.hamcrest.Matchers.hasProperty("title",
                org.hamcrest.Matchers.is("Insights - EnduranceTrio")
            )
        ));
  }

  @Test
  void getInsightDetailPageWithEnglishLocale() throws Exception {
    when(messageService.getMessage(eq("page.insights.detail.metadata.description"), any(),
        any()
    )).thenReturn("Full article view");
    when(insightService.getArticleBySlug(eq(ARTICLE_EN.slug()), any())).thenReturn(ARTICLE_EN);

    mockMvc.perform(get("/en/insights/" + ARTICLE_EN.slug()))
        .andExpect(status().isOk())
        .andExpect(view().name("insight-article"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attributeExists("metadata"))
        .andExpect(model().attribute("article", ARTICLE_EN));
  }

  @Test
  void getInsightDetailPageWithPortugueseLocale() throws Exception {
    when(messageService.getMessage(eq("page.insights.detail.metadata.description"), any(),
        any()
    )).thenReturn("Visualização completa do artigo");
    when(insightService.getArticleBySlug(eq(ARTICLE_PT.slug()), any())).thenReturn(ARTICLE_PT);

    mockMvc.perform(get("/pt/insights/" + ARTICLE_PT.slug()))
        .andExpect(status().isOk())
        .andExpect(view().name("insight-article"))
        .andExpect(model().attribute("language", "pt"))
        .andExpect(model().attributeExists("metadata"))
        .andExpect(model().attribute("article", ARTICLE_PT));
  }

  @Test
  void getInsightDetailPageMetadataUsesMetaTitle() throws Exception {
    when(messageService.getMessage(eq("page.insights.detail.metadata.description"), any(),
        any()
    )).thenReturn("Full article view");
    when(insightService.getArticleBySlug(eq(ARTICLE_WITH_META.slug()), any())).thenReturn(
        ARTICLE_WITH_META);

    mockMvc.perform(get("/en/insights/" + ARTICLE_WITH_META.slug()))
        .andExpect(model().attribute("metadata", org.hamcrest.Matchers.hasProperty("title",
                org.hamcrest.Matchers.is(ArticleDTOFixtures.META_TITLE)
            )
        ));
  }

  @Test
  void getInsightDetailPageMetadataUsesTitleFallback() throws Exception {
    when(messageService.getMessage(eq("page.insights.detail.metadata.description"), any(),
        any()
    )).thenReturn("Full article view");
    when(insightService.getArticleBySlug(eq(ARTICLE_EN.slug()), any())).thenReturn(ARTICLE_EN);

    mockMvc.perform(get("/en/insights/" + ARTICLE_EN.slug()))
        .andExpect(model().attribute("metadata", org.hamcrest.Matchers.hasProperty("title",
                org.hamcrest.Matchers.is(ArticleDTOFixtures.STANDARD_TITLE)
            )
        ));
  }
}
