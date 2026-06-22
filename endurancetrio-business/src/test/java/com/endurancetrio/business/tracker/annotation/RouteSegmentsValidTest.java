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

package com.endurancetrio.business.tracker.annotation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link RouteSegmentsValid} annotation.
 * <p>
 * This test may seem redundant since it only verifies annotation properties, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class RouteSegmentsValidTest {

  @Test
  void shouldBeTargetedAtType() {
    Target target = RouteSegmentsValid.class.getAnnotation(Target.class);

    assertNotNull(target);
    assertEquals(1, target.value().length);
    assertEquals(ElementType.TYPE, target.value()[0]);
  }

  @Test
  void shouldHaveRuntimeRetention() {
    Retention retention = RouteSegmentsValid.class.getAnnotation(Retention.class);

    assertNotNull(retention);
    assertEquals(RetentionPolicy.RUNTIME, retention.value());
  }

  @Test
  void shouldBeConstraint() {
    Constraint constraint = RouteSegmentsValid.class.getAnnotation(Constraint.class);

    assertNotNull(constraint);
    assertNotNull(constraint.validatedBy());
  }

  @Test
  void shouldHaveDefaultMessage() {
    RouteSegmentsValid annotation = AnnotatedClass.class.getAnnotation(RouteSegmentsValid.class);

    assertNotNull(annotation);
    assertTrue(annotation.message().contains("continuous sequence"));
  }

  @RouteSegmentsValid
  private static class AnnotatedClass {

  }
}
