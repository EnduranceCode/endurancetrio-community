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
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link OrganizerDTO} DTO.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class OrganizerDTOTest {

  private static final Long ID = 1L;
  private static final String NAME = "Test Club";
  private static final String ORGANIZER_TYPE = "CLUB";
  private static final String CITY = "Lisbon";
  private static final String COUNTY = "Lisbon";
  private static final String DISTRICT = "Lisbon";

  private OrganizerDTO underTest;

  @BeforeEach
  void setUp() {
    underTest = new OrganizerDTO(ID, NAME, ORGANIZER_TYPE, CITY, COUNTY, DISTRICT);
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(ID, underTest.id());
    assertEquals(NAME, underTest.name());
    assertEquals(ORGANIZER_TYPE, underTest.organizerType());
    assertEquals(CITY, underTest.city());
    assertEquals(COUNTY, underTest.county());
    assertEquals(DISTRICT, underTest.district());
  }

  @Test
  void shouldRejectNullName() {
    assertThrows(IllegalArgumentException.class,
        () -> new OrganizerDTO(ID, null, ORGANIZER_TYPE, CITY, COUNTY, DISTRICT)
    );
  }

  @Test
  void shouldRejectBlankName() {
    assertThrows(IllegalArgumentException.class,
        () -> new OrganizerDTO(ID, " ", ORGANIZER_TYPE, CITY, COUNTY, DISTRICT)
    );
  }

  @Test
  void shouldRejectNullOrganizerType() {
    assertThrows(IllegalArgumentException.class,
        () -> new OrganizerDTO(ID, NAME, null, CITY, COUNTY, DISTRICT)
    );
  }

  @Test
  void shouldRejectBlankOrganizerType() {
    assertThrows(IllegalArgumentException.class,
        () -> new OrganizerDTO(ID, NAME, "", CITY, COUNTY, DISTRICT)
    );
  }

  @Test
  void shouldRejectNullCity() {
    assertThrows(IllegalArgumentException.class,
        () -> new OrganizerDTO(ID, NAME, ORGANIZER_TYPE, null, COUNTY, DISTRICT)
    );
  }

  @Test
  void shouldRejectBlankCity() {
    assertThrows(IllegalArgumentException.class,
        () -> new OrganizerDTO(ID, NAME, ORGANIZER_TYPE, " ", COUNTY, DISTRICT)
    );
  }

  @Test
  void shouldRejectNullCounty() {
    assertThrows(IllegalArgumentException.class,
        () -> new OrganizerDTO(ID, NAME, ORGANIZER_TYPE, CITY, null, DISTRICT)
    );
  }

  @Test
  void shouldRejectBlankCounty() {
    assertThrows(IllegalArgumentException.class,
        () -> new OrganizerDTO(ID, NAME, ORGANIZER_TYPE, CITY, " ", DISTRICT)
    );
  }

  @Test
  void shouldRejectNullDistrict() {
    assertThrows(IllegalArgumentException.class,
        () -> new OrganizerDTO(ID, NAME, ORGANIZER_TYPE, CITY, COUNTY, null)
    );
  }

  @Test
  void shouldRejectBlankDistrict() {
    assertThrows(IllegalArgumentException.class,
        () -> new OrganizerDTO(ID, NAME, ORGANIZER_TYPE, CITY, COUNTY, "")
    );
  }
}
