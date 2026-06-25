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

package com.endurancetrio.data.competitor.model.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.endurancetrio.data.competitor.model.enumerator.AthleteGender;
import com.endurancetrio.data.competitor.model.enumerator.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class AthleteTest {

  private Athlete underTest;

  @BeforeEach
  void setUp() {
    underTest = new Athlete();
    underTest.setId(1L);
    underTest.setLongName("Paulo José Paula Carvalho");
    underTest.setKnownName("Paulo Paula Carvalho");
    underTest.setGender(AthleteGender.MALE);
    underTest.setCountry(Country.POR);
    underTest.setYearOfBirth(1961);
  }

  @Test
  void entityShouldRetainValues() {
    assertEquals(1L, underTest.getId());
    assertEquals("Paulo José Paula Carvalho", underTest.getLongName());
    assertNull(underTest.getBirthName());
    assertEquals("Paulo Paula Carvalho", underTest.getKnownName());
    assertEquals(AthleteGender.MALE, underTest.getGender());
    assertEquals(Country.POR, underTest.getCountry());
    assertEquals(1961, underTest.getYearOfBirth());
  }
}
