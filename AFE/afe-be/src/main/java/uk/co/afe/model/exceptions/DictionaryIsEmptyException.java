package uk.co.afe.model.exceptions;

/**
 * @author Sergey Teryoshin
 * 21.03.2018 18:41
 */
public class DictionaryIsEmptyException extends BaseRuntimeException {
    public DictionaryIsEmptyException(String code, String name) {
        super(code, String.format("Dictionary of '%s' is empty", name));
    }
}
