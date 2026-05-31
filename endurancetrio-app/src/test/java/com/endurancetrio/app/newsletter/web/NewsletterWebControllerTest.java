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

package com.endurancetrio.app.newsletter.web;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.newsletter.dto.NewsletterSubscriptionDTO;
import com.endurancetrio.business.newsletter.service.NewsletterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ExtendWith(MockitoExtension.class)
class NewsletterWebControllerTest {

  private static final String FIRST_NAME = "John";
  private static final String EMAIL = "john@example.com";

  @Mock
  MessageService messageService;

  @Mock
  NewsletterService newsletterService;

  @Captor
  ArgumentCaptor<NewsletterSubscriptionDTO> dtoCaptor;

  AppProperties appProperties;

  NewsletterWebController newsletterWebController;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    appProperties = new AppProperties();
    appProperties.getOpenGraph().setDefaultImg("/img/endurancetrio-open-graph.png");
    appProperties.getOpenGraph().setDefaultImgWidth(1200);
    appProperties.getOpenGraph().setDefaultImgHeight(628);
    appProperties.getSocial().setFacebookPageId("1692877750958091");
    appProperties.getSocial().setTwitterSite("@EnduranceTrio");

    newsletterWebController = new NewsletterWebController(appProperties, messageService,
        newsletterService
    );

    InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
    viewResolver.setPrefix("/WEB-INF/views/");
    viewResolver.setSuffix(".html");

    mockMvc = MockMvcBuilders.standaloneSetup(newsletterWebController)
        .setViewResolvers(viewResolver)
        .build();
  }

  @Test
  void successPageWithEnglishLocale() throws Exception {
    when(messageService.getMessage(eq("page.newsletter.newsletter-success.metadata.title"), any(),
        any()
    )).thenReturn("Newsletter - EnduranceTrio");
    when(messageService.getMessage(eq("page.newsletter.newsletter-success.metadata.description"),
        any(), any()
    )).thenReturn("Newsletter subscription successful");

    mockMvc.perform(get("/en/newsletter/success"))
        .andExpect(status().isOk())
        .andExpect(view().name("newsletter-success"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attributeExists("metadata"));
  }

  @Test
  void successPageWithPortugueseLocale() throws Exception {
    when(messageService.getMessage(eq("page.newsletter.newsletter-success.metadata.title"), any(),
        any()
    )).thenReturn("Newsletter - EnduranceTrio");
    when(messageService.getMessage(eq("page.newsletter.newsletter-success.metadata.description"),
        any(), any()
    )).thenReturn("Subscrição concluída com sucesso");

    mockMvc.perform(get("/pt/newsletter/success"))
        .andExpect(status().isOk())
        .andExpect(view().name("newsletter-success"))
        .andExpect(model().attribute("language", "pt"))
        .andExpect(model().attributeExists("metadata"));
  }

  @Test
  void errorPageWithEnglishLocale() throws Exception {
    when(messageService.getMessage(eq("page.newsletter.newsletter-error.metadata.title"), any(),
        any()
    )).thenReturn("Newsletter - EnduranceTrio");
    when(messageService.getMessage(eq("page.newsletter.newsletter-error.metadata.description"),
        any(), any()
    )).thenReturn("Newsletter subscription error");

    mockMvc.perform(get("/en/newsletter/error"))
        .andExpect(status().isOk())
        .andExpect(view().name("newsletter-error"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attributeExists("metadata"));
  }

  @Test
  void errorPageWithPortugueseLocale() throws Exception {
    when(messageService.getMessage(eq("page.newsletter.newsletter-error.metadata.title"), any(),
        any()
    )).thenReturn("Newsletter - EnduranceTrio");
    when(messageService.getMessage(eq("page.newsletter.newsletter-error.metadata.description"),
        any(), any()
    )).thenReturn("Erro na subscrição da newsletter");

    mockMvc.perform(get("/pt/newsletter/error"))
        .andExpect(status().isOk())
        .andExpect(view().name("newsletter-error"))
        .andExpect(model().attribute("language", "pt"))
        .andExpect(model().attributeExists("metadata"));
  }

  @Test
  void confirmedPageWithEnglishLocale() throws Exception {
    when(messageService.getMessage(eq("page.newsletter.newsletter-confirmed.metadata.title"),
        any(), any()
    )).thenReturn("Newsletter - EnduranceTrio");
    when(messageService.getMessage(
        eq("page.newsletter.newsletter-confirmed.metadata.description"), any(), any()
    )).thenReturn("Newsletter subscription confirmed");

    mockMvc.perform(get("/en/newsletter/confirmed"))
        .andExpect(status().isOk())
        .andExpect(view().name("newsletter-confirmed"))
        .andExpect(model().attribute("language", "en"))
        .andExpect(model().attributeExists("metadata"));
  }

  @Test
  void confirmedPageWithPortugueseLocale() throws Exception {
    when(messageService.getMessage(eq("page.newsletter.newsletter-confirmed.metadata.title"),
        any(), any()
    )).thenReturn("Newsletter - EnduranceTrio");
    when(messageService.getMessage(
        eq("page.newsletter.newsletter-confirmed.metadata.description"), any(), any()
    )).thenReturn("Subscrição da newsletter confirmada");

    mockMvc.perform(get("/pt/newsletter/confirmed"))
        .andExpect(status().isOk())
        .andExpect(view().name("newsletter-confirmed"))
        .andExpect(model().attribute("language", "pt"))
        .andExpect(model().attributeExists("metadata"));
  }

  @Test
  void subscribeWithValidData() throws Exception {
    mockMvc.perform(post("/en/newsletter/subscribe")
            .param("firstName", FIRST_NAME)
            .param("email", EMAIL))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/en/newsletter/success"));

    verify(newsletterService).subscribe(dtoCaptor.capture(), eq("en"));
    NewsletterSubscriptionDTO captured = dtoCaptor.getValue();
    assertEquals(FIRST_NAME, captured.firstName());
    assertEquals(EMAIL, captured.email());
  }

  @Test
  void subscribeWithValidDataInPortuguese() throws Exception {
    mockMvc.perform(post("/pt/newsletter/subscribe")
            .param("firstName", FIRST_NAME)
            .param("email", EMAIL))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/pt/newsletter/success"));

    verify(newsletterService).subscribe(dtoCaptor.capture(), eq("pt"));
  }

  @Test
  void subscribeWithInvalidShortName() throws Exception {
    mockMvc.perform(post("/en/newsletter/subscribe")
            .param("firstName", "A")
            .param("email", EMAIL))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/en/newsletter/error"));

    verifyNoInteractions(newsletterService);
  }

  @Test
  void subscribeWithInvalidEmailNoAtSign() throws Exception {
    mockMvc.perform(post("/en/newsletter/subscribe")
            .param("firstName", FIRST_NAME)
            .param("email", "invalid"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/en/newsletter/error"));

    verifyNoInteractions(newsletterService);
  }

  @Test
  void subscribeWithInvalidEmailStartsWithAt() throws Exception {
    mockMvc.perform(post("/en/newsletter/subscribe")
            .param("firstName", FIRST_NAME)
            .param("email", "@example.com"))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/en/newsletter/error"));

    verifyNoInteractions(newsletterService);
  }

  @Test
  void subscribeWhenServiceFails() throws Exception {
    doThrow(EnduranceTrioException.class).when(newsletterService).subscribe(any(), eq("en"));

    mockMvc.perform(post("/en/newsletter/subscribe")
            .param("firstName", FIRST_NAME)
            .param("email", EMAIL))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/en/newsletter/error"));

    verify(newsletterService).subscribe(any(), eq("en"));
  }

  @Test
  void subscribeTrimsWhitespace() throws Exception {
    mockMvc.perform(post("/en/newsletter/subscribe")
            .param("firstName", "  John  ")
            .param("email", "  john@example.com  "))
        .andExpect(status().is3xxRedirection())
        .andExpect(view().name("redirect:/en/newsletter/success"));

    verify(newsletterService).subscribe(dtoCaptor.capture(), eq("en"));
    NewsletterSubscriptionDTO captured = dtoCaptor.getValue();
    assertEquals("John", captured.firstName());
    assertEquals("john@example.com", captured.email());
  }
}
