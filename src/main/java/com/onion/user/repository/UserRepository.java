package com.onion.user.repository;

import com.onion.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

  Optional<UserEntity> findByUsername(String username);

  default UserEntity findByUsernameOrThrow(String username) {
    return findByUsername(username).orElseThrow(() -> new IllegalArgumentException("없는 아이디"));
  }

  default UserEntity findByIdOrThrow(Long id) {
    return findById(id).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원"));
  }
}
