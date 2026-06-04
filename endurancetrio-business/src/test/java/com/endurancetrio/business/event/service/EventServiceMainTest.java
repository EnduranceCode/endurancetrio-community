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

package com.endurancetrio.business.event.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import com.endurancetrio.data.event.repository.EventRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EventServiceMainTest {

  private static final List<Integer> ALL_YEARS = List.of(1989, 1988, 1987, 1986, 1985, 1984);

  @Mock
  EventRepository eventRepository;

  EventServiceMain eventService;

  @BeforeEach
  void setUp() {
    eventService = new EventServiceMain(eventRepository);
  }

  @Test
  void getEventYearsShouldReturnAllYears() {
    when(eventRepository.findDistinctYears()).thenReturn(ALL_YEARS);

    List<Integer> result = eventService.getEventYears();

    assertNotNull(result);
    assertEquals(ALL_YEARS, result);
  }

  @Test
  void getEventYearsShouldReturnEmptyWhenNoYears() {
    when(eventRepository.findDistinctYears()).thenReturn(List.of());

    List<Integer> result = eventService.getEventYears();

    assertNotNull(result);
    assertEquals(List.of(), result);
  }
}
