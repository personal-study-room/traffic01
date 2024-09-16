package com.onion.article.dto;


import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {
    private UUID articleId;
    private String title;
    private String content;
    private UUID userId;
    private UUID boardId;
}
