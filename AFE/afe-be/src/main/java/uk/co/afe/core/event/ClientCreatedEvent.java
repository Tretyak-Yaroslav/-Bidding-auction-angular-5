package uk.co.afe.core.event;

import uk.co.afe.model.client.Client;

/**
 * @author Sergey Teryoshin
 * 27.03.2018 15:33
 */
public final class ClientCreatedEvent extends BaseEvent<Client> {
    public ClientCreatedEvent(Client source) {
        super(source);
    }
}
