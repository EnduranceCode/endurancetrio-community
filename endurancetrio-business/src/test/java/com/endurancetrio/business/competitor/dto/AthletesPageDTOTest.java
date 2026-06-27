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

package com.endurancetrio.business.competitor.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.endurancetrio.business.competitor.fixtures.AthleteDTOFixtures;
import com.endurancetrio.business.competitor.fixtures.AthletesPageDTOFixtures;
import com.endurancetrio.business.competitor.fixtures.PaginationDTOFixtures;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AthletesPageDTOTest {

  private AthletesPageDTO underTest;

  @BeforeEach
  void setUp() {
    underTest = AthletesPageDTOFixtures.page0();
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(AthletesPageDTOFixtures.page0().athletes(), underTest.athletes());
    assertEquals(AthletesPageDTOFixtures.page0().pagination(), underTest.pagination());
  }

  @Test
  void shouldRejectNullAthletes() {
    var pagination = PaginationDTOFixtures.firstPage();
    assertThrows(IllegalArgumentException.class,
        () -> new AthletesPageDTO(null, pagination));
  }

  @Test
  void shouldRejectNullPagination() {
    var athletes = List.of(AthleteDTOFixtures.standard());
    assertThrows(IllegalArgumentException.class,
        () -> new AthletesPageDTO(athletes, null));
  }

  @Test
  void athletesCanBeEmpty() {
    AthletesPageDTO page = new AthletesPageDTO(List.of(), PaginationDTOFixtures.firstPage());
    assertTrue(page.athletes().isEmpty());
  }
}
