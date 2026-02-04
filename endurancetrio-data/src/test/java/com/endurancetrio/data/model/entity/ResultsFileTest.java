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

package com.endurancetrio.data.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.endurancetrio.data.model.enumerator.OrganizerType;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link ResultsFileTest} entity.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is
 * to establish a testing culture from the very beginning of the project. It serves as a reminder
 * that every part of the application should be testable and that tests should always be present.
 */
class ResultsFileTest {

  ResultsFile underTest;

  Organizer testOrganizer;
  Event testEvent;

  @BeforeEach
  void setUp() {
    testOrganizer = new Organizer();
    testOrganizer.setId(1L);
    testOrganizer.setName("Câmara Municipal de Grândola");
    testOrganizer.setDistrict("Setúbal");
    testOrganizer.setCounty("Grândola");
    testOrganizer.setCity("Grândola");
    testOrganizer.setOrganizerType(OrganizerType.PUBLIC);

    Set<Organizer> organizers = new HashSet<>();
    organizers.add(testOrganizer);

    testEvent = new Event();
    testEvent.setId(1L);
    testEvent.setTitle("XVI Duatlo Jovem de Grândola");
    testEvent.setStartDate(LocalDate.parse("2010-03-06"));
    testEvent.setEndDate(LocalDate.parse("2010-03-07"));
    testEvent.setOrganizers(organizers);

    underTest = new ResultsFile();
    underTest.setId(1L);
    underTest.setTitle("XVI Duatlo Jovem de Grândola");
    underTest.setSubtitle("Benjamins Masculinos");
    underTest.setFileName("20100306FTP001-003A-01.pdf");
    underTest.setRevision(1);
    underTest.setActive(true);
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(1L, underTest.getId());
    assertEquals("XVI Duatlo Jovem de Grândola", underTest.getTitle());
    assertEquals("Benjamins Masculinos", underTest.getSubtitle());
    assertEquals("20100306FTP001-003A-01.pdf", underTest.getFileName());
    assertEquals(1, underTest.getRevision());
    assertTrue(underTest.getActive());
  }
}
