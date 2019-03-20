package uk.co.afe.model.request.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.product.ProductSortType;

import javax.validation.constraints.NotNull;

/**
 * Sort type of list of products
 *
 * @author Sergey Teryoshin
 * 28.03.2018 16:19
 */
@Data
@ToString
@ApiModel(description = "Sort type of list of products")
public class GetSortedLimitProductsRequest {

    /**
     * Sort type of list of products
     */
    @ApiModelProperty(value = "Sort type of list of products", required = true)
    @NotNull
    private ProductSortType sortType;

    /**
     * Limit of entities
     */
    @ApiModelProperty(value = "Limit of entities", required = true)
    @NotNull
    private Long limit;

}
