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

import com.endurancetrio.business.common.dto.ErrorDTO;
import com.endurancetrio.business.common.dto.PaginationDTO;
import com.endurancetrio.business.common.exception.EnduranceTrioError;
import com.endurancetrio.business.common.exception.EnduranceTrioException;
import com.endurancetrio.business.competitor.dto.AthleteDTO;
import com.endurancetrio.business.competitor.dto.AthleteRacesPageDTO;
import com.endurancetrio.business.competitor.dto.AthletesPageDTO;
import com.endurancetrio.business.competitor.mapper.AthleteMapper;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.mapper.RaceMapper;
import com.endurancetrio.data.competitor.repository.AthleteRepository;
import com.endurancetrio.data.event.repository.IndividualResultRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AthleteServiceMain implements AthleteService {

  private static final Logger LOG = LoggerFactory.getLogger(AthleteServiceMain.class);

  private final AthleteRepository athleteRepository;
  private final IndividualResultRepository individualResultRepository;
  private final AthleteMapper athleteMapper;
  private final RaceMapper raceMapper;

  @Autowired
  public AthleteServiceMain(
      AthleteRepository athleteRepository,
      IndividualResultRepository individualResultRepository, AthleteMapper athleteMapper,
      RaceMapper raceMapper
  ) {
    this.athleteRepository = athleteRepository;
    this.individualResultRepository = individualResultRepository;
    this.athleteMapper = athleteMapper;
    this.raceMapper = raceMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public AthletesPageDTO getAthletes(Pageable pageable) {
    Page<AthleteDTO> athletePage = athleteRepository.findAllOrderedByKnownName(pageable)
        .map(athleteMapper::mapToAthleteDTO);

    return new AthletesPageDTO(athletePage.getContent(), PaginationDTO.from(athletePage));
  }

  @Override
  @Transactional(readOnly = true)
  public AthleteDTO getAthleteById(Long id) {
    return athleteRepository.findById(id).map(athleteMapper::mapToAthleteDTO).orElseThrow(() -> {
      String errorMsg = String.format("No athlete found with ID %d", id);
      LOG.warn(errorMsg);
      return new EnduranceTrioException(new ErrorDTO(EnduranceTrioError.NOT_FOUND, errorMsg));
    });
  }

  @Override
  @Transactional(readOnly = true)
  public AthleteRacesPageDTO getAthleteRaces(Long athleteId, Pageable pageable) {
    Page<RaceDTO> racePage = individualResultRepository.findRacesByAthleteId(athleteId, pageable)
        .map(raceMapper::mapWithEvent);

    return new AthleteRacesPageDTO(racePage.getContent(), PaginationDTO.from(racePage));
  }
}
