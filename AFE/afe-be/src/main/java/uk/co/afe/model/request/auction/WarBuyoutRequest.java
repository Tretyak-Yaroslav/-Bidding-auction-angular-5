package uk.co.afe.model.request.auction;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.tx.Transaction;
import uk.co.afe.model.tx.TransactionType;

import javax.validation.constraints.NotNull;

/**
 * Product identifier to buyout
 *
 * @author Sergey Teryoshin
 * 25.03.2018 13:35
 */
@Data
@ToString
@ApiModel(description = "Product identifier to buyout")
public class WarBuyoutRequest {

    /**
     * Identifier of product
     */
    @ApiModelProperty(value = "Identifier of product", required = true)
    @NotNull
    private Long productId;

    /**
     * Convert to a big
     */
    public Transaction toBid() {
        Transaction tx = new Transaction();
        tx.setType(TransactionType.BID);
        tx.setProductId(productId);
        return tx;
    }
}
