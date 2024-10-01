package com.onion.article.event;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class ArticleIncreaseViewCountEvent {
    private final UUID articleId;

    public static ArticleIncreaseViewCountEvent of(UUID articleId) {
        return new ArticleIncreaseViewCountEvent(articleId);
    }

}
