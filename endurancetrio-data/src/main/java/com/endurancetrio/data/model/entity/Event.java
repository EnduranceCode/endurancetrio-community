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

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * The {@link Event} entity represents an endurance sport event that can have one or more endurance
 * races.
 * <p>
 * The field {@link #getTitle() title} stores the event title.
 * <p>
 * An event can be held on a single day or span several days. The event's
 * {@link  #getStartDate() startDate} is the start date of the event's first race and the event's
 * {@link #getEndDate() endDate} is the end date of the event's last race.
 * <p>
 * An event can have one or multiple {@link Organizer organizers}.
 */
@Entity(name = "Event")
@Table(name = "event")
public class Event {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "start_date", nullable = false)
  private LocalDate startDate;

  @Column(name = "end_date", nullable = false)
  private LocalDate endDate;

  @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH,
      CascadeType.DETACH})
  @JoinTable(name = "event_organizer", joinColumns = {
      @JoinColumn(name = "event_id", referencedColumnName = "id")}, inverseJoinColumns = {
      @JoinColumn(name = "organizer_id", referencedColumnName = "id")})
  private Set<Organizer> organizers = new HashSet<>();

  public Event() {
    super();
  }

  public Long getId() {
    return id;
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

  public Set<Organizer> getOrganizers() {
    return organizers;
  }

  public void setOrganizers(Set<Organizer> organizers) {
    this.organizers = organizers;
  }
}
