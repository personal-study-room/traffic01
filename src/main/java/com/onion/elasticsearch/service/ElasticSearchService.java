package com.onion.elasticsearch.service;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.UpdateRequest;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ElasticSearchService {

    private final ElasticsearchClient elasticsearchClient;

    public <T> void upsertDocument(String indexName, T document, UUID entityId) throws IOException {
        elasticsearchClient.index(index -> index
                .index(indexName)
                .id(entityId.toString())
                .document(document)
        );
    }

    public <T> void updatePartialDocument(String indexName, Map<String, Object> updatedFields, UUID entityId)
            throws IOException {
        elasticsearchClient.update(UpdateRequest.of(update -> update
                .index(indexName)               // 인덱스 이름
                .id(entityId.toString())        // 문서 ID
                .doc(updatedFields)             // 수정할 필드가 포함된 객체
        ), Map.class);
    }

    // 진짜 제거 시에 사용하는데, 현재로썬 없음
    public <T> void deleteDocument(String indexName, UUID entityId) throws IOException {
        elasticsearchClient.delete(deleteRequest -> deleteRequest
                .index(indexName)
                .id(entityId.toString())
        );
    }
}
