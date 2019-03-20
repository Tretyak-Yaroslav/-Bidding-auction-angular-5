package uk.co.afe.persistance.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.co.afe.model.Platform;

/**
 * Repository of platforms
 *
 * @author Sergey Teryoshin
 * 20.03.2018 14:53
 */
@Repository
@Transactional
public interface DaoPlatform extends JpaRepository<Platform, Long>,RefreshableRepository<Platform> {
}
