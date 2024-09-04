package com.onion.user.repository;

import com.onion.user.entity.UserEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);


    default UserEntity findByUsernameOrThrow(String username) {
        return findByUsername(username).orElseThrow(() -> new IllegalArgumentException("없는 아이디"));
    }

    default UserEntity findByIdOrThrow(UUID id) {
        return findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));
    }

    default UserEntity findByEmailOrThrow(String email) {
        return findByEmail(email).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 이메일"));
    }
}
