package uk.co.afe.model.exceptions;

import uk.co.afe.model.ErrorCode;

/**
 * @author Sergey Teryoshin
 * 24.03.2018 13:30
 */
public class AuctionIsFinishedBidRejectedException extends BaseRuntimeException {
    public AuctionIsFinishedBidRejectedException(Long amount) {
        super(ErrorCode.AUCTION_IS_FINISHED, String.format("Auction is finished already. Bid '%s' will be rejected", amount));
    }
}
