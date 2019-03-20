package uk.co.afe.model.exceptions;

/**
 * @author Sergey Teryoshin
 * 22.03.2018 21:43
 */
public class AlreadyExistsException extends BaseRuntimeException  {
    public AlreadyExistsException(String code, Class<?> clazz, String existsField) {
        super(code, String.format("%s with %s already exists", clazz.getSimpleName(), existsField));
    }
}
