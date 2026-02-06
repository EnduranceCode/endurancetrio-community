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
 * The {@link DuathlonDistance} entity extends the {@link Distance} entity and represents the data
 * of a {@link Sport#DUATHLON Duathlon} and {@link Sport#CROSS_DUATHLON Cross Duathlon}
 * {@link Course}'s {@link Distance}.
 * <p>
 * Besides the fields inherited from {@link Distance}, the {@link DuathlonDistance}'s
 * fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getFirstRunDistance() firstRundDistance} : the distance, in meters,
 *     of the first run leg of the {@link Course}.
 *   </li>
 *   <li>
 *     {@link #getFirstRunLaps() firstRunLaps} : the number of laps that the first run distance
 *     is divided into.
 *   </li>
 *   <li>
 *     {@link #getBikeDistance() bikeDistance} : the distance, in meters, of the bike leg
 *     of the {@link Course}.
 *   </li>
 *   <li>
 *     {@link #getBikeLaps() bikeLaps} : the number of laps that the bike distance is divided into.
 *   </li>
 *   <li>
 *     {@link #getSecondRunDistance() secondRunDistance} - the distance, in meters,
 *     of the second run leg of the {@link Course}.
 *   </li>
 *   <li>
 *     {@link #getSecondRunLaps() runLaps} : the number of laps that the second run distance
 *     is divided into.
 *   </li>
 * </ul>
 */
@Entity(name = "DuathlonDistance")
@Table(name = "duathlon_distance")
public class DuathlonDistance extends Distance implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "first_run_distance")
  private Integer firstRunDistance;

  @Column(name = "first_run_laps")
  private Integer firstRunLaps;

  @Column(name = "bike_distance")
  private Integer bikeDistance;

  @Column(name = "bike_laps")
  private Integer bikeLaps;

  @Column(name = "second_run_distance")
  private Integer secondRunDistance;

  @Column(name = "second_run_laps")
  private Integer secondRunLaps;

  /**
   * Default constructor for the {@link DuathlonDistance} entity.
   */
  public DuathlonDistance() {
    super();
  }

  public Integer getFirstRunDistance() {
    return firstRunDistance;
  }

  public void setFirstRunDistance(Integer firstRunDistance) {
    this.firstRunDistance = firstRunDistance;
  }

  public Integer getFirstRunLaps() {
    return firstRunLaps;
  }

  public void setFirstRunLaps(Integer firstRunLaps) {
    this.firstRunLaps = firstRunLaps;
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

  public Integer getSecondRunDistance() {
    return secondRunDistance;
  }

  public void setSecondRunDistance(Integer secondRunDistance) {
    this.secondRunDistance = secondRunDistance;
  }

  public Integer getSecondRunLaps() {
    return secondRunLaps;
  }

  public void setSecondRunLaps(Integer secondRunLaps) {
    this.secondRunLaps = secondRunLaps;
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
    DuathlonDistance duathlonDistance = (DuathlonDistance) o;
    return super.getId() != null && Objects.equals(super.getId(), duathlonDistance.getId());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", DuathlonDistance.class.getSimpleName() + "[", "]")
        .add("id=" + super.getId())
        .add("type=" + super.getDistanceType())
        .add("firstRunDistance=" + firstRunDistance)
        .add("firstRunLaps=" + firstRunLaps)
        .add("bikeDistance=" + bikeDistance)
        .add("bikeLaps=" + bikeLaps)
        .add("secondRunDistance=" + secondRunDistance)
        .add("secondRunLaps=" + secondRunLaps)
        .toString();
  }
}
