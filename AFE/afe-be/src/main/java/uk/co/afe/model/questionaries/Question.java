package uk.co.afe.model.questionaries;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Question in the questionnaire
 *
 * @author Sergey Teryoshin
 * 21.03.2018 18:08
 */
@Data
@ToString
@ApiModel(description = "Question in the questionnaire")
public class Question {

    /**
     * Question code
     */
    @ApiModelProperty("Question code")
    @NotNull
    private Long code;

    /**
     * Question text
     */
    @ApiModelProperty("Question text")
    @NotNull
    private String text;

    /**
     * List of answers
     */
    @ApiModelProperty("Array of answers")
    @NotNull
    @Valid
    private List<Answer> answers;
}
