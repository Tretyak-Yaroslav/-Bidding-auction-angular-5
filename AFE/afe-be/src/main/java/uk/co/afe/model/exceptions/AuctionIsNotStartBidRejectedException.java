package uk.co.afe.model.exceptions;

import uk.co.afe.model.ErrorCode;

/**
 * @author Sergey Teryoshin
 * 24.03.2018 13:30
 */
public class AuctionIsNotStartBidRejectedException extends BaseRuntimeException {
    public AuctionIsNotStartBidRejectedException(Long amount) {
        super(ErrorCode.AUCTION_IS_NOT_START, String.format("Auction is not start yet. Bid '%s' will be rejected", amount));
    }
}
