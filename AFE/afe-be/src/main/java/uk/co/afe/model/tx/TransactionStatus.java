package uk.co.afe.model.tx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import uk.co.afe.model.exceptions.UnknownEnumerateValueException;
import uk.co.afe.model.ErrorCode;

/**
 * Status of transaction
 *
 * @author Sergey Teryoshin
 * 24.03.2018 12:25
 */
@ApiModel(description = "Status of transaction")
public enum TransactionStatus {

    /**
     * The bid is created and verified
     */
    @ApiModelProperty("The bid is created and verified")
    SUBMITTED(0),

    /**
     * The bid is accepted by lot owner
     */
    @ApiModelProperty("The bid is accepted by lot owner")
    ACCEPTED(1),

    /**
     * The bids of rejected, hidden, canceled lots
     */
    @ApiModelProperty("The bids of rejected, hidden, canceled lots")
    CLOSED(2),

    /**
     * The bid was accepted by lot owner and approved by back-office
     */
    @ApiModelProperty("The bid was accepted by lot owner and approved by back-office")
    COMPLETED(3),

    /**
     * The bid was not accepted yet and canceled by bid owner
     */
    @ApiModelProperty("The bid was not accepted yet and canceled by bid owner")
    WITHDRAWAL(4);

    private final Integer code;

    TransactionStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public static TransactionStatus valueOf(Integer code) {
        for (TransactionStatus status : TransactionStatus.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new UnknownEnumerateValueException(ErrorCode.UNKNOWN_ENUM_VALUE, TransactionStatus.class, code);
    }
}
