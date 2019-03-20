package uk.co.afe.model.request.client;

import com.google.common.collect.ImmutableList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.model.questionaries.Answer;
import uk.co.afe.model.questionaries.Question;
import uk.co.afe.model.questionaries.Questionnaire;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Request with questionnaire answers
 *
 * @author Sergey Teryoshin
 * 22.03.2018 12:10
 */
@Data
@ToString
@ApiModel(description = "Request with questionnaire answers")
public class WarUpdateQuestionnaireResultsRequest {

    /**
     * Identifier of questionnaire
     */
    @ApiModelProperty("Identifier of questionnaire")
    @NotNull
    private Long id;
    /**
     * Client answers. Key is a code of question, value is a code of answer
     */
    @ApiModelProperty("Client answers. Key is a code of question, value is a code of answer")
    @NotNull
    private Map<Long, Long> answers;

    /**
     * Convert to questionnaire
     */
    public Questionnaire toQuestionnaire() {
        Questionnaire questionnaire = new Questionnaire();
        questionnaire.setId(id);
        List<Question> questions = answers.entrySet().stream()
                .map(e -> {
                    Question q = new Question();
                    q.setCode(e.getKey());
                    Answer a = new Answer();
                    a.setCode(e.getValue());
                    q.setAnswers(ImmutableList.of(a));
                    return q;
                }).collect(Collectors.toList());
        questionnaire.setQuestions(questions);
        return questionnaire;
    }

}
