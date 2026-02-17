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

package com.endurancetrio.app.common.handler;

import static com.endurancetrio.app.common.constants.ControllerConstants.MSG_SERVER_ERROR;
import static com.endurancetrio.app.common.constants.ControllerConstants.REASON_500;
import static com.endurancetrio.app.common.constants.ControllerConstants.STATUS_500;

import com.endurancetrio.app.common.annotation.EnduranceTrioRestController;
import com.endurancetrio.app.common.response.EnduranceTrioResponse;
import com.endurancetrio.business.common.dto.ErrorDTO;
import com.endurancetrio.business.common.exception.EnduranceTrioError;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import jakarta.validation.ConstraintViolationException;
import java.util.List;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The {@link EnduranceTrioExceptionHandlerAPI} class is a global exception handler for the
 * EnduranceTrio application.
 * <p>
 * This class interceptor captures:
 * <ul>
 *   <li>Domain-specific {@link EnduranceTrioException}</li>
 *   <li>Standard Spring Web exceptions (Validation, JSON parsing)</li>
 *   <li>Generic unhandled exceptions</li>
 * </ul>
 *
 * All exceptions are transformed into a standardized {@link EnduranceTrioResponse}.
 */
@RestControllerAdvice(annotations = EnduranceTrioRestController.class)
public class EnduranceTrioExceptionHandlerAPI extends ResponseEntityExceptionHandler {

  private static final Logger LOG = LoggerFactory.getLogger(EnduranceTrioExceptionHandlerAPI.class);

  private static final String KEY_VALUE_FORMAT = "%s: %s";

  /**
   * Intercepts malformed JSON requests where the payload cannot be deserialized.
   *
   * @return a {@link ResponseEntity} containing a {@link EnduranceTrioError} BAD_REQUEST error.
   */
  @Override
  protected ResponseEntity<@NonNull Object> handleHttpMessageNotReadable(
      @NonNull HttpMessageNotReadableException exception, @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status, @NonNull WebRequest request
  ) {

    LOG.warn("JSON Deserialization failed: {}", exception.getMostSpecificCause().getMessage());

    ErrorDTO error = new ErrorDTO(EnduranceTrioError.BAD_REQUEST, "JSON Deserialization failed");

    return handledException(new EnduranceTrioException(error));
  }

  /**
   * Intercepts validation failures on method-level parameters such as {@code @PathVariable} or
   * {@code @RequestParam}.
   *
   * @return a {@link ResponseEntity} containing a list of parameter validation errors.
   */
  @Override
  protected ResponseEntity<@NonNull Object> handleHandlerMethodValidationException(
      @NonNull HandlerMethodValidationException exception, @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status, @NonNull WebRequest request
  ) {

    List<ErrorDTO> validationErrors = exception.getParameterValidationResults()
        .stream()
        .flatMap(result -> result.getResolvableErrors()
            .stream()
            .map(error -> new ErrorDTO(
                EnduranceTrioError.BAD_REQUEST, // Passing the Enum as per your snippet
                String.format(KEY_VALUE_FORMAT, result.getMethodParameter().getParameterName(),
                    error.getDefaultMessage()
                )
            )))
        .toList();

    return handledException(new EnduranceTrioException(validationErrors));
  }

  /**
   * Intercepts validation failures for {@code @Valid} annotated {@code @RequestBody} objects.
   *
   * @return a {@link ResponseEntity} containing a list of field-level validation errors.
   */
  @Override
  protected ResponseEntity<@NonNull Object> handleMethodArgumentNotValid(
      @NonNull MethodArgumentNotValidException exception, @NonNull HttpHeaders headers,
      @NonNull HttpStatusCode status, @NonNull WebRequest request
  ) {

    List<ErrorDTO> validationErrors = exception.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(fieldError -> new ErrorDTO(EnduranceTrioError.BAD_REQUEST,
            String.format(KEY_VALUE_FORMAT, fieldError.getField(), fieldError.getDefaultMessage())
        ))
        .toList();

    return handledException(new EnduranceTrioException(validationErrors));
  }

  /**
   * Intercepts validation failures from the business layer or manual validation triggers.
   *
   * @return a {@link ResponseEntity} containing the constraint violations.
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<Object> handleConstraintViolation(ConstraintViolationException ex) {

    List<ErrorDTO> errors = ex.getConstraintViolations()
        .stream()
        .map(violation -> new ErrorDTO(EnduranceTrioError.BAD_REQUEST,
            String.format(KEY_VALUE_FORMAT, violation.getPropertyPath(), violation.getMessage())
        ))
        .toList();

    return handledException(new EnduranceTrioException(errors));
  }

  /**
   * Intercepts database integrity violations, such as unique constraint conflicts or foreign key
   * violations.
   * <p>
   * This often occurs in the data layer when the state of the database prevents the requested
   * persistence operation.
   *
   * @param ex The data integrity violation exception.
   * @return a {@link ResponseEntity} mapped to a concurrency or conflict error.
   */
  @ExceptionHandler(DataIntegrityViolationException.class)
  public ResponseEntity<Object> handleDataIntegrity(DataIntegrityViolationException ex) {

    LOG.warn("Database integrity violation: {}", ex.getMostSpecificCause().getMessage());

    ErrorDTO error = new ErrorDTO(EnduranceTrioError.CONFLICT);

    return handledException(new EnduranceTrioException(error));
  }

  /**
   * Handles business logic exceptions defined within the EnduranceTrio domain. This is the primary
   * funnel for all expected error states.
   *
   * @param exception The domain exception containing error codes and messages.
   * @return a standardized {@link EnduranceTrioResponse}.
   */
  @ExceptionHandler(EnduranceTrioException.class)
  public ResponseEntity<@NonNull Object> handledException(EnduranceTrioException exception) {

    HttpStatus status = HttpStatus.resolve(exception.getCode());
    if (status == null) {
      status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    String errorMessageTemplate = "Handled Exception ({}): {}";
    if (status.is5xxServerError() || exception.getCause() != null) {
      LOG.error(errorMessageTemplate, status.value(), exception.getMessage(), exception);
    } else {
      LOG.warn(errorMessageTemplate, status.value(), exception.getMessage());
    }

    String responseMessage = EnduranceTrioError.fromCode(exception.getCode())
        .orElse(EnduranceTrioError.INTERNAL_ERROR)
        .getMessage();

    EnduranceTrioResponse<List<ErrorDTO>> response = new EnduranceTrioResponse<>(status.value(),
        status.getReasonPhrase(), responseMessage, exception.getErrors()
    );

    return new ResponseEntity<>(response, status);
  }

  /**
   * Catch-all handler for any unexpected system exceptions. Maps unknown errors to a standard HTTP
   * 500 Internal Server Error to avoid leaking system details.
   *
   * @param exception The unexpected throwable.
   * @return a {@link ResponseEntity} with a generic error message.
   */
  @ExceptionHandler(Exception.class)
  public ResponseEntity<@NonNull EnduranceTrioResponse<String>> unhandledException(Exception exception) {

    LOG.error("Unhandled exception ({}); {}", STATUS_500, exception.getMessage(), exception);

    EnduranceTrioResponse<String> response = new EnduranceTrioResponse<>(STATUS_500, REASON_500,
        MSG_SERVER_ERROR
    );

    return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
