package com.onion.comment.mapper;


import com.onion.article.entity.ArticleEntity;
import com.onion.comment.dto.CommentDTO;
import com.onion.comment.entity.CommentEntity;
import com.onion.user.entity.UserEntity;

public class CommentMapper {

    public static CommentEntity toEntity(String content, UserEntity user, ArticleEntity article) {
        return CommentEntity.builder()
                .content(content)
                .user(user)
                .article(article)
                .build();
    }

    public static CommentDTO toDTO(CommentEntity commentEntity) {
        return CommentDTO.builder()
                .commentId(commentEntity.getId())
                .content(commentEntity.getContent())
                .userId(commentEntity.getUser().getId())
                .articleId(commentEntity.getArticle().getId())
                .build();
    }

}
