package uk.co.afe.persistance.mapping;

import uk.co.afe.model.client.ClientStatus;
import uk.co.afe.model.product.ProductStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Sergey Teryoshin
 * 20.03.2018 16:35
 */
@Converter
public class ClientStatusConverter implements AttributeConverter<ClientStatus, Integer> {

    public Integer convertToDatabaseColumn(ClientStatus status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    public ClientStatus convertToEntityAttribute(Integer data) {
        if (data == null) {
            return null;
        }
        return ClientStatus.valueOf(data);
    }
}
