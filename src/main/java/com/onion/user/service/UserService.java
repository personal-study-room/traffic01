package com.onion.user.service;


import com.onion.user.dto.UserCreateDTO;
import com.onion.user.entity.UserEntity;
import com.onion.user.mapper.UserMapper;
import com.onion.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserMapper userMapper;
  private final UserRepository userRepository;

  public void createUser(UserCreateDTO dto) {
    Optional<UserEntity> findUser = userRepository.findByUsername(dto.getUsername());

    if(findUser.isPresent()) {
      throw new IllegalArgumentException("Username already exists");
    }

    UserEntity newUser = userMapper.toEntity(dto.getUsername(), dto.getPassword(), dto.getEmail());
    userRepository.save(newUser);
  }

  public void deleteUser(Long userId) {
    UserEntity findUser = userRepository.findByIdOrThrow(userId);
    userRepository.delete(findUser);
  }
}
