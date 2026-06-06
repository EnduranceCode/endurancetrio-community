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

package com.endurancetrio.business.event.service;

import com.endurancetrio.business.common.dto.PaginationDTO;
import com.endurancetrio.business.event.dto.EventDTO;
import com.endurancetrio.business.event.dto.EventsPageDTO;
import com.endurancetrio.data.event.repository.EventRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EventServiceMain implements EventService {

  private final EventRepository eventRepository;

  @Autowired
  public EventServiceMain(EventRepository eventRepository) {
    this.eventRepository = eventRepository;
  }

  @Override
  public List<Integer> getEventYears() {
    return eventRepository.findDistinctYears();
  }

  @Override
  public EventsPageDTO getEventsByYear(int year, Pageable pageable) {
    Page<EventDTO> eventPage = eventRepository.findByEventYear(year, pageable)
        .map(event -> {
          List<String> sportCodes = event.getCourses().stream()
              .map(course -> course.getSport().getCode())
              .distinct()
              .sorted()
              .toList();
          return new EventDTO(event.getId(), event.getTitle(), event.getStartDate(),
              event.getEndDate(), event.getCity(), event.getCounty(), event.getDistrict(),
              sportCodes
          );
        });

    return new EventsPageDTO(eventPage.getContent(), PaginationDTO.from(eventPage));
  }
}
