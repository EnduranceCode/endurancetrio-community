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

import com.endurancetrio.business.competitor.dto.TeamDTO;

/**
 * Fixture class providing pre-configured {@link TeamDTO} instances for unit tests.
 * <p>
 * This class is duplicated in {@code endurancetrio-app} under {@code competitor.fixtures} because
 * the modules do not share a test-jar dependency. Each module keeps its own copy of the fixtures it
 * needs, keeping the build simple and avoiding cross-module test-jar complications. If you add or
 * modify a factory method here, apply the same change to the app module's copy.
 */
public class TeamDTOFixtures {

  private TeamDTOFixtures() {
  }

  public static TeamDTO standard() {
    return new TeamDTO(TeamFixture.STANDARD_ID, TeamFixture.STANDARD_FULL_NAME,
        TeamFixture.STANDARD_SHORT_NAME, TeamFixture.STANDARD_CITY, TeamFixture.STANDARD_COUNTY,
        TeamFixture.STANDARD_DISTRICT
    );
  }
}
