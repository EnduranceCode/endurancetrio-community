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

import com.endurancetrio.data.model.converter.DistanceTypeConverter;
import com.endurancetrio.data.model.enumerator.DistanceType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The {@link Distance} entity represents the data of a {@link Course}'s {@link Distance}.
 * <p>
 * The {@link Distance}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getId() id} : the unique identifier of the {@link Distance} that
 *     is automatically generated and is the primary key.
 *   </li>
 *   <li>
 *     {@link #getDistanceType() type} : the {@link DistanceType} that defines the {@link Course}'s
 *     {@link Distance} classification.
 *   </li>
 * </ul>
 */
@Entity(name = "Distance")
@Table(name = "distance")
@Inheritance(strategy = InheritanceType.JOINED)
public class Distance implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_distance")
  @SequenceGenerator(name = "sq_distance", sequenceName = "sq_distance", allocationSize = 1)
  private Long id;

  @Column(name = "distance_type", nullable = false)
  @Convert(converter = DistanceTypeConverter.class)
  private DistanceType distanceType;

  /**
   * Default constructor for the {@link Distance} entity.
   */
  public Distance() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public DistanceType getDistanceType() {
    return distanceType;
  }

  public void setDistanceType(DistanceType distanceType) {
    this.distanceType = distanceType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Distance distance = (Distance) o;
    return id != null && Objects.equals(id, distance.id);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Distance.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("type=" + distanceType)
        .toString();
  }
}
