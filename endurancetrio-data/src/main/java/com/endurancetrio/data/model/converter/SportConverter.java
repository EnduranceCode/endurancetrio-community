package com.endurancetrio.data.model.converter;

import com.endurancetrio.data.model.enumerator.Sport;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class SportConverter implements AttributeConverter<Sport, String> {

  @Override
  public String convertToDatabaseColumn(Sport sport) {
    if (sport == null) {
      return null;
    }

    return sport.getCode();
  }

  @Override
  public Sport convertToEntityAttribute(String code) {
    return Stream.of(Sport.values())
        .filter(sport -> sport.getCode().equals(code))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("The value '%s' returned from the database is not valid", code)));
  }
}
