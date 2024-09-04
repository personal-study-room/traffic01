CREATE TABLE refresh_token (
    refresh_token_id BINARY(16) PRIMARY KEY,
    refresh_token VARCHAR(255) NOT NULL,
    user_id BINARY(16) NOT NULL,
    created_at datetime(6) NULL,
    updated_at datetime(6) NULL,
    CONSTRAINT fk_user_refresh_token FOREIGN KEY (user_id) REFERENCES user(user_id)
    ON DELETE CASCADE
    ON UPDATE CASCADE
) ENGINE=InnoDB

CHARSET=utf8mb4

COLLATE=utf8mb4_bin;