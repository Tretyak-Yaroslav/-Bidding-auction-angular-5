package uk.co.afe.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uk.co.afe.core.PermissionBean;
import uk.co.afe.model.response.security.WarGetPermissionsResponse;
import uk.co.afe.utils.SessionUtils;

import javax.validation.Valid;
import java.util.List;

/**
 * Services of security actions
 *
 * @author Sergey Teryoshin
 * 17.03.2018 22:51
 */
@RestController
@RequestMapping("api/v1/security")
@Api(description = "Services of security actions",
        consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class SecurityController {

    private static final Logger LOG = LoggerFactory.getLogger(SecurityController.class);

    @Autowired
    private PermissionBean permissionBean;

    /**
     * Getting list of roles of current user
     * <p>Required permissions: hasRole('SECURITY_PERMISSION_GET')</p>
     */
    @ApiOperation(value = "Getting list of roles of current user", notes = "hasRole('SECURITY_PERMISSION_GET')")
    @PreAuthorize("hasRole('SECURITY_PERMISSION_GET')")
    @GetMapping("permissions/get")
    @Valid
    public WarGetPermissionsResponse get() {
        LOG.info("-> api/v1/security/permissions/get");
        Long clientId = SessionUtils.getClientId();
        List<String> permissions = permissionBean.getActions(clientId);
        WarGetPermissionsResponse response = new WarGetPermissionsResponse(permissions);
        LOG.info("<- api/v1/security/permissions/get. " + response);
        return response;
    }

}
