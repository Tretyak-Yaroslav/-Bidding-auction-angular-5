package uk.co.afe.model.request.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.client.Client;

/**
 * Client identifier and new risk ranking attribute to update ones
 *
 * @author Sergey Teryoshin
 * 23.03.2018 11:54
 */
@Data
@ToString
@ApiModel(description = "Client identifier and new risk ranking attribute to update ones")
public class WarUpdateRiskRankingAttRequest {

    /**
     * Identifier of client to update
     */
    @ApiModelProperty("Identifier of client to update")
    private Long clientId;
    /**
     * Risk ranking attribute
     */
    @ApiModelProperty("Risk ranking attribute")
    private Short atr;

    /**
     * Convert to client model
     */
    public Client toClient() {
        Client client = new Client();
        client.setAtr(atr);
        client.setClientId(clientId);
        return client;
    }
}
