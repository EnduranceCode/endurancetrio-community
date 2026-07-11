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

package com.endurancetrio.business.competitor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.competitor.dto.AthleteDTO;
import com.endurancetrio.business.competitor.dto.AthleteRacesPageDTO;
import com.endurancetrio.business.competitor.dto.AthletesPageDTO;
import com.endurancetrio.business.competitor.fixtures.AthleteDTOFixtures;
import com.endurancetrio.business.competitor.fixtures.AthleteFixture;
import com.endurancetrio.business.competitor.mapper.AthleteMapper;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.mapper.RaceMapper;
import com.endurancetrio.data.competitor.model.entity.Athlete;
import com.endurancetrio.data.competitor.repository.AthleteRepository;
import com.endurancetrio.data.event.model.entity.Race;
import com.endurancetrio.data.event.model.enumerator.GenderCategory;
import com.endurancetrio.data.event.model.enumerator.RaceStatus;
import com.endurancetrio.data.event.model.enumerator.RaceType;
import com.endurancetrio.data.event.repository.IndividualResultRepository;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class AthleteServiceMainTest {

  private static final Pageable PAGEABLE = PageRequest.of(0, 30);
  private static final Long ATHLETE_ID = 1L;

  @Mock
  private AthleteRepository athleteRepository;

  @Mock
  private IndividualResultRepository individualResultRepository;

  @Mock
  private AthleteMapper athleteMapper;

  @Mock
  private RaceMapper raceMapper;

  @InjectMocks
  private AthleteServiceMain underTest;

  @Test
  void getAthletesShouldReturnPageWithAthletes() {
    Athlete testCarvalhoEntity = AthleteFixture.standard();
    Athlete testCavaleiroEntity = AthleteFixture.athleteCavaleiro();
    Athlete testBelloEntity = AthleteFixture.athleteBello();

    AthleteDTO testCarvalhoDTO = AthleteDTOFixtures.standard();
    AthleteDTO testCavaleiroDTO = AthleteDTOFixtures.athleteCavaleiro();
    AthleteDTO testBelloDTO = AthleteDTOFixtures.athleteBello();

    Page<Athlete> athletePage = new PageImpl<>(
        List.of(testCarvalhoEntity, testCavaleiroEntity, testBelloEntity), PAGEABLE, 3L);

    when(athleteRepository.findAllOrderedByKnownName(PAGEABLE)).thenReturn(athletePage);
    when(athleteMapper.map(testCarvalhoEntity)).thenReturn(testCarvalhoDTO);
    when(athleteMapper.map(testCavaleiroEntity)).thenReturn(testCavaleiroDTO);
    when(athleteMapper.map(testBelloEntity)).thenReturn(testBelloDTO);

    AthletesPageDTO result = underTest.getAthletes(PAGEABLE);

    assertNotNull(result);
    assertEquals(3, result.athletes().size());
    assertEquals(testCarvalhoDTO, result.athletes().get(0));
    assertEquals(testCavaleiroDTO, result.athletes().get(1));
    assertEquals(testBelloDTO, result.athletes().get(2));
    assertEquals(0, result.pagination().pageNumber());
    assertEquals(1, result.pagination().totalPages());
    assertEquals(3L, result.pagination().totalItems());
  }

  @Test
  void getAthletesShouldReturnEmptyPage() {
    Page<Athlete> emptyPage = new PageImpl<>(List.of(), PAGEABLE, 0L);
    when(athleteRepository.findAllOrderedByKnownName(PAGEABLE)).thenReturn(emptyPage);

    AthletesPageDTO result = underTest.getAthletes(PAGEABLE);

    assertNotNull(result);
    assertTrue(result.athletes().isEmpty());
    assertEquals(0, result.pagination().totalItems());
  }

  @Test
  void getAthleteByIdShouldReturnAthlete() {
    Athlete testAthlete = AthleteFixture.standard();
    AthleteDTO testAthleteDTO = AthleteDTOFixtures.standard();

    when(athleteRepository.findById(ATHLETE_ID)).thenReturn(Optional.of(testAthlete));
    when(athleteMapper.map(testAthlete)).thenReturn(testAthleteDTO);

    AthleteDTO result = underTest.getAthleteById(ATHLETE_ID);

    assertNotNull(result);
    assertEquals(testAthleteDTO, result);
  }

  @Test
  void getAthleteByIdShouldThrowWhenNotFound() {
    when(athleteRepository.findById(ATHLETE_ID)).thenReturn(Optional.empty());

    EnduranceTrioException exception = assertThrows(EnduranceTrioException.class,
        () -> underTest.getAthleteById(ATHLETE_ID)
    );

    assertEquals(404, exception.getCode());
  }

  @Test
  void getAthleteRacesShouldReturnPageWithRaces() {
    Race race1 = createRace(1L, "Race 1", LocalDate.of(1984, Month.AUGUST, 15));
    Race race2 = createRace(3L, "Race 2", LocalDate.of(1984, Month.AUGUST, 15));

    RaceDTO race1DTO = new RaceDTO(1L, "Race 1", "Subtitle 1", LocalDate.of(1984, Month.AUGUST, 15),
        null, List.of("SWIM"), List.of("SPRINT"), RaceType.INDIVIDUAL_PARENT, "INDIVIDUAL", null,
        null, "UNKNOWN"
    );
    RaceDTO race2DTO = new RaceDTO(3L, "Race 2", "Subtitle 2", LocalDate.of(1984, Month.AUGUST, 15),
        null, List.of("SWIM"), List.of("SPRINT"), RaceType.INDIVIDUAL_PARENT, "INDIVIDUAL", null,
        null, "UNKNOWN"
    );

    Page<Race> racePage = new PageImpl<>(List.of(race1, race2), PAGEABLE, 2L);

    when(individualResultRepository.findRacesByAthleteId(ATHLETE_ID, PAGEABLE)).thenReturn(
        racePage);
    when(raceMapper.mapWithEvent(race1)).thenReturn(race1DTO);
    when(raceMapper.mapWithEvent(race2)).thenReturn(race2DTO);

    AthleteRacesPageDTO result = underTest.getAthleteRaces(ATHLETE_ID, PAGEABLE);

    assertNotNull(result);
    assertEquals(2, result.races().size());
    assertEquals(race1DTO, result.races().get(0));
    assertEquals(race2DTO, result.races().get(1));
    assertEquals(0, result.pagination().pageNumber());
    assertEquals(1, result.pagination().totalPages());
    assertEquals(2L, result.pagination().totalItems());
  }

  @Test
  void getAthleteRacesShouldReturnEmptyPage() {
    Page<Race> emptyPage = new PageImpl<>(List.of(), PAGEABLE, 0L);
    when(individualResultRepository.findRacesByAthleteId(ATHLETE_ID, PAGEABLE)).thenReturn(
        emptyPage);

    AthleteRacesPageDTO result = underTest.getAthleteRaces(ATHLETE_ID, PAGEABLE);

    assertNotNull(result);
    assertTrue(result.races().isEmpty());
    assertEquals(0, result.pagination().totalItems());
  }

  private static Race createRace(Long id, String title, LocalDate date) {
    Race race = new Race();
    race.setId(id);
    race.setRaceReference("19840815NAC001-001");
    race.setRaceType(RaceType.INDIVIDUAL_PARENT);
    race.setTitle(title);
    race.setSubtitle("Subtitle");
    race.setGenderCategory(GenderCategory.OPEN);
    race.setDate(date);
    race.setRaceStatus(RaceStatus.COMPLETED);
    return race;
  }
}
