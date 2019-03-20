package uk.co.afe.core.checkers;

import uk.co.afe.model.client.Client;

/**
 * Checker of client info to approve client login
 *
 * @author Sergey Teryoshin
 * 23.03.2018 11:27
 */
public interface AuthChecker {

    /**
     * Check client info and credentials
     * <p> Throws an exception if client don't pass verification
     */
    void check(Client client, String email, String password);
}
