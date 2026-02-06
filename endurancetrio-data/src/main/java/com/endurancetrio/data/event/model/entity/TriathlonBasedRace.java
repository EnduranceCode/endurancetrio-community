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

import com.endurancetrio.data.event.model.enumerator.WetsuitRule;
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
 *     for the {@link Race}.
 *   </li>
 *   <li>
 *     {@link #getWetsuitRule() wetSuitRule} : the {@link WetsuitRule wetsuit rule}
 *     for the {@link Race} (in Celsius).
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
    TriathlonBasedRace triathlonBasedRace = (TriathlonBasedRace) o;
    return super.getId() != null && Objects.equals(super.getId(), triathlonBasedRace.getId());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
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
