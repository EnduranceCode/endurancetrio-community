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

import com.endurancetrio.app.common.utils.WebUtils;
import jakarta.servlet.Filter;
import java.util.Locale;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web configuration class that customizes Spring MVC configuration. This class implements
 * {@link WebMvcConfigurer} to customize various aspects of Spring MVC, including view controllers,
 * resource handlers, and interceptors.
 *
 * @see WebMvcConfigurer
 * @see ViewControllerRegistry
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

  @Bean
  public LocaleResolver localeResolver() {
    return new PathVariableLocaleResolver();
  }

  @Bean
  public FilterRegistrationBean<Filter> localeRedirectFilter() {
    FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new LocaleRedirectFilter());
    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 50);
    return registrationBean;
  }

  @Bean
  public FilterRegistrationBean<Filter> contentLanguageFilter() {
    FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new ContentLanguageFilter());
    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 60);
    return registrationBean;
  }

  /**
   * Registers the filter that caches HTML page output until rendering completes, so a partially
   * rendered page can be discarded when an error occurs. Non-page requests pass through untouched.
   */
  @Bean
  public FilterRegistrationBean<Filter> htmlResponseCachingFilter() {
    FilterRegistrationBean<Filter> registrationBean = new FilterRegistrationBean<>();
    registrationBean.setFilter(new HtmlResponseCachingFilter());
    registrationBean.addUrlPatterns("/*");
    registrationBean.setOrder(Ordered.HIGHEST_PRECEDENCE + 70);
    return registrationBean;
  }

  /**
   * Registers the converter used to resolve a language path variable to its {@link Locale}.
   *
   * @param registry the formatter registry to which the converter is added
   * @see WebUtils#fromLanguage
   */
  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addConverter(String.class, Locale.class, WebUtils::fromLanguage);
  }

  /**
   * Registers view controllers to handle direct URL-to-view mapping without requiring explicit
   * controller methods.
   *
   * @param registry the ViewControllerRegistry to which view controllers will be added, allowing
   *                 for URL path to view name mappings and redirect configurations
   * @see ViewControllerRegistry
   */
  @Override
  public void addViewControllers(ViewControllerRegistry registry) {
    registry.addRedirectViewController("/en", "/en/");
    registry.addRedirectViewController("/pt", "/pt/");
    registry.addRedirectViewController("/swagger-ui", "/swagger-ui/index.html");
    registry.addRedirectViewController("/swagger-ui/", "/swagger-ui/index.html");
  }
}
