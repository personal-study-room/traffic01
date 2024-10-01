package com.onion.article.listener;

import com.onion.article.event.ArticleIncreaseViewCountEvent;
import com.onion.article.service.ArticleCommandService;
import com.onion.common.event.OnionListener;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ArticleIncreaseViewCountListener implements OnionListener<ArticleIncreaseViewCountEvent> {

    private final ArticleCommandService articleCommandService;

    @Async(value = "defaultAsyncTaskExecutor")
    @EventListener
    @Override
    public void onEvent(ArticleIncreaseViewCountEvent event) {
        articleCommandService.increaseViewCount(event.getArticleId());
    }
}
