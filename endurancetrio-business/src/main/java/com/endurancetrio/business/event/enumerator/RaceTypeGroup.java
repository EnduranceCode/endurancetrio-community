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

package com.endurancetrio.business.event.enumerator;

import com.endurancetrio.business.event.mapper.RaceMapper;

import com.endurancetrio.data.event.model.enumerator.RaceType;

/**
 * The {@link RaceTypeGroup} enum represents the high-level grouping of race types used in the
 * {@link RaceMapper race mapper}.
 * <p>
 * It includes the following constants:
 * <ul>
 *   <li>{@link #INDIVIDUAL} : used for individual races (parent or derived).</li>
 *   <li>{@link #COLLECTIVE} : used for team-based races (by rank, time, or points).</li>
 *   <li>{@link #RELAY} : used for relay races (team or mixed, parent or derived).</li>
 * </ul>
 */
public enum RaceTypeGroup {
  INDIVIDUAL("INDIVIDUAL"),
  COLLECTIVE("COLLECTIVE"),
  RELAY("RELAY");

  private final String code;

  RaceTypeGroup(String code) {
    this.code = code;
  }

  public String getCode() {
    return code;
  }

  @Override
  public String toString() {
    return code;
  }

  /**
   * Returns the high-level group for the given {@link RaceType}.
   * <p>
   * This is the single place that encodes which race types are relay (team or mixed, parent or
   * derived): a relay leg's finishing position belongs to the team, not to the individual athlete.
   *
   * @param raceType the race type to classify
   * @return the matching group
   */
  public static RaceTypeGroup from(RaceType raceType) {
    if (raceType == null) {
      throw new IllegalArgumentException("raceType must not be null");
    }

    return switch (raceType) {
      case INDIVIDUAL_PARENT, INDIVIDUAL_DERIVED -> INDIVIDUAL;
      case TEAM_BY_RANK, TEAM_BY_TIME, TEAM_BY_POINTS -> COLLECTIVE;
      case TEAM_RELAY_PARENT, TEAM_RELAY_DERIVED, MIXED_RELAY_PARENT, MIXED_RELAY_DERIVED -> RELAY;
    };
  }
}
