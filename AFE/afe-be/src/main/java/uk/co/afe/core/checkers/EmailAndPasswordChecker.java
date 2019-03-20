package uk.co.afe.core.checkers;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import uk.co.afe.model.client.Client;
import uk.co.afe.model.exceptions.IncorrectCredentialsException;
import uk.co.afe.utils.SecurityUtils;

/**
 * Checker of credentials
 *
 * @author Sergey Teryoshin
 * 23.03.2018 11:29
 */
@Service
@Order(Ordered.HIGHEST_PRECEDENCE)
public class EmailAndPasswordChecker implements AuthChecker {
    public void check(Client client, String email, String password) {
        if (client == null) {
            throw new IncorrectCredentialsException();
        }
        password = SecurityUtils.getHash(password, client.getSalt());
        if (!password.equals(client.getPassword())) {
            throw new IncorrectCredentialsException();
        }
    }
}
