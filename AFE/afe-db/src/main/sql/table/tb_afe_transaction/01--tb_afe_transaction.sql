CREATE TABLE tb_afe_transaction (
    afe_tx_id        BIGINT    NOT NULL AUTO_INCREMENT,
    transaction_type SMALLINT  NOT NULL,
    client_id        BIGINT    NOT NULL,
    product_id       BIGINT    NOT NULL,
    amount           BIGINT    NOT NULL,
    timestamp        TIMESTAMP NOT NULL,
    trans_status     SMALLINT  NOT NULL,

    CONSTRAINT PK__tb_afe_transaction$afe_tx_id PRIMARY KEY BTREE (afe_tx_id),
    CONSTRAINT FK__tb_afe_transaction$client_id FOREIGN KEY (client_id) REFERENCES tb_afe_client (afe_client_id),
    CONSTRAINT FK__tb_afe_transaction$product_id FOREIGN KEY (product_id) REFERENCES tb_afe_product (afe_product_id)
);