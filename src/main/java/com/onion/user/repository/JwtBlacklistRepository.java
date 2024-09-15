package com.onion.user.repository;

import com.onion.user.entity.JwtBlacklistEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JwtBlacklistRepository extends JpaRepository<JwtBlacklistEntity, UUID> {

    Optional<JwtBlacklistEntity> findByToken(String token);
}
