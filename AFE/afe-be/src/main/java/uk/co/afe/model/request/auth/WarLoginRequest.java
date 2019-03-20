package uk.co.afe.model.request.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.client.Client;
import uk.co.afe.validation.ValidationMessage;
import uk.co.afe.validation.ValidationPattern;
import uk.co.afe.validation.ValidationValue;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Request with credentials for login
 *
 * @author Sergey Teryoshin
 * 13.03.2018 12:07
 * @see Client
 */
@ApiModel(description = "Request with credentials for login")
@Data
@ToString
public class WarLoginRequest {

    /**
     * Login of user
     */
    @ApiModelProperty(value = "Login of user", required = true)
    @NotNull
    @Pattern(regexp = ValidationPattern.EMAIL, message = ValidationMessage.EMAIL)
    @Size(min = ValidationValue.EMAIL_SIZE_MIN, max = ValidationValue.EMAIL_SIZE_MAX)
    private String email;

    /**
     * Hash code of client password
     */
    @ApiModelProperty(value = "Hash code of client password", required = true)
    @NotNull
    private String password;

}
