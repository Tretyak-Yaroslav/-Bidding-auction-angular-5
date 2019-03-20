package uk.co.afe.core.checkers;

import org.springframework.stereotype.Service;
import uk.co.afe.model.client.Client;
import uk.co.afe.model.client.ClientStatus;
import uk.co.afe.model.exceptions.ClientHaveToConfirmEmailException;

/**
 * @author Sergey Teryoshin
 * 28.03.2018 22:18
 */
@Service
public class ClientEmailVerificationChecker implements AuthChecker {
    public void check(Client client, String email, String password) {
        if (client.getStatus() == ClientStatus.CONFIRMATION) {
            throw new ClientHaveToConfirmEmailException();
        }
    }
}
