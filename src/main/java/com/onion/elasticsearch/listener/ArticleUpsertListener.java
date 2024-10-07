package com.onion.elasticsearch.listener;


import com.onion.common.event.OnionListener;
import com.onion.elasticsearch.event.ArticleUpsertEvent;
import com.onion.elasticsearch.service.ElasticSearchService;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class ArticleUpsertListener implements OnionListener<ArticleUpsertEvent> {

    private final ElasticSearchService elasticSearchService;

    @Async(value = "defaultAsyncTaskExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    @Override
    public void onEvent(ArticleUpsertEvent event) throws IOException {

        elasticSearchService.upsertDocument(event.getIndexName(), event.getArticleDocument(), event.getArticleId());

    }
}
