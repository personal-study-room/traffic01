package com.onion.user.repository;


import com.onion.user.entity.RefreshTokenEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    Optional<RefreshTokenEntity> findByUserId(UUID userid);

    Optional<RefreshTokenEntity> findByRefreshToken(String token);

    default RefreshTokenEntity findByRefreshTokenOrThrow(String refreshToken) {
        return findByRefreshToken(refreshToken).orElseThrow(
                () -> new RuntimeException("Refresh token not found"));
    }
}
