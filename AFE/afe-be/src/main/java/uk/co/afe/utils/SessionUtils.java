package uk.co.afe.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utilities to work with session
 *
 * @author Sergey Teryoshin
 * 24.03.2018 14:04
 */
public final class SessionUtils {

    /**
     * Get identifier of current client. In client is not authenticated null will be returned
     *
     * @return Identifier of current client
     */
    public static Long getClientId() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return null;
        }
        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal == null || String.class.isInstance(principal)) {
            return null;
        }
        return (Long) principal;
    }
}
