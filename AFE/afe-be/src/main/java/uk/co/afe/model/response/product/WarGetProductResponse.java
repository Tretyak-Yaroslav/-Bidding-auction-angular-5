package uk.co.afe.model.response.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.product.Product;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Product found by specified identifier
 *
 * @author Sergey Teryoshin
 * 24.03.2018 11:54
 */
@Data
@ToString
@ApiModel(description = "Product found by specified identifier")
public class WarGetProductResponse {

    /**
     * Product found by specified identifier
     */
    @ApiModelProperty("Product found by specified identifier")
    @NotNull
    private Product product;

    public WarGetProductResponse(@NotNull Product product) {
        this.product = product;
    }
}