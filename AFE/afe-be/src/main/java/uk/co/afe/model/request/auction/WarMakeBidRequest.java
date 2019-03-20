package uk.co.afe.model.request.auction;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.tx.Transaction;
import uk.co.afe.model.tx.TransactionType;
import uk.co.afe.validation.ValidationValue;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * Info of bid
 *
 * @author Sergey Teryoshin
 * 24.03.2018 13:56
 */
@Data
@ToString
@ApiModel(description = "Info of bid")
public class WarMakeBidRequest {

    /**
     * Identifier of product
     */
    @ApiModelProperty("Identifier of product")
    @NotNull
    private Long productId;
    /**
     * Amount of money to bid
     */
    @ApiModelProperty("Amount of money to bid")
    @NotNull
    @DecimalMin(ValidationValue.MONEY_MIN)
    private Long amount;

    /**
     * Convert to a big
     */
    public Transaction toBid() {
        Transaction tx = new Transaction();
        tx.setType(TransactionType.BID);
        tx.setAmount(amount);
        tx.setProductId(productId);
        return tx;
    }
}
