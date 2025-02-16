/*
 * Copyright (c) 2011-2025 Ricardo do Canto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.endurancetrio.data.model.converter;

import com.endurancetrio.data.model.enumerator.OrganizerType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * The {@link OrganizerTypeConverter} class is responsible for converting the {@link OrganizerType}
 * enum to its corresponding database column representation and vice versa.
 * <p>
 * This converter is used to persist the {@link OrganizerType} enum as a string in the database. It
 * converts the enum to its code when storing it in the database and converts the code back to the
 * enum when retrieving it from the database.
 */
@Converter
public class OrganizerTypeConverter implements AttributeConverter<OrganizerType, String> {

  @Override
  public String convertToDatabaseColumn(OrganizerType organizerType) {
    return Optional.ofNullable(organizerType)
        .map(OrganizerType::getCode)
        .orElse(null);
  }

  @Override
  public OrganizerType convertToEntityAttribute(String code) {
    return Stream.of(OrganizerType.values())
        .filter(organizerType -> organizerType.getCode().equals(code.toUpperCase()))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("The value '%s' returned from the database is not valid", code)));
  }
}
