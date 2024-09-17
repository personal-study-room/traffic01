package com.onion.article.repository;

import com.onion.article.entity.ArticleEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {

    @Query("select a from article a where a.board.id = :boardId and a.isDeleted = false order by a.createdAt DESC limit :limit")
    List<ArticleEntity> findByBoardIdAndOrderByCreateDateDesc(@Param("boardId") UUID boardId,
                                                              @Param("limit") Integer limit);

    Optional<ArticleEntity> findByBoardIdAndIdAndIsDeletedFalse(UUID boardId, UUID id);

    Optional<ArticleEntity> findByIdAndIsDeletedFalse(UUID id);

    default ArticleEntity findByIdAndIsDeletedFalseOrThrow(UUID articleId) {
        return findByIdAndIsDeletedFalse(articleId).orElseThrow(
                () -> new IllegalArgumentException("Article not found"));
    }

    default ArticleEntity findByBoardIdAndIdAndIsDeletedFalseOrThrow(UUID boardId, UUID articleId) {
        return findByBoardIdAndIdAndIsDeletedFalse(boardId, articleId).orElseThrow(
                () -> new IllegalArgumentException("Article not found"));
    }
}
