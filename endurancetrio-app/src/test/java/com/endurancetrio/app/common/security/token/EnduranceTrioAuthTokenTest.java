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

package com.endurancetrio.app.common.security.token;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

class EnduranceTrioAuthTokenTest {

  private static final String OWNER = "testOwner";
  private static final String KEY = "testKey123";

  @Test
  void unauthenticatedConstructorShouldSetPrincipalAndCredentials() {
    EnduranceTrioAuthToken token = new EnduranceTrioAuthToken(OWNER, KEY);

    assertEquals(OWNER, token.getPrincipal());
    assertEquals(KEY, token.getCredentials());
    assertFalse(token.isAuthenticated());
  }

  @Test
  void authenticatedConstructorShouldSetPrincipalAndAuthorities() {
    EnduranceTrioAuthToken token = new EnduranceTrioAuthToken(OWNER,
        List.of(new SimpleGrantedAuthority("ROLE_TRACKER"))
    );

    assertEquals(OWNER, token.getPrincipal());
    assertNull(token.getCredentials());
    assertTrue(token.isAuthenticated());
  }

  @Test
  void tokensWithSameFieldsShouldBeEqual() {
    EnduranceTrioAuthToken token1 = new EnduranceTrioAuthToken(OWNER, KEY);
    EnduranceTrioAuthToken token2 = new EnduranceTrioAuthToken(OWNER, KEY);

    assertEquals(token1, token2);
    assertEquals(token1.hashCode(), token2.hashCode());
  }

  @Test
  void tokensWithDifferentOwnersShouldNotBeEqual() {
    EnduranceTrioAuthToken token1 = new EnduranceTrioAuthToken(OWNER, KEY);
    EnduranceTrioAuthToken token2 = new EnduranceTrioAuthToken("otherOwner", KEY);

    assertNotEquals(token1, token2);
  }

  @Test
  void tokensWithDifferentCredentialsShouldNotBeEqual() {
    EnduranceTrioAuthToken token1 = new EnduranceTrioAuthToken(OWNER, KEY);
    EnduranceTrioAuthToken token2 = new EnduranceTrioAuthToken(OWNER, "otherKey");

    assertNotEquals(token1, token2);
  }
}
