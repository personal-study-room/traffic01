package com.onion.article.repository;

import com.onion.article.entity.ArticleEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ArticleRepository extends JpaRepository<ArticleEntity, UUID> {

    @Query("select a from article a where a.board.id = :boardId and a.isDeleted = false order by a.createdAt DESC limit :limit")
    List<ArticleEntity> findByBoardIdAndOrderByCreateDateDesc(@Param("boardId") UUID boardId,
                                                              @Param("limit") Integer limit);

    @EntityGraph(attributePaths = {"user", "board"})
    List<ArticleEntity> findByBoardIdAndIsDeletedIsFalseOrderByCreatedAtDesc(UUID boardId);

    // soft delete와 함께 사용하기 위해선 명시적으로 Query를 사용하는게 나을 것 같음
//    @EntityGraph(attributePaths = {"user", "board", "comments"})
    // left-join 하지 않으면 댓글이 없을 경우, 데이터를 찾아올 수 없다.
    @Query("select a from article a "
            + "left join fetch a.user u "
            + "left join fetch a.board b "
            + "left join fetch a.comments cs "
            + "where a.board.id = :boardId and a.id = :id and a.isDeleted = false and cs.isDeleted = false")
    Optional<ArticleEntity> findByBoardIdAndIdAndIsDeletedFalse(@Param("boardId") UUID boardId, @Param("id") UUID id);

    @EntityGraph(attributePaths = {"board"})
    Optional<ArticleEntity> findArticleEntityByBoardIdAndIdAndIsDeletedFalse(UUID boardId, UUID id);

    Optional<ArticleEntity> findByIdAndIsDeletedIsFalse(UUID id);

    default ArticleEntity findByIdAndIsDeletedFalseOrThrow(UUID articleId) {
        return findByIdAndIsDeletedIsFalse(articleId).orElseThrow(
                () -> new IllegalArgumentException("Article not found"));
    }

    default ArticleEntity findByBoardIdAndIdAndIsDeletedFalseOrThrow(UUID boardId, UUID articleId) {
        return findByBoardIdAndIdAndIsDeletedFalse(boardId, articleId).orElseThrow(
                () -> new IllegalArgumentException("Article not found"));
    }

    default ArticleEntity findArticleEntityByBoardIdAndIdAndIsDeletedFalseOrThrow(UUID boardId, UUID articleId) {
        return findArticleEntityByBoardIdAndIdAndIsDeletedFalse(boardId, articleId).orElseThrow(
                () -> new IllegalArgumentException("Article not found"));
    }

    default ArticleEntity findByIdOrThrow(UUID articleId) {
        return findById(articleId).orElseThrow(() -> new IllegalArgumentException("Article not found"));
    }
}
