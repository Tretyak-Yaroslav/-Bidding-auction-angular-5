package uk.co.afe.core.event;

import uk.co.afe.model.client.Client;

/**
 * @author Sergey Teryoshin
 * 28.03.2018 22:22
 */
public final class ClientActivatedEvent extends BaseEvent<Client> {
    public ClientActivatedEvent(Client source) {
        super(source);
    }
}
