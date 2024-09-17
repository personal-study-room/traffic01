package com.onion.comment.service;


import com.onion.article.entity.ArticleEntity;
import com.onion.article.repository.ArticleRepository;
import com.onion.comment.dto.CommentDTO;
import com.onion.comment.entity.CommentEntity;
import com.onion.comment.mapper.CommentMapper;
import com.onion.comment.repository.CommentRepository;
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
}
