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

package com.endurancetrio.business.newsletter.dto;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class NewsletterSubscriptionDTOTest {

  private static ValidatorFactory validatorFactory;
  private static Validator validator;

  @BeforeAll
  static void setUpAll() {
    validatorFactory = Validation.buildDefaultValidatorFactory();
    validator = validatorFactory.getValidator();
  }

  @AfterAll
  static void tearDownAll() {
    if (validatorFactory != null) {
      validatorFactory.close();
    }
  }

  @ParameterizedTest
  @CsvSource({
      "John,    john@example.com, ",
      "João,    joao@example.pt,  ",
      "A,       a@b.co,           ",
  })
  void validDto(String firstName, String email) {
    var dto = new NewsletterSubscriptionDTO(firstName, email);
    Set<ConstraintViolation<NewsletterSubscriptionDTO>> violations = validator.validate(dto);

    assertTrue(violations.isEmpty());
  }

  @ParameterizedTest
  @CsvSource({
      ",     john@example.com, firstName",
      "'',   john@example.com, firstName",
      "' ',  john@example.com, firstName",
      "John, ,                 email",
      "John, '',               email",
      "John, ' ',              email",
      "John, not-an-email,     email",
  })
  void invalidDto(String firstName, String email, String expectedViolationProperty) {
    var dto = new NewsletterSubscriptionDTO(firstName, email);
    Set<ConstraintViolation<NewsletterSubscriptionDTO>> violations = validator.validate(dto);

    assertFalse(violations.isEmpty());
    assertTrue(violations.stream()
        .map(v -> v.getPropertyPath().toString())
        .anyMatch(expectedViolationProperty::equals)
    );
  }
}
