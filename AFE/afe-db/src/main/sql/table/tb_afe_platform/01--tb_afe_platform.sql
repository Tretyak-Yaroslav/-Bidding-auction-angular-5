CREATE TABLE tb_afe_platform (
    afe_platform_id BIGINT       NOT NULL AUTO_INCREMENT,
    name            VARCHAR(128) NOT NULL,
    hidden          BIT          NOT NULL,

    CONSTRAINT PK__tb_afe_platform$afe_platform_id PRIMARY KEY BTREE (afe_platform_id)
);