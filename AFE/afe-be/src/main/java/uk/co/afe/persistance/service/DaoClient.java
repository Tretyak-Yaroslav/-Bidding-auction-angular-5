package uk.co.afe.persistance.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.co.afe.model.client.Client;

/**
 * Repository of clients
 *
 * @author Sergey Teryoshin
 * 16.03.2018 12:16
 */
@Transactional
@Repository
public interface DaoClient extends JpaRepository<Client, Long>,RefreshableRepository<Client> {
}
