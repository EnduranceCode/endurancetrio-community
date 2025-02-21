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
 * The {@link DoubleBiathlonDistance} entity extends the {@link Distance} entity and represents the
 * data of a {@link Sport#DOUBLE_BIATHLON Double Biathlon} {@link Course}'s {@link Distance}.
 * <p>
 * Besides the fields inherited from {@link Distance}, the {@link DoubleBiathlonDistance}'s
 * fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getFirstBikeDistance() firstBikeDistance} : the distance, in meters, of the bike leg
 *     of the {@link Course}.
 *   </li>
 *   <li>
 *     {@link #getFirstBikeLaps() firstBikeLaps} : the number of laps that the bike distance
 *     is divided into.
 *   </li>
 *   <li>
 *     {@link #getFirstRunDistance() firstRunDistance} : the distance, in meters,
 *     of the first run leg of the {@link Course}.
 *   </li>
 *   <li>
 *     {@link #getFirstRunLaps() firstRunLaps} : the number of laps that the first run distance
 *     is divided into.
 *   </li>
 *   <li>
 *     {@link #getSecondBikeDistance() secondBikeDistance} : the distance, in meters,
 *     of the bike leg of the {@link Course}.
 *   </li>
 *   <li>
 *     {@link #getSecondBikeLaps() secondBikeLaps} : the number of laps that the bike distance
 *     is divided into.
 *   </li>
 *   <li>
 *     {@link #getSecondRunDistance() secondRunDistance} : the distance, in meters,
 *     of the second run leg of the {@link Course}.
 *   </li>
 *   <li>
 *     {@link #getSecondRunLaps() secondRunLaps} : the number of laps that the second run distance
 *     is divided into.
 *   </li>
 * </ul>
 */
@Entity(name = "DoubleBiathlonDistance")
@Table(name = "double_biathlon_distance")
public class DoubleBiathlonDistance extends Distance implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "first_bike_distance")
  private Integer firstBikeDistance;

  @Column(name = "firstBike_laps")
  private Integer firstBikeLaps;

  @Column(name = "first_run_distance")
  private Integer firstRunDistance;

  @Column(name = "first_run_laps")
  private Integer firstRunLaps;

  @Column(name = "second_bike_distance")
  private Integer secondBikeDistance;

  @Column(name = "second_bike_laps")
  private Integer secondBikeLaps;

  @Column(name = "second_run_distance")
  private Integer secondRunDistance;

  @Column(name = "second_run_laps")
  private Integer secondRunLaps;

  /**
   * Default constructor for the {@link DoubleBiathlonDistance} entity.
   */
  public DoubleBiathlonDistance() {
    super();
  }

  public Integer getFirstBikeDistance() {
    return firstBikeDistance;
  }

  public void setFirstBikeDistance(Integer firstBikeDistance) {
    this.firstBikeDistance = firstBikeDistance;
  }

  public Integer getFirstBikeLaps() {
    return firstBikeLaps;
  }

  public void setFirstBikeLaps(Integer firstBikeLaps) {
    this.firstBikeLaps = firstBikeLaps;
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

  public Integer getSecondBikeDistance() {
    return secondBikeDistance;
  }

  public void setSecondBikeDistance(Integer secondBikeDistance) {
    this.secondBikeDistance = secondBikeDistance;
  }

  public Integer getSecondBikeLaps() {
    return secondBikeLaps;
  }

  public void setSecondBikeLaps(Integer secondBikeLaps) {
    this.secondBikeLaps = secondBikeLaps;
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
    DoubleBiathlonDistance doubleBiathlonDistance = (DoubleBiathlonDistance) o;
    return super.getId() != null && Objects.equals(super.getId(), doubleBiathlonDistance.getId());
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", DoubleBiathlonDistance.class.getSimpleName() + "[", "]")
        .add("id=" + super.getId())
        .add("type=" + super.getType())
        .add("firstBikeDistance=" + firstBikeDistance)
        .add("firstBikeLaps=" + firstBikeLaps)
        .add("firstRunDistance=" + firstRunDistance)
        .add("firstRunLaps=" + firstRunLaps)
        .add("secondBikeDistance=" + secondBikeDistance)
        .add("secondBikeLaps=" + secondBikeLaps)
        .add("secondRunDistance=" + secondRunDistance)
        .add("secondRunLaps=" + secondRunLaps)
        .toString();
  }
}
