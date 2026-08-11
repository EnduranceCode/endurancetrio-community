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

package com.endurancetrio.app.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.endurancetrio.app.common.model.PageMetadata;
import com.endurancetrio.app.config.AppProperties;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;

class PageMetadataUtilsTest {

  @Test
  void createShouldDerivePortugueseAlternateForEnglishUrl() {
    AppProperties appProperties = createAppProperties();
    HttpServletRequest request = createRequest(
        "http://localhost:8080/en/insights/how-to-train", "/en/insights/how-to-train");

    PageMetadata result = PageMetadataUtils.create("insight-article", "Title", "Description",
        request, appProperties
    );

    assertNotNull(result);
    assertEquals("http://localhost:8080/en/insights/how-to-train", result.getHreflangUrlEn());
    assertEquals("http://localhost:8080/pt/insights/how-to-train", result.getHreflangUrlPt());
  }

  @Test
  void createShouldDeriveEnglishAlternateForPortugueseUrl() {
    AppProperties appProperties = createAppProperties();
    HttpServletRequest request = createRequest(
        "http://localhost:8080/pt/insights/como-treinar", "/pt/insights/como-treinar");

    PageMetadata result = PageMetadataUtils.create("insight-article", "Titulo", "Descricao",
        request, appProperties
    );

    assertNotNull(result);
    assertEquals("http://localhost:8080/en/insights/como-treinar", result.getHreflangUrlEn());
    assertEquals("http://localhost:8080/pt/insights/como-treinar", result.getHreflangUrlPt());
  }

  @Test
  void createShouldDeriveAlternatesForEnglishHomePage() {
    AppProperties appProperties = createAppProperties();
    HttpServletRequest request = createRequest("http://localhost:8080/en/", "/en/");

    PageMetadata result = PageMetadataUtils.create("home", "Title", "Description", request,
        appProperties
    );

    assertNotNull(result);
    assertEquals("http://localhost:8080/en/", result.getHreflangUrlEn());
    assertEquals("http://localhost:8080/pt/", result.getHreflangUrlPt());
  }

  @Test
  void createShouldDeriveAlternatesForPortugueseHomePage() {
    AppProperties appProperties = createAppProperties();
    HttpServletRequest request = createRequest("http://localhost:8080/pt/", "/pt/");

    PageMetadata result = PageMetadataUtils.create("home", "Titulo", "Descricao", request,
        appProperties
    );

    assertNotNull(result);
    assertEquals("http://localhost:8080/en/", result.getHreflangUrlEn());
    assertEquals("http://localhost:8080/pt/", result.getHreflangUrlPt());
  }

  @Test
  void createShouldAppendLanguageToRootUrl() {
    AppProperties appProperties = createAppProperties();
    HttpServletRequest request = createRequest("http://localhost:8080/", "/");

    PageMetadata result = PageMetadataUtils.create("home", "Title", "Description", request,
        appProperties
    );

    assertNotNull(result);
    assertEquals("http://localhost:8080/en", result.getHreflangUrlEn());
    assertEquals("http://localhost:8080/pt", result.getHreflangUrlPt());
  }

  @Test
  void createShouldLeaveHreflangNullForNonLanguageUrl() {
    AppProperties appProperties = createAppProperties();
    HttpServletRequest request = createRequest("http://localhost:8080/error", "/error");

    PageMetadata result = PageMetadataUtils.create("error", "Title", "Description", request,
        appProperties
    );

    assertNotNull(result);
    assertNull(result.getHreflangUrlEn());
    assertNull(result.getHreflangUrlPt());
  }

  @Test
  void constructorShouldThrow() {
    assertThrows(InvocationTargetException.class, () -> {
      var constructor = PageMetadataUtils.class.getDeclaredConstructor();
      constructor.setAccessible(true);
      constructor.newInstance();
    });
  }

  private static AppProperties createAppProperties() {
    AppProperties appProperties = new AppProperties();
    appProperties.getOpenGraph().setDefaultImg("/img/endurancetrio-open-graph.png");
    appProperties.getOpenGraph().setDefaultImgWidth(1200);
    appProperties.getOpenGraph().setDefaultImgHeight(628);
    appProperties.getSocial().setFacebookPageId("1692877750958091");
    appProperties.getSocial().setTwitterSite("@EnduranceTrio");
    appProperties.getGoogle().setAdsenseId("ca-pub-test");
    appProperties.getKoFi().setUserId("test-kofi");
    appProperties.setCopyrightYear("2026");
    appProperties.setVersion("0.4.0");
    return appProperties;
  }

  private static HttpServletRequest createRequest(String url, String uri) {
    HttpServletRequest request = mock(HttpServletRequest.class);
    when(request.getRequestURL()).thenReturn(new StringBuffer(url));
    when(request.getRequestURI()).thenReturn(uri);
    return request;
  }
}
