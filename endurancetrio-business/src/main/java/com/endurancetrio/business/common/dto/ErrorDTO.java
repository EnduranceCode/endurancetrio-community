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

package com.endurancetrio.business.common.dto;

import com.endurancetrio.business.common.exception.EnduranceTrioError;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serial;
import java.io.Serializable;

/**
 * The {@link ErrorDTO} class represents structured error information returned to API clients.
 * It carries an HTTP status code, a machine-readable error key, and a human-readable message.
 *
 * <h2>Constructor Usage</h2>
 *
 * <h3>Generic Error (Enum Message)</h3>
 * <p>
 * Use when the enum's default message is sufficient:
 * <pre>
 *   {@code new ErrorDTO(EnduranceTrioError.NOT_FOUND)}
 * </pre>
 *
 * <h3>Specific Error (Custom Message)</h3>
 * <p>
 * Use when providing request-specific context improves client error handling:
 *
 * <pre>
 *   {@code new ErrorDTO(EnduranceTrioError.NOT_FOUND, "No route found with ID 42")}
 * </pre>
 *
 * <h2>Field Semantics</h2>
 *
 * <ul>
 *   <li>
 *     <b>code</b>: HTTP status code (e.g., 404). Aligns with response-level {@code status}
 *     but included for self-contained error objects in multi-error scenarios.
 *   </li>
 *   <li>
 *     <b>error</b>: Stable enum name (e.g., "NOT_FOUND"). Machine-readable;
 *     clients can switch/match on this for programmatic handling.
 *   </li>
 *   <li>
 *     <b>message</b>: Human-readable description. May be generic (from enum) or specific
 *     (from context). Intended for display or logging.
 *   </li>
 * </ul>
 *
 * @see EnduranceTrioError for predefined error types
 * @see EnduranceTrioException for exception usage patterns
 */
public record ErrorDTO(@JsonIgnore int code, String error, String message) implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  public ErrorDTO(EnduranceTrioError error) {
    this(error.getCode(), error.name(), error.getMessage());
  }

  public ErrorDTO(EnduranceTrioError error, String message) {
    this(error.getCode(), error.name(), message);
  }
}
