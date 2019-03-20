package uk.co.afe.core.event;

import uk.co.afe.model.product.Product;

/**
 * @author Sergey Teryoshin
 * 27.03.2018 22:39
 */
public final class ProductCreatedEvent extends BaseEvent<Product> {
    public ProductCreatedEvent(Product source) {
        super(source);
    }
}
