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

import com.endurancetrio.business.event.dto.IndividualResultDTO;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;

/**
 * The {@code IndividualResultColumns} enum defines the columns that can appear in individual
 * race result tables. Each constant pairs a column code, used in Thymeleaf templates to control
 * column visibility, with a {@link Predicate} that determines whether the column should be
 * rendered based on the data in an {@link IndividualResultDTO}.
 * <p>
 * Column activation follows an OPEN-group heuristic: a column is included in the active set
 * when at least one result in the OPEN group satisfies its predicate. Three categories exist:
 * <ul>
 *   <li>
 *     Always-active columns ({@link #RANK}, {@link #ATHLETE}, {@link #TOTAL}) via
 *     {@code r -> true}.
 *   </li>
 *   <li>
 *     Field-present columns, active when the corresponding DTO field is non-null
 *     (e.g. {@link #SWIM}, {@link #T1}, {@link #BIKE}).
 *   </li>
 *   <li>
 *     Conditional columns with custom predicates (e.g. {@link #TEAM} requires a non-blank
 *     team short name).
 *   </li>
 * </ul>
 */
public enum IndividualResultColumns {

  RANK("rank", r -> true),
  ATHLETE("athlete", r -> true),
  AGE_GROUP("ageGroup", r -> r.ageGroup() != null),
  PARA_CLASS("paraClass", r -> r.paraClass() != null),
  TEAM("team", r -> r.team() != null && StringUtils.isNotBlank(r.team().shortName())),
  BIB("bib", r -> StringUtils.isNotBlank(r.bib())),
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
  TOTAL("total", r -> true),
  GAP("gap", r -> r.gap() != null),
  POINTS("points", r -> r.points() != null);

  private final String code;
  private final Predicate<IndividualResultDTO> activator;

  IndividualResultColumns(String code, Predicate<IndividualResultDTO> activator) {
    this.code = code;
    this.activator = activator;
  }

  public String getCode() {
    return code;
  }

  /**
   * Tests whether this column should be rendered for the given result.
   *
   * @param result the individual result to test
   * @return {@code true} if the column should be active based on the result's data
   */
  public boolean isActive(IndividualResultDTO result) {
    return activator.test(result);
  }
}
