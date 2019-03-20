package uk.co.afe.core.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import uk.co.afe.ValueConstants;
import uk.co.afe.core.NotificationBean;
import uk.co.afe.core.event.ProductCreatedEvent;
import uk.co.afe.model.product.Product;

/**
 * @author Sergey Teryoshin
 * 27.03.2018 22:40
 */
@Service
public class ProductCreatedEventHandler {

    @Autowired
    private NotificationBean notificationBean;
    @Autowired
    private MessageSource messageSource;

    @EventListener
    public void onEvent(ProductCreatedEvent event) {
        Product product = event.getSource();
        String subject = messageSource.getMessage("product.created.subject",
                new String[]{"" + product.getProductId(), product.getName()},
                ValueConstants.DEFAULT_LOCALE);
        String text = messageSource.getMessage("product.created.text",
                new String[]{"" + product.getProductId(), product.getName()},
                ValueConstants.DEFAULT_LOCALE);
        notificationBean.sendEmail(product.getProductOwnerId(), subject, text);
    }

}
