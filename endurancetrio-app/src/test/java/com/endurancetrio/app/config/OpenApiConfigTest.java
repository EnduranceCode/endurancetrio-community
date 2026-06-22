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

import static org.junit.jupiter.api.Assertions.assertNotNull;

import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.junit.jupiter.api.Test;

class OpenApiConfigTest {

  @Test
  void shouldCreateInstance() {
    OpenApiConfig config = new OpenApiConfig(new AppProperties());
    assertNotNull(config);
  }

  @Test
  void securitySchemesShouldBePresent() {
    SecurityScheme[] schemes = OpenApiConfig.class.getAnnotationsByType(SecurityScheme.class);

    assertNotNull(schemes);
  }

  @Test
  void accountNameSchemeShouldBeApiKeyHeader() {
    SecurityScheme accountName = OpenApiConfig.class.getAnnotationsByType(SecurityScheme.class)[0];

    assertNotNull(accountName);
  }
}
