package com.onion.article.service;


import com.onion.article.entity.ArticleEntity;
import com.onion.article.mapper.ArticleMapper;
import com.onion.article.repository.ArticleRepository;
import com.onion.board.entity.BoardEntity;
import com.onion.board.repository.BoardRepository;
import com.onion.user.entity.UserEntity;
import com.onion.user.mapper.UserMapper;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class ArticleCommandService {

    private final ArticleRepository articleRepository;
    private final BoardRepository boardRepository;

    public void writeArticle(UUID userId, UUID boardId, String title, String content) {
        UserEntity user = UserMapper.toEntityOnlyId(userId);
        BoardEntity board = boardRepository.findByIdOrThrow(boardId);

        ArticleEntity article = ArticleMapper.toEntity(user, board, title, content);

        articleRepository.save(article);
    }

    public void updateArticle(UUID userId, UUID boardId, UUID articleId, String title, String content) {
        ArticleEntity article = articleRepository.findArticleEntityByBoardIdAndIdAndIsDeletedFalseOrThrow(boardId,
                articleId);

        if (!userId.equals(article.getUser().getId())) {
            throw new IllegalArgumentException("this article is not yours");
        }

        article.update(title, content);

        articleRepository.save(article);
    }

    public void deleteArticle(UUID userId, UUID boardId, UUID articleId) {
        ArticleEntity article = articleRepository.findArticleEntityByBoardIdAndIdAndIsDeletedFalseOrThrow(boardId,
                articleId);

        if (!userId.equals(article.getUser().getId())) {
            throw new IllegalArgumentException("this article is not yours");
        }

        article.deleteSoft();
        articleRepository.save(article);
    }

    public void increaseViewCount(UUID articleId) {
        log.info("Thread = {}", Thread.currentThread().getName());
        articleRepository.increaseViewCount(articleId);
    }
}
