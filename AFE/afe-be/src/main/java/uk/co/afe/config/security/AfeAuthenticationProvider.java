package uk.co.afe.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import uk.co.afe.core.AuthorizationBean;
import uk.co.afe.core.PermissionBean;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergey Teryoshin
 * 12.03.2017 17:22
 */
@Component
public class AfeAuthenticationProvider implements AuthenticationProvider {

    private static final String ROLE_PREFIX = "ROLE_";

    @Autowired
    private PermissionBean permissionBean;
    @Autowired
    private AuthorizationBean bean;

    /**
     * @inheritDoc
     */
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Long clientId = (Long) authentication.getPrincipal();
        String[] userRoles = getRoles(clientId);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        authentication = new AfeAuthentication(clientId, authorities);
        authentication.setAuthenticated(true);
        return authentication;
    }

    /**
     * @inheritDoc
     */
    public boolean supports(Class<?> authentication) {
        return authentication == AfeAuthentication.class;
    }

    /**
     * Getting client roles
     *
     * @param clientId Client identifier.
     * @return Client roles.
     */
    private String[] getRoles(Long clientId) {
        List<String> actions = permissionBean.getActions(clientId);
        return toSpringSecurityRoles(actions);
    }

    /**
     * Converting roles to SpringSecurity view.
     */
    private String[] toSpringSecurityRoles(List<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return new String[]{};
        }
        String[] result = new String[roles.size()];
        result = roles.stream().map(role -> ROLE_PREFIX + role).collect(Collectors.toList()).toArray(result);
        return result;
    }

}
