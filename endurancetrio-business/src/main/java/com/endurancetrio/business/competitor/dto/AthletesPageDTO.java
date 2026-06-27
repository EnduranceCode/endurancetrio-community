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

import com.endurancetrio.business.common.dto.PaginationDTO;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * {@link AthletesPageDTO} carries a page of {@link AthleteDTO athlete data} together with
 * {@link PaginationDTO pagination metadata}. It is returned by the service layer so that
 * controllers can render both the athlete list and pagination controls without depending on Spring
 * Data's {@link Page}.
 *
 * @param athletes   the list of {@link AthleteDTO} for the current page
 * @param pagination the {@link PaginationDTO} with pagination state
 */
public record AthletesPageDTO(List<AthleteDTO> athletes, PaginationDTO pagination) {

  public AthletesPageDTO {
    if (athletes == null) {
      throw new IllegalArgumentException("athletes must not be null");
    }
    if (pagination == null) {
      throw new IllegalArgumentException("pagination must not be null");
    }
  }
}
