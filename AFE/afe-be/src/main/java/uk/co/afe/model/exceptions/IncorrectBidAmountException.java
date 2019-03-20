package uk.co.afe.model.exceptions;

/**
 * @author Sergey Teryoshin
 * 31.03.2018 23:47
 */
public class IncorrectBidAmountException extends BaseRuntimeException {
    public IncorrectBidAmountException(String code, String message) {
        super(code, message);
    }
}
