package uk.co.afe.core.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import uk.co.afe.ValueConstants;
import uk.co.afe.core.NotificationBean;
import uk.co.afe.core.event.ExpireProductEvent;
import uk.co.afe.model.product.Product;

/**
 * @author Sergey Teryoshin
 * 29.03.2018 23:51
 */
@Service
public class OnExpireProductEventHandler {

    @Autowired
    private NotificationBean notificationBean;
    @Autowired
    private MessageSource messageSource;

    @EventListener
    public void onExpireAuction(ExpireProductEvent event) {
        Product product = event.getSource();
        String subject = messageSource.getMessage("product.auction.expire.subject",
                new String[]{"" + product.getProductId(), product.getProductOwnerFullName()},
                ValueConstants.DEFAULT_LOCALE);
        String text = messageSource.getMessage("product.auction.expire.text",
                new String[]{"" + product.getProductId(), product.getProductOwnerFullName()},
                ValueConstants.DEFAULT_LOCALE);
        notificationBean.sendEmail(product.getProductOwnerId(), subject, text);
    }
}
