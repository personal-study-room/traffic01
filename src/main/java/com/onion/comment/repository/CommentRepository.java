package com.onion.comment.repository;

import com.onion.comment.entity.CommentEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity, UUID> {


    List<CommentEntity> findByArticleIdAndIsDeletedIsFalse(UUID articleId);

    Optional<CommentEntity> findByIdAndIsDeletedIsFalse(UUID commentId);

    default CommentEntity findByIdAndIsDeletedIsFalseOrThrow(UUID commentId) {
        return findByIdAndIsDeletedIsFalse(commentId).orElseThrow(
                () -> new IllegalStateException("Comment with id " + commentId + " not found")
        );
    }

}
