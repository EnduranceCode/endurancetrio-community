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

/**
 * The {@link ResultsDTO} sealed hierarchy models the results of a race according to its result
 * type.
 * <p>
 * Each permitted variant carries only the data relevant to that result type and exposes a
 * {@link #resultType()} discriminator so callers can branch on the concrete variant.
 */
public sealed interface ResultsDTO permits IndividualRaceResultsDTO, TeamRaceResultsDTO, RelayRaceResultsDTO,
    EmptyRaceResultsDTO {

  /**
   * Returns the discriminator identifying this result type.
   *
   * @return the result type code
   */
  String resultType();
}
