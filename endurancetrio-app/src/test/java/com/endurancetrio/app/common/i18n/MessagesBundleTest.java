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

package com.endurancetrio.app.common.i18n;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Guards the message bundles against drifting apart.
 *
 * <p>The application ships the default (English) bundle and the Portuguese one, and there is no
 * fallback path that surfaces a missing translation, so a key added to one bundle and forgotten
 * in the other silently renders the raw key. These tests fail fast instead.
 */
class MessagesBundleTest {

  private static final String DEFAULT_BUNDLE = "messages.properties";
  private static final String PORTUGUESE_BUNDLE = "messages_pt.properties";

  private static Properties defaultBundle;
  private static Properties portugueseBundle;

  @BeforeAll
  static void loadBundles() throws IOException {
    defaultBundle = load(DEFAULT_BUNDLE);
    portugueseBundle = load(PORTUGUESE_BUNDLE);
  }

  @Test
  void bundlesShouldDeclareTheSameKeys() {
    Set<String> missingInPortuguese = new TreeSet<>(defaultBundle.stringPropertyNames());
    missingInPortuguese.removeAll(portugueseBundle.stringPropertyNames());

    Set<String> missingInDefault = new TreeSet<>(portugueseBundle.stringPropertyNames());
    missingInDefault.removeAll(defaultBundle.stringPropertyNames());

    assertTrue(missingInPortuguese.isEmpty(),
        () -> "Keys missing from " + PORTUGUESE_BUNDLE + ": " + missingInPortuguese
    );
    assertTrue(missingInDefault.isEmpty(), () -> "Keys missing from " + DEFAULT_BUNDLE + ": " + missingInDefault);
  }

  @Test
  void bundlesShouldNotDeclareBlankValues() {
    assertNoBlankValues(DEFAULT_BUNDLE, defaultBundle);
    assertNoBlankValues(PORTUGUESE_BUNDLE, portugueseBundle);
  }

  private static void assertNoBlankValues(String bundle, Properties properties) {
    Set<String> blankKeys = new TreeSet<>();
    for (String key : properties.stringPropertyNames()) {
      if (properties.getProperty(key).isBlank()) {
        blankKeys.add(key);
      }
    }

    assertTrue(blankKeys.isEmpty(), () -> "Blank values in " + bundle + ": " + blankKeys);
  }

  private static Properties load(String resource) throws IOException {
    InputStream stream = MessagesBundleTest.class.getClassLoader().getResourceAsStream(resource);
    assertNotNull(stream, () -> resource + " is not on the test classpath");

    Properties properties = new Properties();
    try (Reader reader = new InputStreamReader(stream, StandardCharsets.UTF_8)) {
      properties.load(reader);
    }
    return properties;
  }
}
