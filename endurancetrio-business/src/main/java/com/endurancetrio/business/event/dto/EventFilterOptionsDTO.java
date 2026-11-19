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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Filter options for the event directory, with precomputed lookup sets for validation.
 *
 * @param years the years available as filters, in descending order
 * @param yearLookup the years available as filters
 * @param countiesByDistrict the counties available as filters, grouped by district
 * @param countyLookup every county available as a filter
 * @param countyLookupByDistrict the counties available as filters, grouped by district
 */
public record EventFilterOptionsDTO(
    List<Integer> years,
    Set<Integer> yearLookup,
    Map<String, List<String>> countiesByDistrict,
    Set<String> countyLookup,
    Map<String, Set<String>> countyLookupByDistrict
) {

  /**
   * Creates an immutable set of filter options from the year and location values in the archive.
   *
   * @param years the years present in the archive
   * @param countiesByDistrict the counties present in the archive, grouped by district
   * @return the filter options with precomputed lookup sets
   */
  public static EventFilterOptionsDTO of(List<Integer> years, Map<String, List<String>> countiesByDistrict) {
    Map<String, List<String>> orderedCountiesByDistrict = new LinkedHashMap<>();
    Map<String, Set<String>> orderedCountyLookupByDistrict = new LinkedHashMap<>();
    Set<String> countyLookup = new LinkedHashSet<>();

    countiesByDistrict.forEach((district, counties) -> {
      List<String> orderedCounties = List.copyOf(counties);
      orderedCountiesByDistrict.put(district, orderedCounties);
      orderedCountyLookupByDistrict.put(district, Set.copyOf(orderedCounties));
      countyLookup.addAll(orderedCounties);
    });

    return new EventFilterOptionsDTO(List.copyOf(years), Set.copyOf(years),
        Collections.unmodifiableMap(orderedCountiesByDistrict), Set.copyOf(countyLookup),
        Collections.unmodifiableMap(orderedCountyLookupByDistrict)
    );
  }
}
