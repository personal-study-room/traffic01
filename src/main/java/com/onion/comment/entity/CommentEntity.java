package com.onion.comment.entity;

import static jakarta.persistence.FetchType.LAZY;
import static java.lang.Boolean.FALSE;

import com.onion.article.entity.ArticleEntity;
import com.onion.common.entity.BaseEntity;
import com.onion.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "comment")
@Table(indexes = {
        @Index(name = "idx_user_id", columnList = "user"),
        @Index(name = "idx_article_id", columnList = "article")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "comment_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    // 외래키는 최근에는 많이 걸진 않는다
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "article_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private ArticleEntity article;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = FALSE;
}
