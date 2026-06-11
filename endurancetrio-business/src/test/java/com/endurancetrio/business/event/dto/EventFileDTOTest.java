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

package com.endurancetrio.business.event.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link EventFileDTO} DTO.
 * <p>
 * This test may seem redundant since it only verify getters and setters, but its purpose is to
 * establish a testing culture from the very beginning of the project. It serves as a reminder that
 * every part of the application should be testable and that tests should always be present.
 */
class EventFileDTOTest {

  private static final Long ID = 1L;
  private static final String TITLE = "Event Guide";
  private static final String FILE_TYPE = "GUIDE";
  private static final String FILE_NAME = "20260601TST001-GUI001-01.pdf";

  private EventFileDTO underTest;

  @BeforeEach
  void setUp() {
    underTest = new EventFileDTO(ID, TITLE, FILE_TYPE, FILE_NAME);
  }

  @Test
  void dtoShouldRetainValues() {
    assertEquals(ID, underTest.id());
    assertEquals(TITLE, underTest.title());
    assertEquals(FILE_TYPE, underTest.fileType());
    assertEquals(FILE_NAME, underTest.fileName());
  }

  @Test
  void shouldRejectNullTitle() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventFileDTO(ID, null, FILE_TYPE, FILE_NAME)
    );
  }

  @Test
  void shouldRejectBlankTitle() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventFileDTO(ID, " ", FILE_TYPE, FILE_NAME)
    );
  }

  @Test
  void shouldRejectNullFileType() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventFileDTO(ID, TITLE, null, FILE_NAME)
    );
  }

  @Test
  void shouldRejectBlankFileType() {
    assertThrows(IllegalArgumentException.class, () -> new EventFileDTO(ID, TITLE, "", FILE_NAME));
  }

  @Test
  void shouldRejectNullFileName() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventFileDTO(ID, TITLE, FILE_TYPE, null)
    );
  }

  @Test
  void shouldRejectBlankFileName() {
    assertThrows(IllegalArgumentException.class,
        () -> new EventFileDTO(ID, TITLE, FILE_TYPE, "\t")
    );
  }
}
