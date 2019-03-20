package uk.co.afe.core.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import uk.co.afe.ValueConstants;
import uk.co.afe.core.NotificationBean;
import uk.co.afe.core.ProductBean;
import uk.co.afe.core.event.BidAcceptedEvent;
import uk.co.afe.core.event.BuyoutEvent;
import uk.co.afe.model.product.Product;
import uk.co.afe.model.tx.Transaction;

/**
 * @author Sergey Teryoshin
 * 27.03.2018 17:51
 */
@Service
public class BidAcceptedEventHandler {

    @Autowired
    private NotificationBean notificationBean;
    @Autowired
    private ProductBean productBean;
    @Autowired
    private MessageSource messageSource;

    @EventListener
    public void doBuyout(BuyoutEvent event) {
        doJob(event.getSource());
    }

    @EventListener
    public void bidSelected(BidAcceptedEvent event) {
        doJob(event.getSource());
    }

    private void doJob(Transaction bid) {
        Long productId = bid.getProductId();
        Product product = productBean.getProductById(productId);
        productBean.salePendingProduct(product.getProductOwnerId(), productId);
        sendNotifToBidOwner(bid, product);
        sendNotifToProductOwner(bid, product);
    }

    private void sendNotifToBidOwner(Transaction bid, Product product) {
        String subject = messageSource.getMessage("bid.accepted.subject",
                new String[]{"" + bid.getAmount(), "" + product.getProductId(), product.getName()},
                ValueConstants.DEFAULT_LOCALE);
        String text = messageSource.getMessage("bid.accepted.text",
                new String[]{"" + bid.getAmount(), "" + product.getProductId(), product.getName()},
                ValueConstants.DEFAULT_LOCALE);
        notificationBean.sendEmail(product.getProductOwnerId(), subject, text);
    }

    private void sendNotifToProductOwner(Transaction bid, Product product) {
        String subject = messageSource.getMessage("product.auction.finished.subject",
                new String[]{"" + product.getProductId(), product.getName()},
                ValueConstants.DEFAULT_LOCALE);
        String text = messageSource.getMessage("product.auction.finished.text",
                new String[]{"" + product.getProductId(), product.getName(), "" + bid.getAmount()},
                ValueConstants.DEFAULT_LOCALE);
        notificationBean.sendEmail(product.getProductOwnerId(), subject, text);
    }

}
