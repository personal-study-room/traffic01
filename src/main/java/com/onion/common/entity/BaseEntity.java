package com.onion.common.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.UUID;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;

    public static <T> T toEntityOnlyId(UUID id, Class<T> clazz) {
        T entity = null;
        try {
            entity = clazz.getDeclaredConstructor().newInstance();

            Field idField = clazz.getDeclaredField("id");
            idField.setAccessible(true);  // private 필드 접근 가능하도록 설정

            // 'id' 필드에 UUID 값을 세팅
            idField.set(entity, id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return entity;
    }
}
