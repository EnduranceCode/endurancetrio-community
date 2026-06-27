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

import com.endurancetrio.business.common.dto.PaginationDTO;
import com.endurancetrio.business.competitor.dto.AthleteDTO;
import com.endurancetrio.business.competitor.dto.AthletesPageDTO;
import com.endurancetrio.business.competitor.mapper.AthleteMapper;
import com.endurancetrio.data.competitor.repository.AthleteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AthleteServiceMain implements AthleteService {

  private final AthleteRepository athleteRepository;
  private final AthleteMapper athleteMapper;

  @Autowired
  public AthleteServiceMain(AthleteRepository athleteRepository, AthleteMapper athleteMapper) {
    this.athleteRepository = athleteRepository;
    this.athleteMapper = athleteMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public AthletesPageDTO getAthletes(Pageable pageable) {
    Page<AthleteDTO> athletePage = athleteRepository.findAllOrderedByKnownName(pageable)
        .map(athleteMapper::mapToAthleteDTO);

    return new AthletesPageDTO(athletePage.getContent(), PaginationDTO.from(athletePage));
  }
}
