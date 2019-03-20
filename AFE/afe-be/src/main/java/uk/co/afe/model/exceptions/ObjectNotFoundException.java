package uk.co.afe.model.exceptions;

import uk.co.afe.model.tx.TransactionStatus;

/**
 * @author Sergey Teryoshin
 * 20.03.2018 21:46
 */
public class ObjectNotFoundException extends BaseRuntimeException {
    public ObjectNotFoundException(String code, Long id, Class<?> clazz) {
        this(code, id, clazz.getSimpleName());
    }

    public ObjectNotFoundException(String code, Long id, String object) {
        super(code, String.format("Instance of '%s' with identifier '%s' not found", object, id));
    }

    public ObjectNotFoundException(String code, Long productId, TransactionStatus status) {
        super(code, String.format("Bid with status '%s' of lot '%s' not found", status, productId));
    }
}
