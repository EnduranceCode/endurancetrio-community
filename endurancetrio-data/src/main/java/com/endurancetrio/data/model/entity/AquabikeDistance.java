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

import com.endurancetrio.data.model.enumerator.Sport;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The {@link AquabikeDistance} entity extends the {@link Distance} entity and represents the data
 * of a {@link Sport#AQUABIKE Aquabike} {@link Course}'s {@link Distance}.
 * <p>
 * Besides the fields inherited from {@link Distance}, the {@link AquabikeDistance}'s
 * fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getSwimDistance() swimDistance} : the distance, in meters, of the swim leg
 *     of the {@link Course}.
 *   </li>
 *   <li>
 *     {@link #getSwimLaps() swimLaps} : the number of laps that the swim distance is divided into.
 *   </li>
 *   <li>
 *     {@link #getBikeDistance() bikeDistance} : the distance, in meters, of the bike leg
 *     of the {@link Course}.
 *   </li>
 *   <li>
 *     {@link #getBikeLaps() bikeLaps} : the number of laps that the bike distance is divided into.
 *   </li>
 * </ul>
 */
@Entity(name = "AquabikeDistance")
@Table(name = "aquabike_distance")
public class AquabikeDistance extends Distance implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "swim_distance")
  private Integer swimDistance;

  @Column(name = "swim_laps")
  private Integer swimLaps;

  @Column(name = "bike_distance")
  private Integer bikeDistance;

  @Column(name = "bike_laps")
  private Integer bikeLaps;

  /**
   * Default constructor for the {@link AquabikeDistance} entity.
   */
  public AquabikeDistance() {
    super();
  }

  public Integer getSwimDistance() {
    return swimDistance;
  }

  public void setSwimDistance(Integer swimDistance) {
    this.swimDistance = swimDistance;
  }

  public Integer getSwimLaps() {
    return swimLaps;
  }

  public void setSwimLaps(Integer swimLaps) {
    this.swimLaps = swimLaps;
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
    AquabikeDistance aquabikeDistance = (AquabikeDistance) o;
    return super.getId() != null && Objects.equals(super.getId(), aquabikeDistance.getId());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", AquabikeDistance.class.getSimpleName() + "[", "]")
        .add("id=" + super.getId())
        .add("type=" + super.getDistanceType())
        .add("swimDistance=" + swimDistance)
        .add("swimLaps=" + swimLaps)
        .add("bikeDistance=" + bikeDistance)
        .add("bikeLaps=" + bikeLaps)
        .toString();
  }
}
