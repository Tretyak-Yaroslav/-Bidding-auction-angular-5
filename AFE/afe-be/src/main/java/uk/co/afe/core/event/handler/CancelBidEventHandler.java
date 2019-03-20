package uk.co.afe.core.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import uk.co.afe.ValueConstants;
import uk.co.afe.core.NotificationBean;
import uk.co.afe.core.ProductBean;
import uk.co.afe.core.TransactionBean;
import uk.co.afe.core.event.CancelBidEvent;
import uk.co.afe.core.event.CloseAcceptedBidEvent;
import uk.co.afe.core.event.CloseBidEvent;
import uk.co.afe.model.product.Product;
import uk.co.afe.model.tx.Transaction;

import java.util.List;

/**
 * @author Sergey Teryoshin
 * 27.03.2018 23:03
 */
@Service
public class CancelBidEventHandler {

    @Autowired
    private NotificationBean notificationBean;
    @Autowired
    private ProductBean productBean;
    @Autowired
    private TransactionBean transactionBean;
    @Autowired
    private MessageSource messageSource;

    @EventListener
    public void onCancelBid(CancelBidEvent event) {
        Transaction bid = event.getSource();
        Product product = productBean.getProductById(bid.getProductId());
        String subject = messageSource.getMessage("client.activated.subject",
                new String[]{"" + bid.getAmount(), "" + product.getProductId(), product.getProductOwnerFullName()},
                ValueConstants.DEFAULT_LOCALE);
        String text = messageSource.getMessage("client.activated.text",
                new String[]{"" + bid.getAmount(), "" + product.getProductId(), product.getProductOwnerFullName()},
                ValueConstants.DEFAULT_LOCALE);
        notificationBean.sendEmail(bid.getClientId(), subject, text);
    }

    @EventListener
    public void onCloseBid(CloseBidEvent event) {
        Transaction bid = event.getSource();
        sendEmailToBidOwner(bid);
    }

    @EventListener
    public void onCloseAcceptedBid(CloseAcceptedBidEvent event) {
        Transaction bid = event.getSource();
        Long productId = bid.getProductId();
        Product product = productBean.getProductById(productId);
        List<Transaction> bids = transactionBean.getBidsByProductId(productId);
        if (bids.isEmpty()) {
            productBean.expireProduct(ValueConstants.TECH_USER, productId);
            String subject = messageSource.getMessage("bid.accepted.cancel.subject",
                    new String[]{"" + bid.getAmount(), "" + product.getProductId(), product.getProductOwnerFullName()},
                    ValueConstants.DEFAULT_LOCALE);
            String text = messageSource.getMessage("bid.accepted.cancel.text",
                    new String[]{"" + bid.getAmount(), "" + product.getProductId(), product.getProductOwnerFullName()},
                    ValueConstants.DEFAULT_LOCALE);
            notificationBean.sendEmail(product.getProductOwnerId(), subject, text);
        } else {
            productBean.saleProduct(ValueConstants.TECH_USER, productId);
            String subject = messageSource.getMessage("bid.accepted.cancel.last.subject",
                    new String[]{"" + bid.getAmount(), "" + product.getProductId(), product.getProductOwnerFullName()},
                    ValueConstants.DEFAULT_LOCALE);
            String text = messageSource.getMessage("bid.accepted.cancel.last.text",
                    new String[]{"" + bid.getAmount(), "" + product.getProductId(), product.getProductOwnerFullName()},
                    ValueConstants.DEFAULT_LOCALE);
            notificationBean.sendEmail(product.getProductOwnerId(), subject, text);
        }
        sendEmailToBidOwner(bid);
    }

    private void sendEmailToBidOwner(Transaction bid) {
        Product product = productBean.getProductById(bid.getProductId());
        String subject = messageSource.getMessage("bid.cancel.subject",
                new String[]{"" + bid.getAmount(), "" + product.getProductId(), product.getProductOwnerFullName()},
                ValueConstants.DEFAULT_LOCALE);
        String text = messageSource.getMessage("bid.cancel.text",
                new String[]{"" + bid.getAmount(), "" + product.getProductId(), product.getProductOwnerFullName()},
                ValueConstants.DEFAULT_LOCALE);
        notificationBean.sendEmail(bid.getClientId(), subject, text);
    }


}
