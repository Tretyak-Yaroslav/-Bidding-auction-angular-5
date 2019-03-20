package uk.co.afe.model.exceptions;

import uk.co.afe.model.ErrorCode;

/**
 * @author Sergey Teryoshin
 * 28.03.2018 10:23
 */
public class BidWithdrawException extends BaseRuntimeException {
    public BidWithdrawException(Long txId) {
        super(ErrorCode.BID_CANT_BE_WITHDRAWAL, String.format("Bid '%s' can't be withdrawal. No auction", txId));
    }
}
