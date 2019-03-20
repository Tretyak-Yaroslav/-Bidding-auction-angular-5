package uk.co.afe.model.request.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * New password and current password
 *
 * @author Sergey Teryoshin
 * 21.03.2018 15:18
 */
@ApiModel(description = "New password and current password")
@Data
@ToString
public class WarUpdatePasswordRequest {

    /**
     * Hash of new password
     */
    @ApiModelProperty("Hash of new password")
    private String newPassword;

    /**
     * Hash of old password
     */
    @ApiModelProperty("Hash of old password")
    private String currentPassword;

}
