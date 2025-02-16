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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

/**
 * The {@link Course} entity represents an {@link Event}'s {@link Course course}.
 * <p>
 * The {@link Course}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getId() id} : the unique identifier of the {@link Course} that
 *     is automatically generated and is the primary key.
 *   </li>
 *   <li>
 *     {@link #getEvent() event} : the {@link Event} to which the {@link Course} belongs.
 *   </li>
 *   <li>
 *     {@link #getTitle() title} : the title of the {@link Course}, typically consisting
 *     of the name of the {@link Course course}'s {@link Sport sport} and its distance,
 *     such as "Sprint Triathlon" or "Standard Duathlon". When an {@link Event} has different
 *     {@link Course courses} with the same distance (e.g. with different routes), a second
 *     reference should be added to the name of the programs so that they can be
 *     unambiguously differentiated.
 *   </li>
 *   <li>
 *     {@link #getSport() sport} : the type of sport that the {@link Course} is used for.
 *   </li>
 *   <li>
 *     {@link #getDistance() distance} : the data that defines
 *     the {@link Course}'s {@link Distance distance}.
 *   </li>
 *   <li>
 *     {@link #getRaces() races} : the {@link Race races} that use this {@link Course}.
 *   </li>
 * </ul>
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

  @OneToOne(mappedBy = "course", cascade = CascadeType.ALL, optional = true, orphanRemoval = true)
  private Distance distance;

  @ManyToMany(
      mappedBy = "courses", fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE}
  )
  private Set<Race> races;

  /**
   * Default constructor for the {@link Course} entity.
   */
  public Course() {
    super();
    this.races = new HashSet<>();
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

  public Set<Race> getRaces() {
    return races;
  }

  public void setRaces(Set<Race> races) {
    this.races = races;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Course course = (Course) o;
    return id != null && Objects.equals(id, course.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Course.class.getSimpleName() + "[", "]").add("id=" + id)
        .add("eventId=" + Optional.ofNullable(event).map(Event::getId).orElse(null))
        .add("title='" + title + "'")
        .add("sport=" + sport)
        .add("distance=" + (distance != null ? distance.getId() : "null"))
        .toString();
  }
}
