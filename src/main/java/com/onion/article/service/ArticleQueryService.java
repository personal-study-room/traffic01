package com.onion.article.service;


import com.onion.article.dto.ArticleDTO;
import com.onion.article.entity.ArticleEntity;
import com.onion.article.mapper.ArticleMapper;
import com.onion.article.repository.ArticleRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleQueryService {

    private final ArticleRepository articleRepository;
    private final ArticleMapper articleMapper;


    public List<ArticleDTO> getLatestArticleInBoard(UUID boardId, Integer limit) {
        List<ArticleEntity> articles = articleRepository.findByBoardIdAndOrderByCreateDateDesc(boardId, limit);
        return articles.stream().map(articleMapper::toDTO).toList();
    }

    public ArticleDTO getArticle(UUID articleId) {
        ArticleEntity article = articleRepository.findByIdOrThrow(articleId);
        return articleMapper.toDTO(article);
    }
}
