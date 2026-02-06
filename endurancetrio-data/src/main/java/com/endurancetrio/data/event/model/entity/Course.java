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

import com.endurancetrio.data.event.model.converter.SportConverter;
import com.endurancetrio.data.event.model.enumerator.Sport;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

/**
 * The {@link Course} entity represents a {@link Course course}
 * used by an {@link Event}'s {@link Race}.
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
 *     of the name of the {@link Course}'s {@link Sport sport} and its distance, such as
 *     "Sprint Triathlon" or "Standard Duathlon". When an {@link Event} has different
 *     {@link Course courses} with the same distance (e.g. with different routes), a second
 *     reference should be added to the title of the {@link Course} so that they can be
 *     unambiguously differentiated.
 *   </li>
 *   <li>
 *     {@link #getSport() sport} : the {@link Sport} of the {@link Race} that the {@link Course}
 *     is used for.
 *   </li>
 *   <li>
 *     {@link #getDistance() distance} : the data of a {@link Course}'s {@link Distance}.
 *     Each {@link Course} has a unique {@link Distance}, and distances are not shared
 *     across multiple courses. The relationship is unidirectional, meaning {@link Distance}
 *     does not have a reference back to {@link Course}. Deleting a {@link Course} will also
 *     delete its associated {@link Distance} due to cascading and orphan removal settings.
 *   </li>
 *   <li>
 *     {@link #getRaces() races} : the {@link Race races} that the {@link Course} is used for.
 *   </li>
 * </ul>
 */
@Entity(name = "Course")
@Table(name = "course")
public class Course implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sq_course")
  @SequenceGenerator(name = "sq_course", sequenceName = "sq_course", allocationSize = 1)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "event_id", referencedColumnName = "id", nullable = false)
  private Event event;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "sport", nullable = false)
  @Convert(converter = SportConverter.class)
  private Sport sport;

  @OneToOne(
      fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = true, orphanRemoval = true
  )
  @JoinColumn(name = "distance_id", referencedColumnName = "id")
  private Distance distance;

  @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinTable(
      name = "course_race",
      joinColumns = @JoinColumn(name = "course_id"),
      inverseJoinColumns = @JoinColumn(name = "race_id")
  )
  private Set<Race> races;

  /**
   * Default constructor for the {@link Course} entity.
   */
  public Course() {
    super();
    this.races = new HashSet<>();
  }

  /**
   * Adds a {@link Race} to this {@link Course}'s {@link #getRaces() races} collection. Ensures
   * bidirectional consistency by also adding this {@link Course} to the {@link Race}'s
   * {@link Race#getCourses() courses} collection.
   *
   * @param race the {@link Race} to add; ignored if {@code null} or already present
   */
  public void addRace(Race race) {
    if (race != null && this.races.add(race)) {
      race.addCourse(this);
    }
  }

  /**
   * Removes a {@link Race} from this {@link Course}'s {@link #getRaces() races} collection. Ensures
   * bidirectional consistency by also removing this {@link Course} from the {@link Race}'s
   * {@link Race#getCourses() courses} collection.
   *
   * @param race the {@link Race} to remove; ignored if {@code null} or not present
   */
  public void removeRace(Race race) {
    if (race != null && this.races.remove(race)) {
      race.removeCourse(this);
    }
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
    if (event != null) {
      event.getCourses().add(this);
    }
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
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Course.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("eventId=" + Optional.ofNullable(event).map(Event::getId).orElse(null))
        .add("title='" + title + "'")
        .add("sport=" + sport)
        .add("distanceId=" + Optional.ofNullable(distance).map(Distance::toString).orElse(null))
        .toString();
  }
}
