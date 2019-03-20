package uk.co.afe.core.checkers;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import uk.co.afe.model.client.Client;
import uk.co.afe.model.exceptions.ClientIsHiddenException;

/**
 * Checker of client hidden status
 *
 * @author Sergey Teryoshin
 * 23.03.2018 11:29
 */
@Service
@Order
public class ClientHiddenStatusChecker implements AuthChecker {
    public void check(Client client, String email, String password) {
        if (client.getHidden()) {
            throw new ClientIsHiddenException(email);
        }
    }
}
