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
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link DistanceLegDTO} DTO.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class DistanceLegDTOTest {

  private static final String CORE_SPORT = "SWIM";
  private static final Integer LENGTH = 1500;

  private DistanceLegDTO underTest;

  @BeforeEach
  void setUp() {
    underTest = new DistanceLegDTO(CORE_SPORT, LENGTH);
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(CORE_SPORT, underTest.coreSport());
    assertEquals(LENGTH, underTest.length());
  }

  @Test
  void coreSportCanBeNull() {
    DistanceLegDTO dto = new DistanceLegDTO(null, LENGTH);
    assertNull(dto.coreSport());
    assertEquals(LENGTH, dto.length());
  }

  @Test
  void shouldRejectNullLength() {
    assertThrows(IllegalArgumentException.class, () -> new DistanceLegDTO(CORE_SPORT, null));
  }
}
