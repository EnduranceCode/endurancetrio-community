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
 * EVEN IF HAVE BEEN INFORMED OF THEIR POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.data.competitor.model.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.endurancetrio.data.competitor.model.enumerator.ParaClass;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link ParaClassConverter} converter.
 */
class ParaClassConverterTest {

  private ParaClassConverter underTest;

  @BeforeEach
  void setUp() {
    underTest = new ParaClassConverter();
  }

  @Test
  void shouldConvertParaClassToDatabaseColumn() {
    assertEquals("PTVI", underTest.convertToDatabaseColumn(ParaClass.WT_PTVI));
    assertEquals("PTWC", underTest.convertToDatabaseColumn(ParaClass.WT_PTWC));
    assertEquals("PTS4", underTest.convertToDatabaseColumn(ParaClass.WT_PTS4));
  }

  @Test
  void shouldReturnNullWhenConvertingNullParaClassToDatabaseColumn() {
    assertNull(underTest.convertToDatabaseColumn(null));
  }

  @Test
  void shouldConvertCodeToParaClass() {
    assertEquals(ParaClass.WT_PTVI, underTest.convertToEntityAttribute("PTVI"));
    assertEquals(ParaClass.WT_PTWC, underTest.convertToEntityAttribute("PTWC"));
    assertEquals(ParaClass.WT_PTS4, underTest.convertToEntityAttribute("PTS4"));
  }

  @Test
  void shouldThrowExceptionForInvalidCode() {
    assertThrows(IllegalArgumentException.class,
        () -> underTest.convertToEntityAttribute("INVALID"));
  }
}
