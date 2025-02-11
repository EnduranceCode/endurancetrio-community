/*
 * Copyright (c) 2011-2022 Ricardo do Canto
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
 * The {@link Event} entity represents an endurance sport event that can have one or more endurance
 * races.
 * <p>
 * The field {@link #getEventReference() eventReference} is a unique identifier of the {@link Event}
 * and contains exactly 14 characters long and has the format "YYYYMMDDXXXNNN". In this format,
 * "YYYY" corresponds to the year of the {@link Event} {@link  #getStartDate() startDate}, "MM"
 * corresponds to the month of the {@link Event} {@link  #getStartDate() startDate}, "DD"
 * corresponds to the day of the {@link Event} {@link  #getStartDate() startDate}, "XXX" corresponds
 * to the {@link Event}'s scope code (written in upper case), and "NNN" corresponds to the
 * {@link Event}'s order number.
 * <p>
 * The {@link Event}'s order number is the number corresponding to the chronological order of
 * {@link Event}'s with the same {@link  #getStartDate() startDate} and the same scope. That is, the
 * first {@link Event} with the same {@link  #getStartDate() startDate} and  scope has an order
 * number (""NNN"") of 001, the second {@link Event} with that same
 * {@link  #getStartDate() startDate} and scope has an order number of 002, and so on.
 * <p>
 * The field {@link #getTitle() title} stores the event title.
 * <p>
 * An event can be held on a single day or span several days. The event's
 * {@link  #getStartDate() startDate} is the start date of the event's first race and the event's
 * {@link #getEndDate() endDate} is the end date of the event's last race.
 * <p>
 * An event can have one or multiple {@link Organizer organizers} and zero or multiple
 * {@link EventFile}.
 */
@Entity(name = "Event")
@Table(name = "event")
public class Event implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Pattern(regexp = "\\d{8}[A-Z]{3}\\d{3}", message = "Invalid event reference format")
  @Column(name = "event_reference", unique = true, nullable = false)
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

  @ManyToMany
  @JoinTable(
      name = "event_organizer",
      joinColumns = {@JoinColumn(name = "event_id", referencedColumnName = "id")},
      inverseJoinColumns = {@JoinColumn(name = "organizer_id", referencedColumnName = "id")}
  )
  private Set<Organizer> organizers = new HashSet<>();

  @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
  private Set<EventFile> eventFiles;

  @AssertTrue(message = "Start date must be before or equal to end date")
  private boolean isValidDateRange() {
    return startDate != null && endDate != null && !startDate.isAfter(endDate);
  }

  public Event() {
    super();
    this.organizers = new HashSet<>();
  }

  public Long getId() {
    return id;
  }

  public String getEventReference() {
    return eventReference;
  }

  public void setEventReference(String eventReference) {
    this.eventReference = eventReference;
  }

  public void setId(Long id) {
    this.id = id;
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

  @Override
  public boolean equals(Object o) {
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Event event = (Event) o;
    return Objects.equals(id, event.id);
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
