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
 * The {@link AquathlonDistance} entity extends the {@link Distance} entity and represents the data
 * of a {@link Sport#AQUATHLON Aquathlon} {@link Course}'s {@link Distance}.
 * <p>
 * Besides the fields inherited from {@link Distance}, the {@link AquathlonDistance}'s
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
 *     {@link #getRunDistance() runDistance} : the distance, in meters, of the run leg
 *     of the {@link Course}.
 *   </li>
 *   <li>
 *     {@link #getRunLaps() runLaps} : the number of laps that the run distance is divided into.
 *   </li>
 * </ul>
 */
@Entity(name = "AquathlonDistance")
@Table(name = "aquathlon_distance")
public class AquathlonDistance extends Distance implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Column(name = "swim_distance")
  private Integer swimDistance;

  @Column(name = "swim_laps")
  private Integer swimLaps;

  @Column(name = "run_distance")
  private Integer runDistance;

  @Column(name = "run_laps")
  private Integer runLaps;

  /**
   * Default constructor for the {@link AquathlonDistance} entity.
   */
  public AquathlonDistance() {
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
    AquathlonDistance that = (AquathlonDistance) o;
    return super.getId() != null && Objects.equals(super.getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.getId());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", AquathlonDistance.class.getSimpleName() + "[", "]")
        .add("id=" + super.getId())
        .add("type=" + super.getType())
        .add("swimDistance=" + swimDistance)
        .add("swimLaps=" + swimLaps)
        .add("runDistance=" + runDistance)
        .add("runLaps=" + runLaps)
        .toString();
  }
}
