package com.onion.comment.service;


import com.onion.comment.dto.CommentDTO;
import com.onion.comment.entity.CommentEntity;
import com.onion.comment.mapper.CommentMapper;
import com.onion.comment.repository.CommentRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentQueryService {

    private final CommentRepository commentRepository;

    public List<CommentDTO> getCommentsIsDeletedFalse(UUID articleId) {
        List<CommentEntity> comments = commentRepository.findByArticleIdAndIsDeletedIsFalse(
                articleId);

        return comments.stream().map(CommentMapper::toDTO).toList();
    }
}
