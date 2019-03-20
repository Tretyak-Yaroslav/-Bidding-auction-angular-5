CREATE TABLE tb_afe_client (
    afe_client_id BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(128) NOT NULL,
    address       VARCHAR(768) NULL,
    postcode      VARCHAR(32)  NULL,
    email         VARCHAR(256) NOT NULL,
    phone_number  VARCHAR(15)  NULL,
    password      VARCHAR(128) NOT NULL,
    salt          VARCHAR(64)  NOT NULL,
    birthday      DATETIME     NULL,
    questionnaire JSON         NULL,
    atr           SMALLINT     NULL,
    hidden        BIT          NOT NULL DEFAULT 0,
    status        SMALLINT     NOT NULL,
    avatar        VARCHAR(256)  NULL,

    CONSTRAINT PK__tb_afe_client$afe_client_id PRIMARY KEY BTREE (afe_client_id),
    CONSTRAINT UQ__tb_afe_client$email UNIQUE (email),
    CONSTRAINT UQ__tb_afe_client$phone_number UNIQUE (phone_number)
);
