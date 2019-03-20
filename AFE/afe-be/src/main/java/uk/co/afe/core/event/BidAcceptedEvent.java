package uk.co.afe.core.event;

import uk.co.afe.model.tx.Transaction;

/**
 * @author Sergey Teryoshin
 * 27.03.2018 22:51
 */
public final class BidAcceptedEvent extends BaseEvent<Transaction> {
    public BidAcceptedEvent(Transaction source) {
        super(source);
    }
}
