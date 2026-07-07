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
import com.endurancetrio.data.competitor.model.entity.Athlete;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serial;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * The {@link Author} entity represents an author profile for the Insights section.
 * <p>
 * The {@link Author}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getId() id} : the unique identifier of the {@link Author} that
 *     is automatically generated and is the primary key.
 *   </li>
 *   <li>
 *     {@link #getKnownName() knownName} : the display name of the {@link Author},
 *     which must be unique.
 *   </li>
 *   <li>
 *     {@link #getBio() bio} : an optional biography of the {@link Author}.
 *   </li>
 *   <li>
 *     {@link #getProfileFileName() profileFileName} : the optional file name of the
 *     {@link Author}'s profile image.
 *   </li>
 *   <li>
 *     {@link #getAthlete() athlete} : the optional {@link Athlete} associated with
 *     the {@link Author}.
 *   </li>
 * </ul>
 */
@Entity(name = "Author")
@Table(name = "author")
@SequenceGenerator(
    name = "seq_endurancetrio_generator", sequenceName = "seq_author_id", allocationSize = 1
)
public class Author extends BaseEntity<Long> {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "known_name", nullable = false, unique = true)
  private String knownName;

  @Column(name = "bio")
  private String bio;

  @Column(name = "profile_file_name")
  private String profileFileName;

  @OneToOne(fetch = FetchType.LAZY, optional = true)
  @JoinColumn(name = "athlete_id")
  private Athlete athlete;

  public Author() {
    super();
  }

  public String getKnownName() {
    return knownName;
  }

  public void setKnownName(String knownName) {
    this.knownName = knownName;
  }

  public String getBio() {
    return bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getProfileFileName() {
    return profileFileName;
  }

  public void setProfileFileName(String profileFileName) {
    this.profileFileName = profileFileName;
  }

  public Athlete getAthlete() {
    return athlete;
  }

  public void setAthlete(Athlete athlete) {
    this.athlete = athlete;
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
    return new StringJoiner(", ", Author.class.getSimpleName() + "[", "]").add("id=" + this.getId())
        .add("knownName='" + knownName + "'")
        .add("athleteId=" + Optional.ofNullable(athlete).map(Athlete::getId).orElse(null))
        .toString();
  }
}
