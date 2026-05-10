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

package com.endurancetrio.data.event.model.entity;

import com.endurancetrio.data.common.model.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serial;
import java.util.StringJoiner;

/**
 * The {@link AgeGroup} entity represents the participants age group category allowed to
 * enter a {@link Race}.
 * <p>
 * The {@link AgeGroup}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getId() id} : the unique identifier of the {@link AgeGroup} that
 *     is automatically generated and is the primary key.
 *   </li>
 *   <li>{@link #getTitle() title} : the full title of the {@link AgeGroup age group}.</li>
 *   <li>
 *     {@link #getShortTitle() shortTitle} : the abbreviated title of the {@link AgeGroup age group}.
 *   </li>
 * </ul>
 */
@Entity(name = "AgeGroup")
@Table(name = "age_group")
@SequenceGenerator(name = "seq_endurancetrio_generator", sequenceName = "seq_age_group_id", allocationSize = 1)
public class AgeGroup extends BaseEntity<Long> {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "short_title", nullable = false)
  private String shortTitle;

  /**
   * Default constructor for the {@link AgeGroup} entity.
   */
  public AgeGroup() {
    super();
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getShortTitle() {
    return shortTitle;
  }

  public void setShortTitle(String shortTitle) {
    this.shortTitle = shortTitle;
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
    return new StringJoiner(", ", AgeGroup.class.getSimpleName() + "[", "]")
        .add("id=" + this.getId())
        .add("title='" + title + "'")
        .add("shortTitle='" + shortTitle + "'")
        .toString();
  }
}
