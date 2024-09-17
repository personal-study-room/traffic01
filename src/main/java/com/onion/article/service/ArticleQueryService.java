package com.onion.article.service;


import com.onion.article.dto.ArticleDTO;
import com.onion.article.dto.ArticleDetailDTO;
import com.onion.article.entity.ArticleEntity;
import com.onion.article.mapper.ArticleMapper;
import com.onion.article.repository.ArticleRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ArticleQueryService {

    private final ArticleRepository articleRepository;


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

        ArticleEntity article = articleRepository.findByBoardIdAndIdAndIsDeletedFalseOrThrow(boardId, articleId);

        return ArticleMapper.toDetailDTO(article);
    }
}
