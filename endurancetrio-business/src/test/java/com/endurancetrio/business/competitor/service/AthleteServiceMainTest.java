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
 * EVEN IF WE HAVE BEEN INFORMED OF THE POSSIBILITY IN ADVANCE.
 */

package com.endurancetrio.business.competitor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import com.endurancetrio.business.competitor.dto.AthleteDTO;
import com.endurancetrio.business.competitor.dto.AthletesPageDTO;
import com.endurancetrio.business.competitor.fixtures.AthleteDTOFixtures;
import com.endurancetrio.business.competitor.fixtures.AthleteFixture;
import com.endurancetrio.business.competitor.mapper.AthleteMapper;
import com.endurancetrio.data.competitor.model.entity.Athlete;
import com.endurancetrio.data.competitor.repository.AthleteRepository;
import java.util.List;
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

  @Mock
  AthleteRepository athleteRepository;

  @Mock
  AthleteMapper athleteMapper;

  @InjectMocks
  AthleteServiceMain underTest;

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
    when(athleteMapper.mapToAthleteDTO(testCarvalhoEntity)).thenReturn(testCarvalhoDTO);
    when(athleteMapper.mapToAthleteDTO(testCavaleiroEntity)).thenReturn(testCavaleiroDTO);
    when(athleteMapper.mapToAthleteDTO(testBelloEntity)).thenReturn(testBelloDTO);

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
}
