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

package com.endurancetrio.business.insight.mapper;

import com.endurancetrio.business.insight.dto.AuthorDTO;
import com.endurancetrio.data.common.model.entity.BaseEntity;
import com.endurancetrio.data.insight.model.entity.Author;
import java.util.Objects;
import java.util.Optional;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
public class AuthorMapper {

  /**
   * Maps an {@link Author} entity to an {@link AuthorDTO}.
   *
   * @param entity the Author entity to be mapped; must not be {@code null}
   * @return the corresponding AuthorDTO
   */
  public AuthorDTO map(@NonNull Author entity) {
    Objects.requireNonNull(entity, "Author must not be null");

    Long athleteId = Optional.ofNullable(entity.getAthlete()).map(BaseEntity::getId).orElse(null);

    return new AuthorDTO(entity.getId(), entity.getKnownName(), entity.getBio(),
        entity.getProfileFileName(), athleteId
    );
  }
}
