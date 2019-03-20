CREATE TABLE tb_afe_email_token (
    afe_email_token_id BIGINT       NOT NULL AUTO_INCREMENT,
    client_id          BIGINT       NOT NULL,
    token              VARCHAR(256) NOT NULL,

    CONSTRAINT PK__tb_afe_email_token$afe_email_token_id PRIMARY KEY BTREE (afe_email_token_id),
    CONSTRAINT FK__tb_afe_email_token$client_id FOREIGN KEY (client_id) REFERENCES tb_afe_client (afe_client_id)

);
