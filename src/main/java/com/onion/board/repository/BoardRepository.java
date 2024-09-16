package com.onion.board.repository;

import com.onion.board.entity.BoardEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, UUID> {

    default BoardEntity findByIdOrThrow(UUID id) {
        return findById(id).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 게시판")
        );
    }
}
