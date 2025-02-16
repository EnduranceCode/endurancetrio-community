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
import java.util.Optional;
import java.util.StringJoiner;

/**
 * The {@link SingleSportDistance} entity extends the {@link Distance} entity and represents the
 * data of a {@link Course}'s single {@link Sport} {@link Distance}.
 * <p>
 * Besides the fields inherited from the {@link Distance}, the {@link SingleSportDistance}'s
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
    SingleSportDistance that = (SingleSportDistance) o;
    return super.getId() != null && Objects.equals(super.getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.getId());
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", SingleSportDistance.class.getSimpleName() + "[", "]")
        .add("id=" + super.getId())
        .add("courseId=" + Optional.ofNullable(super.getCourse()).map(Course::getId).orElse(null))
        .add("type=" + super.getType())
        .add("distance=" + distance)
        .add("laps=" + laps)
        .toString();
  }
}
