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
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class EventFilterDTOTest {

  @Test
  void normalizesValues() {
    EventFilterDTO filter = new EventFilterDTO(" São João ", 2024, " Lisboa ", " Cascais ");

    assertEquals("São João", filter.q());
    assertEquals(2024, filter.year());
    assertEquals("Lisboa", filter.district());
    assertEquals("Cascais", filter.county());
  }

  @Test
  void convertsBlankValuesToNull() {
    EventFilterDTO filter = new EventFilterDTO(" ", null, " ", " ");

    assertNull(filter.q());
    assertNull(filter.year());
    assertNull(filter.district());
    assertNull(filter.county());
  }
}
