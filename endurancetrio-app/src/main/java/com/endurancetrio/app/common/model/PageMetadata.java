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

package com.endurancetrio.app.common.model;

/**
 * The {@link PageMetadata} class holds page-level metadata used across shared template fragments in
 * the application. This includes HTML {@code <head>} elements (title, description, canonical URL,
 * Open Graph tags) as well as view-specific data consumed by other layout components such as the
 * navigation bar and footer.
 */
public class PageMetadata {

  private String appVersion;
  private String canonicalUrl;
  private String copyrightYear;
  private String description;
  private String facebookPageId;
  private String kofiUserId;
  private String ogImage;
  private Integer ogImageHeight;
  private Integer ogImageWidth;
  private String title;
  private String twitterSite;
  private String view;

  public PageMetadata() {
    super();
  }

  public String getAppVersion() {
    return appVersion;
  }

  public void setAppVersion(String appVersion) {
    this.appVersion = appVersion;
  }

  public String getCanonicalUrl() {
    return canonicalUrl;
  }

  public String getCopyrightYear() {
    return copyrightYear;
  }

  public void setCopyrightYear(String copyrightYear) {
    this.copyrightYear = copyrightYear;
  }

  public void setCanonicalUrl(String canonicalUrl) {
    this.canonicalUrl = canonicalUrl;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getFacebookPageId() {
    return facebookPageId;
  }

  public void setFacebookPageId(String facebookPageId) {
    this.facebookPageId = facebookPageId;
  }

  public String getKofiUserId() {
    return kofiUserId;
  }

  public void setKofiUserId(String kofiUserId) {
    this.kofiUserId = kofiUserId;
  }

  public String getOgImage() {
    return ogImage;
  }

  public void setOgImage(String ogImage) {
    this.ogImage = ogImage;
  }

  public Integer getOgImageHeight() {
    return ogImageHeight;
  }

  public void setOgImageHeight(Integer ogImageHeight) {
    this.ogImageHeight = ogImageHeight;
  }

  public Integer getOgImageWidth() {
    return ogImageWidth;
  }

  public void setOgImageWidth(Integer ogImageWidth) {
    this.ogImageWidth = ogImageWidth;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getTwitterSite() {
    return twitterSite;
  }

  public void setTwitterSite(String twitterSite) {
    this.twitterSite = twitterSite;
  }

  public String getView() {
    return view;
  }

  public void setView(String view) {
    this.view = view;
  }
}
