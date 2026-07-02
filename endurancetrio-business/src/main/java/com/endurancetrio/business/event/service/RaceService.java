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

package com.endurancetrio.business.event.service;

import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.dto.RaceResultsDTO;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;

/**
 * The {@link RaceService} defines the business operations for managing race data.
 */
public interface RaceService {

  /**
   * Returns a {@link RaceResultsDTO} containing the results of the given {@code race}, grouped by
   * {@link AgeGroup}. The results include individual, team, or relay entries depending on the
   * race's type group, each enriched with computed time gaps relative to the overall winner.
   *
   * @param race the race whose results are to be retrieved
   * @return a {@link RaceResultsDTO} with the race results
   * @throws EnduranceTrioException if the race's type group is invalid or unsupported
   */
  RaceResultsDTO getRaceResults(RaceDTO race);
}
