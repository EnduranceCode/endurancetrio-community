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

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "kit")
public class KitProperties {

  private String apiBaseUrl;
  private String apiKey;
  private String apiKeyHeader;
  private String apiVersion;
  private String formIdEn;
  private String formIdPt;
  private String pathFormSubscribers;
  private String pathSubscribers;

  public String getApiBaseUrl() {
    return apiBaseUrl;
  }

  public void setApiBaseUrl(String apiBaseUrl) {
    this.apiBaseUrl = apiBaseUrl;
  }

  public String getApiKey() {
    return apiKey;
  }

  public void setApiKey(String apiKey) {
    this.apiKey = apiKey;
  }

  public String getApiKeyHeader() {
    return apiKeyHeader;
  }

  public void setApiKeyHeader(String apiKeyHeader) {
    this.apiKeyHeader = apiKeyHeader;
  }

  public String getApiVersion() {
    return apiVersion;
  }

  public void setApiVersion(String apiVersion) {
    this.apiVersion = apiVersion;
  }

  public String getFormIdEn() {
    return formIdEn;
  }

  public void setFormIdEn(String formIdEn) {
    this.formIdEn = formIdEn;
  }

  public String getFormIdPt() {
    return formIdPt;
  }

  public void setFormIdPt(String formIdPt) {
    this.formIdPt = formIdPt;
  }

  public String getPathFormSubscribers() {
    return pathFormSubscribers;
  }

  public void setPathFormSubscribers(String pathFormSubscribers) {
    this.pathFormSubscribers = pathFormSubscribers;
  }

  public String getPathSubscribers() {
    return pathSubscribers;
  }

  public void setPathSubscribers(String pathSubscribers) {
    this.pathSubscribers = pathSubscribers;
  }
}
