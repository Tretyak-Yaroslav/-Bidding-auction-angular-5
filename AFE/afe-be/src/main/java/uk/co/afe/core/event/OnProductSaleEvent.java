package uk.co.afe.core.event;

import uk.co.afe.model.product.Product;

/**
 * @author Sergey Teryoshin
 * 28.03.2018 12:36
 */
public final class OnProductSaleEvent extends BaseEvent<Product> {
    public OnProductSaleEvent(Product source) {
        super(source);
    }
}
