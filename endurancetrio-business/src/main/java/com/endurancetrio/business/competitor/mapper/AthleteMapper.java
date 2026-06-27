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

import com.endurancetrio.business.competitor.dto.AthleteDTO;
import com.endurancetrio.data.competitor.model.entity.Athlete;
import org.springframework.stereotype.Component;

/**
 * The {@link AthleteMapper} provides mapping between {@link Athlete} entities and
 * {@link AthleteDTO} objects.
 */
@Component
public class AthleteMapper {

  /**
   * Maps an {@link Athlete} entity to an {@link AthleteDTO}.
   *
   * @param entity the Athlete entity to be mapped
   * @return the corresponding AthleteDTO, or {@code null} if the entity is {@code null}
   */
  public AthleteDTO mapToAthleteDTO(Athlete entity) {
    if (entity == null) {
      return null;
    }

    return new AthleteDTO(entity.getId(), entity.getLongName(), entity.getBirthName(),
        entity.getKnownName(), entity.getGender(), entity.getCountry(), entity.getYearOfBirth()
    );
  }
}
