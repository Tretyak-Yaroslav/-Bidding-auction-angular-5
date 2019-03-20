package uk.co.afe.model.exceptions;

import uk.co.afe.model.ErrorCode;

/**
 * @author Sergey Teryoshin
 * 21.03.2018 18:44
 */
public class ObjectConvertationException extends BaseRuntimeException {

    public ObjectConvertationException(String object, Throwable thr) {
        super(ErrorCode.CONVERT_DATA, String.format("Error while convert '%s'", object), thr);
    }
}
