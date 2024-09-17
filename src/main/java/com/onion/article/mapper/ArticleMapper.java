package com.onion.article.mapper;


import com.onion.article.dto.ArticleDTO;
import com.onion.article.dto.ArticleDetailDTO;
import com.onion.article.entity.ArticleEntity;
import com.onion.board.entity.BoardEntity;
import com.onion.comment.mapper.CommentMapper;
import com.onion.user.entity.UserEntity;

public class ArticleMapper {

    public static ArticleEntity toEntity(UserEntity userEntity, BoardEntity boardEntity, String title, String content) {
        return ArticleEntity.builder()
                .content(content)
                .title(title)
                .user(userEntity)
                .board(boardEntity)
                .build();
    }

    public static ArticleDTO toDTO(ArticleEntity articleEntity) {
        return ArticleDTO.builder()
                .articleId(articleEntity.getId())
                .title(articleEntity.getTitle())
                .content(articleEntity.getContent())
                // TODO : 연관관계없이 id만 매핑을 해놓은 상태인데, 이게 가능한가? jpa 에서 객체는 이걸 기억할 수 있는가? => 가능.
                .userId(articleEntity.getUser().getId())
                .boardId(articleEntity.getBoard().getId())
                .build();
    }

    public static ArticleDetailDTO toDetailDTO(ArticleEntity articleEntity) {
        return ArticleDetailDTO.builder()
                .articleId(articleEntity.getId())
                .title(articleEntity.getTitle())
                .content(articleEntity.getContent())
                .userId(articleEntity.getUser().getId())
                .username(articleEntity.getUser().getUsername())
                .boardId(articleEntity.getBoard().getId())
                .boardTitle(articleEntity.getBoard().getTitle())
                .comments(
                        articleEntity.getComments().stream().map(CommentMapper::toDTO).toList()
                )
                .build();
    }

}
