package uk.co.afe.model.response.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.product.Product;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Response with array of all products, exclude hidden
 *
 * @author Sergey Teryoshin
 * 20.03.2018 16:03
 */
@Data
@ToString
@ApiModel(description = "Response with array of all products, exclude hidden")
public class WarGetProductsResponse {

    /**
     * Array of active lots
     */
    @ApiModelProperty("Array of products")
    @NotNull
    @Valid
    private List<Product> items;

    public WarGetProductsResponse(List<Product> products) {
        this.items = products;
    }
}
