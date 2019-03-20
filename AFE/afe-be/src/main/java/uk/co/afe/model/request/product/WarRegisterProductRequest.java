package uk.co.afe.model.request.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;
import uk.co.afe.model.product.Product;
import uk.co.afe.validation.ValidationValue;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * Register new product to be lot in auction
 *
 * @author Sergey Teryoshin
 * 24.03.2018 15:56
 */
@Data
@ToString
@ApiModel(description = "Register new product to be lot in auction")
public class WarRegisterProductRequest {

    /**
     * P2P/CF platform
     */
    @NotNull
    @ApiModelProperty("P2P/CF platform")
    private Long platformId;
    /**
     * Starting bid on auction
     */
    @NotNull
    @ApiModelProperty("Starting bid on auction")
    @DecimalMin(ValidationValue.MONEY_MIN)
    private Long startingBid;
    /**
     * Buyout price on auction
     */
    @NotNull
    @ApiModelProperty("Buyout price on auction")
    @DecimalMin(ValidationValue.MONEY_MIN)
    private Long buyOutPrice;
    /**
     * Auction start datetime
     */
    @NotNull
    @ApiModelProperty("Auction start time")
    private LocalDateTime startDate;
    /**
     * Auction end datetime
     */
    @NotNull
    @ApiModelProperty("Auction end time")
    private LocalDateTime endDate;
    /**
     * Product name
     */
    @NotNull
    @ApiModelProperty("Product time")
    private String name;
    /**
     * Product description
     */
    @NotNull
    @ApiModelProperty("Product description")
    private String description;

    /**
     * Convert to product model
     */
    public Product toProduct() {
        Product p = new Product();
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
