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
import com.endurancetrio.data.event.model.converter.OrganizerTypeConverter;
import com.endurancetrio.data.event.model.enumerator.OrganizerType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serial;
import java.util.StringJoiner;

/**
 * The {@link Organizer} entity represents a single organizer of an endurance sport {@link Event}.
 * <p>
 * The {@link Organizer}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getId() id} : the unique identifier of the {@link Organizer} that is automatically
 *     generated and is the primary key.
 *   </li>
 *   <li>{@link #getName() name} : the name or designation of the {@link Organizer}.</li>
 *   <li>
 *     {@link #getDistrict() district} : the district of the {@link Organizer} headquarters.
 *   </li>
 *   <li>
 *     {@link #getCounty() county} : the county of the {@link Organizer} headquarters.
 *   </li>
 *   <li>{@link #getCity() city} : the city of the {@link Organizer} headquarters.</li>
 *   <li>
 *     {@link #getOrganizerType() organizerType} : the {@link OrganizerType} used to classify
 *     the type of {@link Event event}'s {@link Organizer}.
 *   </li>
 * </ul>
 */
@Entity(name = "Organizer")
@Table(name = "organizer")
@SequenceGenerator(name = "seq_endurancetrio_generator", sequenceName = "seq_organizer_id", allocationSize = 1)
public class Organizer extends BaseEntity<Long> {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "district", nullable = false)
  private String district;

  @Column(name = "county", nullable = false)
  private String county;

  @Column(name = "city", nullable = false)
  private String city;

  @Column(name = "organizer_type", nullable = false)
  @Convert(converter = OrganizerTypeConverter.class)
  private OrganizerType organizerType;

  /**
   * Default constructor for the {@link Organizer} entity.
   */
  public Organizer() {
    super();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public OrganizerType getOrganizerType() {
    return organizerType;
  }

  public void setOrganizerType(OrganizerType organizerType) {
    this.organizerType = organizerType;
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
    return new StringJoiner(", ", Organizer.class.getSimpleName() + "[", "]")
        .add("id=" + this.getId())
        .add("name='" + name + "'")
        .add("district='" + district + "'")
        .add("county='" + county + "'")
        .add("city='" + city + "'")
        .add("organizerType=" + organizerType)
        .toString();
  }
}
