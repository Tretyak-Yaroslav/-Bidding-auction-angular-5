package uk.co.afe.model.exceptions;

import uk.co.afe.model.ErrorCode;

/**
 * @author Sergey Teryoshin
 * 16.03.2018 12:40
 */
public class ClientNeedApproveException extends BaseRuntimeException {

    public ClientNeedApproveException() {
        super(ErrorCode.CLIENT_NEED_APPROVE, "Client need approve");
    }

}
