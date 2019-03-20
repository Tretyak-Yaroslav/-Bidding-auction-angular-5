package uk.co.afe.model.exceptions;

/**
 * @author Sergey Teryoshin
 * 13.03.2018 17:16
 */
public class BaseException extends Exception {

    private String code;

    public BaseException(String code) {
        this.code = code;
    }

    public BaseException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BaseException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BaseException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

}
