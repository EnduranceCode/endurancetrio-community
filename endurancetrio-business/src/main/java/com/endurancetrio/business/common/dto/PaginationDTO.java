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

package com.endurancetrio.business.common.dto;

import org.springframework.data.domain.Page;

/**
 * {@link PaginationDTO} is a generic pagination metadata record used to pass pagination state from
 * the service layer to the presentation layer without exposing Spring Data {@link Page} directly.
 *
 * @param pageNumber  the 0-based current page number
 * @param totalPages  the total number of pages
 * @param totalItems  the total number of items across all pages
 * @param hasPrevious whether a previous page exists
 * @param hasNext     whether a next page exists
 */
public record PaginationDTO(int pageNumber, int totalPages, long totalItems, boolean hasPrevious,
                            boolean hasNext) {

  public PaginationDTO {
    if (pageNumber < 0) {
      throw new IllegalArgumentException("pageNumber must not be negative");
    }
    if (totalPages < 0) {
      throw new IllegalArgumentException("totalPages must not be negative");
    }
    if (totalItems < 0) {
      throw new IllegalArgumentException("totalItems must not be negative");
    }
  }

  /**
   * Creates a {@link PaginationDTO} from a Spring Data {@link Page}.
   *
   * @param page the Spring Data {@link Page} to derive values from
   * @param <T>  the content type of the page
   * @return a new {@link PaginationDTO} with the pagination state of the given {@link Page}
   */
  public static <T> PaginationDTO from(Page<T> page) {
    return new PaginationDTO(page.getNumber(), page.getTotalPages(), page.getTotalElements(),
        page.hasPrevious(), page.hasNext()
    );
  }
}
