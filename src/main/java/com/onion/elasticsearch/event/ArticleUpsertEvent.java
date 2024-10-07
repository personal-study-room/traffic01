package com.onion.elasticsearch.event;


import com.onion.elasticsearch.document.ArticleDocument;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ArticleUpsertEvent {
    private UUID articleId;
    private ArticleDocument articleDocument;
    private final String indexName = "article";

    public static ArticleUpsertEvent of(UUID articleId, ArticleDocument articleDocument) {
        return new ArticleUpsertEvent(articleId, articleDocument);
    }
}
