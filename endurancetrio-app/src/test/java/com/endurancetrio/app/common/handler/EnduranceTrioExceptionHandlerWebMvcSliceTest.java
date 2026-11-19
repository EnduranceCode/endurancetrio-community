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

package com.endurancetrio.app.common.handler;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import com.endurancetrio.app.common.annotation.EnduranceTrioWebController;
import com.endurancetrio.app.common.security.entrypoint.EnduranceTrioAuthEntryPoint;
import com.endurancetrio.app.common.security.provider.EnduranceTrioAuthProvider;
import com.endurancetrio.app.common.service.MessageService;
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.app.config.AppSecurityConfig;
import com.endurancetrio.app.config.WebConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@WebMvcTest(useDefaultFilters = false)
@ActiveProfiles("test")
class EnduranceTrioExceptionHandlerWebMvcSliceTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void shouldRenderEnglishBadRequestPageForTypeMismatch() throws Exception {
    mockMvc.perform(get("/en/test/bad-year/abc"))
        .andExpect(status().isBadRequest())
        .andExpect(view().name("error/400"))
        .andExpect(content().string(containsString("Invalid entry: Bad Request")))
        .andExpect(content().string(containsString("The request parameters were invalid")));
  }

  @Test
  void shouldRenderPortugueseBadRequestPageForTypeMismatch() throws Exception {
    mockMvc.perform(get("/pt/test/bad-year/abc"))
        .andExpect(status().isBadRequest())
        .andExpect(view().name("error/400"))
        .andExpect(content().string(containsString("Inscrição inválida: Pedido Inválido")))
        .andExpect(content().string(containsString("Os parâmetros do pedido eram inválidos")));
  }

  @EnduranceTrioWebController
  static class BadRequestTestController {

    @SuppressWarnings("unused")
    @GetMapping("/{language:en|pt}/test/bad-year/{year}")
    String badYear(@PathVariable int year) {
      return "never";
    }
  }

  @SpringBootConfiguration
  @Import(
      {
          AppSecurityConfig.class, WebConfig.class, MessageService.class, EnduranceTrioExceptionHandlerWeb.class,
          BadRequestTestController.class
      }
  )
  static class TestApplication {

    @Bean
    EnduranceTrioAuthProvider enduranceTrioAuthProvider() {
      return mock(EnduranceTrioAuthProvider.class);
    }

    @Bean
    EnduranceTrioAuthEntryPoint enduranceTrioAuthEntryPoint() {
      return mock(EnduranceTrioAuthEntryPoint.class);
    }

    @Bean
    AppProperties appProperties() {
      AppProperties properties = new AppProperties();
      properties.setCopyrightYear("2026");
      properties.setSiteUrl("https://endurancetrio.com");
      properties.setVersion("0.4.0");
      properties.getOpenGraph().setDefaultImg("/img/endurancetrio-open-graph.png");
      properties.getOpenGraph().setDefaultImgWidth(1200);
      properties.getOpenGraph().setDefaultImgHeight(628);
      return properties;
    }
  }
}
