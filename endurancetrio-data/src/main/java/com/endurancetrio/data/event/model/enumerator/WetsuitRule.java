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

import com.endurancetrio.data.event.model.entity.TriathlonBasedRace;

/**
 * The {@link WetsuitRule} enum represents the wetsuit rule for a {@link TriathlonBasedRace}.
 * <p>
 * It includes the following constants:
 * <ul>
 *   <li>
 *     {@link #FORBIDDEN} : used for the {@link TriathlonBasedRace races} where the use
 *     of wetsuits is forbidden.
 *   </li>
 *   <li>
 *     {@link #MANDATORY} : used for the {@link TriathlonBasedRace races} where the use
 *     of wetsuits is mandatory.
 *   </li>
 *   <li>
 *     {@link #OPTIONAL} : used for the {@link TriathlonBasedRace races} where the use
 *     of wetsuits is optional.
 *   </li>
 *   <li>
 *     {@link #UNKNOWN} : used for the {@link TriathlonBasedRace races} where the rule for the use
 *     of wetsuits is unknown.
 *   </li>
 * </ul>
 */
public enum WetsuitRule {
  FORBIDDEN("FORBIDDEN"),
  MANDATORY("MANDATORY"),
  OPTIONAL("OPTIONAL"),
  UNKNOWN("UNKNOWN");

  private final String code;

  WetsuitRule(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }
}
