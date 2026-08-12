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

import com.endurancetrio.app.common.annotation.EnduranceTrioWebController;
import com.endurancetrio.app.config.AppProperties;
import com.endurancetrio.business.common.dto.SitemapEntry;
import com.endurancetrio.business.common.service.SitemapService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * The {@link SitemapController} serves the {@code /sitemap.xml} endpoint with every public URL in both language
 * variants, annotated with hreflang alternates.
 * <p>
 * The XML is rendered from the {@code templates/sitemap.xml} template by a dedicated {@link SpringTemplateEngine}
 * configured with an {@code .xml} suffix and XML template mode. The engine is created in the constructor rather
 * than as a Spring bean so that the autoconfigured Thymeleaf engine (which uses the {@code .html} suffix
 * for HTML views) is left untouched.
 */
@EnduranceTrioWebController
public class SitemapController {

  private static final String TEMPLATE_SITEMAP = "sitemap";
  private static final String ATTRIBUTE_ENTRIES = "entries";

  private final AppProperties appProperties;
  private final SitemapService sitemapService;
  private final SpringTemplateEngine sitemapTemplateEngine;

  @Autowired
  public SitemapController(AppProperties appProperties, SitemapService sitemapService) {
    this.appProperties = appProperties;
    this.sitemapService = sitemapService;
    this.sitemapTemplateEngine = buildSitemapTemplateEngine();
  }

  /**
   * Returns the XML sitemap with all public URLs in both language variants.
   *
   * @return the rendered XML sitemap
   */
  @GetMapping(value = "/sitemap.xml", produces = MediaType.APPLICATION_XML_VALUE)
  @ResponseBody
  public String generateSitemap() {
    String baseUrl = normalizeBaseUrl(appProperties.getSiteUrl());
    List<SitemapEntry> entries = sitemapService.getSitemapEntries(baseUrl);

    Context context = new Context();
    context.setVariable(ATTRIBUTE_ENTRIES, entries);

    return sitemapTemplateEngine.process(TEMPLATE_SITEMAP, context);
  }

  /**
   * Removes any trailing slash from the configured site URL so that path concatenation produces
   * consistent absolute URLs.
   *
   * @param siteUrl the configured site URL
   * @return the site URL without a trailing slash
   */
  static String normalizeBaseUrl(String siteUrl) {
    return siteUrl == null ? "" : siteUrl.replaceFirst("/$", "");
  }

  /**
   * Builds the dedicated {@link SpringTemplateEngine} used to render the XML sitemap template.
   *
   * @return the sitemap template engine
   */
  private static SpringTemplateEngine buildSitemapTemplateEngine() {
    ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
    resolver.setPrefix("templates/");
    resolver.setSuffix(".xml");
    resolver.setTemplateMode(TemplateMode.XML);

    SpringTemplateEngine engine = new SpringTemplateEngine();
    engine.setTemplateResolver(resolver);
    return engine;
  }
}
