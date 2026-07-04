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

import com.endurancetrio.data.event.model.enumerator.ResultStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link ResultStatusConverter} converter.
 */
class ResultStatusConverterTest {

  private ResultStatusConverter underTest;

  @BeforeEach
  void setUp() {
    underTest = new ResultStatusConverter();
  }

  @Test
  void shouldConvertResultStatusToDatabaseColumn() {
    assertEquals("UNKNOWN", underTest.convertToDatabaseColumn(ResultStatus.UNKNOWN));
    assertEquals("PENDING", underTest.convertToDatabaseColumn(ResultStatus.PENDING));
    assertEquals("INCOMPLETE", underTest.convertToDatabaseColumn(ResultStatus.INCOMPLETE));
    assertEquals("COMPLETE", underTest.convertToDatabaseColumn(ResultStatus.COMPLETE));
    assertEquals("EMPTY", underTest.convertToDatabaseColumn(ResultStatus.EMPTY));
  }

  @Test
  void shouldReturnNullWhenConvertingNullResultStatusToDatabaseColumn() {
    assertNull(underTest.convertToDatabaseColumn(null));
  }

  @Test
  void shouldConvertCodeToResultStatus() {
    assertEquals(ResultStatus.UNKNOWN, underTest.convertToEntityAttribute("UNKNOWN"));
    assertEquals(ResultStatus.PENDING, underTest.convertToEntityAttribute("PENDING"));
    assertEquals(ResultStatus.INCOMPLETE, underTest.convertToEntityAttribute("INCOMPLETE"));
    assertEquals(ResultStatus.COMPLETE, underTest.convertToEntityAttribute("COMPLETE"));
    assertEquals(ResultStatus.EMPTY, underTest.convertToEntityAttribute("EMPTY"));
  }

  @Test
  void shouldThrowExceptionForInvalidCode() {
    assertThrows(IllegalArgumentException.class,
        () -> underTest.convertToEntityAttribute("INVALID")
    );
  }
}
