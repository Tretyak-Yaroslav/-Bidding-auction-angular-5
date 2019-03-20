CREATE DATABASE IF NOT EXISTS db_afe;

CREATE USER IF NOT EXISTS 'db_afe_admin'@'localhost' IDENTIFIED WITH mysql_native_password;
SET old_passwords = 0;
SET PASSWORD FOR 'db_afe_admin'@'localhost' = PASSWORD('db_afe_admin');

GRANT ALL PRIVILEGES ON db_afe.* TO 'db_afe_admin'@'localhost' WITH GRANT OPTION;

CREATE USER IF NOT EXISTS 'db_afe_gate'@'localhost' IDENTIFIED WITH mysql_native_password;
SET old_passwords = 0;
SET PASSWORD FOR 'db_afe_gate'@'localhost' = PASSWORD('db_afe_gate');

GRANT ALL PRIVILEGES ON db_afe.* TO 'db_afe_gate'@'localhost' WITH GRANT OPTION;