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

package com.endurancetrio.app.competitor.fixtures;

import com.endurancetrio.business.competitor.dto.AthleteDTO;
import com.endurancetrio.data.competitor.model.enumerator.AthleteGender;
import com.endurancetrio.data.competitor.model.enumerator.Country;

/**
 * Fixture class providing pre-configured {@link AthleteDTO} instances for unit tests in the
 * {@code endurancetrio-app} module.
 * <p>
 * This class is duplicated in {@code endurancetrio-business} under {@code competitor.fixtures}
 * because the modules do not share a test-jar dependency. Each module keeps its own copy of the
 * fixtures it needs, keeping the build simple and avoiding cross-module test-jar complications. If
 * you add or modify a factory method here, apply the same change to the business module's copy.
 */
public class AthleteDTOFixtures {

  private AthleteDTOFixtures() {
  }

  public static AthleteDTO standard() {
    return new AthleteDTO(1L, "Paulo José Paula Carvalho", null, "Paulo Paula Carvalho",
        AthleteGender.MALE, Country.POR, 1961
    );
  }

  public static AthleteDTO athleteCavaleiro() {
    return new AthleteDTO(4L, "Paulo Cavaleiro", null, "Paulo Cavaleiro", AthleteGender.MALE,
        Country.POR, null
    );
  }

  public static AthleteDTO athleteBello() {
    return new AthleteDTO(22L, "Nuno Bello Conceição", null, "Nuno Bello Conceição",
        AthleteGender.MALE, Country.POR, null
    );
  }
}
