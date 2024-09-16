CREATE TABLE board (
    board_id BINARY(16) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    created_at datetime(6) NULL,
    updated_at datetime(6) NULL
)ENGINE=InnoDB

CHARSET=utf8mb4

COLLATE=utf8mb4_bin;


CREATE TABLE article (
    article_id BINARY(16) PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content LONGTEXT NOT NULL,
    user_id BINARY(16),
    board_id BINARY(16),
    created_at datetime(6) NULL,
    updated_at datetime(6) NULL
)ENGINE=InnoDB

CHARSET=utf8mb4

COLLATE=utf8mb4_bin;

