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

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * The {@link Venue} entity represents a venue of a race of an endurance sport event.
 * <p>
 * The field {@link #getTitle() title} stores the {@link Venue venue}'s title.
 * <p>
 * The {@link #getCity() city}, {@link #getCounty() county} and {@link #getDistrict() district}
 * fields are used to store the city, county and district of the race's {@link Venue venue}.
 */
@Entity(name = "Venue")
@Table(name = "venue")
public class Venue implements Serializable {

  private static final long serialVersionUID = 7978080716989864204L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "district", nullable = false)
  private String district;

  @Column(name = "county", nullable = false)
  private String county;

  @Column(name = "city", nullable = false)
  private String city;

  public Venue() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
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

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }
}
