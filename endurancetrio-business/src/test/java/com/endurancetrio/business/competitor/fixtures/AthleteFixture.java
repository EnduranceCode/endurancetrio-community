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

package com.endurancetrio.business.competitor.fixtures;

import com.endurancetrio.data.competitor.model.entity.Athlete;
import com.endurancetrio.data.competitor.model.enumerator.AthleteGender;
import com.endurancetrio.data.competitor.model.enumerator.Country;

/**
 * Fixture class providing pre-configured {@link Athlete} entity instances for unit tests.
 * <p>
 * The constants defined here are the source of truth for standard athlete test data. They are
 * referenced by {@link AthleteDTOFixtures} and by assertion code in tests, ensuring entity data,
 * DTO data, and test expectations always align.
 * <p>
 * This class is duplicated in {@code endurancetrio-data} under {@code competitor.fixtures} because
 * the modules do not share a test-jar dependency. Each module keeps its own copy of the fixtures it
 * needs, keeping the build simple and avoiding cross-module test-jar complications. If you add or
 * modify a factory method here, apply the same change to the business module's copy.
 */
public class AthleteFixture {

  public static final Long STANDARD_ID = 1L;
  public static final String STANDARD_LONG_NAME = "Paulo José Paula Carvalho";
  public static final String STANDARD_KNOWN_NAME = "Paulo Paula Carvalho";
  public static final AthleteGender STANDARD_GENDER = AthleteGender.MALE;
  public static final Country STANDARD_COUNTRY = Country.POR;
  public static final Integer STANDARD_YEAR_OF_BIRTH = 1961;

  private AthleteFixture() {
  }

  public static Athlete standard() {
    Athlete entity = new Athlete();
    entity.setId(STANDARD_ID);
    entity.setLongName(STANDARD_LONG_NAME);
    entity.setBirthName(null);
    entity.setKnownName(STANDARD_KNOWN_NAME);
    entity.setGender(STANDARD_GENDER);
    entity.setCountry(STANDARD_COUNTRY);
    entity.setYearOfBirth(STANDARD_YEAR_OF_BIRTH);
    return entity;
  }

  public static Athlete athleteCavaleiro() {
    Athlete entity = new Athlete();
    entity.setId(4L);
    entity.setLongName("Paulo Cavaleiro");
    entity.setBirthName(null);
    entity.setKnownName("Paulo Cavaleiro");
    entity.setGender(AthleteGender.MALE);
    entity.setCountry(Country.POR);
    entity.setYearOfBirth(null);
    return entity;
  }

  public static Athlete athleteBello() {
    Athlete entity = new Athlete();
    entity.setId(22L);
    entity.setLongName("Nuno Bello Conceição");
    entity.setBirthName(null);
    entity.setKnownName("Nuno Bello Conceição");
    entity.setGender(AthleteGender.MALE);
    entity.setCountry(Country.POR);
    entity.setYearOfBirth(null);
    return entity;
  }
}
