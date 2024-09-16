package com.onion.article.repository;

import com.onion.article.entity.ArticleEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {
}
