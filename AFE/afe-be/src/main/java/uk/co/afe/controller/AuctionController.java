package uk.co.afe.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uk.co.afe.core.TransactionBean;
import uk.co.afe.model.request.auction.WarBuyoutRequest;
import uk.co.afe.model.request.auction.WarMakeBidRequest;
import uk.co.afe.model.response.auction.WarGetAllBidsResponse;
import uk.co.afe.model.tx.Transaction;
import uk.co.afe.utils.SessionUtils;

import javax.validation.Valid;
import java.util.List;

/**
 * Services of auction
 *
 * @author Sergey Teryoshin
 * 24.03.2018 13:21
 */
@RestController
@RequestMapping("api/v1/auction")
@Api(description = "Services of auction",
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuctionController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationController.class);

    @Autowired
    private TransactionBean transactionBean;

    /**
     * Get all bids of lot by product identifier
     * <p>Required permissions: isAuthenticated()</p>
     *
     * @param productId Identifier of product
     */
    @ApiOperation(value = "Get all bids of lot by product identifier", notes = "<p>Required permissions: isAuthenticated()</p>")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("bid/all/{id}")
    public WarGetAllBidsResponse getBids(@PathVariable("id") Long productId) {
        LOG.info("-> api/v1/auction/bid/all/{id}. " + productId);
        List<Transaction> bids = transactionBean.getBidsByProductId(productId);
        WarGetAllBidsResponse response = new WarGetAllBidsResponse(bids);
        LOG.info("<- api/v1/auction/bid/all/{id}. " + response);
        return response;
    }

    /**
     * Make a bid to lot in auction
     * <p>Required permissions: hasRole('AUCTION_MAKE_BID')</p>
     *
     * @param request Info of bid
     */
    @ApiOperation(value = "Make a bid to lot in auction", notes = "<p>Required permissions: hasRole('AUCTION_MAKE_BID')</p>")
    @PreAuthorize("hasRole('AUCTION_MAKE_BID')")
    @PutMapping("bid/make")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void makeBid(@Valid @RequestBody WarMakeBidRequest request) {
        LOG.info("-> api/v1/auction/bid/make. " + request);
        Long clientId = SessionUtils.getClientId();
        Transaction bid = request.toBid();
        bid.setClientId(clientId);
        transactionBean.makeBid(bid);
        LOG.info("<- api/v1/auction/bid/make");
    }

    /**
     * Buyout product by identifier
     * <p>Required permissions: hasRole('AUCTION_BUYOUT')</p>
     *
     * @param request Product identifier
     */
    @ApiOperation(value = "Buyout product by identifier", notes = "<p>Required permissions: hasRole('AUCTION_BUYOUT')</p>")
    @PreAuthorize("hasRole('AUCTION_BUYOUT')")
    @PostMapping("buyout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void buyout(@Valid @RequestBody WarBuyoutRequest request) {
        LOG.info("-> api/v1/auction/buyout. " + request);
        Long clientId = SessionUtils.getClientId();
        Transaction bid = request.toBid();
        bid.setClientId(clientId);
        transactionBean.buyout(bid);
        LOG.info("<- api/v1/auction/buyout");
    }

    /**
     * Withdraw bid from auction
     * <p>Required permissions: hasRole('AUCTION_BID_WITHDRAW')</p>
     *
     * @param txId Identifier of bid
     */
    @ApiOperation(value = "Withdraw bid from auction", notes = "<p>Required permissions: hasRole('AUCTION_BID_WITHDRAW')</p>")
    @PreAuthorize("hasRole('AUCTION_BID_WITHDRAW')")
    @PostMapping("bid/withdraw/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void withdrawBid(@PathVariable("id") Long txId) {
        LOG.info("-> api/v1/auction/bid/withdraw/{id}. " + txId);
        Long clientId = SessionUtils.getClientId();
        transactionBean.withdrawBid(clientId, txId);
        LOG.info("<- api/v1/auction/bid/withdraw/{id}");
    }

}
