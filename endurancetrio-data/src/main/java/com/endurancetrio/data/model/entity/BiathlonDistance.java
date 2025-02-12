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
 * The {@link BiathlonDistance} entity extends the {@link Distance} entity and represents the data
 * of a {@link Course}'s {@link Sport#BIATHLON} {@link Distance}.
 * <p>
 * The {@link BiathlonDistance}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getBikeDistance() bikeDistance} - the distance, in meters, of the bike leg
 *     of the {@link Course}
 *   </li>
 *   <li>
 *     {@link #getBikeLaps() bikeLaps} - the number of laps that the bike distance is divided into
 *   </li>
 *   <li>
 *     {@link #getRunDistance() runDistance} - the distance, in meters, of the run leg
 *     of the {@link Course}
 *   </li>
 *   <li>
 *     {@link #getRunLaps() runLaps} - the number of laps that the run distance is divided into
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
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    if (!super.equals(o)) {
      return false;
    }
    BiathlonDistance that = (BiathlonDistance) o;
    return Objects.equals(super.getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.getId());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", BiathlonDistance.class.getSimpleName() + "[", "]")
        .add("id=" + super.getId())
        .add("course=" + (super.getCourse() != null ? super.getCourse().getId() : null))
        .add("type=" + super.getType())
        .add("bikeDistance=" + bikeDistance)
        .add("bikeLaps=" + bikeLaps)
        .add("runDistance=" + runDistance)
        .add("runLaps=" + runLaps)
        .toString();
  }
}
