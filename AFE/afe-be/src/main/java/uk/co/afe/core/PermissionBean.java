package uk.co.afe.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import uk.co.afe.ValueConstants;
import uk.co.afe.model.role.Action;
import uk.co.afe.model.role.Permission;
import uk.co.afe.model.role.Roles;
import uk.co.afe.persistance.service.DaoPermission;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Sergey Teryoshin
 * 17.03.2018 22:52
 */
@Service
public class PermissionBean {

    @Autowired
    private DaoPermission daoPermission;

    public List<Permission> getPermissions(Long clientId) {
        Permission permission = new Permission();
        permission.setClientId(clientId);
        return daoPermission.findAll(Example.of(permission));
    }

    public Permission savePermission(Permission permission) {
        permission = daoPermission.saveAndFlush(permission);
        daoPermission.refresh(permission);
        return permission;
    }

    public List<String> getActions(Long clientId) {
        List<Permission> permissions = getPermissions(clientId);
        return permissions.stream()
                .map(Permission::getRole)
                .flatMap(r -> r.getActions().stream())
                .distinct()
                .map(Action::getName)
                .collect(Collectors.toList());
    }


    public Boolean isBackOffice(Long clientId) {
        List<Permission> permissions = getPermissions(clientId);
        for (Permission permission : permissions) {
            if (permission.getRole().getRoleId().equals(Roles.BACKOFFICE.getRoleId())) {
                return true;
            }
        }
        return false;
    }

    public Boolean isClient(Long clientId) {
        List<Permission> permissions = getPermissions(clientId);
        for (Permission permission : permissions) {
            if (permission.getRole().getRoleId().equals(Roles.CLIENT.getRoleId())) {
                return true;
            }
        }
        return false;
    }

    public Boolean isTechUser(Long clientId) {
        return clientId.equals(ValueConstants.TECH_USER);
    }
}
