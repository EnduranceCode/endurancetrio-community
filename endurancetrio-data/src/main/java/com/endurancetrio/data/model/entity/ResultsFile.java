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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * The {@link ResultsFile} entity represents a file storing the official results of a {@link Race}.
 * <p>
 * The {@link ResultsFile}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getId() id} : the unique identifier of the {@link ResultsFile} that is automatically
 *     generated and is the primary key.
 *   </li>
 *   <li>
 *     {@link #getRace() race} : the {@link Race} whose results the {@link ResultsFile} stores.
 *   </li>
 *   <li>
 *     {@link #getTitle() title} : the title of the {@link Race}. It should ideally match the
 *     championship it belongs to (e.g., "Individual National Championship") or describe the race
 *     distance (e.g., "Duathlon Powerman Mafra Sprint" or "Duathlon Powerman Mafra Standard").
 *   </li>
 *   <li>
 *     {@link #getSubtitle() subtitle} : the subtitle of the {@link Race}. If the
 *     {@link Race#getGenderCategory() genderCategory} alone is sufficient to distinguish the race,
 *     it is used as the {@link #getSubtitle() subtitle} (e.g., "Women"). Otherwise, both the
 *     {@link Race#getAgeGroup() ageGroup} and the {@link Race#getGenderCategory() genderCategory}
 *     are included (e.g., "Elite Women").
 *   </li>
 *   <li>
 *     {@link #getFileName()  fileName} : the name of the {@link ResultsFile} which has exactly
 *     21 characters and follows the format "YYYYMMDDXXXNNN-YYYZ-VV.ext". In this format,
 *     "YYYMMDDXXXNNN-YYY" corresponds to the {@link Race#getRaceReference() raceReference},
 *     "Z" defines the class of the results contained in the {@link ResultsFile}, "VV" corresponds
 *     to the revision/version of the file (the first revision always starts at "01")
 *     and "ext" corresponds to the extension (Mime type) of the file. The letter corresponding
 *     to the class of results has the value "A" when the results correspond to the absolute
 *     classification of the {@link Race} and "B" when the results are grouped
 *     by {@link AgeGroup age group} (additional letters may be used when applicable.).
 *     The {@link #getFileName()  fileName} is unique, there cannot be two different
 *     {@link ResultsFile} with the same {@link #getFileName()  fileName}.
 *   </li>
 *   <li>
 *     {@link #getRevisionNumber() revisionNumber} : the revision number
 *     of the {@link ResultsFile results file}. The first revision always starts at "01".
 *   </li>
 *   <li>
 *     {@link #getActive() isActive} : flag that indicates the most recent version
 *     of a {@link ResultsFile}. It is set to <i>true</i> for the file with the highest
 *     {@link #getRevisionNumber() revisionNumber} and <i>false</i> for all previous versions.
 *   </li>
 * </ul>
 */
@Entity(name = "ResultsFile")
@Table(name = "results_file")
public class ResultsFile implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH}
  )
  @JoinColumn(name = "race_id", referencedColumnName = "id", nullable = false)
  private Race race;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "subtitle", nullable = false)
  private String subtitle;

  @Column(name = "file_name", nullable = false, unique = true)
  @Pattern(
      regexp = "^[0-9]{8}[A-Z]{3}[0-9]{3}-[0-9]{3}[A-Z]-[0-9]{2}\\.[a-zA-Z0-9]+$",
      message = "File name must follow the format YYYYMMDDXXXNNN-YYYZ-VV.ext (e.g., 19840815NAC001-001A-01.pdf)"
  )
  private String fileName;

  @Column(name = "revision", nullable = false)
  private Integer revisionNumber;

  @Column(name = "is_active", nullable = false)
  private Boolean isActive;

  /**
   * Default constructor for the {@link ResultsFile} entity.
   */
  public ResultsFile() {
    super();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Race getRace() {
    return race;
  }

  public void setRace(Race race) {
    this.race = race;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getSubtitle() {
    return subtitle;
  }

  public void setSubtitle(String subtitle) {
    this.subtitle = subtitle;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ResultsFile that = (ResultsFile) o;
    return id != null && Objects.equals(id, that.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", ResultsFile.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("raceId=" + Optional.ofNullable(race).map(Race::getId).orElse(null))
        .add("title='" + title + "'")
        .add("subtitle='" + subtitle + "'")
        .add("fileName='" + fileName + "'")
        .add("revisionNumber=" + revisionNumber)
        .add("isActive=" + isActive)
        .toString();
  }
}
