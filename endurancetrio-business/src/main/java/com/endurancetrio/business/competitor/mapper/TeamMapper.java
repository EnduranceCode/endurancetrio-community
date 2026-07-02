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

import com.endurancetrio.business.competitor.dto.TeamDTO;
import com.endurancetrio.data.competitor.model.entity.Team;
import org.springframework.stereotype.Component;

/**
 * The {@link TeamMapper} provides mapping between {@link Team} entities and {@link TeamDTO}
 * objects.
 */
@Component
public class TeamMapper {

  /**
   * Maps a {@link Team} entity to a {@link TeamDTO}.
   * <p>
   * If the entity is {@code null}, a custom {@code teamName} may be provided for display purposes
   * when no formal team record exists in the database.
   *
   * @param entity   the Team entity to map; may be null
   * @param teamName the team name to use when the entity is null; may be null
   * @return the corresponding TeamDTO, or {@code null} if the entity is {@code null}
   */
  public TeamDTO map(Team entity, String teamName) {
    if (entity == null) {
      return null;
    }

    return new TeamDTO(entity.getId(), entity.getFullName(), teamName, entity.getCity(),
        entity.getCounty(), entity.getDistrict()
    );
  }
}
