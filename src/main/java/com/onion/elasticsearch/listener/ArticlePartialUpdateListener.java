package com.onion.elasticsearch.listener;


import com.onion.common.event.OnionListener;
import com.onion.elasticsearch.event.ArticlePartialUpdateEvent;
import com.onion.elasticsearch.service.ElasticSearchService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ArticlePartialUpdateListener implements OnionListener<ArticlePartialUpdateEvent> {

    private final ElasticSearchService elasticSearchService;

    @Async(value = "defaultAsyncTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Override
    public void onEvent(ArticlePartialUpdateEvent event) throws IOException {
        elasticSearchService.updatePartialDocument(event.getIndexName(), event.getUpdateFields(), event.getArticleId());
    }
}
