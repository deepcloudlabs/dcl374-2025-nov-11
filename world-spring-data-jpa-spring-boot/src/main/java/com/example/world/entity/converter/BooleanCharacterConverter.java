package com.example.world.entity.converter;

import java.util.Objects;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * 
 * @author Binnur Kurt <binnur.kurt@gmail.com>
 *
 */
@Converter
public class BooleanCharacterConverter implements AttributeConverter<Boolean, String> {

	@Override
	public String convertToDatabaseColumn(Boolean value) {
		if (Objects.isNull(value)) return "F";
		return value ? "T" : "F";
	}

	@Override
	public Boolean convertToEntityAttribute(String value) {
		return "T".equals(value);
	}

}
