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

import java.time.Duration;
import java.util.List;

/**
 * The {@link TeamResultDTO} represents a team's stored result in a collective race, together with
 * the individual results that contribute to it.
 *
 * @param id                the unique identifier of the team result
 * @param race              the race context for this result
 * @param rank              the team's final rank in the race (may be null if unranked)
 * @param teamName          the team name as recorded for this race
 * @param bib               the team's bib number (may be null)
 * @param athletesCount     the number of athletes contributing to the team result
 * @param total             the team's total time (may be null)
 * @param totalGap          the difference between the team's total and the winner's, for
 *                          TEAM_BY_TIME races (may be null)
 * @param points            the points awarded to the team (may be null)
 * @param pointsGap         the difference between the winner's points and the team's, for
 *                          TEAM_BY_POINTS races (may be null)
 * @param cumulativeRank    the sum of the contributing athletes' ranks (may be null)
 * @param cumulativeRankGap the difference between the team's rank sum and the winner's, for
 *                          TEAM_BY_RANK races (may be null)
 * @param individualResults the individual results that contribute to the team result
 */
public record TeamResultDTO(
  Long id,
  RaceDTO race,
  Integer rank, String teamName, String bib, Integer athletesCount,
  Duration total,
  Duration totalGap,
  Integer points,
  Integer pointsGap,
  Integer cumulativeRank,
  Integer cumulativeRankGap,
  List<IndividualResultDTO> individualResults
) {

}
