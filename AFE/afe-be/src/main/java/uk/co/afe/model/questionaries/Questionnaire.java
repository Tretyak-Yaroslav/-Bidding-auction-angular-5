package uk.co.afe.model.questionaries;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Container for questions and options of answer
 *
 * @author Sergey Teryoshin
 * 21.03.2018 18:22
 */
@Data
@ToString
@ApiModel(description = "Container for questions")
public class Questionnaire {

    /**
     * Code of questionnaire
     */
    @ApiModelProperty("Code of questionnaire")
    @NotNull
    private Long id;
    /**
     * List of questions
     */
    @ApiModelProperty("List of questions")
    @Valid
    @NotNull
    private List<Question> questions;

}
