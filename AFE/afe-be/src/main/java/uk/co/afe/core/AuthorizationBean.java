package uk.co.afe.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import uk.co.afe.ValueConstants;
import uk.co.afe.core.checkers.AuthChecker;
import uk.co.afe.core.event.ClientCreatedEvent;
import uk.co.afe.core.event.EmailConfirmationEvent;
import uk.co.afe.model.EmailToken;
import uk.co.afe.model.ErrorCode;
import uk.co.afe.model.client.Client;
import uk.co.afe.model.client.ClientStatus;
import uk.co.afe.model.exceptions.AlreadyExistsException;
import uk.co.afe.model.exceptions.UnknownConfirmationTokenException;
import uk.co.afe.persistance.service.DaoEmailToken;
import uk.co.afe.utils.SecurityUtils;

import java.util.List;

/**
 * @author Sergey Teryoshin
 * 13.03.2018 11:54
 */
@Service
public class AuthorizationBean {

    @Autowired
    private ClientBean clientBean;
    @Autowired
    private PermissionBean permissionBean;
    @Autowired
    private List<AuthChecker> checkers;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private DaoEmailToken daoEmailToken;
    @Autowired
    private NotificationBean notificationBean;
    @Autowired
    private MessageSource messageSource;

    @Value("${front-end.url.root}")
    private String url;

    public Client encryptPassAndSave(Client client) {
        String salt = SecurityUtils.getSalt();
        String password = SecurityUtils.getHash(client.getPassword(), salt);
        client.setPassword(password);
        client.setSalt(salt);
        client.setHidden(false);
        client.setStatus(ClientStatus.CONFIRMATION);
        if (!clientBean.isEmailAvailable(client.getEmail())) {
            throw new AlreadyExistsException(ErrorCode.CLIENT_EMAIL_UNAVAILABLE, Client.class,
                    String.format("with email '%s'", client.getEmail()));
        }
        client = clientBean.save(client);
        publisher.publishEvent(new ClientCreatedEvent(client));
        EmailToken emailToken = new EmailToken();
        emailToken.setClientId(client.getClientId());
        emailToken.setToken(SecurityUtils.getSalt());
        daoEmailToken.saveAndFlush(emailToken);
        String subject = messageSource.getMessage("client.email-confirm.subject",
                new String[]{},
                ValueConstants.DEFAULT_LOCALE);
        String text = messageSource.getMessage("client.email-confirm.text",
                new String[]{url, "" + client.getClientId(), client.getName(), emailToken.getToken()},
                ValueConstants.DEFAULT_LOCALE);
        notificationBean.sendEmail(client.getEmail(), subject, text);
        return client;
    }

    public Client checkCredentialsAndGet(String email, String password) {
        Client client = new Client();
        client.setEmail(email);
        client = clientBean.getClient(client);
        for (AuthChecker checker : checkers) {
            checker.check(client, email, password);
        }
        return client;
    }

    public void confirmEmail(String token) {
        EmailToken filter = new EmailToken();
        filter.setToken(token);
        List<EmailToken> all = daoEmailToken.findAll(Example.of(filter));
        if (all.isEmpty()) {
            throw new UnknownConfirmationTokenException();
        }
        EmailToken emailToken = all.get(0);
        clientBean.confirmClient(emailToken.getClientId());
        Client client = clientBean.getClientById(emailToken.getClientId());
        publisher.publishEvent(new EmailConfirmationEvent(client));
    }
}
