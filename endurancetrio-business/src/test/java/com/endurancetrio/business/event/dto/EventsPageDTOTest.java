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

import com.endurancetrio.business.common.dto.PaginationDTO;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link EventsPageDTO} DTO.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class EventsPageDTOTest {

  private static final List<EventDTO> EVENTS = List.of(
      new EventDTO(1L, "Event One", LocalDate.of(2026, 6, 1), LocalDate.of(2026, 6, 1),
          "City", "County", "District", List.of("TRIATHLON"))
  );
  private static final PaginationDTO PAGINATION = new PaginationDTO(0, 10, 55, true, false);

  private EventsPageDTO underTest;

  @BeforeEach
  void setUp() {
    underTest = new EventsPageDTO(EVENTS, PAGINATION);
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(EVENTS, underTest.events());
    assertEquals(PAGINATION, underTest.pagination());
  }

  @Test
  void shouldRejectNullEvents() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventsPageDTO(null, PAGINATION)
    );
  }

  @Test
  void shouldRejectNullPagination() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventsPageDTO(EVENTS, null)
    );
  }

  @Test
  void eventsListCanBeEmpty() {
    EventsPageDTO dto = new EventsPageDTO(List.of(), PAGINATION);
    assertEquals(List.of(), dto.events());
  }
}
