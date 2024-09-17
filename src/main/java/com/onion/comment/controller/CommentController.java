package com.onion.comment.controller;


import com.onion.comment.dto.CommentDTO;
import com.onion.comment.dto.CommentWriteDTO;
import com.onion.comment.service.CommentCommandService;
import com.onion.comment.service.CommentQueryService;
import com.onion.user.domain.UserDetailsImpl;
import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/articles")
public class CommentController {

    private final CommentCommandService commentCommandService;
    private final CommentQueryService commentQueryService;

    @PostMapping("/{articleId}/comments")
    public ResponseEntity<CommentDTO> writeComment(
            @PathVariable("articleId") UUID articleId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody CommentWriteDTO dto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorProperties = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                    .toList();

            throw new IllegalArgumentException(String.join(", ", errorProperties));
        }

        UUID userId = userDetails.getUserId();

        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(commentCommandService.writeComment(dto.getContent(), userId, articleId));
    }


    @PutMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<CommentDTO> updateComment(
            @PathVariable("articleId") UUID articleId,
            @PathVariable("commentId") UUID commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @Valid @RequestBody CommentWriteDTO dto,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            List<String> errorProperties = bindingResult.getAllErrors().stream().map(ObjectError::getDefaultMessage)
                    .toList();

            throw new IllegalArgumentException(String.join(", ", errorProperties));
        }

        UUID userId = userDetails.getUserId();

        return ResponseEntity.status(HttpStatus.CREATED.value())
                .body(commentCommandService.updateComment(dto.getContent(), userId, articleId, commentId));
    }


    @GetMapping("/{articleId}/comments")
    public ResponseEntity<List<CommentDTO>> getCommentsIsDeletedFalse(@PathVariable("articleId") UUID articleId) {

        List<CommentDTO> result = commentQueryService.getCommentsIsDeletedFalse(articleId);

        return ResponseEntity.ok(Collections.unmodifiableList(result));
    }

    @DeleteMapping("/{articleId}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable("articleId") UUID articleId,
            @PathVariable("commentId") UUID commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        UUID userId = userDetails.getUserId();
        commentCommandService.deleteComment(userId, articleId, commentId);
        return ResponseEntity.noContent().build();
    }

}
