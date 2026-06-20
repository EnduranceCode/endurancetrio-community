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
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serial;
import java.util.StringJoiner;

/**
 * The {@link Team} entity represents a collective competitor (club or team).
 * <p>
 * The {@link Team}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getId() id} : the unique identifier of the {@link Team} that
 *     is automatically generated and is the primary key.
 *   </li>
   *   <li>{@link #getFullName() name} : the canonical name of the {@link Team}.</li>
   *   <li>
   *     {@link #getShortName() shortName} : the abbreviated or short name for the {@link Team}
   *     (e.g., initials like "SC" for a sports club).
   *   </li>
   *   <li>{@link #getCity() city} : the city where the {@link Team} is based.</li>
 *   <li>{@link #getCounty() county} : the county where the {@link Team} is based.</li>
 *   <li>{@link #getDistrict() district} : the district where the {@link Team} is based.</li>
 * </ul>
 */
@Entity(name = "Team")
@Table(name = "team")
@SequenceGenerator(
    name = "seq_endurancetrio_generator", sequenceName = "seq_team_id", allocationSize = 1
)
public class Team extends BaseEntity<Long> {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(name = "short_name")
  private String shortName;

  @Column(name = "city")
  private String city;

  @Column(name = "county")
  private String county;

  @Column(name = "district")
  private String district;

  public Team() {
    super();
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }

  public String getShortName() {
    return shortName;
  }

  public void setShortName(String shortName) {
    this.shortName = shortName;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
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
    return new StringJoiner(", ", Team.class.getSimpleName() + "[", "]")
        .add("id=" + this.getId())
        .add("name='" + fullName + "'")
        .add("shortName='" + shortName + "'")
        .add("city='" + city + "'")
        .add("county='" + county + "'")
        .add("district='" + district + "'")
        .toString();
  }
}
