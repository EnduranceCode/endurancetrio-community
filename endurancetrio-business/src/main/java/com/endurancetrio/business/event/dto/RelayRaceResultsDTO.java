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

import com.endurancetrio.business.event.enumerator.RaceTypeGroup;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import java.util.List;
import java.util.Map;

/**
 * The {@link RelayRaceResultsDTO} holds a relay race's stored results grouped by age group.
 *
 * @param relayResults the relay results by age group, each list in display order
 */
public record RelayRaceResultsDTO(Map<AgeGroup, List<RelayResultDTO>> relayResults) implements ResultsDTO {

  @Override
  public RaceTypeGroup resultType() {
    return RaceTypeGroup.RELAY;
  }
}
