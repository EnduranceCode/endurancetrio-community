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

package com.endurancetrio.data.insight.model.entity;

import com.endurancetrio.data.common.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serial;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * The {@link ArticleContent} entity represents localized content for an {@link Article}.
 * <p>
 * The {@link ArticleContent}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getId() id} : the unique identifier of the {@link ArticleContent} that
 *     is automatically generated and is the primary key.
 *   </li>
 *   <li>
 *     {@link #getArticle() article} : the {@link Article} that this content belongs to.
 *   </li>
 *   <li>
 *     {@link #getLocale() locale} : the locale code (e.g., "en" for English or "pt"
 *     for Portuguese).
 *   </li>
 *   <li>
 *     {@link #getTitle() title} : the title of the {@link Article} in the given locale.
 *   </li>
 *   <li>
 *     {@link #getSubtitle() subtitle} : the optional subtitle of the {@link Article}
 *     in the given locale.
 *   </li>
 *   <li>
 *   {@link #getIntroText() introText} : the introductory HTML content of the
 *     {@link Article} in the given locale.
 *   </li>
 *   <li>
 *     {@link #getFullText() fullText} : the optional full HTML content of the
 *     {@link Article} in the given locale.
 *   </li>
 *   <li>
 *     {@link #getMetaTitle() metaTitle} : the optional SEO meta title for the
 *     {@link Article} in the given locale.
 *   </li>
 *   <li>
 *     {@link #getMetaDescription() metaDescription} : the optional SEO meta description
 *     for the {@link Article} in the given locale.
 *   </li>
 * </ul>
 */
@Entity(name = "ArticleContent")
@Table(name = "article_content")
@SequenceGenerator(
    name = "seq_endurancetrio_generator",
    sequenceName = "seq_article_content_id",
    allocationSize = 1
)
public class ArticleContent extends BaseEntity<Long> {

  @Serial
  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "article_id", nullable = false)
  private Article article;

  @Column(name = "locale", nullable = false)
  private String locale;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "subtitle")
  private String subtitle;

  @Column(name = "intro_text", nullable = false)
  private String introText;

  @Column(name = "full_text")
  private String fullText;

  @Column(name = "meta_title")
  private String metaTitle;

  @Column(name = "meta_description")
  private String metaDescription;

  /**
   * Default constructor for the {@link ArticleContent} entity.
   */
  public ArticleContent() {
    super();
  }

  public Article getArticle() {
    return article;
  }

  public void setArticle(Article article) {
    this.article = article;
  }

  public String getLocale() {
    return locale;
  }

  public void setLocale(String locale) {
    this.locale = locale;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
  }

  public String getIntroText() {
    return introText;
  }

  public void setIntroText(String introText) {
    this.introText = introText;
  }

  public String getFullText() {
    return fullText;
  }

  public void setFullText(String fullText) {
    this.fullText = fullText;
  }

  public String getMetaTitle() {
    return metaTitle;
  }

  public void setMetaTitle(String metaTitle) {
    this.metaTitle = metaTitle;
  }

  public String getMetaDescription() {
    return metaDescription;
  }

  public void setMetaDescription(String metaDescription) {
    this.metaDescription = metaDescription;
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ArticleContent.class.getSimpleName() + "[", "]").add(
            "id=" + this.getId())
        .add("articleId=" + Optional.ofNullable(article).map(Article::getId).orElse(null))
        .add("locale='" + locale + "'")
        .add("title='" + title + "'")
        .toString();
  }
}
