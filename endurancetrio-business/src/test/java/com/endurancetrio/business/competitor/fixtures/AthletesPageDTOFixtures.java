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

import com.endurancetrio.business.competitor.dto.AthletesPageDTO;
import java.util.List;

/**
 * Fixture class providing pre-configured {@link AthletesPageDTO} instances for unit tests.
 * <p>
 * This class is duplicated in {@code endurancetrio-app} under {@code competitor.fixtures} because
 * the modules do not share a test-jar dependency. Each module keeps its own copy of the fixtures it
 * needs, keeping the build simple and avoiding cross-module test-jar complications. If you add or
 * modify a factory method here, apply the same change to the app module's copy.
 */
public class AthletesPageDTOFixtures {

  private AthletesPageDTOFixtures() {
  }

  public static AthletesPageDTO page0() {
    return new AthletesPageDTO(
        List.of(AthleteDTOFixtures.standard(), AthleteDTOFixtures.athleteCavaleiro(),
            AthleteDTOFixtures.athleteBello()
        ), PaginationDTOFixtures.firstPage()
    );
  }

  public static AthletesPageDTO page1() {
    return new AthletesPageDTO(
        List.of(AthleteDTOFixtures.athleteCavaleiro(), AthleteDTOFixtures.athleteBello()),
        PaginationDTOFixtures.secondPage()
    );
  }

  public static AthletesPageDTO page2() {
    return new AthletesPageDTO(List.of(AthleteDTOFixtures.athleteBello()),
        PaginationDTOFixtures.thirdPage()
    );
  }

  public static AthletesPageDTO empty() {
    return new AthletesPageDTO(List.of(), PaginationDTOFixtures.empty());
  }
}
