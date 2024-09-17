package com.onion.comment.dto;


import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {

    private UUID commentId;
    private String content;
    private UUID userId;
    private UUID articleId;
}
