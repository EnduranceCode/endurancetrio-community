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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.endurancetrio.app.common.model.PageWindow;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import org.junit.jupiter.api.Test;

class PaginationUtilsTest {

  @Test
  void pageWindowShouldReturnEmptyWindowWhenTotalPagesIsZero() {
    assertEquals(new PageWindow(List.of(), false, false), PaginationUtils.pageWindow(0, 0));
  }

  @Test
  void pageWindowShouldReturnEmptyWindowWhenTotalPagesIsNegative() {
    assertEquals(new PageWindow(List.of(), false, false), PaginationUtils.pageWindow(3, -2));
  }

  @Test
  void pageWindowShouldReturnSinglePageWhenTotalPagesIsOne() {
    assertEquals(new PageWindow(List.of(0), false, false), PaginationUtils.pageWindow(0, 1));
  }

  @Test
  void pageWindowShouldReturnAllPagesWhenTotalPagesIsAtMostFive() {
    assertEquals(new PageWindow(List.of(0, 1, 2), false, false), PaginationUtils.pageWindow(1, 3));
    assertEquals(new PageWindow(List.of(0, 1, 2, 3, 4), false, false), PaginationUtils.pageWindow(2, 5));
  }

  @Test
  void pageWindowShouldReturnAllPagesWithoutGapsWhenTotalPagesIsSixAndPageIsMiddle() {
    assertEquals(new PageWindow(List.of(0, 1, 2, 3, 4, 5), false, false), PaginationUtils.pageWindow(2, 6));
  }

  @Test
  void pageWindowShouldReturnWindowWithBothGapsWhenCurrentPageIsMiddle() {
    assertEquals(new PageWindow(List.of(0, 3, 4, 5, 6, 7, 13), true, true), PaginationUtils.pageWindow(5, 14));
  }

  @Test
  void pageWindowShouldReturnWindowWithTrailingGapWhenCurrentPageIsFirst() {
    assertEquals(new PageWindow(List.of(0, 1, 2, 13), false, true), PaginationUtils.pageWindow(0, 14));
    assertEquals(new PageWindow(List.of(0, 1, 2, 3, 13), false, true), PaginationUtils.pageWindow(1, 14));
  }

  @Test
  void pageWindowShouldReturnWindowWithLeadingGapWhenCurrentPageIsLast() {
    assertEquals(new PageWindow(List.of(0, 11, 12, 13), true, false), PaginationUtils.pageWindow(13, 14));
    assertEquals(new PageWindow(List.of(0, 10, 11, 12, 13), true, false), PaginationUtils.pageWindow(12, 14));
  }

  @Test
  void pageWindowShouldClampNegativeCurrentPageToFirstPage() {
    assertEquals(new PageWindow(List.of(0, 1, 2, 13), false, true), PaginationUtils.pageWindow(-4, 14));
  }

  @Test
  void pageWindowShouldClampOutOfRangeCurrentPageToLastPage() {
    assertEquals(new PageWindow(List.of(0, 11, 12, 13), true, false), PaginationUtils.pageWindow(99, 14));
  }

  @Test
  void constructorShouldThrow() {
    assertThrows(InvocationTargetException.class, () -> {
          var constructor = PaginationUtils.class.getDeclaredConstructor();
          constructor.setAccessible(true);
          constructor.newInstance();
        }
    );
  }
}
