package uk.co.afe.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import uk.co.afe.core.event.ClientActivatedEvent;
import uk.co.afe.core.event.OnHideClientEvent;
import uk.co.afe.core.event.OnUnHideClientEvent;
import uk.co.afe.model.ErrorCode;
import uk.co.afe.model.client.Client;
import uk.co.afe.model.client.ClientStatus;
import uk.co.afe.model.exceptions.*;
import uk.co.afe.model.questionaries.Questionnaire;
import uk.co.afe.persistance.service.DaoClient;
import uk.co.afe.persistance.service.DaoFileManager;
import uk.co.afe.utils.SecurityUtils;

import java.util.List;

/**
 * @author Sergey Teryoshin
 * 18.03.2018 10:16
 */
@Service
public class ClientBean {

    @Autowired
    private DaoClient daoClient;
    @Autowired
    private DaoFileManager fileManager;
    @Autowired
    private ApplicationEventPublisher publisher;

    public Client save(Client client) {
        client = daoClient.saveAndFlush(client);
        return client;
    }

    public Client updateInfo(Client client) {
        Client current = getClientById(client.getClientId());
        if (client.getName() != null) {
            current.setName(client.getName());
        }
        if (client.getPhoneNumber() != null && !client.getPhoneNumber().equals(current.getPhoneNumber())) {
            if (!isPhoneAvailable(client.getPhoneNumber())) {
                throw new AlreadyExistsException(ErrorCode.CLIENT_PHONE_NUMBER_UNAVAILABLE, Client.class,
                        String.format("with phone number '%s'", client.getPhoneNumber()));
            }
            current.setPhoneNumber(client.getPhoneNumber());
        }
        if (client.getBirthday() != null) {
            current.setBirthday(client.getBirthday());
        }
        if (client.getAtr() != null) {
            current.setAtr(client.getAtr());
        }
        return save(current);
    }

    public Client updateQuestionnaireAnswers(Long clientId, Questionnaire questionnaire) {
        Client client = getClientById(clientId);
        client.setQuestionnaire(questionnaire);
        client.setAtr(null);
        return save(client);
    }

    public Client updatePassword(Long clientId, String password, String newPassword) {
        Client client = getClientById(clientId);
        String salt = client.getSalt();
        String hash = SecurityUtils.getHash(password, salt);
        if (!hash.equals(client.getPassword())) {
            throw new IncorrectCredentialsException();
        }
        salt = SecurityUtils.getSalt();
        newPassword = SecurityUtils.getHash(newPassword, salt);
        client.setSalt(salt);
        client.setPassword(newPassword);
        return save(client);
    }

    public boolean isEmailAvailable(String email) {
        Client c = new Client();
        c.setEmail(email);
        c = getClient(c);
        return c == null;
    }

    public boolean isPhoneAvailable(String phone) {
        Client c = new Client();
        c.setPhoneNumber(phone);
        c = getClient(c);
        return c == null;
    }

    public Client getClient(Client client) {
        List<Client> list = getAllClients(client);
        if (list.size() == 0) {
            return null;
        }
        if (list.size() > 1) {
            throw new TooMatchEntityFoundException(ErrorCode.TOO_MUCH_CLIENTS_FOUND, 1, list.size());
        }
        return list.get(0);
    }

    public Client approveClient(Long clientId) {
        Client client = getClientById(clientId);
        if (client.getStatus() == ClientStatus.CONFIRMATION) {
            throw new ClientHaveToConfirmEmailException();
        }
        client.setStatus(ClientStatus.ACTIVE);
        publisher.publishEvent(new ClientActivatedEvent(client));
        return save(client);
    }

    public Client confirmClient(Long clientId) {
        Client client = getClientById(clientId);
        if (client.getStatus() == ClientStatus.ACTIVE) {
            return client;
        }
        client.setStatus(ClientStatus.PENDING);
        publisher.publishEvent(new ClientActivatedEvent(client));
        return save(client);
    }

    public Client getClientById(Long id) {
        return daoClient.findById(id).orElseThrow(() -> new ObjectNotFoundException(ErrorCode.CLIENT_NOT_FOUND, id, Client.class));
    }

    public List<Client> getAllClients(Client client) {
        return daoClient.findAll(Example.of(client));
    }

    public List<Client> getAllClients() {
        return daoClient.findAll();
    }

    public Client changeClientHideStatus(Long clientId, Boolean isHidden) {
        Client client = getClientById(clientId);
        client.setHidden(isHidden);
        Client save = save(client);
        if (isHidden) {
            publisher.publishEvent(new OnHideClientEvent(save));
        } else {
            publisher.publishEvent(new OnUnHideClientEvent(save));
        }
        return save;
    }

    public Client setAvatar(Long clientId, byte[] body) {
        Client client = getClientById(clientId);
        String oldAvatar = client.getAvatar();
        String path = fileManager.save(body);
        client.setAvatar(path);
        client = save(client);
        if (oldAvatar != null) {
            fileManager.remove(oldAvatar);
        }
        return client;
    }

    public byte[] getAvatar(Long clientId) {
        Client client = getClientById(clientId);
        String path = client.getAvatar();
        byte[] bytes = null;
        if (path != null) {
            bytes = fileManager.get(path);
        }

        if (bytes == null) {
            throw new ObjectNotFoundException(ErrorCode.CLIENT_AVATAR_NOT_FOUND, clientId, "client avatar");
        }
        return bytes;
    }
}
