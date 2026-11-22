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

package com.endurancetrio.business.event.dto;

import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import java.util.List;
import java.util.Map;

/**
 * The {@link RaceResultsDTO} holds the complete set of results for a given race.
 *
 * @param race    the race these results belong to
 * @param results the results variant matching the race's result type
 */
public record RaceResultsDTO(RaceDTO race, ResultsDTO results) {

  /**
   * Returns the individual results grouped by age group.
   *
   * @return the individual result map, or {@code null} when this variant has no individual results
   */
  public Map<AgeGroup, List<IndividualResultDTO>> individualResults() {
    return switch (results) {
      case IndividualRaceResultsDTO(Map<AgeGroup, List<IndividualResultDTO>> individualResults) ->
          individualResults;
      case EmptyRaceResultsDTO() -> null;
    };
  }
}
