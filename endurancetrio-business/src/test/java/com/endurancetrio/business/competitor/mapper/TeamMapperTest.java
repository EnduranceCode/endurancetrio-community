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

package com.endurancetrio.business.competitor.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.endurancetrio.business.competitor.dto.TeamDTO;
import com.endurancetrio.business.competitor.fixtures.RelayEntryFixture;
import com.endurancetrio.business.competitor.fixtures.TeamFixture;
import com.endurancetrio.data.competitor.model.entity.RelayEntry;
import com.endurancetrio.data.competitor.model.entity.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TeamMapperTest {

  private Team entity;
  private TeamMapper underTest;

  @BeforeEach
  void setUp() {
    underTest = new TeamMapper();
    entity = TeamFixture.standard();
  }

  @Test
  void mapNullEntity() {
    assertNull(underTest.map(null));
  }

  @Test
  void mapEntity() {
    TeamDTO result = underTest.map(entity);
    assertNotNull(result);
    assertEquals(TeamFixture.STANDARD_ID, result.id());
    assertEquals(TeamFixture.STANDARD_FULL_NAME, result.fullName());
    assertEquals(TeamFixture.STANDARD_SHORT_NAME, result.shortName());
    assertEquals(TeamFixture.STANDARD_CITY, result.city());
    assertEquals(TeamFixture.STANDARD_COUNTY, result.county());
    assertEquals(TeamFixture.STANDARD_DISTRICT, result.district());
  }

  @Test
  void mapRelayEntryPolymorphically() {
    RelayEntry relayEntry = RelayEntryFixture.standard();

    TeamDTO result = underTest.map(relayEntry);

    assertNotNull(result);
    assertEquals(RelayEntryFixture.STANDARD_ID, result.id());
    assertEquals(RelayEntryFixture.STANDARD_FULL_NAME, result.fullName());
  }
}
