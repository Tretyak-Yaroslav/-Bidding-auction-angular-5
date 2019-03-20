package uk.co.afe.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Example;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import uk.co.afe.core.event.*;
import uk.co.afe.model.ErrorCode;
import uk.co.afe.model.exceptions.*;
import uk.co.afe.model.product.Product;
import uk.co.afe.model.product.ProductStatus;
import uk.co.afe.model.tx.Transaction;
import uk.co.afe.model.tx.TransactionStatus;
import uk.co.afe.model.tx.TransactionType;
import uk.co.afe.persistance.service.DaoTransaction;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergey Teryoshin
 * 24.03.2018 12:18
 */
@Service
public class TransactionBean {

    @Autowired
    private DaoTransaction daoTransaction;
    @Autowired
    private ProductBean productBean;
    @Autowired
    private PermissionBean permissionBean;
    @Autowired
    private ApplicationEventPublisher publisher;

    public List<Transaction> getBidsByClient(Long clientId) {
        Transaction filter = new Transaction();
        filter.setClientId(clientId);
        return getBidsByFilter(filter);
    }

    public List<Transaction> getBidsByProductId(Long productId) {
        Transaction filter = new Transaction();
        filter.setProductId(productId);
        return getBidsByFilter(filter);
    }

    private List<Transaction> getBidsByFilter(Transaction filter) {
        filter.setType(TransactionType.BID);
        List<Transaction> list = daoTransaction.findAll(Example.of(filter));
        return list.stream()
                .filter(t -> t.getStatus() != TransactionStatus.WITHDRAWAL)
                .filter(t -> t.getStatus() != TransactionStatus.CLOSED)
                .collect(Collectors.toList());
    }

    private Transaction getOne(Long txId) {
        Transaction filter = new Transaction();
        filter.setTxId(txId);
        filter.setType(TransactionType.BID);
        return daoTransaction.findOne(Example.of(filter)).orElseThrow(() ->
                new ObjectNotFoundException(ErrorCode.BID_NOT_FOUND, txId, Transaction.class)
        );
    }

    public Transaction makeBid(Transaction bid) {
        Product product = getProduct(bid);
        if (!isAuctionStart(product)) {
            throw new AuctionIsNotStartBidRejectedException(bid.getAmount());
        }
        if (isAuctionEnd(product)) {
            throw new AuctionIsFinishedBidRejectedException(bid.getAmount());
        }
        if (bid.getAmount() < product.getStartingBid()) {
            throw new IncorrectBidAmountException(ErrorCode.BID_LESS_THEN_STATING, "Bid amount can't be less then starting bid of product");
        }
        bid.setStatus(TransactionStatus.SUBMITTED);
        return makeBidInternal(product, bid);
    }

    public Transaction buyout(Transaction bid) {
        Product product = getProduct(bid);
        bid.setAmount(product.getBuyOutPrice());
        bid.setStatus(TransactionStatus.ACCEPTED);
        bid = makeBidInternal(product, bid);
        publisher.publishEvent(new BuyoutEvent(bid));
        return bid;
    }

    private Transaction makeBidInternal(Product product, Transaction bid) {
        if (product.getProductStatus() != ProductStatus.FOR_SALE) {
            throw new AuctionIsFinishedBidRejectedException(bid.getAmount());
        }
        if (product.getPrice() >= bid.getAmount()) {
            throw new IncorrectBidAmountException(ErrorCode.BID_LESS_THEN_LAST_BID, "Bid amount less then last bid of product");
        }
        bid = daoTransaction.saveAndFlush(bid);
        product.setPrice(bid.getAmount());
        productBean.updateProductPrice(product.getProductId(), bid.getAmount());
        return bid;
    }

    public Transaction completeLot(Long clientId, Long productId) {
        if (!permissionBean.isTechUser(clientId)) {
            throw new AccessDeniedException("Access denied!");
        }
        Transaction filter = new Transaction();
        filter.setProductId(productId);
        filter.setType(TransactionType.BID);
        filter.setStatus(TransactionStatus.ACCEPTED);
        Transaction bid = daoTransaction.findOne(Example.of(filter)).orElseThrow(() ->
                new ObjectNotFoundException(ErrorCode.BID_NOT_FOUND, productId, TransactionStatus.ACCEPTED)
        );
        if (bid.getStatus() != TransactionStatus.ACCEPTED) {
            throw new ChangeTransactionStatusException(bid.getTxId(), bid.getStatus(), TransactionStatus.ACCEPTED);
        }
        bid.setStatus(TransactionStatus.COMPLETED);
        daoTransaction.saveAndFlush(bid);
        return bid;
    }

    public Transaction acceptBid(Long clientId, Long txId) {
        if (!permissionBean.isTechUser(clientId)) {
            throw new AccessDeniedException("Access denied!");
        }
        Transaction bid = getOne(txId);
        if (bid.getStatus() != TransactionStatus.SUBMITTED) {
            throw new ChangeTransactionStatusException(txId, bid.getStatus(), TransactionStatus.ACCEPTED);
        }
        Product product = getProduct(bid);
        if (product.getEndDate().isAfter(LocalDateTime.now())) {
            throw new AuctionIsNotFinishBidCantBeAcceptedException(txId);
        }
        bid.setStatus(TransactionStatus.ACCEPTED);
        bid = daoTransaction.saveAndFlush(bid);
        publisher.publishEvent(new BidAcceptedEvent(bid));
        return bid;
    }

    public void closeBid(Long clientId, Transaction bid) {
        if (!permissionBean.isTechUser(clientId)) {
            throw new AccessDeniedException("Access denied!");
        }
        boolean accepted = bid.getStatus() == TransactionStatus.ACCEPTED;
        bid.setStatus(TransactionStatus.CLOSED);
        if (accepted) {
            publisher.publishEvent(new CloseBidEvent(bid));
        } else {
            publisher.publishEvent(new CloseAcceptedBidEvent(bid));
        }
    }

    public void withdrawBid(Long clientId, Long txId) {
        Transaction bid = getOne(txId);
        if (!bid.getClientId().equals(clientId)) {
            throw new AccessDeniedException("Access denied!");
        }
        if (bid.getStatus() == TransactionStatus.ACCEPTED) {
            throw new ChangeTransactionStatusException(txId, TransactionStatus.ACCEPTED, TransactionStatus.WITHDRAWAL);
        }
        Product product = productBean.getProductById(bid.getProductId());
        if (product.getProductStatus() != ProductStatus.FOR_SALE &&
                product.getProductStatus() != ProductStatus.SALE_PENDING) {
            throw new BidWithdrawException(txId);
        }
        bid.setStatus(TransactionStatus.WITHDRAWAL);
        daoTransaction.saveAndFlush(bid);
        publisher.publishEvent(new CancelBidEvent(bid));
    }

    private Boolean isAuctionEnd(Product product) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(product.getEndDate()) || now.isEqual(product.getEndDate())
                || product.getProductStatus() != ProductStatus.FOR_SALE;
    }

    private Boolean isAuctionStart(Product product) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(product.getStartDate()) && now.isBefore(product.getEndDate())
                && product.getProductStatus() == ProductStatus.FOR_SALE;

    }

    private Product getProduct(Transaction bid) {
        Long productId = bid.getProductId();
        return productBean.getProductById(productId);
    }

    public Transaction getHighestBid(Long productId) {
        return daoTransaction.findTop1ByProductIdAndStatusOrderByAmountDesc(productId, TransactionStatus.SUBMITTED);
    }
}
