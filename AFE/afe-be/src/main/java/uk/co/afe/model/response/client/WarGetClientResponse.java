package uk.co.afe.model.response.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.client.Client;

import javax.validation.Valid;

/**
 * Client info without password
 *
 * @author Sergey Teryoshin
 * 21.03.2018 15:03
 */
@ApiModel(description = "Client info without password")
@Data
@ToString
public class WarGetClientResponse {

    /**
     * Client info without password
     */
    @ApiModelProperty("Client info without password")
    @Valid
    private Client client;

    public WarGetClientResponse(@Valid Client client) {
        this.client = client;
    }
}
