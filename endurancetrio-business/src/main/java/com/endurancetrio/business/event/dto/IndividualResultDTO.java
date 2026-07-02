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
import com.endurancetrio.business.competitor.dto.TeamDTO;
import com.endurancetrio.data.competitor.model.enumerator.AgeGroup;
import com.endurancetrio.data.competitor.model.enumerator.ParaClass;
import com.endurancetrio.data.event.model.enumerator.Penalty;
import java.time.Duration;

/**
 * The {@link IndividualResultDTO} represents an athlete's result in a race, including split times,
 * ranking, penalty, and the time gap to the overall winner.
 *
 * @param id         the unique identifier of the individual result
 * @param race       the race context for this result
 * @param rank       the athlete's final rank in the race (may be null if unranked)
 * @param penalty    the penalty applied to the athlete (may be null)
 * @param athlete    the athlete who achieved this result
 * @param ageGroup   the age group classification of the athlete (may be null)
 * @param paraClass  the para-classification of the athlete (may be null)
 * @param team       the team the athlete represented (may be null)
 * @param bib        the athlete's bib number
 * @param swim       the swim split time (may be null)
 * @param firstBike  the first bike split time (may be null)
 * @param firstRun   the first run split time (may be null)
 * @param t1         the first transition time (may be null)
 * @param bike       the bike split time (may be null)
 * @param t2         the second transition time (may be null)
 * @param run        the run split time (may be null)
 * @param secondRun  the second run split time (may be null)
 * @param t3         the third transition time (may be null)
 * @param secondBike the second bike split time (may be null)
 * @param total      the athlete's total race time (may be null)
 * @param gap        the time gap to the overall winner (may be null)
 * @param points     the points awarded for this result (may be null)
 */
public record IndividualResultDTO(Long id, RaceDTO race, Integer rank, Penalty penalty,
                                  AthleteDTO athlete, AgeGroup ageGroup, ParaClass paraClass,
                                  TeamDTO team, String bib, Duration swim, Duration firstBike,
                                  Duration firstRun, Duration t1, Duration bike, Duration t2,
                                  Duration run, Duration secondRun, Duration t3,
                                  Duration secondBike, Duration total, Duration gap,
                                  Integer points) {

}
