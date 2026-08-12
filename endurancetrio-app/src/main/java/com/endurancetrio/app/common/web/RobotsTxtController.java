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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

/**
 * The {@link RobotsTxtController} serves the {@code /robots.txt} endpoint.
 * <p>
 * The content is rendered from the {@code templates/robots.txt} template so that the {@code Sitemap} directive uses
 * the configured {@code app.site-url} at runtime. The template is processed by a dedicated {@link SpringTemplateEngine}
 * configured with an {@code .txt} suffix and TEXT template mode. The engine is created in the constructor rather
 * than as a Spring bean so that the autoconfigured Thymeleaf engine (which uses the {@code .html} suffix for HTML
 * views) is left untouched.
 */
@EnduranceTrioWebController
public class RobotsTxtController {

  private static final String TEMPLATE_ROBOTS = "robots";
  private static final String ATTRIBUTE_SITE_URL = "siteUrl";

  private final AppProperties appProperties;
  private final SpringTemplateEngine robotsTemplateEngine;

  @Autowired
  public RobotsTxtController(AppProperties appProperties) {
    this.appProperties = appProperties;
    this.robotsTemplateEngine = buildRobotsTemplateEngine();
  }

  /**
   * Returns the robots.txt content with the {@code Sitemap} directive pointing to the configured site URL.
   *
   * @return the rendered robots.txt content
   */
  @GetMapping(value = "/robots.txt", produces = MediaType.TEXT_PLAIN_VALUE)
  @ResponseBody
  public String generateRobotsTxt() {
    Context context = new Context();
    context.setVariable(ATTRIBUTE_SITE_URL, SitemapController.normalizeBaseUrl(appProperties.getSiteUrl()));
    return robotsTemplateEngine.process(TEMPLATE_ROBOTS, context);
  }

  /**
   * Builds the dedicated {@link SpringTemplateEngine} used to render the robots.txt template.
   *
   * @return the robots template engine
   */
  private static SpringTemplateEngine buildRobotsTemplateEngine() {
    ClassLoaderTemplateResolver resolver = new ClassLoaderTemplateResolver();
    resolver.setPrefix("templates/");
    resolver.setSuffix(".txt");
    resolver.setTemplateMode(TemplateMode.TEXT);

    SpringTemplateEngine engine = new SpringTemplateEngine();
    engine.setTemplateResolver(resolver);
    return engine;
  }
}
