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

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

/**
 * The {@link Event} entity represents an endurance sport {@link Event} that can have
 * one or more endurance {@link Race races}.
 * <p>
 * The {@link Event}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getId() id} : the unique identifier of the {@link Event} that
 *     is automatically generated and is the primary key.
 *   </li>
 *   <li>
 *     {@link #getEventReference() eventReference} : the unique {@link Event} reference number
 *     that is exactly 14 characters long and has the format "YYYYMMDDXXXNNN". In this format,
 *     "YYYY" corresponds to the year of the {@link Event} {@link  #getStartDate() startDate},
 *     "MM" corresponds to the month of the {@link Event} {@link  #getStartDate() startDate},
 *     "DD" corresponds to the day of the {@link Event} {@link  #getStartDate() startDate},
 *     "XXX" corresponds to the {@link Event}'s scope code (written in upper case),
 *     and "NNN" corresponds to the {@link Event}'s order number. The {@link Event}'s order number
 *     is the number corresponding to the chronological order of {@link Event}'s with
 *     the same {@link  #getStartDate() startDate} and the same scope. That is, the first
 *     {@link Event} with the same {@link  #getStartDate() startDate} and scope has an order number
 *     (""NNN"") of 001, the second {@link Event} with that same {@link  #getStartDate() startDate}
 *     and scope has an order number of 002, and so on.
 *   </li>
 *   <li>{@link #getTitle() title} : the title of the {@link Event}.</li>
 *   <li>{@link #getStartDate() startDate} : the start date of the {@link Event}.</li>
 *   <li>
 *     {@link #getEndDate() endDate} : the end date of the {@link Event}. As an {@link Event} can be
 *     held on a single day or span several days, the {@link #getEndDate() endDate} must be equals
 *     or after the {@link #getStartDate() startDate}.
 *   </li>
 *   <li>
 *     {@link #getDistrict() district} : the district of the {@link Event event}'s main location.
 *   </li>
 *   <li>{@link #getCounty() county} : the county of the {@link Event event}'s main location.</li>
 *   <li>{@link #getCity() city} : the city of the {@link Event event}'s main location.</li>
 *   <li>
 *     {@link #getOrganizers() organizers} : the {@link Organizer} of the {@link Event}.
 *     An {@link Event event} can have one or multiple {@link Organizer organizers}.
 *   </li>
 *   <li>
 *     {@link #getEventFiles() eventFiles} : the list of {@link EventFile event files}
 *     of the {@link Event}. An {@link Event event} can have zero or multiple
 *     {@link EventFile event files}.
 *   </li>
 *   <li>
 *     {@link #getCourses() courses} : the {@link Course courses} of the {@link Event}.
 *     An {@link Event} can have one or multiple {@link Course courses}.
 *   </li>
 * </ul>
 */
@Entity(name = "Event")
@Table(name = "event")
public class Event implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "event_reference", unique = true, nullable = false)
  @Pattern(
      regexp = "^\\d{8}[A-Z]{3}\\d{3}$",
      message = "Event reference must follow the format YYYYMMDDXXXNNN (e.g., 19840815NAC001)"
  )
  private String eventReference;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @Column(name = "district", nullable = false)
  private String district;

  @Column(name = "county", nullable = false)
  private String county;

  @Column(name = "city", nullable = false)
  private String city;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "event_organizer",
      joinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "organizer_id", referencedColumnName = "id")}
  )
  private Set<Organizer> organizers;

  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private Set<EventFile> eventFiles;

  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private Set<Course> courses;

  /**
   * Default constructor for the {@link Event} entity.
   */
  public Event() {
    super();
    this.organizers = new HashSet<>();
    this.eventFiles = new HashSet<>();
    this.courses = new HashSet<>();
  }

  @AssertTrue(message = "Start date must be before or equal to end date")
  private boolean isValidDateRange() {
    return startDate != null && endDate != null && !startDate.isAfter(endDate);
  }

  /**
   * Adds an {@link EventFile} to the {@link Event}. This method also sets the {@link Event}
   * reference in the {@link EventFile}.
   *
   * @param eventFile the {@link EventFile} to be added
   */
  public void addEventFile(EventFile eventFile) {
    this.eventFiles.add(eventFile);
    eventFile.setEvent(this);
  }

  /**
   * Removes an {@link EventFile} from the {@link Event}. This method also removes the {@link Event}
   * reference from the {@link EventFile}.
   *
   * @param eventFile the {@link EventFile} to be removed
   */
  public void removeEventFile(EventFile eventFile) {
    this.eventFiles.remove(eventFile);
    eventFile.setEvent(null);
  }

  /**
   * Adds an {@link Course} to the {@link Event}. This method also sets the {@link Event}
   * reference in the {@link Course}.
   *
   * @param course the {@link Course} to be added
   */
  public void addCourse(Course course) {
    courses.add(course);
    course.setEvent(this);
  }

  /**
   * Removes an {@link Course} from the {@link Event}. This method also removes the {@link Event}
   * reference from the {@link Course}.
   *
   * @param course the {@link Course} to be removed
   */
  public void removeCourse(Course course) {
    courses.remove(course);
    course.setEvent(null);
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getEventReference() {
    return eventReference;
  }

  public void setEventReference(String eventReference) {
    this.eventReference = eventReference;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public String getDistrict() {
    return district;
  }

  public void setDistrict(String district) {
    this.district = district;
  }

  public String getCounty() {
    return county;
  }

  public void setCounty(String county) {
    this.county = county;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Set<Organizer> getOrganizers() {
    return organizers;
  }

  public void setOrganizers(Set<Organizer> organizers) {
    this.organizers = organizers;
  }

  public Set<EventFile> getEventFiles() {
    return eventFiles;
  }

  public void setEventFiles(Set<EventFile> eventFiles) {
    this.eventFiles = eventFiles;
  }

  public Set<Course> getCourses() {
    return courses;
  }

  public void setCourses(Set<Course> courses) {
    this.courses = courses;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Event event = (Event) o;
    return id != null && Objects.equals(id, event.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Event.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("eventReference='" + eventReference + "'")
        .add("title='" + title + "'")
        .add("startDate=" + startDate)
        .add("endDate=" + endDate)
        .add("district='" + district + "'")
        .add("county='" + county + "'")
        .add("city='" + city + "'")
        .toString();
  }
}
