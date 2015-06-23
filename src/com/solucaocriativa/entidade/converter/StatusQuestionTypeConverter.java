package com.solucaocriativa.entidade.converter;


import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

import com.solucaocriativa.entidade.StatusQuestionType;

@Converter(autoApply = true)
public class StatusQuestionTypeConverter implements AttributeConverter<StatusQuestionType, String> {

    @Override
    public String convertToDatabaseColumn(StatusQuestionType attribute) {
	switch (attribute) {
	case YES:
	    return "Y";
	case NO:
	    return "N";
	default:
	    throw new IllegalArgumentException("Unknown" + attribute);
	}
    }

    @Override
    public StatusQuestionType convertToEntityAttribute(String dbData) {
	switch (dbData) {
	case "Y":
	    return StatusQuestionType.YES;
	case "N":
	    return StatusQuestionType.NO;
	default:
	    throw new IllegalArgumentException("Unknown" + dbData);
	}
    }
}
