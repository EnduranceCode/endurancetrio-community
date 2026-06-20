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

import com.endurancetrio.data.common.model.entity.BaseEntity;
import com.endurancetrio.data.competitor.model.converter.AthleteGenderConverter;
import com.endurancetrio.data.competitor.model.converter.CountryConverter;
import com.endurancetrio.data.competitor.model.enumerator.AthleteGender;
import com.endurancetrio.data.competitor.model.enumerator.Country;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serial;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * The {@link Athlete} entity represents an individual competitor in the system.
 * <p>
 * The {@link Athlete}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getId() id} : the unique identifier of the {@link Athlete} that
 *     is automatically generated and is the primary key.
 *   </li>
 *   <li>
 *     {@link #getFullName() fullName} : the athlete's full name.
 *   </li>
 *   <li>
 *     {@link #getKnownName() knownName} : the athlete's known or preferred name.
 *   </li>
 *   <li>
 *     {@link #getGender() gender} : the {@link AthleteGender} of the athlete.
 *   </li>
 *   <li>
 *     {@link #getCountry() country} : the athlete's {@link Country} of representation.
 *   </li>
 *   <li>
 *     {@link #getYearOfBirth() yearOfBirth} : the athlete's year of birth.
 *   </li>
 * </ul>
 */
@Entity(name = "Athlete")
@Table(name = "athlete")
@SequenceGenerator(
    name = "seq_endurancetrio_generator", sequenceName = "seq_athlete_id", allocationSize = 1
)
public class Athlete extends BaseEntity<Long> {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(name = "known_name")
  private String knownName;

  @Column(name = "gender")
  @Convert(converter = AthleteGenderConverter.class)
  private AthleteGender gender;

  @Column(name = "country")
  @Convert(converter = CountryConverter.class)
  private Country country;

  @Column(name = "year_of_birth")
  private Integer yearOfBirth;

  public Athlete() {
    super();
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getKnownName() {
    return knownName;
  }

  public void setKnownName(String knownName) {
    this.knownName = knownName;
  }

  public AthleteGender getGender() {
    return gender;
  }

  public void setGender(AthleteGender gender) {
    this.gender = gender;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public Integer getYearOfBirth() {
    return yearOfBirth;
  }

  public void setYearOfBirth(Integer yearOfBirth) {
    this.yearOfBirth = yearOfBirth;
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
    return new StringJoiner(", ", Athlete.class.getSimpleName() + "[", "]")
        .add("id=" + this.getId())
        .add("name='" + fullName + "'")
        .add("knownName='" + knownName + "'")
        .add("gender=" + Optional.ofNullable(gender).map(AthleteGender::getCode).orElse(null))
        .add("country=" + Optional.ofNullable(country).map(Country::getCode).orElse(null))
        .add("yearOfBirth=" + yearOfBirth)
        .toString();
  }
}
