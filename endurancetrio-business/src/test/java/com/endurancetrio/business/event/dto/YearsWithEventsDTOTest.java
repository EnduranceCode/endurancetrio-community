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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link YearsWithEventsDTO} DTO.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class YearsWithEventsDTOTest {

  private static final List<Integer> YEARS = List.of(2026, 2025, 2024);
  private static final List<Integer> NEXT_YEARS = List.of(2023, 2022, 2021);
  private static final List<Integer> PREVIOUS_YEARS = List.of();
  private static final int CURRENT_PAGE = 0;
  private static final int TOTAL_BATCHES = 3;
  private static final long TOTAL_YEARS = 8L;
  private static final int BATCH_SIZE = 3;
  private static final int BATCH_GROUP_PREVIOUS_PAGE = -1;
  private static final int BATCH_GROUP_NEXT_PAGE = 2;

  private YearsWithEventsDTO underTest;

  @BeforeEach
  void setUp() {
    underTest = new YearsWithEventsDTO(YEARS, NEXT_YEARS, PREVIOUS_YEARS, CURRENT_PAGE, TOTAL_BATCHES,
        TOTAL_YEARS, BATCH_SIZE, BATCH_GROUP_PREVIOUS_PAGE, BATCH_GROUP_NEXT_PAGE
    );
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(YEARS, underTest.years());
    assertEquals(NEXT_YEARS, underTest.nextYears());
    assertEquals(PREVIOUS_YEARS, underTest.previousYears());
    assertEquals(CURRENT_PAGE, underTest.currentPage());
    assertEquals(TOTAL_BATCHES, underTest.totalBatches());
    assertEquals(TOTAL_YEARS, underTest.totalYears());
    assertEquals(BATCH_SIZE, underTest.batchSize());
    assertEquals(BATCH_GROUP_PREVIOUS_PAGE, underTest.batchGroupPreviousPage());
    assertEquals(BATCH_GROUP_NEXT_PAGE, underTest.batchGroupNextPage());
  }

  @Test
  void yearsListsShouldBeImmutable() {
    List<Integer> years = underTest.years();
    List<Integer> nextYears = underTest.nextYears();
    List<Integer> previousYears = underTest.previousYears();

    assertThrows(UnsupportedOperationException.class, () -> years.add(0));
    assertThrows(UnsupportedOperationException.class, () -> nextYears.add(0));
    assertThrows(UnsupportedOperationException.class, () -> previousYears.add(0));
  }
}
