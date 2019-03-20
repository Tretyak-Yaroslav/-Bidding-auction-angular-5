CREATE TABLE tb_afe_product (
    afe_product_id   BIGINT          NOT NULL AUTO_INCREMENT,
    product_owner_id BIGINT          NOT NULL,
    name             VARCHAR(128)    NOT NULL,
    description      VARCHAR(512)    NOT NULL,
    photo            VARCHAR(256)    NULL,
    product_status   SMALLINT        NOT NULL,
    platform_id      BIGINT          NOT NULL,
    starting_bid     BIGINT UNSIGNED NOT NULL,
    buy_out_price    BIGINT UNSIGNED NOT NULL,
    start_date       DATETIME        NOT NULL,
    end_date         DATETIME        NOT NULL,
    risk_ranking     SMALLINT        NULL,
    price            BIGINT UNSIGNED NOT NULL,
    created_datetime DATETIME        NOT NULL DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT PK__tb_afe_product$afe_product_id PRIMARY KEY BTREE (afe_product_id),
    CONSTRAINT FK__tb_afe_product$product_owner_id FOREIGN KEY (product_owner_id) REFERENCES tb_afe_client (afe_client_id),
    CONSTRAINT FK__tb_afe_product$afe_platform_id FOREIGN KEY (platform_id) REFERENCES tb_afe_platform (afe_platform_id)
);