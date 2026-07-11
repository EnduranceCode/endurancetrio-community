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

import com.endurancetrio.business.competitor.fixtures.PaginationDTOFixtures;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.data.event.model.enumerator.RaceType;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AthleteRacesPageDTOTest {

  private AthleteRacesPageDTO underTest;

  @BeforeEach
  void setUp() {
    var races = List.of(createRaceDTO());
    underTest = new AthleteRacesPageDTO(races, PaginationDTOFixtures.firstPage());
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(1, underTest.races().size());
    assertEquals(PaginationDTOFixtures.firstPage(), underTest.pagination());
  }

  @Test
  void shouldRejectNullRaces() {
    var page = PaginationDTOFixtures.firstPage();
    assertThrows(IllegalArgumentException.class, () -> new AthleteRacesPageDTO(null, page));
  }

  @Test
  void shouldRejectNullPagination() {
    var races = List.of(createRaceDTO());
    assertThrows(IllegalArgumentException.class, () -> new AthleteRacesPageDTO(races, null));
  }

  @Test
  void racesCanBeEmpty() {
    var page = PaginationDTOFixtures.firstPage();
    AthleteRacesPageDTO dto = new AthleteRacesPageDTO(List.of(), page);
    assertTrue(dto.races().isEmpty());
  }

  private static RaceDTO createRaceDTO() {
    return new RaceDTO(1L, "Triatlo de Peniche", "geral", LocalDate.of(1984, Month.AUGUST, 15),
        null, List.of("SWIM"), List.of("SPRINT"), RaceType.INDIVIDUAL_PARENT, "INDIVIDUAL", null,
        null, "UNKNOWN"
    );
  }
}
