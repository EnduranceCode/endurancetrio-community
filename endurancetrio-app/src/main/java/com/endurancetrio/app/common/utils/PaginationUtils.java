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

package com.endurancetrio.app.common.utils;

import com.endurancetrio.app.common.model.PageWindow;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

/**
 * The {@link PaginationUtils} class provides utility methods for computing windowed pagination
 * data used by numbered pagination controls.
 */
public final class PaginationUtils {

  private static final int MAX_PAGES_WITHOUT_WINDOW = 5;
  private static final int WINDOW_RADIUS = 2;

  private PaginationUtils() {
    throw new IllegalStateException("Utility Class");
  }

  /**
   * Computes the window of page indices to display for the given current page. The window is the
   * union of the first page, the last page and up to {@value WINDOW_RADIUS} pages on each side of
   * the current page. When there are at most five pages in total, all pages are listed and no
   * gaps are reported.
   *
   * @param currentPage the 0-based current page number (clamped into the valid range)
   * @param totalPages  the total number of pages
   * @return a {@link PageWindow} with the sorted 0-based page indices to display and flags telling
   *         whether an ellipsis is needed before or after the window; an empty window when
   *         {@code totalPages} is not positive
   */
  public static PageWindow pageWindow(int currentPage, int totalPages) {
    if (totalPages <= 0) {
      return new PageWindow(List.of(), false, false);
    }

    if (totalPages <= MAX_PAGES_WITHOUT_WINDOW) {
      List<Integer> pages = new ArrayList<>(totalPages);
      for (int i = 0; i < totalPages; i++) {
        pages.add(i);
      }
      return new PageWindow(pages, false, false);
    }

    int current = Math.clamp(currentPage, 0, totalPages - 1);

    TreeSet<Integer> pages = new TreeSet<>();
    pages.add(0);
    pages.add(totalPages - 1);
    for (int i = Math.max(0, current - WINDOW_RADIUS); i <= Math.min(totalPages - 1, current + WINDOW_RADIUS); i++) {
      pages.add(i);
    }

    List<Integer> innerPages = pages.stream().filter(page -> page > 0 && page < totalPages - 1).toList();
    boolean gapBefore = !innerPages.isEmpty() && innerPages.getFirst() > 1;
    boolean gapAfter = !innerPages.isEmpty() && innerPages.getLast() < totalPages - 2;

    return new PageWindow(List.copyOf(pages), gapBefore, gapAfter);
  }
}
