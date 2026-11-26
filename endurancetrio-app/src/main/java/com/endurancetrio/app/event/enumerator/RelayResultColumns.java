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

import com.endurancetrio.business.event.dto.RelayResultDTO;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;

/**
 * The {@code RelayResultColumns} enum defines the columns that can appear in a relay race's team
 * result table. Each constant pairs a column code, used in Thymeleaf templates to control column
 * visibility, with a {@link Predicate} that determines whether the column should be rendered based
 * on the data in a {@link RelayResultDTO}.
 * <p>
 * A column is active when at least one relay row has a value for it. Field-present columns test for
 * a non-null value; text columns additionally require non-blank content. This mirrors
 * {@link TeamResultColumns} for the collective variant, with the relay split durations in place of
 * the points and cumulative rank columns.
 */
public enum RelayResultColumns {

  RANK("rank", r -> r.rank() != null),
  TEAM_NAME("teamName", r -> StringUtils.isNotBlank(r.teamName())),
  AGE_GROUP("ageGroup", r -> r.ageGroup() != null),
  BIB("bib", r -> StringUtils.isNotBlank(r.bib())),
  ATHLETES_COUNT("athletesCount", r -> r.athletesCount() != null),
  SWIM("swim", r -> r.swim() != null),
  FIRST_BIKE("firstBike", r -> r.firstBike() != null),
  FIRST_RUN("firstRun", r -> r.firstRun() != null),
  T1("t1", r -> r.t1() != null),
  BIKE("bike", r -> r.bike() != null),
  T2("t2", r -> r.t2() != null),
  RUN("run", r -> r.run() != null),
  SECOND_RUN("secondRun", r -> r.secondRun() != null),
  T3("t3", r -> r.t3() != null),
  SECOND_BIKE("secondBike", r -> r.secondBike() != null),
  TOTAL("total", r -> r.total() != null),
  GAP("gap", r -> r.gap() != null);

  private final String code;
  private final Predicate<RelayResultDTO> activator;

  RelayResultColumns(String code, Predicate<RelayResultDTO> activator) {
    this.code = code;
    this.activator = activator;
  }

  public String getCode() {
    return code;
  }

  /**
   * Tests whether this column should be rendered for the given relay result.
   *
   * @param result the relay result to test
   * @return {@code true} if the column should be active based on the result's data
   */
  public boolean isActive(RelayResultDTO result) {
    return activator.test(result);
  }
}
