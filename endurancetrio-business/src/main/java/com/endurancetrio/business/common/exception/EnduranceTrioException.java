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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.jspecify.annotations.NonNull;

/**
 * The {@link EnduranceTrioException} class represents all custom exceptions in the EnduranceTrio
 * project. It extends {@link RuntimeException} and carries HTTP status codes aligned with
 * REST semantics, along with structured error details.
 *
 * <h2>Usage Patterns</h2>
 *
 * <h3>Single Error with Generic Message</h3>
 * <p>
 * Use when the error is self-explanatory and the enum's default message suffices:
 *
 * <pre>
 *   {@code throw new EnduranceTrioException(new ErrorDTO(EnduranceTrioError.NOT_FOUND));}
 * </pre>
 * Response: {@code details: "The requested resource was not found"}
 *
 * <h3>Single Error with Specific Context</h3>
 * <p>
 * Use when you need to provide request-specific details to the client:
 *
 * <pre>
 *   {@code
 *     String errorMsg = String.format("No route found with ID %d", routeId);
 *     throw new EnduranceTrioException(new ErrorDTO(EnduranceTrioError.NOT_FOUND, errorMsg));
 *   }
 * </pre>
 * <p>
 * Response:
 *
 * <pre>
 *   {@code
 *     {
 *       "details": "The requested resource was not found",
 *       "data": [
 *         {
 *           "error": "NOT_FOUND",
 *           "message": "No route found with ID 5"
 *         }
 *       ]
 *     }
 *   }
 * </pre>
 *
 * <h3>Wrapping Lower-Layer Exceptions</h3>
 * <p>
 * Use when catching and re-throwing to preserve the root cause:
 *
 * <pre>
 *   {@code
 *     try {
 *       repository.save(entity);
 *     } catch (DataIntegrityViolationException ex) {
 *       throw new EnduranceTrioException(
 *           new ErrorDTO(EnduranceTrioError.CONCURRENT_UPDATE, "Version conflict detected"), ex
 *       );
 *     }
 *   }
 * </pre>
 * <p>
 * The original {@code DataIntegrityViolationException} is preserved in the cause chain and logged
 * for debugging.
 *
 * <h3>Multiple Errors (Validation)</h3>
 * Use for aggregated validation failures (e.g., multiple field errors):
 * <pre>
 *   {@code
 *     List<ErrorDTO> errors = List.of(
 *         new ErrorDTO(EnduranceTrioError.BAD_REQUEST, "Name must not be blank"),
 *         new ErrorDTO(EnduranceTrioError.BAD_REQUEST, "Distance must be positive")
 *     );
 *     throw new EnduranceTrioException(errors);
 *   }
 * </pre>
 *
 * <h2>HTTP Code Mapping</h2>
 * The {@code code} field is intentionally coupled to HTTP status codes for REST API clarity:
 * <ul>
 *   <li>400 (Bad Request): Invalid client input</li>
 *   <li>404 (Not Found): Resource does not exist</li>
 *   <li>409 (Conflict): Optimistic locking or concurrent modification</li>
 * </ul>
 * New error codes must align with HTTP semantics; see {@link EnduranceTrioError}.
 *
 * @see EnduranceTrioError for available error types
 * @see ErrorDTO for error detail structure
 */
public class EnduranceTrioException extends RuntimeException {

  private final int code;
  private final List<ErrorDTO> errors = new ArrayList<>();

  public EnduranceTrioException(@NonNull ErrorDTO error) {
    this(Collections.singletonList(error), null);
  }

  public EnduranceTrioException(@NonNull List<ErrorDTO> errors) {
    this(errors, null);
  }

  public EnduranceTrioException(@NonNull List<ErrorDTO> errors, Throwable cause) {
    super(errors.getFirst().message(), cause);
    this.code = errors.getFirst().code();
    this.errors.addAll(errors);
  }

  public int getCode() {
    return code;
  }

  public List<ErrorDTO> getErrors() {
    return errors;
  }
}
