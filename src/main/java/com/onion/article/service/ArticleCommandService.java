package com.onion.article.service;


import com.onion.article.entity.ArticleEntity;
import com.onion.article.mapper.ArticleMapper;
import com.onion.article.repository.ArticleRepository;
import com.onion.board.entity.BoardEntity;
import com.onion.board.repository.BoardRepository;
import com.onion.elasticsearch.document.ArticleDocument;
import com.onion.elasticsearch.event.ArticlePartialUpdateEvent;
import com.onion.elasticsearch.event.ArticleUpsertEvent;
import com.onion.user.entity.UserEntity;
import com.onion.user.mapper.UserMapper;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleCommandService {

    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;

    private final ApplicationEventPublisher applicationEventPublisher;

    public void writeArticle(UUID userId, String username, UUID boardId, String title, String content) {
        UserEntity user = UserMapper.toEntityOnlyId(userId);
        BoardEntity board = boardRepository.findByIdOrThrow(boardId);

        ArticleEntity article = ArticleMapper.toEntity(user, board, title, content);

        articleRepository.save(article);

        applicationEventPublisher.publishEvent(
                ArticleUpsertEvent.of(article.getId(), ArticleDocument.builder()
                        .author(username)
                        .content(content)
                        .title(title)
                        .viewCount(article.getViewCount())
                        .boardTitle(board.getTitle())
                        .updatedAt(article.getUpdatedAt())
                        .createdAt(article.getCreatedAt())
                        .build())
        );
    }

    public void updateArticle(UUID userId, String username, UUID boardId, UUID articleId, String title,
                              String content) {
        ArticleEntity article = articleRepository.findArticleEntityByBoardIdAndIdAndIsDeletedFalseOrThrow(boardId,
                articleId);

        if (!userId.equals(article.getUser().getId())) {
            throw new IllegalArgumentException("this article is not yours");
        }

        article.update(title, content);

        articleRepository.save(article);

        applicationEventPublisher.publishEvent(
                ArticleUpsertEvent.of(article.getId(), ArticleDocument.builder()
                        .author(username)
                        .content(content)
                        .title(title)
                        .viewCount(article.getViewCount())
                        .boardTitle(article.getBoard().getTitle())
                        .updatedAt(article.getUpdatedAt())
                        .createdAt(article.getCreatedAt())
                        .build())
        );


    }

    public void deleteArticle(UUID userId, String username, UUID boardId, UUID articleId) {
        ArticleEntity article = articleRepository.findArticleEntityByBoardIdAndIdAndIsDeletedFalseOrThrow(boardId,
                articleId);

        if (!userId.equals(article.getUser().getId())) {
            throw new IllegalArgumentException("this article is not yours");
        }

        article.deleteSoft();
        articleRepository.save(article);

        applicationEventPublisher.publishEvent(
                ArticleUpsertEvent.of(article.getId(), ArticleDocument.builder()
                        .author(username)
                        .content(article.getContent())
                        .title(article.getTitle())
                        .viewCount(article.getViewCount())
                        .boardTitle(article.getBoard().getTitle())
                        .updatedAt(article.getUpdatedAt())
                        .createdAt(article.getCreatedAt())
                        .isDeleted(true)
                        .build())
        );
    }

    public void increaseViewCount(UUID articleId) {
        log.info("Thread = {}", Thread.currentThread().getName());
        ArticleEntity article = articleRepository.findByIdOrThrow(articleId);
        Long viewCount = article.incrementViewCount();

        articleRepository.save(article);

        Map<String, Object> updateFields = new HashMap<>();
        updateFields.put("viewCount", viewCount);

        applicationEventPublisher.publishEvent(
                ArticlePartialUpdateEvent.of(articleId, updateFields)
        );
    }
}
