package uk.co.afe.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uk.co.afe.core.ProductBean;
import uk.co.afe.model.exceptions.ValidationException;
import uk.co.afe.model.product.Product;
import uk.co.afe.model.request.product.*;
import uk.co.afe.model.response.product.WarGetProductResponse;
import uk.co.afe.model.response.product.WarGetProductsResponse;
import uk.co.afe.utils.SessionUtils;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

/**
 * Services of products
 *
 * @author Sergey Teryoshin
 * 20.03.2018 15:40
 */
@Api(description = "Services of products")
@RestController
@RequestMapping("api/v1/product")
public class ProductController {

    private static final Logger LOG = LoggerFactory.getLogger(ProductController.class);

    @Autowired
    private ProductBean productBean;

    /**
     * Get sorted limit of products, exclude hidden.
     * <p>Required permissions: permitAll()</p>
     */
    @ApiOperation(value = "Get sorted limit of products, exclude hidden.", notes = "<p>Required permissions: permitAll()</p>")
    @PreAuthorize("permitAll()")
    @PostMapping("get/sorted-limit")
    @Valid
    public WarGetProductsResponse getAllProducts(@Valid @RequestBody GetSortedLimitProductsRequest request) {
        LOG.info("-> api/v1/product/get/sorted-limit");
        List<Product> products = productBean.getLimitedActiveLots(request.getSortType(), request.getLimit());
        WarGetProductsResponse response = new WarGetProductsResponse(products);
        LOG.info("<- api/v1/product/get/sorted-limit. " + response);
        return response;
    }

    /**
     * Get all products, exclude hidden.
     * <p>Required permissions: permitAll()</p>
     */
    @ApiOperation(value = "Get all products, exclude hidden.", notes = "<p>Required permissions: permitAll()</p>")
    @PreAuthorize("permitAll()")
    @GetMapping("get/all")
    @Valid
    public WarGetProductsResponse getAllProducts() {
        LOG.info("-> api/v1/product/get/all");
        List<Product> products = productBean.getAllActiveLots();
        WarGetProductsResponse response = new WarGetProductsResponse(products);
        LOG.info("<- api/v1/product/get/all. " + response);
        return response;
    }

    /**
     * Get my products by status
     * <p>Required permissions: isAuthenticated()</p>
     *
     * @param request Statuses to lookup products
     */
    @ApiOperation(value = "Get my products by status", notes = "<p>Required permissions: isAuthenticated()</p>")
    @PreAuthorize("isAuthenticated()")
    @PostMapping("get/my/by-status")
    @Valid
    public WarGetProductsResponse getMyProductsByStatus(@Valid @RequestBody WarGetProductsByStatusRequest request) {
        LOG.info("-> api/v1/product/get/my/by-status. " + request);
        Long clientId = SessionUtils.getClientId();
        List<Product> list = productBean.getProductsByStatus(clientId, request.getStatuses());
        WarGetProductsResponse response = new WarGetProductsResponse(list);
        LOG.info("<- api/v1/product/get/my/by-status. " + response);
        return response;
    }

    /**
     * Get products by status
     * <p>Required permissions: hasRole('PRODUCT_GET_ALL_BY_STATUS')</p>
     *
     * @param request Statuses to lookup products
     */
    @ApiOperation(value = "Get products by status", notes = "<p>Required permissions: hasRole('PRODUCT_GET_ALL_BY_STATUS')</p>")
    @PreAuthorize("hasRole('PRODUCT_GET_ALL_BY_STATUS')")
    @PostMapping("get/all/by-status")
    @Valid
    public WarGetProductsResponse getAllProductsByStatus(@Valid @RequestBody WarGetProductsByStatusRequest request) {
        LOG.info("-> api/v1/product/get/all/by-status. " + request);
        List<Product> list = productBean.getProductsByStatus(request.getStatuses());
        WarGetProductsResponse response = new WarGetProductsResponse(list);
        LOG.info("<- api/v1/product/get/all/by-status. " + response);
        return response;
    }

    /**
     * Get all products of client. Lookup by specified identifier of client
     * <p>Required permissions: isAuthenticated()</p>
     *
     * @param clientId Identifier of client
     */
    @ApiOperation(value = "Get all products of client. Lookup by specified identifier of client", notes = "<p>Required permissions: isAuthenticated()</p>")
    @PreAuthorize("isAuthenticated()")
    @GetMapping("get/all/{id}")
    @Valid
    public WarGetProductsResponse getAllProductsByOwnerId(@Valid @PathVariable("id") Long clientId) {
        LOG.info("-> api/v1/product/get/all/{id}. " + clientId);
        List<Product> list = productBean.getProductByOwnerId(clientId);
        WarGetProductsResponse response = new WarGetProductsResponse(list);
        LOG.info("<- api/v1/product/get/all/{id}. " + response);
        return response;
    }

    /**
     * Get product by identifier
     * <p>Required permissions: permitAll()</p>
     *
     * @param id Identifier of product for lookup
     */
    @ApiOperation(value = "Get product by identifier", notes = "<p>Required permissions: permitAll()</p>")
    @PreAuthorize("permitAll()")
    @GetMapping("get/{id}")
    @Valid
    public WarGetProductResponse getProduct(@Valid @PathVariable("id") Long id) {
        LOG.info("-> api/v1/product/get/{id}. " + id);
        Product product = productBean.getProductById(id);
        WarGetProductResponse response = new WarGetProductResponse(product);
        LOG.info("<- api/v1/product/get/{id}. " + response);
        return response;
    }

    /**
     * Register new product
     * <p>Required permissions: hasRole('PRODUCT_REGISTER')</p>
     *
     * @param request Data of new product
     */
    @ApiOperation(value = "Register new product", notes = "<p>Required permissions: hasRole('PRODUCT_REGISTER')</p>")
    @PreAuthorize("hasRole('PRODUCT_REGISTER')")
    @PostMapping("register")
    @Valid
    public WarGetProductResponse registerProduct(@Valid @RequestBody WarRegisterProductRequest request) {
        LOG.info("-> api/v1/product/register. " + request);
        Product product = request.toProduct();
        product.setProductOwnerId(SessionUtils.getClientId());
        if (product.getStartingBid() >= product.getBuyOutPrice()) {
            throw new ValidationException("Starting bid cannot be equals or bigger than buyout price");
        }
        if (!product.getStartDate().isBefore(product.getEndDate())) {
            throw new ValidationException("The start date of the auction must be before the end date");
        }
        product = productBean.registerProduct(product);
        WarGetProductResponse response = new WarGetProductResponse(product);
        LOG.info("<- api/v1/product/register. " + response);
        return response;
    }

    /**
     * Update risk ranking attribute of product
     * <p>Required permissions: hasRole('PRODUCT_UPDATE_ATR')</p>
     *
     * @param request New risk ranking attribute and identifier of product
     */
    @ApiOperation(value = "Update risk ranking attribute of product", notes = "<p>Required permissions: hasRole('PRODUCT_UPDATE_ATR')</p>")
    @PreAuthorize("hasRole('PRODUCT_UPDATE_ATR')")
    @PostMapping("update/atr")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRiskRankingAtr(@Valid @RequestBody WarUpdateRiskRankingAtrRequest request) {
        LOG.info("-> api/v1/product/update/atr. " + request);
        productBean.updateProductAtr(request.getProductId(), request.getRiskRanking());
        LOG.info("<- api/v1/product/update/atr");
    }

    /**
     * Update product details
     * <p>Required permissions: hasRole('PRODUCT_UPDATE_INFO')</p>
     *
     * @param request New product details
     */
    @ApiOperation(value = "Update product details", notes = "<p>Required permissions: hasRole('PRODUCT_UPDATE_INFO')</p>")
    @PreAuthorize("hasRole('PRODUCT_UPDATE_INFO')")
    @PostMapping("update/info")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateProductInfo(@Valid @RequestBody WarUpdateProductRequest request) {
        LOG.info("-> api/v1/product/update/info. " + request);
        Long clientId = SessionUtils.getClientId();
        productBean.updateProductInfo(clientId, request.toProduct());
        LOG.info("<- api/v1/product/update/info");
    }

    /**
     * Reject product
     * <p>Required permissions: hasRole('PRODUCT_REJECT')</p>
     *
     * @param productId Identifier of product
     */
    @ApiOperation(value = "Reject product", notes = "<p>Required permissions: hasRole('PRODUCT_REJECT')</p>")
    @PreAuthorize("hasRole('PRODUCT_REJECT')")
    @PostMapping("reject/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void rejectProduct(@PathVariable("id") Long productId) {
        LOG.info("-> api/v1/product/reject/{id}. " + productId);
        Long clientId = SessionUtils.getClientId();
        productBean.rejectProduct(clientId, productId);
        LOG.info("<- api/v1/product/reject/{id}");
    }

    /**
     * Approve product
     * <p>Required permissions: hasRole('PRODUCT_APPROVE')</p>
     *
     * @param productId Identifier of product
     */
    @ApiOperation(value = "Approve product", notes = "<p>Required permissions: hasRole('PRODUCT_APPROVE')</p>")
    @PreAuthorize("hasRole('PRODUCT_APPROVE')")
    @PostMapping("approve/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void approveProduct(@PathVariable("id") Long productId) {
        LOG.info("-> api/v1/product/approve/{id}. " + productId);
        Long clientId = SessionUtils.getClientId();
        productBean.saleProduct(clientId, productId);
        LOG.info("<- api/v1/product/approve/{id}");
    }

    /**
     * Approve deal for selected bid
     * <p>Required permissions: hasRole('PRODUCT_APPROVE_DEAL')</p>
     *
     * @param productId Identifier of product
     */
    @ApiOperation(value = "Approve deal for selected bid", notes = "<p>Required permissions: hasRole('PRODUCT_APPROVE_DEAL')</p>")
    @PreAuthorize("hasRole('PRODUCT_APPROVE_DEAL')")
    @PostMapping("approve-deal/{id}")
    public void approveDeal(@PathVariable("id") Long productId) {
        LOG.info("-> api/v1/product/approve-deal/{id}. " + productId);
        Long clientId = SessionUtils.getClientId();
        productBean.finishProduct(clientId, productId);
        LOG.info("<- api/v1/product/approve-deal/{id}");
    }

    /**
     * Update image of product
     * <p>Required permissions: hasRole('CLIENT_UPDATE_IMAGE')</p>
     *
     * @param productId Identifier of product * @param file New photo of product
     */
    @ApiOperation(value = "Update image of product", notes = "<p>Required permissions: hasRole('CLIENT_UPDATE_IMAGE')</p>")
    @PreAuthorize("hasRole('CLIENT_UPDATE_IMAGE')")
    @PutMapping(value = "update/image/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateImage(@PathVariable("id") Long productId,
                            @RequestPart("file") MultipartFile file) throws IOException {
        LOG.info("-> api/v1/product/update/image/{id}. " + productId);
        if (file != null) {
            byte[] body = file.getBytes();
            productBean.setPhoto(productId, body);
        }
        LOG.info("<- api/v1/product/update/image/{id}. " + productId);
    }

    /**
     * Getting photo of product by product identifier
     * <p>Required permissions: permitAll()</p>
     *
     * @param productId Identifier of product
     */
    @ApiOperation(value = "Getting photo of product by product identifier", notes = "<p>Required permissions: permitAll()</p>")
    @PreAuthorize("permitAll()")
    @GetMapping("photo/{id}")
    public byte[] getPhoto(@PathVariable("id") Long productId) {
        LOG.info("-> api/v1/product/photo/{id}. " + productId);
        byte[] bytes = productBean.getPhoto(productId);
        LOG.info("<- api/v1/product/photo/{id}. " + bytes);
        return bytes;
    }
}
