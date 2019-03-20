package uk.co.afe.model.exceptions;

/**
 * @author Sergey Teryoshin
 * 16.03.2018 12:27
 */
public class TooMatchEntityFoundException extends BaseRuntimeException {

    public TooMatchEntityFoundException(String code, Integer expected, Integer actual) {
        super(code, String.format("Too much entity was found. Expected %d but actual %d", expected, actual));
    }

}
