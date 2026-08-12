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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link SitemapEntry} DTO.
 */
class SitemapEntryTest {

  @Test
  void dtoShouldRetainValues() {
    SitemapEntry underTest = new SitemapEntry("https://endurancetrio.com/en/insights/how-to-train",
        "https://endurancetrio.com/pt/insights/how-to-train", "2026-08-12", 0.7
    );

    assertEquals("https://endurancetrio.com/en/insights/how-to-train", underTest.urlEn());
    assertEquals("https://endurancetrio.com/pt/insights/how-to-train", underTest.urlPt());
    assertEquals("2026-08-12", underTest.lastModified());
    assertEquals(0.7, underTest.priority());
  }

  @Test
  void shouldAcceptNullLastModified() {
    SitemapEntry underTest = new SitemapEntry("https://endurancetrio.com/en", "https://endurancetrio.com/pt", null,
        0.5
    );

    assertEquals("https://endurancetrio.com/en", underTest.urlEn());
    assertNull(underTest.lastModified());
  }

  @Test
  void shouldRejectNullUrlEn() {
    assertThrows(IllegalArgumentException.class,
        () -> new SitemapEntry(null, "https://endurancetrio.com/pt", null, 0.5)
    );
  }

  @Test
  void shouldRejectBlankUrlEn() {
    assertThrows(IllegalArgumentException.class,
        () -> new SitemapEntry(" ", "https://endurancetrio.com/pt", null, 0.5)
    );
  }

  @Test
  void shouldRejectNullUrlPt() {
    assertThrows(IllegalArgumentException.class,
        () -> new SitemapEntry("https://endurancetrio.com/en", null, null, 0.5)
    );
  }

  @Test
  void shouldRejectBlankUrlPt() {
    assertThrows(IllegalArgumentException.class,
        () -> new SitemapEntry("https://endurancetrio.com/en", " ", null, 0.5)
    );
  }
}
