package uk.co.afe.model.exceptions;

import uk.co.afe.model.ErrorCode;

/**
 * @author Sergey Teryoshin
 * 16.03.2018 12:40
 */
public class IncorrectCredentialsException extends BaseRuntimeException {

    public IncorrectCredentialsException() {
        super(ErrorCode.INCORRECT_CREDENTIALS, "Specified credentials is incorrect");
    }

}
