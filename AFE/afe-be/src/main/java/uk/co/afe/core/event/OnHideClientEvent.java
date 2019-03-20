package uk.co.afe.core.event;

import uk.co.afe.model.client.Client;

/**
 * @author Sergey Teryoshin
 * 28.03.2018 12:21
 */
public final class OnHideClientEvent extends BaseEvent<Client> {
    public OnHideClientEvent(Client source) {
        super(source);
    }
}
