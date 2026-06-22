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
 * EVEN IF HAVE BEEN INFORMED OF THEIR POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.app.common.security.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.endurancetrio.app.common.security.token.EnduranceTrioAuthToken;
import com.endurancetrio.business.tracker.service.TrackerAccountService;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;

@ExtendWith(MockitoExtension.class)
class EnduranceTrioAuthProviderTest {

  private static final String OWNER = "testOwner";
  private static final String VALID_KEY = "validKey123";
  private static final String INVALID_KEY = "invalidKey456";

  @Mock
  private TrackerAccountService trackerAccountService;

  @InjectMocks
  private EnduranceTrioAuthProvider underTest;

  private EnduranceTrioAuthToken authRequest;

  @BeforeEach
  void setUp() {
    authRequest = new EnduranceTrioAuthToken(OWNER, VALID_KEY);
  }

  @Test
  void validKeyShouldReturnAuthenticatedToken() {
    when(trackerAccountService.validateKey(OWNER, VALID_KEY)).thenReturn(true);

    Authentication result = underTest.authenticate(authRequest);

    assertNotNull(result);
    assertTrue(result.isAuthenticated());
    assertEquals(OWNER, result.getPrincipal());
    assertTrue(result.getAuthorities()
        .stream()
        .anyMatch(a -> Objects.equals(a.getAuthority(), "ROLE_TRACKER")));
  }

  @Test
  void invalidKeyShouldThrowBadCredentials() {
    EnduranceTrioAuthToken badRequest = new EnduranceTrioAuthToken(OWNER, INVALID_KEY);
    when(trackerAccountService.validateKey(OWNER, INVALID_KEY)).thenReturn(false);

    assertThrows(BadCredentialsException.class, () -> underTest.authenticate(badRequest));
  }

  @Test
  void supportsShouldReturnTrueForEnduranceTrioAuthToken() {
    assertTrue(underTest.supports(EnduranceTrioAuthToken.class));
  }

  @Test
  void supportsShouldReturnFalseForOtherTypes() {
    assertFalse(underTest.supports(String.class));
  }

  @Test
  void authenticatedTokenShouldHaveNoCredentials() {
    when(trackerAccountService.validateKey(OWNER, VALID_KEY)).thenReturn(true);

    Authentication result = underTest.authenticate(authRequest);

    assertNotNull(result);
    assertNull(result.getCredentials());
  }

  private void assertNull(Object credentials) {
    org.junit.jupiter.api.Assertions.assertNull(credentials);
  }
}
