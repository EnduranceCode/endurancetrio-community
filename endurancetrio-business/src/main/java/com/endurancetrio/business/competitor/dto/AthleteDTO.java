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

package com.endurancetrio.business.competitor.dto;

import com.endurancetrio.data.competitor.model.enumerator.AthleteGender;
import com.endurancetrio.data.competitor.model.enumerator.Country;

/**
 * {@link AthleteDTO} represents an athlete with its basic information.
 *
 * @param id          the unique identifier of the athlete
 * @param longName    the athlete's longest available name
 * @param birthName   the athlete's name at birth (may be null)
 * @param knownName   the athlete's known or preferred name
 * @param gender      the athlete's gender
 * @param country     the athlete's country of representation (may be null)
 * @param yearOfBirth the athlete's year of birth (may be null)
 */
public record AthleteDTO(Long id, String longName, String birthName, String knownName,
                         AthleteGender gender, Country country, Integer yearOfBirth) {

  public AthleteDTO {
    if (longName == null || longName.isBlank()) {
      throw new IllegalArgumentException("longName must not be null or blank");
    }
    if (knownName == null || knownName.isBlank()) {
      throw new IllegalArgumentException("knownName must not be null or blank");
    }
    if (gender == null) {
      throw new IllegalArgumentException("gender must not be null");
    }
  }
}
