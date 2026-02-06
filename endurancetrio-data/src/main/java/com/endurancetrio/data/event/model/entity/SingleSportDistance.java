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
 * The {@link SingleSportDistance} entity extends the {@link Distance} entity and represents the
 * data of a single {@link Sport} {@link Course}'s {@link Distance}.
 * <p>
 * Besides the fields inherited from {@link Distance}, the {@link SingleSportDistance}'s
 * fields are defined as follows:
 * <ul>
 *   <li>{@link #getDistance() distance} : the distance, in meters, of the {@link Course}.</li>
 *   <li>{@link #getLaps() laps} : the number of laps that the distance is divided into.</li>
 * </ul>
 */
@Entity(name = "SingleSportDistance")
@Table(name = "single_sport_distance")
public class SingleSportDistance extends Distance implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "distance")
  private Integer distance;

  @Column(name = "laps")
  private Integer laps;

  /**
   * Default constructor for the {@link SingleSportDistance} entity.
   */
  public SingleSportDistance() {
    super();
  }

  public Integer getDistance() {
    return distance;
  }

  public void setDistance(Integer distance) {
    this.distance = distance;
  }

  public Integer getLaps() {
    return laps;
  }

  public void setLaps(Integer laps) {
    this.laps = laps;
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
    SingleSportDistance singleSportDistance = (SingleSportDistance) o;
    return super.getId() != null && Objects.equals(super.getId(), singleSportDistance.getId());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SingleSportDistance.class.getSimpleName() + "[", "]")
        .add("id=" + super.getId())
        .add("type=" + super.getDistanceType())
        .add("distance=" + distance)
        .add("laps=" + laps)
        .toString();
  }
}
