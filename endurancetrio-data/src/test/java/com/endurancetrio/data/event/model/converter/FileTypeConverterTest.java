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

import com.endurancetrio.data.event.model.enumerator.FileType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link FileTypeConverter} converter.
 */
class FileTypeConverterTest {

  private FileTypeConverter underTest;

  @BeforeEach
  void setUp() {
    underTest = new FileTypeConverter();
  }

  @Test
  void shouldConvertFileTypeToDatabaseColumn() {
    assertEquals("COURSE_MAPS", underTest.convertToDatabaseColumn(FileType.COURSE_MAPS));
    assertEquals("COVER_IMAGE", underTest.convertToDatabaseColumn(FileType.COVER_IMAGE));
    assertEquals("GUIDE", underTest.convertToDatabaseColumn(FileType.GUIDE));
    assertEquals("POSTER", underTest.convertToDatabaseColumn(FileType.POSTER));
    assertEquals("RULES", underTest.convertToDatabaseColumn(FileType.RULES));
    assertEquals("START_LIST", underTest.convertToDatabaseColumn(FileType.START_LIST));
  }

  @Test
  void shouldReturnNullWhenConvertingNullFileTypeToDatabaseColumn() {
    assertNull(underTest.convertToDatabaseColumn(null));
  }

  @Test
  void shouldConvertCodeToFileType() {
    assertEquals(FileType.COURSE_MAPS, underTest.convertToEntityAttribute("COURSE_MAPS"));
    assertEquals(FileType.COVER_IMAGE, underTest.convertToEntityAttribute("COVER_IMAGE"));
    assertEquals(FileType.GUIDE, underTest.convertToEntityAttribute("GUIDE"));
    assertEquals(FileType.POSTER, underTest.convertToEntityAttribute("POSTER"));
    assertEquals(FileType.RULES, underTest.convertToEntityAttribute("RULES"));
    assertEquals(FileType.START_LIST, underTest.convertToEntityAttribute("START_LIST"));
  }

  @Test
  void shouldThrowExceptionForInvalidCode() {
    assertThrows(IllegalArgumentException.class,
        () -> underTest.convertToEntityAttribute("INVALID"));
  }
}
