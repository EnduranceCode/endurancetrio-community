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
 * EVEN IF WE HAVE BEEN INFORMED OF THEIR POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.app;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

class TrackerApplicationTest {

  @Test
  void shouldBeAnnotatedWithSpringBootApplication() {
    SpringBootApplication annotation = TrackerApplication.class.getAnnotation(
        SpringBootApplication.class);

    assertNotNull(annotation);
  }

  @Test
  void shouldBeAnnotatedWithEnableJpaRepositories() {
    EnableJpaRepositories annotation = TrackerApplication.class.getAnnotation(
        EnableJpaRepositories.class);

    assertNotNull(annotation);
  }

  @Test
  void shouldBeAnnotatedWithEntityScan() {
    EntityScan annotation = TrackerApplication.class.getAnnotation(EntityScan.class);

    assertNotNull(annotation);
  }

  @Test
  void shouldCreateInstance() {
    assertDoesNotThrow(TrackerApplication::new);
  }
}
