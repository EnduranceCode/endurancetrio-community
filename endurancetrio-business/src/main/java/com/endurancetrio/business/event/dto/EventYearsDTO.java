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

import java.io.Serializable;
import java.util.List;

/**
 * The {@link EventYearsDTO} represents a paginated batch of years for navigating through events by
 * year. It carries both the current batch of years and adjacent batches for pagination, along with
 * metadata about the overall pagination state.
 *
 * @param years                  the list of years in the current batch
 * @param nextYears              the list of years in the next batch (may be empty if on the last
 *                               batch)
 * @param previousYears          the list of years in the previous batch (may be empty if on the
 *                               first batch)
 * @param currentPage            the current page index (zero-based)
 * @param totalBatches           the total number of batches available
 * @param totalYears             the total number of distinct event years
 * @param batchSize              the maximum number of years per batch
 * @param batchGroupPreviousPage the page index of the previous batch group (may be -1 if none)
 * @param batchGroupNextPage     the page index of the next batch group (may be -1 if none)
 */
public record EventYearsDTO(List<Integer> years, List<Integer> nextYears,
                            List<Integer> previousYears, int currentPage, int totalBatches,
                            long totalYears, int batchSize, int batchGroupPreviousPage,
                            int batchGroupNextPage) implements Serializable {

  public EventYearsDTO {
    years = List.copyOf(years);
    nextYears = List.copyOf(nextYears);
    previousYears = List.copyOf(previousYears);
  }
}
