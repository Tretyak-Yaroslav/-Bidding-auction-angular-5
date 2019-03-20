package uk.co.afe.model.response.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * New photo of product
 *
 * @author Sergey Teryoshin
 * 29.03.2018 13:45
 */
@Data
@ToString
@ApiModel(description = "New photo of product")
public class WarSetProductPhotoRequest {

    /**
     * Identifier of product
     */
    @ApiModelProperty("Identifier of product")
    @NotNull
    private Long productId;

    /**
     * New photo of product
     */
    @ApiModelProperty("New photo of product")
    @NotNull
    private MultipartFile file;

}
