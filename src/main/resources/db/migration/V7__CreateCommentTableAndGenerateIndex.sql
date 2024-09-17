CREATE TABLE comment (
    comment_id BINARY(16) PRIMARY KEY,
    content LONGTEXT NOT NULL,
    user_id BINARY(16),
    article_id BINARY(16),
    is_deleted TINYINT(1) NOT NULL DEFAULT 0,
    created_at datetime(6) NULL,
    updated_at datetime(6) NULL
)ENGINE=InnoDB

CHARSET=utf8mb4

COLLATE=utf8mb4_bin;

CREATE INDEX idx_user_id on comment (user_id);
CREATE INDEX idx_article_id on comment (article_id);
