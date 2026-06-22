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
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.endurancetrio.data.event.model.enumerator.WetsuitRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Unit test for the {@link WetSuitRuleConverter} converter.
 */
class WetSuitRuleConverterTest {

  private WetSuitRuleConverter underTest;

  @BeforeEach
  void setUp() {
    underTest = new WetSuitRuleConverter();
  }

  @Test
  void shouldConvertWetsuitRuleToDatabaseColumn() {
    assertEquals("FORBIDDEN", underTest.convertToDatabaseColumn(WetsuitRule.FORBIDDEN));
    assertEquals("MANDATORY", underTest.convertToDatabaseColumn(WetsuitRule.MANDATORY));
    assertEquals("OPTIONAL", underTest.convertToDatabaseColumn(WetsuitRule.OPTIONAL));
    assertEquals("UNKNOWN", underTest.convertToDatabaseColumn(WetsuitRule.UNKNOWN));
  }

  @Test
  void shouldReturnUnknownWhenConvertingNullWetsuitRuleToDatabaseColumn() {
    assertEquals("UNKNOWN", underTest.convertToDatabaseColumn(null));
  }

  @Test
  void shouldConvertCodeToWetsuitRule() {
    assertEquals(WetsuitRule.FORBIDDEN, underTest.convertToEntityAttribute("FORBIDDEN"));
    assertEquals(WetsuitRule.MANDATORY, underTest.convertToEntityAttribute("MANDATORY"));
    assertEquals(WetsuitRule.OPTIONAL, underTest.convertToEntityAttribute("OPTIONAL"));
    assertEquals(WetsuitRule.UNKNOWN, underTest.convertToEntityAttribute("UNKNOWN"));
  }

  @Test
  void shouldThrowExceptionForInvalidCode() {
    assertThrows(IllegalArgumentException.class,
        () -> underTest.convertToEntityAttribute("INVALID"));
  }
}
