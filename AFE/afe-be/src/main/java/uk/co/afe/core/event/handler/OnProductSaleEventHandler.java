package uk.co.afe.core.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import uk.co.afe.ValueConstants;
import uk.co.afe.core.NotificationBean;
import uk.co.afe.core.TransactionBean;
import uk.co.afe.core.event.OnProductSaleEvent;
import uk.co.afe.model.product.Product;

/**
 * @author Sergey Teryoshin
 * 28.03.2018 12:37
 */
@Service
public class OnProductSaleEventHandler {

    @Autowired
    private TransactionBean transactionBean;
    @Autowired
    private NotificationBean notificationBean;
    @Autowired
    private MessageSource messageSource;

    @EventListener
    public void onProductSale(OnProductSaleEvent event) {
        Product product = event.getSource();
        transactionBean.completeLot(ValueConstants.TECH_USER, product.getProductId());
        String subject = messageSource.getMessage("product.auction.sold.subject",
                new String[]{"" + product.getProductId(), product.getProductOwnerFullName()},
                ValueConstants.DEFAULT_LOCALE);
        String text = messageSource.getMessage("product.auction.sold.text",
                new String[]{"" + product.getProductId(), product.getProductOwnerFullName()},
                ValueConstants.DEFAULT_LOCALE);
        notificationBean.sendEmail(product.getProductOwnerId(), subject, text);
    }

}
