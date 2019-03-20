package uk.co.afe.model.response.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * Response model with info about phone number availability for register
 *
 * @author Sergey Teryoshin
 * 18.03.2018 10:12
 */
@ApiModel(description = "Response model with info about phone number availability for register")
@Data
@ToString
public class WarIsPhoneAvailableResponse {

    /**
     * true - if phone number can be used, false - if not
     */
    @ApiModelProperty(value = "true - if phone number can be used, false - if not",required = true)
    @NotNull
    private Boolean available;

    public WarIsPhoneAvailableResponse(Boolean available) {
        this.available = available;
    }
}
