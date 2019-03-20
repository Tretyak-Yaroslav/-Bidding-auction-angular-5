package uk.co.afe.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import uk.co.afe.config.security.AfeAuthentication;
import uk.co.afe.core.AuthorizationBean;
import uk.co.afe.model.client.Client;
import uk.co.afe.model.request.auth.WarLoginRequest;
import uk.co.afe.model.request.auth.WarRegisterRequest;
import uk.co.afe.model.response.client.WarGetClientResponse;
import uk.co.afe.utils.SessionUtils;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * Services of registration, authorization and authentication actions
 *
 * @author Sergey Teryoshin
 * 13.03.2018 11:57
 */
@RestController
@RequestMapping("api/v1/auth")
@Api(description = "Services of registration, authorization and authentication actions",
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthorizationController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorizationController.class);

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AuthorizationBean bean;

    /**
     * Is client authorized
     * <p>Required permissions: permitAll()</p>
     */
    @PreAuthorize("permitAll()")
    @GetMapping("refresh-token")
    @ApiOperation(value = "Is client authorized", notes = "<p>Required permissions: permitAll()</p>")
    public boolean isAuthorized() {
        LOG.info("-> api/v1/auth/authorized");
        return SessionUtils.getClientId() != null;
    }

    /**
     * Signing up of new client
     * <p>Required permissions: isAnonymous()</p>
     *
     * @param request Registration data
     */
    @PreAuthorize("isAnonymous()")
    @PutMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Signing up of new client"
            , notes = "It's better to use hash code of password for signing up <p>Required permissions: isAnonymous()</p>")
    public void register(@Valid @RequestBody WarRegisterRequest request) {
        LOG.info("-> api/v1/auth/register. " + request);
        Client client = request.toClient();
        bean.encryptPassAndSave(client);
        LOG.info("<- api/v1/auth/register");
    }

    /**
     * Signing in into system
     * <p>Required permissions: isAnonymous()</p>
     *
     * @param request Client credentials to authorize
     */
    @PreAuthorize("isAnonymous()")
    @PostMapping(value = "login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Signing in into system"
            , notes = "It's better to use hash code of password for signing in <p>Required permissions: isAnonymous()</p>")
    public WarGetClientResponse login(@Valid @RequestBody WarLoginRequest request) {
        LOG.info("-> api/v1/auth/login. " + request);
        Client client = bean.checkCredentialsAndGet(request.getEmail(), request.getPassword());
        doLogin(client);
        WarGetClientResponse response = new WarGetClientResponse(client);
        LOG.info("<- api/v1/auth/login. " + response);
        return response;
    }

    /**
     * Logging out
     * <p>Required permissions: isAuthenticated()</p>
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping(value = "logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Logging out")
    public void logout(HttpSession session) {
        LOG.info("-> api/v1/auth/logout");
        doLogout(session);
        LOG.info("<- api/v1/auth/logout");
    }

    /**
     * Email confirmation service
     * <p>Required permissions: isAnonymous()</p>
     *
     * @param token Confirmation token
     */
    @PreAuthorize("isAnonymous()")
    @GetMapping("confirm/email/{token}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Confirmation of email address", notes = "<p>Required permissions: isAnonymous()</p>")
    public void confirmEmail(@PathVariable("token") String token) {
        LOG.info("-> api/v1/auth/confirm/email/{token}. " + token);
        bean.confirmEmail(token);
        LOG.info("<- api/v1/auth/confirm/email/{token}");
    }

    private void doLogin(Client client) {
        Authentication authentication = authenticationManager.authenticate(new AfeAuthentication(client.getClientId()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private void doLogout(HttpSession session) {
        SecurityContextHolder.clearContext();
        session.invalidate();
    }

}
