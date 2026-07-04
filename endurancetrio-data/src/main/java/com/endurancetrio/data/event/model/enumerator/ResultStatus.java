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

package com.endurancetrio.data.event.model.enumerator;

import com.endurancetrio.data.event.model.entity.Race;

/**
 * The {@link ResultStatus} enum represents the state of result data availability for a
 * {@link Race}, independent of the race lifecycle status.
 * <p>
 * It includes the following constants:
 * <ul>
 *   <li>
 *     {@link #UNKNOWN} : no result data is known; the race has not been researched yet
 *     or no information exists.
 *   </li>
 *   <li>
 *     {@link #PENDING} : the result data has been collected and is available but has not yet been
 *     entered into the database.
 *   </li>
 *   <li>
 *     {@link #INCOMPLETE} : some results have been entered, but the data set is incomplete;
 *     community help may be needed to fill gaps.
 *   </li>
 *   <li>
 *     {@link #COMPLETE} : full result data is available and has been entered.
 *   </li>
 *   <li>
 *     {@link #EMPTY} : the race was held but had no participants, so there are no results
 *     to display. This is a final state — no future results will ever exist for this race.
 *   </li>
 * </ul>
 */
public enum ResultStatus {
  UNKNOWN("UNKNOWN"),
  PENDING("PENDING"),
  INCOMPLETE("INCOMPLETE"),
  COMPLETE("COMPLETE"),
  EMPTY("EMPTY");

  private final String code;

  ResultStatus(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  @Override
  public String toString() {
    return code;
  }
}
