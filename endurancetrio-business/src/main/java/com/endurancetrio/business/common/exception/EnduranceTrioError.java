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

import com.endurancetrio.business.common.dto.ErrorDTO;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * The {@link EnduranceTrioError} enum defines standardized error codes and messages for the
 * EnduranceTrio project. Each constant represents a specific failure category aligned with
 * HTTP status codes.
 *
 * <h2>Design Rationale</h2>
 * <p>
 * Error codes are intentionally coupled to HTTP status codes because:
 * <ul>
 *   <li>This is a REST API—HTTP codes provide universal client semantics</li>
 *   <li>Clients can branch on status ranges (4xx = client error, 5xx = server error)</li>
 *   <li>Standard codes reduce API documentation burden</li>
 * </ul>
 *
 * <h2>Adding New Errors</h2>
 * <p>
 * When adding constants:
 * <ol>
 *   <li>Choose HTTP code that best matches the semantic (see IANA registry)</li>
 *   <li>Provide a generic, reusable message (specific context goes in {@link ErrorDTO})</li>
 *   <li>Document when the error should be used (see examples above)</li>
 * </ol>
 *
 * <b>Reference:</b>
 * <a href="https://www.iana.org/assignments/http-status-codes/http-status-codes.xhtml">
 *   IANA HTTP Status Codes
 * </a>
 *
 * @see ErrorDTO for error construction
 * @see EnduranceTrioException for exception usage patterns
 */
public enum EnduranceTrioError {

  BAD_REQUEST(400, "The request was made with invalid or incomplete data"),
  NOT_FOUND(404, "The requested resource was not found"),
  CONFLICT(409, "A conflict occurred with the current state of the resource"),
  INTERNAL_ERROR(500, "An internal server error occurred");

  private static final Map<Integer, EnduranceTrioError> BY_CODE = Arrays.stream(values())
      .collect(Collectors.toUnmodifiableMap(EnduranceTrioError::getCode, Function.identity()));

  private final int code;
  private final String message;

  EnduranceTrioError(int code, String message) {
    this.code = code;
    this.message = message;
  }

  /**
   * Retrieves the {@link EnduranceTrioError} constant corresponding to the given error code.
   *
   * @param code the error code
   * @return an {@link Optional} containing the matching {@link EnduranceTrioError}, or empty if
   *     no match is found
   */
  public static Optional<EnduranceTrioError> fromCode(int code) {
    return Optional.ofNullable(BY_CODE.get(code));
  }

  public int getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
