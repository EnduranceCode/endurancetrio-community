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

import com.endurancetrio.business.common.dto.PaginationDTO;
import java.util.List;
import org.springframework.data.domain.Page;

/**
 * {@link InsightPageDTO} carries a page of {@link ArticleDTO article data} together with
 * {@link PaginationDTO pagination metadata}. It is returned by the service layer so that
 * controllers can render both the article list and pagination controls without depending on Spring
 * Data's {@link Page}.
 *
 * @param articles   the list of {@link ArticleDTO} for the current page, must not be null
 * @param pagination the {@link PaginationDTO} with pagination state, must not be null
 */
public record InsightPageDTO(List<ArticleDTO> articles, PaginationDTO pagination) {

  public InsightPageDTO {
    if (articles == null) {
      throw new IllegalArgumentException("articles must not be null");
    }
    if (pagination == null) {
      throw new IllegalArgumentException("pagination must not be null");
    }
  }
}
