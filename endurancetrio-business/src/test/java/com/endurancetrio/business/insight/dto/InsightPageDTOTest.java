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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.endurancetrio.business.common.dto.PaginationDTO;
import com.endurancetrio.business.insight.fixtures.ArticleDTOFixture;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InsightPageDTOTest {

  private static final List<ArticleDTO> ARTICLES = List.of(ArticleDTOFixture.standard());
  private static final PaginationDTO PAGINATION = new PaginationDTO(0, 10, 55, true, false);

  private InsightPageDTO underTest;

  @BeforeEach
  void setUp() {
    underTest = new InsightPageDTO(ARTICLES, PAGINATION);
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(ARTICLES, underTest.articles());
    assertEquals(PAGINATION, underTest.pagination());
  }

  @Test
  void shouldRejectNullArticles() {
    assertThrows(IllegalArgumentException.class, () -> new InsightPageDTO(null, PAGINATION));
  }

  @Test
  void shouldRejectNullPagination() {
    assertThrows(IllegalArgumentException.class, () -> new InsightPageDTO(ARTICLES, null));
  }

  @Test
  void articlesListCanBeEmpty() {
    InsightPageDTO dto = new InsightPageDTO(List.of(), PAGINATION);
    assertEquals(List.of(), dto.articles());
  }
}
