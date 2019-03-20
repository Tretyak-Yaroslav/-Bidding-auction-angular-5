package uk.co.afe.model.response.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.Platform;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Response with array of P2P/CF platforms
 *
 * @author Sergey Teryoshin
 * 20.03.2018 15:19
 */
@Data
@ToString
@ApiModel(description = "Response with array of P2P/CF platforms")
public class WarGetPlatformsResponse {

    /**
     * Array of all platforms, include hidden
     */
    @ApiModelProperty("Array of all platforms, include hidden")
    @NotNull
    @Valid
    private List<Platform> items;

    public WarGetPlatformsResponse(List<Platform> items) {
        this.items = items;
    }
}
