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

package com.endurancetrio.data.competitor.model.converter;

import com.endurancetrio.data.competitor.model.enumerator.AthleteGender;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * The {@link AthleteGenderConverter} class is responsible for converting the {@link AthleteGender}
 * enum to its corresponding database column representation and vice versa.
 * <p>
 * This converter is used to persist the {@link AthleteGender} enum as a string in the database. It
 * converts the enum to its code when storing it in the database and converts the code back to the
 * enum when retrieving it from the database.
 */
@Converter
public class AthleteGenderConverter implements AttributeConverter<AthleteGender, String> {

  @Override
  public String convertToDatabaseColumn(AthleteGender athleteGender) {
    return Optional.ofNullable(athleteGender).map(AthleteGender::getCode).orElse(null);
  }

  @Override
  public AthleteGender convertToEntityAttribute(String code) {
    return Stream.of(AthleteGender.values())
        .filter(gender -> gender.getCode().equals(code))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            "The value '" + code + "' returned from the database is not a valid AthleteGender"));
  }
}
