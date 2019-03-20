package uk.co.afe.model.response.dictionary;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.questionaries.Questionnaire;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Response with questionnaire
 *
 * @author Sergey Teryoshin
 * 22.03.2018 11:30
 */
@Data
@ToString
@ApiModel(description = "Response with questionnaire")
public class WarGetQuestionnaireResponse {

    /**
     * Questionnaire with list of questions and list of answers
     */
    @ApiModelProperty("Questionnaire")
    @NotNull
    @Valid
    private Questionnaire questionnaire;

    public WarGetQuestionnaireResponse(Questionnaire questionnare) {
        this.questionnaire = questionnare;
    }
}
