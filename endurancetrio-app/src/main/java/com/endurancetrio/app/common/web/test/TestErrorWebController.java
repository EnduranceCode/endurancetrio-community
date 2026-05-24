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

package com.endurancetrio.app.common.web.test;

import com.endurancetrio.app.common.annotation.EnduranceTrioWebController;
import com.endurancetrio.business.common.dto.ErrorDTO;
import com.endurancetrio.business.common.exception.EnduranceTrioError;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * The {@link TestErrorWebController} provides endpoints for testing error pages during local
 * development. It is only active when the {@code local} Spring profile is enabled and is never
 * instantiated in production deployments.
 */
@EnduranceTrioWebController
@Profile("local")
public class TestErrorWebController {

  @GetMapping("/{language:en|pt}/test/error-403")
  public String trigger403(@PathVariable String language) {
    throw new EnduranceTrioException(new ErrorDTO(EnduranceTrioError.FORBIDDEN,
        "This is a test 403 error for the " + language + " locale"
    ));
  }

  @GetMapping("/{language:en|pt}/test/error-404")
  public String trigger404(@PathVariable String language) {
    throw new EnduranceTrioException(new ErrorDTO(EnduranceTrioError.NOT_FOUND,
        "This is a test 404 error for the " + language + " locale"
    ));
  }

  @GetMapping("/{language:en|pt}/test/error-500")
  public String trigger500(@PathVariable String language) {
    throw new TestServerErrorException("This is a test 500 error for the " + language + " locale");
  }

  private static class TestServerErrorException extends RuntimeException {

    TestServerErrorException(String message) {
      super(message);
    }
  }
}
