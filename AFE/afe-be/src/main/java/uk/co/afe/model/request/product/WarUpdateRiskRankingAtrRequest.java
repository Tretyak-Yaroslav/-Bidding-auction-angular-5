package uk.co.afe.model.request.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.validation.ValidationValue;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * Update risk ranking attribute of product
 *
 * @author Sergey Teryoshin
 * 25.03.2018 0:20
 */
@Data
@ToString
@ApiModel(description = "Update risk ranking attribute of product")
public class WarUpdateRiskRankingAtrRequest {

    /**
     * Identifier of product
     */
    @ApiModelProperty(value = "Identifier of product", required = true)
    @NotNull
    private Long productId;

    /**
     * New product risk ranking
     */
    @ApiModelProperty(value = "New product risk ranking", required = true)
    @NotNull
    @DecimalMin(ValidationValue.RISK_RANKING_ATR_MIN)
    @DecimalMax(ValidationValue.RISK_RANKING_ATR_MAX)
    private Integer riskRanking;

}
