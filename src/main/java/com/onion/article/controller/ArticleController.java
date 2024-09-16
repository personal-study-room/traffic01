package com.onion.article.controller;


import com.onion.article.dto.ArticleCreateDTO;
import com.onion.article.dto.ArticleDTO;
import com.onion.article.service.ArticleCommandService;
import com.onion.article.service.ArticleQueryService;
import com.onion.user.domain.UserDetailsImpl;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleCommandService articleCommandService;
    private final ArticleQueryService articleQueryService;

    @PostMapping("/{boardId}/articles")
    public ResponseEntity<Void> writeArticle(
            @PathVariable("boardId") UUID boardId,
            @RequestBody ArticleCreateDTO dto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        UUID userId = userDetails.getUserId();

        articleCommandService.writeArticle(userId, boardId, dto.getTitle(), dto.getContent());

        return ResponseEntity
                .status(HttpStatus.CREATED.value())
                .build();
    }

    @GetMapping("/{boardId}/articles")
    public ResponseEntity<List<ArticleDTO>> getLatestArticlesInBoard(
            @PathVariable("boardId") UUID boardId,
            @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit
    ) {
        return ResponseEntity.ok(articleQueryService.getLatestArticleInBoard(boardId, limit));
    }
}
