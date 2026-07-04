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
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link EventOverviewDTO} DTO.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class EventOverviewDTOTest {

  private static final Long ID = 1L;
  private static final String TITLE = "Test Event";
  private static final LocalDate START_DATE = LocalDate.of(2026, Month.JUNE, 1);
  private static final LocalDate END_DATE = LocalDate.of(2026, Month.JUNE, 1);
  private static final String CITY = "Test City";
  private static final String COUNTY = "Test County";
  private static final String DISTRICT = "Test District";
  private static final List<OrganizerDTO> ORGANIZERS = List.of(
      new OrganizerDTO(1L, "Organizer A", "CLUB", "City", "County", "District")
  );
  private static final List<RaceDTO> RACES = List.of(
      new RaceDTO(1L, "Race 1", "Elite", LocalDate.of(2026, Month.JUNE, 1), null,
          List.of("TRIATHLON"), List.of("SPRINT"), "TRIATHLON", null, null, "UNKNOWN"
      )
  );
  private static final List<EventFileDTO> FILES = List.of(
      new EventFileDTO(1L, "Regulamento", "RULES", "19840815NAC001-REG001-01.pdf")
  );

  private EventOverviewDTO underTest;

  @BeforeEach
  void setUp() {
    underTest = new EventOverviewDTO(
        ID, TITLE, START_DATE, END_DATE, CITY, COUNTY, DISTRICT, ORGANIZERS, RACES, FILES
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
    assertEquals(ORGANIZERS, underTest.organizers());
    assertEquals(RACES, underTest.races());
    assertEquals(FILES, underTest.files());
  }

  @Test
  void shouldRejectNullTitle() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventOverviewDTO(ID, null, START_DATE, END_DATE, CITY, COUNTY, DISTRICT,
            ORGANIZERS, RACES, FILES
        )
    );
  }

  @Test
  void shouldRejectBlankTitle() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventOverviewDTO(ID, " ", START_DATE, END_DATE, CITY, COUNTY, DISTRICT,
            ORGANIZERS, RACES, FILES
        )
    );
  }

  @Test
  void shouldRejectNullStartDate() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventOverviewDTO(ID, TITLE, null, END_DATE, CITY, COUNTY, DISTRICT,
            ORGANIZERS, RACES, FILES
        )
    );
  }

  @Test
  void shouldRejectNullEndDate() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventOverviewDTO(ID, TITLE, START_DATE, null, CITY, COUNTY, DISTRICT,
            ORGANIZERS, RACES, FILES
        )
    );
  }

  @Test
  void shouldRejectNullCity() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventOverviewDTO(ID, TITLE, START_DATE, END_DATE, null, COUNTY, DISTRICT,
            ORGANIZERS, RACES, FILES
        )
    );
  }

  @Test
  void shouldRejectBlankCity() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventOverviewDTO(ID, TITLE, START_DATE, END_DATE, "", COUNTY, DISTRICT,
            ORGANIZERS, RACES, FILES
        )
    );
  }

  @Test
  void shouldRejectNullCounty() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventOverviewDTO(ID, TITLE, START_DATE, END_DATE, CITY, null, DISTRICT,
            ORGANIZERS, RACES, FILES
        )
    );
  }

  @Test
  void shouldRejectBlankCounty() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventOverviewDTO(ID, TITLE, START_DATE, END_DATE, CITY, " ", DISTRICT,
            ORGANIZERS, RACES, FILES
        )
    );
  }

  @Test
  void shouldRejectNullDistrict() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventOverviewDTO(ID, TITLE, START_DATE, END_DATE, CITY, COUNTY, null,
            ORGANIZERS, RACES, FILES
        )
    );
  }

  @Test
  void shouldRejectBlankDistrict() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventOverviewDTO(ID, TITLE, START_DATE, END_DATE, CITY, COUNTY, "\t",
            ORGANIZERS, RACES, FILES
        )
    );
  }

  @Test
  void shouldRejectNullOrganizers() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventOverviewDTO(ID, TITLE, START_DATE, END_DATE, CITY, COUNTY, DISTRICT,
            null, RACES, FILES
        )
    );
  }

  @Test
  void shouldRejectNullRaces() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventOverviewDTO(ID, TITLE, START_DATE, END_DATE, CITY, COUNTY, DISTRICT,
            ORGANIZERS, null, FILES
        )
    );
  }

  @Test
  void shouldRejectNullFiles() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventOverviewDTO(ID, TITLE, START_DATE, END_DATE, CITY, COUNTY, DISTRICT,
            ORGANIZERS, RACES, null
        )
    );
  }
}
