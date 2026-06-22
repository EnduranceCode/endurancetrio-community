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

package com.endurancetrio.app.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.RETURNS_SELF;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.endurancetrio.app.common.security.entrypoint.EnduranceTrioAuthEntryPoint;
import com.endurancetrio.app.common.security.provider.EnduranceTrioAuthProvider;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

@ExtendWith(MockitoExtension.class)
class AppSecurityConfigTest {

  @Mock
  private EnduranceTrioAuthProvider authProvider;

  @Mock
  private EnduranceTrioAuthEntryPoint entryPoint;

  @Test
  void corsConfigurationSourceWithNullOriginsShouldReturnNonNullSource() {
    AppSecurityConfig config = new AppSecurityConfig(null, authProvider, entryPoint);

    assertNotNull(config.corsConfigurationSource());
  }

  @Test
  void corsConfigurationSourceWithEmptyOriginsShouldReturnNonNullSource() {
    AppSecurityConfig config = new AppSecurityConfig("", authProvider, entryPoint);

    assertNotNull(config.corsConfigurationSource());
  }

  @Test
  void corsConfigurationSourceShouldConfigureAllowedOrigins() {
    AppSecurityConfig config = new AppSecurityConfig("http://localhost:3000", authProvider,
        entryPoint
    );
    CorsConfigurationSource source = config.corsConfigurationSource();

    CorsConfiguration corsConfig = source.getCorsConfiguration(
        new MockHttpServletRequest("GET", "/api/test"));

    assertNotNull(corsConfig);
    assertNotNull(corsConfig.getAllowedOrigins());
    assertTrue(corsConfig.getAllowedOrigins().contains("http://localhost:3000"));
  }

  @Test
  void corsConfigurationSourceShouldParseAndTrimMultipleOrigins() {
    AppSecurityConfig config = new AppSecurityConfig(" https://a.com , https://b.com ", authProvider,
        entryPoint
    );
    CorsConfigurationSource source = config.corsConfigurationSource();

    CorsConfiguration corsConfig = source.getCorsConfiguration(
        new MockHttpServletRequest("GET", "/api/test"));

    assertNotNull(corsConfig);
    assertNotNull(corsConfig.getAllowedOrigins());
    assertTrue(corsConfig.getAllowedOrigins().contains("https://a.com"));
    assertTrue(corsConfig.getAllowedOrigins().contains("https://b.com"));
  }

  @Test
  void corsConfigurationSourceShouldConfigureCorsSettings() {
    AppSecurityConfig config = new AppSecurityConfig("*", authProvider, entryPoint);
    CorsConfigurationSource source = config.corsConfigurationSource();

    CorsConfiguration corsConfig = source.getCorsConfiguration(
        new MockHttpServletRequest("GET", "/api/test"));

    assertNotNull(corsConfig);
    assertNotNull(corsConfig.getAllowedMethods());
    assertTrue(corsConfig.getAllowedMethods().containsAll(
        List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS")));
    assertNotNull(corsConfig.getAllowedHeaders());
    assertTrue(corsConfig.getAllowedHeaders().contains("*"));
    assertEquals(Boolean.TRUE, corsConfig.getAllowCredentials());
  }

  @Test
  void authenticationManagerShouldReturnProviderManager() {
    AppSecurityConfig config = new AppSecurityConfig("*", authProvider, entryPoint);

    AuthenticationManager manager = config.authenticationManager();

    assertNotNull(manager);
    assertInstanceOf(ProviderManager.class, manager);
  }

  @Test
  void authenticationFilterShouldReturnNonNullFilter() {
    AppSecurityConfig config = new AppSecurityConfig("*", authProvider, entryPoint);

    assertNotNull(config.authenticationFilter());
  }

  @Test
  void securityFilterChainAPIShouldBuildSuccessfully() {
    AppSecurityConfig config = new AppSecurityConfig("*", authProvider, entryPoint);
    HttpSecurity http = mock(HttpSecurity.class, RETURNS_SELF);
    DefaultSecurityFilterChain chain = mock(DefaultSecurityFilterChain.class);
    when(http.build()).thenReturn(chain);

    assertSame(chain, config.securityFilterChainAPI(http));
  }

  @Test
  void securityFilterChainWebShouldBuildSuccessfully() {
    AppSecurityConfig config = new AppSecurityConfig("*", authProvider, entryPoint);
    HttpSecurity http = mock(HttpSecurity.class, RETURNS_SELF);
    DefaultSecurityFilterChain chain = mock(DefaultSecurityFilterChain.class);
    when(http.build()).thenReturn(chain);

    assertSame(chain, config.securityFilterChainWeb(http));
  }
}
