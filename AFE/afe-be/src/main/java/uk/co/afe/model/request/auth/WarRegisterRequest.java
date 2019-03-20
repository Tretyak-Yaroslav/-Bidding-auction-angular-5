package uk.co.afe.model.request.auth;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.client.Client;
import uk.co.afe.validation.ValidationMessage;
import uk.co.afe.validation.ValidationPattern;
import uk.co.afe.validation.ValidationValue;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * Request with register data of new client
 *
 * @author Sergey Teryoshin
 * 13.03.2018 11:59
 * @see Client
 */
@ApiModel(description = "Request with register data of new client")
@Data
@ToString
public class WarRegisterRequest {

    /**
     * Full name of client owner
     */
    @ApiModelProperty(value = "Full name of client owner", required = true)
    @NotNull
    private String fullName;

    /**
     * Email of client owner
     */
    @ApiModelProperty(value = "Email of client owner", required = true)
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

    /**
     * Granting permissions to work-over user data
     */
    @ApiModelProperty(value = "Granting permissions to work-over user data", required = true)
    @NotNull
    @AssertTrue
    private Boolean consent;

    /**
     * Converter of request in Client
     */
    public Client toClient() {
        Client a = new Client();
        a.setEmail(email);
        a.setPassword(password);
        a.setName(fullName);
        return a;
    }

}
