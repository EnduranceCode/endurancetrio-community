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

package com.endurancetrio.data.event.model.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.Duration;
import java.util.Optional;

/**
 * The {@link DurationToIntegerConverter} class is responsible for converting {@link Duration}
 * values to their corresponding database column representation (milliseconds) and vice versa.
 * <p>
 * This converter is used to persist {@link Duration} fields as an {@link Integer} of milliseconds
 * in the database. It converts the {@link Duration} to its total milliseconds when storing it and
 * converts the milliseconds back to a {@link Duration} when retrieving it from the database.
 */
@Converter
public class DurationToIntegerConverter implements AttributeConverter<Duration, Integer> {

  @Override
  public Integer convertToDatabaseColumn(Duration duration) {
    return Optional.ofNullable(duration).map(d -> (int) d.toMillis()).orElse(null);
  }

  @Override
  public Duration convertToEntityAttribute(Integer milliseconds) {
    return Optional.ofNullable(milliseconds).map(Duration::ofMillis).orElse(null);
  }
}
