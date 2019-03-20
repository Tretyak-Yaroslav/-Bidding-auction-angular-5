CREATE TABLE tb_afe_role (
    afe_role_id BIGINT       NOT NULL AUTO_INCREMENT,
    name        VARCHAR(128) NOT NULL,

    CONSTRAINT PK__tb_afe_role$afe_role_id PRIMARY KEY BTREE (afe_role_id),
    CONSTRAINT UQ__tb_afe_role$name UNIQUE (name)
);

INSERT INTO tb_afe_role (name) VALUES
    ('Client'),
    ('Backoffice');