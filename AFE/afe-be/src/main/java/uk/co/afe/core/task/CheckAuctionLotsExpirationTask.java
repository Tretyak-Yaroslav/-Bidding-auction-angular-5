package uk.co.afe.core.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import uk.co.afe.ValueConstants;
import uk.co.afe.core.NotificationBean;
import uk.co.afe.core.ProductBean;
import uk.co.afe.core.TransactionBean;
import uk.co.afe.model.product.Product;
import uk.co.afe.model.tx.Transaction;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Sergey Teryoshin
 * 27.03.2018 21:55
 */
@Service
public class CheckAuctionLotsExpirationTask {

    @Autowired
    private ProductBean productBean;
    @Autowired
    private TransactionBean transactionBean;

    @Scheduled(cron = "*/15 * * * * *")
    public void checkLots() {
        List<Product> products = productBean.getAllActiveLots();
        LocalDateTime now = LocalDateTime.now();
        for (Product product : products) {
            LocalDateTime time = product.getEndDate();
            if (now.isAfter(time) || now.equals(time)) {
                decide(product);
            }
        }
    }

    private void decide(Product product) {
        Transaction bid = transactionBean.getHighestBid(product.getProductId());
        if (bid == null) {
            productBean.expireProduct(ValueConstants.TECH_USER, product.getProductId());
        } else {
            transactionBean.acceptBid(ValueConstants.TECH_USER, bid.getTxId());
        }
    }

}
