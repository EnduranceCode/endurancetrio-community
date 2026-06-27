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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link RaceDTO} DTO.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class RaceDTOTest {

  private static final Long ID = 1L;
  private static final String TITLE = "Test Race";
  private static final String SUBTITLE = "Elite";
  private static final LocalDate DATE = LocalDate.of(2026, Month.JUNE, 1);
  private static final LocalTime TIME = LocalTime.of(9, 0);
  private static final List<String> SPORTS = List.of("TRIATHLON");
  private static final List<String> DISTANCE_TYPES = List.of("SPRINT");
  private static final String RACE_TYPE_GROUP = "TRIATHLON";
  private static final DistanceMetadataDTO DISTANCE_METADATA = new DistanceMetadataDTO(
      List.of(new DistanceLegDTO("SWIM", 1500), new DistanceLegDTO("RUN", 10000)));

  private RaceDTO underTest;

  @BeforeEach
  void setUp() {
    underTest = new RaceDTO(ID, TITLE, SUBTITLE, DATE, TIME, SPORTS, DISTANCE_TYPES,
        RACE_TYPE_GROUP, DISTANCE_METADATA, null
    );
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(ID, underTest.id());
    assertEquals(TITLE, underTest.title());
    assertEquals(SUBTITLE, underTest.subtitle());
    assertEquals(DATE, underTest.date());
    assertEquals(TIME, underTest.time());
    assertEquals(SPORTS, underTest.sports());
    assertEquals(DISTANCE_TYPES, underTest.distanceTypes());
    assertEquals(RACE_TYPE_GROUP, underTest.raceTypeGroup());
    assertEquals(DISTANCE_METADATA, underTest.distanceMetadata());
  }

  @Test
  void shouldRejectNullSubtitle() {
    assertThrows(IllegalArgumentException.class,
        () -> new RaceDTO(ID, TITLE, null, DATE, TIME, SPORTS, DISTANCE_TYPES, RACE_TYPE_GROUP,
            DISTANCE_METADATA, null
        )
    );
  }

  @Test
  void shouldRejectBlankSubtitle() {
    assertThrows(IllegalArgumentException.class,
        () -> new RaceDTO(ID, TITLE, " ", DATE, TIME, SPORTS, DISTANCE_TYPES, RACE_TYPE_GROUP,
            DISTANCE_METADATA, null
        )
    );
  }

  @Test
  void timeCanBeNull() {
    RaceDTO dto = new RaceDTO(ID, TITLE, SUBTITLE, DATE, null, SPORTS, DISTANCE_TYPES,
        RACE_TYPE_GROUP, DISTANCE_METADATA, null
    );
    assertNull(dto.time());
  }

  @Test
  void distanceMetadataCanBeNull() {
    RaceDTO dto = new RaceDTO(ID, TITLE, SUBTITLE, DATE, TIME, SPORTS, DISTANCE_TYPES,
        RACE_TYPE_GROUP, null, null
    );
    assertNull(dto.distanceMetadata());
  }

  @Test
  void shouldRejectNullTitle() {
    assertThrows(IllegalArgumentException.class,
        () -> new RaceDTO(ID, null, SUBTITLE, DATE, TIME, SPORTS, DISTANCE_TYPES, RACE_TYPE_GROUP,
            DISTANCE_METADATA, null
        )
    );
  }

  @Test
  void shouldRejectBlankTitle() {
    assertThrows(IllegalArgumentException.class,
        () -> new RaceDTO(ID, " ", SUBTITLE, DATE, TIME, SPORTS, DISTANCE_TYPES, RACE_TYPE_GROUP,
            DISTANCE_METADATA, null
        )
    );
  }

  @Test
  void shouldRejectNullDate() {
    assertThrows(IllegalArgumentException.class,
        () -> new RaceDTO(ID, TITLE, SUBTITLE, null, TIME, SPORTS, DISTANCE_TYPES, RACE_TYPE_GROUP,
            DISTANCE_METADATA, null
        )
    );
  }

  @Test
  void shouldRejectNullSports() {
    assertThrows(IllegalArgumentException.class,
        () -> new RaceDTO(ID, TITLE, SUBTITLE, DATE, TIME, null, DISTANCE_TYPES, RACE_TYPE_GROUP,
            DISTANCE_METADATA, null
        )
    );
  }

  @Test
  void shouldRejectNullDistanceTypes() {
    assertThrows(IllegalArgumentException.class,
        () -> new RaceDTO(ID, TITLE, SUBTITLE, DATE, TIME, SPORTS, null, RACE_TYPE_GROUP,
            DISTANCE_METADATA, null
        )
    );
  }

  @Test
  void shouldRejectNullRaceTypeGroup() {
    assertThrows(IllegalArgumentException.class,
        () -> new RaceDTO(ID, TITLE, SUBTITLE, DATE, TIME, SPORTS, DISTANCE_TYPES, null,
            DISTANCE_METADATA, null
        )
    );
  }

  @Test
  void shouldRejectBlankRaceTypeGroup() {
    assertThrows(IllegalArgumentException.class,
        () -> new RaceDTO(ID, TITLE, SUBTITLE, DATE, TIME, SPORTS, DISTANCE_TYPES, "",
            DISTANCE_METADATA, null
        )
    );
  }
}
