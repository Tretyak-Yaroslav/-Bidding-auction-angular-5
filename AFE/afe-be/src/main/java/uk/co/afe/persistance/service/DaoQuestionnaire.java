package uk.co.afe.persistance.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.co.afe.model.questionaries.QuestionnaireEntity;

/**
 * Repository of questionnaire
 *
 * @author Sergey Teryoshin
 * 21.03.2018 18:28
 */
@Repository
@Transactional
public interface DaoQuestionnaire extends JpaRepository<QuestionnaireEntity, Long>, RefreshableRepository {
}
