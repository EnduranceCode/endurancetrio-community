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
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.endurancetrio.business.common.dto.ErrorDTO;
import java.util.List;
import org.junit.jupiter.api.Test;

class EnduranceTrioExceptionTest {

  @Test
  void shouldCreateWithSingleError() {
    ErrorDTO error = new ErrorDTO(EnduranceTrioError.NOT_FOUND);
    EnduranceTrioException exception = new EnduranceTrioException(error);

    assertEquals(404, exception.getCode());
    assertEquals(1, exception.getErrors().size());
    assertEquals("NOT_FOUND", exception.getErrors().getFirst().error());
    assertNotNull(exception.getMessage());
  }

  @Test
  void shouldCreateWithMultipleErrors() {
    ErrorDTO error1 = new ErrorDTO(EnduranceTrioError.BAD_REQUEST, "field1 is required");
    ErrorDTO error2 = new ErrorDTO(EnduranceTrioError.BAD_REQUEST, "field2 is invalid");
    List<ErrorDTO> errors = List.of(error1, error2);

    EnduranceTrioException exception = new EnduranceTrioException(errors);

    assertEquals(400, exception.getCode());
    assertEquals(2, exception.getErrors().size());
  }

  @Test
  void shouldRetainFirstErrorCode() {
    ErrorDTO error1 = new ErrorDTO(EnduranceTrioError.NOT_FOUND);
    ErrorDTO error2 = new ErrorDTO(EnduranceTrioError.BAD_REQUEST);

    EnduranceTrioException exception = new EnduranceTrioException(List.of(error1, error2));

    assertEquals(404, exception.getCode());
  }

  @Test
  void shouldHandleInternalError() {
    ErrorDTO error = new ErrorDTO(EnduranceTrioError.INTERNAL_ERROR);
    EnduranceTrioException exception = new EnduranceTrioException(error);

    assertEquals(500, exception.getCode());
  }
}
