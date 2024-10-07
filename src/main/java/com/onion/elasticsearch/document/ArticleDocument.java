package com.onion.elasticsearch.document;


import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ArticleDocument {

    private String title;
    private String content;
    private String author;
    private String boardTitle;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long viewCount;
    @Builder.Default
    private Boolean isDeleted = false;
}
