package uk.co.afe.model.exceptions;

import uk.co.afe.model.ErrorCode;
import uk.co.afe.model.tx.TransactionStatus;

/**
 * @author Sergey Teryoshin
 * 28.03.2018 9:51
 */
public class ChangeTransactionStatusException extends BaseRuntimeException {

    public ChangeTransactionStatusException(Long txId, TransactionStatus curStatus, TransactionStatus newStatus) {
        super(ErrorCode.CHANGE_TX_STATUS_ERROR,
                String.format("Error while changing stats of bid %s. Status of bid cannot be " +
                        "changed to %s because current status is %s", txId, newStatus, curStatus));
    }
}
