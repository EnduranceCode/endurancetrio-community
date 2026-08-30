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

package com.endurancetrio.data.competitor.model.entity;

import com.endurancetrio.data.competitor.model.converter.AgeGroupConverter;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.event.model.converter.GenderCategoryConverter;
import com.endurancetrio.data.event.model.entity.Race;
import com.endurancetrio.data.event.model.enumerator.GenderCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

/**
 * The {@link RelayEntry} entity represents a one-off relay competitor in a {@link Race}.
 * <p>
 * A relay entry is not a real {@link Team team} (a club or organization). It is an entry on a race result table:
 * a group of athletes that enters a specific race as a relay. It inherits the canonical {@link #getFullName() fullName}
 * from {@link Team}.
 * <p>
 * Besides the fields inherited from {@link Team}, the {@link RelayEntry}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getGenderCategory() genderCategory} : the {@link GenderCategory} of the relay
 *     entry for this specific {@link Race},
 *     if applicable.
 *   </li>
 *   <li>
 *     {@link #getAgeGroup() ageGroup} : the {@link AgeGroup} of the relay entry for this
 *     specific {@link Race}, if applicable.
 *   </li>
 *   <li>
 *     {@link #getAthletes() athletes} : the {@link Athlete athletes} that make up this one-off
 *     relay entry. A relay is a group of athletes entering a specific {@link Race} together, and
 *     unlike a {@link Team club} it has no enduring roster. This set records the contributing
 *     members, typically when the relay's athletes have no individual results of their own.
 *   </li>
 * </ul>
 */
@Entity(name = "RelayEntry")
@Table(name = "relay_entry")
public class RelayEntry extends Team implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Convert(converter = GenderCategoryConverter.class)
  @Column(name = "gender_category")
  private GenderCategory genderCategory;

  @Convert(converter = AgeGroupConverter.class)
  @Column(name = "age_group")
  private AgeGroup ageGroup;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "relay_entry_athlete",
      joinColumns = @JoinColumn(name = "relay_entry_id"),
      inverseJoinColumns = @JoinColumn(name = "athlete_id")
  )
  private Set<Athlete> athletes;

  /**
   * Default constructor for the {@link RelayEntry} entity.
   */
  public RelayEntry() {
    super();
    this.athletes = new HashSet<>();
  }

  /**
   * Adds an {@link Athlete} to this {@link RelayEntry}'s set of contributing members.
   *
   * @param athlete the {@link Athlete} to add; ignored if {@code null} or already present
   */
  public void addAthlete(Athlete athlete) {
    if (athlete != null) {
      this.athletes.add(athlete);
    }
  }

  /**
   * Removes an {@link Athlete} from this {@link RelayEntry}'s set of contributing members.
   *
   * @param athlete the {@link Athlete} to remove; ignored if {@code null} or not present
   */
  public void removeAthlete(Athlete athlete) {
    if (athlete != null) {
      this.athletes.remove(athlete);
    }
  }

  public GenderCategory getGenderCategory() {
    return genderCategory;
  }

  public void setGenderCategory(GenderCategory genderCategory) {
    this.genderCategory = genderCategory;
  }

  public AgeGroup getAgeGroup() {
    return ageGroup;
  }

  public void setAgeGroup(AgeGroup ageGroup) {
    this.ageGroup = ageGroup;
  }

  public Set<Athlete> getAthletes() {
    return athletes;
  }

  public void setAthletes(Set<Athlete> athletes) {
    this.athletes = athletes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    RelayEntry relayEntry = (RelayEntry) o;
    return super.getId() != null && Objects.equals(super.getId(), relayEntry.getId());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", RelayEntry.class.getSimpleName() + "[", "]").add("id=" + super.getId())
        .add("fullName='" + super.getFullName() + "'")
        .add("genderCategory=" + genderCategory)
        .add("ageGroup=" + ageGroup)
        .add("athletes=" + Optional.ofNullable(athletes).map(a -> a.stream().map(Athlete::getId).toList()).orElse(null))
        .toString();
  }
}
