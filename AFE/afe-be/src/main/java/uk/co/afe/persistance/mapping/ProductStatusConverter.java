package uk.co.afe.persistance.mapping;

import uk.co.afe.model.product.ProductStatus;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * @author Sergey Teryoshin
 * 20.03.2018 16:35
 */
@Converter
public class ProductStatusConverter implements AttributeConverter<ProductStatus, Integer> {

    public Integer convertToDatabaseColumn(ProductStatus status) {
        if (status == null) {
            return null;
        }
        return status.getCode();
    }

    public ProductStatus convertToEntityAttribute(Integer data) {
        if (data == null) {
            return null;
        }
        return ProductStatus.valueOf(data);
    }
}
