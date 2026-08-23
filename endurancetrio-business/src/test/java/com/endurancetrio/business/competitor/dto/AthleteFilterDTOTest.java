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
import static org.junit.jupiter.api.Assertions.assertNull;

import com.endurancetrio.business.competitor.fixtures.AthleteFilterDTOFixtures;
import org.junit.jupiter.api.Test;

class AthleteFilterDTOTest {

  @Test
  void normalizesValues() {
    AthleteFilterDTO filter = AthleteFilterDTOFixtures.withWhitespace();

    assertEquals(AthleteFilterDTOFixtures.LETTER_RANGE, filter.letterRange());
    assertEquals(AthleteFilterDTOFixtures.GENDER, filter.gender());
    assertEquals(AthleteFilterDTOFixtures.SEARCH, filter.searchTerm());
  }

  @Test
  void convertsBlankValuesToNull() {
    AthleteFilterDTO filter = AthleteFilterDTOFixtures.allBlank();

    assertNull(filter.letterRange());
    assertNull(filter.gender());
    assertNull(filter.searchTerm());
  }
}
