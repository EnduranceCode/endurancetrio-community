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
 * EVEN IF WE HAVE BEEN INFORMED OF THE POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.business.event.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.endurancetrio.business.event.dto.OrganizerDTO;
import com.endurancetrio.data.event.model.entity.Organizer;
import com.endurancetrio.data.event.model.enumerator.OrganizerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OrganizerMapperTest {

  private static final Long ID = 1L;
  private static final String NAME = "Test Club";
  private static final OrganizerType ORGANIZER_TYPE = OrganizerType.CLUB;
  private static final String CITY = "Lisbon";
  private static final String COUNTY = "Lisbon";
  private static final String DISTRICT = "Lisbon";

  private Organizer entityTest;

  private OrganizerMapper underTest;

  @BeforeEach
  void setUp() {
    underTest = new OrganizerMapper();

    entityTest = new Organizer();
    entityTest.setId(ID);
    entityTest.setName(NAME);
    entityTest.setOrganizerType(ORGANIZER_TYPE);
    entityTest.setCity(CITY);
    entityTest.setCounty(COUNTY);
    entityTest.setDistrict(DISTRICT);
  }

  @Test
  void mapEntity() {
    OrganizerDTO result = underTest.map(entityTest);

    assertNotNull(result);
    assertEquals(ID, result.id());
    assertEquals(NAME, result.name());
    assertEquals(ORGANIZER_TYPE.name(), result.organizerType());
    assertEquals(CITY, result.city());
    assertEquals(COUNTY, result.county());
    assertEquals(DISTRICT, result.district());
  }

  @Test
  void mapNullEntity() {
    OrganizerDTO result = underTest.map((Organizer) null);

    assertNull(result);
  }
}
