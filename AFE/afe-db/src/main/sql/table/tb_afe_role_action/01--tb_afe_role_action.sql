CREATE TABLE tb_afe_role_action (
    role_id   BIGINT NOT NULL,
    action_id BIGINT NOT NULL,

    CONSTRAINT FK__tb_afe_role_action$role_id FOREIGN KEY (role_id) REFERENCES tb_afe_role (afe_role_id),
    CONSTRAINT FK__tb_afe_role_action$action_id FOREIGN KEY (action_id) REFERENCES tb_afe_action (afe_action_id),
    CONSTRAINT UQ__tb_afe_role_action$role_id$action_id UNIQUE (role_id, action_id)
);

CREATE INDEX IX__tb_afe_role_action$role_id
    ON tb_afe_role_action (role_id) LOCK NONE;

INSERT INTO tb_afe_role_action (role_id, action_id) VALUES
    (1, 1),  -- SECURITY_PERMISSION_GET
    (1, 2),  -- CLIENT_IS_PHONE_NUMBER_AVAILABLE
    (1, 3),  -- CLIENT_UPDATE_INFO
    (1, 4),  -- CLIENT_UPDATE_PASSWORD
    (1, 5),  -- CLIENT_UPDATE_QUESTIONNAIRE_RESULT
    (1, 6),  -- _IS_CLIENT
    (1, 12), -- AUCTION_MAKE_BID
    (1, 13), -- PRODUCT_REGISTER
    (1, 15), -- PRODUCT_UPDATE_INFO
    (1, 16), -- AUCTION_BUYOUT
    (1, 17), -- CLIENT_SHOW_RISK_RANKING_ATR
    (1, 18), -- AUCTION_BID_WITHDRAW
    (1, 19), -- AUCTION_BID_ACCEPT
    (1, 25), -- CLIENT_UPDATE_IMAGE

    (2, 1),  -- SECURITY_PERMISSION_GET
    (2, 2),  -- CLIENT_IS_PHONE_NUMBER_AVAILABLE
    (2, 3),  -- CLIENT_UPDATE_INFO
    (2, 4),  -- CLIENT_UPDATE_PASSWORD
    (2, 7),  -- _IS_BACK_OFFICE
    (2, 8),  -- CLIENT_GET_ALL
    (2, 9),  -- CLIENT_UPDATE_HIDE
    (2, 10), -- CLIENT_UPDATE_UNHIDE
    (2, 11), -- CLIENT_UPDATE_RISK_ATR
    (2, 14), -- PRODUCT_UPDATE_ATR
    (2, 20), -- PRODUCT_REJECT
    (2, 21), -- PRODUCT_APPROVE
    (2, 23), -- PRODUCT_GET_ALL_BY_STATUS
    (2, 24), -- CLIENT_APPROVE
    (2, 26), -- DICTIONARY_PLATFORM_UPDATE
    (2, 22); -- PRODUCT_APPROVE_DEAL