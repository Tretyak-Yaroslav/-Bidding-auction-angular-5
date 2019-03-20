package uk.co.afe.persistance.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.co.afe.model.tx.Transaction;
import uk.co.afe.model.tx.TransactionStatus;

/**
 * Repository of transactions
 *
 * @author Sergey Teryoshin
 * 24.03.2018 12:51
 */
@Transactional
@Repository
public interface DaoTransaction extends JpaRepository<Transaction, Long> {

    Transaction findTop1ByProductIdAndStatusOrderByAmountDesc(Long productId, TransactionStatus status);

}
