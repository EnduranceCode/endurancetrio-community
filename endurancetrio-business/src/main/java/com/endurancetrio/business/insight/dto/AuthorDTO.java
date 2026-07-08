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

package com.endurancetrio.business.insight.dto;

import com.endurancetrio.data.competitor.model.entity.Athlete;

/**
 * {@link AuthorDTO} carries the author profile data for the Insights section.
 *
 * @param id              the unique identifier of the author
 * @param knownName       the display name of the author (must be non-null and non-blank)
 * @param bio             the optional biography of the author
 * @param profileFileName the optional file name of the author's profile image
 * @param athleteId       the optional identifier of the {@link Athlete Athlete} linked to this
 *                        author, used in the frontend to connect author and athlete profiles
 */
public record AuthorDTO(Long id, String knownName, String bio, String profileFileName,
                        Long athleteId) {

  public AuthorDTO {
    if (knownName == null || knownName.isBlank()) {
      throw new IllegalArgumentException("knownName must not be null or blank");
    }
  }
}
