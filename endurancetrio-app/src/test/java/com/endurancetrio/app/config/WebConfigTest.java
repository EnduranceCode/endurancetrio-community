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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import org.jspecify.annotations.NullMarked;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.config.annotation.RedirectViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

@NullMarked
class WebConfigTest {

  @Test
  void shouldCreateInstance() {
    WebConfig config = new WebConfig();
    assertNotNull(config);
  }

  @Test
  void addViewControllersShouldRegisterRedirects() {
    WebConfig config = new WebConfig();
    TestViewControllerRegistry registry = new TestViewControllerRegistry();

    config.addViewControllers(registry);

    assertEquals(4, registry.redirectCount);
  }

  private static class TestViewControllerRegistry extends ViewControllerRegistry {

    private int redirectCount = 0;

    private TestViewControllerRegistry() {
      super(mock(ApplicationContext.class));
    }

    @Override
    public RedirectViewControllerRegistration addRedirectViewController(
        String urlPath,
        String redirectUri
    ) {
      redirectCount++;
      return super.addRedirectViewController(urlPath, redirectUri);

    }
  }
}
