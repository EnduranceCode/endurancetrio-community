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

package com.endurancetrio.business.tracker.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link RouteDTO} DTO.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class RouteDTOTest {

  private static final Long ID = 1L;
  private static final String REFERENCE = "SMP";
  private static final RouteSegmentDTO SEGMENT = new RouteSegmentDTO(1L, 1, "SDABC", "SDDEF");

  private RouteDTO underTest;

  @BeforeEach
  void setUp() {
    underTest = new RouteDTO(ID, REFERENCE, List.of(SEGMENT));
  }

  @Test
  void dtoShouldRetainValues() {

    assertEquals(ID, underTest.id());
    assertEquals(REFERENCE, underTest.reference());
    assertEquals(1, underTest.segments().size());
    assertEquals("SDABC", underTest.segments().getFirst().startDevice());
  }
}
