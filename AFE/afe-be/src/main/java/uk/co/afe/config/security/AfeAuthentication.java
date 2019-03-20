package uk.co.afe.config.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Authentication container implementation
 *
 * @author Sergey Teryoshin
 * 12.03.2017 22:01
 */
public final class AfeAuthentication extends AbstractAuthenticationToken {

    private static final long serialVersionUID = 2125821574324950714L;

    private final Long clientId;

    public AfeAuthentication(Long clientId) {
        this(clientId, null);
    }

    public AfeAuthentication(Long clientId, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.clientId = clientId;
    }

    @Override
    public Object getCredentials() {
        return "unused";
    }

    @Override
    public Long getPrincipal() {
        return clientId;
    }
}
