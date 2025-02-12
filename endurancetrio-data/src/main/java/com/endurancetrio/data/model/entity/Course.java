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

import com.endurancetrio.data.model.converter.SportConverter;
import com.endurancetrio.data.model.enumerator.Sport;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * The {@link Course} entity represents an {@link Event}'s {@link Course course}.
 * <p>
 * The field {@link #getTitle() title} uniquely identifies it in relation to any other
 * {@link Course courses} of the {@link Event}. Typically, it consists of the name of the
 * {@link Course course}'s {@link Sport sport} and its distance, such as "Sprint Triathlon" or
 * "Standard Duathlon". When an {@link Event} has different {@link Course courses} with the same
 * distance (e.g. with different routes), a second reference should be added to the name of the
 * programs so that they can be unambiguously differentiated.
 * <p>
 * The field {@link #getSport() sport} is the type of sport that the {@link Course} is related to.
 * <p>
 * The {@link Course} also has a one-to-one relationship with a {@link Distance} field that contains
 * the {@link Course}'s {@link Distance}.
 */
@Entity(name = "Course")
@Table(name = "course")
public class Course implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
  private Event event;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "sport", nullable = false)
  @Convert(converter = SportConverter.class)
  private Sport sport;

  @OneToOne(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
  private Distance distance;

  public Course() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Event getEvent() {
    return event;
  }

  public void setEvent(Event event) {
    this.event = event;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Sport getSport() {
    return sport;
  }

  public void setSport(Sport sport) {
    this.sport = sport;
  }

  public Distance getDistance() {
    return distance;
  }

  public void setDistance(Distance distance) {
    this.distance = distance;
  }

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Course course = (Course) o;
    return Objects.equals(id, course.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Course.class.getSimpleName() + "[", "]").add("id=" + id)
        .add("event=" + (event != null ? event.getId() : "null"))
        .add("title='" + title + "'")
        .add("sport=" + sport)
        .add("distance=" + (distance != null ? distance.getId() : "null"))
        .toString();
  }
}
