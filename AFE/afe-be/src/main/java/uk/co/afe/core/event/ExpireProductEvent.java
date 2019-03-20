package uk.co.afe.core.event;

import uk.co.afe.model.product.Product;

/**
 * @author Sergey Teryoshin
 * 29.03.2018 23:51
 */
public final class ExpireProductEvent extends BaseEvent<Product> {
    public ExpireProductEvent(Product source) {
        super(source);
    }
}
