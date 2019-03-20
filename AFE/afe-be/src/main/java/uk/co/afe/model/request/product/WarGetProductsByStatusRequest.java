package uk.co.afe.model.request.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.product.ProductStatus;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * List of statuses to lookup
 *
 * @author Sergey Teryoshin
 * 24.03.2018 20:22
 */
@ApiModel(description = "List of statuses to lookup")
@Data
@ToString
public class WarGetProductsByStatusRequest {

    /**
     * List of product statuses
     */
    @ApiModelProperty(value = "List of product statuses",required = true)
    @NotNull
    private List<ProductStatus> statuses;

}
