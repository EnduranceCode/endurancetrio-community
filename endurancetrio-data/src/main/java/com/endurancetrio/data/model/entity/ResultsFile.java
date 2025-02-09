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
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serial;
import java.io.Serializable;

/**
 * The {@link ResultsFile} entity represents the file with the results of a race that is part of an
 * {@link Event event}.
 * <p>
 * The {@link Event} defines the event that includes the race results contained in the
 * {@link ResultsFile}.
 * <p>
 * The field {@link #getRaceTitle() title} stores the race title that is related with the results
 * included in the {@link ResultsFile}.
 * <p>
 * The field {@link #getCompetitionTitle() competitionTitle} stores the competition title that is
 * related with the results included in the {@link ResultsFile}.
 * <p>
 * The field {@link #getFileName()  fileName} stores the name of the
 * {@link ResultsFile results file}.
 * <p>
 * The field {@link #getRevisionNumber() revisionNumber} stores the revision number of the file and
 * the first revision of each file must always be one.
 * <p>
 * The field {@link #getActive() isActive} is a flag that must be set to <i>true</i> for the file,
 * referring to the same content, with the highest revision number. All other files of the same
 * content must have this flag set to <i>false</i>.
 */
@Entity(name = "ResultsFile")
@Table(name = "results_file")
public class ResultsFile implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
      CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
  @JoinColumn(name = "event_id", nullable = false)
  private Event event;

  @Column(name = "race_title", nullable = false)
  private String raceTitle;

  @Column(name = "competition_title", nullable = false)
  private String competitionTitle;

  @Column(name = "file_name", nullable = false)
  private String fileName;

  @Column(name = "revision", nullable = false)
  private Integer revisionNumber;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  public ResultsFile() {
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

  public String getRaceTitle() {
    return raceTitle;
  }

  public void setRaceTitle(String raceTitle) {
    this.raceTitle = raceTitle;
  }

  public String getCompetitionTitle() {
    return competitionTitle;
  }

  public void setCompetitionTitle(String competitionTitle) {
    this.competitionTitle = competitionTitle;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Integer getRevisionNumber() {
    return revisionNumber;
  }

  public void setRevisionNumber(Integer revisionNumber) {
    this.revisionNumber = revisionNumber;
  }

  public Boolean getActive() {
    return isActive;
  }

  public void setActive(Boolean active) {
    isActive = active;
  }
}
