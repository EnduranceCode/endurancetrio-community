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

package com.endurancetrio.data.competitor.fixtures;

import com.endurancetrio.data.competitor.model.entity.Athlete;
import com.endurancetrio.data.competitor.model.entity.RelayEntry;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.event.model.enumerator.GenderCategory;

/**
 * Fixture class providing pre-configured {@link RelayEntry} entity instances for unit tests.
 */
public class RelayEntryFixture {

  public static final Long STANDARD_ID = 1L;
  public static final String STANDARD_FULL_NAME = "Relay Cavaleiro | Bello | Carvalho";
  public static final GenderCategory STANDARD_GENDER_CATEGORY = GenderCategory.MIXED;
  public static final AgeGroup STANDARD_AGE_GROUP = AgeGroup.OPEN;
  public static final Long STANDARD_MEMBER_ID = 1L;

  private RelayEntryFixture() {
  }

  public static RelayEntry standard() {
    RelayEntry entity = new RelayEntry();
    entity.setId(STANDARD_ID);
    entity.setFullName(STANDARD_FULL_NAME);
    entity.setGenderCategory(STANDARD_GENDER_CATEGORY);
    entity.setAgeGroup(STANDARD_AGE_GROUP);
    return entity;
  }

  public static RelayEntry withMembers() {
    RelayEntry entity = standard();
    Athlete member = new Athlete();
    member.setId(STANDARD_MEMBER_ID);
    entity.addAthlete(member);
    return entity;
  }
}
