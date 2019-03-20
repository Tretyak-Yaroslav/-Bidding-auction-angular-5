package uk.co.afe.model.exceptions;

import uk.co.afe.model.ErrorCode;

/**
 * @author Sergey Teryoshin
 * 31.03.2018 18:37
 */
public class UnknownConfirmationTokenException extends BaseRuntimeException {
    public UnknownConfirmationTokenException() {
        super(ErrorCode.UNKNOWN_CONFIRMATION_TOKEN, "Unknown confirmation token");
    }
}
