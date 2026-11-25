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

package com.endurancetrio.app.event.enumerator;

import com.endurancetrio.business.event.dto.TeamResultDTO;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;

/**
 * The {@code TeamResultColumns} enum defines the columns that can appear in a collective race's
 * team result table. Each constant pairs a column code, used in Thymeleaf templates to control
 * column visibility, with a {@link Predicate} that determines whether the column should be rendered
 * based on the data in a {@link TeamResultDTO}.
 * <p>
 * A column is active when at least one team row has a value for it. Field-present columns test for
 * a non-null value; text columns additionally require non-blank content. The {@link #GAP} column is
 * active when any of the three race-type-specific gap fields has a value.
 */
public enum TeamResultColumns {

  RANK("rank", r -> r.rank() != null),
  TEAM_NAME("teamName", r -> StringUtils.isNotBlank(r.teamName())),
  BIB("bib", r -> StringUtils.isNotBlank(r.bib())),
  ATHLETES_COUNT("athletesCount", r -> r.athletesCount() != null),
  TOTAL("total", r -> r.total() != null),
  POINTS("points", r -> r.points() != null),
  CUMULATIVE_RANK("cumulativeRank", r -> r.cumulativeRank() != null),
  GAP("gap", r -> r.totalGap() != null || r.pointsGap() != null || r.cumulativeRankGap() != null);

  private final String code;
  private final Predicate<TeamResultDTO> activator;

  TeamResultColumns(String code, Predicate<TeamResultDTO> activator) {
    this.code = code;
    this.activator = activator;
  }

  public String getCode() {
    return code;
  }

  /**
   * Tests whether this column should be rendered for the given team result.
   *
   * @param result the team result to test
   * @return {@code true} if the column is active based on the result's data
   */
  public boolean isActive(TeamResultDTO result) {
    return activator.test(result);
  }
}
