package uk.co.afe.persistance.mapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import uk.co.afe.model.exceptions.ObjectConvertationException;
import uk.co.afe.model.questionaries.Questionnaire;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Sergey Teryoshin
 * 22.03.2018 12:21
 */
@Converter
public class QuestionnaireConverter implements AttributeConverter<Questionnaire, String> {

    private final ObjectMapper mapper = new ObjectMapper();

    public String convertToDatabaseColumn(Questionnaire attribute) {
        try {
            if (attribute == null) {
                return null;
            }
            return mapper.writeValueAsString(attribute);
        } catch (Exception e) {
            throw new ObjectConvertationException("Questionnaire", e);
        }
    }

    public Questionnaire convertToEntityAttribute(String json) {
        try {
            if (json == null) {
                return null;
            }
            return mapper.readValue(json, Questionnaire.class);
        } catch (Exception e) {
            throw new ObjectConvertationException("Questionnaire", e);
        }
    }
}
