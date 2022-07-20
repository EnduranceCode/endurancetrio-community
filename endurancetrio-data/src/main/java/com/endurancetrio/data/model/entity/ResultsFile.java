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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity(name = "ResultsFile")
@Table(name = "results_file")
public class ResultsFile {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.PERSIST, CascadeType.MERGE,
      CascadeType.REFRESH, CascadeType.DETACH}, optional = false)
  @JoinColumn(name = "results_file_id", nullable = false)
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
