UPDATE tb_afe_client_role r
SET r.role_id = 2
WHERE r.client_id = (SELECT afe_client_id FROM tb_afe_client where email = 'test@email.com');