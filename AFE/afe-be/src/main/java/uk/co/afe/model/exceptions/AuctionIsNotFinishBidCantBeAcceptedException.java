package uk.co.afe.model.exceptions;

import uk.co.afe.model.ErrorCode;

/**
 * @author Sergey Teryoshin
 * 24.03.2018 13:30
 */
public class AuctionIsNotFinishBidCantBeAcceptedException extends BaseRuntimeException {
    public AuctionIsNotFinishBidCantBeAcceptedException(Long txId) {
        super(ErrorCode.AUCTION_IS_FINISHED, String.format("Auction is not finished yet. Bid '%s' cannot be accepter", txId));
    }
}
