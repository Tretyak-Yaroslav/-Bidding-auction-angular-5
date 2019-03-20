package uk.co.afe.model.response.auction;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.tx.Transaction;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Array of bids of lot found by specified identifier
 *
 * @author Sergey Teryoshin
 * 24.03.2018 13:48
 */
@ApiModel(description = "Array of bids of lot found by specified identifier")
@Data
@ToString
public class WarGetAllBidsResponse {

    /**
     * Array of bids
     */
    @ApiModelProperty("Array of bids")
    @Valid
    @NotNull
    private List<Transaction> items;

    public WarGetAllBidsResponse(List<Transaction> bids) {
        items = bids;
    }
}
