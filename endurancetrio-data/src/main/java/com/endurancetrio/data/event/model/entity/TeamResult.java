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

import com.endurancetrio.data.common.model.entity.BaseEntity;
import com.endurancetrio.data.competitor.model.converter.AgeGroupConverter;
import com.endurancetrio.data.competitor.model.converter.ParaClassConverter;
import com.endurancetrio.data.competitor.model.entity.Team;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.competitor.model.enumerator.ParaClass;
import com.endurancetrio.data.event.model.converter.DurationToIntegerConverter;
import com.endurancetrio.data.event.model.converter.PenaltyConverter;
import com.endurancetrio.data.event.model.enumerator.Penalty;
import com.endurancetrio.data.event.model.enumerator.RaceType;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serial;
import java.time.Duration;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;

/**
 * The {@link TeamResult} entity represents a team's result in a collective {@link Race}.
 * <p>
 * Team results are calculated from the individual results of team members. The calculation method
 * depends on the {@link RaceType race type}.
 * <p>
 * The {@link TeamResult}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getId() id} : the unique identifier of the {@link TeamResult} that
 *     is automatically generated and is the primary key.
 *   </li>
 *   <li>
 *     {@link #getRace() race} : the {@link Race} that this team result belongs to.
 *   </li>
 *   <li>
 *     {@link #getRank() rank} : the team's finishing position in the team classification.
 *   </li>
 *   <li>
 *     {@link #getSourceResult() sourceResult} : the parent {@link TeamResult} that this
 *     result is derived from, if any.
 *   </li>
 *   <li>
 *     {@link #getPenalty() penalty} : the {@link Penalty} applied to the team in this
 *     {@link Race}.
 *   </li>
 *   <li>
 *     {@link #getTeam() team} : the {@link Team} that this result belongs to.
 *   </li>
 *   <li>
 *     {@link #getTeamName() teamName} : the display name of the {@link Team} as it appears
 *     in this specific {@link Race}'s results.
 *   </li>
 *   <li>
 *     {@link #getAthletesCount() athletesCount} : the number of athletes contributing
 *     to this team result.
 *   </li>
 *   <li>
 *     {@link #getIndividualResults() individualResults} : the individual
 *     {@link IndividualResult race results} that contribute to this team result.
 *   </li>
 *   <li>
 *     {@link #getAgeGroup() ageGroup} : the {@link AgeGroup} of the team for this
 *     specific {@link Race}.
 *   </li>
 *   <li>
 *     {@link #getBib() bib} : the team's race bib number.
 *   </li>
 *   <li>
 *     {@link #getCumulativeRank() cumulativeRank} : the sum of the finishing positions
 *     of the athletes contributing to the team classification.
 *   </li>
 *   <li>
 *     {@link #getTotal() total} : the total team time in milliseconds.
 *   </li>
 *   <li>
 *     {@link #getPoints() points} : the points earned by the team in this {@link Race}.
 *   </li>
 * </ul>
 */
@Entity(name = "TeamResult")
@Table(name = "team_result")
@SequenceGenerator(
    name = "seq_endurancetrio_generator", sequenceName = "seq_team_result_id", allocationSize = 1
)
public class TeamResult extends BaseEntity<Long> {

  @Serial
  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "race_id", referencedColumnName = "id", nullable = false)
  private Race race;

  @Column(name = "rank", nullable = false)
  private Integer rank;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "source_result_id", referencedColumnName = "id")
  private TeamResult sourceResult;

  @Column(name = "penalty")
  @Convert(converter = PenaltyConverter.class)
  private Penalty penalty;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", referencedColumnName = "id", nullable = false)
  private Team team;

  @Column(name = "team_name", nullable = false)
  private String teamName;

  @Column(name = "athletes_count", nullable = false)
  private Integer athletesCount;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(
      name = "team_result_individual_result",
      joinColumns = @JoinColumn(name = "team_result_id"),
      inverseJoinColumns = @JoinColumn(name = "individual_result_id")
  )
  private Set<IndividualResult> individualResults;

  @Convert(converter = AgeGroupConverter.class)
  @Column(name = "age_group")
  private AgeGroup ageGroup;

  @Convert(converter = ParaClassConverter.class)
  @Column(name = "para_class")
  private ParaClass paraClass;

  @Column(name = "bib")
  private String bib;

  @Column(name = "cumulative_rank")
  private Integer cumulativeRank;

  @Column(name = "total")
  @Convert(converter = DurationToIntegerConverter.class)
  private Duration total;

  @Column(name = "points")
  private Integer points;

  public TeamResult() {
    super();
    this.individualResults = new HashSet<>();
  }

  /**
   * Adds an individual {@link IndividualResult} to this {@link TeamResult}'s contributing results.
   *
   * @param individualResult the {@link IndividualResult} to add; ignored if {@code null} or already
   *                         present
   */
  public void addIndividualResult(IndividualResult individualResult) {
    if (individualResult != null) {
      this.individualResults.add(individualResult);
    }
  }

  /**
   * Removes an individual {@link IndividualResult} from this {@link TeamResult}'s contributing
   * results.
   *
   * @param individualResult the {@link IndividualResult} to remove; ignored if {@code null} or not
   *                         present
   */
  public void removeIndividualResult(IndividualResult individualResult) {
    if (individualResult != null) {
      this.individualResults.remove(individualResult);
    }
  }

  public Race getRace() {
    return race;
  }

  public void setRace(Race race) {
    this.race = race;
  }

  public Integer getRank() {
    return rank;
  }

  public void setRank(Integer rank) {
    this.rank = rank;
  }

  public TeamResult getSourceResult() {
    return sourceResult;
  }

  public void setSourceResult(TeamResult sourceResult) {
    this.sourceResult = sourceResult;
  }

  public Penalty getPenalty() {
    return penalty;
  }

  public void setPenalty(Penalty penalty) {
    this.penalty = penalty;
  }

  public Team getTeam() {
    return team;
  }

  public void setTeam(Team team) {
    this.team = team;
  }

  public String getTeamName() {
    return teamName;
  }

  public void setTeamName(String teamName) {
    this.teamName = teamName;
  }

  public Integer getAthletesCount() {
    return athletesCount;
  }

  public void setAthletesCount(Integer athletesCount) {
    this.athletesCount = athletesCount;
  }

  public Set<IndividualResult> getIndividualResults() {
    return individualResults;
  }

  public void setIndividualResults(Set<IndividualResult> individualResults) {
    this.individualResults = individualResults;
  }

  public AgeGroup getAgeGroup() {
    return ageGroup;
  }

  public void setAgeGroup(AgeGroup ageGroup) {
    this.ageGroup = ageGroup;
  }

  public ParaClass getParaClass() {
    return paraClass;
  }

  public void setParaClass(ParaClass paraClass) {
    this.paraClass = paraClass;
  }

  public String getBib() {
    return bib;
  }

  public void setBib(String bib) {
    this.bib = bib;
  }

  public Integer getCumulativeRank() {
    return cumulativeRank;
  }

  public void setCumulativeRank(Integer cumulativeRank) {
    this.cumulativeRank = cumulativeRank;
  }

  public Duration getTotal() {
    return total;
  }

  public void setTotal(Duration total) {
    this.total = total;
  }

  public Integer getPoints() {
    return points;
  }

  public void setPoints(Integer points) {
    this.points = points;
  }

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public String toString() {
    return new StringJoiner(", ", TeamResult.class.getSimpleName() + "[", "]")
        .add("id=" + this.getId())
        .add("raceId=" + Optional.ofNullable(race).map(Race::getId).orElse(null))
        .add("rank=" + rank)
        .add("sourceResultId=" + Optional.ofNullable(sourceResult)
            .map(TeamResult::getId)
            .orElse(null))
        .add("penalty=" + Optional.ofNullable(penalty).map(Penalty::getCode).orElse(null))
        .add("teamId=" + Optional.ofNullable(team).map(Team::getId).orElse(null))
        .add("teamName='" + teamName + "'")
        .add("athletesCount=" + athletesCount)
        .add("individualResults=" + Optional.ofNullable(individualResults)
            .map(r -> r.stream().map(IndividualResult::getId).toList())
            .orElse(null))
        .add("ageGroup=" + Optional.ofNullable(ageGroup).map(AgeGroup::getCode).orElse(null))
        .add("paraClass=" + Optional.ofNullable(paraClass).map(ParaClass::getCode).orElse(null))
        .add("bib='" + bib + "'")
        .add("total=" + total)
        .add("points=" + points)
        .add("cumulativeRank=" + cumulativeRank)
        .toString();
  }
}
