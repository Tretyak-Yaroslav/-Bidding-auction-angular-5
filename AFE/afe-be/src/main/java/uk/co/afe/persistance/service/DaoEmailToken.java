package uk.co.afe.persistance.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.co.afe.model.EmailToken;

/**
 * Repository of email token
 *
 * @author Sergey Teryoshin
 * 31.03.2018 18:26
 */
@Repository
@Transactional
public interface DaoEmailToken extends JpaRepository<EmailToken, Long> {
}
