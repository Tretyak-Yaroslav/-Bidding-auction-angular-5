package uk.co.afe.core.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import uk.co.afe.ValueConstants;
import uk.co.afe.core.NotificationBean;
import uk.co.afe.core.event.OnUnHideClientEvent;
import uk.co.afe.model.client.Client;

/**
 * @author Sergey Teryoshin
 * 28.03.2018 12:20
 */
@Service
public class OnUnHideClientEventHandler {

    @Autowired
    private NotificationBean notificationBean;
    @Autowired
    private MessageSource messageSource;

    @EventListener
    public void onUnHide(OnUnHideClientEvent event) {
        Client client = event.getSource();
        String subject = messageSource.getMessage("client.unhide.subject",
                new String[]{},
                ValueConstants.DEFAULT_LOCALE);
        String text = messageSource.getMessage("client.unhide.text",
                new String[]{},
                ValueConstants.DEFAULT_LOCALE);
        notificationBean.sendEmail(client.getClientId(), subject, text);
    }

}
