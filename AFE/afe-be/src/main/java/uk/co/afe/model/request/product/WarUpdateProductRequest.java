package uk.co.afe.model.request.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.product.Product;
import uk.co.afe.validation.ValidationValue;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Update product details
 *
 * @author Sergey Teryoshin
 * 24.03.2018 15:56
 */
@Data
@ToString
@ApiModel(description = "Update product details")
public class WarUpdateProductRequest {

    @NotNull
    @ApiModelProperty("Identifier of product")
    private Long productId;
    /**
     * P2P/CF platform
     */
    @ApiModelProperty("P2P/CF platform")
    private Long platformId;
    /**
     * Starting bid on auction
     */
    @ApiModelProperty("Starting bid on auction")
    @DecimalMin(ValidationValue.MONEY_MIN)
    private Long startingBid;
    /**
     * Buyout price on auction
     */
    @ApiModelProperty("Buyout price on auction")
    @DecimalMin(ValidationValue.MONEY_MIN)
    private Long buyOutPrice;
    /**
     * Auction start datetime
     */
    @ApiModelProperty("Auction start time")
    private LocalDateTime startDate;
    /**
     * Auction end datetime
     */
    @ApiModelProperty("Auction end time")
    private LocalDateTime endDate;
    /**
     * Product name
     */
    @ApiModelProperty("Product time")
        private String name;
    /**
     * Product description
     */
    @ApiModelProperty("Product description")
    private String description;

    /**
     * Convert to product model
     */
    public Product toProduct() {
        Product p = new Product();
        p.setProductId(productId);
        p.setBuyOutPrice(buyOutPrice);
        p.setDescription(description);
        p.setName(name);
        p.setEndDate(endDate);
        p.setStartDate(startDate);
        p.setStartingBid(startingBid);
        p.setPlatformId(platformId);
        return p;
    }

}
