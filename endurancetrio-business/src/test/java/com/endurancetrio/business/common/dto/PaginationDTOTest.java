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

package com.endurancetrio.business.common.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

/**
 * Unit test for the {@link PaginationDTO} DTO.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class PaginationDTOTest {

  @Test
  void dtoShouldRetainValues() {
    PaginationDTO underTest = new PaginationDTO(1, 5, 100, true, true);

    assertEquals(1, underTest.pageNumber());
    assertEquals(5, underTest.totalPages());
    assertEquals(100, underTest.totalItems());
    assertTrue(underTest.hasPrevious());
    assertTrue(underTest.hasNext());
  }

  @Test
  void firstPageShouldHaveNoPrevious() {
    PaginationDTO underTest = new PaginationDTO(0, 3, 50, false, true);

    assertEquals(0, underTest.pageNumber());
    assertFalse(underTest.hasPrevious());
    assertTrue(underTest.hasNext());
  }

  @Test
  void lastPageShouldHaveNoNext() {
    PaginationDTO underTest = new PaginationDTO(2, 3, 50, true, false);

    assertEquals(2, underTest.pageNumber());
    assertTrue(underTest.hasPrevious());
    assertFalse(underTest.hasNext());
  }

  @Test
  void singlePageShouldHaveNoNavigation() {
    PaginationDTO underTest = new PaginationDTO(0, 1, 5, false, false);

    assertFalse(underTest.hasPrevious());
    assertFalse(underTest.hasNext());
  }

  @Test
  void shouldRejectNegativePageNumber() {
    assertThrows(IllegalArgumentException.class, () -> new PaginationDTO(-1, 1, 0, false, false));
  }

  @Test
  void shouldRejectNegativeTotalPages() {
    assertThrows(IllegalArgumentException.class, () -> new PaginationDTO(0, -1, 0, false, false));
  }

  @Test
  void shouldRejectNegativeTotalItems() {
    assertThrows(IllegalArgumentException.class, () -> new PaginationDTO(0, 1, -1, false, false));
  }

  @Test
  void fromShouldCreateFromSpringDataPage() {
    Page<String> springPage = new PageImpl<>(List.of("a", "b"), PageRequest.of(2, 2), 10);

    PaginationDTO underTest = PaginationDTO.from(springPage);

    assertEquals(2, underTest.pageNumber());
    assertEquals(5, underTest.totalPages());
    assertEquals(10, underTest.totalItems());
    assertTrue(underTest.hasPrevious());
    assertTrue(underTest.hasNext());
  }

  @Test
  void fromShouldHandleFirstPage() {
    Page<String> springPage = new PageImpl<>(List.of("a", "b"), PageRequest.of(0, 5), 2);

    PaginationDTO underTest = PaginationDTO.from(springPage);

    assertEquals(0, underTest.pageNumber());
    assertEquals(1, underTest.totalPages());
    assertEquals(2, underTest.totalItems());
    assertFalse(underTest.hasPrevious());
    assertFalse(underTest.hasNext());
  }
}
