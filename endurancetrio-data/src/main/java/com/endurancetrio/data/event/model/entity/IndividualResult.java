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
import com.endurancetrio.data.competitor.model.entity.Athlete;
import com.endurancetrio.data.competitor.model.entity.Team;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.competitor.model.enumerator.ParaClass;
import com.endurancetrio.data.event.model.converter.DurationToIntegerConverter;
import com.endurancetrio.data.event.model.converter.PenaltyConverter;
import com.endurancetrio.data.event.model.enumerator.Penalty;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.io.Serial;
import java.time.Duration;
import java.util.Optional;
import java.util.StringJoiner;

/**
 * The {@link IndividualResult} entity represents a single athlete's result in a {@link Race}.
 * <p>
 * Common fields include {@link #getRace() race} reference, the athlete's {@link #getRank() rank},
 * and the {@link #getSourceResult() sourceResult} for derived race linking. Performance data
 * is stored as split times for each discipline segment.
 * <p>
 * The {@link IndividualResult}'s fields are defined as follows:
 * <ul>
 *   <li>
 *     {@link #getId() id} : the unique identifier of the {@link IndividualResult} that
 *     is automatically generated and is the primary key.
 *   </li>
 *   <li>
 *     {@link #getRace() race} : the {@link Race} that this result belongs to.
 *   </li>
 *   <li>
 *     {@link #getRank() rank} : the athlete's finishing position in this {@link Race}.
 *   </li>
 *   <li>
 *     {@link #getSourceResult() sourceResult} : the parent {@link IndividualResult} that this
 *     result is derived from, if any. Performance data should be read from this source
 *     for derived race results.
 *   </li>
 *   <li>
 *     {@link #getPenalty() penalty} : the {@link Penalty} applied to the athlete in this
 *     {@link Race} (e.g., DNF, DSQ, DNS).
 *   </li>
 *   <li>
 *     {@link #getAthlete() athlete} : the {@link Athlete} this result belongs to.
 *   </li>
 *   <li>
 *     {@link #getTeam() team} : the {@link Team} this result belongs to, if applicable.
 *   </li>
 *   <li>
 *     {@link #getTeamName() teamName} : the display name of the {@link Team} as it appears
 *     in this specific {@link Race}. May differ from the team's canonical name.
 *   </li>
 *   <li>
 *     {@link #getAgeGroup() ageGroup} : the {@link AgeGroup} of the athlete for this
 *     specific {@link Race}.
 *   </li>
 *   <li>
 *     {@link #getParaClass() paraClass} : the paratriathlon sport {@link ParaClass} of the
 *     athlete for this specific {@link Race}.
 *   </li>
 *   <li>
 *     {@link #getBib() bib} : the athlete's race bib number.
 *   </li>
 *   <li>
 *     {@link #getSwim() swim} : the swim split time.
 *   </li>
 *   <li>
 *     {@link #getFirstBike() firstBike} : the first bike split time
 *     (for double-biathlon based disciplines).
 *   </li>
 *   <li>
 *     {@link #getFirstRun() firstRun} : the first run split time
 *     (for duathlon and double-biathlon based disciplines).
 *   </li>
 *   <li>
 *     {@link #getT1() t1} : the first transition time.
 *   </li>
 *   <li>
 *     {@link #getBike() bike} : the bike split time.
 *   </li>
 *   <li>
 *     {@link #getT2() t2} : the second transition time.
 *   </li>
 *   <li>
 *     {@link #getRun() run} : the run split time.
 *   </li>
 *   <li>
 *     {@link #getSecondRun() secondRun} : the second run split time
 *     (for duathlon and double-biathlon based disciplines).
 *   </li>
 *   <li>
 *     {@link #getT3() t3} : the third transition time
 *     (for double-biathlon based disciplines).
 *   </li>
 *   <li>
 *     {@link #getSecondBike() secondBike} : the second bike split time
 *     (for double-biathlon based disciplines).
 *   </li>
 *   <li>
 *     {@link #getTotal() total} : the total race time.
 *   </li>
 *   <li>
 *     {@link #getPoints() points} : the points earned by the athlete in this {@link Race}.
 *   </li>
 * </ul>
 */
@Entity(name = "IndividualResult")
@Table(name = "individual_result")
@SequenceGenerator(
    name = "seq_endurancetrio_generator",
    sequenceName = "seq_individual_result_id",
    allocationSize = 1
)
public class IndividualResult extends BaseEntity<Long> {

  @Serial
  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "race_id", referencedColumnName = "id", nullable = false)
  private Race race;

  @Column(name = "rank")
  private Integer rank;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "source_result_id", referencedColumnName = "id")
  private IndividualResult sourceResult;

  @Column(name = "penalty")
  @Convert(converter = PenaltyConverter.class)
  private Penalty penalty;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "athlete_id", referencedColumnName = "id", nullable = false)
  private Athlete athlete;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id", referencedColumnName = "id")
  private Team team;

  @Column(name = "team_name")
  private String teamName;

  @Convert(converter = AgeGroupConverter.class)
  @Column(name = "age_group")
  private AgeGroup ageGroup;

  @Convert(converter = ParaClassConverter.class)
  @Column(name = "para_class")
  private ParaClass paraClass;

  @Column(name = "bib")
  private String bib;

  @Column(name = "swim")
  @Convert(converter = DurationToIntegerConverter.class)
  private Duration swim;

  @Column(name = "first_bike")
  @Convert(converter = DurationToIntegerConverter.class)
  private Duration firstBike;

  @Column(name = "first_run")
  @Convert(converter = DurationToIntegerConverter.class)
  private Duration firstRun;

  @Column(name = "t1")
  @Convert(converter = DurationToIntegerConverter.class)
  private Duration t1;

  @Column(name = "bike")
  @Convert(converter = DurationToIntegerConverter.class)
  private Duration bike;

  @Column(name = "t2")
  @Convert(converter = DurationToIntegerConverter.class)
  private Duration t2;

  @Column(name = "run")
  @Convert(converter = DurationToIntegerConverter.class)
  private Duration run;

  @Column(name = "second_run")
  @Convert(converter = DurationToIntegerConverter.class)
  private Duration secondRun;

  @Column(name = "t3")
  @Convert(converter = DurationToIntegerConverter.class)
  private Duration t3;

  @Column(name = "second_bike")
  @Convert(converter = DurationToIntegerConverter.class)
  private Duration secondBike;

  @Column(name = "total")
  @Convert(converter = DurationToIntegerConverter.class)
  private Duration total;

  @Column(name = "points")
  private Integer points;

  public IndividualResult() {
    super();
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

  public IndividualResult getSourceResult() {
    return sourceResult;
  }

  public void setSourceResult(IndividualResult sourceResult) {
    this.sourceResult = sourceResult;
  }

  public Penalty getPenalty() {
    return penalty;
  }

  public void setPenalty(Penalty penalty) {
    this.penalty = penalty;
  }

  public Athlete getAthlete() {
    return athlete;
  }

  public void setAthlete(Athlete athlete) {
    this.athlete = athlete;
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

  public Duration getSwim() {
    return swim;
  }

  public void setSwim(Duration swim) {
    this.swim = swim;
  }

  public Duration getFirstBike() {
    return firstBike;
  }

  public void setFirstBike(Duration firstBike) {
    this.firstBike = firstBike;
  }

  public Duration getFirstRun() {
    return firstRun;
  }

  public void setFirstRun(Duration firstRun) {
    this.firstRun = firstRun;
  }

  public Duration getT1() {
    return t1;
  }

  public void setT1(Duration t1) {
    this.t1 = t1;
  }

  public Duration getBike() {
    return bike;
  }

  public void setBike(Duration bike) {
    this.bike = bike;
  }

  public Duration getT2() {
    return t2;
  }

  public void setT2(Duration t2) {
    this.t2 = t2;
  }

  public Duration getRun() {
    return run;
  }

  public void setRun(Duration run) {
    this.run = run;
  }

  public Duration getSecondRun() {
    return secondRun;
  }

  public void setSecondRun(Duration secondRun) {
    this.secondRun = secondRun;
  }

  public Duration getT3() {
    return t3;
  }

  public void setT3(Duration t3) {
    this.t3 = t3;
  }

  public Duration getSecondBike() {
    return secondBike;
  }

  public void setSecondBike(Duration secondBike) {
    this.secondBike = secondBike;
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
    return new StringJoiner(", ", IndividualResult.class.getSimpleName() + "[", "]")
        .add("id=" + this.getId())
        .add("raceId=" + Optional.ofNullable(race).map(Race::getId).orElse(null))
        .add("rank=" + rank)
        .add("sourceResultId=" + Optional.ofNullable(sourceResult)
            .map(IndividualResult::getId)
            .orElse(null))
        .add("penalty=" + Optional.ofNullable(penalty).map(Penalty::getCode).orElse(null))
        .add("athleteId=" + Optional.ofNullable(athlete).map(Athlete::getId).orElse(null))
        .add("teamId=" + Optional.ofNullable(team).map(Team::getId).orElse(null))
        .add("teamName='" + teamName + "'")
        .add("ageGroup=" + Optional.ofNullable(ageGroup).map(AgeGroup::getCode).orElse(null))
        .add("paraClass=" + Optional.ofNullable(paraClass).map(ParaClass::getCode).orElse(null))
        .add("bib='" + bib + "'")
        .add("swim=" + swim)
        .add("firstBike=" + firstBike)
        .add("firstRun=" + firstRun)
        .add("t1=" + t1)
        .add("bike=" + bike)
        .add("t2=" + t2)
        .add("run=" + run)
        .add("secondRun=" + secondRun)
        .add("t3=" + t3)
        .add("secondBike=" + secondBike)
        .add("total=" + total)
        .add("points=" + points)
        .toString();
  }
}
