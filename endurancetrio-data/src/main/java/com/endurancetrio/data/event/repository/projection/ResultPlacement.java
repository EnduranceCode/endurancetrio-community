/*
 * Copyright (c) 2011-2026 Ricardo do Canto
 *
 * This file is part of the EnduranceTrio project.
 *
 * Licensed under the Functional Software License (FSL), Version 1.1, ALv2 Future License
 * (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at https://fsl.software/
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND WITHOUT WARRANTIES OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING WITHOUT LIMITATION WARRANTIES OF FITNESS FOR A PARTICULAR
 * PURPOSE, MERCHANTABILITY, TITLE OR NON-INFRINGEMENT.
 *
 * IN NO EVENT WILL WE HAVE ANY LIABILITY TO YOU ARISING OUT OF OR RELATED TO THE
 * SOFTWARE, INCLUDING INDIRECT, SPECIAL, INCIDENTAL OR CONSEQUENTIAL DAMAGES,
 * EVEN IF WE HAVE BEEN INFORMED OF THEIR POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.data.event.repository.projection;

import com.endurancetrio.data.event.model.enumerator.Penalty;

/**
 * A result's placement: an individual result's ID together with its rank and penalty.
 * <p>
 * The projection is generic in shape. The accompanying query restricts results to those linked
 * to a {@code team_result} in the <em>same</em> race, which excludes cross-race team
 * classifications such as {@code TEAM_BY_RANK}. The call site further restricts usage to relay
 * races.
 */
public interface ResultPlacement {

  /**
   * Returns the ID of the individual result.
   *
   * @return the individual result ID
   */
  Long getIndividualResultId();

  /**
   * Returns the finishing position, or {@code null} if the result was not ranked.
   *
   * @return the rank
   */
  Integer getRank();

  /**
   * Returns the penalty applied to the result, or {@code null} if none.
   *
   * @return the penalty
   */
  Penalty getPenalty();
}