package uk.co.afe.persistance.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.co.afe.model.role.Permission;

/**
 * Repository of client permissions
 *
 * @author Sergey Teryoshin
 * 17.03.2018 20:53
 */
@Transactional
@Repository
public interface DaoPermission extends JpaRepository<Permission, Long>,RefreshableRepository<Permission> {
}
