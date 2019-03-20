package uk.co.afe.model.request.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * Request with phone number to check availability
 *
 * @author Sergey Teryoshin
 * 18.03.2018 10:18
 */
@ApiModel(description = "Request with phone number to check availability")
@Data
@ToString
public class WarIsPhoneNumberAvailableRequest {

    /**
     * Phone number to check availability
     */
    @ApiModelProperty(value = "Phone number to check availability", required = true)
    @NotNull
    private String phoneNumber;
}
