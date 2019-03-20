package uk.co.afe.core.event;

import uk.co.afe.model.tx.Transaction;

/**
 * @author Sergey Teryoshin
 * 27.03.2018 23:02
 */
public final class CloseAcceptedBidEvent extends BaseEvent<Transaction> {
    public CloseAcceptedBidEvent(Transaction source) {
        super(source);
    }
}
