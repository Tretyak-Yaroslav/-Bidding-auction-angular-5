package uk.co.afe.core.event;

import uk.co.afe.model.tx.Transaction;

/**
 * @author Sergey Teryoshin
 * 27.03.2018 23:04
 */
public final class CancelBidEvent extends BaseEvent<Transaction> {
    public CancelBidEvent(Transaction source) {
        super(source);
    }
}
