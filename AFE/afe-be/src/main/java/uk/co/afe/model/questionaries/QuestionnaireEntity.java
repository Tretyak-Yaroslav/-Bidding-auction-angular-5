package uk.co.afe.model.questionaries;

import lombok.Data;
import lombok.ToString;
import uk.co.afe.persistance.mapping.QuestionnaireConverter;

import javax.persistence.*;

/**
 * Database model of Questionnaire
 *
 * @author Sergey Teryoshin
 * 21.03.2018 18:34
 */
@Data
@ToString
@Entity
@Table(name = "tb_afe_questionnaire")
public class QuestionnaireEntity {

    /**
     * Questionnaire identifier
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "afe_questionnaire_id")
    private Long questionnaireId;

    /**
     * Questionnaire content
     */
    @Column(name = "questionnaire")
    @Convert(converter = QuestionnaireConverter.class)
    private Questionnaire questionnaire;
}
