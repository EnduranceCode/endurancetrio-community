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
import com.endurancetrio.business.competitor.dto.AthleteResultDTO;
import com.endurancetrio.business.competitor.dto.AthleteResultsPageDTO;
import com.endurancetrio.business.competitor.dto.AthletesPageDTO;
import com.endurancetrio.business.competitor.dto.LetterRangeAvailabilityDTO;
import com.endurancetrio.business.competitor.enumerator.AthleteLetterRange;
import com.endurancetrio.business.competitor.mapper.AthleteMapper;
import com.endurancetrio.business.event.enumerator.RaceTypeGroup;
import com.endurancetrio.business.event.mapper.RaceMapper;
import com.endurancetrio.data.competitor.model.entity.Athlete;
import com.endurancetrio.data.competitor.repository.AthleteRepository;
import com.endurancetrio.data.competitor.repository.projection.AthleteFirstLetterCount;
import com.endurancetrio.data.event.model.entity.IndividualResult;
import com.endurancetrio.data.event.model.enumerator.Penalty;
import com.endurancetrio.data.event.repository.IndividualResultRepository;
import com.endurancetrio.data.event.repository.TeamResultRepository;
import com.endurancetrio.data.event.repository.projection.ResultPlacement;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
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
  private final TeamResultRepository teamResultRepository;
  private final AthleteMapper athleteMapper;
  private final RaceMapper raceMapper;

  @Autowired
  public AthleteServiceMain(
      AthleteRepository athleteRepository, IndividualResultRepository individualResultRepository,
      TeamResultRepository teamResultRepository, AthleteMapper athleteMapper, RaceMapper raceMapper
  ) {
    this.athleteRepository = athleteRepository;
    this.individualResultRepository = individualResultRepository;
    this.teamResultRepository = teamResultRepository;
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
  public AthleteResultsPageDTO getAthleteResults(Long athleteId, Pageable pageable) {
    Page<Long> sourceResultIdsPage = individualResultRepository.findSourceResultIdsByAthleteId(athleteId, pageable);

    if (sourceResultIdsPage.isEmpty()) {
      return new AthleteResultsPageDTO(List.of(), PaginationDTO.from(sourceResultIdsPage));
    }

    List<Long> orderedSourceIds = sourceResultIdsPage.getContent();

    // Fetch the source results with their race graph (bounded to the page's IDs).
    List<IndividualResult> sourceEntities = individualResultRepository.findResultsByIdInWithRaceGraph(orderedSourceIds);
    Map<Long, IndividualResult> sourcesById = HashMap.newHashMap(sourceEntities.size());
    for (IndividualResult source : sourceEntities) {
      sourcesById.put(source.getId(), source);
    }

    // Resolve the team (relay) placement for each relay race result. On a relay race the finishing
    // position belongs to the team, not to the individual leg, so it must be read from the linked
    // team_result. The query is issued once for the whole page and only when a relay result is
    // present, so non-relay pages pay nothing.
    Map<Long, ResultPlacement> teamPlacementsBySourceId = resolveTeamPlacements(sourceEntities);

    // Fetch the derived results whose source result is one of the page's source results.
    List<IndividualResult> derivedEntities = individualResultRepository.findDerivedResultsBySourceResultIdsWithRaceGraph(
        athleteId, orderedSourceIds);

    Map<Long, List<IndividualResult>> derivedBySourceId = groupDerivedResultsBySource(derivedEntities);

    List<AthleteResultDTO> results = new ArrayList<>(orderedSourceIds.size());
    for (Long sourceId : orderedSourceIds) {
      IndividualResult source = sourcesById.get(sourceId);
      if (source == null) {
        LOG.warn("Source result {} of athlete {} is missing from the race graph fetch", sourceId, athleteId);
        continue;
      }

      // On a relay race the rank and penalty belong to the team result, not to the individual leg.
      EffectivePlacement effectivePlacement = resolveEffectivePlacement(source, teamPlacementsBySourceId);

      List<IndividualResult> derived = derivedBySourceId.getOrDefault(sourceId, List.of());
      List<AthleteResultDTO> derivedResults = new ArrayList<>(derived.size());
      for (IndividualResult derivedResult : derived) {
        IndividualResult sourceResult = sourcesById.get(derivedResult.getSourceResult().getId());
        derivedResults.add(
            new AthleteResultDTO(raceMapper.mapWithEvent(derivedResult.getRace()), derivedResult.getRank(),
                sourceResult.getPenalty(), sourceResult.getSwim(), sourceResult.getFirstBike(),
                sourceResult.getFirstRun(), sourceResult.getT1(), sourceResult.getBike(), sourceResult.getT2(),
                sourceResult.getRun(), sourceResult.getSecondRun(), sourceResult.getT3(), sourceResult.getSecondBike(),
                sourceResult.getTotal(), List.of()
            ));
      }

      results.add(new AthleteResultDTO(raceMapper.mapWithEvent(source.getRace()), effectivePlacement.rank(),
          effectivePlacement.penalty(), source.getSwim(), source.getFirstBike(), source.getFirstRun(), source.getT1(),
          source.getBike(), source.getT2(), source.getRun(), source.getSecondRun(), source.getT3(),
          source.getSecondBike(), source.getTotal(), derivedResults
      ));
    }

    return new AthleteResultsPageDTO(results, PaginationDTO.from(sourceResultIdsPage));
  }

  /**
   * Builds the JPA {@link Specification} used to filter athletes by the given filter criteria.
   *
   * @param filter the athlete filter
   * @return the specification
   */
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

  /**
   * Groups the derived results by their source result ID and orders each group by race date
   * descending and race reference ascending.
   *
   * @param derivedEntities the derived results to group
   * @return the grouped and ordered derived results, keyed by source result ID
   */
  private Map<Long, List<IndividualResult>> groupDerivedResultsBySource(List<IndividualResult> derivedEntities) {
    Map<Long, List<IndividualResult>> derivedBySourceId = HashMap.newHashMap(derivedEntities.size());
    for (IndividualResult derived : derivedEntities) {
      Long sourceId = derived.getSourceResult().getId();
      derivedBySourceId.computeIfAbsent(sourceId, key -> new ArrayList<>()).add(derived);
    }

    // A derived race shares the parent's date, so ordering by race reference ascending lists the
    // "older" derived race first. The date comparison only covers data-contract violations.
    for (List<IndividualResult> derived : derivedBySourceId.values()) {
      derived.sort((first, second) -> {
        int dateComparison = second.getRace().getDate().compareTo(first.getRace().getDate());
        if (dateComparison != 0) {
          return dateComparison;
        }

        return first.getRace().getRaceReference().compareTo(second.getRace().getRaceReference());
      });
    }

    return derivedBySourceId;
  }

  /**
   * Resolves the effective rank and penalty for a source result. On a relay race these come from
   * the linked team result rather than from the individual leg; otherwise the individual values
   * are kept.
   *
   * @param source the source result
   * @param teamPlacementsBySourceId the resolved team placements, keyed by individual result ID
   * @return the effective rank and penalty
   */
  private EffectivePlacement resolveEffectivePlacement(
      IndividualResult source, Map<Long, ResultPlacement> teamPlacementsBySourceId) {
    Integer rank = source.getRank();
    Penalty penalty = source.getPenalty();
    if (RaceTypeGroup.from(source.getRace().getRaceType()) != RaceTypeGroup.RELAY) {
      return new EffectivePlacement(rank, penalty);
    }

    ResultPlacement teamPlacement = teamPlacementsBySourceId.get(source.getId());
    if (teamPlacement != null && (teamPlacement.getRank() != null || teamPlacement.getPenalty() != null)) {
      return new EffectivePlacement(teamPlacement.getRank(), teamPlacement.getPenalty());
    }

    return new EffectivePlacement(rank, penalty);
  }

  /**
   * Collects the IDs of the relay source results on the page and resolves their team placements
   * in a single query.
   *
   * @param results the page's source results
   * @return the team placements, keyed by individual result ID
   */
  private Map<Long, ResultPlacement> resolveTeamPlacements(List<IndividualResult> results) {
    List<Long> relayResultIds = new ArrayList<>(results.size());
    for (IndividualResult result : results) {
      if (RaceTypeGroup.from(result.getRace().getRaceType()) == RaceTypeGroup.RELAY) {
        relayResultIds.add(result.getId());
      }
    }

    Map<Long, ResultPlacement> teamResultPlacements = HashMap.newHashMap(results.size());
    if (relayResultIds.isEmpty()) {
      return teamResultPlacements;
    }

    for (ResultPlacement placement : teamResultRepository.findTeamPlacementsByIndividualResultIds(relayResultIds)) {
      teamResultPlacements.put(placement.getIndividualResultId(), placement);
    }
    return teamResultPlacements;
  }

  /**
   * Holds the effective rank and penalty resolved for a source result, which on a relay race come
   * from the linked team result rather than from the individual leg.
   */
  private record EffectivePlacement(Integer rank, Penalty penalty) {

  }
}
