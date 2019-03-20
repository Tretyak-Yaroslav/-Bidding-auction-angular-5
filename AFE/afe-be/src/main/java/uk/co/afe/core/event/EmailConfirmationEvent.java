package uk.co.afe.core.event;

import uk.co.afe.model.client.Client;

/**
 * @author Sergey Teryoshin
 * 31.03.2018 17:37
 */
public final class EmailConfirmationEvent extends BaseEvent<Client> {
    public EmailConfirmationEvent(Client source) {
        super(source);
    }
}
