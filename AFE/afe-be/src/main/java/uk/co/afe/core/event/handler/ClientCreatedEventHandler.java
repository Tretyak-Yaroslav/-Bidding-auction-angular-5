package uk.co.afe.core.event.handler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import uk.co.afe.ValueConstants;
import uk.co.afe.core.NotificationBean;
import uk.co.afe.core.PermissionBean;
import uk.co.afe.core.event.ClientCreatedEvent;
import uk.co.afe.model.client.Client;
import uk.co.afe.model.role.Permission;
import uk.co.afe.model.role.Roles;
import uk.co.afe.persistance.service.DaoEmailToken;

/**
 * @author Sergey Teryoshin
 * 27.03.2018 15:20
 */
@Service
public class ClientCreatedEventHandler {

    @Autowired
    private PermissionBean permissionBean;
    @Autowired
    private NotificationBean notificationBean;
    @Autowired
    private MessageSource messageSource;

    @EventListener
    public void grantPermissions(ClientCreatedEvent event) {
        Client client = event.getSource();
        permissionBean.savePermission(new Permission(client.getClientId(), Roles.CLIENT));
    }

    @EventListener
    public void sendNotificationToBackOffice(ClientCreatedEvent event) {
        Client client = event.getSource();
        String subject = messageSource.getMessage("client.created.subject",
                new String[]{"" + client.getClientId(), client.getName()},
                ValueConstants.DEFAULT_LOCALE);
        String text = messageSource.getMessage("client.created.text",
                new String[]{"" + client.getClientId(), client.getName()},
                ValueConstants.DEFAULT_LOCALE);
        notificationBean.sendEmailToBackOffice(subject, text);
    }

}
