/*
 * Copyright (c) 2011-2022 Ricardo do Canto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.endurancetrio.data.model.entity;

import com.endurancetrio.data.model.converter.OrganizerTypeConverter;
import com.endurancetrio.data.model.enumerator.OrganizerType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The {@link Organizer} entity represents a single organizer of an endurance sport
 * {@link Event event}.
 * <p>
 * The {@link #getName() name} field stores the name or designation of the {@link Event event}'s
 * {@link Organizer organizer}.
 * <p>
 * The {@link #getCity() city}, {@link #getCounty() county} and {@link #getDistrict() district}
 * fields are used to store the city, county and district of the {@link Event event}'s
 * {@link Organizer organizer} headquarters.
 * <p>
 * The {@link OrganizerType} defines the type of the {@link Event event}'s
 * {@link Organizer organizer}.
 */
@Entity(name = "Organizer")
@Table(name = "organizer")
public class Organizer implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "district", nullable = false)
  private String district;

  @Column(name = "county", nullable = false)
  private String county;

  @Column(name = "city", nullable = false)
  private String city;

  @Convert(converter = OrganizerTypeConverter.class)
  @Column(name = "organizer_type", nullable = false)
  private OrganizerType organizerType;

  public Organizer() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Organizer organizer = (Organizer) o;
    return Objects.equals(id, organizer.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Organizer.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("name='" + name + "'")
        .add("district='" + district + "'")
        .add("county='" + county + "'")
        .add("city='" + city + "'")
        .add("organizerType=" + organizerType)
        .toString();
  }
}
