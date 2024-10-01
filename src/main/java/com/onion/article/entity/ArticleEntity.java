package com.onion.article.entity;


import static jakarta.persistence.FetchType.LAZY;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

import com.onion.board.entity.BoardEntity;
import com.onion.comment.entity.CommentEntity;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "article")
@Table(indexes = {
        @Index(name = "idx_user_id", columnList = "user"),
        @Index(name = "idx_board_id", columnList = "board")
})
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArticleEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "article_id", columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Builder.Default
    @OneToMany(fetch = LAZY, mappedBy = "article")
    private List<CommentEntity> comments = new ArrayList<>();

    // 외래키는 최근에는 많이 걸진 않는다
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private UserEntity user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "board_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private BoardEntity board;

    @Column(nullable = false)
    @Builder.Default
    private Long viewCount = 0L;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isDeleted = FALSE;

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void deleteSoft() {
        isDeleted = TRUE;
    }

    public void addComments(CommentEntity comment) {
        this.comments.add(comment);
    }
}
