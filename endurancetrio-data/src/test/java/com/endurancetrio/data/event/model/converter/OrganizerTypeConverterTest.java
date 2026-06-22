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

package com.endurancetrio.data.event.model.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.endurancetrio.data.event.model.enumerator.OrganizerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link OrganizerTypeConverter} converter.
 */
class OrganizerTypeConverterTest {

  private OrganizerTypeConverter underTest;

  @BeforeEach
  void setUp() {
    underTest = new OrganizerTypeConverter();
  }

  @Test
  void shouldConvertOrganizerTypeToDatabaseColumn() {
    assertEquals("CLUB", underTest.convertToDatabaseColumn(OrganizerType.CLUB));
    assertEquals("PRIVATE", underTest.convertToDatabaseColumn(OrganizerType.PRIVATE));
    assertEquals("PUBLIC", underTest.convertToDatabaseColumn(OrganizerType.PUBLIC));
  }

  @Test
  void shouldReturnNullWhenConvertingNullOrganizerTypeToDatabaseColumn() {
    assertNull(underTest.convertToDatabaseColumn(null));
  }

  @Test
  void shouldConvertCodeToOrganizerType() {
    assertEquals(OrganizerType.CLUB, underTest.convertToEntityAttribute("CLUB"));
    assertEquals(OrganizerType.PRIVATE, underTest.convertToEntityAttribute("PRIVATE"));
    assertEquals(OrganizerType.PUBLIC, underTest.convertToEntityAttribute("PUBLIC"));
  }

  @Test
  void shouldThrowExceptionForInvalidCode() {
    assertThrows(IllegalArgumentException.class,
        () -> underTest.convertToEntityAttribute("INVALID"));
  }
}
