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

import com.endurancetrio.data.event.model.enumerator.Sport;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The {@link BiathlonDistance} entity extends the {@link Distance} entity and represents the data
 * of a {@link Sport#BIATHLON Biathlon} {@link Course}'s {@link Distance}.
 * <p>
 * Besides the fields inherited from {@link Distance}, the {@link BiathlonDistance}'s
 * fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getBikeDistance() bikeDistance} : the distance, in meters, of the bike leg
 *     of the {@link Course}.
 *   </li>
 *   <li>
 *     {@link #getBikeLaps() bikeLaps} : the number of laps that the bike distance is divided into.
 *   </li>
 *   <li>
 *     {@link #getRunDistance() runDistance} : the distance, in meters, of the run leg
 *     of the {@link Course}.
 *   </li>
 *   <li>
 *     {@link #getRunLaps() runLaps} : the number of laps that the run distance is divided into.
 *   </li>
 * </ul>
 */
@Entity(name = "BiathlonDistance")
@Table(name = "biathlon_distance")
public class BiathlonDistance extends Distance implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "bike_distance")
  private Integer bikeDistance;

  @Column(name = "bike_laps")
  private Integer bikeLaps;

  @Column(name = "run_distance")
  private Integer runDistance;

  @Column(name = "run_laps")
  private Integer runLaps;

  /**
   * Default constructor for the {@link BiathlonDistance} entity.
   */
  public BiathlonDistance() {
    super();
  }

  public Integer getBikeDistance() {
    return bikeDistance;
  }

  public void setBikeDistance(Integer bikeDistance) {
    this.bikeDistance = bikeDistance;
  }

  public Integer getBikeLaps() {
    return bikeLaps;
  }

  public void setBikeLaps(Integer bikeLaps) {
    this.bikeLaps = bikeLaps;
  }

  public Integer getRunDistance() {
    return runDistance;
  }

  public void setRunDistance(Integer runDistance) {
    this.runDistance = runDistance;
  }

  public Integer getRunLaps() {
    return runLaps;
  }

  public void setRunLaps(Integer runLaps) {
    this.runLaps = runLaps;
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
    BiathlonDistance biathlonDistance = (BiathlonDistance) o;
    return super.getId() != null && Objects.equals(super.getId(), biathlonDistance.getId());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", BiathlonDistance.class.getSimpleName() + "[", "]")
        .add("id=" + super.getId())
        .add("type=" + super.getDistanceType())
        .add("bikeDistance=" + bikeDistance)
        .add("bikeLaps=" + bikeLaps)
        .add("runDistance=" + runDistance)
        .add("runLaps=" + runLaps)
        .toString();
  }
}
