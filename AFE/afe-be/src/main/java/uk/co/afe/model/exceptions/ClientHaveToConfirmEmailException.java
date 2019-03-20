package uk.co.afe.model.exceptions;

import uk.co.afe.model.ErrorCode;

/**
 * @author Sergey Teryoshin
 * 16.03.2018 12:40
 */
public class ClientHaveToConfirmEmailException extends BaseRuntimeException {

    public ClientHaveToConfirmEmailException() {
        super(ErrorCode.CLIENT_HAVE_TO_CONFIRM_EMAIL, "Client have to confirm email to login");
    }

}
