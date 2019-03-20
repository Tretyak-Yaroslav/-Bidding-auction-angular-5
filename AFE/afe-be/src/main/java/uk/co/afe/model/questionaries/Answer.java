package uk.co.afe.model.questionaries;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * Option to answer
 *
 * @author Sergey Teryoshin
 * 21.03.2018 18:13
 */
@Data
@ToString
@ApiModel(description = "The answer to the question")
public class Answer {

    /**
     * Answer code
     */
    @ApiModelProperty("Answer code")
    @NotNull
    private Long code;

    /**
     * Text of answer
     */
    @ApiModelProperty("Text of answer")
    @NotNull
    private String text;
}

