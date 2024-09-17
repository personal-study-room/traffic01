package com.onion.article.controller;


import com.onion.article.dto.ArticleDTO;
import com.onion.article.dto.ArticleWriteDTO;
import com.onion.article.service.ArticleCommandService;
import com.onion.article.service.ArticleQueryService;
import com.onion.user.domain.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
    public ResponseEntity<Void> writeArticle(@PathVariable("boardId") UUID boardId,
                                             @RequestBody @Valid ArticleWriteDTO dto, BindingResult bindingResult,
                                             @AuthenticationPrincipal UserDetailsImpl userDetails)
            throws BadRequestException {
        if (bindingResult.hasErrors()) {
            List<String> errorProperties = bindingResult.getAllErrors().stream().map(ObjectError::getObjectName)
                    .toList();

            throw new BadRequestException(String.join(", ", errorProperties));
        }

        UUID userId = userDetails.getUserId();

        articleCommandService.writeArticle(userId, boardId, dto.getTitle(), dto.getContent());

        return ResponseEntity.status(HttpStatus.CREATED.value()).build();
    }

    @PutMapping("/{boardId}/articles/{articleId}")
    public ResponseEntity<Void> updateArticle(@PathVariable("boardId") UUID boardId,
                                              @PathVariable("articleId") UUID articleId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails,
                                              @Valid @RequestBody ArticleWriteDTO dto, BindingResult bindingResult)
            throws BadRequestException {
        if (bindingResult.hasErrors()) {
            List<String> errorProperties = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                    .toList();

            throw new IllegalArgumentException(String.join(", ", errorProperties));
        }

        UUID userId = userDetails.getUserId();

        articleCommandService.updateArticle(userId, boardId, articleId, dto.getTitle(), dto.getContent());

        return ResponseEntity.status(HttpStatus.NO_CONTENT.value()).build();
    }

    @GetMapping("/{boardId}/articles")
    public ResponseEntity<List<ArticleDTO>> getLatestArticlesInBoard(@PathVariable("boardId") UUID boardId,
                                                                     @RequestParam(name = "limit", required = false, defaultValue = "10") Integer limit) {
        return ResponseEntity.ok(articleQueryService.getLatestArticleInBoard(boardId, limit));
    }
}
