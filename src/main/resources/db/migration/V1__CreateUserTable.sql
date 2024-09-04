CREATE TABLE user (
    user_id BINARY(16) PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    `password` VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    user_role VARCHAR(255) NOT NULL,
    last_login datetime(6) NULL,
    created_at datetime(6) null,
    updated_at datetime(6) null,
    UNIQUE (email)
) ENGINE=InnoDB

CHARSET=utf8mb4

COLLATE=utf8mb4_bin;