package uk.co.afe.persistance.mapping;

import uk.co.afe.model.tx.TransactionStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Sergey Teryoshin
 * 24.03.2018 12:40
 */
@Converter
public class TransactionStatusConverter implements AttributeConverter<TransactionStatus, Integer> {
    @Override
    public Integer convertToDatabaseColumn(TransactionStatus status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    @Override
    public TransactionStatus convertToEntityAttribute(Integer code) {
        if (code == null) {
            return null;
        }
        return TransactionStatus.valueOf(code);
    }
}
