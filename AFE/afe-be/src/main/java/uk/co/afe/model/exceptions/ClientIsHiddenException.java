package uk.co.afe.model.exceptions;

import uk.co.afe.model.ErrorCode;

/**
 * @author Sergey Teryoshin
 * 23.03.2018 11:34
 */
public class ClientIsHiddenException extends BaseRuntimeException {
    public ClientIsHiddenException(String email) {
        super(ErrorCode.CLIENT_IS_HIDDEN, String.format("Client with email '%s' is hidden and can't be login in", email));
    }
}
