package uk.co.afe.model.response.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.client.Client;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Get array of all clients, include hidden
 *
 * @author Sergey Teryoshin
 * 22.03.2018 22:31
 */
@Data
@ToString
@ApiModel(description = "Get array of all clients, include hidden")
public class WarGetAllClientsResponse {

    /**
     * Array of all clients, include hidden
     */
    @ApiModelProperty("Array of all clients, include hidden")
    @NotNull
    private List<Client> items;

    public WarGetAllClientsResponse(List<Client> clients) {
        this.items = clients;
    }
}
