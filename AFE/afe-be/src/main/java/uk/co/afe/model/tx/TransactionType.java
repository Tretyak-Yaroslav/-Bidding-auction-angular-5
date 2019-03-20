package uk.co.afe.model.tx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.co.afe.model.exceptions.UnknownEnumerateValueException;
import uk.co.afe.model.ErrorCode;

/**
 * Type of transaction
 *
 * @author Sergey Teryoshin
 * 24.03.2018 12:19
 */
@ApiModel("Type of transaction")
public enum TransactionType {

    /**
     * Transaction is a bid
     */
    @ApiModelProperty("Transaction is a bid")
    BID(0),
    /**
     * Transaction is a payment
     */
    @ApiModelProperty("Transaction is a payment")
    PAYMENT(1);

    private final Integer code;

    TransactionType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    /**
     * Getting enumerate value by code of value
     */
    public static TransactionType valueOf(Integer code) {
        for (TransactionType status : TransactionType.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new UnknownEnumerateValueException(ErrorCode.UNKNOWN_ENUM_VALUE, TransactionType.class, code);
    }
}
