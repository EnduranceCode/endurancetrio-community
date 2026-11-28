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

package com.endurancetrio.app.common.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import org.junit.jupiter.api.Test;

class WebUtilsTest {

  private static final Locale LOCALE_PORTUGUESE = Locale.of("pt", "PT");

  @Test
  void fromLanguageShouldReturnPortugueseForLowercasePt() {
    assertEquals(LOCALE_PORTUGUESE, WebUtils.fromLanguage("pt"));
  }

  @Test
  void fromLanguageShouldReturnPortugueseForUppercasePt() {
    assertEquals(LOCALE_PORTUGUESE, WebUtils.fromLanguage("PT"));
  }

  @Test
  void fromLanguageShouldReturnEnglishForLowercaseEn() {
    assertEquals(Locale.ENGLISH, WebUtils.fromLanguage("en"));
  }

  @Test
  void fromLanguageShouldReturnEnglishForUppercaseEn() {
    assertEquals(Locale.ENGLISH, WebUtils.fromLanguage("EN"));
  }

  @Test
  void fromLanguageShouldReturnEnglishForUnexpectedValue() {
    assertEquals(Locale.ENGLISH, WebUtils.fromLanguage("fr"));
  }

  @Test
  void fromLanguageShouldReturnEnglishForBlankValue() {
    assertEquals(Locale.ENGLISH, WebUtils.fromLanguage(" "));
  }

  @Test
  void fromLanguageShouldReturnEnglishForNullValue() {
    assertEquals(Locale.ENGLISH, WebUtils.fromLanguage(null));
  }
}
