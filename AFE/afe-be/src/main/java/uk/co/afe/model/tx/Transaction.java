package uk.co.afe.model.tx;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import uk.co.afe.persistance.mapping.TransactionStatusConverter;
import uk.co.afe.persistance.mapping.TransactionTypeConverter;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Info of transaction
 *
 * @author Sergey Teryoshin
 * 24.03.2018 12:19
 */
@Data
@ToString
@ApiModel(description = "Info of transaction")
@Entity
@Table(name = "tb_afe_transaction")
public class Transaction {

    /**
     * Identifier of transaction
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "afe_tx_id")
    @ApiModelProperty("Identifier of transaction")
    private Long txId;

    /**
     * Type of transaction
     */
    @Convert(converter = TransactionTypeConverter.class)
    @Column(name = "transaction_type")
    @ApiModelProperty("Type of transaction")
    private TransactionType type;

    /**
     * Identifier of transaction owner
     */
    @Column(name = "client_id")
    @ApiModelProperty("Identifier of transaction owner")
    private Long clientId;

    /**
     * Identifier of product this transaction belongs to
     */
    @Column(name = "product_id")
    @ApiModelProperty("Identifier of product this transaction belongs to")
    private Long productId;

    /**
     * Amount of money of transaction
     */
    @Column(name = "amount")
    @ApiModelProperty("Amount of money of transaction")
    private Long amount;

    /**
     * Timestamp of transaction
     */
    @Column(name = "timestamp")
    @ApiModelProperty("Timestamp of transaction")
    private LocalDateTime timestamp;

    /**
     * Status of transaction
     */
    @Convert(converter = TransactionStatusConverter.class)
    @Column(name = "trans_status")
    @ApiModelProperty("Status of transaction")
    private TransactionStatus status;

}
