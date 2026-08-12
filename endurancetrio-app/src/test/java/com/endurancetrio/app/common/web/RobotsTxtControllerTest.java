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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.endurancetrio.app.config.AppProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

class RobotsTxtControllerTest {

  AppProperties appProperties;

  RobotsTxtController robotsTxtController;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    appProperties = new AppProperties();
    appProperties.setSiteUrl("https://endurancetrio.com");

    robotsTxtController = new RobotsTxtController(appProperties);
    mockMvc = MockMvcBuilders.standaloneSetup(robotsTxtController).build();
  }

  @Test
  void robotsTxtShouldReturnPlainTextWithDefaultSiteUrl() throws Exception {
    MvcResult result = mockMvc.perform(get("/robots.txt"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
        .andReturn();

    String robotsTxt = result.getResponse().getContentAsString();
    assertTrue(robotsTxt.contains("Scraping of this website is not allowed"));
    assertTrue(robotsTxt.contains("User-agent: *"));
    assertTrue(robotsTxt.contains("Disallow: /api/"));
    assertTrue(robotsTxt.contains("Disallow: /actuator/"));
    assertTrue(robotsTxt.contains("Disallow: /swagger-ui/"));
    assertTrue(robotsTxt.contains("Disallow: /v3/api-docs/"));
    assertTrue(robotsTxt.contains("Disallow: /swagger-resources/"));
    assertTrue(robotsTxt.contains("Disallow: /error"));
    assertTrue(robotsTxt.contains("Sitemap: https://endurancetrio.com/sitemap.xml"));
  }

  @Test
  void robotsTxtShouldUseConfiguredSiteUrl() throws Exception {
    appProperties.setSiteUrl("https://staging.example.com/");

    MvcResult result = mockMvc.perform(get("/robots.txt")).andExpect(status().isOk()).andReturn();

    String robotsTxt = result.getResponse().getContentAsString();
    assertTrue(robotsTxt.contains("Sitemap: https://staging.example.com/sitemap.xml"));
    assertFalse(robotsTxt.contains("https://endurancetrio.com"));
  }
}
