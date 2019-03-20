package uk.co.afe.model.client;

import uk.co.afe.model.ErrorCode;
import uk.co.afe.model.exceptions.UnknownEnumerateValueException;

/**
 * Client status
 *
 * @author Sergey Teryoshin
 * 28.03.2018 22:13
 */
public enum ClientStatus {

    /**
     * Client should confirm email
     */
    CONFIRMATION(0),
    /**
     * Client addition is pending
     */
    PENDING(1),
    /**
     * Client is activated
     */
    ACTIVE(2);

    private final Integer code;

    ClientStatus(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    /**
     * Getting enumerate value by code of value
     */
    public static ClientStatus valueOf(Integer code) {
        for (ClientStatus status : ClientStatus.values()) {
            if (status.code.equals(code)) {
                return status;
            }
        }
        throw new UnknownEnumerateValueException(ErrorCode.UNKNOWN_ENUM_VALUE, ClientStatus.class, code);
    }
}
