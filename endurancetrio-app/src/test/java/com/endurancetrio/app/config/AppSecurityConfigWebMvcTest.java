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

package com.endurancetrio.app.config;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.head;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.endurancetrio.app.common.security.entrypoint.EnduranceTrioAuthEntryPoint;
import com.endurancetrio.app.common.security.provider.EnduranceTrioAuthProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@WebMvcTest(useDefaultFilters = false)
@ActiveProfiles("test")
class AppSecurityConfigWebMvcTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void headOnPublicPageShouldBeAllowed() throws Exception {
    mockMvc.perform(head("/en/")).andExpect(status().isOk());
  }

  @Test
  void getOnPublicPageShouldBeAllowed() throws Exception {
    mockMvc.perform(get("/en/")).andExpect(status().isOk());
  }

  @Test
  void headOnNonPublicPageShouldBeDenied() throws Exception {
    mockMvc.perform(head("/admin/dashboard")).andExpect(status().isForbidden());
  }

  @RestController
  static class PublicPageTestController {

    @GetMapping("/{language:en|pt}/")
    String home() {
      return "home";
    }
  }

  @SpringBootConfiguration
  @Import({AppSecurityConfig.class, PublicPageTestController.class})
  static class TestApplication {

    @Bean
    EnduranceTrioAuthProvider enduranceTrioAuthProvider() {
      return mock(EnduranceTrioAuthProvider.class);
    }

    @Bean
    EnduranceTrioAuthEntryPoint enduranceTrioAuthEntryPoint() {
      return mock(EnduranceTrioAuthEntryPoint.class);
    }
  }
}
