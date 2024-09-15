CREATE TABLE jwt_blacklist (
    jwt_blacklist_id BINARY(16) PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    expiry_time datetime(6) NOT NULL,
    created_at datetime(6) NULL,
    updated_at datetime(6) NULL,
    constraint uk_jwt_blacklist_token UNIQUE (token)
)ENGINE=InnoDB

CHARSET=utf8mb4

COLLATE=utf8mb4_bin;