/*
 * Copyright (c) 2011-2025 Ricardo do Canto
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

import com.endurancetrio.data.model.enumerator.WetsuitRule;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The {@link TriathlonBasedRace} class represents a triathlon based {@link Race} such as Aquathlon,
 * Aquabike or Triathlon.
 * <p>
 * Besides the fields inherited from the {@link Race}, the {@link TriathlonBasedRace}'s fields
 * are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getWaterTemperature() waterTemperature} : the official water temperature
 *     for the {@link Race}
 *   </li>
 *   <li>
 *     {@link #getWetsuitRule() wetSuitRule} : the {@link WetsuitRule wetsuit rule}
 *     for the {@link Race}
 *   </li>
 * </ul>
 */
@Entity(name = "TriathlonBasedRace")
@Table(name = "triathlon_based_race")
public class TriathlonBasedRace extends Race implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "water_temperature", nullable = true)
  private Double waterTemperature;

  @Column(name = "wetsuit_rule", nullable = false)
  private WetsuitRule wetsuitRule;

  /**
   * Default constructor for the {@link TriathlonBasedRace} entity.
   */
  public TriathlonBasedRace() {
    super();
  }

  public Double getWaterTemperature() {
    return waterTemperature;
  }

  public void setWaterTemperature(Double waterTemperature) {
    this.waterTemperature = waterTemperature;
  }

  public WetsuitRule getWetsuitRule() {
    return wetsuitRule;
  }

  public void setWetsuitRule(WetsuitRule wetsuitRule) {
    this.wetsuitRule = wetsuitRule;
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
    TriathlonBasedRace that = (TriathlonBasedRace) o;
    return super.getId() != null && Objects.equals(super.getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), waterTemperature);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", TriathlonBasedRace.class.getSimpleName() + "[", "]")
        .add("id=" + super.getId())
        .add("raceReference='" + super.getRaceReference() + "'")
        .add("raceType=" + super.getRaceType().getCode())
        .add("title='" + super.getTitle() + "'")
        .add("subtitle='" + super.getSubtitle() + "'")
        .add("genderCategory=" + super.getGenderCategory().getCode())
        .add("ageGroup=" + super.getAgeGroup().getTitle())
        .add("date=" + super.getDate())
        .add("time=" + super.getTime())
        .add("raceStatus=" + super.getRaceStatus().getCode())
        .add("gunTime=" + super.getGunTime())
        .add("airTemperature=" + super.getAirTemperature())
        .add("waterTemperature=" + waterTemperature)
        .add("wetsuitRule=" + wetsuitRule)
        .toString();
  }
}
