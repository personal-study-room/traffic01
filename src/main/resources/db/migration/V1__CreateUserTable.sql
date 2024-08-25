CREATE TABLE user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    last_login datetime(6) NULL,
    created_at datetime(6) null,
    updated_at datetime(6) null,
    UNIQUE (username),
    UNIQUE (email)
) ENGINE=InnoDB

CHARSET=utf8mb4

COLLATE=utf8mb4_bin;