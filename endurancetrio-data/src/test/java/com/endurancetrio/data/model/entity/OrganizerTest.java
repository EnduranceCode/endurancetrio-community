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

import com.endurancetrio.data.model.enumerator.OrganizerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link Organizer} entity.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is
 * to establish a testing culture from the very beginning of the project. It serves as a reminder
 * that every part of the application should be testable and that tests should always be present.
 */
class OrganizerTest {

  Organizer underTest;

  @BeforeEach
  void setUp() {
    underTest = new Organizer();
    underTest.setId(1L);
    underTest.setName("Câmara Municipal de Grândola");
    underTest.setDistrict("Setúbal");
    underTest.setCounty("Grândola");
    underTest.setCity("Grândola");
    underTest.setOrganizerType(OrganizerType.PUBLIC);
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(1L, underTest.getId());
    assertEquals("Câmara Municipal de Grândola", underTest.getName());
    assertEquals("Setúbal", underTest.getDistrict());
    assertEquals("Grândola", underTest.getCounty());
    assertEquals("Grândola", underTest.getCity());
    assertEquals(OrganizerType.PUBLIC, underTest.getOrganizerType());
  }
}
