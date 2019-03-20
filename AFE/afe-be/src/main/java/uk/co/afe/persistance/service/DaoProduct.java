package uk.co.afe.persistance.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import uk.co.afe.model.product.Product;
import uk.co.afe.model.product.ProductStatus;

import java.util.List;

/**
 * @author Sergey Teryoshin
 * 20.03.2018 14:56
 */
@Repository
@Transactional
public interface DaoProduct extends JpaRepository<Product, Long>, RefreshableRepository<Product> {

    List<Product> findProductsByProductStatusIs(ProductStatus statuses);

    List<Product> findProductsByProductStatusIsOrderByCreatedDatetimeDesc(ProductStatus statuses, Pageable pageable);

    List<Product> findProductsByProductStatusIsOrderByBidCountDesc(ProductStatus statuses, Pageable pageable);

    List<Product> findProductsByProductOwnerIdAndProductStatusIsIn(Long clientId, List<ProductStatus> statuses);

    List<Product> findProductsByProductStatusIsIn(List<ProductStatus> statuses);

}
