CREATE TABLE tb_afe_client_role (
    afe_client_role_id BIGINT NOT NULL AUTO_INCREMENT,
    client_id          BIGINT NOT NULL,
    role_id            BIGINT NOT NULL,

    CONSTRAINT PK__tb_afe_client_role$afe_client_role_id PRIMARY KEY BTREE (afe_client_role_id),
    CONSTRAINT FK__tb_afe_client_role$role_id FOREIGN KEY (role_id) REFERENCES tb_afe_role (afe_role_id),
    CONSTRAINT FK__tb_afe_client_role$client_id FOREIGN KEY (client_id) REFERENCES tb_afe_client (afe_client_id),
    CONSTRAINT UQ__tb_afe_client_role$client_id$role_id UNIQUE (client_id, role_id)
);

CREATE INDEX IX__tb_afe_client_role$client_id
    ON tb_afe_client_role (client_id) LOCK NONE;