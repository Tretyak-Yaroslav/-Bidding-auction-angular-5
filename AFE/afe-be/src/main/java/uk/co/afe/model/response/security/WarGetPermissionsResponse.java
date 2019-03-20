package uk.co.afe.model.response.security;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.client.Client;
import uk.co.afe.model.role.Permission;

import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

/**
 * Response model with list of roles
 *
 * @author Sergey Teryoshin
 * 17.03.2018 22:49
 * @see Client
 * @see Permission
 */

@ApiModel(description = "Response model with roles of current user")
@Data
@ToString
public class WarGetPermissionsResponse {

    /**
     * List of roles of current user
     */
    @ApiModelProperty(value = "List of roles of current user", required = true)
    @NotNull
    private List<String> roles;

    public WarGetPermissionsResponse(List<String> permissions) {
        roles = Collections.unmodifiableList(permissions);
    }
}
