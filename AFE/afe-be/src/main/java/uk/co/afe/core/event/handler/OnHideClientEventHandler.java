package uk.co.afe.core.event.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import uk.co.afe.ValueConstants;
import uk.co.afe.core.NotificationBean;
import uk.co.afe.core.ProductBean;
import uk.co.afe.core.TransactionBean;
import uk.co.afe.core.event.OnHideClientEvent;
import uk.co.afe.model.client.Client;
import uk.co.afe.model.product.Product;
import uk.co.afe.model.product.ProductStatus;
import uk.co.afe.model.tx.Transaction;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author Sergey Teryoshin
 * 28.03.2018 12:20
 */
@Service
public class OnHideClientEventHandler {

    private static final Logger LOG = LoggerFactory.getLogger(OnHideClientEventHandler.class);

    @Autowired
    private TransactionBean transactionBean;
    @Autowired
    private ProductBean productBean;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private NotificationBean notificationBean;

    @EventListener
    public void onHide(OnHideClientEvent event) {
        Client client = event.getSource();
        List<Transaction> bids = transactionBean.getBidsByClient(client.getClientId());
        for (Transaction bid : bids) {
            try {
                transactionBean.closeBid(ValueConstants.TECH_USER, bid);
            } catch (Exception e) {
                LOG.info(String.format("Error while close bid %s", bid.getTxId()), e);
            }
        }
        List<Product> products = productBean.getProductsByStatus(client.getClientId(),
                Arrays.asList(ProductStatus.FOR_SALE,ProductStatus.SALE_PENDING));
        for (Product product : products) {
            productBean.expireProduct(ValueConstants.TECH_USER, product.getProductId());
        }
        products = productBean.getProductsByStatus(client.getClientId(),
                Collections.singletonList(ProductStatus.PENDING));
        for (Product product : products) {
            productBean.rejectProduct(ValueConstants.TECH_USER, product.getProductId());
        }
    }

    @EventListener
    public void onHideClientSendNotification(OnHideClientEvent event) {
        Client client = event.getSource();
        String subject = messageSource.getMessage("client.hide.subject",
                new String[]{},
                ValueConstants.DEFAULT_LOCALE);
        String text = messageSource.getMessage("client.hide.text",
                new String[]{},
                ValueConstants.DEFAULT_LOCALE);
        notificationBean.sendEmail(client.getClientId(), subject, text);
    }

}
