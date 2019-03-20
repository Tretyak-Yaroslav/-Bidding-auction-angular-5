package uk.co.afe.core.event;

import uk.co.afe.model.tx.Transaction;

/**
 * @author Sergey Teryoshin
 * 27.03.2018 17:49
 */
public final class BuyoutEvent extends BaseEvent<Transaction> {
    public BuyoutEvent(Transaction source) {
        super(source);
    }
}
