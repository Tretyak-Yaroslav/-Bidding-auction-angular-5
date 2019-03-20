package uk.co.afe.model.response.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * Model with current server time
 *
 * @author Sergey Teryoshin
 * 26.03.2018 21:01
 */
@Data
@ToString
@ApiModel(description = "Current server time")
public class WarGetCurrentServerTimeResponse {

    /**
     * Current date and time
     */
    @ApiModelProperty(value = "Current date and time", required = true)
    private LocalDateTime currentTime;

}
