package uk.co.afe.persistance.mapping;

import uk.co.afe.model.tx.TransactionType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Sergey Teryoshin
 * 24.03.2018 12:22
 */
@Converter
public class TransactionTypeConverter implements AttributeConverter<TransactionType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TransactionType type) {
        if (type == null) {
            return null;
        }
        return type.getCode();
    }

    @Override
    public TransactionType convertToEntityAttribute(Integer code) {
        if(code == null) {
            return null;
        }
        return TransactionType.valueOf(code);
    }
}
