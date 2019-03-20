package uk.co.afe.model.request.client;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.client.Client;

import java.time.LocalDateTime;

/**
 * New info to update client
 *
 * @author Sergey Teryoshin
 * 21.03.2018 14:59
 */
@ApiModel(description = "New info to update client")
@Data
@ToString
public class WarUpdateInfoRequest {

    /**
     * New name of client
     */
    @ApiModelProperty("New name of client")
    private String name;

    /**
     * New phone number of client
     */
    @ApiModelProperty("New phone number of client")
    private String phoneNumber;

    /**
     * New birthday of client
     */
    @ApiModelProperty("New birthday of client")
    private LocalDateTime birthday;

    /**
     * Converting to client model
     */
    public Client toClient() {
        Client client = new Client();
        client.setPhoneNumber(phoneNumber);
        client.setBirthday(birthday);
        client.setName(name);
        return client;
    }
}
