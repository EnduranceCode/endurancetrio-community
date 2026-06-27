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
 * EVEN IF WE HAVE BEEN INFORMED OF THE POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.data.competitor.fixtures;

import com.endurancetrio.data.competitor.model.entity.AgeGroup;

/**
 * Fixture class providing pre-configured {@link AgeGroup} entity instances for unit tests.
 */
public class AgeGroupFixture {

  public static final Long STANDARD_ID = 1L;
  public static final String STANDARD_CODE = "BEN";
  public static final String STANDARD_DENOMINATION_EN = "Benjamin";
  public static final String STANDARD_DENOMINATION_PT = "Benjamin";

  private AgeGroupFixture() {
  }

  public static AgeGroup standard() {
    AgeGroup entity = new AgeGroup();
    entity.setId(STANDARD_ID);
    entity.setCode(STANDARD_CODE);
    entity.setDenominationEN(STANDARD_DENOMINATION_EN);
    entity.setDenominationPT(STANDARD_DENOMINATION_PT);
    return entity;
  }
}
