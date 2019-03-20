package uk.co.afe.model.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Formula;
import uk.co.afe.persistance.mapping.ProductStatusConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity that contains information of product
 *
 * @author Sergey Teryoshin
 * 20.03.2018 14:46
 */
@Data
@ToString
@Entity
@Table(name = "tb_afe_product")
@ApiModel(description = "Entity that contains information of product")
public class Product {

    /**
     * Product identifier
     */
    @ApiModelProperty("Product identifier")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "afe_product_id")
    private Long productId;

    /**
     * Identifier of product owner
     */
    @ApiModelProperty("Identifier of product owner")
    @Column(name = "product_owner_id")
    private Long productOwnerId;

    /**
     * Full name of product owner
     */
    @ApiModelProperty("Full name of product owner")
    @Formula("(SELECT tc.name FROM tb_afe_client tc WHERE tc.afe_client_id = product_owner_id)")
    private String productOwnerFullName;
    /**
     * Product status
     */
    @ApiModelProperty("Product status")
    @Column(name = "product_status")
    @Convert(converter = ProductStatusConverter.class)
    private ProductStatus productStatus;

    /**
     * Identifier of platform
     */
    @ApiModelProperty("Identifier of platform")
    @Column(name = "platform_id")
    private Long platformId;

    /**
     * Starting bid of product in auction
     */
    @ApiModelProperty("Starting bid of product in auction")
    @Column(name = "starting_bid")
    private Long startingBid;

    /**
     * Buyout price of product in auction
     */
    @ApiModelProperty("Buyout price of product in auction")
    @Column(name = "buy_out_price")
    private Long buyOutPrice;

    /**
     * Start datetime of auction
     */
    @ApiModelProperty("Start datetime of auction")
    @Column(name = "start_date")
    private LocalDateTime startDate;

    /**
     * End datetime of auction
     */
    @ApiModelProperty("End datetime of auction")
    @Column(name = "end_date")
    private LocalDateTime endDate;

    /**
     * Rank of risk of product calculated by back-office
     */
    @ApiModelProperty("Rank of risk of product calculated by back-office")
    @Column(name = "risk_ranking")
    private Integer riskRanking;

    /**
     * Product creation date and time
     */
    @ApiModelProperty("Product creation date and time")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "created_datetime")
    private LocalDateTime createdDatetime;

    /**
     * Product name
     */
    @ApiModelProperty("Product name")
    @Column(name = "name")
    private String name;

    /**
     * Product description
     */
    @ApiModelProperty("Product description")
    @Column(name = "description")
    private String description;

    /**
     * Link to photo of product
     */
    @ApiModelProperty("Link to photo of product")
    @Column(name = "photo")
    private String photo;

    /**
     * Number of bids of lot
     */
    @ApiModelProperty("Number of bids of lot")
    @Formula("(SELECT COUNT(0) FROM tb_afe_transaction tt WHERE tt.transaction_type = 0 AND tt.product_id = afe_product_id AND tt.trans_status != 4 AND tt.trans_status != 2)")
    private Long bidCount;

    /**
     * Current price of product. Null if no bids for this product
     */
    @ApiModelProperty("Current price of product. Null if no bids for this product")
    @Column(name = "price")
    private Long price;
}
