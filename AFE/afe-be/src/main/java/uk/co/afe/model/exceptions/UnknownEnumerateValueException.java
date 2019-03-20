package uk.co.afe.model.exceptions;

/**
 * @author Sergey Teryoshin
 * 20.03.2018 16:23
 */
public class UnknownEnumerateValueException extends BaseRuntimeException {

    public UnknownEnumerateValueException(String code, Class<?> clazz, Integer valueIntCode) {
        super(code, String.format("Unknown enumerate value '%s' of enumeration '%s'", valueIntCode, clazz.getSimpleName()));
    }
}
