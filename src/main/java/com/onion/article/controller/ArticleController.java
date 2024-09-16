package com.onion.article.controller;


import com.onion.article.dto.ArticleCreateDTO;
import com.onion.article.service.ArticleCommandService;
import com.onion.user.domain.UserDetailsImpl;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleCommandService articleCommandService;


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
}
