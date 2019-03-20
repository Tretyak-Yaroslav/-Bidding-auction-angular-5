package uk.co.afe.model.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.co.afe.model.ErrorCode;
import uk.co.afe.model.exceptions.UnknownEnumerateValueException;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Status of product
 *
 * @author Sergey Teryoshin
 * 20.03.2018 16:20
 */
@ApiModel(description = "Status of product")
public enum ProductStatus {

    /**
     * Pending to approve by back-office
     */
    @ApiModelProperty("Pending to approve by back-office")
    PENDING(1, Arrays.asList(2, 3)),
    /**
     * Product addition is rejected
     */
    @ApiModelProperty("Product addition is rejected")
    REJECTED(2, Collections.emptyList()),
    /**
     * Product is registered to auction
     */
    @ApiModelProperty("Product is registered to auction")
    FOR_SALE(3, Arrays.asList(4, 6)),
    /**
     * Auction has been finished. Back-office working on
     */
    @ApiModelProperty("Auction has been finished. Back-office working on")
    SALE_PENDING(4, Arrays.asList(3, 5, 6)),
    /**
     * Product change owner
     */
    @ApiModelProperty("Product change owner")
    OWNED(5, Collections.emptyList()),
    /**
     * Lot is expired
     */
    @ApiModelProperty("Lot is expired")
    EXPIRED(6, Collections.emptyList());

    /**
     * Code of status
     */
    private final Integer code;
    /**
     * Available statuses to be changed
     */
    private List<Integer> availableStatuses;

    ProductStatus(Integer code) {
        this.code = code;
    }

    ProductStatus(Integer code, List<Integer> availableStatuses) {
        this(code);
        this.availableStatuses = availableStatuses;
    }

    public Integer getCode() {
        return code;
    }

    /**
     * Check if current status can be changed to specified
     */
    public Boolean canBeChangedTo(ProductStatus status) {
        for (Integer availableStatus : this.availableStatuses) {
            if (availableStatus.equals(status.code)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Getting enumerate value by code of value
     */
    public static ProductStatus valueOf(Integer code) {
        for (ProductStatus status : ProductStatus.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new UnknownEnumerateValueException(ErrorCode.UNKNOWN_ENUM_VALUE, ProductStatus.class, code);
    }
}
