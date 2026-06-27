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

import com.endurancetrio.data.competitor.model.entity.Team;

/**
 * Fixture class providing pre-configured {@link Team} entity instances for unit tests.
 */
public class TeamFixture {

  public static final Long STANDARD_ID = 1L;
  public static final String STANDARD_FULL_NAME = "Sociedade de Instrução e Recreio de Janes e Malveira";
  public static final String STANDARD_SHORT_NAME = "Victória de Janes";
  public static final String STANDARD_CITY = "Alcabidiche";
  public static final String STANDARD_COUNTY = "Cascais";
  public static final String STANDARD_DISTRICT = "Lisboa";

  private TeamFixture() {
  }

  public static Team standard() {
    Team entity = new Team();
    entity.setId(STANDARD_ID);
    entity.setFullName(STANDARD_FULL_NAME);
    entity.setShortName(STANDARD_SHORT_NAME);
    entity.setCity(STANDARD_CITY);
    entity.setCounty(STANDARD_COUNTY);
    entity.setDistrict(STANDARD_DISTRICT);
    return entity;
  }
}
