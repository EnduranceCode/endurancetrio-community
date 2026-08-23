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
import com.endurancetrio.business.competitor.dto.AthleteFilterDTO;
import com.endurancetrio.business.competitor.dto.AthleteRacesPageDTO;
import com.endurancetrio.business.competitor.dto.AthletesPageDTO;
import com.endurancetrio.business.competitor.dto.LetterRangeAvailabilityDTO;
import com.endurancetrio.business.competitor.enumerator.AthleteLetterRange;
import com.endurancetrio.business.competitor.mapper.AthleteMapper;
import com.endurancetrio.business.event.dto.RaceDTO;
import com.endurancetrio.business.event.mapper.RaceMapper;
import com.endurancetrio.data.competitor.model.entity.Athlete;
import com.endurancetrio.data.competitor.repository.AthleteRepository;
import com.endurancetrio.data.competitor.repository.projection.AthleteFirstLetterCount;
import com.endurancetrio.data.event.repository.IndividualResultRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AthleteServiceMain implements AthleteService {

  private static final String ATTRIBUTE_GENDER = "gender";
  private static final String ATTRIBUTE_ID = "id";
  private static final String ATTRIBUTE_KNOWN_NAME = "knownName";
  private static final String ATTRIBUTE_LONG_NAME = "longName";
  private static final String FOLD_FROM = "áàâãäåéèêëíìîïóòôõöøúùûüýÿñçšž";
  private static final String FOLD_TO = "aaaaaaeeeeiiiiooooouuuuyyyncsz";

  private static final Logger LOG = LoggerFactory.getLogger(AthleteServiceMain.class);

  private final AthleteRepository athleteRepository;
  private final IndividualResultRepository individualResultRepository;
  private final AthleteMapper athleteMapper;
  private final RaceMapper raceMapper;

  @Autowired
  public AthleteServiceMain(
      AthleteRepository athleteRepository, IndividualResultRepository individualResultRepository,
      AthleteMapper athleteMapper, RaceMapper raceMapper
  ) {
    this.athleteRepository = athleteRepository;
    this.individualResultRepository = individualResultRepository;
    this.athleteMapper = athleteMapper;
    this.raceMapper = raceMapper;
  }

  @Override
  @Transactional(readOnly = true)
  public AthletesPageDTO getAthletes(Pageable pageable, AthleteFilterDTO filter) {
    Specification<Athlete> specification = buildSpecification(filter);
    Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
        Sort.by(ATTRIBUTE_KNOWN_NAME).and(Sort.by(ATTRIBUTE_ID))
    );
    Page<AthleteDTO> athletePage = athleteRepository.findAll(specification, sortedPageable).map(athleteMapper::map);

    Map<AthleteLetterRange, Long> counts = new EnumMap<>(AthleteLetterRange.class);
    for (AthleteLetterRange range : AthleteLetterRange.values()) {
      counts.put(range, 0L);
    }
    for (AthleteFirstLetterCount count : athleteRepository.findGlobalFirstLetterCounts()) {
      String letter = count.getLetter();
      if (letter != null && !letter.isEmpty()) {
        char firstLetter = letter.toUpperCase(Locale.ROOT).charAt(0);
        for (AthleteLetterRange range : AthleteLetterRange.values()) {
          if (firstLetter >= range.getStart() && firstLetter <= range.getEnd()) {
            counts.merge(range, count.getTotal(), Long::sum);
            break;
          }
        }
      }
    }
    List<LetterRangeAvailabilityDTO> letterRanges = new ArrayList<>();
    for (AthleteLetterRange range : AthleteLetterRange.values()) {
      letterRanges.add(new LetterRangeAvailabilityDTO(range.getId(), counts.get(range)));
    }

    return new AthletesPageDTO(athletePage.getContent(), PaginationDTO.from(athletePage), letterRanges);
  }

  @Override
  @Transactional(readOnly = true)
  public AthleteDTO getAthleteById(Long id) {
    return athleteRepository.findById(id).map(athleteMapper::map).orElseThrow(() -> {
      String errorMsg = String.format("No athlete found with ID %d", id);
      LOG.warn(errorMsg);
      return new EnduranceTrioException(new ErrorDTO(EnduranceTrioError.NOT_FOUND, errorMsg));
    });
  }

  @Override
  @Transactional(readOnly = true)
  public AthleteRacesPageDTO getAthleteRaces(Long athleteId, Pageable pageable) {
    Page<RaceDTO> racePage = individualResultRepository.findRacesByAthleteId(athleteId, pageable)
        .map(raceMapper::mapWithoutDistanceWithEvent);

    return new AthleteRacesPageDTO(racePage.getContent(), PaginationDTO.from(racePage));
  }

  private Specification<Athlete> buildSpecification(AthleteFilterDTO filter) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (filter.letterRange() != null) {
        AthleteLetterRange range = AthleteLetterRange.fromId(filter.letterRange());
        if (range != null) {
          predicates.add(criteriaBuilder.between(foldedInitial(root, criteriaBuilder),
              String.valueOf(Character.toLowerCase(range.getStart())),
              String.valueOf(Character.toLowerCase(range.getEnd()))
          ));
        }
      }

      if (filter.gender() != null) {
        predicates.add(criteriaBuilder.equal(root.get(ATTRIBUTE_GENDER), filter.gender()));
      }

      if (filter.searchTerm() != null) {
        String escapedTerm = escapeSearchTerm(filter.searchTerm());
        Expression<String> foldedTerm = folded(criteriaBuilder.literal(escapedTerm), criteriaBuilder);
        Expression<String> pattern = criteriaBuilder.concat(criteriaBuilder.literal("%"),
            criteriaBuilder.concat(foldedTerm, criteriaBuilder.literal("%"))
        );
        predicates.add(criteriaBuilder.or(
            criteriaBuilder.like(folded(root.get(ATTRIBUTE_KNOWN_NAME), criteriaBuilder), pattern, '\\'),
            criteriaBuilder.like(folded(root.get(ATTRIBUTE_LONG_NAME), criteriaBuilder), pattern, '\\')
        ));
      }

      return predicates.isEmpty() ? criteriaBuilder.conjunction()
          : criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    };
  }

  private static String escapeSearchTerm(String searchTerm) {
    return searchTerm.replace("\\", "\\\\").replace("%", "\\%").replace("_", "\\_");
  }

  private static Expression<String> folded(Expression<String> expression, CriteriaBuilder criteriaBuilder) {
    return criteriaBuilder.function("translate", String.class, criteriaBuilder.lower(expression),
        criteriaBuilder.literal(FOLD_FROM), criteriaBuilder.literal(FOLD_TO)
    );
  }

  private static Expression<String> foldedInitial(Root<Athlete> root, CriteriaBuilder criteriaBuilder) {
    return criteriaBuilder.substring(folded(root.get(ATTRIBUTE_KNOWN_NAME), criteriaBuilder), 1, 1);
  }
}
