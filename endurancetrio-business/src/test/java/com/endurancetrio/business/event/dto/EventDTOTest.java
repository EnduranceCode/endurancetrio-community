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

import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link EventDTO} DTO.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class EventDTOTest {

  private static final Long ID = 1L;
  private static final String TITLE = "Test Event";
  private static final LocalDate START_DATE = LocalDate.of(2026, 6, 1);
  private static final LocalDate END_DATE = LocalDate.of(2026, 6, 1);
  private static final String CITY = "Test City";
  private static final String COUNTY = "Test County";
  private static final String DISTRICT = "Test District";
  private static final List<String> SPORT_CODES = List.of("TRIATHLON", "ROAD_RUNNING");

  private EventDTO underTest;

  @BeforeEach
  void setUp() {
    underTest = new EventDTO(
        ID, TITLE, START_DATE, END_DATE, CITY, COUNTY, DISTRICT, SPORT_CODES
    );
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(ID, underTest.id());
    assertEquals(TITLE, underTest.title());
    assertEquals(START_DATE, underTest.startDate());
    assertEquals(END_DATE, underTest.endDate());
    assertEquals(CITY, underTest.city());
    assertEquals(COUNTY, underTest.county());
    assertEquals(DISTRICT, underTest.district());
    assertEquals(SPORT_CODES, underTest.sportCodes());
  }

  @Test
  void shouldRejectNullId() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventDTO(null, TITLE, START_DATE, END_DATE, CITY, COUNTY, DISTRICT, SPORT_CODES)
    );
  }

  @Test
  void shouldRejectNullTitle() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventDTO(ID, null, START_DATE, END_DATE, CITY, COUNTY, DISTRICT, SPORT_CODES)
    );
  }

  @Test
  void shouldRejectBlankTitle() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventDTO(ID, " ", START_DATE, END_DATE, CITY, COUNTY, DISTRICT, SPORT_CODES)
    );
  }

  @Test
  void shouldRejectNullStartDate() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventDTO(ID, TITLE, null, END_DATE, CITY, COUNTY, DISTRICT, SPORT_CODES)
    );
  }

  @Test
  void shouldRejectNullEndDate() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventDTO(ID, TITLE, START_DATE, null, CITY, COUNTY, DISTRICT, SPORT_CODES)
    );
  }

  @Test
  void shouldRejectNullCity() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventDTO(ID, TITLE, START_DATE, END_DATE, null, COUNTY, DISTRICT, SPORT_CODES)
    );
  }

  @Test
  void shouldRejectBlankCity() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventDTO(ID, TITLE, START_DATE, END_DATE, "", COUNTY, DISTRICT, SPORT_CODES)
    );
  }

  @Test
  void shouldRejectNullCounty() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventDTO(ID, TITLE, START_DATE, END_DATE, CITY, null, DISTRICT, SPORT_CODES)
    );
  }

  @Test
  void shouldRejectBlankCounty() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventDTO(ID, TITLE, START_DATE, END_DATE, CITY, " ", DISTRICT, SPORT_CODES)
    );
  }

  @Test
  void shouldRejectNullDistrict() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventDTO(ID, TITLE, START_DATE, END_DATE, CITY, COUNTY, null, SPORT_CODES)
    );
  }

  @Test
  void shouldRejectBlankDistrict() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventDTO(ID, TITLE, START_DATE, END_DATE, CITY, COUNTY, "\t", SPORT_CODES)
    );
  }

  @Test
  void shouldRejectNullSportCodes() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventDTO(ID, TITLE, START_DATE, END_DATE, CITY, COUNTY, DISTRICT, null)
    );
  }

  @Test
  void sportCodesCanBeEmpty() {
    EventDTO dto = new EventDTO(ID, TITLE, START_DATE, END_DATE, CITY, COUNTY, DISTRICT, List.of());
    assertEquals(List.of(), dto.sportCodes());
  }
}
