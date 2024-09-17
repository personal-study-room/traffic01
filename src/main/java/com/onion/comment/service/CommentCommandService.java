package com.onion.comment.service;


import com.onion.article.entity.ArticleEntity;
import com.onion.article.repository.ArticleRepository;
import com.onion.comment.dto.CommentDTO;
import com.onion.comment.entity.CommentEntity;
import com.onion.comment.mapper.CommentMapper;
import com.onion.comment.repository.CommentRepository;
import com.onion.common.exception.CustomAccessDeniedException;
import com.onion.user.entity.UserEntity;
import com.onion.user.mapper.UserMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentCommandService {

    private final CommentRepository commentRepository;
    private final ArticleRepository articleRepository;

    public CommentDTO writeComment(String content, UUID userId, UUID articleId) {
        UserEntity user = UserMapper.toEntityOnlyId(userId);
        ArticleEntity article = articleRepository.findByIdAndIsDeletedFalseOrThrow(articleId);

        CommentEntity comment = CommentMapper.toEntity(content, user, article);
        commentRepository.save(comment);
        article.addComments(comment);
        return CommentMapper.toDTO(comment);
    }

    public CommentDTO updateComment(String content, UUID userId, UUID articleId, UUID commentId) {
        CommentEntity comment = commentRepository.findByIdAndIsDeletedIsFalseOrThrow(commentId);

        if (!userId.equals(comment.getUser().getId())) {
            throw new CustomAccessDeniedException("당신의 댓글이 아닙니다");
        }

        if (!articleId.equals(comment.getArticle().getId())) {
            throw new IllegalArgumentException("해당 게시글의 댓글이 아닙니다");
        }

        comment.updateContent(content);
        commentRepository.save(comment);

        return CommentMapper.toDTO(comment);
    }

    public void deleteComment(UUID userId, UUID articleId, UUID commentId) {
        CommentEntity comment = commentRepository.findByIdAndIsDeletedIsFalseOrThrow(commentId);

        if (!userId.equals(comment.getUser().getId())) {
            throw new CustomAccessDeniedException("당신의 댓글이 아닙니다");
        }

        if (!articleId.equals(comment.getArticle().getId())) {
            throw new IllegalArgumentException("해당 게시글의 댓글이 아닙니다");
        }

        comment.deleteSoft();
        commentRepository.save(comment);
    }
}
