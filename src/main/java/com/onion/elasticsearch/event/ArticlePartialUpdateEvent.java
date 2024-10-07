package com.onion.elasticsearch.event;


import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticlePartialUpdateEvent {
    private UUID articleId;
    private Map<String, Object> updateFields;
    private final String indexName = "article";

    public static ArticlePartialUpdateEvent of(UUID articleId, Map<String, Object> updateFields) {
        return new ArticlePartialUpdateEvent(articleId, updateFields);
    }
}
