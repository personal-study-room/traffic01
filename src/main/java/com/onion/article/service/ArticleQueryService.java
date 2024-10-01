package com.onion.article.service;


import com.onion.article.dto.ArticleDTO;
import com.onion.article.dto.ArticleDetailDTO;
import com.onion.article.entity.ArticleEntity;
import com.onion.article.event.ArticleIncreaseViewCountEvent;
import com.onion.article.mapper.ArticleMapper;
import com.onion.article.repository.ArticleRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true) // 읽기 전용 트랜잭션에서는 쓰기 잠금(lock)이나 트랜잭션 로그 기록 등의 오버헤드를 줄일 수 있음.
@RequiredArgsConstructor
public class ArticleQueryService {

    private final ArticleRepository articleRepository;
    private final ApplicationEventPublisher eventPublisher;


    public List<ArticleDTO> getLatestArticleInBoard(UUID boardId, Integer limit) {
        List<ArticleEntity> articles = articleRepository.findByBoardIdAndOrderByCreateDateDesc(boardId, limit);
        return articles.stream().map(ArticleMapper::toDTO).toList();
    }

    /**
     * @TestMethod - db 수준에서는 fk 를 걸진 않았지만, Jpa에서 제공하는 Lazy로딩의 기능은 활용 가능하다.
     */
    public List<ArticleDTO> getLatestArticleInBoard(UUID boardId) {
        List<ArticleEntity> articles = articleRepository.findByBoardIdAndIsDeletedIsFalseOrderByCreatedAtDesc(boardId);
        return articles.stream().map(ArticleMapper::toDTO).toList();
    }


    public ArticleDetailDTO getArticle(UUID boardId, UUID articleId) {
        log.info("Thread = {}", Thread.currentThread().getName());

        ArticleEntity article = articleRepository.findByBoardIdAndIdAndIsDeletedFalseOrThrow(boardId, articleId);

        eventPublisher.publishEvent(ArticleIncreaseViewCountEvent.of(articleId));

        return ArticleMapper.toDetailDTO(article);
    }
}