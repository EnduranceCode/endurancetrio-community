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
import com.endurancetrio.data.event.model.entity.Event;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serial;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

/**
 * The {@link Article} entity represents an insight article in the Insights section.
 * <p>
 * The {@link Article}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getId() id} : the unique identifier of the {@link Article} that
 *     is automatically generated and is the primary key.
 *   </li>
 *   <li>
 *     {@link #getSlug() slug} : the unique URL slug of the {@link Article}.
 *   </li>
 *   <li>
 *     {@link #getAuthor() author} : the {@link Author} of the {@link Article}.
 *   </li>
 *   <li>
 *     {@link #getPublishedDate() publishedDate} : the date when the {@link Article}
 *     was published.
 *   </li>
 *   <li>
 *     {@link #getFeaturedImage() featuredImage} : the optional URL of the featured image
 *     for the {@link Article}.
 *   </li>
 *   <li>
 *     {@link #getFeaturedImageWidth() featuredImageWidth} : the optional width of the
 *     featured image in pixels.
 *   </li>
 *   <li>
 *     {@link #getFeaturedImageHeight() featuredImageHeight} : the optional height of the
 *     featured image in pixels.
 *   </li>
 *   <li>
 *     {@link #getContents() contents} : the localized content entries for the
 *     {@link Article}.
 *   </li>
 *   <li>
 *     {@link #getRelatedEvents() relatedEvents} : the optional set of {@link Event events}
 *     related to the {@link Article}.
 *   </li>
 * </ul>
 */
@Entity(name = "Article")
@Table(name = "article")
@SequenceGenerator(
    name = "seq_endurancetrio_generator", sequenceName = "seq_article_id", allocationSize = 1
)
public class Article extends BaseEntity<Long> {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "slug", nullable = false, unique = true)
  private String slug;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "author_id", nullable = false)
  private Author author;

  @Column(name = "published_date")
  private LocalDateTime publishedDate;

  @Column(name = "featured_image")
  private String featuredImage;

  @Column(name = "featured_image_width")
  private Integer featuredImageWidth;

  @Column(name = "featured_image_height")
  private Integer featuredImageHeight;

  @OneToMany(
      mappedBy = "article", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true
  )
  private Set<ArticleContent> contents;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "article_event",
      joinColumns = {@JoinColumn(name = "article_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")}
  )
  private Set<Event> relatedEvents;

  /**
   * Default constructor for the {@link Article} entity.
   */
  public Article() {
    super();
    this.contents = new HashSet<>();
    this.relatedEvents = new HashSet<>();
  }

  /**
   * Adds an {@link ArticleContent} to the {@link Article}. This method also sets the
   * {@link Article} reference in the {@link ArticleContent}.
   *
   * @param content the {@link ArticleContent} to be added
   */
  public void addContent(ArticleContent content) {
    if (content != null && this.contents.add(content)) {
      content.setArticle(this);
    }
  }

  /**
   * Removes an {@link ArticleContent} from the {@link Article}. This method also removes the
   * {@link Article} reference from the {@link ArticleContent}.
   *
   * @param content the {@link ArticleContent} to be removed
   */
  public void removeContent(ArticleContent content) {
    if (content != null && this.contents.remove(content)) {
      content.setArticle(null);
    }
  }

  public String getSlug() {
    return slug;
  }

  public void setSlug(String slug) {
    this.slug = slug;
  }

  public Author getAuthor() {
    return author;
  }

  public void setAuthor(Author author) {
    this.author = author;
  }

  public LocalDateTime getPublishedDate() {
    return publishedDate;
  }

  public void setPublishedDate(LocalDateTime publishedDate) {
    this.publishedDate = publishedDate;
  }

  public String getFeaturedImage() {
    return featuredImage;
  }

  public void setFeaturedImage(String featuredImage) {
    this.featuredImage = featuredImage;
  }

  public Integer getFeaturedImageWidth() {
    return featuredImageWidth;
  }

  public void setFeaturedImageWidth(Integer featuredImageWidth) {
    this.featuredImageWidth = featuredImageWidth;
  }

  public Integer getFeaturedImageHeight() {
    return featuredImageHeight;
  }

  public void setFeaturedImageHeight(Integer featuredImageHeight) {
    this.featuredImageHeight = featuredImageHeight;
  }

  public Set<ArticleContent> getContents() {
    return contents;
  }

  public void setContents(Set<ArticleContent> contents) {
    this.contents = contents;
  }

  public Set<Event> getRelatedEvents() {
    return relatedEvents;
  }

  public void setRelatedEvents(Set<Event> relatedEvents) {
    this.relatedEvents = relatedEvents;
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
    return new StringJoiner(", ", Article.class.getSimpleName() + "[", "]").add(
            "id=" + this.getId())
        .add("slug='" + slug + "'")
        .add("authorId=" + Optional.ofNullable(author).map(Author::getId).orElse(null))
        .add("publishedDate=" + publishedDate)
        .toString();
  }
}
