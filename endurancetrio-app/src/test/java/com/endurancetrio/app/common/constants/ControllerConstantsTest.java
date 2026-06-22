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

package com.endurancetrio.app.common.constants;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Test;

class ControllerConstantsTest {

  @Test
  void apiPathShouldBeApi() {
    assertEquals("/api", ControllerConstants.API_PATH);
  }

  @Test
  void status200ShouldBe200() {
    assertEquals(200, ControllerConstants.STATUS_200);
  }

  @Test
  void status500ShouldBe500() {
    assertEquals(500, ControllerConstants.STATUS_500);
  }

  @Test
  void msgSuccessShouldBeCorrect() {
    assertEquals("Request handled successfully", ControllerConstants.MSG_SUCCESS);
  }

  @Test
  void msgAuthFailureShouldBeCorrect() {
    assertEquals("Authentication failed", ControllerConstants.MSG_AUTH_FAILURE);
  }

  @Test
  void msgAuthDeniedShouldBeCorrect() {
    assertEquals("Access Denied: Missing required permissions",
        ControllerConstants.MSG_AUTH_DENIED
    );
  }

  @Test
  void msgServerErrorShouldBeCorrect() {
    assertEquals("An internal server error occurred", ControllerConstants.MSG_SERVER_ERROR);
  }

  @Test
  void privateConstructorShouldThrow() throws Exception {
    Constructor<ControllerConstants> constructor = ControllerConstants.class.getDeclaredConstructor();
    constructor.setAccessible(true);

    InvocationTargetException thrown = assertThrows(InvocationTargetException.class,
        constructor::newInstance
    );
    assertEquals(IllegalStateException.class, thrown.getCause().getClass());
  }
}
