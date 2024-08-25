package com.onion.user.mapper;


import com.onion.user.entity.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

  public UserEntity toEntity(String username, String password, String email) {
    return UserEntity.builder()
            .email(email)
            .password(password)
            .username(username)
            .build();
  }

}
