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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.endurancetrio.app.common.response.EnduranceTrioResponse;
import com.endurancetrio.business.common.dto.ErrorDTO;
import com.endurancetrio.business.common.exception.EnduranceTrioError;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.core.MethodParameter;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.MapBindingResult;
import org.springframework.validation.method.MethodValidationResult;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@ExtendWith(MockitoExtension.class)
class EnduranceTrioExceptionHandlerAPITest {

  @InjectMocks
  private EnduranceTrioExceptionHandlerAPI underTest;

  @Test
  void handledExceptionShouldReturnMappedHttpStatus() {
    EnduranceTrioException exception = new EnduranceTrioException(
        new ErrorDTO(EnduranceTrioError.NOT_FOUND));

    ResponseEntity<Object> response = underTest.handledException(exception);

    assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
  }

  @Test
  void handledExceptionShouldReturn400ForBadRequest() {
    EnduranceTrioException exception = new EnduranceTrioException(
        new ErrorDTO(EnduranceTrioError.BAD_REQUEST));

    ResponseEntity<Object> response = underTest.handledException(exception);

    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void handledExceptionShouldReturn409ForConflict() {
    EnduranceTrioException exception = new EnduranceTrioException(
        new ErrorDTO(EnduranceTrioError.CONFLICT));

    ResponseEntity<Object> response = underTest.handledException(exception);

    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
  }

  @Test
  void handledExceptionShouldReturn500ForInternalError() {
    EnduranceTrioException exception = new EnduranceTrioException(
        new ErrorDTO(EnduranceTrioError.INTERNAL_ERROR));

    ResponseEntity<Object> response = underTest.handledException(exception);

    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
  }

  @Test
  void unhandledExceptionShouldReturn500() {
    RuntimeException exception = new RuntimeException("Unexpected error");

    ResponseEntity<EnduranceTrioResponse<String>> response = underTest.unhandledException(
        exception);

    assertNotNull(response);
    assertNotNull(response.getBody());
    assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    assertEquals(500, response.getBody().status());
  }

  @SuppressWarnings("unchecked")
  @Test
  void handledExceptionShouldReturnErrorsInBody() {
    ErrorDTO error = new ErrorDTO(EnduranceTrioError.BAD_REQUEST, "field is required");
    EnduranceTrioException exception = new EnduranceTrioException(error);

    ResponseEntity<Object> response = underTest.handledException(exception);

    EnduranceTrioResponse<List<ErrorDTO>> body = (EnduranceTrioResponse<List<ErrorDTO>>) response.getBody();

    assertNotNull(body);
    assertEquals(1, body.data().size());
    assertEquals("field is required", body.data().getFirst().message());
  }

  @Test
  void handleMethodArgumentNotValidShouldReturnValidationErrors() {
    MapBindingResult bindingResult = new MapBindingResult(new java.util.HashMap<>(), "target");
    bindingResult.rejectValue("fieldName", "error.code", "must not be null");
    MethodArgumentNotValidException exception = new MethodArgumentNotValidException(
        mock(org.springframework.core.MethodParameter.class), bindingResult);

    ResponseEntity<Object> response = underTest.handleMethodArgumentNotValid(exception,
        HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, mock(WebRequest.class)
    );

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void handleConstraintViolationShouldReturnBadRequest() {
    ConstraintViolation<?> violation = mock(ConstraintViolation.class);
    Path propertyPath = mock(Path.class);
    when(propertyPath.toString()).thenReturn("fieldName");
    when(violation.getPropertyPath()).thenReturn(propertyPath);
    when(violation.getMessage()).thenReturn("must not be null");
    ConstraintViolationException exception = new ConstraintViolationException("validation failed",
        Set.of(violation)
    );

    ResponseEntity<Object> response = underTest.handleConstraintViolation(exception);

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }

  @Test
  void handleDataIntegrityShouldReturnConflict() {
    DataIntegrityViolationException exception = new DataIntegrityViolationException(
        "constraint violation");

    ResponseEntity<Object> response = underTest.handleDataIntegrity(exception);

    assertNotNull(response);
    assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
  }

  @Test
  void handleHandlerMethodValidationExceptionShouldReturnBadRequest() {
    MethodValidationResult methodValidationResult = mock(MethodValidationResult.class);
    ParameterValidationResult paramResult = mock(ParameterValidationResult.class);
    MethodParameter methodParam = mock(MethodParameter.class);
    MessageSourceResolvable error = mock(MessageSourceResolvable.class);
    when(methodParam.getParameterName()).thenReturn("paramName");
    when(paramResult.getMethodParameter()).thenReturn(methodParam);
    when(error.getDefaultMessage()).thenReturn("must not be null");
    when(paramResult.getResolvableErrors()).thenReturn(List.of(error));
    when(methodValidationResult.getParameterValidationResults()).thenReturn(List.of(paramResult));

    HandlerMethodValidationException exception = new HandlerMethodValidationException(
        methodValidationResult);

    ResponseEntity<Object> response = underTest.handleHandlerMethodValidationException(exception,
        HttpHeaders.EMPTY, HttpStatus.BAD_REQUEST, mock(WebRequest.class)
    );

    assertNotNull(response);
    assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
  }
}
