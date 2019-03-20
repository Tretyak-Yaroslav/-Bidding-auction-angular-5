package uk.co.afe.model.exceptions;

import uk.co.afe.model.ErrorCode;
import uk.co.afe.model.product.ProductStatus;

/**
 * @author Sergey Teryoshin
 * 27.03.2018 22:16
 */
public class ChangeProductStatusException extends BaseRuntimeException {

    public ChangeProductStatusException(Long productId, ProductStatus curStatus, ProductStatus newStatus) {
        super(ErrorCode.CHANGE_PRODUCT_STATUS_ERROR,
                String.format("Error while changing stats of product %s. Status of product can be " +
                        "changed to %s because current status is %s", productId, newStatus, curStatus));
    }
}
