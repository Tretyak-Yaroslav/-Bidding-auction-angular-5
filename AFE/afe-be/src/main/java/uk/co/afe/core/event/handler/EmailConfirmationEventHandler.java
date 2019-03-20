package uk.co.afe.core.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import uk.co.afe.ValueConstants;
import uk.co.afe.core.NotificationBean;
import uk.co.afe.core.event.EmailConfirmationEvent;
import uk.co.afe.model.client.Client;

/**
 * @author Sergey Teryoshin
 * 31.03.2018 17:38
 */
@Service
public class EmailConfirmationEventHandler {

    @Autowired
    private NotificationBean notificationBean;
    @Autowired
    private MessageSource messageSource;

    @EventListener
    public void onEmailConfirmation(EmailConfirmationEvent event) {
        Client client = event.getSource();
        String subject = messageSource.getMessage("client.email-confirm.success.subject",
                new String[]{"" + client.getClientId(), client.getName()},
                ValueConstants.DEFAULT_LOCALE);
        String text = messageSource.getMessage("client.email-confirm.success.text",
                new String[]{"" + client.getClientId(), client.getName()},
                ValueConstants.DEFAULT_LOCALE);
        notificationBean.sendEmailToBackOffice(subject, text);
    }
}
