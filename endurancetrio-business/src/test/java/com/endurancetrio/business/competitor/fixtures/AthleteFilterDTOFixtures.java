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

package com.endurancetrio.business.competitor.fixtures;

import com.endurancetrio.business.competitor.dto.AthleteFilterDTO;
import com.endurancetrio.data.competitor.model.enumerator.AthleteGender;
import java.util.Locale;

/**
 * Fixture class providing pre-configured {@link AthleteFilterDTO} instances for unit tests.
 * <p>
 * This class is duplicated in {@code endurancetrio-app} under {@code competitor.fixtures} because
 * the modules do not share a test-jar dependency. Each module keeps its own copy of the fixtures it
 * needs, keeping the build simple and avoiding cross-module test-jar complications. If you add or
 * modify a factory method here, apply the same change to the app module's copy.
 */
public class AthleteFilterDTOFixtures {

  public static final String LETTER_RANGE = "A_F";
  public static final AthleteGender GENDER = AthleteGender.MALE;
  public static final String SEARCH = "João";

  private AthleteFilterDTOFixtures() {
  }

  public static AthleteFilterDTO withWhitespace() {
    return new AthleteFilterDTO(String.format(" %s ", LETTER_RANGE.toLowerCase(Locale.ROOT)), GENDER,
        String.format(" %s ", SEARCH)
    );
  }

  public static AthleteFilterDTO allBlank() {
    return new AthleteFilterDTO(" ", null, " ");
  }
}
