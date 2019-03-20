CREATE TABLE tb_afe_action (
    afe_action_id BIGINT       NOT NULL AUTO_INCREMENT,
    name          VARCHAR(128) NOT NULL,

    CONSTRAINT PK__tb_afe_action$afe_action_id PRIMARY KEY BTREE (afe_action_id),
    CONSTRAINT UQ__tb_afe_action$name UNIQUE (name)
);

INSERT INTO tb_afe_action (name) VALUES
    /* 1  */   ('SECURITY_PERMISSION_GET'),
    /* 2  */   ('CLIENT_IS_PHONE_NUMBER_AVAILABLE'),
    /* 3  */   ('CLIENT_UPDATE_INFO'),
    /* 4  */   ('CLIENT_UPDATE_PASSWORD'),
    /* 5  */   ('CLIENT_UPDATE_QUESTIONNAIRE_RESULT'),
    /* 6  */   ('_IS_CLIENT'),
    /* 7  */   ('_IS_BACK_OFFICE'),
    /* 8  */   ('CLIENT_GET_ALL'),
    /* 9  */   ('CLIENT_UPDATE_HIDE'),
    /* 10 */   ('CLIENT_UPDATE_UNHIDE'),
    /* 11 */   ('CLIENT_UPDATE_RISK_ATR'),
    /* 12 */   ('AUCTION_MAKE_BID'),
    /* 13 */   ('PRODUCT_REGISTER'),
    /* 14 */   ('PRODUCT_UPDATE_ATR'),
    /* 15 */   ('PRODUCT_UPDATE_INFO'),
    /* 16 */   ('AUCTION_BUYOUT'),
    /* 17 */   ('CLIENT_SHOW_RISK_RANKING_ATR'),
    /* 18 */   ('AUCTION_BID_WITHDRAW'),
    /* 19 */   ('AUCTION_BID_ACCEPT'),
    /* 20 */   ('PRODUCT_REJECT'),
    /* 21 */   ('PRODUCT_APPROVE'),
    /* 22 */   ('PRODUCT_APPROVE_DEAL'),
    /* 23 */   ('PRODUCT_GET_ALL_BY_STATUS'),
    /* 24 */   ('CLIENT_APPROVE'),
    /* 25 */   ('CLIENT_UPDATE_IMAGE'),
    /* 26 */   ('DICTIONARY_PLATFORM_UPDATE');