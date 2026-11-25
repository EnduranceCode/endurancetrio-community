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

package com.endurancetrio.business.event.dto;

import com.endurancetrio.business.competitor.dto.AthleteDTO;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.competitor.model.enumerator.ParaClass;
import com.endurancetrio.data.event.model.enumerator.Penalty;
import java.time.Duration;
import java.util.List;

/**
 * The {@link RelayResultDTO} represents a relay team's stored result, including split times and
 * the data used to show its contributors.
 *
 * @param id                the unique identifier of the relay result
 * @param race              the race context for this result
 * @param rank              the team's final rank in the race (may be null if unranked)
 * @param penalty           the penalty applied to the team (may be null)
 * @param teamName          the team name as recorded for this race
 * @param bib               the team's bib number (may be null)
 * @param athletesCount     the number of athletes contributing to the relay result
 * @param ageGroup          the team's age group classification (may be null)
 * @param paraClass         the team's para-classification (may be null)
 * @param swim              the swim split time (may be null)
 * @param firstBike         the first bike split time (may be null)
 * @param firstRun          the first run split time (may be null)
 * @param t1                the first transition time (may be null)
 * @param bike              the bike split time (may be null)
 * @param t2                the second transition time (may be null)
 * @param run               the run split time (may be null)
 * @param secondRun         the second run split time (may be null)
 * @param t3                the third transition time (may be null)
 * @param secondBike        the second bike split time (may be null)
 * @param total             the team's total time (may be null)
 * @param individualResults the individual results that contribute to the relay result
 * @param athletes          the relay entry's members, used when no individual results are stored
 */
public record RelayResultDTO(
    Long id,
    RaceDTO race,
    Integer rank,
    Penalty penalty,
    String teamName,
    String bib,
    Integer athletesCount,
    AgeGroup ageGroup,
    ParaClass paraClass,
    Duration swim,
    Duration firstBike,
    Duration firstRun,
    Duration t1,
    Duration bike,
    Duration t2,
    Duration run,
    Duration secondRun,
    Duration t3,
    Duration secondBike,
    Duration total,
    List<IndividualResultDTO> individualResults,
    List<AthleteDTO> athletes
) {

}
