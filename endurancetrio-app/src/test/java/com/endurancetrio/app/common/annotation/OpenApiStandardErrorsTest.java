/*
 * Copyright (c) 2025-2025 Ricardo do Canto
 *
 * This file is part of the EnduranceTrio Tracker project.
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
 * EVEN IF HAVE BEEN INFORMED OF THEIR POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.app.common.annotation;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Test;

class OpenApiStandardErrorsTest {

  @Test
  void shouldBeTargetedAtMethodAndType() {
    Target target = OpenApiStandardErrors.class.getAnnotation(Target.class);

    assertNotNull(target);
    assertTrue(target.value().length >= 2);
  }

  @Test
  void shouldHaveRuntimeRetention() {
    Retention retention = OpenApiStandardErrors.class.getAnnotation(Retention.class);

    assertNotNull(retention);
    assertSame(RetentionPolicy.RUNTIME, retention.value());
  }

  @Test
  void shouldHaveApiResponseAnnotations() {
    ApiResponse[] responses = OpenApiStandardErrors.class.getAnnotationsByType(ApiResponse.class);

    assertNotNull(responses);
    assertTrue(responses.length >= 4);
  }
}
