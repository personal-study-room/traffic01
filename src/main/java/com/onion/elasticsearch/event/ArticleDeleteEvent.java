package com.onion.elasticsearch.event;


import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleDeleteEvent {
    private UUID articleId;
    private final String indexName = "article";

    public static ArticleDeleteEvent of(UUID articleId) {
        return new ArticleDeleteEvent(articleId);
    }
}
