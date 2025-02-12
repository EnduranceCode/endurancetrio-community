package com.endurancetrio.data.model.converter;

import com.endurancetrio.data.model.enumerator.DistanceType;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.util.stream.Stream;

@Converter
public class DistanceTypeConverter implements AttributeConverter<DistanceType, String> {

  @Override
  public String convertToDatabaseColumn(DistanceType distanceType) {
    if (distanceType == null) {
      return null;
    }

    return distanceType.getCode();
  }

  @Override
  public DistanceType convertToEntityAttribute(String code) {
    return Stream.of(DistanceType.values())
        .filter(distanceType -> distanceType.getCode().equals(code))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException(
            String.format("The value '%s' returned from the database is not valid", code)));
  }
}
