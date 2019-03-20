package uk.co.afe.core;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import uk.co.afe.core.event.ExpireProductEvent;
import uk.co.afe.core.event.OnProductSaleEvent;
import uk.co.afe.core.event.ProductCreatedEvent;
import uk.co.afe.model.ErrorCode;
import uk.co.afe.model.exceptions.ChangeProductStatusException;
import uk.co.afe.model.exceptions.ObjectNotFoundException;
import uk.co.afe.model.product.Product;
import uk.co.afe.model.product.ProductSortType;
import uk.co.afe.model.product.ProductStatus;
import uk.co.afe.persistance.service.DaoFileManager;
import uk.co.afe.persistance.service.DaoProduct;
import uk.co.afe.utils.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author Sergey Teryoshin
 * 20.03.2018 15:44
 */
@Service
public class ProductBean {

    @Autowired
    private DaoProduct daoProduct;
    @Autowired
    private PermissionBean permissionBean;
    @Autowired
    private ApplicationEventPublisher publisher;
    @Autowired
    private DaoFileManager fileManager;

    public List<Product> getLimitedActiveLots(ProductSortType sortType, Long limit) {
        Pageable pageable = PageRequest.of(0, limit.intValue());
        switch (sortType) {
            case CREATE_DATE:
                return daoProduct.findProductsByProductStatusIsOrderByCreatedDatetimeDesc(ProductStatus.FOR_SALE, pageable);
            case BID_COUNT:
                return daoProduct.findProductsByProductStatusIsOrderByBidCountDesc(ProductStatus.FOR_SALE, pageable);
        }
        return getAllActiveLots();
    }

    public List<Product> getAllActiveLots() {
        return daoProduct.findProductsByProductStatusIs(ProductStatus.FOR_SALE);
    }

    public Product updateProductAtr(Long productId, Integer atr) {
        Product product = getProductById(productId);
        product.setRiskRanking(atr);
        return daoProduct.saveAndFlush(product);
    }

    public Product getProductById(Long id) {
        return daoProduct.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(ErrorCode.PRODUCT_NOT_FOUND, id, Product.class));
    }

    public List<Product> getProductsByStatus(Long ownerId, List<ProductStatus> statuses) {
        return daoProduct.findProductsByProductOwnerIdAndProductStatusIsIn(ownerId, statuses);
    }

    public List<Product> getProductsByStatus(List<ProductStatus> statuses) {
        return daoProduct.findProductsByProductStatusIsIn(statuses);
    }

    public List<Product> getProductByOwnerId(Long clientId) {
        return getProductsByStatus(clientId, Arrays.asList(ProductStatus.values()));
    }

    public Product registerProduct(Product product) {
        product.setProductStatus(ProductStatus.PENDING);
        product.setCreatedDatetime(LocalDateTime.now());
        product.setPrice(product.getStartingBid());
        product = daoProduct.saveAndFlush(product);
        publisher.publishEvent(new ProductCreatedEvent(product));
        return product;
    }

    public Product updateProductInfo(Long clientId, Product product) {
        Product original = getProductById(product.getProductId());
        if (!original.getProductOwnerId().equals(clientId)) {
            throw new AccessDeniedException("Access denied!");
        }
        BeanUtils.copyProperties(product, original, ObjectUtils.getNullPropertiesNames(product));
        return daoProduct.saveAndFlush(original);
    }

    public Product rejectProduct(Long clientId, Long productId) {
        if (!permissionBean.isBackOffice(clientId)
                && !permissionBean.isTechUser(clientId)) {
            throw new AccessDeniedException("Access denied!");
        }
        return updateStatus(productId, ProductStatus.REJECTED);
    }

    public Product saleProduct(Long clientId, Long productId) {
        if (!permissionBean.isBackOffice(clientId)
                && !permissionBean.isTechUser(clientId)) {
            throw new AccessDeniedException("Access denied!");
        }
        return updateStatus(productId, ProductStatus.FOR_SALE);
    }

    public Product expireProduct(Long clientId, Long productId) {
        if (!permissionBean.isTechUser(clientId)) {
            throw new AccessDeniedException("Access denied!");
        }
        Product product = updateStatus(productId, ProductStatus.EXPIRED);
        publisher.publishEvent(new ExpireProductEvent(product));
        return product;
    }

    public Product salePendingProduct(Long clientId, Long productId) {
        if (!permissionBean.isClient(clientId)) {
            throw new AccessDeniedException("Access denied!");
        }
        return updateStatus(productId, ProductStatus.SALE_PENDING);
    }

    public Product finishProduct(Long clientId, Long productId) {
        if (!permissionBean.isBackOffice(clientId)) {
            throw new AccessDeniedException("Access denied!");
        }
        Product product = updateStatus(productId, ProductStatus.OWNED);
        publisher.publishEvent(new OnProductSaleEvent(product));
        return product;
    }

    private Product updateStatus(Long productId, ProductStatus newStatus) {
        Product original = getProductById(productId);
        if (!original.getProductStatus().canBeChangedTo(newStatus)) {
            throw new ChangeProductStatusException(productId, original.getProductStatus(), newStatus);
        }
        original.setProductStatus(newStatus);
        return daoProduct.saveAndFlush(original);
    }

    public Product setPhoto(Long productId, byte[] body) {
        Product product = getProductById(productId);
        String oldPhoto = product.getPhoto();
        String path = fileManager.save(body);
        product.setPhoto(path);
        product = daoProduct.saveAndFlush(product);
        if (oldPhoto != null) {
            fileManager.remove(oldPhoto);
        }
        return product;
    }

    public byte[] getPhoto(Long productId) {
        Product product = getProductById(productId);
        String path = product.getPhoto();
        byte[] bytes = null;
        if (path != null) {
            bytes = fileManager.get(path);
        }
        if (bytes == null) {
            throw new ObjectNotFoundException(ErrorCode.PRODUCT_PHOTO_NOT_FOUND, productId, "product photo");
        }
        return bytes;
    }

    public void updateProductPrice(Long productId, Long amount) {
        Product product = getProductById(productId);
        product.setPrice(amount);
        daoProduct.saveAndFlush(product);
    }
}
