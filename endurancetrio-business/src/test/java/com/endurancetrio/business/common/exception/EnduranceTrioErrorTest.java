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

package com.endurancetrio.business.common.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;
import org.junit.jupiter.api.Test;

class EnduranceTrioErrorTest {

  @Test
  void badRequestShouldHaveCode400() {
    assertEquals(400, EnduranceTrioError.BAD_REQUEST.getCode());
  }

  @Test
  void unauthorizedShouldHaveCode401() {
    assertEquals(401, EnduranceTrioError.UNAUTHORIZED.getCode());
  }

  @Test
  void notFoundShouldHaveCode404() {
    assertEquals(404, EnduranceTrioError.NOT_FOUND.getCode());
  }

  @Test
  void conflictShouldHaveCode409() {
    assertEquals(409, EnduranceTrioError.CONFLICT.getCode());
  }

  @Test
  void internalErrorShouldHaveCode500() {
    assertEquals(500, EnduranceTrioError.INTERNAL_ERROR.getCode());
  }

  @Test
  void fromCodeShouldReturnMatchingError() {
    Optional<EnduranceTrioError> result = EnduranceTrioError.fromCode(404);

    assertTrue(result.isPresent());
    assertEquals(EnduranceTrioError.NOT_FOUND, result.get());
  }

  @Test
  void fromCodeShouldReturnEmptyForUnknownCode() {
    Optional<EnduranceTrioError> result = EnduranceTrioError.fromCode(999);

    assertTrue(result.isEmpty());
  }

  @Test
  void fromCodeShouldFindBadRequest() {
    Optional<EnduranceTrioError> result = EnduranceTrioError.fromCode(400);

    assertTrue(result.isPresent());
    assertEquals(EnduranceTrioError.BAD_REQUEST, result.get());
  }

  @Test
  void fromCodeShouldFindInternalError() {
    Optional<EnduranceTrioError> result = EnduranceTrioError.fromCode(500);

    assertTrue(result.isPresent());
    assertEquals(EnduranceTrioError.INTERNAL_ERROR, result.get());
  }

  @Test
  void badRequestShouldHaveMessage() {
    assertEquals("The request was made with invalid or incomplete data",
        EnduranceTrioError.BAD_REQUEST.getMessage()
    );
  }

  @Test
  void unauthorizedShouldHaveMessage() {
    assertEquals("Authentication is required to access this resource",
        EnduranceTrioError.UNAUTHORIZED.getMessage()
    );
  }

  @Test
  void notFoundShouldHaveMessage() {
    assertEquals("The requested resource was not found", EnduranceTrioError.NOT_FOUND.getMessage());
  }

  @Test
  void conflictShouldHaveMessage() {
    assertEquals("A conflict occurred with the current state of the resource",
        EnduranceTrioError.CONFLICT.getMessage()
    );
  }

  @Test
  void internalErrorShouldHaveMessage() {
    assertEquals("An internal server error occurred",
        EnduranceTrioError.INTERNAL_ERROR.getMessage()
    );
  }
}
