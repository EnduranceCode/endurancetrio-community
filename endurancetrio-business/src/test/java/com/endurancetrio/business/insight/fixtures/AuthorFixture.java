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

package com.endurancetrio.business.insight.fixtures;

import com.endurancetrio.data.insight.model.entity.Author;

/**
 * Fixture class providing pre-configured {@link Author} entity instances for unit tests in the
 * {@code endurancetrio-business} module.
 * <p>
 * This class is duplicated in {@code endurancetrio-data} under {@code insight.fixtures} because the
 * modules do not share a test-jar dependency. Each module keeps its own copy of the fixtures it
 * needs, keeping the build simple and avoiding cross-module test-jar complications. If you add or
 * modify a factory method here, apply the same change to the data module's copy.
 */
public class AuthorFixture {

  public static final Long STANDARD_ID = 1L;
  public static final String STANDARD_KNOWN_NAME = "John Doe";
  public static final String STANDARD_BIO = "An experienced endurance sports journalist.";

  private AuthorFixture() {
  }

  /**
   * Creates a standard {@link Author} entity with default test values.
   *
   * @return a standard Author entity instance
   */
  public static Author standard() {
    Author entity = new Author();
    entity.setId(STANDARD_ID);
    entity.setKnownName(STANDARD_KNOWN_NAME);
    entity.setBio(STANDARD_BIO);
    return entity;
  }
}
