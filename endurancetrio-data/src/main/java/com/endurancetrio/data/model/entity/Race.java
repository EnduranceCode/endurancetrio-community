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

import com.endurancetrio.data.model.converter.GenderCategoryConverter;
import com.endurancetrio.data.model.converter.RaceStatusConverter;
import com.endurancetrio.data.model.converter.RaceTypeConverter;
import com.endurancetrio.data.model.enumerator.GenderCategory;
import com.endurancetrio.data.model.enumerator.RaceStatus;
import com.endurancetrio.data.model.enumerator.RaceType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

/**
 * The {@link Race} entity represents a contest within an {@link Event}.
 * <p>
 * A {@link Race} is uniquely defined by its {@link Course} and its start list formation rules,
 * meaning no two {@link Race races} can have identical {@link Course course} and start list
 * formation rules. Additionally, each {@link Race} corresponds to a single start; a {@link Race}
 * cannot have multiple starts.
 * <p>
 * Both individual and collective competitions are represented by this entity. Collective
 * competitions (such as club or team events) derive their classification from the results
 * of individual {@link Race races} which are its {@link #getParentRaces() parent races}.
 * <p>
 * In most cases, a {@link Race} belongs to a single {@link Course}. However, in specific
 * scenarios (e.g., collective youth races whose classification derives from multiple individual
 * {@link Race races}), a {@link Race} may belong to multiple {@link Course courses}.
 * <p>
 * The {@link Race}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getId() id} : the unique identifier of the {@link Race} that is automatically
 *     generated and is the primary key.
 *   </li>
 *   <li>
 *     {@link #getRaceReference() raceReference} : is a race reference number that has exactly
 *     18 characters and has the format "YYYYMMDDXXXNNN-YYY". In this format, "YYYMMDDXXXNNN"
 *     follows the same formation rules as the {@link Event#getEventReference() eventReference}
 *     with the particularity that the segment referring to the date corresponds to the start date
 *     of the {@link Race}. "YYY" corresponds to the {@link Race} order number which follows
 *     the order of the {@link Race}'s {@link #getTime() start time}.
 *     The {@link #getRaceReference() raceReference} is unique, there cannot be two different
 *     {@link Race races} with the same {@link #getRaceReference() raceReference}.
 *   </li>
 *   <li>
 *     {@link #getCourses() courses} : the {@link Course courses} where the {@link Race} is raced.
 *   </li>
 *   <li>{@link #getRaceType() raceType} : the {@link RaceType type} of the {@link Race}</li>
 *   <li>
 *     {@link #getParentRaces() parentRaces} : the list of {@link Race} that the {@link Race}
 *     depends on. For example, a collective {@link Race} (those in which the
 *     competitors are clubs and/or teams) has results that are calculated from the results
 *     of an individual {@link Race} and therefore that individual {@link Race}
 *     is its parent {@link Race}.
 *   </li>
 *   <li>
 *     {@link #getTitle() title} : the title of the {@link Race}. Together with
 *     the {@link #getSubtitle() subtitle} it unequivocally identifies the {@link Race}.
 *     The {@link #getTitle() title} of the {@link Race} must preferably be the same as the
 *     championship it belongs to (e.g., Individual National Championship) or the distance
 *     of the {@link Race} (e.g., Duathlon Powerman Mafra Sprint and
 *     Duathlon Powerman Mafra Standard).
 *   </li>
 *   <li>
 *     {@link #getSubtitle() subtitle} : the subtitle of the {@link Race}. Together with
 *     the {@link #getTitle() title}, it unequivocally identifies the {@link Race}.
 *     If the {@link #getGenderCategory() genderCategory} of the {@link Race} is sufficient to
 *     unequivocally distinguish the {@link #getGenderCategory() genderCategory}, only the
 *     {@link #getGenderCategory() genderCategory} is used as a {@link #getSubtitle() subtitle}
 *     (e.g., "Women"). If the {@link #getGenderCategory() genderCategory} is not sufficient
 *     to unequivocally identify the {@link Race}, the {@link #getAgeGroup() ageGroup} and
 *     the {@link #getGenderCategory() genderCategory} are used (e.g., "Elite Women").
 *   </li>
 *   <li>{@link #getGenderCategory() genderCategory} the gender category of the {@link Race}.</li>
 *   <li>{@link #getAgeGroup() ageGroup} : the {@link AgeGroup age group} of the {@link Race}.</li>
 *   <li>{@link #getDate() date} : the date of the {@link Race}.</li>
 *   <li>{@link #getTime() time} : the time scheduled for the starting gun of the {@link Race}.</li>
 *   <li>
 *     {@link #getRaceStatus() raceStatus} : the {@link RaceStatus status} of the {@link Race}.
 *   </li>
 *   <li>{@link #getGunTime() gunTime} : the time of the starting gun of the {@link Race}.</li>
 *   <li>
 *     {@link #getAirTemperature() airTemperature} : the official air temperature
 *     for the {@link Race}.
 *   </li>
 *   <li>
 *     {@link #getResultsFiles() resultsFiles} : the {@link ResultsFile} of the {@link Race}.
 *   </li>
 * </ul>
 */
@Entity(name = "Race")
@Table(name = "race")
@Inheritance(strategy = InheritanceType.JOINED)
public class Race implements Serializable {

  @Serial
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "race_reference", unique = true, nullable = false)
  @Pattern(
      regexp = "^\\d{8}[A-Z]{3}\\d{3}-\\d{3}$",
      message = "Race reference must follow the format YYYYMMDDXXXNNN-YYY (e.g., 19840815NAC001-001)"
  )
  private String raceReference;

  @ManyToMany(mappedBy = "races", fetch = FetchType.LAZY)
  private Set<Course> courses;

  @Column(name = "race_type", nullable = false)
  @Convert(converter = RaceTypeConverter.class)
  private RaceType raceType;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "race_hierarchy",
      joinColumns = @JoinColumn(name = "race_id"),
      inverseJoinColumns = @JoinColumn(name = "parent_race_id")
  )
  private Set<Race> parentRaces;

  @Column(name = "title", nullable = false)
  private String title;

  @Column(name = "subtitle", nullable = false)
  private String subtitle;

  @Column(name = "gender_category", nullable = false)
  @Convert(converter = GenderCategoryConverter.class)
  private GenderCategory genderCategory;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "age_group_id", nullable = false)
  private AgeGroup ageGroup;

  @Column(name = "date", nullable = false)
  LocalDate date;

  @Column(name = "time", nullable = false)
  LocalTime time;

  @Column(name = "race_status", nullable = false)
  @Convert(converter = RaceStatusConverter.class)
  RaceStatus raceStatus;

  @Column(name = "gun_time", nullable = true)
  LocalTime gunTime;

  @Column(name = "air_temperature", nullable = true)
  private Double airTemperature;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "race_id", referencedColumnName = "id")
  private Set<ResultsFile> resultsFiles;

  /**
   * Default constructor for the {@link Race} entity.
   */
  public Race() {
    super();
    this.courses = new HashSet<>();
    this.parentRaces = new HashSet<>();
    this.resultsFiles = new HashSet<>();
  }

  /**
   * Adds a {@link Course} to this {@link Race}'s {@link #getCourses() races} collection. Ensures
   * bidirectional consistency by also adding this {@link Race} to the {@link Course}'s
   * {@link Course#getRaces() races} collection.
   *
   * @param course the {@link Course} to add; ignored if {@code null} or already present
   */
  public void addCourse(Course course) {
    if (course != null && this.courses.add(course)) {
      course.addRace(this);
    }
  }

  /**
   * Removes a {@link Course} from this {@link Race}'s {@link #getCourses() courses} collection.
   * Ensures bidirectional consistency by also removing this {@link Race} from the {@link Course}'s
   * {@link Course#getRaces() races} collection.
   *
   * @param course the {@link Course} to remove; ignored if {@code null} or not present
   */
  public void removeCourse(Course course) {
    if (course != null && this.courses.remove(course)) {
      course.removeRace(this);
    }
  }

  /**
   * Adds a parent {@link Race} to this {@link Race}.
   * <p>
   * This method establishes a parent-child relationship between the current {@link Race} and the
   * provided {@link Race}. The parent {@link Race} must already be persisted in the database before
   * adding it as a parent.
   *
   * @param parentRace The {@link Race} to be added as a parent. It must be a non-null race.
   */
  public void addParentRace(Race parentRace) {
    if (parentRace != null && parentRace.getId() != null  && !this.equals(parentRace)) {
      this.parentRaces.add(parentRace);
    }
  }

  /**
   * Removes a parent {@link Race} from this {@link Race}.
   * <p>
   * This method removes the association between the current {@link Race} and the specified parent
   * {@link Race}. The provided parent {@link Race} must be one of the already existing
   * {@link #getParentRaces() parentRaces} of this {@link Race}.
   *
   * @param parentRace The {@link Race} to be removed from the list
   *                   {@link #getParentRaces() parentRaces}. It must already be associated as a
   *                   parent.
   */
  public void removeParentRace(Race parentRace) {
    if (parentRace != null && parentRace.getId() != null) {
      this.parentRaces.remove(parentRace);
    }
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRaceReference() {
    return raceReference;
  }

  public void setRaceReference(String raceReference) {
    this.raceReference = raceReference;
  }

  public Set<Course> getCourses() {
    return courses;
  }

  public void setCourses(Set<Course> courses) {
    this.courses = courses;
  }

  public RaceType getRaceType() {
    return raceType;
  }

  public void setRaceType(RaceType raceType) {
    this.raceType = raceType;
  }

  public Set<Race> getParentRaces() {
    return parentRaces;
  }

  public void setParentRaces(Set<Race> parentRaces) {
    this.parentRaces = parentRaces;
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

  public GenderCategory getGenderCategory() {
    return genderCategory;
  }

  public void setGenderCategory(GenderCategory genderCategory) {
    this.genderCategory = genderCategory;
  }

  public AgeGroup getAgeGroup() {
    return ageGroup;
  }

  public void setAgeGroup(AgeGroup ageGroup) {
    this.ageGroup = ageGroup;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public LocalTime getTime() {
    return time;
  }

  public void setTime(LocalTime time) {
    this.time = time;
  }

  public RaceStatus getRaceStatus() {
    return raceStatus;
  }

  public void setRaceStatus(RaceStatus raceStatus) {
    this.raceStatus = raceStatus;
  }

  public LocalTime getGunTime() {
    return gunTime;
  }

  public void setGunTime(LocalTime gunTime) {
    this.gunTime = gunTime;
  }

  public Double getAirTemperature() {
    return airTemperature;
  }

  public void setAirTemperature(Double airTemperature) {
    this.airTemperature = airTemperature;
  }

  public Set<ResultsFile> getResultsFiles() {
    return resultsFiles;
  }

  public void setResultsFiles(Set<ResultsFile> resultsFiles) {
    this.resultsFiles = resultsFiles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Race race = (Race) o;
    return id != null && Objects.equals(id, race.id);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(id);
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", Race.class.getSimpleName() + "[", "]")
        .add("id=" + id)
        .add("raceReference='" + raceReference + "'")
        .add("raceType=" + raceType.getCode())
        .add("title='" + title + "'")
        .add("subtitle='" + subtitle + "'")
        .add("genderCategory=" + genderCategory.getCode())
        .add("ageGroup=" + ageGroup.getTitle())
        .add("date=" + date)
        .add("time=" + time)
        .add("raceStatus=" + raceStatus.getCode())
        .add("gunTime=" + gunTime)
        .add("airTemperature=" + airTemperature)
        .toString();
  }
}
