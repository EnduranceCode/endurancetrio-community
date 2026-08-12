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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.business.common.dto.SitemapEntry;
import com.endurancetrio.business.common.service.SitemapService;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilderFactory;
import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@ExtendWith(MockitoExtension.class)
class SitemapControllerTest {

  private static final String SITEMAPS_NAMESPACE = "http://www.sitemaps.org/schemas/sitemap/0.9";
  private static final String XHTML_NAMESPACE = "http://www.w3.org/1999/xhtml";

  @Mock
  SitemapService sitemapService;

  AppProperties appProperties;

  SitemapController sitemapController;

  MockMvc mockMvc;

  @BeforeEach
  void setUp() {
    appProperties = new AppProperties();
    appProperties.setSiteUrl("https://endurancetrio.com");

    sitemapController = new SitemapController(appProperties, sitemapService);
    mockMvc = MockMvcBuilders.standaloneSetup(sitemapController).build();
  }

  @Test
  void sitemapShouldReturnXmlWithAllEntries() throws Exception {
    when(sitemapService.getSitemapEntries("https://endurancetrio.com")).thenReturn(buildSitemapEntries());

    MvcResult result = mockMvc.perform(get("/sitemap.xml"))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_XML))
        .andReturn();

    String xml = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
    Document document = parse(xml);

    NodeList urlNodes = document.getElementsByTagNameNS(SITEMAPS_NAMESPACE, "url");
    assertEquals(9, urlNodes.getLength());

    List<String> locs = extractText(urlNodes, "loc");
    assertEquals(9, locs.size());
    assertTrue(locs.contains("https://endurancetrio.com/en/"));
    assertTrue(locs.contains("https://endurancetrio.com/en/insights"));
    assertTrue(locs.contains("https://endurancetrio.com/en/events"));
    assertTrue(locs.contains("https://endurancetrio.com/en/about"));
    assertTrue(locs.contains("https://endurancetrio.com/en/mission"));
    assertTrue(locs.contains("https://endurancetrio.com/en/athletes"));
    assertTrue(locs.contains("https://endurancetrio.com/en/privacy-policy"));
    assertTrue(locs.contains("https://endurancetrio.com/en/insights/how-to-train"));
    assertTrue(locs.contains("https://endurancetrio.com/en/events/2026/10/overview"));

    assertTrue(xml.contains("hreflang=\"en\" href=\"https://endurancetrio.com/en/insights/how-to-train\""));
    assertTrue(xml.contains("hreflang=\"pt\" href=\"https://endurancetrio.com/pt/insights/how-to-train\""));
    assertTrue(xml.contains("hreflang=\"x-default\" href=\"https://endurancetrio.com/en/insights/how-to-train\""));
    assertTrue(xml.contains("hreflang=\"pt\" href=\"https://endurancetrio.com/pt/\""));
  }

  @Test
  void sitemapShouldUsePublishedDateAsLastmodAndPriorities() throws Exception {
    when(sitemapService.getSitemapEntries("https://endurancetrio.com")).thenReturn(buildSitemapEntries());

    MvcResult result = mockMvc.perform(get("/sitemap.xml")).andExpect(status().isOk()).andReturn();

    String xml = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
    Document document = parse(xml);

    NodeList urlNodes = document.getElementsByTagNameNS(SITEMAPS_NAMESPACE, "url");
    assertEquals(9, urlNodes.getLength());

    List<String> lastmods = extractText(urlNodes, "lastmod");
    assertTrue(lastmods.contains("2026-05-01"));
    assertTrue(lastmods.contains("2026-08-15"));
    assertTrue(lastmods.contains(LocalDate.now(ZoneOffset.UTC).toString()));

    List<String> priorities = extractText(urlNodes, "priority");
    assertTrue(priorities.contains("1.0"));
    assertTrue(priorities.contains("0.8"));
    assertTrue(priorities.contains("0.7"));
    assertTrue(priorities.contains("0.6"));
    assertTrue(priorities.contains("0.5"));
    assertTrue(priorities.contains("0.3"));
  }

  @Test
  void sitemapShouldRenderEmptyUrlsetWhenNoEntries() throws Exception {
    when(sitemapService.getSitemapEntries("https://endurancetrio.com")).thenReturn(List.of());

    MvcResult result = mockMvc.perform(get("/sitemap.xml")).andExpect(status().isOk()).andReturn();

    String xml = result.getResponse().getContentAsString(StandardCharsets.UTF_8);
    Document document = parse(xml);

    NodeList urlNodes = document.getElementsByTagNameNS(SITEMAPS_NAMESPACE, "url");
    assertEquals(0, urlNodes.getLength());

    NodeList linkNodes = document.getElementsByTagNameNS(XHTML_NAMESPACE, "link");
    assertEquals(0, linkNodes.getLength());
  }

  private static @NonNull List<SitemapEntry> buildSitemapEntries() {
    String today = LocalDate.now(ZoneOffset.UTC).toString();

    List<SitemapEntry> entries = new ArrayList<>();
    entries.add(new SitemapEntry("https://endurancetrio.com/en/", "https://endurancetrio.com/pt/", today, 1.0));
    entries.add(
        new SitemapEntry("https://endurancetrio.com/en/insights", "https://endurancetrio.com/pt/insights", today, 0.7));
    entries.add(
        new SitemapEntry("https://endurancetrio.com/en/events", "https://endurancetrio.com/pt/events", today, 0.7));
    entries.add(
        new SitemapEntry("https://endurancetrio.com/en/about", "https://endurancetrio.com/pt/about", today, 0.6));
    entries.add(
        new SitemapEntry("https://endurancetrio.com/en/mission", "https://endurancetrio.com/pt/mission", today, 0.6));
    entries.add(
        new SitemapEntry("https://endurancetrio.com/en/athletes", "https://endurancetrio.com/pt/athletes", today, 0.5));
    entries.add(
        new SitemapEntry("https://endurancetrio.com/en/privacy-policy", "https://endurancetrio.com/pt/privacy-policy",
            today, 0.3
        ));
    entries.add(new SitemapEntry("https://endurancetrio.com/en/events/2026/10/overview",
        "https://endurancetrio.com/pt/events/2026/10/overview", "2026-08-15", 0.8
    ));
    entries.add(new SitemapEntry("https://endurancetrio.com/en/insights/how-to-train",
        "https://endurancetrio.com/pt/insights/how-to-train", "2026-05-01", 0.7
    ));
    return entries;
  }

  private static @NonNull List<String> extractText(NodeList urlNodes, String elementName) {
    ArrayList<String> values = new ArrayList<>();
    for (int i = 0; i < urlNodes.getLength(); i++) {
      Node urlNode = urlNodes.item(i);
      if (urlNode instanceof Element urlElement) {
        NodeList children = urlElement.getChildNodes();
        for (int j = 0; j < children.getLength(); j++) {
          Node child = children.item(j);
          if (child.getNodeType() == Node.ELEMENT_NODE && child.getLocalName().equals(elementName)) {
            values.add(child.getTextContent());
          }
        }
      }
    }
    return values;
  }

  private static @NonNull Document parse(String xml) throws Exception {
    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    factory.setNamespaceAware(true);
    return factory.newDocumentBuilder().parse(new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8)));
  }
}
