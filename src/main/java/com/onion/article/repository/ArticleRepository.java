package com.onion.article.repository;

import com.onion.article.entity.ArticleEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {

    @Query("select a from article a where a.board.id = :boardId order by a.createdAt DESC limit :limit")
    List<ArticleEntity> findByBoardIdAndOrderByCreateDateDesc(@Param("boardId") UUID boardId,
                                                              @Param("limit") Integer limit);

    Optional<ArticleEntity> findByBoardIdAndId(UUID boardId, UUID id);

    default ArticleEntity findByIdOrThrow(UUID articleId) {
        return findById(articleId).orElseThrow(() -> new IllegalArgumentException("Article not found"));
    }

    default ArticleEntity findByBoardIdAndIdOrThrow(UUID boardId, UUID articleId) {
        return findByBoardIdAndId(boardId, articleId).orElseThrow(
                () -> new IllegalArgumentException("Article not found"));
    }
}
