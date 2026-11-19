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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.Test;

class EventFilterOptionsDTOTest {

  @Test
  void ofPreservesOrderAndBuildsLookupSets() {
    Map<String, List<String>> countiesByDistrict = new LinkedHashMap<>();
    countiesByDistrict.put("Aveiro", List.of("Aveiro", "Ovar"));
    countiesByDistrict.put("Lisboa", List.of("Cascais"));

    EventFilterOptionsDTO options = EventFilterOptionsDTO.of(List.of(1989, 1984), countiesByDistrict);

    assertEquals(List.of(1989, 1984), options.years());
    assertEquals(Set.of(1989, 1984), options.yearLookup());
    assertEquals(List.of("Aveiro", "Lisboa"), new ArrayList<>(options.countiesByDistrict().keySet()));
    assertEquals(Set.of("Aveiro", "Cascais", "Ovar"), options.countyLookup());
    assertEquals(Set.of("Aveiro", "Ovar"), options.countyLookupByDistrict().get("Aveiro"));
  }

  @Test
  void ofReturnsImmutableCollections() {
    EventFilterOptionsDTO options = EventFilterOptionsDTO.of(List.of(1984), Map.of("Aveiro", List.of("Ovar")));

    List<Integer> years = options.years();
    assertThrows(UnsupportedOperationException.class, () -> years.add(1985));
    Set<Integer> yearLookup = options.yearLookup();
    assertThrows(UnsupportedOperationException.class, () -> yearLookup.add(1985));
    Map<String, List<String>> countiesByDistrict = options.countiesByDistrict();
    List<String> newCountyList = List.of("Cascais");
    assertThrows(UnsupportedOperationException.class, () -> countiesByDistrict.put("Lisboa", newCountyList));
    List<String> aveiroCounties = countiesByDistrict.get("Aveiro");
    assertThrows(UnsupportedOperationException.class, () -> aveiroCounties.add("Espinho"));
    Set<String> countyLookup = options.countyLookup();
    assertThrows(UnsupportedOperationException.class, () -> countyLookup.add("Cascais"));
    Set<String> aveiroCountyLookup = options.countyLookupByDistrict().get("Aveiro");
    assertThrows(UnsupportedOperationException.class, () -> aveiroCountyLookup.add("Espinho"));
  }
}
